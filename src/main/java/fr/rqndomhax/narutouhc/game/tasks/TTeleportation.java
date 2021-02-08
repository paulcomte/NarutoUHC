/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.managers.MGameBuild;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;

public class TTeleportation implements Task {

    private final TMain mainTask;
    int remainingTime;

    public TTeleportation(TMain mainTask) {
        mainTask.getSetup().getGame().setGameState(GameState.GAME_TELEPORTING);
        this.mainTask = mainTask;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameRules().narutoTeleportingDuration;
        GameInfo.currentMap = Maps.NARUTO_UNIVERSE;
        MGameActions.teleportationFinished = false;
        MGameActions.teleportPlayers2(mainTask.getSetup());
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null || !MGameActions.teleportationFinished)
            return;

        if (!mainTask.lobbyRemoved) {
            MGameBuild.removeLobby(mainTask.getSetup().getMain());
            mainTask.lobbyRemoved = true;
        }

        if (!mainTask.hasInventory) {
            MGameActions.giveStartInventory(mainTask.getSetup());
            mainTask.hasInventory = true;
        }

        if (remainingTime == 45 ||remainingTime == 30 || remainingTime == 15 || remainingTime == 10 || remainingTime <= 5 && remainingTime > 0)
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.GOLD + "" + remainingTime, "", Instrument.STICKS, true, 0, Note.Tone.F);
        if (remainingTime == 0) {
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Au combat !", null, false, 0, null);
            MGameBuild.removePlatform(mainTask.getSetup().getGame().getGamePlayers());
            mainTask.getSetup().getGame().setGameState(GameState.GAME_TELEPORTATION_INVINCIBILITY);
            mainTask.lastTaskFinished = true;
        }

        remainingTime--;
    }
}
