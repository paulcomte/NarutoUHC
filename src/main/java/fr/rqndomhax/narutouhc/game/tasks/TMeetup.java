/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;

public class TMeetup implements Task {

    private final TMain mainTask;

    public TMeetup(TMain mainTask) {
        mainTask.getSetup().getGame().setGameState(GameState.GAME_MEETUP);
        ServerPing.currentPing = Pings.MEETUP;
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;
        mainTask.time++;
    }
}
