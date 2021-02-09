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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Roles {

    MADARA("Madara", Team.MADARA, new ItemBuilder(Material.GOLDEN_APPLE).setName("Madara").toItemStack(), Madara.class),
    SASUKE("Sasuke", Team.SASUKE, new ItemBuilder(Material.ENDER_PEARL).setName("Sasuke").toItemStack(), Sasuke.class),

    DANZO("Danzo", Team.DANZO, new ItemBuilder(Material.GLOWSTONE).setName("Danzo").toItemStack(), Danzo.class),

    DEIDARA("Deidara", Team.AKATSUKI, new ItemBuilder(Material.TNT).setName("Deidara").toItemStack(), Deidara.class),
    NAGATO("Nagato", Team.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Nagato").toItemStack(), Nagato.class),
    KAKUZU("Kakuzu", Team.AKATSUKI, new ItemBuilder(Material.APPLE).setName("Kakuzu").toItemStack(), Kakuzu.class),
    KISAME("Kisame", Team.AKATSUKI, new ItemBuilder(Material.WATER_BUCKET).setName("Kisame").toItemStack(), Kisame.class),
    //OBITO("Obito", Team.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Obito").toItemStack(), Obito.class),
    KONAN("Konan", Team.AKATSUKI, new ItemBuilder(Material.PAPER).setName("Konan").toItemStack(), Konan.class),
    HIDAN("Hidan", Team.AKATSUKI, new ItemBuilder(Material.BONE).setName("Hidan").toItemStack(), Hidan.class),

    NARUTO("Naruto", Team.SHINOBI, new ItemBuilder(Material.FIREBALL).setName("Naruto").toItemStack(), Naruto.class),
    KAKASHI_HATAKE("Kakashi", Team.SHINOBI, new ItemBuilder(Material.SPIDER_EYE).setName("Kakashi Hatake").toItemStack(), KakashiHatake.class),
    SHIKAMARU("Shikamaru", Team.SHINOBI, new ItemBuilder(Material.STRING).setName("Shikamaru").toItemStack(), Shikamaru.class),
    HINATA("Hinata", Team.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 4).setName("Hinata").toItemStack(), Hinata.class),
    MINATO("Minato", Team.SHINOBI, new ItemBuilder(Material.RABBIT_FOOT).setName("Minato").toItemStack(), Minato.class),
    GAI("Gaï", Team.SHINOBI, new ItemBuilder(Material.IRON_DOOR).setName("Gaï").toItemStack(), Gai.class),
    NEJI("Neji", Team.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 12).setName("Neji").toItemStack(), Neji.class),
    SHISUI("Shisui", Team.SHINOBI, new ItemBuilder(Material.REDSTONE).setName("Shishui").toItemStack(), Shisui.class),
    SAKURA("Sakura", Team.SHINOBI, new ItemBuilder(Material.RED_ROSE, 1, (byte) 7).setName("Sakura").toItemStack(), Sakura.class),
    ITACHI("Itachi", Team.SHINOBI, new ItemBuilder(Material.EYE_OF_ENDER).setName("Itachi").toItemStack(), Itachi.class),
    KILLER_BEE("Killer Bee", Team.SHINOBI, new ItemBuilder(Material.DIAMOND_SWORD).setName("Killer bee").toItemStack(), KillerBee.class),
    GAARA("Gaara", Team.SHINOBI, new ItemBuilder(Material.SAND).setName("Gaara").toItemStack(), Gaara.class),
    FU("Fû", Team.SHINOBI, new ItemBuilder(Material.FEATHER).setName("Fû").toItemStack(), Fu.class),

    OROCHIMARU("Orochimaru", Team.OROCHIMARU, new ItemBuilder(Material.INK_SACK, 1, (byte) 15).setName("Orochimaru").toItemStack(), Orochimaru.class),
    KABUTO("Kabuto", Team.OROCHIMARU, new ItemBuilder(Material.INK_SACK, 1, (byte) 2).setName("Kabuto").toItemStack(), Kabuto.class);

    private final String roleName;

    private Team team;

    private final ItemStack item;

    private final Class<? extends RoleInfo> roleInfo;

    Roles(String roleName, Team team, ItemStack item, Class<? extends RoleInfo> roleInfo) {
        this.roleName = roleName;
        this.team = team;
        this.item = item;
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

    public Class<? extends RoleInfo> getRoleInfo() {
        return roleInfo;
    }
}
