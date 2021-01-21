/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.Set;

public abstract class MGameBuild {

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

    public static void removePlatform(Set<MPlayer> players) {

        for (MPlayer mPlayer : players) {
            if (mPlayer == null) continue;

            Location center = mPlayer.location;

            World world = center.getWorld();
            for (double z = center.getZ() - 2 ; z < center.getZ() + 3 ; z++) {
                for (double x = center.getX() - 2 ; x < center.getX() + 3 ; x++) {
                    Block block = world.getBlockAt(new Location(world, x, center.getY() - 1, z));
                    block.setType(Material.AIR);
                    block.getState().update();
                }
            }

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;
            player.playSound(player.getLocation(), Sound.GLASS, 1, 1);
        }
    }

    public static void setCage(Block newBlock, int boundaries, int maxY, Location center) {

        World world = center.getWorld();

        for (double z = center.getZ() - boundaries ; z <= center.getZ() + boundaries ; z++) {
            for (double x = center.getX() - boundaries ; x <= center.getX() + boundaries ; x++) {
                if (z == center.getZ() - boundaries || z == center.getZ() + boundaries
                        || x == center.getX() - boundaries || x == center.getX() + boundaries)
                    for (int y = (int) center.getY() ; y < maxY ; y++) {
                        Block block = world.getBlockAt(new Location(world, x, y, z));
                        block.setType(newBlock.getType());
                        block.setData((byte) (new Random().nextInt(14) + 1));
                        block.getState().update();
                    }
                Block block = world.getBlockAt(new Location(world, x, center.getY() - 1, z));
                block.setType(newBlock.getType());
                block.setData((byte) (new Random().nextInt(14) + 1));
                block.getState().update();
            }
        }
    }

    public static void placeLobby() {

        World world = Bukkit.getWorld(Maps.NO_PVP.name());
        int boundaries = 19;
        byte color = (byte) (new Random().nextInt(14) + 1);
        Location center = new Location(world, 0, 230, 0);

        Block block = world.getBlockAt(center);
        block.setType(Material.STAINED_GLASS);
        block.setData((byte) (new Random().nextInt(14) + 1));
        setCage(block, boundaries, 235, center);
        block.setType(Material.AIR);
        block.getState().update();
    }

    public static void removeLobby() {

        World world = Bukkit.getWorld(Maps.NO_PVP.name());
        int boundaries = 19;
        Location center = new Location(world, 0, 230, 0);

        Block block = world.getBlockAt(center);
        block.setType(Material.AIR);
        setCage(block, boundaries, 235, center);
    }

}
