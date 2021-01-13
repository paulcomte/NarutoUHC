/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class MGameActions {

    public static void addKill(MPlayer killer, MPlayer killed) {
        if (killer.kills.size() != 0)
            killer.kills.add(killed.uuid);
    }

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
            MGameBuild.placePlatform(locations.get(i));
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

    private static void giveNightVision(Set<MPlayer> players) {
        for (MPlayer mPlayer : players) {

            if (mPlayer == null) continue;
            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0, false, false));

        }
    }

    public static void sendInfo(MPlayer mPlayer, int i) {

        if (mPlayer == null) return;

        Player player = Bukkit.getPlayer(mPlayer.uuid);
        if (player == null) return;

        player.playNote(player.getLocation(), Instrument.PIANO, Note.flat(1,  Note.Tone.A));
        player.sendTitle(ChatColor.GOLD + String.valueOf(i), "");
    }

    public static void sendInfos(Set<MPlayer> players, int i) {
        for (MPlayer mPlayer : players) {

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;

            if (i != 0) {
                player.playNote(player.getLocation(), Instrument.PIANO, Note.flat(1,  Note.Tone.A));
                player.sendTitle(ChatColor.GOLD + String.valueOf(i), "");
            }
        }
    }

    public static Location teleportToRandomLocation(Setup setup) {
        World world = Bukkit.getWorld(setup.getGame().getGameInfo().getCurrentMap().name());
        Location location = new Location(world, 0, 120, 0);
        return location;
    }

}
