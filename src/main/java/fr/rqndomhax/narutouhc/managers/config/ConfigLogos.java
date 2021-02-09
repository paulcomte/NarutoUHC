/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.config;

import net.minecraft.server.v1_8_R3.ItemSaddle;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public enum ConfigLogos {
    DEFAULT(new ItemStack(Material.DIRT)),
    PAPER(new ItemStack(Material.PAPER)),
    SLIME(new ItemStack(Material.SLIME_BALL)),
    BORDER(new ItemStack(Material.BARRIER)),
    XP(new ItemStack(Material.EXP_BOTTLE)),
    ENDERPEARL(new ItemStack(Material.ENDER_PEARL)),
    LAVA_BUCKET(new ItemStack(Material.LAVA_BUCKET)),
    APPLE(new ItemStack(Material.GOLDEN_APPLE)),
    PICK(new ItemStack(Material.IRON_PICKAXE)),
    SWORD(new ItemStack(Material.DIAMOND_SWORD)),
    POWDER(new ItemStack(Material.BLAZE_POWDER)),
    BOTTLE(new ItemStack(Material.GLASS_BOTTLE)),
    EMERALD(new ItemStack(Material.EMERALD)),
    HOOK(new ItemStack(Material.TRIPWIRE_HOOK)),
    HOPPER(new ItemStack(Material.HOPPER))
    ;

    private final ItemStack item;

    ConfigLogos(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}
