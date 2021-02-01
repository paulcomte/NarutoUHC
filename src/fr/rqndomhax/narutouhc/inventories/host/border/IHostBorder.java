/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.border;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class IHostBorder {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostBorder(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int[] bars = new int[]{3, 5, 48, 50};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.MAIN_HOST_BORDER_CONFIG.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(inventory.getInventory());

        inventory.setItem(13, IInfos.HOST_BORDER_CENTER, e -> {
            new IHostBorderCenter(setup, player, inventory);
        });

        inventory.setItem(29, IInfos.HOST_BORDER_ACTIVATION, e -> {
            new IHostBorderActivation(setup, player, inventory);
        });

        inventory.setItem(31, IInfos.HOST_BORDER_SIZE, e -> {
            new IHostBorderSize(setup, player, inventory);
        });

        inventory.setItem(33, IInfos.HOST_BORDER_SPEED_AND_DAMAGE, e -> {
            new IHostBorderStats(setup, player, inventory);
        });

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            MRules rules = setup.getGame().getGameInfo().getMRules();
            player.closeInventory();
            if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId()))
                player.openInventory(new IHost(setup, player).getInventory());
        });

        player.updateInventory();
    }

}
