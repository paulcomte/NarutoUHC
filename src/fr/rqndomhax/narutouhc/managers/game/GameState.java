/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

public enum GameState {
    LOADING,
    LOBBY_WAITING,
    LOBBY_TELEPORTING,
    GAME_INVINCIBILITY,
    GAME_PREPARATION,
    GAME_TELEPORTING,
    GAME_PVP,
    GAME_FINISHED;

    public GameState nextGameState() {
        GameState last = null;
        for (GameState gameState : GameState.values()) {
            if (last == this)
                return gameState;
            last = gameState;
        }
        return LOADING;
    }
}
