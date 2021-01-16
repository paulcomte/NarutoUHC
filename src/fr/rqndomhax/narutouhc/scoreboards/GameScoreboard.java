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
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameScoreboard {

    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private final Setup setup;
    private int i;

    public GameScoreboard(Setup setup) {
        this.setup = setup;
    }

    public void newGameScoreboard(Player p) {
        FastBoard fb = new FastBoard(p);
        fb.updateTitle(setup.getGame().getGameInfo().getMRules().gameTitle);

        boards.put(p.getUniqueId(), fb);
    }

    public void removeGameScoreboard(Player p) {
        boards.remove(p.getUniqueId());
    }

    private void updateBoard(FastBoard board) {

        String separator = ChatColor.BOLD + "" + ChatColor.UNDERLINE + "            \n";
        ChatColor colorPrefix = ChatColor.YELLOW;

        board.updateLine(0, separator);
        MRules rules = setup.getGame().getGameInfo().getMRules();
        if (rules.gameHost != null)
            board.updateLine(1, colorPrefix + "➤ Host : " + ChatColor.WHITE + Bukkit.getOfflinePlayer(rules.gameHost).getName());
        else
            board.updateLine(2, colorPrefix + "➤ Host : " + ChatColor.WHITE + "Aucun");
        board.updateLine(3, colorPrefix + "➤ Groupes : " + ChatColor.WHITE + rules.groupSize);
        board.updateLine(4, separator);
        board.updateLine(5, colorPrefix + "➤ Episode : ");
        board.updateLine(6, separator);
        board.updateLine(7, colorPrefix + "➤ PvP : " + (setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_PVP) ? ChatColor.GREEN + "✔" : ChatColor.DARK_RED + "✘"));
        board.updateLine(8, colorPrefix + "➤ Bordure : " + setup.getGame().getGameInfo().getMBorder().timeBeforeResize);
        if (setup.getGame().getGameInfo().getMTime() != null)
            board.updateLine(9, colorPrefix + "➤ Temps : " + ChatColor.WHITE + setup.getGame().getGameInfo().getMTime().rawTime);
        else
            board.updateLine(9, colorPrefix + "➤ Temps : " + ChatColor.WHITE + "00:00");
        board.updateLine(10, separator);
        MPlayer mPlayer = setup.getGame().getMPlayer(board.getPlayer().getUniqueId());
        if (mPlayer != null) {
            board.updateLine(11, colorPrefix + "➤ Rôle : " + ChatColor.WHITE + (mPlayer.role == null ? "Aucun" : mPlayer.role.getRole().name().toLowerCase()));
            board.updateLine(12, colorPrefix + "➤ Kills : " + ChatColor.WHITE + mPlayer.kills.size());
        }
        else {
            board.updateLine(11, colorPrefix + "➤ Rôle : " + ChatColor.WHITE + "Spectateur");
            board.updateLine(12, colorPrefix + "➤ Kills : " + ChatColor.WHITE + "0");
        }
        board.updateLine(13, separator);
        board.updateLine(14, colorPrefix + "➤ Bordure : " + ChatColor.WHITE + Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()).getWorldBorder().getSize());
        board.updateLine(15, separator);
    }

    public void runBoard() {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(setup.getMain(), () -> {
            for (FastBoard board : boards.values()) {

                updateBoard(board);

                i++;
            }
        }, 0, 20);
    }

}
