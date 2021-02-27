/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.tasks.TMain;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.listeners.serverping.Pings;
import fr.rqndomhax.narutouhc.listeners.serverping.ServerPing;
import fr.rqndomhax.narutouhc.managers.config.ConfigLogos;
import fr.rqndomhax.narutouhc.managers.config.HostConfig;
import fr.rqndomhax.narutouhc.role.GameRole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Game {

    private GameState gameState = GameState.LOADING;
    private TMain mainTask;
    private HostConfig currentConfig;
    private GameRole gameRole;
    private final Set<GamePlayer> gamePlayers = new HashSet<>();

    public Game createGame() {
        currentConfig = new HostConfig(new GameRules(), "Default", ConfigLogos.DEFAULT, "default.cfg");

        gameRole = new GameRole(this);
        return this;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public TMain getMainTask() {
        return mainTask;
    }

    public HostConfig getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(HostConfig currentConfig) {
        this.currentConfig = currentConfig;
    }

    public GameRole getGameRole() {
        return gameRole;
    }

    public GameRules getGameRules() {
        if (currentConfig == null)
            return null;
        return currentConfig.getRules();
    }

    public GamePlayer getGamePlayerFromRole(Roles role) {
        for (GamePlayer gamePlayer : gamePlayers) {
            if (gamePlayer.role != null && gamePlayer.role.getRole().equals(role))
                return gamePlayer;
        }
        return null;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public GamePlayer getGamePlayer(String username) {
        Player player = Bukkit.getPlayer(username);

        if (player == null) return null;
        return getGamePlayer(player.getUniqueId());
    }

    public GamePlayer getGamePlayer(UUID uuid) {
        for (GamePlayer player : gamePlayers) {
            if (player.uuid.equals(uuid))
                return player;
        }
        return null;
    }

    public void startTask(Setup setup) {
        removeTask();
        mainTask = new TMain(setup);
    }

    public void removeTask() {
        if (mainTask == null)
            return;
        mainTask.cancel();
        mainTask = null;
        ServerPing.currentPing = Pings.LOBBY_WAITING;
    }

}
