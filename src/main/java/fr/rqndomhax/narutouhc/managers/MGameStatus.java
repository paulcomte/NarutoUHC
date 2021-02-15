/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.listeners.EPlayerActions;
import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.tasks.TMain;
import fr.rqndomhax.narutouhc.tasks.TDeath;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Set;

public abstract class MGameStatus {

    public static Team winners = null;

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

        TMain mainTask = setup.getGame().getMainTask();
        if (mainTask != null && mainTask.hasRoles) {
            winners = winningTeam(setup.getGame().getGamePlayers());
            if (winners != null)
                showWin(setup, winners);
        }

    }

    private static void showWin(Setup setup, Team winners) {
        setup.getGame().setGameState(GameState.GAME_FINISHED);
        for (TDeath death : EPlayerActions.deaths)
            death.onDeath();
        ServerPing.currentPing = Pings.FINISHED;
        Bukkit.broadcastMessage(Messages.SEPARATORS);

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.role != null && (winners == null || gamePlayer.role.getRole().getTeam().equals(winners)))
                continue;

            if (gamePlayer.isDead)
                if (gamePlayer.role != null)
                    Bukkit.broadcastMessage(ChatColor.STRIKETHROUGH + gamePlayer.name + ChatColor.RESET + " - " + ChatColor.BLUE + gamePlayer.role.getRole().getRoleName() + ChatColor.RESET + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
                else
                    Bukkit.broadcastMessage(ChatColor.STRIKETHROUGH + gamePlayer.name + ChatColor.RESET + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
            else
                if (gamePlayer.role != null)
                    Bukkit.broadcastMessage(gamePlayer.name + " - " + ChatColor.BLUE + gamePlayer.role.getRole().getRoleName() + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
                else
                    Bukkit.broadcastMessage(gamePlayer.name + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
        }

        if (winners == null) {
            setup.getGame().removeTask();
            return;
        }

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Victoire du camp " + winners.getTeamName() + " " + ChatColor.BLACK + "-----\n");

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.role == null || !gamePlayer.role.getRole().getTeam().equals(winners))
                continue;

            if (gamePlayer.isDead)
                if (gamePlayer.role != null)
                    Bukkit.broadcastMessage(ChatColor.STRIKETHROUGH + Bukkit.getOfflinePlayer(gamePlayer.uuid).getName() + ChatColor.RESET + " - " + ChatColor.BLUE + gamePlayer.role.getRole().getRoleName() + ChatColor.RESET + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
                else
                    Bukkit.broadcastMessage(ChatColor.STRIKETHROUGH + Bukkit.getOfflinePlayer(gamePlayer.uuid).getName() + ChatColor.RESET + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
            else
            if (gamePlayer.role != null)
                Bukkit.broadcastMessage(Bukkit.getOfflinePlayer(gamePlayer.uuid).getName() + " - " + ChatColor.BLUE + gamePlayer.role.getRole().getRoleName() + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
            else
                Bukkit.broadcastMessage(Bukkit.getOfflinePlayer(gamePlayer.uuid).getName() + " - " + ChatColor.RED + gamePlayer.kills.size() + " kills");
        }

        setup.getGame().removeTask();
        MGamePublicRoles.stopList();
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
