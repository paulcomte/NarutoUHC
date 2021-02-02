/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

public class TMeetup implements Task {

    private final TMain mainTask;

    public TMeetup(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null || !mainTask.isAlive)
            return;
        mainTask.time++;
    }
}
