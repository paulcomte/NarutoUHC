/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MPlayer;

public abstract class RoleInfo implements Role {

    private final Roles role;
    private final MPlayer mPlayer;

    public boolean hasClaimed = false;

    public RoleInfo(MPlayer mPlayer, Roles role) {
        this.mPlayer = mPlayer;
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public MPlayer getMPlayer() {
        return mPlayer;
    }
}
