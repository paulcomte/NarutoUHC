/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class Kakuzu extends RoleInfo {

    public Kakuzu(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KAKUZU);
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

        ItemStack books = new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).toItemStack();

        player.getInventory().addItem(books);
        player.getInventory().addItem(books);
        hasClaimed = true;
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        player.setMaxHealth(30);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Kakuzu.");
        player.sendMessage("Votre but est de gagner avec l'akatsuki.");
        player.sendMessage("Pour ce faire, à l'annonce des rôles vous gagnez, 5 slots de coeurs vides ainsi que " + ChatColor.LIGHT_PURPLE + "2 livres protection 3" + ChatColor.RESET + ".");
    }
}
