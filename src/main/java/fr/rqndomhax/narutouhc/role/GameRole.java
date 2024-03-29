/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.Game;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.role.shinobi.Naruto;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameRole {

    private final Game game;
    private final List<Roles> availableRoles = new ArrayList<>();
    private final HashMap<GamePlayer, Roles> adminRoles = new HashMap<>();

    public GameRole(Game game) {
        this.game = game;
    }

    public void dispatchRoles(Setup setup) {
        availableRoles.clear();
        availableRoles.addAll(game.getGameRules().activatedRoles);
        for (GamePlayer player : game.getGamePlayers()) {
            if (availableRoles.get(0) == null) return;
            Roles role = adminRoles.getOrDefault(player, null);
            if (role != null)
                adminRoles.remove(player);
            if (role == null)
                role = availableRoles.get(0);
            if (player.role == null) {
                try {
                    player.role = (RoleInfo) role.getRoleInfo().getDeclaredConstructors()[0].newInstance(player);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                availableRoles.remove(0);
            }
        }

        for (GamePlayer player : game.getGamePlayers()) {
            if (player.role instanceof Naruto) {
                GamePlayer sasuke = game.getGamePlayerFromRole(Roles.SASUKE);

                if (sasuke != null && (sasuke.role.getRole().getTeam().equals(Team.AKATSUKI)))
                    ((Naruto) player.role).isSasuke = true;
            }
            if (player.role != null) {
                player.role.onInit(setup);
                player.role.onDesc();
                player.role.giveEffects();
                player.role.onTeam();
            }
        }
    }

    public String setAdminRole(GamePlayer player, Roles role) {
        availableRoles.clear();
        availableRoles.addAll(game.getGameRules().activatedRoles);
        if (!game.getGameRules().adminRoles)
            return Messages.ADMIN_ROLES_NOT_ENABLE;
        if (player == null)
            return Messages.PLAYER_NOT_EXIST;
        if (!game.getGameState().equals(GameState.LOBBY_WAITING))
            return Messages.NOT_IN_LOBBY;
        if (!availableRoles.contains(role))
            return Messages.ROLE_NOT_PRESENT;
        Roles r = adminRoles.getOrDefault(player, null);
        if (r != null && adminRoles.containsValue(role) && !r.equals(role))
            return Messages.ADMIN_ROLE_ALREADY_GAVE;
        adminRoles.put(player, role);
        return Messages.ADMIN_ROLE_ADDED.replace("%role%", role.name()).replace("%player%", Bukkit.getOfflinePlayer(player.uuid).getName());
    }

}
