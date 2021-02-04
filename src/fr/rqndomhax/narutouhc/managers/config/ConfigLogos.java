/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ConfigLogos {
    DEFAULT(new ItemStack(Material.DEAD_BUSH));

    private final ItemStack item;

    ConfigLogos(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
