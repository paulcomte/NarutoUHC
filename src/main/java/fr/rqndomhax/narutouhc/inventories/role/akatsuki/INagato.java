/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.akatsuki.Nagato;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
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
            player.sendMessage(Messages.PREFIX + "Vous avez ressuscité " + selectedPlayer.getName());
            player.playSound(player.getLocation(), "mob.wither.shoot", 2f,  1.8f);
            nagato.hasUsedCapacity = true;
            ItemStack[] inventory = selected.inventory.clone();
            int firstGoldenApple = -1;
            int firstEmptySlot = -1;
            for(int slot = 0; slot < 36; slot++){
                // Check if slot is empty
                if (inventory[slot] == null) {
                    // Check if it's the firstEmptySlot
                    if (firstEmptySlot == -1)
                        firstEmptySlot = slot;
                    continue;
                }
                // Check if player has slot for golden apple
                ItemStack item = inventory[slot];
                if (item.getType().equals(Material.GOLDEN_APPLE)) {
                    if (firstGoldenApple == -1)
                        firstGoldenApple = slot;
                    inventory[slot] = null;
                }
            }
            if (firstGoldenApple != -1)
                inventory[firstGoldenApple] = new ItemStack(Material.GOLDEN_APPLE, 2);
            else if (firstEmptySlot != -1)
                inventory[firstEmptySlot] = new ItemStack(Material.GOLDEN_APPLE, 2);

            PlayerManager.revive(setup, selectedPlayer, selected, inventory);
        };
    }

}
