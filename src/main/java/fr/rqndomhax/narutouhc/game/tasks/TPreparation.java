/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

public class TPreparation implements Task {

    private final TMain mainTask;
    public int remainingTime = 0;

    public TPreparation(TMain mainTask) {
        mainTask.getSetup().getGame().setGameState(GameState.GAME_PREPARATION);
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameRules().preparationDuration;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;

        if (remainingTime == 45*60 || remainingTime == 30*60 || remainingTime == 15*60 || remainingTime == 10*60 || remainingTime == 5*60 || remainingTime == 60) {
            if (remainingTime == 60)
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(remainingTime/60))
                        .replace("secondes", "minute"));
            else
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(remainingTime/60))
                        .replace("secondes", "minutes"));
        }

        if (remainingTime == 45 ||remainingTime == 30 || remainingTime == 15 || remainingTime == 10 || remainingTime <= 5 && remainingTime > 0) {
            if (remainingTime == 1)
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(remainingTime))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(remainingTime)));
        }

        if (remainingTime == 0) {
            Bukkit.broadcastMessage(Messages.NARUTO_MAP_TPING);
            mainTask.lastTaskFinished = true;
        }
        remainingTime--;
        mainTask.time++;
    }

}
