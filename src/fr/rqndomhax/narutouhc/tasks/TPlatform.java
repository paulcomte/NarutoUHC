/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;

public class TPlatform implements Task {

    private final TMain mainTask;
    int remainingTime = 0;

    public TPlatform(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameInfo().getMRules().teleportingDuration;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null || !mainTask.isAlive)
            return;

        if (remainingTime < 0) {
            mainTask.lastTaskFinished = true;
            return;
        }

        if (remainingTime == 45 ||remainingTime == 30 || remainingTime == 15 || remainingTime == 10 || remainingTime <= 5 && remainingTime > 0)
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.GOLD + "" + remainingTime, "", Instrument.STICKS, true, 0, Note.Tone.F);
        if (remainingTime == 0) {
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Bonne chance !", null, false, 0, null);
            mainTask.getSetup().getGame().getGameInfo().setGameState(GameState.GAME_INVINCIBILITY);
            MGameBuild.removePlatform(mainTask.getSetup().getGame().getGamePlayers());
        }
        remainingTime--;
    }
}
