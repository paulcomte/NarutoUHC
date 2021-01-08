/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EPlayerLogin implements Listener {

    private final Setup setup;

    public EPlayerLogin(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

    }
}
