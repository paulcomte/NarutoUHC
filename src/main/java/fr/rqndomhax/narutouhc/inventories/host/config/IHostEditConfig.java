/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.config;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.config.HostConfig;
import fr.rqndomhax.narutouhc.managers.config.MConfig;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostEditConfig {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;
    private final HostConfig selected;

    public IHostEditConfig(Setup setup, Player player, RInventory inventory, HostConfig selected) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;
        this.selected = selected;

        updateInventory();
    }

    private void updateInventory() {

        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);
        IInfos.placeInvBorders(inventory.getInventory());

        inventory.setItem(4, new ItemBuilder(selected.getLogo().getItem().clone())
                .setName(selected.getName())
                .setLore(ChatColor.GOLD + "ID: " + selected.getFilePath().replaceAll("configs/", "").replaceAll(".cfg", ""))
                .addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1)
                .hideEnchants()
                .toItemStack());

        int[] bars = new int[]{3, 5, 48, 50};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            new IHostConfigs(setup, player, inventory);
        });

        inventory.setItem(20, IInfos.HOST_RENAME);
        inventory.setItem(24, IInfos.HOST_ICONS, updateIcon());
        inventory.setItem(31, IInfos.HOST_DELETE, delete());
    }

    private Consumer<InventoryClickEvent> updateIcon() {
        return e -> {
            new IHostIcons(setup, player, inventory, selected);
        };
    }

    private Consumer<InventoryClickEvent> delete() {
        return e -> {
            MConfig.deleteConfig(selected);
            new IHostConfigs(setup, player, inventory);
        };
    }

}