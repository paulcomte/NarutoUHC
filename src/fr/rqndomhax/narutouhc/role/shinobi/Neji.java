/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.shinobi.INeji;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.DistanceRadius;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Neji extends RoleInfo {

    int commandUses = 2;

    public Neji(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.NEJI);
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

            if (DistanceRadius.getRadius(p.getLocation(), p.getLocation()) <= 10)
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

        player.openInventory(new INeji(setup, player, getGamePlayer(), players, inventory_size*9).getInventory());

        commandUses--;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Neji.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, vous pouvez voir si le rôle d'un joueur est \"suspect\" ou \"gentil\", vous disposerez de 2 utilisations en utilisant la commande /na neji");
        player.sendMessage("Un inventaire s'ouvrira avec la liste des joueurs présents dans un rayon de 10 bloocks.");
        player.sendMessage("Une fois le joueur séléctionné, vous devrez rester à proximité du joueur pendant 10 secondes au total.");
        player.sendMessage("Si aucun joueur n'a été sélectioonné dans l'inventaire, vous perdre tout de même une utilisation.");
    }
}
