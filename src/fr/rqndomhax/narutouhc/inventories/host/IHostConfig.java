/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class IHostConfig {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostConfig(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int[] bars = new int[]{3, 5, 48, 50};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.MAIN_HOST_HOST.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(inventory.getInventory());

        inventory.setItem(13, getSpectators(), changeSpectators());

        inventory.setItem(29, getBorderDisconnects(), changeBorderDisconnects());

        OfflinePlayer offlineHost = Bukkit.getOfflinePlayer(setup.getGame().getGameRules().gameHost);

        inventory.setItem(31, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setName("Host: " + offlineHost.getName()).setSkullOwner(offlineHost.getName()).toItemStack());

        inventory.setItem(33, getWhitelist(), changeWhitelist());

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            GameRules rules = setup.getGame().getGameRules();
            player.closeInventory();
            if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId()))
                player.openInventory(new IHost(setup, player).getInventory());
        });

        player.updateInventory();
    }

    private ItemStack getBorderDisconnects() {
        if (setup.getGame().getGameRules().spectatorsAfterBorder)
            return new ItemBuilder(IInfos.HOST_SPECTATORS_AFTER_BORDER.clone()).setName("Déconnexion bordure " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_SPECTATORS_AFTER_BORDER.clone()).setName("Déconnexion bordure " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeBorderDisconnects() {
        return e -> {
            setup.getGame().getGameRules().spectatorsAfterBorder = !setup.getGame().getGameRules().spectatorsAfterBorder;
            updateInventory();
        };
    }

    private ItemStack getSpectators() {
        if (setup.getGame().getGameRules().allowSpectators)
            return new ItemBuilder(IInfos.HOST_SPECTATORS.clone()).setName("Spectateurs " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_SPECTATORS.clone()).setName("Spectateurs " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeSpectators() {
        return e -> {
            setup.getGame().getGameRules().allowSpectators = !setup.getGame().getGameRules().allowSpectators;
            updateInventory();
        };
    }

    private ItemStack getWhitelist() {
        if (setup.getGame().getGameRules().hasWhitelist)
            return new ItemBuilder(IInfos.HOST_WHITELIST.clone()).setName("Whitelist " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_WHITELIST.clone()).setName("Whitelist " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeWhitelist() {
        return e -> {
            setup.getGame().getGameRules().hasWhitelist = !setup.getGame().getGameRules().hasWhitelist;
            updateInventory();
        };
    }

}
