/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOADING)) {
            e.setKickMessage(Messages.SERVER_STARTING);
            return;
        }

        if (setup.getGame().getGameInfo().getMRules().bannedPlayers.contains(e.getPlayer().getUniqueId())) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Messages.PLAYER_BANNED);
            return;
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage(Messages.PLAYER_JOIN.replace("%player%", e.getPlayer().getName()));

        setup.getGameScoreboard().newGameScoreboard(e.getPlayer());

        if (setup.getGame().getGameInfo().getMRules().gameHost == null)
            setup.getGame().getGameInfo().getMRules().gameHost = e.getPlayer().getUniqueId();

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING)) {
            MPlayer mPlayer = new MPlayer(e.getPlayer().getUniqueId());
            setup.getGame().getGamePlayers().add(mPlayer);
            MGameActions.clearPlayerLobby(setup, e.getPlayer());
            e.getPlayer().spigot().respawn();
            e.getPlayer().teleport(new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 230, 0));
            return;
        }
        if (setup.getGame().getMPlayer(e.getPlayer().getUniqueId()) == null) {
            MGameActions.clearPlayer(e.getPlayer());
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getUniqueId() != e.getPlayer().getUniqueId()).findAny().ifPresent(player -> e.getPlayer().teleport(player.getLocation()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        e.setQuitMessage(Messages.PLAYER_LEFT.replace("%player%", e.getPlayer().getName()));

        setup.getGameScoreboard().removeGameScoreboard(e.getPlayer());

        if (Bukkit.getOnlinePlayers().size() == 1)
            setup.getGame().getGameInfo().getMRules().gameHost = null;

        setup.getGame().getGameInfo().getMRules().gameCoHost.remove(e.getPlayer().getUniqueId());

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING)) {
            setup.getGame().getGamePlayers().removeIf(player -> player.uuid == e.getPlayer().getUniqueId());
            return;
        }

        MPlayer player = setup.getGame().getMPlayer(e.getPlayer().getUniqueId());
        if (player == null || player.isDead)
            return;
    }
}
