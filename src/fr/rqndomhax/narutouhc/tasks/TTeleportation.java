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

public class TTeleportation implements Task {

    private final TMain mainTask;
    int remainingTime = 0;
    int time = 0;

    public TTeleportation(TMain mainTask) {
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameInfo().getMRules().narutoTeleportingDuration;
        MGameActions.teleportPlayers2(mainTask.getSetup());
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

        if (r == 45 ||r == 30 || r == 15 || r == 10 || r <= 5 && r > 0)
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.GOLD + "" + r, "", Instrument.STICKS, true, 0, Note.Tone.F);
        if (r == 0) {
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Au combat !", null, false, 0, null);
            mainTask.getSetup().getGame().getGameInfo().setGameState(GameState.GAME_BORDER);
            MGameBuild.removePlatform(mainTask.getSetup().getGame().getGamePlayers());
        }

        time++;
    }
}
