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
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.title.Title;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

        player.setFoodLevel(20);
        player.setHealth(player.getMaxHealth());

        player.setTotalExperience(0);

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

    public static void teleportPlayers2(Setup setup) {
        
    }

    public static void teleportPlayers1(Setup setup) {

        Set<MPlayer> players = setup.getGame().getGamePlayers();

        giveStartInventory(setup);

        giveNightVision(players);

        List<Location> locations = new ArrayList<>();

        double delta = (2 * Math.PI) / players.size();
        double angle = 0;
        int radius = setup.getGame().getGameInfo().getMRules().playerDispatchingSize / 2;

        for (int i = 0 ; i < players.size() ; i++) {
            locations.add(new Location(Bukkit.getWorld(Maps.NO_PVP.name()),
                    radius * Math.sin(angle) + 0.500, 230, radius * Math.cos(angle) + 0.500));
            angle += delta;
            MGameBuild.placePlatform(locations.get(i));
        }

        for (MPlayer mPlayer : players) {

            if (locations.get(0) == null) return;
            if (mPlayer == null) continue;

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;

            player.teleport(locations.get(0));
            player.setGameMode(GameMode.SURVIVAL);
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

    public static void sendInfos(Set<MPlayer> players, String title, String desc, Instrument instrument, boolean playNote, int octave, Note.Tone tone) {
        for (MPlayer mPlayer : players) {

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;

            new Title(title, desc, 3, 20, 2).send(player);
            if (playNote)
                player.playNote(player.getLocation(), instrument, Note.flat(octave,  tone));
        }
    }

    public static Location teleportToRandomLocation() {
        World world = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
        int x = new Random().nextInt((int) (world.getWorldBorder().getCenter().getX() + world.getWorldBorder().getSize() / 2));
        int z = new Random().nextInt((int) (world.getWorldBorder().getCenter().getZ() + world.getWorldBorder().getSize() / 2));
        return new Location(world, x, world.getHighestBlockYAt(x, z), z);
    }

}
