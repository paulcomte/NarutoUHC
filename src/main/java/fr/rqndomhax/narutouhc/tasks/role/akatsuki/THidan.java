/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.utils.Chrono;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.tools.ActionBar;
import org.bukkit.Bukkit;
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

        if (setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || hidan == null || !hidan.isDead) {
            cancel();
            return;
        }

        Player player = Bukkit.getPlayer(hidan.uuid);
        if (player == null)
            return;

        int current = 10*60 - remainingTime;
        int full = 10*60;

        ActionBar.displayProgressBar("Régénération", Chrono.timeToString(current), current, full, player);

        if (remainingTime == 0) {
            ItemStack[] inventory = new ItemStack[40];
            inventory[0] = new ItemBuilder(Material.IRON_SWORD).addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1).toItemStack();
            inventory[1] = new ItemStack(Material.GOLDEN_APPLE, 5);
            inventory[2] = new ItemStack(Material.COOKED_BEEF, 64);
            inventory[3] = new ItemStack(Material.WATER_BUCKET);
            inventory[4] = new ItemStack(Material.COBBLESTONE, 64);
            inventory[5] = new ItemStack(Material.COBBLESTONE, 64);
            inventory[36] = new ItemBuilder(Material.IRON_HELMET).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            inventory[37] = new ItemBuilder(Material.IRON_CHESTPLATE).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            inventory[38] = new ItemBuilder(Material.IRON_LEGGINGS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            inventory[39] = new ItemBuilder(Material.IRON_BOOTS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            PlayerManager.revive(player, hidan, inventory);
        }
        remainingTime--;
    }

}
