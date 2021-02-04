/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EPlayerLogin implements Listener {

    private final Setup setup;

    public EPlayerLogin(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {

        if (setup.getGame().getGameState().equals(GameState.LOADING)) {
            e.setKickMessage(Messages.SERVER_STARTING);
            return;
        }

        if (setup.getGame().getGameRules().bannedPlayers.contains(e.getPlayer().getUniqueId())) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Messages.PLAYER_BANNED);
            return;
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage(Messages.PLAYER_JOIN.replace("%player%", e.getPlayer().getName()));

        setup.getGameScoreboard().newGameScoreboard(e.getPlayer());

        if (setup.getGame().getGameRules().gameHost == null)
            setup.getGame().getGameRules().gameHost = e.getPlayer().getUniqueId();

        if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            if (setup.getGame().getMainTask() != null) {
                MGameActions.sendInfos(setup.getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage " + ChatColor.RED + "annulé", Instrument.BASS_DRUM, true, 0, Note.Tone.B);
                setup.getGame().removeTask();
            }

            GamePlayer gamePlayer = new GamePlayer(e.getPlayer().getUniqueId());
            setup.getGame().getGamePlayers().add(gamePlayer);
            MGameActions.clearPlayerLobby(setup.getGame().getGameRules(), e.getPlayer());

            e.getPlayer().teleport(new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 230, 0));
            return;
        }

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());

        if (gamePlayer != null)
                MVillagers.deleteVillager(gamePlayer);

        else {
            MGameActions.clearPlayer(e.getPlayer());
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getUniqueId() != e.getPlayer().getUniqueId() && !player.getGameMode().equals(GameMode.SPECTATOR)).findAny().ifPresent(player -> e.getPlayer().teleport(player.getLocation()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        e.setQuitMessage(Messages.PLAYER_LEFT.replace("%player%", e.getPlayer().getName()));

        setup.getGameScoreboard().removeGameScoreboard(e.getPlayer());

        if (Bukkit.getOnlinePlayers().size() == 1)
            setup.getGame().getGameRules().gameHost = null;

        setup.getGame().getGameRules().gameCoHost.remove(e.getPlayer().getUniqueId());

        if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            if (setup.getGame().getMainTask() != null) {
                MGameActions.sendInfos(setup.getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage " + ChatColor.RED + "annulé", Instrument.BASS_DRUM, true, 0, Note.Tone.B);
                setup.getGame().removeTask();
            }
            setup.getGame().getGamePlayers().removeIf(player -> player.uuid == e.getPlayer().getUniqueId());
            return;
        }

        GamePlayer player = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());
        if (player != null && !player.isDead)
            MVillagers.createVillager(e.getPlayer().getLocation(), player);
    }
}
