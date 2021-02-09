/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class GameScoreboard {

    private static final Map<UUID, FastBoard> boards = new HashMap<>();
    private static Setup setup;
    private static int i;

    public static void init(Setup setup) {
        GameScoreboard.setup = setup;
        runBoard();
    }

    public static void newGameScoreboard(Player player) {
        FastBoard fb = new FastBoard(player);
        fb.updateTitle(setup.getGame().getGameRules().gameTitle);

        boards.put(player.getUniqueId(), fb);
    }

    public static void removeGameScoreboard(Player player) {
        boards.remove(player.getUniqueId());
    }

    private static void updateBoard(FastBoard board) {

        GameState state = setup.getGame().getGameState();

        switch (state) {
            case GAME_FINISHED:
                GameScoreboardManager.updateGameFinishedBoard(setup, board);
                break;
            case GAME_TELEPORTATION_INVINCIBILITY:
            case GAME_BORDER:
            case GAME_MEETUP:
                if (setup.getGame().getMainTask() == null)
                    GameScoreboardManager.updateLobbyBoard(setup, board);
                else
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

    private static void runBoard() {
        Bukkit.getServer().getScheduler().runTaskTimer(setup.getMain(), () -> {
            for (FastBoard board : boards.values()) {

                updateBoard(board);

                i++;
            }
        }, 0, 20);
    }

}
