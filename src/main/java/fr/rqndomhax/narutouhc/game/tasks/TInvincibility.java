/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

public class TInvincibility implements Task {

    private final TMain mainTask;
    private int remainingTime;

    public TInvincibility(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        if (mainTask.episode == 0)
            mainTask.episode++;
        remainingTime = mainTask.getSetup().getGame().getGameRules().invincibilityDuration;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;

        if (remainingTime == 45 ||remainingTime == 30 || remainingTime == 15 || remainingTime == 10 || remainingTime <= 5 && remainingTime > 0) {
            if (remainingTime == 1)
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(remainingTime))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(remainingTime)));
        }
        if (remainingTime == 0) {
            Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED);
            mainTask.lastTaskFinished = true;
        }
        remainingTime--;
        mainTask.time++;
    }

}
