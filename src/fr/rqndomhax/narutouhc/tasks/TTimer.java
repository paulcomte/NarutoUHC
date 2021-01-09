/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class TTimer extends BukkitRunnable {

    private final Setup setup;
    public int i;

    public TTimer(Setup setup, int i) {
        this.setup = setup;
        this.i = i;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (i == 0) {
            setup.getGame().getGameInfo().setGameState(setup.getGame().getGameInfo().getGameState().nextGameState());
            cancel();
            return;
        }
        i--;
    }
}
