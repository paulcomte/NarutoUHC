/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.*;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.listeners.world.ChunkUnloadListener;
import fr.rqndomhax.narutouhc.utils.title.Title;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.Random;
import java.util.Set;

public abstract class MGameActions {

    public static boolean teleportationFinished = false;

    public static void addKill(GamePlayer killer, GamePlayer killed) {
        if (killer == null)
            return;
        if (killed == null)
            return;
        killer.kills.add(killed.uuid);
    }

    public static void clearPlayer(Player player) {
        player.closeInventory();

        InventoryManager.clearInventory(player);

        player.setFoodLevel(30);
        player.setHealth(player.getMaxHealth());

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        player.updateInventory();
    }

    public static void clearPlayerLobby(Player player) {

        clearPlayer(player);

        player.setGameMode(GameMode.ADVENTURE);

        if (GameInfo.gameHost.equals(player.getUniqueId()) || GameInfo.gameCoHost.contains(player.getUniqueId())) {
            player.getInventory().setItem(4, IInfos.MAIN_HOST_ITEM);
            player.updateInventory();
        }

    }

    public static void giveStartInventory(Setup setup) {
        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            Player player = Bukkit.getPlayer(gamePlayer.uuid);
            SActions.giveScenariosEffect(setup.getGame().getGameRules().activatedScenarios, gamePlayer);

            if (player != null)
                InventoryManager.giveInventory(setup.getGame().getGameRules().startInventory, player);
        }
    }

    public static void teleportPlayers(World world, int size, Set<GamePlayer> players, int xCenter, int zCenter, JavaPlugin plugin) {
        new MGenerate(world, size, players, xCenter, zCenter, plugin);
    }

    public static void teleportPlayers2(Setup setup) {

        GameBorder border = setup.getGame().getGameRules().gameBorder;
        teleportPlayers(Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()), border.defaultSize/2, setup.getGame().getGamePlayers(), border.center.getX(), border.center.getZ(), setup.getMain());
    }

    public static void teleportPlayers1(Setup setup) {
        World world = Bukkit.getWorld(Maps.NO_PVP.name());

        teleportPlayers(world, (int) (world.getWorldBorder().getSize()/2), setup.getGame().getGamePlayers(), 0, 0, setup.getMain());
    }

    public static void sendInfo(GamePlayer gamePlayer, int i) {

        if (gamePlayer == null) return;

        Player player = Bukkit.getPlayer(gamePlayer.uuid);
        if (player == null) return;

        player.playNote(player.getLocation(), Instrument.PIANO, Note.flat(1,  Note.Tone.A));
        new Title(ChatColor.GOLD + String.valueOf(i), "", 3, 20, 2).send(player);
    }

    public static void sendInfos(Set<GamePlayer> players, String title, String desc, Instrument instrument, boolean playNote, int octave, Note.Tone tone) {
        for (GamePlayer gamePlayer : players) {

            Player player = Bukkit.getPlayer(gamePlayer.uuid);
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

        int x = (int) ((world.getWorldBorder().getSize()/ 2) - new Random().nextInt((int) (world.getWorldBorder().getSize())) + xcenter);
        int z = (int) ((world.getWorldBorder().getSize() / 2) - new Random().nextInt((int) (world.getWorldBorder().getSize())) + zcenter);

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

    public static void saveChunk(Chunk chunk) {
        for (int x = chunk.getX() - 1; x < chunk.getX() + 1; x++)
            for (int z = chunk.getZ() - 1; z < chunk.getZ() + 1; z++) {
                chunk.load();
                ChunkUnloadListener.keepChunk.add(chunk);
            }
    }

    public static void deleteChunk(Chunk chunk) {
        for (int x = chunk.getX() - 1; x < chunk.getX() + 1; x++)
            for (int z = chunk.getZ() - 1; z < chunk.getZ() + 1; z++)
                ChunkUnloadListener.keepChunk.remove(chunk);
    }

}
