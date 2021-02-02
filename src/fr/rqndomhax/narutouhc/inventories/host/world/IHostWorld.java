/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.world;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.entity.Player;

public class IHostWorld {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostWorld(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        IInfos.placeInvBorders(inventory.getInventory());

        inventory.setItem(22, IInfos.WORLD_HOST_DROPS, e -> {
            new IHostWorldDrops(setup, player, inventory);
        });

        inventory.setItem(29, IInfos.WORLD_HOST_DAY_CYCLE, e -> {
            new IHostWorldDayCycle(setup, player, inventory);
        });

        inventory.setItem(33, IInfos.WORLD_HOST_DIFFICULTY, e -> {
            new IHostWorldDifficulty(setup, player, inventory);
        });

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            player.closeInventory();
            player.openInventory(new IHost(setup, player).getInventory());
        });

        player.updateInventory();
    }
}
