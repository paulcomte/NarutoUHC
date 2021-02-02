/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

public enum Tasks {

    START(TStart.class),
    WAIT(TPlatform.class),
    INVINCIBILITY(TInvincibility.class),
    PREPARATION(TPreparation.class),
    TELEPORTATION(TTeleportation.class),
    BORDER(TBorder.class),
    MEETUP(TMeetup.class);

    private final Class<? extends Task> runnable;

    Tasks(Class<? extends Task> runnable) {
        this.runnable = runnable;
    }

    public Class<? extends Task> getRunnable() {
        return runnable;
    }
}
