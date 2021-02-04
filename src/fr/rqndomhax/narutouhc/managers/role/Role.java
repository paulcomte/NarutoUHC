/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role;

import fr.rqndomhax.narutouhc.managers.GamePlayer;

public interface Role {

    void onRoleGiven();

    void onClaim();

    void onChose();

    void giveEffects();

    void onHit(GamePlayer gamePlayer);

    void onDesc();

    void onCommand();

    void onKill(GamePlayer killed);

    void onDeath();

    void onPlayerDeath(GamePlayer dead);

    void onPlayerJoin();

    void onNewEpisode(int episode);

}
