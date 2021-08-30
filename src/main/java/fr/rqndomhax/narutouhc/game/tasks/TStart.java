/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.utils.Timers;
import fr.rqndomhax.narutouhc.utils.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class TStart implements Task {

    private final TMain mainTask;
    int remainingTime = 0;

    public TStart(TMain mainTask) {
        this.mainTask = mainTask;
        if (mainTask == null)
            return;
        mainTask.lastTaskFinished = false;
        remainingTime = mainTask.getSetup().getGame().getGameRules().startDuration;
        ServerPing.currentPing = Pings.LOBBY_STARTING;
        loop();
    }

    @Override
    public void loop() {
        if (mainTask == null)
            return;

        for (Player player : Bukkit.getOnlinePlayers())
            player.setLevel(remainingTime);

        switch(remainingTime){
            case 10:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    showTitle(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP,3, 5);
                }
                break;
            case 9:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setExp(0.9f);
                }
                break;
            case 8:
                for (Player player : Bukkit.getOnlinePlayers())
                    player.setExp(0.8f);
                break;
            case 7:
                for (Player player : Bukkit.getOnlinePlayers())
                    player.setExp(0.7f);
                break;
            case 6:
                for (Player player : Bukkit.getOnlinePlayers())
                    player.setExp(0.6f);
                break;
            case 5:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    showTitle(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP,3, 5);
                    player.setExp(0.5f);
                }
                break;
            case 4:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    showTitle(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP,3, 5);
                    player.setExp(0.4f);
                }
                break;
            case 3:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    showTitle(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP,3, 5);
                    player.setExp(0.3f);
                }
                break;
            case 2:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    showTitle(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP,3, 5);
                    player.setExp(0.2f);
                }
                break;
            case 1:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    showTitle(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP,3, 1);
                    player.setExp(0.2f);
                }
                break;
            case 0:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setExp(0.3f);
                    player.setLevel(0);
                }
                startGame();
                break;

        }

        remainingTime--;
    }

    private void updateRules() {
        World naruto = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
        WorldBorder border = naruto.getWorldBorder();
        border.setSize(mainTask.getSetup().getGame().getGameRules().gameBorder.defaultSize);
        border.setCenter(mainTask.getSetup().getGame().getGameRules().gameBorder.center.getX(), mainTask.getSetup().getGame().getGameRules().gameBorder.center.getZ());

        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("doEntityDrops", "true");
            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("doMobLoot", "true");
            world.setGameRuleValue("doMobSpawning", "true");
            world.setGameRuleValue("doTileDrops", "true");
            world.setGameRuleValue("keepInventory", "false");
            world.setGameRuleValue("mobGriefing", "true");
            world.setGameRuleValue("reducedDebugInfo", "false");
            world.setGameRuleValue("naturalRegeneration", String.valueOf(mainTask.getSetup().getGame().getGameRules().naturalRegeneration));
            world.setGameRuleValue("doDaylightCycle", String.valueOf(mainTask.getSetup().getGame().getGameRules().dayCycle.equals(DayCycle.NORMAL)));

            if (mainTask.getSetup().getGame().getGameRules().dayCycle.equals(DayCycle.NIGHT))
                world.setTime(18000);

            if (mainTask.getSetup().getGame().getGameRules().dayCycle.equals(DayCycle.DAY))
                world.setTime(6000);

            if (mainTask.getSetup().getGame().getGameRules().dayCycle.equals(DayCycle.NORMAL))
                world.setTime(0);
        }
    }

    private void updateScenarios() {
        if (mainTask.getSetup().getGame().getGameRules().activatedScenarios.contains(Scenarios.FASTER_EPISODE))
            Timers.EPISODE_LENGTH = 10*60;
        if (mainTask.getSetup().getGame().getGameRules().activatedScenarios.contains(Scenarios.FASTER_TEAM))
            Timers.TEAM_LENGTH = 10*60;
    }

    private void startGame() {
        MGameActions.sendInfos(mainTask.getSetup().getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Téléportation...", Instrument.BASS_DRUM, true, 1, Note.Tone.E);
        updateRules();
        updateScenarios();

        mainTask.roleRemainingTime = mainTask.getSetup().getGame().getGameRules().rolesAnnounce;
        mainTask.lastTaskFinished = true;
    }

    private void showTitle(Player player) {
        new Title(ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage dans " + ChatColor.RED + remainingTime + ChatColor.DARK_AQUA + "s", 0, 30, 0).send(player);
    }
}
