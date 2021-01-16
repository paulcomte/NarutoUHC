/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
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
            e.setKickMessage(Messages.PLAYER_BANNED);
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
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        e.setQuitMessage(Messages.PLAYER_LEFT.replace("%player%", e.getPlayer().getName()));

        setup.getGameScoreboard().removeGameScoreboard(e.getPlayer());

        if (Bukkit.getOnlinePlayers().size() == 1)
            setup.getGame().getGameInfo().getMRules().gameHost = null;

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING))
            setup.getGame().getGamePlayers().removeIf(p -> p.uuid == e.getPlayer().getUniqueId());
    }
}
