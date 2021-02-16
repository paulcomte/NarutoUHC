/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import fr.rqndomhax.narutouhc.utils.Chrono;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

public class TRestart implements Task {

    private final TMain mainTask;
    private int remainingTime = 30;

    public TRestart(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;

        if (remainingTime >= 1 && remainingTime <= 5 || remainingTime == 10 || remainingTime == 20 || remainingTime == 30)
            Bukkit.broadcastMessage(Messages.PREFIX + "Redémarrage du serveur dans " + Chrono.timeToString(remainingTime));

        if (remainingTime == 0) {
            mainTask.lastTaskFinished = true;
            Bukkit.getServer().reload();
            return;
        }
        remainingTime--;
    }
}
