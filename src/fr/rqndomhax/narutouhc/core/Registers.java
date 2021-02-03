/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.commands.*;
import fr.rqndomhax.narutouhc.commands.host.CHost;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.listeners.ECancels;
import fr.rqndomhax.narutouhc.listeners.ELobbyCancel;
import fr.rqndomhax.narutouhc.listeners.EPlayerActions;
import fr.rqndomhax.narutouhc.listeners.EPlayerLogin;
import fr.rqndomhax.narutouhc.listeners.scenarios.SCutClean;
import fr.rqndomhax.narutouhc.listeners.scenarios.SDrop;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import fr.rqndomhax.narutouhc.utils.BiomeSwapper;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.WorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;

public class Registers {

    private final Setup setup;

    public Registers(Setup setup) {
        this.setup = setup;
    }

    public boolean registerWorlds() {

        Bukkit.getWorlds().forEach(worlds -> Bukkit.unloadWorld(worlds, true));

        WorldManager.deletePlayerData(new File("."));

        File narutoMap = new File("original/" + Maps.NARUTO_UNIVERSE.name());

        if (!narutoMap.exists()) {
            System.out.println(Messages.PLUGIN_MAP_NOT_PRESENT);
            return false;
        }

        try {
            FileUtils.deleteDirectory(new File(Maps.NARUTO_UNIVERSE.name()));
            FileUtils.copyDirectory(narutoMap, new File(Maps.NARUTO_UNIVERSE.name()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(Messages.PLUGIN_INTERNAL_ERROR);
            return false;
        }

        new WorldCreator(Maps.NARUTO_UNIVERSE.name()).createWorld();

        File noPvp = new File(Maps.NO_PVP.name());
        if (noPvp.exists() && noPvp.isDirectory()) {
            System.out.println(Messages.PLUGIN_DELETING_WORLD);
            try {
                FileUtils.deleteDirectory(noPvp);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(Messages.PLUGIN_INTERNAL_ERROR);
                return false;
            }
        }

        WorldCreator wc = new WorldCreator(Maps.NO_PVP.name());
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);
        BiomeSwapper.init();
        wc.createWorld();

        World noPVP = Bukkit.getWorld(Maps.NO_PVP.name());

        noPVP.getWorldBorder().setCenter(0, 0);
        noPVP.getWorldBorder().setSize(20000);

        noPVP.setPVP(false);
        Bukkit.getWorld(Maps.NO_PVP.name()).setPVP(false);

        System.out.println(Messages.PLUGIN_GENERATING_LOBBY);
        MGameBuild.placeLobby();

        return true;
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new EPlayerActions(setup), setup.getMain());
        pm.registerEvents(new EPlayerLogin(setup), setup.getMain());
        pm.registerEvents(new ECancels(setup), setup.getMain());

        pm.registerEvents(new SCutClean(setup), setup.getMain());
        pm.registerEvents(new SDrop(setup), setup.getMain());

        pm.registerEvents(new ELobbyCancel(setup), setup.getMain());
    }

    public void registerCommands() {
        setup.getMain().getCommand("help").setExecutor(new CHelp());
        setup.getMain().getCommand("rules").setExecutor(new CRules(setup));
        setup.getMain().getCommand("inventory").setExecutor(new CInventory(setup));
        setup.getMain().getCommand("tpnaruto").setExecutor(new CTPNaruto());
        setup.getMain().getCommand("revive").setExecutor(new CRevive(setup));
        setup.getMain().getCommand("msg").setExecutor(new CWhisper(setup));
        setup.getMain().getCommand("host").setExecutor(new CHost(setup));
        setup.getMain().getCommand("heal").setExecutor(new CHeal(setup));
        setup.getMain().getCommand("save").setExecutor(new CSave(setup));
        setup.getMain().getCommand("cancel").setExecutor(new CCancel(setup));
        setup.getMain().getCommand("force").setExecutor(new CForce(setup));
        setup.getMain().getCommand("enchant").setExecutor(new CEnchant(setup));
    }
}
