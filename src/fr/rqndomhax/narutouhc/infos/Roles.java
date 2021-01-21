/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.managers.role.shinobi.*;
import fr.rqndomhax.narutouhc.utils.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.PotionBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public enum Roles {

    MADARA(RoleType.SASUKE, new ItemBuilder(Material.GOLDEN_APPLE, 1, (byte) 1).setName("Madara").toItemStack(), Naruto.class),
    SASUKE(RoleType.SASUKE, new ItemBuilder(Material.ENDER_PEARL).setName("Sasuke").toItemStack(), Naruto.class),

    DANZO(RoleType.DANZO, new ItemBuilder(Material.REDSTONE).setName("Danzo").toItemStack(), Naruto.class),

    DEIDARA(RoleType.AKATSUKI, new ItemBuilder(Material.TNT).setName("Deidara").toItemStack(), Naruto.class),
    NAGATO(RoleType.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Nagato").toItemStack(), Naruto.class),
    Kakuzu(RoleType.AKATSUKI, new ItemBuilder(Material.GOLDEN_APPLE).setName("Kakuzu").toItemStack(), Naruto.class),
    KISAME(RoleType.AKATSUKI, new ItemBuilder(Material.WATER_BUCKET).setName("Kisame").toItemStack(), Naruto.class),
    OBITO(RoleType.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Obito").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), Naruto.class),
    KONAN(RoleType.AKATSUKI, new ItemBuilder(Material.PAPER).setName("Konan").toItemStack(), Naruto.class),
    HIDAN(RoleType.AKATSUKI, new ItemBuilder(Material.BONE).setName("Hidan").toItemStack(), Naruto.class),

    NARUTO(RoleType.SHINOBI, new ItemBuilder(Material.FIREBALL).setName("Naruto").toItemStack(), Naruto.class),
    KAKASHI_HATAKE(RoleType.SHINOBI, new ItemBuilder(Material.SPIDER_EYE).setName("Kakashi Hatake").toItemStack(), KakashiHatake.class),
    SHIKAMARU(RoleType.SHINOBI, new ItemBuilder(new PotionBuilder(PotionType.SLOWNESS).setSplash(true).toItemStack()).setName("Shikamaru").toItemStack(), Shikamaru.class),
    HINATA(RoleType.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 12).setName("Hinata").toItemStack(), Hinata.class),
    MINATO(RoleType.SHINOBI, new ItemBuilder(new PotionBuilder(PotionType.SPEED).toItemStack()).setName("Minato").toItemStack(), Minato.class),
    GAI(RoleType.SHINOBI, new ItemBuilder(new PotionBuilder(PotionType.STRENGTH).toItemStack()).setName("Gaï").toItemStack(), Gai.class),
    NEJI(RoleType.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 12).setName("Neji").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), Neji.class),
    SHISUI(RoleType.SHINOBI, new ItemBuilder(Material.EYE_OF_ENDER).setName("Shishui").toItemStack(), Shishui.class),
    SAKURA(RoleType.SHINOBI, new ItemBuilder(Material.RED_ROSE, 1, (byte) 7).setName("Sakura").toItemStack(), Sakura.class),
    ITACHI(RoleType.SHINOBI, new ItemBuilder(Material.EYE_OF_ENDER).setName("Itachi").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), Itachi.class),
    KILLER_BEE(RoleType.SHINOBI, new ItemBuilder(Material.DIAMOND_SWORD).setName("Killer bee").toItemStack(), KillerBee.class),
    GAARA(RoleType.SHINOBI, new ItemBuilder(Material.SAND).setName("Gaara").toItemStack(), Gaara.class),
    FU(RoleType.SHINOBI, new ItemBuilder(Material.FEATHER).setName("Fû").toItemStack(), Fu.class),

    OROCHIMARU(RoleType.OROCHIMARU, new ItemBuilder(new PotionBuilder(PotionType.POISON).toItemStack()).setName("Orochimaru").toItemStack(), Naruto.class),
    KABUTO(RoleType.OROCHIMARU, new ItemBuilder(new PotionBuilder(PotionType.POISON).setSplash(true).toItemStack()).setName("Kabuto").toItemStack(), Naruto.class);

    private final RoleType roleType;
    private final ItemStack item;
    private final Class<? extends RoleInfo> roleInfo;

    Roles(RoleType roleType, ItemStack item, Class<? extends RoleInfo> roleInfo) {
        this.roleType = roleType;
        this.item = item;
        this.roleInfo = roleInfo;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public ItemStack getItem() {
        return item;
    }

    public Class<? extends RoleInfo> getRoleInfo() {
        return roleInfo;
    }
}
