/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.commands.CHelp;
import fr.rqndomhax.narutouhc.commands.CTPNaruto;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.listeners.EPlayerActions;
import fr.rqndomhax.narutouhc.listeners.EPlayerLogin;
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

        System.out.println(Messages.PLUGIN_INIT_EVENTS);
        registerEvents();

        System.out.println(Messages.PLUGIN_INIT_COMMANDS);
        registerCommands();

        System.out.println(Messages.CREATING_WORLD);
        if (!registerWorlds()) {
            main.getPluginLoader().disablePlugin(main);
            return;
        }

        System.out.println(Messages.PLUGIN_LAST_TASKS);
        game = new MGame(this);

        System.out.println(Messages.PLUGIN_INITIALIZED);
        game.getGameInfo().getmBorder().resizeBorder(this);
    }

    private boolean registerWorlds() {
        new WorldCreator(Maps.NARUTO_UNIVERSE.name()).createWorld();
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
    }

    private void registerCommands() {
        this.main.getCommand("help").setExecutor(new CHelp());
        this.main.getCommand("tpnaruto").setExecutor(new CTPNaruto());
    }

    public MGame getGame() {
        return game;
    }

    public Main getMain() {
        return main;
    }
}
