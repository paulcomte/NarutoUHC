/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.utils.tools.ProgressBar;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MGenerate extends BukkitRunnable {

    final List<Location> locations = new ArrayList<>();
    final Set<GamePlayer> players;
    final JavaPlugin plugin;
    final World world;
    double delta;
    double angle = 0;
    int radius;
    final int xCenter;
    final int zCenter;
    int i = 0;

    public MGenerate(World world, int size, Set<GamePlayer> players, int xCenter, int zCenter, JavaPlugin plugin) {
        this.world = world;
        this.players = players;
        this.xCenter = xCenter;
        this.zCenter = zCenter;
        this.plugin = plugin;
        delta = (2 * Math.PI) / players.size();
        radius = size / 2;
        runTaskTimer(plugin, 0, 5);
    }

    @Override
    public void run() {

        if (players.size() == i) {
            new MTeleport(players, locations, plugin);
            cancel();
            return;
        }

        locations.add(new Location(world,
                xCenter + (radius * Math.sin(angle) + 0.500), 230, zCenter + (radius * Math.cos(angle) + 0.500)));
        angle += delta;
        MGameActions.saveChunk(locations.get(0).getChunk());
        MGameBuild.placePlatform(locations.get(i));
        ProgressBar.displayProgressBar("Génération", (i * 100) / players.size() + "%", i, players.size());
        i++;
    }
}
