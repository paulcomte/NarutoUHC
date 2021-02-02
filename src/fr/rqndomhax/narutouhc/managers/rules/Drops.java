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

public enum Drops {
    FLINTS(10.0d, ChatColor.DARK_GRAY + "Silex", new ItemStack(Material.FLINT)),
    PEARLS(50.0d, ChatColor.DARK_BLUE + "Perle", new ItemStack(Material.ENDER_PEARL));

    private double percentage;
    private final String name;
    private final ItemStack item;

    Drops(double percentage, String name, ItemStack item) {
        this.percentage = percentage;
        this.name = name;
        this.item = item;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }
}
