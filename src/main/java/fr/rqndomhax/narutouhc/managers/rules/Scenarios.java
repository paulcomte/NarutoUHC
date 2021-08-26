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

public enum Scenarios {

    CAT_EYES(ChatColor.YELLOW + "Cat's Eyes", new ItemStack(Material.EYE_OF_ENDER)),
    MEETUP(ChatColor.RED + "Meetup", new ItemStack(Material.REDSTONE)),
    CUTCLEAN(ChatColor.DARK_GRAY + "CutClean", new ItemStack(Material.IRON_INGOT)),
    REVIVE_BEFORETP(ChatColor.LIGHT_PURPLE + "Auto Revive - Preparation", new ItemStack(Material.IRON_CHESTPLATE)),
    FINAL_HEAL_TELEPORT(ChatColor.GREEN + "Final Heal - TP", new ItemStack(Material.GHAST_TEAR)),
    FASTER_EPISODE(ChatColor.GOLD + "Faster episode", new ItemStack(Material.EMERALD)),
    HIDDEN_ROLES(ChatColor.LIGHT_PURPLE + "Hidden roles", new ItemStack(Material.ROTTEN_FLESH)),
    NO_NAME_TAG(ChatColor.AQUA + "NoNameTag", new ItemStack(Material.NAME_TAG)),
    FASTER_TEAM(ChatColor.DARK_BLUE + "Faster team", new ItemStack(Material.WATCH));

    private final String description;
    private final ItemStack item;

    Scenarios(String description, ItemStack item) {
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
