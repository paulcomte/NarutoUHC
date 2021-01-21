/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostInventories extends RInventory {

    private final Setup setup;
    private final Player player;

    public IHostInventories(Setup setup, Player player) {
        super(null, "", 9*6);
        this.setup = setup;
        this.player = player;

        IInfos.placeInvBorders(this.getInventory());

        for (int i = 3 ; i <= 5 ; this.setItem(i, IInfos.BARS), i += 2);

        this.setItem(4, IInfos.MAIN_HOST_INVENTORIES);

        this.setItem(30, IInfos.INVENTORIES_HOST_START, getStartInventory());

        this.setItem(31, IInfos.BARS);

        this.setItem(32, IInfos.INVENTORIES_HOST_DEATH, getDeathInventory());

        this.setItem(49, IInfos.RETURN_ITEM, e -> {
            player.closeInventory();
            player.openInventory(new IHost(setup, player).getInventory());
        });
    }

    private Consumer<InventoryClickEvent> getDeathInventory() {
        return e -> {
            player.closeInventory();
            player.openInventory(new IHostDeathInventory(setup, player).getInventory());
        };
    }

    private Consumer<InventoryClickEvent> getStartInventory() {
        return e -> {

            player.closeInventory();
            player.openInventory(new IHostStartInventory(setup, player).getInventory());
        };
    }

}
