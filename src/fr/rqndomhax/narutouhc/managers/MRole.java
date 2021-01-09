/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class MRole {

    private final Setup setup;
    private final List<Roles> availableRoles;
    private final List<Roles> adminRoles = new ArrayList<>();

    public MRole(Setup setup) {
        this.setup = setup;
        availableRoles = setup.getGame().getGameInfo().getmRules().activatedRoles;
    }

    private void dispatchRoles() {
        for (MPlayer player : setup.getGame().getGamePlayers()) {
            if (availableRoles.get(0) == null) return;
            if (adminRoles.contains(availableRoles.get(0))) {
                availableRoles.remove(0);
                continue;
            }
            if (player.role != null) continue;
            player.role = availableRoles.get(0);
            availableRoles.remove(0);
        }
    }

    public void autoUpdateAdminRoles() {
        for (MPlayer player : setup.getGame().getGamePlayers()) {
            if (player.role == null) continue;
            if (availableRoles.contains(player.role)) continue;
            adminRoles.remove(player.role);
            player.role = null;
        }
    }

    public String removeAdminRole(MPlayer player) {
        if (!setup.getGame().getGameInfo().getmRules().adminRoles)
            return Messages.ADMIN_ROLES_NOT_ENABLE;
        if (player == null)
            return Messages.PLAYER_NOT_EXIST;
        if (!setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING))
            return Messages.NOT_IN_LOBBY;
        if (player.role == null)
            return Messages.ADMIN_PLAYER_ROLE_NOT_PRESENT;
        Roles role = player.role;
        adminRoles.remove(player.role);
        player.role = null;
        return Messages.ADMIN_ROLE_REMOVED.replace("%role%", role.name()).replace("%player%", Bukkit.getOfflinePlayer(player.uuid).getName());
    }

    public String setAdminRole(MPlayer player, Roles role) {
        if (!setup.getGame().getGameInfo().getmRules().adminRoles)
            return Messages.ADMIN_ROLES_NOT_ENABLE;
        if (player == null)
            return Messages.PLAYER_NOT_EXIST;
        if (!setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING))
            return Messages.NOT_IN_LOBBY;
        if (!availableRoles.contains(role))
            return Messages.ROLE_NOT_PRESENT;
        if (adminRoles.contains(role))
            return Messages.ADMIN_ROLE_ALREADY_GAVE;
        if (player.role != null)
            adminRoles.remove(player.role);
        player.role = role;
        adminRoles.add(role);
        return Messages.ADMIN_ROLE_ADDED.replace("%role%", role.name()).replace("%player%", Bukkit.getOfflinePlayer(player.uuid).getName());
    }

}
