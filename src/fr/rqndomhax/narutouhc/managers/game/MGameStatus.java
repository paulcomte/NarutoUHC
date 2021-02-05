/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.tasks.game.TMain;

import java.util.Set;

public abstract class MGameStatus {

    public static boolean hasAtLeastOneDifferentCamp(Setup setup) {
        Team team = null;
        for (Roles role : setup.getGame().getGameRules().activatedRoles) {
            if (team == null) {
                team = role.getTeam();
                continue;
            }
            if (team != role.getTeam())
                return true;
        }
        return false;
    }

    public static void checkWin(Setup setup) {

        GameState state = setup.getGame().getGameState();

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

        TMain mainTask = setup.getGame().getMainTask();
        if (mainTask != null && mainTask.hasRoles) {
            Team winners = winningTeam(setup.getGame().getGamePlayers());
            if (winners != null)
                showWin(setup, winners);
        }

    }

    private static void showWin(Setup setup, Team winners) {

        setup.getGame().setGameState(GameState.GAME_FINISHED);
        setup.getGame().removeTask();
    }

    private static Team winningTeam(Set<GamePlayer> players) {
        Team team = null;
        for (GamePlayer gamePlayer : players) {
            if (gamePlayer == null || gamePlayer.role == null || gamePlayer.isDead)
                continue;
            if (team == null) {
                team = gamePlayer.role.getRole().getTeam();
                continue;
            }
            if (team != gamePlayer.role.getRole().getTeam())
                return null;
        }
        if (team == null)
            return null;
        return team;
    }

}
