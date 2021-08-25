/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.role.akatsuki.*;
import fr.rqndomhax.narutouhc.role.orochimaru.Kabuto;
import fr.rqndomhax.narutouhc.role.orochimaru.Orochimaru;
import fr.rqndomhax.narutouhc.role.shinobi.*;
import fr.rqndomhax.narutouhc.role.solos.Danzo;
import fr.rqndomhax.narutouhc.role.solos.Madara;
import fr.rqndomhax.narutouhc.role.solos.Sasuke;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Roles {

    MADARA("Madara", Team.MADARA, new ItemBuilder(Material.GOLDEN_APPLE).setName("Madara").toItemStack(), null, Madara.class),
    SASUKE("Sasuke", Team.SASUKE, new ItemBuilder(Material.ENDER_PEARL).setName("Sasuke").toItemStack(), null, Sasuke.class),

    DANZO("Danzo", Team.DANZO, new ItemBuilder(Material.GLOWSTONE).setName("Danzo").toItemStack(), null, Danzo.class),

    DEIDARA("Deidara", Team.AKATSUKI, new ItemBuilder(Material.TNT).setName("Deidara").toItemStack(), null, Deidara.class),
    NAGATO("Nagato", Team.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Nagato").toItemStack(), null, Nagato.class),
    KAKUZU("Kakuzu", Team.AKATSUKI, new ItemBuilder(Material.APPLE).setName("Kakuzu").toItemStack(), null, Kakuzu.class),
    KISAME("Kisame", Team.AKATSUKI, new ItemBuilder(Material.WATER_BUCKET).setName("Kisame").toItemStack(), null, Kisame.class),
    OBITO("Obito", Team.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Obito").toItemStack(), new ItemBuilder(Material.LEATHER).setName(ChatColor.BLACK + "Ninjutsu Spatio-Temporel").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), Obito.class),
    KONAN("Konan", Team.AKATSUKI, new ItemBuilder(Material.PAPER).setName("Konan").toItemStack(), null, Konan.class),
    HIDAN("Hidan", Team.AKATSUKI, new ItemBuilder(Material.BONE).setName("Hidan").toItemStack(), null, Hidan.class),

    NARUTO("Naruto", Team.SHINOBI, new ItemBuilder(Material.FIREBALL).setName("Naruto").toItemStack(), null, Naruto.class),
    KAKASHI_HATAKE("Kakashi", Team.SHINOBI, new ItemBuilder(Material.SPIDER_EYE).setName("Kakashi Hatake").toItemStack(), null, KakashiHatake.class),
    SHIKAMARU("Shikamaru", Team.SHINOBI, new ItemBuilder(Material.STRING).setName("Shikamaru").toItemStack(), null, Shikamaru.class),
    HINATA("Hinata", Team.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 4).setName("Hinata").toItemStack(), null, Hinata.class),
    MINATO("Minato", Team.SHINOBI, new ItemBuilder(Material.RABBIT_FOOT).setName("Minato").toItemStack(), new ItemBuilder(Material.BOW).setName(ChatColor.YELLOW + "Kunaï").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().setDurability((short) 50).toItemStack(), Minato.class),
    GAI("Gaï", Team.SHINOBI, new ItemBuilder(Material.IRON_DOOR).setName("Gaï").toItemStack(), new ItemBuilder(Material.IRON_DOOR).setName(ChatColor.DARK_AQUA + "Huit portes").addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().toItemStack(), Gai.class),
    NEJI("Neji", Team.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 12).setName("Neji").toItemStack(), null, Neji.class),
    SHISUI("Shisui", Team.SHINOBI, new ItemBuilder(Material.REDSTONE).setName("Shisui").toItemStack(), null, Shisui.class),
    SAKURA("Sakura", Team.SHINOBI, new ItemBuilder(Material.RED_ROSE, 1, (byte) 7).setName("Sakura").toItemStack(), null, Sakura.class),
    ITACHI("Itachi", Team.SHINOBI, new ItemBuilder(Material.EYE_OF_ENDER).setName("Itachi").toItemStack(), null, Itachi.class),
    KILLER_BEE("Killer Bee", Team.SHINOBI, new ItemBuilder(Material.DIAMOND_SWORD).setName("Killer bee").toItemStack(), new ItemBuilder(Material.RABBIT_FOOT).setName(ChatColor.YELLOW + "Hachibi").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), KillerBee.class),
    GAARA("Gaara", Team.SHINOBI, new ItemBuilder(Material.SAND).setName("Gaara").toItemStack(), null, Gaara.class),
    FU("Fû", Team.SHINOBI, new ItemBuilder(Material.FEATHER).setName("Fû").toItemStack(), new ItemBuilder(Material.WEB).setName(ChatColor.DARK_GRAY + "Prison de l'araignée").addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().toItemStack(), Fu.class),

    OROCHIMARU("Orochimaru", Team.OROCHIMARU, new ItemBuilder(Material.INK_SACK, 1, (byte) 15).setName("Orochimaru").toItemStack(), null, Orochimaru.class),
    KABUTO("Kabuto", Team.OROCHIMARU, new ItemBuilder(Material.INK_SACK, 1, (byte) 2).setName("Kabuto").toItemStack(), null, Kabuto.class);

    private final String roleName;

    private Team team;

    private final ItemStack item;

    private final ItemStack roleItem;

    private final Class<? extends RoleInfo> roleInfo;

    Roles(String roleName, Team team, ItemStack item, ItemStack roleItem, Class<? extends RoleInfo> roleInfo) {
        this.roleName = roleName;
        this.team = team;
        this.item = item;
        this.roleItem = roleItem;
        this.roleInfo = roleInfo;
    }

    public String getRoleName() {
        return roleName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getRoleItem() {
        return roleItem;
    }

    public static boolean isRoleItem(ItemStack item) {
        if (item == null)
            return false;
        for (Roles role : Roles.values())
            if (item == role.roleItem)
                return true;
        return false;
    }

    public Class<? extends RoleInfo> getRoleInfo() {
        return roleInfo;
    }
}
