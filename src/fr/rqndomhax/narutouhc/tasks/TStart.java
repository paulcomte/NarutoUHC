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
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import org.bukkit.*;

public class TStart implements Task {

    private final TMain mainTask;
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

        if (remainingTime < 0) {
            mainTask.lastTaskFinished = true;
            return;
        }

        if (remainingTime == 45 ||remainingTime == 30 || remainingTime == 15 || remainingTime == 10 || remainingTime <= 5 && remainingTime > 0)
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage dans " + ChatColor.RED + remainingTime + ChatColor.DARK_AQUA + "s", Instrument.STICKS, true, 0, Note.Tone.F);
        if (remainingTime == 0) {
            MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Téléportation...", Instrument.BASS_DRUM, true, 1, Note.Tone.E);
            mainTask.getSetup().getGame().getGameInfo().setGameState(GameState.LOBBY_TELEPORTING);
            MGameActions.teleportPlayers1(mainTask.getSetup());
            MGameBuild.removeLobby();
            World naruto = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
            World nopvp = Bukkit.getWorld(Maps.NO_PVP.name());
            WorldBorder border = naruto.getWorldBorder();
            border.setSize(mainTask.getSetup().getGame().getGameInfo().getMBorder().defaultSize);
            border.setCenter(mainTask.getSetup().getGame().getGameInfo().getMBorder().center.getX(), mainTask.getSetup().getGame().getGameInfo().getMBorder().center.getZ());

            naruto.setGameRuleValue("doEntityDrops", "true");
            nopvp.setGameRuleValue("doEntityDrops", "true");

            naruto.setGameRuleValue("doFireTick", "true");
            nopvp.setGameRuleValue("doFireTick", "true");

            naruto.setGameRuleValue("doMobLoot", "true");
            nopvp.setGameRuleValue("doMobLoot", "true");

            naruto.setGameRuleValue("doMobSpawning", "true");
            nopvp.setGameRuleValue("doMobSpawning", "true");

            naruto.setGameRuleValue("doTileDrops", "true");
            nopvp.setGameRuleValue("doTileDrops", "true");

            naruto.setGameRuleValue("keepInventory", "false");
            nopvp.setGameRuleValue("keepInventory", "false");

            naruto.setGameRuleValue("mobGriefing", "true");
            nopvp.setGameRuleValue("mobGriefing", "true");

            naruto.setGameRuleValue("reducedDebugInfo", "false");
            nopvp.setGameRuleValue("reducedDebugInfo", "false");

            naruto.setGameRuleValue("naturalRegeneration", String.valueOf(mainTask.getSetup().getGame().getGameInfo().getMRules().naturalRegeneration));
            nopvp.setGameRuleValue("naturalRegeneration", String.valueOf(mainTask.getSetup().getGame().getGameInfo().getMRules().naturalRegeneration));

            naruto.setGameRuleValue("doDaylightCycle", String.valueOf(mainTask.getSetup().getGame().getGameInfo().getMRules().dayCycle.equals(DayCycle.NORMAL)));
            nopvp.setGameRuleValue("doDaylightCycle", String.valueOf(mainTask.getSetup().getGame().getGameInfo().getMRules().dayCycle.equals(DayCycle.NORMAL)));

            if (mainTask.getSetup().getGame().getGameInfo().getMRules().dayCycle.equals(DayCycle.NIGHT)) {
                nopvp.setTime(18000);
                naruto.setTime(18000);
            }
            if (mainTask.getSetup().getGame().getGameInfo().getMRules().dayCycle.equals(DayCycle.DAY)) {
                nopvp.setTime(6000);
                naruto.setTime(6000);
            }
            if (mainTask.getSetup().getGame().getGameInfo().getMRules().dayCycle.equals(DayCycle.NORMAL)) {
                nopvp.setTime(0);
                naruto.setTime(0);
            }

            mainTask.getSetup().getGame().getGameInfo().getMRules().currentMap = Maps.NO_PVP;
        }
        remainingTime--;
    }
}
