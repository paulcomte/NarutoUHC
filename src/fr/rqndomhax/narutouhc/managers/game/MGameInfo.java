/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.managers.config.HostConfig;
import fr.rqndomhax.narutouhc.managers.config.MConfig;
import fr.rqndomhax.narutouhc.tasks.TMain;

public class MGameInfo {

    private GameState gameState = GameState.LOADING;
    private TMain mainTask;
    private HostConfig currentConfig;

    public MGameInfo(HostConfig currentConfig) {
        this.currentConfig = currentConfig;
    }

    public HostConfig getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(HostConfig currentConfig) {
        this.currentConfig = currentConfig;
    }

    public MRules getMRules() {
        if (currentConfig == null)
            return null;
        return currentConfig.getRules();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void startTask(Setup setup) {
        if (this.mainTask != null)
            return;
        this.mainTask = new TMain(setup);
    }

    public void removeTask() {
        if (this.mainTask == null)
            return;
        mainTask.isAlive = false;
        mainTask.cancel();
        this.mainTask = null;
    }

    public TMain getMainTask() {
        return mainTask;
    }
}
