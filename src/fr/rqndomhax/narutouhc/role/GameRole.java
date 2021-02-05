/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.Game;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GameRole {

    private final Game game;
    private final List<Roles> availableRoles = new ArrayList<>();
    private final List<Roles> adminRoles = new ArrayList<>();

    public GameRole(Game game) {
        this.game = game;
        availableRoles.addAll(game.getGameRules().activatedRoles);
    }

    public void dispatchRoles() {
        for (GamePlayer player : game.getGamePlayers()) {
            if (availableRoles.get(0) == null) return;
            if (adminRoles.contains(availableRoles.get(0)))
                availableRoles.remove(0);
            if (player.role == null) {
                try {
                    player.role = (RoleInfo) availableRoles.get(0).getRoleInfo().getDeclaredConstructors()[0].newInstance(player);
                    player.role.onDesc();
                    player.role.giveEffects();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                availableRoles.remove(0);
            }
        }
    }

    public void autoUpdateAdminRoles() {
        for (GamePlayer player : game.getGamePlayers()) {
            if (player.role == null) continue;
            if (availableRoles.contains(player.role.getRole())) continue;
            adminRoles.remove(player.role.getRole());
            player.role = null;
        }
    }

    public String removeAdminRole(GamePlayer player) {
        if (!game.getGameRules().adminRoles)
            return Messages.ADMIN_ROLES_NOT_ENABLE;
        if (player == null)
            return Messages.PLAYER_NOT_EXIST;
        if (!game.getGameState().equals(GameState.LOBBY_WAITING))
            return Messages.NOT_IN_LOBBY;
        if (player.role == null)
            return Messages.ADMIN_PLAYER_ROLE_NOT_PRESENT;
        Roles role = player.role.getRole();
        adminRoles.remove(role);
        player.role = null;
        return Messages.ADMIN_ROLE_REMOVED.replace("%role%", role.name()).replace("%player%", Bukkit.getOfflinePlayer(player.uuid).getName());
    }

    public String setAdminRole(GamePlayer player, Roles role) {
        if (!game.getGameRules().adminRoles)
            return Messages.ADMIN_ROLES_NOT_ENABLE;
        if (player == null)
            return Messages.PLAYER_NOT_EXIST;
        if (!game.getGameState().equals(GameState.LOBBY_WAITING))
            return Messages.NOT_IN_LOBBY;
        if (!availableRoles.contains(role))
            return Messages.ROLE_NOT_PRESENT;
        if (adminRoles.contains(role))
            return Messages.ADMIN_ROLE_ALREADY_GAVE;
        if (player.role != null)
            adminRoles.remove(player.role.getRole());
        try {
            player.role = (RoleInfo) role.getRoleInfo().getDeclaredConstructors()[0].newInstance(player);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        adminRoles.add(role);
        return Messages.ADMIN_ROLE_ADDED.replace("%role%", role.name()).replace("%player%", Bukkit.getOfflinePlayer(player.uuid).getName());
    }

}
