/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.tools.ProgressBar;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MTeleport extends BukkitRunnable {

    final Iterator<GamePlayer> players;
    final List<Location> locations;
    final int size;
    int i = 0;

    public MTeleport(Set<GamePlayer> players, List<Location> locations, JavaPlugin plugin) {
        this.players = players.iterator();
        this.locations = locations;
        size = players.size();
        runTaskTimer(plugin, 0, 5);
    }

    @Override
    public void run() {
        if (!players.hasNext() || size - i == 0) {
            cancel();
            MGameActions.teleportationFinished = true;
            return;
        }

        GamePlayer gamePlayer = players.next();
        Location location = locations.get(0);

        if (locations.get(0) == null) return;

        if (gamePlayer == null)
            return;

        gamePlayer.location = location;

        Player player = Bukkit.getPlayer(gamePlayer.uuid);

        if (player == null)
            MVillagers.createVillager(location, gamePlayer);
        else {

            player.teleport(locations.get(0));

            player.setGameMode(GameMode.SURVIVAL);

            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
        }

        MGameActions.deleteChunk(locations.get(0).getChunk());

        locations.remove(location);

        i++;

        ProgressBar.displayProgressBar("Téléportation", (i * 100) / size + "%", i, size);
    }

}
