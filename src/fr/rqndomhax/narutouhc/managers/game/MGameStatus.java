/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;

public abstract class MGameStatus {

    public static void checkWin(Setup setup) {

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_PREPARATION))
            if (setup.getGame().getGamePlayers().stream().filter(o -> !o.isDead).count() == 1)
                showWin(setup);
    }

    private static void showWin(Setup setup) {
        setup.getGame().getGameInfo().setGameState(GameState.GAME_FINISHED);
        setup.getGame().getGameInfo().removeTask();
    }

}
