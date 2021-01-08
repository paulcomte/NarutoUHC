/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.commands.CHelp;
import fr.rqndomhax.narutouhc.listeners.EPlayerActions;
import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MGameInfo;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Setup {

    private final Main main;
    private MGameInfo GameInfo;

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

        System.out.println(Messages.PLUGIN_LAST_TASKS);
        GameInfo = new MGameInfo(new MBorder(), new MRules());
        System.out.println(Messages.PLUGIN_INITIALIZED);
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new EPlayerActions(this), main);
    }

    private void registerCommands() {
        this.main.getCommand("help").setExecutor(new CHelp());
    }

    public MGameInfo getGameInfo() {
        return GameInfo;
    }
}
