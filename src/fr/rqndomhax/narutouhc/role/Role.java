/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;

public interface Role {

    void onRoleGiven(Setup setup);

    void onClaim();

    void onChose();

    void giveEffects();

    void onHit(GamePlayer gamePlayer);

    void onDesc();

    void onCommand(Setup setup);

    void onKill(GamePlayer killed);

    void onDeath();

    void onPlayerDeath(GamePlayer dead);

    void onPlayerJoin();

    void onNewEpisode(int episode);

}
