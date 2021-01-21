/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.InventoryManager;
import fr.rqndomhax.narutouhc.utils.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class IHostDeathInventory extends RInventory {

    private final Setup setup;
    private final Player player;

    public IHostDeathInventory(Setup setup, Player player) {
        super(null, "", 9*6);
        this.setup = setup;
        this.player = player;

        int[] orange = new int[]{0, 2, 6, 8};
        int[] black = new int[]{1, 7};

        for (Integer glass : orange)
            this.setItem(glass, IInfos.ORANGE_GLASS_BORDER);
        for (Integer glass : black)
            this.setItem(glass, IInfos.BLACK_GLASS_BORDER);
        for (int i = 3 ; i <= 5 ; this.setItem(i, IInfos.BARS), i += 2);
        this.setItem(4, IInfos.INVENTORIES_HOST_DEATH);
        for (int i = 49 ; i <= 51 ; this.setItem(i, IInfos.BARS), i++);
        this.setItem(52, IInfos.INVENTORIES_EDIT, editInventory());
        this.setItem(53, IInfos.RETURN_ITEM, e -> {
            player.closeInventory();
            player.openInventory(new IHostInventories(setup, player).getInventory());
        });
        this.setItem(45, new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.BLACK).setLeatherArmorColor(Color.RED).toItemStack());
        this.setItem(46, new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.BLACK).setLeatherArmorColor(Color.RED).toItemStack());
        this.setItem(47, new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.BLACK).setLeatherArmorColor(Color.RED).toItemStack());
        this.setItem(48, new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.BLACK).setLeatherArmorColor(Color.RED).toItemStack());
        IInfos.setInventoryContent(this.getInventory(), setup.getGame().getGameInfo().getMRules().deathInventory);
    }

    private Consumer<InventoryClickEvent> editInventory() {

        MRules mRules = setup.getGame().getGameInfo().getMRules();

        return e -> {

            if (mRules.deathInventoryInEdit != null) {
                player.sendMessage(Messages.HOST_INVENTORY_ALREADY_IN_EDIT);
                return;
            }

            mRules.deathInventoryInEdit = player.getUniqueId();
            player.closeInventory();
            player.setGameMode(GameMode.CREATIVE);
            InventoryManager.clearInventory(player);
            InventoryManager.giveInventory(setup.getGame().getGameInfo().getMRules().deathInventory, player);
        };
    }

}
