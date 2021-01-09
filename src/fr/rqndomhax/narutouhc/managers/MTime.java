/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import org.bukkit.scheduler.BukkitRunnable;

public class MTime extends BukkitRunnable {

    private final Setup setup;
    public int time = 0;

    public MTime(Setup setup) {
        this.setup = setup;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (time == setup.getGame().getGameInfo().getmBorder().timeBeforeResize)
            setup.getGame().getGameInfo().getmBorder().resizeBorder(setup);

        time++;
    }

}
