/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.MBuilder;
import fr.rqndomhax.narutouhc.infos.Maps;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class MGameBuild {

    public static boolean hasBuilt = true;

    public static void placePlatform(Location center) {
        byte color = (byte) (new Random().nextInt(14) + 1);
        World world = center.getWorld();
        for (double z = center.getZ() - 2 ; z < center.getZ() + 3 ; z++) {
            for (double x = center.getX() - 2 ; x < center.getX() + 3 ; x++) {
                Block block = world.getBlockAt(new Location(world, x, center.getY() - 1, z));
                block.setType(Material.STAINED_GLASS);
                block.setData(color);
                block.getState().update();
            }
        }
    }

    public static void removePlatform(List<GamePlayer> players) {

        for (GamePlayer gamePlayer : players) {
            if (gamePlayer == null) continue;

            Location center = gamePlayer.location;

            World world = center.getWorld();
            for (double z = center.getZ() - 2 ; z < center.getZ() + 3 ; z++) {
                for (double x = center.getX() - 2 ; x < center.getX() + 3 ; x++) {
                    Block block = world.getBlockAt(new Location(world, x, center.getY() - 1, z));
                    block.setType(Material.AIR);
                    block.getState().update();
                }
            }

            Player player = Bukkit.getPlayer(gamePlayer.uuid);
            if (player == null) continue;
            player.playSound(player.getLocation(), Sound.GLASS, 1, 1);
        }
    }

    public static void placeLobby(JavaPlugin plugin) {
        setCage(Material.STAINED_GLASS, 19, new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 230, 0), true, false, 235, plugin);
    }

    public static void removeLobby(JavaPlugin plugin) {
        setCage(Material.AIR, 19, new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 230, 0), false, false, 235, plugin);
    }

    private static void setCage(Material material, int boundaries, Location center, boolean updateData, boolean hasRoof, int maxY, JavaPlugin plugin) {
        hasBuilt = false;
        new MBuilder(material, boundaries, center, updateData, hasRoof, maxY, plugin);
    }

}
