/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import org.bukkit.*;

public class TStart implements Task {

    private final TMain mainTask;
    int time = 0;
    int remainingTime = 0;

    public TStart(TMain mainTask) {
        this.mainTask = mainTask;
        if (mainTask == null)
            return;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameInfo().getMRules().startDuration;
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
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage dans " + ChatColor.RED + r + ChatColor.DARK_AQUA + "s", Instrument.STICKS, true, 0, Note.Tone.F);
        if (r == 0) {
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Téléportation...", Instrument.BASS_DRUM, true, 1, Note.Tone.E);
            mainTask.getSetup().getGame().getGameInfo().setGameState(GameState.LOBBY_TELEPORTING);
            MGameActions.teleportPlayers1(mainTask.getSetup());
            MGameBuild.removeLobby();
            WorldBorder border = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()).getWorldBorder();
            border.setSize(mainTask.getSetup().getGame().getGameInfo().getMBorder().defaultSize);
            border.setCenter(mainTask.getSetup().getGame().getGameInfo().getMBorder().center.getX(), mainTask.getSetup().getGame().getGameInfo().getMBorder().center.getZ());
        }
        time++;
    }
}
