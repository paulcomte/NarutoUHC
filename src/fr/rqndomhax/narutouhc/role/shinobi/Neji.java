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

        player.sendMessage("Vous êtes Neji.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, vous pouvez voir si le rôle d'un joueur est \"suspect\" ou \"gentil\".");
        player.sendMessage("Cet item vous donnera l'effet \"" + ChatColor.DARK_RED + "force 2" + ChatColor.RESET + ", " + ChatColor.AQUA + "speed 1" + ChatColor.RESET + "et " + ChatColor.GOLD + "fire resistance " + ChatColor.RESET + ".");
        player.sendMessage("Ainsi que \"" + ChatColor.RED + "2 coeurs supplémentaires" + ChatColor.RESET + " et " + ChatColor.DARK_PURPLE + "no fall" + ChatColor.RESET + ".");
        player.sendMessage("Pour une durée totale de 10 minutes.");
        player.sendMessage("Une fois ces 10 minutes passées, vous tomberez à " + ChatColor.RED + "0.5 coeurs permanents" + ChatColor.RESET + ".");
        if (!hasClaimed)
            player.sendMessage("Pour le récupérer faites \"/na claim\"");
    }
}
