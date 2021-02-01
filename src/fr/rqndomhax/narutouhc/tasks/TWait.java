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
import org.bukkit.scheduler.BukkitRunnable;

public class TWait extends BukkitRunnable {

    private final TMain mainTask;
    int remainingTime = 0;
    int time = 0;

    public TWait(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameInfo().getMRules().teleportingDuration;
        runTaskTimer(mainTask.getSetup().getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (mainTask == null || !mainTask.isAlive) {
            cancel();
            return;
        }

        int r = remainingTime - time;
        if (r < 0) {
            mainTask.lastTaskFinished = true;
            cancel();
            return;
        }

        if (r == 60 ||r == 30 || r == 15 || r == 10 || r <= 5 && r > 0)
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.GOLD + "" + r, "", Instrument.STICKS, true, 0, Note.Tone.F);
        if (r == 0) {
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Bonne chance !", null, false, 0, null);
            mainTask.getSetup().getGame().getGameInfo().setGameState(GameState.GAME_INVINCIBILITY);
            MGameBuild.removePlatform(mainTask.getSetup().getGame().getGamePlayers());
        }
        time++;
    }
}
