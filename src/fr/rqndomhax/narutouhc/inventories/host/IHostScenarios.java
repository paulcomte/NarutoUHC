/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.entity.Player;

public class IHostScenarios {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostScenarios(Setup setup, Player player, RInventory inventory) {
        this.setup = setup;
        this.player = player;
        this.inventory = inventory;

        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        player.updateInventory();
    }

}