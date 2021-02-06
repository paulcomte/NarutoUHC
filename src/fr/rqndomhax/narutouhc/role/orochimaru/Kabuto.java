/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.orochimaru;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.inventories.role.orochimaru.IOrochimaru;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.DistanceRadius;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Kabuto extends RoleInfo {

    boolean hasUsedCapacity = true;

    public Kabuto(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KABUTO);
    }

    @Override
    public void onCommand(Setup setup) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (hasUsedCapacity) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        Set<GamePlayer> players = new HashSet<>();

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.equals(getGamePlayer()))
                continue;

            if (!gamePlayer.isDead)
                continue;

            if (gamePlayer.role == null || gamePlayer.role.getRole() == null || gamePlayer.role.getRole().getTeam() == null || !gamePlayer.role.getRole().getTeam().equals(Team.AKATSUKI))
                return;

            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;

            if (DistanceRadius.getRadius(p.getLocation(), p.getLocation()) <= 50*50)
                players.add(gamePlayer);
        }

        int size = 0;
        int inventory_size = 1;

        for (GamePlayer gamePlayer : players) {
            if (size == 9) {
                inventory_size++;
                size = 0;
            }
            size++;
        }

        player.openInventory(new IOrochimaru(setup, player, players, inventory_size*9).getInventory());

        hasUsedCapacity = true;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Kabuto.");
        player.sendMessage("Votre but est de gagner avec votre équipe.");
        player.sendMessage("Pour ce faire, le pseudo de votre/vos teammates seront affichés dans le chat.");
        player.sendMessage("Chaque épisode, vous pourrez donner un effet de " + ChatColor.BLACK + "wither 2 " + ChatColor.RESET + "pendant 5 secondes à une personne qui se trouve dans un rayon de 50 blocks autour de vous, en faisant /na kabuto.");
        player.sendMessage("Un inventaire s'ouvrira et vous devrez cliquer sur la personne de votre choix.");
    }

    @Override
    public void onNewEpisode(int episode) {
        hasUsedCapacity = false;
    }

    @Override
    public void onTeam() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        StringBuilder sb = new StringBuilder();

        for (GamePlayer gamePlayer : MGamePublicRoles.orochimarus) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;
            sb.append(p.getName());
            sb.append("  ");
        }
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Equipe " + ChatColor.BLACK + "-----");
        player.sendMessage(sb.toString());
    }
}
