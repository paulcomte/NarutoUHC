/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class GameScoreboard {

    private static final Map<UUID, FastBoard> boards = new HashMap<>();
    private static Setup setup;
    public static int n;
    public static int nMax;
    public static int compoDuration = 5;
    public static int mainDuration = 15;
    private static List<List<GamePlayer>> players = null;
    private static List<String> compo = new ArrayList<>();

    public static void init(Setup setup) {
        GameScoreboard.setup = setup;
        runBoard();
    }

    public static void newGameScoreboard(Player player) {
        FastBoard fb = new FastBoard(player);
        fb.updateTitle(setup.getGame().getGameRules().gameTitle);

        boards.put(player.getUniqueId(), fb);
    }

    private static void initPlayers(List<GamePlayer> gamePlayers) {
        int size = 10;
        int n = -1;

        players = new ArrayList<>();

        for (GamePlayer player : gamePlayers) {
            if (player.role == null)
                continue;

            if (size == 10) {
                size = 0;
                n++;
                players.add(new ArrayList<>());
            }
            players.get(n).add(player);
            size++;
        }

        nMax = (compoDuration * players.size());
    }

    private static void updateCompo() {
        if (players == null)
            return;

        if (n == 0)
            return;

        if (n < mainDuration)
            return;

        double current = (double) (n - mainDuration) / compoDuration;

        if (current % 1 != 0)
            return;

        List<GamePlayer> list = players.get((int) current);
        GameScoreboard.compo.clear();
        for (int i = 0; i < 10 && i < list.size(); i++) {
            if (list.get(i) == null || list.get(i).role == null)
                continue;

            if (list.get(i).isDead)
                compo.add(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + list.get(i).role.getRole().getRoleName());
            else
                compo.add(i, ChatColor.BLUE + "" + list.get(i).role.getRole().getRoleName());
        }
    }

    private static boolean showCompo(FastBoard board) {
        if (players == null)
            return false;

        if (n == 0 || setup.getGame().getGameRules().activatedScenarios.contains(Scenarios.HIDDEN_ROLES))
            return false;

        if (n < mainDuration)
            return false;

        double current = (double) (n - mainDuration) / compoDuration;

        if (current % 1 != 0)
            return true;

        board.updateTitle(ChatColor.GOLD + "Composition " + (int) (current + 1) + "/" + players.size());
        board.updateLines(compo);
        return true;
    }

    public static void removeGameScoreboard(Player player) {
        boards.remove(player.getUniqueId());
    }

    private static void updateBoard(FastBoard board) {

        if (showCompo(board))
            return;

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
            case GAME_TELEPORTING2:
                GameScoreboardManager.updatePreparationBoard(setup, board);
                break;
            default:
                GameScoreboardManager.updateLobbyBoard(setup, board);
                break;
        }

    }

    private static void runBoard() {

        AtomicBoolean doClear = new AtomicBoolean(false);

        Bukkit.getServer().getScheduler().runTaskTimer(setup.getMain(), () -> {

            if (setup.getGame().getMainTask() != null && setup.getGame().getMainTask().hasRoles && players == null)
                initPlayers(setup.getGame().getGamePlayers());

            if (players != null && !setup.getGame().getGameRules().activatedScenarios.contains(Scenarios.HIDDEN_ROLES))
                n++;

            if (n - mainDuration >= nMax) {
                n = 0;
                doClear.set(true);
            }

            updateCompo();

            for (FastBoard board : boards.values()) {

                if (doClear.get())
                    board.updateLines(new ArrayList<>());

                updateBoard(board);

            }

            doClear.set(false);
        }, 0, 20);
    }

}
