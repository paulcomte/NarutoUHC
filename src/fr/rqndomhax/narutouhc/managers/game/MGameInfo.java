/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MRules;

public class MGameInfo {

    private final MBorder mBorder;
    private GameState gameState;
    private final MRules mRules;

    public MGameInfo(MBorder mBorder, MRules mRules) {
        this.mBorder = mBorder;
        this.mRules = mRules;
        gameState = GameState.LOBBY_WAITING;
    }

    public MBorder getmBorder() {
        return mBorder;
    }

    public MRules getmRules() {
        return mRules;
    }

    public GameState getGameState() {
        return gameState;
    }
}