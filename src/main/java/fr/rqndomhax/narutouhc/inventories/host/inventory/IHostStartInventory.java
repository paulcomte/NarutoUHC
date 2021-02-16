/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.inventory;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostStartInventory {

    private final Setup setup;
    private final Player player;

    public IHostStartInventory(Setup setup, Player player, RInventory inventory) {
        this.setup = setup;
        this.player = player;

        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int[] orange = new int[]{0, 2, 6, 8};
        int[] black = new int[]{1, 7};

        for (Integer glass : orange)
            inventory.setItem(glass, IInfos.ORANGE_GLASS_BORDER);
        for (Integer glass : black)
            inventory.setItem(glass, IInfos.BLACK_GLASS_BORDER);
        for (int i = 3 ; i <= 5 ; inventory.setItem(i, IInfos.BARS), i += 2);
        inventory.setItem(4, IInfos.INVENTORIES_HOST_BEGINNING);
        for (int i = 49 ; i <= 51 ; inventory.setItem(i, IInfos.BARS), i++);
        inventory.setItem(52, IInfos.INVENTORIES_EDIT, editInventory());
        inventory.setItem(53, IInfos.RETURN_ITEM, e -> {
            new IHostInventories(setup, player, inventory);
        });
        IInfos.setInventoryContent(inventory.getInventory(), setup.getGame().getGameRules().startInventory);

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> editInventory() {

        GameRules gameRules = setup.getGame().getGameRules();

        return e -> {

            if (!GameInfo.gameHost.equals(player.getUniqueId()) && !GameInfo.gameCoHost.contains(player.getUniqueId())) {
                player.sendMessage(Messages.COMMAND_ONLY_HOST);
                return;
            }

            if (gameRules.startInventoryInEdit != null) {
                player.sendMessage(Messages.HOST_INVENTORY_ALREADY_IN_EDIT);
                return;
            }

            gameRules.startInventoryInEdit = player.getUniqueId();
            player.closeInventory();
            player.setGameMode(GameMode.CREATIVE);
            InventoryManager.clearInventory(player);
            InventoryManager.giveInventory(setup.getGame().getGameRules().startInventory, player);
            player.sendMessage(Messages.HOST_INVENTORY_EDIT);
        };
    }

}
