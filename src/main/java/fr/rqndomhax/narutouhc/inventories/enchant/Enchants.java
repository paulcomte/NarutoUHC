/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.enchant;

import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Enchants {

    // COMBAT
    SHARPNESS(Enchantment.DAMAGE_ALL, "Sharpness", 20, EnchantsType.COMBAT),
    PUNCH(Enchantment.ARROW_KNOCKBACK, "Punch", 22, EnchantsType.COMBAT),
    POWER(Enchantment.ARROW_DAMAGE, "Power", 24, EnchantsType.COMBAT),
    KNOCKBACK(Enchantment.KNOCKBACK, "Knockback", 30, EnchantsType.COMBAT),
    LOOTING(Enchantment.LOOT_BONUS_MOBS, "Looting", 32, EnchantsType.COMBAT),
    SMITE(Enchantment.DAMAGE_UNDEAD, "Smite", 38, EnchantsType.COMBAT),
    BANE_OF_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, "Bane of Arthropods", 40, EnchantsType.COMBAT),
    INFINITY(Enchantment.ARROW_INFINITE, "Infinity", 42, EnchantsType.COMBAT),

    // DEFENSE
    PROTECTION(Enchantment.PROTECTION_ENVIRONMENTAL, "Protection", 20, EnchantsType.DEFENSE),
    RESPIRATION(Enchantment.OXYGEN, "Respiration", 22, EnchantsType.DEFENSE),
    FEATHER_FALLING(Enchantment.PROTECTION_FALL, "Feather Falling", 24, EnchantsType.DEFENSE),
    BLAST_PROTECTION(Enchantment.PROTECTION_EXPLOSIONS, "Blast Protection", 30, EnchantsType.DEFENSE),
    PROJECTILE_PROTECTION(Enchantment.PROTECTION_PROJECTILE, "Projectile Protection", 32, EnchantsType.DEFENSE),
    DEPTH_STRIDER(Enchantment.DEPTH_STRIDER, "Depth Strider", 38, EnchantsType.DEFENSE),
    AQUA_AFFINITY(Enchantment.WATER_WORKER, "Aqua Affinity", 40, EnchantsType.DEFENSE),
    THORNS(Enchantment.THORNS, "Thorns", 42, EnchantsType.DEFENSE),

    // UTILS
    EFFICIENCY(Enchantment.DIG_SPEED, "Efficiency", 20, EnchantsType.UTILS),
    UNBREAKING(Enchantment.DURABILITY, "Unbreaking", 22, EnchantsType.UTILS),
    LUCK_OF_THE_SEA(Enchantment.LUCK, "Luck of the sea", 24, EnchantsType.UTILS),
    SILK_TOUCH(Enchantment.SILK_TOUCH, "Silk Touch", 38, EnchantsType.UTILS),
    LURE(Enchantment.LURE, "Lure", 40, EnchantsType.UTILS),
    FORTUNE(Enchantment.LOOT_BONUS_BLOCKS, "Fortune", 42, EnchantsType.UTILS),

    // FIRE
    FIRE_ASPECT(Enchantment.FIRE_ASPECT, "Fire Aspect", 29, EnchantsType.FIRE),
    FLAME(Enchantment.ARROW_FIRE, "Flame", 31, EnchantsType.FIRE),
    FIRE_PROTECTION(Enchantment.PROTECTION_FIRE, "Fire Protection", 33, EnchantsType.FIRE),
    ;

    private final Enchantment enchantment;
    private final String name;
    private final int slot;
    private final EnchantsType type;

    Enchants(Enchantment enchantment, String name, int slot, EnchantsType type) {
        this.enchantment = enchantment;
        this.name = name;
        this.slot = slot;
        this.type = type;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public EnchantsType getType() {
        return type;
    }

    enum EnchantsType {
        COMBAT(new ItemBuilder(Material.DIAMOND_SWORD).setName("Combats").toItemStack(), 18),
        DEFENSE(new ItemBuilder(Material.IRON_CHESTPLATE).setName("Défense").toItemStack(), 36),
        UTILS(new ItemBuilder(Material.IRON_PICKAXE).setName("Utilitaires").toItemStack(), 26),
        FIRE(new ItemBuilder(Material.LAVA_BUCKET).setName("Feu").toItemStack(), 44);

        private final ItemStack item;
        private final int slot;

        EnchantsType(ItemStack item, int slot) {
            this.item = item;
            this.slot = slot;
        }

        public ItemStack getItem() {
            return item;
        }

        public int getSlot() {
            return slot;
        }
    }
}
