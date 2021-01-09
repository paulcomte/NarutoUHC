/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class MBorder {

    public int defaultSize = 3000;
    public int maxSize = 100;
    public int timeBeforeResize = 600;
    public int speed = 2;
    public int damage = 1;
    public int xCenter = 0;
    public int yCenter = 0;

    public void resizeBorder(Setup setup) {

        World world = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
        WorldBorder wb = world.getWorldBorder();

        wb.setCenter(xCenter, yCenter);
        wb.setSize(defaultSize);
        wb.setDamageAmount(damage);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (wb.getSize() == maxSize) {
                    wb.setDamageAmount(0);
                    cancel();
                }
                wb.setSize(wb.getSize() - speed);
            }
        }.runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

}
