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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
        if (nSpace < 3) {
            player.sendMessage(Messages.ROLE_ITEMS_NEED_SPACE.replace("%n%", "2"));
            return;
        }

        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.DEPTH_STRIDER, 3).toItemStack());
        player.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE, 32));
        player.getInventory().addItem(Roles.KISAME.getRoleItem());
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
        player.sendMessage(ChatColor.BLUE + "Vous disposez d'une épée en diamant Sharpness 1 \"Sahameda\" qui régénère 0.5 coeur par coup.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "\"Sahameda\", 1 livre depth strider 3 et 32 bottles d'xp.");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "\"Sahameda\", 1 livre depth strider 3 et 32 bottles d'xp.");
    }

    @Override
    public void onHit(GamePlayer gamePlayer) {
        if (gamePlayer == null)
            return;

        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null || !player.isOnline())
            return;

        if (player.getItemInHand() == null || !player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() || !player.getItemInHand().getItemMeta().getDisplayName().equals(Roles.KISAME.getRoleItem().getItemMeta().getDisplayName()) || !player.getItemInHand().getType().equals(Roles.KISAME.getRoleItem().getType()))
            return;
        player.setHealth(player.getHealth() + 1);
    }
}
