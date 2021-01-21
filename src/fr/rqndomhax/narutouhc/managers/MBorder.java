/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.infos.Maps;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class MBorder {

    public int defaultSize = 3000;
    public int maxSize = 100;
    public int timeBeforeResize = 6;
    public double speed = 2;
    public int damage = 20;
    public int xCenter = 0;
    public int zCenter = 0;

    public void resizeBorder() {

        World world = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
        WorldBorder wb = world.getWorldBorder();

        wb.setCenter(xCenter, zCenter);
        wb.setSize(defaultSize);
        wb.setSize(maxSize, (long) ((defaultSize-maxSize)/speed));
        wb.setDamageAmount(damage);
    }

}
