/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.border;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.BorderCenter;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostBorderCenter {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostBorderCenter(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int n = 0;
        int[] bars = new int[]{3, 5, 48, 50};
        int[] centers = new int[]{20, 22, 24, 28, 30, 32, 34};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.HOST_BORDER_CENTER.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(inventory.getInventory());

        for (BorderCenter center : BorderCenter.values()) {
            if (setup.getGame().getGameInfo().getMRules().mBorder.center == center)
                inventory.setItem(centers[n], new ItemBuilder(center.getItem().clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateCenter(center));
            else
                inventory.setItem(centers[n], center.getItem(), updateCenter(center));
            n++;
        }

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            MRules rules = setup.getGame().getGameInfo().getMRules();
            if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId()))
                new IHostBorder(setup, player, inventory);
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updateCenter(BorderCenter center) {
        return e -> {
            setup.getGame().getGameInfo().getMRules().mBorder.center = center;
            updateInventory();
        };
    }

}
