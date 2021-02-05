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

    MADARA(Team.MADARA, new ItemBuilder(Material.GOLDEN_APPLE).setName("Madara").toItemStack(), Madara.class),
    SASUKE(Team.SASUKE, new ItemBuilder(Material.ENDER_PEARL).setName("Sasuke").toItemStack(), Sasuke.class),

    DANZO(Team.DANZO, new ItemBuilder(Material.GLOWSTONE).setName("Danzo").toItemStack(), Danzo.class),

    DEIDARA(Team.AKATSUKI, new ItemBuilder(Material.TNT).setName("Deidara").toItemStack(), Deidara.class),
    NAGATO(Team.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Nagato").toItemStack(), Nagato.class),
    KAKUZU(Team.AKATSUKI, new ItemBuilder(Material.APPLE).setName("Kakuzu").toItemStack(), Kakuzu.class),
    KISAME(Team.AKATSUKI, new ItemBuilder(Material.WATER_BUCKET).setName("Kisame").toItemStack(), Kisame.class),
    OBITO(Team.AKATSUKI, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("Obito").toItemStack(), Obito.class),
    KONAN(Team.AKATSUKI, new ItemBuilder(Material.PAPER).setName("Konan").toItemStack(), Konan.class),
    HIDAN(Team.AKATSUKI, new ItemBuilder(Material.BONE).setName("Hidan").toItemStack(), Hidan.class),

    NARUTO(Team.SHINOBI, new ItemBuilder(Material.FIREBALL).setName("Naruto").toItemStack(), Naruto.class),
    KAKASHI_HATAKE(Team.SHINOBI, new ItemBuilder(Material.SPIDER_EYE).setName("Kakashi Hatake").toItemStack(), KakashiHatake.class),
    SHIKAMARU(Team.SHINOBI, new ItemBuilder(Material.STRING).setName("Shikamaru").toItemStack(), Shikamaru.class),
    HINATA(Team.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 4).setName("Hinata").toItemStack(), Hinata.class),
    MINATO(Team.SHINOBI, new ItemBuilder(Material.RABBIT_FOOT).setName("Minato").toItemStack(), Minato.class),
    GAI(Team.SHINOBI, new ItemBuilder(Material.IRON_DOOR).setName("Gaï").toItemStack(), Gai.class),
    NEJI(Team.SHINOBI, new ItemBuilder(Material.INK_SACK, 1, (byte) 12).setName("Neji").toItemStack(), Neji.class),
    SHISUI(Team.SHINOBI, new ItemBuilder(Material.REDSTONE).setName("Shishui").toItemStack(), Shishui.class),
    SAKURA(Team.SHINOBI, new ItemBuilder(Material.RED_ROSE, 1, (byte) 7).setName("Sakura").toItemStack(), Sakura.class),
    ITACHI(Team.SHINOBI, new ItemBuilder(Material.EYE_OF_ENDER).setName("Itachi").toItemStack(), Itachi.class),
    KILLER_BEE(Team.SHINOBI, new ItemBuilder(Material.DIAMOND_SWORD).setName("Killer bee").toItemStack(), KillerBee.class),
    GAARA(Team.SHINOBI, new ItemBuilder(Material.SAND).setName("Gaara").toItemStack(), Gaara.class),
    FU(Team.SHINOBI, new ItemBuilder(Material.FEATHER).setName("Fû").toItemStack(), Fu.class),

    OROCHIMARU(Team.OROCHIMARU, new ItemBuilder(Material.INK_SACK, 1, (byte) 15).setName("Orochimaru").toItemStack(), Orochimaru.class),
    KABUTO(Team.OROCHIMARU, new ItemBuilder(Material.INK_SACK, 1, (byte) 2).setName("Kabuto").toItemStack(), Kabuto.class);

    private Team team;
    private final ItemStack item;
    private final Class<? extends RoleInfo> roleInfo;

    Roles(Team team, ItemStack item, Class<? extends RoleInfo> roleInfo) {
        this.team = team;
        this.item = item;
        this.roleInfo = roleInfo;
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
