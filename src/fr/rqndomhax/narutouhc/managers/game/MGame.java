/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.MRules;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MGame {

    private final Setup setup;
    private final MGameInfo gameInfo;
    private final Set<MPlayer> gamePlayers = new HashSet<>();

    public MGame(Setup setup) {
        this.setup = setup;
        gameInfo = new MGameInfo(new MBorder(), new MRules());
    }

    public MGameInfo getGameInfo() {
        return gameInfo;
    }

    public Set<MPlayer> getGamePlayers() {
        return gamePlayers;
    }

    public MPlayer getMPlayer(String username) {
        Player player = Bukkit.getPlayer(username);

        if (player == null) return null;
        return getMPlayer(player.getUniqueId());
    }

    public MPlayer getMPlayer(UUID uuid) {
        for (MPlayer player : gamePlayers) {
            if (player.uuid.equals(uuid))
                return player;
        }
        return null;
    }

}
