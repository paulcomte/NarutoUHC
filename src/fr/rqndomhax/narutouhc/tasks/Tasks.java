/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import org.bukkit.scheduler.BukkitRunnable;

public enum Tasks {

    START(TStart.class),
    WAIT(TWait.class);

    private final Class<? extends BukkitRunnable> runnable;

    Tasks(Class<? extends BukkitRunnable> runnable) {
        this.runnable = runnable;
    }

    public Class<? extends BukkitRunnable> getRunnable() {
        return runnable;
    }
}
