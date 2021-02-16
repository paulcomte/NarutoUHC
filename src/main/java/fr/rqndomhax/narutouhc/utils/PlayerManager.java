/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PlayerManager {

    public static void revive(Player player, GamePlayer gamePlayer, ItemStack[] inventory) {
        gamePlayer.isDead = false;
        gamePlayer.isInDead = false;
        player.teleport(MGameActions.teleportToRandomLocation(Bukkit.getWorld(GameInfo.currentMap.name())));
        InventoryManager.giveInventory(inventory, player);
        if (gamePlayer.role != null && gamePlayer.role.getRole() != null)
            gamePlayer.role.giveEffects();
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(Messages.PLAYER_RESURRECTED);
    }

    public static double getRadius(Location firstLocation, Location secondLocation) {
        Location first = firstLocation.clone();
        first.setY(secondLocation.getY());
        return first.distanceSquared(secondLocation);
    }

}
