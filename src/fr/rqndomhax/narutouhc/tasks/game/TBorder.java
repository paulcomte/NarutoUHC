/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.game;

import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

public class TBorder implements Task {

    private final TMain mainTask;
    public int remainingTime;

    public TBorder(TMain mainTask) {
        mainTask.getSetup().getGame().setGameState(GameState.GAME_BORDER);
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameRules().gameBorder.timeBeforeResize;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;

        if (remainingTime == 45*60 || remainingTime == 30*60 || remainingTime == 15*60 || remainingTime == 10*60 || remainingTime == 5*60 || remainingTime == 60) {
            if (remainingTime == 60)
                Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE
                        .replace("%time%", String.valueOf(remainingTime/60))
                        .replace("secondes", "minute"));
            else
                Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE
                        .replace("%time%", String.valueOf(remainingTime/60))
                        .replace("secondes", "minutes"));
        }

        if (remainingTime == 45 ||remainingTime == 30 || remainingTime == 15 || remainingTime == 10 || remainingTime <= 5 && remainingTime > 0) {
            if (remainingTime == 1)
                Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE
                        .replace("%time%", String.valueOf(remainingTime))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE
                        .replace("%time%", String.valueOf(remainingTime)));
        }

        if (remainingTime == 0) {
            mainTask.getSetup().getGame().getGameRules().gameBorder.resizeBorder();
            Bukkit.broadcastMessage(Messages.WB_BORDER_RESIZING);
            mainTask.lastTaskFinished = true;
        }
        remainingTime--;
        mainTask.time++;
    }
}
