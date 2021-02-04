/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum BorderCenter {

    KONOHA(500, -1400, new ItemBuilder(Material.REDSTONE_BLOCK).setName("Konoha (500, -1400)").toItemStack()),
    KUMO(2000, -2400, new ItemBuilder(Material.QUARTZ_BLOCK).setName("Kumo (2000, -2400)").toItemStack()),
    AME(-200, -1200, new ItemBuilder(Material.ICE).setName("Ame (-200, -1200)").toItemStack()),
    SUNA(-1000, -200, new ItemBuilder(Material.SAND).setName("Suna (-1000, -200)").toItemStack()),
    KIRI(2100, -700, new ItemBuilder(Material.LAPIS_BLOCK).setName("Kiri (2100, -700)").toItemStack()),
    IWA(-200, -1500, new ItemBuilder(Material.DIRT).setName("Iwa (-200, -1500)").toItemStack()),
    AKATSUKI(-50, 300, new ItemBuilder(Material.COBBLESTONE).setName("Repaire akatsuki (-50, 300)").toItemStack());

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
