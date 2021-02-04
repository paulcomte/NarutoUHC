/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Villager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

public abstract class MVillagers {

    public static HashMap<Villager, GamePlayer> disconnectedPlayers = new HashMap<>();

    public static void createVillager(Location location, GamePlayer player) {

        if (location == null || player == null || player.uuid == null)
            return;

        if (disconnectedPlayers.containsValue(player))
            return;

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.uuid);

        if (offlinePlayer == null)
            return;

        Villager npc = location.getWorld().spawn(location, Villager.class);

        if (npc == null) {
            Bukkit.getLogger().log(Level.WARNING, "");
            return;
        }

        npc.setCustomName(offlinePlayer.getName());
        npc.setBreed(false);
        npc.setCustomNameVisible(true);
        npc.setProfession(Villager.Profession.LIBRARIAN);
        npc.setMaxHealth(30);
        npc.setHealth(30);
        npc.setCanPickupItems(false);
        npc.setRemoveWhenFarAway(false);
        npc.setAgeLock(true);

        disconnectedPlayers.put(npc, player);
    }

    public static void onVillagerKill(Location deathLocation, Villager villager) {
        if (deathLocation == null || villager == null)
            return;

        GamePlayer player = disconnectedPlayers.getOrDefault(villager, null);
        if (player == null)
            return;

        player.deathLocation = deathLocation;
        player.isDead = true;
        InventoryManager.dropInventory(player.inventory, deathLocation, true);
    }

    public static void deleteVillager(GamePlayer player) {
        Optional<Villager> villager = disconnectedPlayers.entrySet().stream().filter(entry -> entry.getValue().equals(player)).map(Map.Entry::getKey).findFirst();
        villager.ifPresent(MVillagers::deleteVillager);
    }
    public static void deleteVillager(Villager villager) {
        if (villager == null)
            return;

        disconnectedPlayers.remove(villager);
        villager.remove();
    }

}
