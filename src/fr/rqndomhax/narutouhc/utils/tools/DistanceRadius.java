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
        return firstLocation.distanceSquared(secondLocation);
    }

}
