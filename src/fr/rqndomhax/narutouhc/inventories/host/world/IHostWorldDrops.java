/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.world;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.rules.Drops;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.DecimalFormat;
import java.util.function.Consumer;

public class IHostWorldDrops {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;
    DecimalFormat format = new DecimalFormat("#.#");

    public IHostWorldDrops(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        IInfos.placeInvBorders(inventory.getInventory());

        int[] bars = new int[]{3, 5, 48, 50};
        int[] difficulties = new int[]{22, 31};
        int n = 0;

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.WORLD_HOST_DAY_CYCLE).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());

        for (Drops drop : setup.getGame().getGameInfo().getMRules().drops) {
            StringBuilder description = new StringBuilder();
            ItemBuilder item = new ItemBuilder(drop.getItem().clone());

            description.append(drop.getName()).append(" ");
            description.append(ChatColor.DARK_AQUA);
            description.append(format.format(drop.getPercentage()));
            description.append(" %");
            inventory.setItem(difficulties[n], item.setName(description.toString()).toItemStack(), updatePercentage(drop));
            n++;
        }

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            new IHostWorld(setup, player, inventory);
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updatePercentage(Drops drop) {
        return e -> {
            if (e.getClick().isLeftClick()) {
                if (drop.getPercentage() + 1 >= 100d)
                    drop.setPercentage(100);
                else
                    drop.setPercentage(drop.getPercentage() + 1d);
            }
            if (e.getClick().isRightClick()) {
                if (drop.getPercentage() - 1 <= 0)
                    drop.setPercentage(0);
                else
                    drop.setPercentage(drop.getPercentage() - 1d);
            }
            updateInventory();
        };
    }

}
