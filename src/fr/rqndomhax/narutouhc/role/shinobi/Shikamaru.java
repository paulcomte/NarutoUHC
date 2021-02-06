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

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Shikamaru.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, Vous donnerez des effets à une personne dans un rayon de 50 blocks grâce à votre capacité spéciale");
        player.sendMessage("Les effets sont " + ChatColor.GRAY + "\"Blindess 1" + ChatColor.RESET + " + " + ChatColor.DARK_GRAY + "Slowness 2\" " + ChatColor.RESET + "pendant 30 secondes");
        player.sendMessage("Cette capacité s'utilisera grâce à la commande \"/na shikamaru\"");
        player.sendMessage("Un inventaire s'ouvrira et vous devrez cliquer sur la personne de votre choix, parmis une liste de joueurs présents dans un rayon de 50 blocks");
        player.sendMessage("Une fois la commande utilisée, même si aucun joueur n'a été séléctionné, elle ne sera utilisable qu'au prochain épisode.");
        player.sendMessage("Utilisations restantes : " + ChatColor.GREEN + commandUses);
    }

    @Override
    public void onNewEpisode(int episode) {
        commandUsed = false;
    }
}
