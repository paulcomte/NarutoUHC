/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class TWait extends BukkitRunnable {

    private final Setup setup;

    public TWait(Setup setup) {
        this.setup = setup;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (MGameBuild.hasBuilt) {
            setup.getGame().setGameState(GameState.LOBBY_WAITING);
            ServerPing.currentPing = Pings.LOBBY_WAITING;
            Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INITIALIZED);
            cancel();
            return;
        }
    }
}
