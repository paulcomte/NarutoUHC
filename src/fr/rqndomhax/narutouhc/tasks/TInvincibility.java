/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

public class TInvincibility implements Task {

    private final TMain mainTask;
    private int remainingTime = 0;
    private int time = 0;

    public TInvincibility(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameInfo().getMRules().invincibilityTime;
        mainTask.episode++;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null || !mainTask.isAlive)
            return;

        int r = remainingTime - time;
        if (r < 0) {
            mainTask.lastTaskFinished = true;
            return;
        }

        if (r == 45 ||r == 30 || r == 15 || r == 10 || r <= 5 && r > 0) {
            if (r == 1)
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(r))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(r)));
        }
        if (r == 0) {
            mainTask.getSetup().getGame().getGameInfo().setGameState(GameState.GAME_PREPARATION);
            Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED);
        }
        time++;
        mainTask.time++;
    }

}
