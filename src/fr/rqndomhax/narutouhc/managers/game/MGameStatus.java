/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.RoleType;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.tasks.TMain;

import java.util.Set;

public abstract class MGameStatus {

    public static void checkWin(Setup setup) {

        int remainingPlayers = (int) setup.getGame().getGamePlayers().stream().filter(o -> !o.isDead).count();

        if (remainingPlayers == 0) {
            showWin(setup, null);
            return;
        }

        else if (remainingPlayers == 1) {
            showWin(setup, null);
            return;
        }

        TMain mainTask = setup.getGame().getGameInfo().getMainTask();
        if (mainTask != null && mainTask.hasRoles) {
            RoleType winners = winningTeam(setup.getGame().getGamePlayers());
            if (winners != null)
                showWin(setup, winners);
        }

    }

    private static void showWin(Setup setup, RoleType winners) {

        setup.getGame().getGameInfo().setGameState(GameState.GAME_FINISHED);
        setup.getGame().getGameInfo().removeTask();
    }

    private static RoleType winningTeam(Set<MPlayer> players) {
        RoleType team = null;
        for (MPlayer mPlayer : players) {
            if (mPlayer == null ||mPlayer.role == null || mPlayer.isDead)
                continue;
            if (team == null) {
                team = mPlayer.role.getRole().getRoleType();
                continue;
            }
            if (team != mPlayer.role.getRole().getRoleType())
                return null;
        }
        if (team == null)
            return null;
        return team;
    }

}
