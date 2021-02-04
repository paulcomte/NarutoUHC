/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.world;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.rules.Difficulty;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostWorldDifficulty {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostWorldDifficulty(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        IInfos.placeInvBorders(inventory.getInventory());

        int[] bars = new int[]{3, 5, 48, 50};
        int[] difficulties = new int[]{13, 29, 31, 33};
        int n = 0;

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.WORLD_HOST_DIFFICULTY).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());

        for (Difficulty difficulty : Difficulty.values()) {
            StringBuilder description = new StringBuilder();
            ItemBuilder item = new ItemBuilder(difficulty.getItem().clone());

            if (Bukkit.getWorld(Maps.NO_PVP.name()).getDifficulty().equals(difficulty.getDifficulty())) {
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
                description.append(ChatColor.LIGHT_PURPLE + "➤ ");
            }

            description.append(difficulty.getDescription());
            inventory.setItem(difficulties[n], item.setName(description.toString()).toItemStack(), updateWorldDifficulty(difficulty));
            n++;
        }

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            new IHostWorld(setup, player, inventory);
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updateWorldDifficulty(Difficulty difficulty) {
        return e -> {
            Bukkit.getWorld(Maps.NO_PVP.name()).setDifficulty(difficulty.getDifficulty());
            Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()).setDifficulty(difficulty.getDifficulty());
            updateInventory();
        };
    }
}
