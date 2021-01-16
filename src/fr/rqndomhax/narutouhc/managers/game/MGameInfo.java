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
import fr.rqndomhax.narutouhc.managers.MTime;

public class MGameInfo {

    private final MBorder mBorder;
    private GameState gameState = GameState.LOADING;
    private Maps currentMap = Maps.NO_PVP;
    private MTime mTime;
    private final MRules mRules;

    public MGameInfo(MBorder mBorder, MRules mRules) {
        this.mBorder = mBorder;
        this.mRules = mRules;
    }

    public MBorder getMBorder() {
        return mBorder;
    }

    public MRules getMRules() {
        return mRules;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void startMTime(Setup setup) {
        if (this.mTime != null)
            return;
        this.mTime = new MTime(setup);
    }

    public void removeMTime(Setup setup) {
        mTime.cancel();
        this.mTime = null;
        setup.getGame().getGameInfo().setGameState(GameState.LOBBY_WAITING);
    }

    public Maps getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(Maps currentMap) {
        this.currentMap = currentMap;
    }

    public MTime getMTime() {
        return mTime;
    }
}
