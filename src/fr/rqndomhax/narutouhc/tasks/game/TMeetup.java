/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.game;

import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;
import fr.rqndomhax.narutouhc.managers.game.GameState;

public class TMeetup implements Task {

    private final TMain mainTask;

    public TMeetup(TMain mainTask) {
        mainTask.getSetup().getGame().setGameState(GameState.GAME_MEETUP);
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        ServerPing.currentPing = Pings.MEETUP;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;
        mainTask.time++;
    }
}
