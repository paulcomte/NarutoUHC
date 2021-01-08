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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EPlayerLogin implements Listener {

    private final Setup setup;

    public EPlayerLogin(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING))
            setup.getGame().getGamePlayers().add(new MPlayer(e.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING))
            setup.getGame().getGamePlayers().removeIf(p -> p.uuid == e.getPlayer().getUniqueId());
    }
}
