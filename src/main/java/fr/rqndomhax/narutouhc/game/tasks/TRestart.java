/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import org.bukkit.Bukkit;

public class TRestart implements Task {

    private final TMain mainTask;
    private int remainingTime;

    public TRestart(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;

        if (remainingTime == 0) {
            mainTask.lastTaskFinished = true;
            Bukkit.getServer().reload();
            return;
        }
        remainingTime--;
    }
}
