/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.akatsuki.IObito;
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

public class Obito extends RoleInfo {

    int commandUses = 2;

    public Obito(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.OBITO);
    }

    @Override
    public void onCommand(Setup setup) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (commandUses == 0) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        Set<GamePlayer> players = new HashSet<>();

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.equals(getGamePlayer()))
                continue;

            if (gamePlayer.isDead)
                continue;

            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;

            if (DistanceRadius.getRadius(p.getLocation(), p.getLocation()) <= 30*30)
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

        player.openInventory(new IObito(setup, getGamePlayer(), player, players, inventory_size*9).getInventory());

        commandUses--;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Obito.");
        player.sendMessage("Votre but est de gagner avec l'akatsuki.");
        player.sendMessage("Pour ce faire, vous avez la possibilité de vous téléporter 2 fois dans une salle entouré de bedrock en 30x30 dans la bordure.");
        player.sendMessage("Pour cela, vous devez utiliser la commande /na obito,");
        player.sendMessage("Un inventaire s'ouvrira et vous pourrez choisir le joueur de votre choix dans un rayon de 30 blocks.");
        player.sendMessage("Dans cette salle vous obtiendrez l'effet strength 1.");
        player.sendMessage("Vous resterez 30 secondes dans cette pièce et serez téléporté aléatoirement sur la map une fois les 30 secondes écoulées.");
    }

    @Override
    public void onTeam() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        StringBuilder sb = new StringBuilder();

        for (GamePlayer gamePlayer : MGamePublicRoles.akatsukis) {
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
