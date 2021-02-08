/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.tools;

import org.bukkit.Location;

public abstract class DistanceRadius {

    public static double getRadius(Location firstLocation, Location secondLocation) {
        Location first = firstLocation.clone();
        first.setY(secondLocation.getY());
        return first.distanceSquared(secondLocation);
    }

}
