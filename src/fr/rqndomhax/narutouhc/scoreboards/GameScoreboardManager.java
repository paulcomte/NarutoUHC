/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.scoreboards;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public abstract class GameScoreboardManager {

    private static final ChatColor colorPrefix = ChatColor.DARK_BLUE;

    public static void updateLobbyBoard(Setup setup, FastBoard board) {
        board.updateTitle(setup.getGame().getGameInfo().getMRules().gameTitle);
        board.updateLine(0, "");
        if (setup.getGame().getGameInfo().getMRules().gameHost != null)
            board.updateLine(1, colorPrefix + "➤ Host : " + ChatColor.WHITE + Bukkit.getOfflinePlayer(setup.getGame().getGameInfo().getMRules().gameHost).getName());
        else
            board.updateLine(1, colorPrefix + "➤ Host : " + ChatColor.WHITE + "Aucun");
    }

    public static void updatePreparationBoard(Setup setup, FastBoard board) {
        MPlayer mPlayer = setup.getGame().getMPlayer(board.getPlayer().getUniqueId());
        MRules rules = setup.getGame().getGameInfo().getMRules();

        board.updateTitle(rules.gameTitle);
        board.updateLine(0, "");
        if (rules.gameHost != null)
            board.updateLine(1, colorPrefix + "➤ Host : " + ChatColor.WHITE + Bukkit.getOfflinePlayer(rules.gameHost).getName());
        else
            board.updateLine(1, colorPrefix + "➤ Host : " + ChatColor.WHITE + "Aucun");

        board.updateLine(2, "");

        if (mPlayer != null) {
            board.updateLine(3, colorPrefix + "➤ Rôle : " + ChatColor.WHITE + (mPlayer.role == null ? "?" : mPlayer.role.getRole().name().toLowerCase()));
            board.updateLine(4, "");
            board.updateLine(5, colorPrefix + "➤ Temps : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMTime().rawTime);
            board.updateLine(6, "");
            board.updateLine(5, colorPrefix + "➤ Episode : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMTime().episode);
            return;
        }
        board.updateLine(3, colorPrefix + "➤ Temps : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMTime().rawTime);
        board.updateLine(4, "");
        board.updateLine(5, colorPrefix + "➤ Episode : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMTime().episode);
    }

    public static void updateNarutoBoard(Setup setup, FastBoard board) {
        MPlayer mPlayer = setup.getGame().getMPlayer(board.getPlayer().getUniqueId());
        MRules rules = setup.getGame().getGameInfo().getMRules();

        board.updateTitle(rules.gameTitle);
        board.updateLine(0, "");
        if (rules.gameHost != null)
            board.updateLine(1, colorPrefix + "➤ Host : " + ChatColor.WHITE + Bukkit.getOfflinePlayer(rules.gameHost).getName());
        else
            board.updateLine(1, colorPrefix + "➤ Host : " + ChatColor.WHITE + "Aucun");

        board.updateLine(2, "");
        board.updateLine(3, colorPrefix + "➤ Groupes : " + ChatColor.WHITE + rules.groupSize);
        board.updateLine(4, "");

        if (mPlayer != null) {
            board.updateLine(5, colorPrefix + "➤ Rôle : " + ChatColor.WHITE + (mPlayer.role == null ? "?" : mPlayer.role.getRole().name().toLowerCase()));
            board.updateLine(6, "");
            board.updateLine(7, colorPrefix + "➤ Kills : " + ChatColor.WHITE + mPlayer.kills.size());
            board.updateLine(8, "");
            board.updateLine(9, colorPrefix + "➤ Temps : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMTime().rawTime);
            board.updateLine(10, "");
            World world = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
            if (world.getWorldBorder().getSize() == setup.getGame().getGameInfo().getMBorder().defaultSize)
                board.updateLine(11, colorPrefix + "➤ Bordure : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMBorder().timeBeforeResize);
            else
                board.updateLine(11, colorPrefix + "➤ Bordure : " + ChatColor.WHITE + (world.getWorldBorder().getCenter().getX() - world.getWorldBorder().getSize()) + "/" + (world.getWorldBorder().getCenter().getX() + world.getWorldBorder().getSize()));

        }

        board.updateLine(10, "");

        board.updateLine(11, colorPrefix + "➤ Bordure : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMBorder().timeBeforeResize);

    }

}
