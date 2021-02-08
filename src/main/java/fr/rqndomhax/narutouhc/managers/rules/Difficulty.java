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

public enum Difficulty {
    PEACEFUL(ChatColor.LIGHT_PURPLE + "Paisible", new ItemStack(Material.STAINED_CLAY, 1, (byte) 15), org.bukkit.Difficulty.PEACEFUL),
    EASY(ChatColor.GREEN + "Facile", new ItemStack(Material.STAINED_CLAY, 1, (byte) 5), org.bukkit.Difficulty.EASY),
    NORMAL(ChatColor.DARK_GREEN + "Normal", new ItemStack(Material.STAINED_CLAY, 1, (byte) 4), org.bukkit.Difficulty.NORMAL),
    HARD(ChatColor.RED + "Difficile", new ItemStack(Material.STAINED_CLAY, 1, (byte) 14), org.bukkit.Difficulty.HARD);

    private final String description;
    private final ItemStack item;
    private final org.bukkit.Difficulty difficulty;

    Difficulty(String description, ItemStack item, org.bukkit.Difficulty difficulty) {
        this.description = description;
        this.item = item;
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getItem() {
        return item;
    }

    public org.bukkit.Difficulty getDifficulty() {
        return difficulty;
    }
}
