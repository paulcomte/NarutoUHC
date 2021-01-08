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

import java.util.HashSet;
import java.util.Set;

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
}
