/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role;

import fr.rqndomhax.narutouhc.managers.MPlayer;

public interface Role {

    void onRoleGiven();

    void onClaim();

    void onChose();

    void giveEffects();

    void onHit(MPlayer mPlayer);

    void onDesc();

    void onCommand();

    void onKill(MPlayer killed);

    void onDeath();

    void onPlayerDeath(MPlayer dead);

    void onPlayerJoin();

}
