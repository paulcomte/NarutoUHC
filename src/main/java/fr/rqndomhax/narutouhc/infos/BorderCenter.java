/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum BorderCenter {

    KONOHA(500, -1226, new ItemBuilder(Material.REDSTONE_BLOCK).setName("Konoha (500, -1226)").toItemStack()),
    KUMO(2025, -2550, new ItemBuilder(Material.QUARTZ_BLOCK).setName("Kumo (2025,, -2550)").toItemStack()),
    AME(-284, -1185, new ItemBuilder(Material.ICE).setName("Ame (-284, -1185)").toItemStack()),
    SUNA(-959, -425, new ItemBuilder(Material.SAND).setName("Suna (-959, -425)").toItemStack()),
    KIRI(2140, -800, new ItemBuilder(Material.LAPIS_BLOCK).setName("Kiri (2140, -800)").toItemStack()),
    IWA(-300, -1740, new ItemBuilder(Material.DIRT).setName("Iwa (-300, -1740)").toItemStack()),
    AKATSUKI(-114, 289, new ItemBuilder(Material.COBBLESTONE).setName("Repaire akatsuki (-114, 289)").toItemStack());

    private final int x;
    private final int z;
    private final ItemStack item;

    BorderCenter(int x, int z, ItemStack item) {
        this.x = x;
        this.z = z;
        this.item = item;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public ItemStack getItem() {
        return item;
    }
}
