/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.title.Title;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class MGameActions {

    public static void addKill(MPlayer killer, MPlayer killed) {
        killer.kills.add(killed.uuid);
    }

    public static void clearPlayer(Player player) {
        player.closeInventory();

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        player.setFoodLevel(30);
        player.setHealth(player.getMaxHealth());

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        player.updateInventory();
    }

    public static void clearPlayerLobby(Setup setup, Player player) {
        clearPlayer(player);
        player.setGameMode(GameMode.ADVENTURE);
        MRules rules = setup.getGame().getGameInfo().getMRules();
        if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId())) {
            player.getInventory().setItem(4, IInfos.MAIN_HOST_ITEM);
            player.updateInventory();
        }
        else {
            if (rules.startInventoryInEdit != null && rules.startInventoryInEdit.equals(player.getUniqueId()))
                rules.startInventoryInEdit = null;
            if (rules.startInventoryInEdit != null && rules.deathInventoryInEdit.equals(player.getUniqueId()))
                rules.deathInventory = null;
        }
    }

    public static void giveStartInventory(Setup setup) {
        for (MPlayer mPlayer : setup.getGame().getGamePlayers()) {
            Player player = Bukkit.getPlayer(mPlayer.uuid);

            if (player != null)
                InventoryManager.giveInventory(setup.getGame().getGameInfo().getMRules().startInventory, player);
        }
    }

    public static void teleportPlayers(World world, int size, Set<MPlayer> players, int xCenter, int zCenter) {
        List<Location> locations = new ArrayList<>();

        double delta = (2 * Math.PI) / players.size();
        double angle = 0;
        int radius = size / 2;

        for (int i = 0 ; i < players.size() ; i++) {
            locations.add(new Location(world,
                    xCenter + (radius * Math.sin(angle) + 0.500), 230, zCenter + (radius * Math.cos(angle) + 0.500)));
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
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    public static void teleportPlayers2(Setup setup) {

        MBorder border = setup.getGame().getGameInfo().getMRules().mBorder;
        teleportPlayers(Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()), border.defaultSize/2, setup.getGame().getGamePlayers(), border.center.getX(), border.center.getZ());

    }

    public static void teleportPlayers1(Setup setup) {

        World world = Bukkit.getWorld(Maps.NO_PVP.name());

        teleportPlayers(world, (int) (world.getWorldBorder().getSize()/2), setup.getGame().getGamePlayers(), 0, 0);
    }

    public static void sendInfo(MPlayer mPlayer, int i) {

        if (mPlayer == null) return;

        Player player = Bukkit.getPlayer(mPlayer.uuid);
        if (player == null) return;

        player.playNote(player.getLocation(), Instrument.PIANO, Note.flat(1,  Note.Tone.A));
        new Title(ChatColor.GOLD + String.valueOf(i), "", 3, 20, 2).send(player);
    }

    public static void sendInfos(Set<MPlayer> players, String title, String desc, Instrument instrument, boolean playNote, int octave, Note.Tone tone) {
        for (MPlayer mPlayer : players) {

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;

            new Title(title, desc, 3, 20, 2).send(player);
            if (playNote)
                player.playNote(player.getLocation(), instrument, Note.flat(octave,  tone));
        }
    }

    public static Location teleportToRandomLocation(World world) {
        return teleportToRandomLocation(world, 0);
    }

        public static Location teleportToRandomLocation(World world, int i) {
        int xcenter = (int) world.getWorldBorder().getCenter().getX();
        int zcenter = (int) world.getWorldBorder().getCenter().getZ();

        int x = new Random().nextInt((int) (world.getWorldBorder().getSize() / 2)) - xcenter;
        int z = new Random().nextInt((int) (world.getWorldBorder().getSize() / 2)) - zcenter;


        Location location = new Location(world, x, world.getHighestBlockYAt(x, z), z);
        if (i == 100)
            return location;
        if (location.getBlock() == null)
            return teleportToRandomLocation(world, ++i);

        Material type = location.getBlock().getType();
        if (type == null || (!type.equals(Material.GRASS)
                && !type.equals(Material.DIRT)
                && !type.equals(Material.STONE)
                && !type.equals(Material.LEAVES)
                && !type.equals(Material.LEAVES_2)
                && !type.equals(Material.SAND)))
            return teleportToRandomLocation(world, ++i);
        return location;
    }

}
