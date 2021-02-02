/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.rules;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum DayCycle {

    NORMAL(ChatColor.GREEN + "Cycle classique", new ItemStack(Material.FLOWER_POT_ITEM)),
    NIGHT(ChatColor.DARK_BLUE + "Nuit éternelle", new ItemStack(Material.COAL)),
    DAY(ChatColor.YELLOW + "Jour éternelle", new ItemStack(Material.DOUBLE_PLANT));

    private final String description;
    private final ItemStack item;

    DayCycle(String description, ItemStack item) {
        this.description = description;
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getItem() {
        return item;
    }
}
