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

public class IHostScenarios extends RInventory {

    private final Setup setup;
    private final Player player;

    public IHostScenarios(Setup setup, Player player) {
        super(null, "", 9*6);
        this.setup = setup;
        this.player = player;
    }

}
