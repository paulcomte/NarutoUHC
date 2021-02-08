/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.config;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.config.ConfigLogos;
import fr.rqndomhax.narutouhc.managers.config.HostConfig;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostIcons {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;
    private final HostConfig selected;

    public IHostIcons(Setup setup, Player player, RInventory inventory, HostConfig selected) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;
        this.selected = selected;

        updateInventory();
    }

    private void updateInventory() {

        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);
        IInfos.placeInvBorders(inventory.getInventory());

        inventory.setItem(4, new ItemBuilder(IInfos.HOST_ICONS).setName(selected.getName()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());

        int[] bars = new int[]{3, 5, 48, 50};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        int[] icons = new int[]{19, 20, 21, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
        int n = 0;

        for (ConfigLogos logo : ConfigLogos.values()) {
            if (selected.getLogo().equals(logo))
                inventory.setItem(icons[n], new ItemBuilder(selected.getLogo().getItem().clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), selectLogo(logo));
            else
                inventory.setItem(icons[n], logo.getItem(), selectLogo(logo));
            n++;
        }

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            new IHostEditConfig(setup, player, inventory, selected);
        });
    }

    private Consumer<InventoryClickEvent> selectLogo(ConfigLogos logo) {
        return e -> {
            selected.setLogo(logo);
            new IHostEditConfig(setup, player, inventory, selected);
        };
    }

}