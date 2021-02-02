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

public class TPreparation implements Task {

    private final TMain mainTask;
    int remainingTime = 0;
    int time = 0;

    public TPreparation(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameInfo().getMRules().preparationTime;
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

        if (r == 45*60 || r == 30*60 || r == 15*60 || r == 10*60 || r == 5*60 || r == 60) {
            if (r == 60)
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(r/60))
                        .replace("secondes", "minute"));
            else
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(r/60))
                        .replace("secondes", "minutes"));
        }

        if (r == 45 ||r == 30 || r == 15 || r == 10 || r <= 5 && r > 0) {
            if (r == 1)
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(r))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP
                        .replace("%time%", String.valueOf(r)));
        }

        if (r == 0) {
            mainTask.getSetup().getGame().getGameInfo().setGameState(GameState.GAME_TELEPORTING);
            Bukkit.broadcastMessage(Messages.NARUTO_MAP_TPING);
        }
        time++;
        mainTask.time++;
    }

}
