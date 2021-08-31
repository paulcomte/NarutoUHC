/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game;

import fr.rqndomhax.narutouhc.infos.Maps;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class GameInfo {

    public static String gameHost = null;
    public static final Set<UUID> gameCoHost = new HashSet<>();
    public static final Set<UUID> bannedPlayers = new HashSet<>();
    public static Maps currentMap = Maps.NO_PVP;

}
