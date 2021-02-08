/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.game.tasks.TBorder;
import fr.rqndomhax.narutouhc.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.text.DecimalFormat;

public abstract class GameScoreboardManager {

    private static final ChatColor colorPrefix = ChatColor.DARK_BLUE;
    private int scoreboardPosition = 0; // TODO TO SHOW ROLES IN GAME
    static DecimalFormat format = new DecimalFormat("#");

    public static void updateLobbyBoard(Setup setup, FastBoard board) {
        board.updateTitle(setup.getGame().getGameRules().gameTitle);
        board.updateLine(0, "");
        if (GameInfo.gameHost != null)
            board.updateLine(1, colorPrefix + "» Host: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(GameInfo.gameHost).getName());
        else
            board.updateLine(1, colorPrefix + "» Host: " + ChatColor.WHITE + "Aucun");
        board.updateLine(2, "");
        board.updateLine(3, colorPrefix + "» Joueurs: " + ChatColor.WHITE + setup.getGame().getGamePlayers().size() + "/" + setup.getGame().getGameRules().activatedRoles.size());
    }

    public static void updatePreparationBoard(Setup setup, FastBoard board) {
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(board.getPlayer().getUniqueId());
        GameRules rules = setup.getGame().getGameRules();

        board.updateTitle(rules.gameTitle);
        board.updateLine(0, "");
        if (GameInfo.gameHost != null)
            board.updateLine(1, colorPrefix + "» Host: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(GameInfo.gameHost).getName());
        else
            board.updateLine(1, colorPrefix + "» Host: " + ChatColor.WHITE + "Aucun");

        board.updateLine(2, "");

        if (gamePlayer != null) {
            if (gamePlayer.role == null)
                if (setup.getGame().getGameRules().rolesAnnounce - setup.getGame().getMainTask().time > 0)
                    board.updateLine(3, colorPrefix + "» Rôle: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getMainTask().roleRemainingTime - setup.getGame().getMainTask().time));
                else
                    board.updateLine(3, colorPrefix + "» Rôle: " + ChatColor.WHITE + "?");
            else
                board.updateLine(3, colorPrefix + "» Rôle: " + ChatColor.WHITE + gamePlayer.role.getRole().name().toLowerCase());
            board.updateLine(4, "");
            board.updateLine(5, colorPrefix + "» Temps: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getMainTask().time));
            board.updateLine(6, "");
            board.updateLine(7, colorPrefix + "» Episode: " + ChatColor.WHITE + setup.getGame().getMainTask().episode);
            return;
        }
        board.updateLine(3, colorPrefix + "» Temps: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getMainTask().time));
        board.updateLine(4, "");
        board.updateLine(5, colorPrefix + "» Episode: " + ChatColor.WHITE + setup.getGame().getMainTask().episode);
    }

    public static void updateNarutoBoard(Setup setup, FastBoard board) {
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(board.getPlayer().getUniqueId());
        GameRules rules = setup.getGame().getGameRules();

        board.updateTitle(rules.gameTitle);
        board.updateLine(0, "");
        if (GameInfo.gameHost != null)
            board.updateLine(1, colorPrefix + "» Host: " + ChatColor.WHITE + Bukkit.getOfflinePlayer(GameInfo.gameHost).getName());
        else
            board.updateLine(1, colorPrefix + "» Host: " + ChatColor.WHITE + "Aucun");

        board.updateLine(2, "");
        board.updateLine(3, colorPrefix + "» Groupes: " + ChatColor.WHITE + rules.maxGroupSize);
        board.updateLine(4, "");

        World world = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
        if (gamePlayer != null) {
            if (gamePlayer.role == null)
                if (setup.getGame().getGameRules().rolesAnnounce - setup.getGame().getMainTask().time > 0)
                    board.updateLine(5, colorPrefix + "» Rôle: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getMainTask().roleRemainingTime - setup.getGame().getMainTask().time));
                else
                    board.updateLine(5, colorPrefix + "» Rôle: " + ChatColor.WHITE + "?");
            else
                board.updateLine(5, colorPrefix + "» Rôle: " + ChatColor.WHITE + gamePlayer.role.getRole().name().toLowerCase());board.updateLine(6, "");
            board.updateLine(6, "");
            board.updateLine(7, colorPrefix + "» Kills: " + ChatColor.WHITE + gamePlayer.kills.size());
            board.updateLine(8, "");
            board.updateLine(9, colorPrefix + "» Temps: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getMainTask().time));
            board.updateLine(10, "");
            if (world.getWorldBorder().getSize() == setup.getGame().getGameRules().gameBorder.defaultSize)
                if (setup.getGame().getMainTask().task.getClass() == TBorder.class)
                    board.updateLine(11, colorPrefix + "» Bordure: " + ChatColor.WHITE + getFormattedTime(((TBorder) setup.getGame().getMainTask().task).remainingTime));
                else
                    board.updateLine(11, colorPrefix + "» Bordure: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getGameRules().gameBorder.timeBeforeResize));
            else
                board.updateLine(11, colorPrefix + "» Bordure: " + ChatColor.WHITE + format.format(Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()).getWorldBorder().getSize()));
        }
        else {
            board.updateLine(5, colorPrefix + "» Temps: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getMainTask().time));
            board.updateLine(6, "");
            if (world.getWorldBorder().getSize() == setup.getGame().getGameRules().gameBorder.defaultSize)
                board.updateLine(7, colorPrefix + "» Bordure: " + ChatColor.WHITE + getFormattedTime(setup.getGame().getGameRules().gameBorder.timeBeforeResize));
            else
                board.updateLine(7, colorPrefix + "» Bordure: " + ChatColor.WHITE + format.format(Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()).getWorldBorder().getSize()));
            board.updateLine(8, "");
            board.updateLine(5, colorPrefix + "» Episode: " + ChatColor.WHITE + setup.getGame().getMainTask().episode);
        }
    }

    private static String getFormattedTime(int time) {
        int hours = 0;
        int mins = 0;
        StringBuilder sb = new StringBuilder();

        while (time - 60*60 >= 0) {
            hours++;
            time -= 60*60;
        }

        while (time - 60 >= 0) {
            mins++;
            time -= 60;
        }

        if (hours != 0) {
            if (hours < 10)
                sb.append("0");
            sb.append(hours);
            sb.append(":");
        }

        if (mins < 10)
            sb.append("0");
        sb.append(mins);
        sb.append(":");
        if (time < 10)
            sb.append("0");
        sb.append(time);
        return sb.toString();
    }

}
