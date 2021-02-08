/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.border;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.game.GameBorder;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.Banners;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;
import java.util.function.Consumer;

public class IHostBorderActivation {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostBorderActivation(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int[] bars = new int[]{3, 5, 48, 50};
        int[] negatives = new int[]{19, 20, 21, 28, 29, 30};
        int[] positives = new int[]{23, 24, 25, 32, 33, 34};
        int n = 0;

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.HOST_BORDER_ACTIVATION.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(inventory.getInventory());

        for (int i = 0; i < 2; i++) {
            for (Banners banner : Banners.getNegatives()) {
                StringBuilder name = new StringBuilder();

                name.append("- ");
                if (n == 0 || n == 3)
                    name.append("10 mins");
                if (n == 1 || n == 4)
                    name.append("5 mins");
                if (n == 2 || n == 5)
                    name.append("1 min");
                inventory.setItem(negatives[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.RED + name.toString()).toItemStack(), updateActivation(true, n));
                n++;
            }
        }
        n = 0;

        for (int i = 0; i < 2; i++) {
            for (Banners banner : Banners.getPositives()) {
                StringBuilder name = new StringBuilder();

                name.append("+ ");
                if (n == 0 || n == 3)
                    name.append("1 min");
                if (n == 1 || n == 4)
                    name.append("5 mins");
                if (n == 2 || n == 5)
                    name.append("10 mins");
                inventory.setItem(positives[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.GREEN + name.toString()).toItemStack(), updateActivation(false, n));
                n++;
            }
        }
        if (setup.getGame().getGameRules().gameBorder.timeBeforeResize == 60) {
            inventory.setItem(22, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Temps: " + ChatColor.DARK_AQUA + "1" + ChatColor.GREEN + " min").toItemStack());
            inventory.setItem(31, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Temps: " + ChatColor.DARK_AQUA + "1" + ChatColor.GREEN + " min").toItemStack());
        }
        else {
            inventory.setItem(22, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Temps: " + ChatColor.DARK_AQUA + setup.getGame().getGameRules().gameBorder.timeBeforeResize / 60 + ChatColor.GREEN + " mins").toItemStack());
            inventory.setItem(31, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Temps: " + ChatColor.DARK_AQUA + setup.getGame().getGameRules().gameBorder.timeBeforeResize / 60 + ChatColor.GREEN + " mins").toItemStack());
        }

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            if (GameInfo.gameHost.equals(player.getUniqueId()) || GameInfo.gameCoHost.contains(player.getUniqueId()))
                new IHostBorder(setup, player, inventory);
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updateActivation(boolean isNegative, int n) {
        return e -> {
            GameBorder border = setup.getGame().getGameRules().gameBorder;
            if (isNegative) {
                if (n == 0 || n == 3)
                    if (border.timeBeforeResize - 10*60 <= 60)
                        border.timeBeforeResize = 60;
                    else
                        border.timeBeforeResize -= 10*60;
                if (n == 1 || n == 4)
                    if (border.timeBeforeResize - 5*60 <= 60)
                        border.timeBeforeResize = 60;
                    else
                        border.timeBeforeResize -= 5*60;
                if (n == 2 || n == 5)
                    if (border.timeBeforeResize - 60 <= 60)
                        border.timeBeforeResize = 60;
                    else
                        border.timeBeforeResize -= 60;
            }
            else {
                if (n == 0 || n == 3)
                    if (border.timeBeforeResize + 60 >= 120*60)
                        border.timeBeforeResize = 120*60;
                    else
                        border.timeBeforeResize += 60;
                if (n == 1 || n == 4)
                    if (border.timeBeforeResize + 5*60 >= 120*60)
                        border.timeBeforeResize = 120*60;
                    else
                        border.timeBeforeResize += 5*60;
                if (n == 2 || n == 5)
                    if (border.timeBeforeResize + 10*60 >= 120*60)
                        border.timeBeforeResize = 120*60;
                    else
                        border.timeBeforeResize += 10*60;
            }
            updateInventory();
        };
    }
}
