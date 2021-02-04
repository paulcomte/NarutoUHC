/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.tools;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryManager {

    public static void dropInventory(ItemStack[] items, Location dropLocation, boolean dropNaturally) {
        for(int slot = 0; slot < items.length; slot++){
            ItemStack item = items[slot];
            if (item == null)
                continue;
            if (dropNaturally)
                dropLocation.getWorld().dropItemNaturally(dropLocation, item);
            else
                dropLocation.getWorld().dropItem(dropLocation, item);
        }
    }

    public static void saveInventory(ItemStack[] items, Player player)  {
        for(int slot = 0; slot < 36; slot++){
            ItemStack item = player.getInventory().getItem(slot);
                items[slot] = item;
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.updateInventory();
    }

    public static void clearInventory(Player player) {
        for (int slot = 0 ; slot < 36 ; slot++)
            player.getInventory().setItem(slot, null);

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.updateInventory();
    }

    public static void giveInventory(ItemStack[] items, Player player) {
        player.getInventory().clear();

        for(int slot = 0; slot < 36; slot++){
            ItemStack item = items[slot];
            player.getInventory().setItem(slot, item);
        }

        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
        player.updateInventory();
    }
}
