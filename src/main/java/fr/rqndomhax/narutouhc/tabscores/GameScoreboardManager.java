/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.tasks.TPreparation;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.game.tasks.TBorder;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.managers.MGameStatus;
import fr.rqndomhax.narutouhc.role.GameRole;
import fr.rqndomhax.narutouhc.role.Role;
import fr.rqndomhax.narutouhc.utils.Chrono;
import fr.rqndomhax.narutouhc.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.management.relation.RoleInfo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class GameScoreboardManager {

    private static int pos = 0; // TODO TO SHOW ROLES IN GAME
    private static int pages = 0;
    private static final ArrayList<GamePlayer> playersList = new ArrayList<>();
    static DecimalFormat format = new DecimalFormat("#");

    public static void updateLobbyBoard(Setup setup, FastBoard board) {
        board.updateTitle(setup.getGame().getGameRules().gameTitle);
        int line = -1;
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");
        if (GameInfo.gameHost != null)
            board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_AQUA + "Hôte: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(GameInfo.gameHost).getName());
        else
            board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_AQUA + "Hôte: " + ChatColor.WHITE + "Aucun");
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");
        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.GREEN + "Joueurs: " + ChatColor.WHITE + setup.getGame().getGamePlayers().size() + "/" + setup.getGame().getGameRules().activatedRoles.size());
    }

    private static void updatePreparationInfos(Setup setup, FastBoard board, GameRules rules) {
        int line = -1;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(board.getPlayer().getUniqueId());

        board.updateTitle(rules.gameTitle);
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");

        if (gamePlayer != null) {
            if (setup.getGame().getGameRules().rolesAnnounce - setup.getGame().getMainTask().time > 0)
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + Chrono.timeToString(setup.getGame().getMainTask().roleRemainingTime - setup.getGame().getMainTask().time));
            else {
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + gamePlayer.role.getRole().getRoleName());
                pos++;
            }

            board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");
        }
        TPreparation preparation = (TPreparation) setup.getGame().getMainTask().task;

        if (preparation == null)
            board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.BLUE + "Téléportation: " + ChatColor.WHITE + Chrono.timeToString(setup.getGame().getGameRules().preparationDuration));
        else
            board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.BLUE + "Téléportation: " + ChatColor.WHITE + Chrono.timeToString(preparation.remainingTime));

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");

        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.GREEN + "Joueurs: " + ChatColor.WHITE + setup.getGame().getGamePlayers().stream().filter(p -> !p.isDead).count() + "/" + setup.getGame().getGameRules().activatedRoles.size());

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");

        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_PURPLE + "Temps: " + ChatColor.WHITE + Chrono.timeToString(setup.getGame().getMainTask().time));

        board.updateLine(++line, "");

        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.LIGHT_PURPLE + "Épisode: " + ChatColor.WHITE + setup.getGame().getMainTask().episode);
    }

    private String updateArrow(Player player, Location target) {
        Location location = player.getLocation();
        location.setY(target.getY());
        Vector dirToMiddle = target.toVector().subtract(player.getEyeLocation().toVector()).normalize();
        Integer distance = (int)Math.round(target.distance(location));
        Vector playerDirection = player.getEyeLocation().getDirection();
        double angle = (double)dirToMiddle.angle(playerDirection);
        double det = dirToMiddle.getX() * playerDirection.getZ() - dirToMiddle.getZ() * playerDirection.getX();
        angle *= Math.signum(det);
        String arrow;
        if (angle > -0.39269908169872414D && angle < 0.39269908169872414D) {
            arrow = "⬆";
        } else if (angle > -1.1780972450961724D && angle < -0.39269908169872414D) {
            arrow = "⬈";
        } else if (angle < 1.1780972450961724D && angle > 0.39269908169872414D) {
            arrow = "⬉";
        } else if (angle > 1.1780972450961724D && angle < 1.9634954084936207D) {
            arrow = "←";
        } else if (angle < -1.1780972450961724D && angle > -1.9634954084936207D) {
            arrow = "➡";
        } else if (angle < -1.9634954084936207D && angle > -2.748893571891069D) {
            arrow = "⬊";
        } else if (angle > 1.9634954084936207D && angle < 2.748893571891069D) {
            arrow = "⬋";
        } else {
            arrow = "⬇";
        }

        return distance + " §l" + arrow;
    }

    private static void initPages(Setup setup) {
        int scoreboardSize = 0;

        Set<GamePlayer> players = setup.getGame().getGamePlayers();

        if (players.size() != 0)
            pages++;

        for (GamePlayer gamePlayer : players) {
            playersList.add(gamePlayer);

            if (scoreboardSize == 11)
                pages++;

            scoreboardSize++;
        }
    }

    public static void updatePreparationBoard(Setup setup, FastBoard board) {
        GameRules rules = setup.getGame().getGameRules();

        if (pos < 16) {
            updatePreparationInfos(setup, board, rules);
            return;
        }

        if (pages == 0)
            initPages(setup);

    }

    public static void updateNarutoBoard(Setup setup, FastBoard board) {
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(board.getPlayer().getUniqueId());
        GameRules rules = setup.getGame().getGameRules();

        int line = -1;

        board.updateTitle(rules.gameTitle);
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");

        if (gamePlayer != null) {
            if (setup.getGame().getGameRules().rolesAnnounce - setup.getGame().getMainTask().time > 0)
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + Chrono.timeToString(setup.getGame().getMainTask().roleRemainingTime - setup.getGame().getMainTask().time));
            else {
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + gamePlayer.role.getRole().getRoleName());
                pos++;
            }

            board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");
            board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.RED + "Kills: " + ChatColor.WHITE + gamePlayer.kills.size());
            board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");
        }

        TBorder border = (TBorder) setup.getGame().getMainTask().task;

        if (border == null)
            board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_GREEN + "Bordure: " + ChatColor.WHITE + Bukkit.getWorld(GameInfo.currentMap.name()).getWorldBorder().getSize());
        else
            board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.AQUA + "Bordure: " + ChatColor.WHITE + Chrono.timeToString(border.remainingTime));

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");

        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.GREEN + "Joueurs: " + ChatColor.WHITE + setup.getGame().getGamePlayers().stream().filter(p -> !p.isDead).count() + "/" + setup.getGame().getGameRules().activatedRoles.size());

        board.updateLine(++line, "");

        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_AQUA + "Groupes: " + ChatColor.WHITE + getGroupSize(setup));

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");

        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.DARK_PURPLE + "Temps: " + ChatColor.WHITE + Chrono.timeToString(setup.getGame().getMainTask().time));

        board.updateLine(++line, "");

        board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.LIGHT_PURPLE + "Épisode: " + ChatColor.WHITE + setup.getGame().getMainTask().episode);
    }

    public static void updateGameFinishedBoard(Setup setup, FastBoard board) {

        board.updateTitle(setup.getGame().getGameRules().gameTitle);

        int line = -1;

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "               ");

        switch (MGameStatus.winners) {
            case SHINOBI:
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.YELLOW + "Victoire de " + ChatColor.YELLOW + ChatColor.BOLD + "l'alliance Shinobi");
                break;
            case AKATSUKI:
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.YELLOW + "Victoire de " + ChatColor.YELLOW + ChatColor.BOLD + "l'Akatsuki");
                break;
            case OROCHIMARU:
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.YELLOW + "Victoire du " + ChatColor.YELLOW + ChatColor.BOLD + "camp Orochimaru");
                break;
            case DANZO:
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.YELLOW + "Victoire de " + ChatColor.DARK_RED + ChatColor.BOLD + "Danzo");
                break;
            case MADARA:
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.YELLOW + "Victoire de " + ChatColor.DARK_RED + ChatColor.BOLD + "Madara");
                break;
            case SASUKE:
                board.updateLine(++line, ChatColor.YELLOW + "⦿ " + ChatColor.YELLOW + "Victoire de " + ChatColor.DARK_RED + ChatColor.BOLD + "Sasuke");
                break;
        }
    }

    private static String getGroupSize(Setup setup) {
        long size = setup.getGame().getGamePlayers().stream().filter(p -> !p.isDead).count();

        if (size >= 20)
            return " 5";
        if (size >= 13)
            return " 4";
        if (size >= 3)
            return " 3";
        return " 2";
    }

}
