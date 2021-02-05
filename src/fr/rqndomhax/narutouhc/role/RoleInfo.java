/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class RoleInfo implements Role {

    private final Roles role;
    private final GamePlayer gamePlayer;

    public boolean hasClaimed = false;

    public RoleInfo(GamePlayer gamePlayer, Roles role) {
        this.gamePlayer = gamePlayer;
        this.role = role;
    }

    @Override
    public void onInit(Setup setup) {

    }

    @Override
    public void onRoleGiven(Setup setup) {
        onDesc();
        giveEffects();
    }

    @Override
    public void onClaim() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.ROLE_NO_ITEMS);
    }

    @Override
    public void onChose() {

    }

    @Override
    public void giveEffects() {

    }

    @Override
    public void onHit(GamePlayer gamePlayer) {

    }

    @Override
    public void onDesc() {

    }

    @Override
    public void onCommand(Setup setup) {

    }

    @Override
    public void onKill(GamePlayer killed) {

    }

    @Override
    public void onPrematureDeath(Location deathLocation) {

    }

    @Override
    public void onDeath(Setup setup) {

    }

    @Override
    public void onPlayerDeath(GamePlayer gamePlayer) {

    }

    @Override
    public void onPlayerJoin() {
        giveEffects();
    }

    @Override
    public void onNewEpisode(int episode) {

    }

    public Roles getRole() {
        return role;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
}
