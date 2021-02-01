/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.infos.BorderCenter;
import fr.rqndomhax.narutouhc.infos.Maps;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class MBorder {

    public int defaultSize = 3000;
    public int finalSize = 100;
    public int timeBeforeResize = 30*60;
    public double speed = 2;
    public double damage = 20;
    public BorderCenter center = BorderCenter.KONOHA;

    public void resizeBorder() {

        World world = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
        WorldBorder wb = world.getWorldBorder();

        wb.setCenter(center.getX(), center.getZ());
        wb.setSize(defaultSize);
        wb.setSize(finalSize, (long) ((defaultSize- finalSize)/speed));
        wb.setDamageAmount(damage);
    }

}
