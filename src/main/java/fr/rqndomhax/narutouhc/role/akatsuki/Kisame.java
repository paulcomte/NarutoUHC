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
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class Kisame extends Akatsuki {

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
            player.sendMessage(Messages.ROLE_ITEMS_NEED_SPACE.replace("%n%", "2"));
            return;
        }

        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.DEPTH_STRIDER, 3).toItemStack());
        player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 32));
        hasClaimed = true;
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Kisame.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'akatsuki.");
        player.sendMessage(ChatColor.BLUE + "Sous l'eau vous disposez d'un effet speed 1 et strength 1.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "1 livre depth strider 3 et 32 bottles d'xp.");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "1 livre depth strider 3 et 32 bottles d'xp.");
    }
}
