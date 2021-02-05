/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class THidan extends BukkitRunnable {

    private int remainingTime = 10*60;
    private final Setup setup;
    private final GamePlayer hidan;

    public THidan(Setup setup, GamePlayer hidan) {
        this.setup = setup;
        this.hidan = hidan;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || hidan == null || !hidan.isDead) {
            cancel();
            return;
        }

        if (remainingTime == 0) {
            Player player = Bukkit.getPlayer(hidan.uuid);
            if (player == null) {
                cancel();
                return;
            }
            ItemStack[] items = new ItemStack[40];
            items[0] = new ItemBuilder(Material.IRON_SWORD).addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1).toItemStack();
            items[1] = new ItemStack(Material.GOLDEN_APPLE, 5);
            items[2] = new ItemStack(Material.COOKED_BEEF, 64);
            items[3] = new ItemStack(Material.COBBLESTONE, 64);
            items[4] = new ItemStack(Material.COBBLESTONE, 64);
            items[36] = new ItemBuilder(Material.IRON_HELMET).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            items[37] = new ItemBuilder(Material.IRON_CHESTPLATE).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            items[38] = new ItemBuilder(Material.IRON_LEGGINGS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            items[39] = new ItemBuilder(Material.IRON_BOOTS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            hidan.isDead = false;
            InventoryManager.giveInventory(items, player);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(MGameActions.teleportToRandomLocation(Bukkit.getWorld(setup.getGame().getGameRules().currentMap.name())));

        }

        remainingTime--;
    }

}
