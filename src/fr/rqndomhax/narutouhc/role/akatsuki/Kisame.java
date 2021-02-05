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

public class Kisame extends RoleInfo {

    public Kisame(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KISAME);
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

        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.DEPTH_STRIDER, 2).toItemStack());
        player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 32));
        hasClaimed = true;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Kisame.");
        player.sendMessage("Votre but est de gagner avec l'akatsuki.");
        player.sendMessage("Pour ce faire, vous obtenez l'effet " + ChatColor.AQUA + "speed 1 " + ChatColor.RESET + "ainsi que l'effet " + ChatColor.RED + "strength 1" + ChatColor.RESET + "sous l'eau, et vous obtenez également un livre " + ChatColor.LIGHT_PURPLE + "depth strider 3 " + ChatColor.RESET + "et de 32 bottles d'xp.");
    }
}
