/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.RoleType;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.tasks.TMain;

import java.util.Set;

public abstract class MGameStatus {

    public static boolean hasAtLeastOneDifferentCamp(Setup setup) {
        RoleType team = null;
        for (Roles role : setup.getGame().getGameInfo().getMRules().activatedRoles) {
            if (team == null) {
                team = role.getRoleType();
                continue;
            }
            if (team != role.getRoleType())
                return true;
        }
        return false;
    }

    public static void checkWin(Setup setup) {

        GameState state = setup.getGame().getGameInfo().getGameState();

        if (state.equals(GameState.LOADING) || state.equals(GameState.LOBBY_WAITING) || state.equals(GameState.LOBBY_TELEPORTING))
            return;

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
