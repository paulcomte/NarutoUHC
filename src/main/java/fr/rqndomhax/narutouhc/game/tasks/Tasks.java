/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import java.lang.reflect.InvocationTargetException;

public enum Tasks {

    START(TStart.class),
    WAIT(TPlatform.class),
    INVINCIBILITY(TInvincibility.class),
    PREPARATION(TPreparation.class),
    TELEPORTATION(TTeleportation.class),
    TELEPORTATION_INVINCIBILITY(TInvincibility.class),
    BORDER(TBorder.class),
    MEETUP(TMeetup.class),
    RESTART(TRestart.class);

    private final Class<? extends Task> runnable;

    Tasks(Class<? extends Task> runnable) {
        this.runnable = runnable;
    }

    public Class<? extends Task> getRunnable() {
        return runnable;
    }

    public static boolean startNextTask(TMain mainTask) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (mainTask == null || mainTask.remainingTasks.isEmpty())
            return false;
        mainTask.task = (Task) mainTask.remainingTasks.get(0).getRunnable().getDeclaredConstructors()[0].newInstance(mainTask);
        mainTask.remainingTasks.remove(0);
        return true;
    }
}
