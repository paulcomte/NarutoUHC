/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.game.Game;
import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;
import fr.rqndomhax.narutouhc.managers.MGameBuild;
import fr.rqndomhax.narutouhc.managers.config.MConfig;
import fr.rqndomhax.narutouhc.tabscores.GameScoreboard;
import fr.rqndomhax.narutouhc.tabscores.TabListManager;
import fr.rqndomhax.narutouhc.tasks.TWait;
import fr.rqndomhax.narutouhc.utils.EasterEggs;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryHandler;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryManager;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryTask;
import fr.rqndomhax.narutouhc.utils.nms.NMSPatcher;
import fr.rqndomhax.narutouhc.utils.tools.FileManager;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Setup {

    private final Main main;
    private Game game;
    
    public Setup(Main main) {
        this.main = main;
        setup();
    }

    private void setup() {

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INIT_STARTED);

        Bukkit.getPluginManager().registerEvents(new ServerPing(this), main);

        Registers registers = new Registers(this);

        new NMSPatcher();

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_CREATING_WORLDS);
        if (!registers.registerWorlds()) {
            main.getPluginLoader().disablePlugin(main);
            return;
        }

        game = new Game().createGame();

        if (game == null) {
            Bukkit.getLogger().log(Level.SEVERE, Messages.CANNOT_INIT);
            Bukkit.getServer().shutdown();
            return;
        }

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INIT_EVENTS);
        registers.registerEvents();

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INIT_COMMANDS);
        registers.registerCommands();

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_LAST_TASKS);

        GameScoreboard.init(this);

        TabListManager.registerTab(main, game);

        EasterEggs.init();

        RInventoryManager rInventoryManager = new RInventoryManager();

        Bukkit.getPluginManager().registerEvents(new RInventoryHandler(main, rInventoryManager), main);

        new RInventoryTask(rInventoryManager).runTaskTimer(main, 0, 1);

        FileManager fileManager = new FileManager(main);

        MConfig.init(fileManager, main.getDataFolder());

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_GENERATING_LOBBY);
        ServerPing.currentPing = Pings.LOBBY_GENERATING;

        MGameBuild.placeLobby(main);

        new TWait(this);
    }

    public Game getGame() {
        return game;
    }

    public Main getMain() {
        return main;
    }

}
