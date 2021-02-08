/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.orochimaru;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class TList extends BukkitRunnable {

    private final Setup setup;
    int remainingTime = 5*60;

    public TList(Setup setup) {
        this.setup = setup;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED)) {
            cancel();
            return;
        }

        if (remainingTime == 0) {
            // TODO SEND LIST
            cancel();
            return;
        }
        remainingTime--;
    }

}
