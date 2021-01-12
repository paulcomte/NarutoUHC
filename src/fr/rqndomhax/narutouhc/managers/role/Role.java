/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role;

import fr.rqndomhax.narutouhc.infos.RoleType;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MPlayer;

public interface Role {

    Roles role = null;
    RoleType roleType = null;

    void onRoleGiven();

    void onClaim();

    void onChose();

    void giveEffects();

    void onHit(MPlayer mPlayer);

    void onDesc();

    void onCommand();

    void onKill(MPlayer killed);

    void onDeath();

}
