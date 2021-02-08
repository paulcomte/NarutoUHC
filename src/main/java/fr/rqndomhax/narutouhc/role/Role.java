/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import org.bukkit.Location;

public interface Role {

    void onRoleGiven(Setup setup);

    void onInit(Setup setup);

    void onTeam();

    void onClaim();

    void giveEffects();

    void onHit(GamePlayer gamePlayer);

    void onDesc();

    void onCommand(Setup setup);

    void onKill(GamePlayer killed);

    void onPrematureDeath(Location deathLocation);

    void onDeath(Setup setup);

    void onPlayerDeath(GamePlayer dead);

    void onPlayerJoin();

    void onNewEpisode(int episode);

}
