/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.shinobi.IShikamaru;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.DistanceRadius;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Shikamaru extends RoleInfo {

    int commandUses = 2;
    boolean commandUsed = false;

    public Shikamaru(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.SHIKAMARU);
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

            if (DistanceRadius.getRadius(player.getLocation(), p.getLocation()) <= 50*50)
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


        player.openInventory(new IShikamaru(setup, player, players, inventory_size*9).getInventory());

        commandUses--;
        commandUsed = true;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Shikamaru.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Deux fois (mais une fois par épisode), vous aurez la possibilité d'utiliser la commande /na shikamaru.");
        player.sendMessage(ChatColor.BLUE + "Vous pourrez donner 30 secondes de blindess 1 et 30 secondes de slowness 2 à  un joueur autour de vous dans un rayon de 50 blocks.");
    }

    @Override
    public void onNewEpisode(int episode) {
        commandUsed = false;
    }
}