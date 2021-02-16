/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.commands.*;
import fr.rqndomhax.narutouhc.commands.host.*;
import fr.rqndomhax.narutouhc.commands.inventory.CCancel;
import fr.rqndomhax.narutouhc.commands.inventory.CEnchant;
import fr.rqndomhax.narutouhc.commands.inventory.CInventory;
import fr.rqndomhax.narutouhc.commands.inventory.CSave;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.listeners.*;
import fr.rqndomhax.narutouhc.listeners.role.*;
import fr.rqndomhax.narutouhc.listeners.scenarios.SCutClean;
import fr.rqndomhax.narutouhc.listeners.scenarios.SDrop;
import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;
import fr.rqndomhax.narutouhc.tablecompletes.TabHost;
import fr.rqndomhax.narutouhc.tablecompletes.TabNaruto;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.WorldManager;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Registers {

    private final Setup setup;

    public Registers(Setup setup) {
        this.setup = setup;
    }

    public boolean registerWorlds() {

        ServerPing.currentPing = Pings.WORLD_GENERATING;

        Bukkit.getWorlds().forEach(worlds -> Bukkit.unloadWorld(worlds, true));

        WorldManager.deletePlayerData(new File("."));

        File narutoMap = new File("original/" + Maps.NARUTO_UNIVERSE.name());

        if (!narutoMap.exists()) {
            Bukkit.getLogger().log(Level.SEVERE, Messages.PLUGIN_MAP_NOT_PRESENT);
            return false;
        }

        try {
            FileUtils.deleteDirectory(new File(Maps.NARUTO_UNIVERSE.name()));
            FileUtils.copyDirectory(narutoMap, new File(Maps.NARUTO_UNIVERSE.name()));
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, Messages.PLUGIN_INTERNAL_ERROR);
            return false;
        }

        new WorldCreator(Maps.NARUTO_UNIVERSE.name()).createWorld();

        File noPvp = new File(Maps.NO_PVP.name());
        if (noPvp.exists() && noPvp.isDirectory()) {
            Bukkit.getLogger().log(Level.SEVERE, Messages.PLUGIN_DELETING_WORLD);
            try {
                FileUtils.deleteDirectory(noPvp);
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getLogger().log(Level.SEVERE, Messages.PLUGIN_INTERNAL_ERROR);
                return false;
            }
        }

        WorldCreator wc = new WorldCreator(Maps.NO_PVP.name());
        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);
        wc.createWorld();

        World noPVP = Bukkit.getWorld(Maps.NO_PVP.name());

        noPVP.getWorldBorder().setCenter(0, 0);
        noPVP.getWorldBorder().setSize(20000);

        noPVP.setPVP(false);
        Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()).setPVP(true);

        return true;
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        // SETTINGS
        pm.registerEvents(new EPrefix(), setup.getMain());
        pm.registerEvents(new ECancels(setup), setup.getMain());
        pm.registerEvents(new EListener(setup), setup.getMain());

        pm.registerEvents(new EPlayerActions(setup), setup.getMain());
        pm.registerEvents(new EPlayerLogin(setup), setup.getMain());
        pm.registerEvents(new ECancels(setup), setup.getMain());
        pm.registerEvents(new ELobbyCancel(setup), setup.getMain());

        // SCENARIOS
        pm.registerEvents(new SCutClean(setup), setup.getMain());
        pm.registerEvents(new SDrop(setup), setup.getMain());

        // ROLES
        pm.registerEvents(new RGai(setup), setup.getMain());
        pm.registerEvents(new RFu(setup), setup.getMain());
        pm.registerEvents(new RObito(setup), setup.getMain());
        pm.registerEvents(new RKisame(setup), setup.getMain());
        pm.registerEvents(new RMinato(setup), setup.getMain());
        pm.registerEvents(new RNoFall(setup), setup.getMain());
        pm.registerEvents(new RKillerBee(setup), setup.getMain());
    }

    public void registerCommands() {
        setup.getMain().getCommand("help").setExecutor(new CHelp());
        setup.getMain().getCommand("rules").setExecutor(new CRules(setup));
        setup.getMain().getCommand("inventory").setExecutor(new CInventory(setup));
        setup.getMain().getCommand("tpnaruto").setExecutor(new CTPNaruto(setup));
        setup.getMain().getCommand("revive").setExecutor(new CRevive(setup));
        setup.getMain().getCommand("msg").setExecutor(new CWhisper(setup));

        setup.getMain().getCommand("host").setExecutor(new CHost(setup));
        setup.getMain().getCommand("host").setTabCompleter(new TabHost());

        setup.getMain().getCommand("heal").setExecutor(new CHeal(setup));
        setup.getMain().getCommand("save").setExecutor(new CSave(setup));
        setup.getMain().getCommand("cancel").setExecutor(new CCancel(setup));
        setup.getMain().getCommand("force").setExecutor(new CForce(setup));
        setup.getMain().getCommand("enchant").setExecutor(new CEnchant(setup));

        setup.getMain().getCommand("na").setExecutor(new CNaruto(setup));
        setup.getMain().getCommand("na").setTabCompleter(new TabNaruto(setup));

        setup.getMain().getCommand("admininventory").setExecutor(new CAdminInventory(setup));
    }
}
