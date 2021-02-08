/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.role.akatsuki.Nagato;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.function.Consumer;

public class INagato extends RInventory {

    private final Setup setup;
    private final Set<GamePlayer> players;
    private final Player player;
    private final Nagato nagato;

    public INagato(Setup setup, Player player, Set<GamePlayer> players, int size, Nagato nagato) {
        super(null, "Résurrection", size);
        this.setup = setup;
        this.player = player;
        this.players = players;
        this.nagato = nagato;

        updateInventory();
    }

    private void updateInventory() {
        int slot = 0;
        for (GamePlayer gamePlayer : players) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null || !p.isOnline())
                continue;

            this.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setName(p.getName()).setSkullOwner(p.getName()).toItemStack(), select(gamePlayer, p));
            slot++;
        }
    }

    private Consumer<InventoryClickEvent> select(GamePlayer selected, Player selectedPlayer) {
        return e -> {
            if (selectedPlayer == null || !selectedPlayer.isOnline()) {
                updateInventory();
                return;
            }
            player.closeInventory();
            nagato.hasUsedCapacity = true;
            // TODO SEND CONFIRMATION MESSAGE
            selected.isDead = false;
            ItemStack[] items = new ItemStack[40];
            items[0] = new ItemBuilder(Material.IRON_SWORD).addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1).toItemStack();
            items[1] = new ItemStack(Material.GOLDEN_APPLE);
            items[2] = new ItemStack(Material.COOKED_BEEF, 64);
            items[36] = new ItemBuilder(Material.IRON_HELMET).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            items[37] = new ItemBuilder(Material.IRON_CHESTPLATE).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            items[38] = new ItemBuilder(Material.IRON_LEGGINGS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            items[39] = new ItemBuilder(Material.IRON_BOOTS).addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack();
            selectedPlayer.teleport(MGameActions.teleportToRandomLocation(Bukkit.getWorld(GameInfo.currentMap.name())));
            InventoryManager.giveInventory(items, selectedPlayer);
            selectedPlayer.setGameMode(GameMode.SURVIVAL);
            selected.role.giveEffects();
            // TODO REVIVE MESSAGE
        };
    }

}
