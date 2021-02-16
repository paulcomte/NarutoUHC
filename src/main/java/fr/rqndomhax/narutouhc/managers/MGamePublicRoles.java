/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.tasks.role.TList;

import java.util.ArrayList;
import java.util.List;

public abstract class MGamePublicRoles {

    public static final List<GamePlayer> akatsukis = new ArrayList<>();

    public static final List<GamePlayer> itachiAkatsukis = new ArrayList<>();

    public static final List<GamePlayer> orochimarus = new ArrayList<>();

    public static TList task = null;

    public static void stopList() {
        if (task == null)
            return;

        task.cancel();
        task.stopTasks();
        task = null;
    }

    public static void initAkatsukis(Setup setup) {
        task = new TList(setup.getGame().getGamePlayers(), setup.getMain());
    }

    public static void initOrochimarus(Setup setup) {
        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers())
            if (gamePlayer.role.getRole().getTeam().equals(Team.OROCHIMARU))
                orochimarus.add(gamePlayer);
    }

}