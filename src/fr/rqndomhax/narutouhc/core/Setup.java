/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.commands.CHelp;
import fr.rqndomhax.narutouhc.commands.CRevive;
import fr.rqndomhax.narutouhc.commands.CTPNaruto;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.listeners.ECancels;
import fr.rqndomhax.narutouhc.listeners.EPlayerActions;
import fr.rqndomhax.narutouhc.listeners.EPlayerLogin;
import fr.rqndomhax.narutouhc.listeners.EScenarios;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGame;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;

public class Setup {

    private final Main main;
    private MGame game;

    public Setup(Main main) {
        this.main = main;
        setup();
    }

    private void setup() {

        System.out.println(Messages.PLUGIN_INIT_STARTED);
        game = new MGame(this);

        System.out.println(Messages.PLUGIN_INIT_EVENTS);
        registerEvents();

        System.out.println(Messages.PLUGIN_INIT_COMMANDS);
        registerCommands();

        System.out.println(Messages.PLUGIN_CREATING_WORLDS);
        if (!registerWorlds()) {
            main.getPluginLoader().disablePlugin(main);
            return;
        }

        System.out.println(Messages.PLUGIN_LAST_TASKS);
        game.getGameInfo().setGameState(GameState.LOBBY_WAITING);
        System.out.println(Messages.PLUGIN_INITIALIZED);
    }

    private boolean registerWorlds() {

        Bukkit.getWorlds().forEach(worlds -> Bukkit.unloadWorld(worlds, true));

        if (!new File(Maps.NARUTO_UNIVERSE.name()).exists()) {
            System.out.println(Messages.PLUGIN_MAP_NOT_PRESENT);
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
        wc.type(WorldType.LARGE_BIOMES);
        wc.createWorld();
        return true;
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new EPlayerActions(this), main);
        pm.registerEvents(new EPlayerLogin(this), main);
        pm.registerEvents(new ECancels(this), main);
        pm.registerEvents(new EScenarios(), main);
    }

    private void registerCommands() {
        this.main.getCommand("help").setExecutor(new CHelp());
        this.main.getCommand("tpnaruto").setExecutor(new CTPNaruto());
        this.main.getCommand("revive").setExecutor(new CRevive(this));
    }

    public MGame getGame() {
        return game;
    }

    public Main getMain() {
        return main;
    }
}
