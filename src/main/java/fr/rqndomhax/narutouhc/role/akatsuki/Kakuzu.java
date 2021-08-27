/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public class Kakuzu extends Akatsuki {

    public Kakuzu(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KAKUZU);
    }

    double maxHealth = 20;

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        player.setMaxHealth(maxHealth);
    }

    @Override
    public void onClaim() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (hasClaimed) {
            player.sendMessage(Messages.ROLE_NO_ITEMS);
            return;
        }

        long nSpace = Arrays.stream(player.getInventory().getContents()).filter(Objects::isNull).count();
        if (nSpace < 2) {
            player.sendMessage(Messages.ROLE_ITEMS_NEED_SPACE.replace("%n%", "1").replace("objets", "objet"));
            return;
        }

        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack());
        hasClaimed = true;
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Kakuzu.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'akatsuki.");
        player.sendMessage(ChatColor.BLUE + "Vous obtenez 1 coeur supplémentaire à chaque kill.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "2 livres protection 3.");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "2 livres protection 3.");
    }

    @Override
    public void onKill(GamePlayer killed) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);

        if (player == null)
            return;
        if (killed.role == null)
            return;

        maxHealth += 2;
        giveEffects();
    }
}
