/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.inventory;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostInventories {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostInventories(Setup setup, Player player, RInventory inventory) {
        this.setup = setup;
        this.player = player;
        this.inventory = inventory;

        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        IInfos.placeInvBorders(inventory.getInventory());

        for (int i = 3 ; i <= 5 ; inventory.setItem(i, IInfos.BARS), i += 2);

        inventory.setItem(4, new ItemBuilder(IInfos.MAIN_HOST_INVENTORIES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());

        inventory.setItem(30, IInfos.INVENTORIES_HOST_BEGINNING, getStartInventory());

        inventory.setItem(31, IInfos.BARS);

        inventory.setItem(32, IInfos.INVENTORIES_HOST_DEATH, getDeathInventory());

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            GameRules rules = setup.getGame().getGameRules();
            player.closeInventory();
            if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId()))
                player.openInventory(new IHost(setup, player).getInventory());
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> getDeathInventory() {
        return e -> {
            new IHostDeathInventory(setup, player, inventory);
        };
    }

    private Consumer<InventoryClickEvent> getStartInventory() {
        return e -> {
            new IHostStartInventory(setup, player, inventory);
        };
    }

}
