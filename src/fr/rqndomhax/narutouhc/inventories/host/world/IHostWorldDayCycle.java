/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.world;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostWorldDayCycle {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostWorldDayCycle(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        IInfos.placeInvBorders(inventory.getInventory());

        int[] bars = new int[]{3, 5, 48, 50};
        int[] cycles = new int[]{22, 29, 33};
        int n = 0;

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.WORLD_HOST_DAY_CYCLE).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());

        for (DayCycle dayCycle : DayCycle.values()) {
            StringBuilder description = new StringBuilder();
            ItemBuilder item = new ItemBuilder(dayCycle.getItem().clone());

            if (setup.getGame().getGameInfo().getMRules().dayCycle.equals(dayCycle)) {
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
                description.append(ChatColor.LIGHT_PURPLE + "➤ ");
            }

            description.append(dayCycle.getDescription());
            inventory.setItem(cycles[n], item.setName(description.toString()).toItemStack(), updateDayCycle(dayCycle));
            n++;
        }

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            new IHostWorld(setup, player, inventory);
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updateDayCycle(DayCycle cycle) {
        return e -> {
            setup.getGame().getGameInfo().getMRules().dayCycle = cycle;
            updateInventory();
        };
    }

}
