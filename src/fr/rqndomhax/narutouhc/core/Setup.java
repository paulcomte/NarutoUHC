/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.managers.config.MConfig;
import fr.rqndomhax.narutouhc.managers.game.Game;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.scoreboards.GameScoreboard;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryHandler;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryManager;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryTask;
import fr.rqndomhax.narutouhc.utils.tools.FileManager;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Setup {

    private final Main main;
    private Game game;
    private GameScoreboard gameScoreboard;

    public Setup(Main main) {
        this.main = main;
        setup();
    }

    private void setup() {

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INIT_STARTED);

        Registers registers = new Registers(this);

        if (registers == null) {
            Bukkit.getLogger().log(Level.SEVERE, Messages.CANNOT_INIT);
            main.getPluginLoader().disablePlugin(main);
            return;
        }

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_CREATING_WORLDS);
        if (!registers.registerWorlds()) {
            main.getPluginLoader().disablePlugin(main);
            return;
        }

        game = new Game().createGame();

        if (game == null) {
            Bukkit.getLogger().log(Level.SEVERE, Messages.CANNOT_INIT);
            main.getPluginLoader().disablePlugin(main);
            return;
        }

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INIT_EVENTS);
        registers.registerEvents();

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INIT_COMMANDS);
        registers.registerCommands();

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_LAST_TASKS);

        gameScoreboard = new GameScoreboard(this);

        RInventoryManager rInventoryManager = new RInventoryManager();

        Bukkit.getPluginManager().registerEvents(new RInventoryHandler(main, rInventoryManager), main);

        new RInventoryTask(rInventoryManager).runTaskTimer(main, 0, 1);

        FileManager fileManager = new FileManager(main);

        MConfig.init(fileManager, main.getDataFolder());

        gameScoreboard.runBoard();

        game.setGameState(GameState.LOBBY_WAITING);

        Bukkit.getLogger().log(Level.INFO, Messages.PLUGIN_INITIALIZED);
    }

    public Game getGame() {
        return game;
    }

    public Main getMain() {
        return main;
    }

    public GameScoreboard getGameScoreboard() {
        return gameScoreboard;
    }
}
