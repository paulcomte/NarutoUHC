/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.scoreboards;

import fr.rqndomhax.narutouhc.core.Setup;
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

        ChatColor colorPrefix = ChatColor.DARK_BLUE;
        MPlayer mPlayer = setup.getGame().getMPlayer(board.getPlayer().getUniqueId());
        MRules rules = setup.getGame().getGameInfo().getMRules();
        GameState state = setup.getGame().getGameInfo().getGameState();

        switch (state) {
            case GAME_BORDER:
            case GAME_MEETUP:
            case GAME_FINISHED:
                GameScoreboardManager.updateNarutoBoard(setup, board);
                break;
            case LOBBY_WAITING:
            case LOBBY_TELEPORTING:
                GameScoreboardManager.updateLobbyBoard(setup, board);
                break;
            case GAME_INVINCIBILITY:
            case GAME_PREPARATION:
            case GAME_TELEPORTING:
                GameScoreboardManager.updatePreparationBoard(setup, board);
                break;
            default:
                GameScoreboardManager.updateLobbyBoard(setup, board);
                break;
        }


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
