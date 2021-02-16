/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.game.tasks.TBorder;
import fr.rqndomhax.narutouhc.game.tasks.TPreparation;
import fr.rqndomhax.narutouhc.managers.MGameStatus;
import fr.rqndomhax.narutouhc.utils.Chrono;
import fr.rqndomhax.narutouhc.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

public abstract class GameScoreboardManager {

    private static int pos = 0; // TODO TO SHOW ROLES IN GAME
    private static int pages = 0;
    private static final ArrayList<GamePlayer> playersList = new ArrayList<>();
    static DecimalFormat format = new DecimalFormat("#");

    public static void updateLobbyBoard(Setup setup, FastBoard board) {
        board.updateTitle(setup.getGame().getGameRules().gameTitle);
        int line = -1;
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");
        if (GameInfo.gameHost != null)
            board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_AQUA + "Hôte: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(GameInfo.gameHost).getName());
        else
            board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_AQUA + "Hôte: " + ChatColor.WHITE + "Aucun");
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");
        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.GREEN + "Joueurs: " + ChatColor.WHITE + setup.getGame().getGamePlayers().size() + "/" + setup.getGame().getGameRules().activatedRoles.size());
    }

    private static void updatePreparationInfos(Setup setup, FastBoard board, GameRules rules) {
        int line = -1;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(board.getPlayer().getUniqueId());

        board.updateTitle(rules.gameTitle);
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");

        if (gamePlayer != null) {
            if (setup.getGame().getGameRules().rolesAnnounce - setup.getGame().getMainTask().time > 0 && gamePlayer.role == null)
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + Chrono.timeToDigitalString(setup.getGame().getMainTask().roleRemainingTime - setup.getGame().getMainTask().time));
            else {
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + gamePlayer.role.getRole().getRoleName());
            }

            board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");
        }


        if (!(setup.getGame().getMainTask().task instanceof TPreparation))
            board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.BLUE + "Téléportation: " + ChatColor.WHITE + Chrono.timeToDigitalString(setup.getGame().getGameRules().preparationDuration));
        else {
            TPreparation preparation = (TPreparation) setup.getGame().getMainTask().task;
            board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.BLUE + "Téléportation: " + ChatColor.WHITE + Chrono.timeToDigitalString(preparation.remainingTime));
        }

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");

        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.GREEN + "Joueurs: " + ChatColor.WHITE + setup.getGame().getGamePlayers().stream().filter(p -> !p.isDead).count() + "/" + setup.getGame().getGameRules().activatedRoles.size());

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");

        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_PURPLE + "Temps: " + ChatColor.WHITE + Chrono.timeToDigitalString(setup.getGame().getMainTask().time));

        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.LIGHT_PURPLE + "Épisode: " + ChatColor.WHITE + setup.getGame().getMainTask().episode);
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

        updatePreparationInfos(setup, board, rules);

    }

    public static void updateNarutoBoard(Setup setup, FastBoard board) {
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(board.getPlayer().getUniqueId());
        GameRules rules = setup.getGame().getGameRules();

        int line = -1;

        board.updateTitle(rules.gameTitle);
        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");

        if (gamePlayer != null) {
            if (setup.getGame().getGameRules().rolesAnnounce - setup.getGame().getMainTask().time > 0 && gamePlayer.role == null)
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + Chrono.timeToDigitalString(setup.getGame().getMainTask().roleRemainingTime - setup.getGame().getMainTask().time));
            else {
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_BLUE + "Rôle: " + ChatColor.WHITE + gamePlayer.role.getRole().getRoleName());
                pos++;
            }

            board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");
            board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.RED + "Kills: " + ChatColor.WHITE + gamePlayer.kills.size());
            board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");
        }

        if (!(setup.getGame().getMainTask().task instanceof TBorder))
            board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_GREEN + "Bordure: " + ChatColor.WHITE + format.format(Bukkit.getWorld(GameInfo.currentMap.name()).getWorldBorder().getSize()));
        else {
            TBorder border = (TBorder) setup.getGame().getMainTask().task;
            board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.AQUA + "Bordure: " + ChatColor.WHITE + Chrono.timeToDigitalString(border.remainingTime));
        }

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");

        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.GREEN + "Joueurs: " + ChatColor.WHITE + setup.getGame().getGamePlayers().stream().filter(p -> !p.isDead).count() + "/" + setup.getGame().getGameRules().activatedRoles.size());

        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_AQUA + "Groupes: " + ChatColor.WHITE + getGroupSize(setup));

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");

        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.DARK_PURPLE + "Temps: " + ChatColor.WHITE + Chrono.timeToDigitalString(setup.getGame().getMainTask().time));

        board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.LIGHT_PURPLE + "Épisode: " + ChatColor.WHITE + setup.getGame().getMainTask().episode);
    }

    public static void updateGameFinishedBoard(Setup setup, FastBoard board) {

        board.updateTitle(setup.getGame().getGameRules().gameTitle);
        for (int i = 0; i < board.size(); board.removeLine(i), i++);

        int line = -1;

        board.updateLine(++line, ChatColor.BLACK + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                    ");

        switch (MGameStatus.winners) {
            case SHINOBI:
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.YELLOW + "Victoire " + ChatColor.YELLOW + ChatColor.BOLD + " Shinobi");
                break;
            case AKATSUKI:
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.YELLOW + "Victoire " + ChatColor.YELLOW + ChatColor.BOLD + "Akatsuki");
                break;
            case OROCHIMARU:
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.YELLOW + "Victoire " + ChatColor.YELLOW + ChatColor.BOLD + "Orochimaru");
                break;
            case DANZO:
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.YELLOW + "Victoire " + ChatColor.DARK_RED + ChatColor.BOLD + "Danzo");
                break;
            case MADARA:
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.YELLOW + "Victoire " + ChatColor.DARK_RED + ChatColor.BOLD + "Madara");
                break;
            case SASUKE:
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.YELLOW + "Victoire " + ChatColor.DARK_RED + ChatColor.BOLD + "Sasuke");
                break;
            default:
                board.updateLine(++line, ChatColor.GOLD + "⦿ " + ChatColor.YELLOW + "Victoire " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Personne");
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
