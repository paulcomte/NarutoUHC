/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.roles.IHostRoles;
import fr.rqndomhax.narutouhc.inventories.host.roles.IHostRolesPage;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHost extends RInventory {

    private final Setup setup;
    private final Player player;

    public IHost(Setup setup, Player player) {
        super(null, IInfos.MAIN_HOST_NAME, 9*6);
        this.setup = setup;
        this.player = player;

        IInfos.placeInvBorders(this.getInventory());
        this.setItem(4, IInfos.MAIN_HOST_CONFIGS, openHostConfig());
        this.setItem(21, IInfos.MAIN_HOST_INVENTORIES, openInventoriesConfig());
        this.setItem(23, IInfos.MAIN_HOST_WORLD, openWorldConfig());
        this.setItem(31, IInfos.MAIN_HOST_ROLES, openRolesConfig());
        if (setup.getGame().getGameInfo().getMTime() == null)
            this.setItem(49, IInfos.MAIN_HOST_START, onButtonClick(false));
        else
            this.setItem(49, IInfos.MAIN_HOST_STOP, onButtonClick(true));
    }

    private Consumer<InventoryClickEvent> onButtonClick(boolean isStarting) {
        return e -> {
            if (isStarting) {
                setup.getGame().getGameInfo().removeMTime();
                this.setItem(49, IInfos.MAIN_HOST_START, onButtonClick(false));
            }
            else {
                setup.getGame().getGameInfo().startMTime(setup);
                this.setItem(49, IInfos.MAIN_HOST_STOP, onButtonClick(true));
            }
            player.updateInventory();
        };
    }

    private Consumer<InventoryClickEvent> openRolesConfig() {
        return e -> {
            player.closeInventory();
            player.openInventory(new IHostRoles(setup, player, IHostRolesPage.SHINOBI).getInventory());
        };
    }

    private Consumer<InventoryClickEvent> openInventoriesConfig() {
        return e -> {
            player.closeInventory();
            player.openInventory(new IHostInventories(setup, player).getInventory());
        };
    }

    private Consumer<InventoryClickEvent> openWorldConfig() {

        return e ->  {
            player.closeInventory();
            player.openInventory(new IHostWorld(setup, player).getInventory());
        };
    }

    private Consumer<InventoryClickEvent> openHostConfig() {

        return e -> {
            player.closeInventory();
            player.openInventory(new IHostConfig(setup, player).getInventory());
        };
    }

}
