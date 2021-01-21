/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGame;
import fr.rqndomhax.narutouhc.managers.role.MRole;
import fr.rqndomhax.narutouhc.scoreboards.GameScoreboard;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryHandler;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryManager;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryTask;
import org.bukkit.Bukkit;

public class Setup {

    private final Main main;
    private Registers registers;
    private RInventoryManager rInventoryManager;
    private MGame game;
    private MRole role;
    private GameScoreboard gameScoreboard;

    public Setup(Main main) {
        this.main = main;
        setup();
    }

    private void setup() {

        System.out.println(Messages.PLUGIN_INIT_STARTED);
        game = new MGame(this);
        role = new MRole(this);
        registers = new Registers(this);

        System.out.println(Messages.PLUGIN_INIT_EVENTS);
        registers.registerEvents();

        System.out.println(Messages.PLUGIN_INIT_COMMANDS);
        registers.registerCommands();

        System.out.println(Messages.PLUGIN_CREATING_WORLDS);
        if (!registers.registerWorlds()) {
            main.getPluginLoader().disablePlugin(main);
            return;
        }

        System.out.println(Messages.PLUGIN_LAST_TASKS);
        gameScoreboard = new GameScoreboard(this);
        rInventoryManager = new RInventoryManager();
        Bukkit.getPluginManager().registerEvents(new RInventoryHandler(main, rInventoryManager), main);
        new RInventoryTask(rInventoryManager).runTaskTimer(main, 0, 1);
        gameScoreboard.runBoard();
        game.getGameInfo().setGameState(GameState.LOBBY_WAITING);
        System.out.println(Messages.PLUGIN_INITIALIZED);
    }

    public MGame getGame() {
        return game;
    }

    public Main getMain() {
        return main;
    }

    public MRole getRole() {
        return role;
    }

    public GameScoreboard getGameScoreboard() {
        return gameScoreboard;
    }
}
