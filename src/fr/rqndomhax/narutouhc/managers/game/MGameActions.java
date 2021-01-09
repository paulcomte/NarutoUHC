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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class MGameActions {

    public static void teleportPlayers1(int playerDispatchingSize, Set<MPlayer> players) {

        giveNightVision(players);

        List<Location> locations = new ArrayList<>();

        double delta = (2 * Math.PI) / players.size();
        double angle = 0;
        int radius = playerDispatchingSize / 2;

        for (int i = 0 ; i < players.size() ; i++) {
            locations.add(new Location(Bukkit.getWorld(Maps.NO_PVP.name()),
                    radius * Math.sin(angle) + 0.500, 120, radius * Math.cos(angle) + 0.500));
            angle += delta;
            placePlatform(locations.get(i));
        }

        for (MPlayer mPlayer : players) {

            if (locations.get(0) == null) return;
            if (mPlayer == null) continue;

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;

            player.teleport(locations.get(0));
            mPlayer.location = locations.get(0);
            locations.remove(0);

        }
    }

    private static void placePlatform(Location center) {
        World world = center.getWorld();
        for (double z = center.getZ() - 2 ; z < center.getZ() + 3 ; z++) {
            for (double x = center.getX() - 2 ; x < center.getX() + 3 ; x++) {
                Block block = world.getBlockAt(new Location(world, x, center.getY() - 1, z));
                block.setType(Material.STAINED_GLASS);
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

    private static void giveNightVision(Set<MPlayer> players) {
        for (MPlayer mPlayer : players) {

            if (mPlayer == null) continue;
            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0, false, false));

        }
    }

}
