/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.role.Roles;

import java.util.List;

public class MRole {

    private final Setup setup;

    public MRole(Setup setup) {
        this.setup = setup;
    }

    private void dispatchRoles() {
        final List<Roles> availableRoles = setup.getGame().getGameInfo().getmRules().activedRoles;

        for (MPlayer player : setup.getGame().getGamePlayers()) {
            if (availableRoles.get(0) == null) return;
            player.role = availableRoles.get(0);
            availableRoles.remove(0);
        }
    }

}
