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
    FLINTS(10.0d, ChatColor.DARK_GRAY + "Silex", new ItemStack(Material.FLINT), 22),
    PEARLS(50.0d, ChatColor.DARK_BLUE + "Perle", new ItemStack(Material.ENDER_PEARL), 30),
    APPLE(0.5d, ChatColor.RED + "Pommes", new ItemStack(Material.APPLE), 32);

    private double percentage;
    private final String name;
    private final ItemStack item;
    private final int slot;

    Drops(double percentage, String name, ItemStack item, int slot) {
        this.percentage = percentage;
        this.name = name;
        this.item = item;
        this.slot = slot;
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

    public int getSlot() {
        return slot;
    }

    enum speed {
        MINIMUM(0.5),
        EASY(1),
        MEDIUM(5),
        FAST(10);

        private final double value;

        speed(double value) {
            this.value = value;
        }
    }

}
