/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.border;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.Banners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;
import java.util.function.Consumer;

public class IHostBorderSize {
    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostBorderSize(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int[] bars = new int[]{3, 5, 48, 50};
        int[] defaultSize = new int[]{19, 20, 21, 23, 24, 25};
        int[] finalSize = new int[]{28, 29, 30, 32, 33, 34};
        int n = 0;

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.HOST_BORDER_SIZE.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(inventory.getInventory());

        for (Banners banner : Banners.getNegatives()) {
            StringBuilder name = new StringBuilder();

            name.append("- ");
            if (n == 0)
                name.append("10 blocks");
            if (n == 1)
                name.append("5 blocks");
            if (n == 2)
                name.append("1 block");
            inventory.setItem(defaultSize[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.RED + name.toString()).toItemStack(), updateDefaultBorderSize(true, n));
            n++;
        }
        for (Banners banner : Banners.getPositives()) {
            StringBuilder name = new StringBuilder();

            name.append("+ ");
            if (n == 3)
                name.append("1 block");
            if (n == 4)
                name.append("5 blocks");
            if (n == 5)
                name.append("10 blocks");
            inventory.setItem(defaultSize[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.GREEN + name.toString()).toItemStack(), updateDefaultBorderSize(false, n));
            n++;
        }

        n = 0;
        for (Banners banner : Banners.getNegatives()) {
            StringBuilder name = new StringBuilder();

            name.append("- ");
            if (n == 0)
                name.append("10 blocks");
            if (n == 1)
                name.append("5 blocks");
            if (n == 2)
                name.append("1 block");
            inventory.setItem(finalSize[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.RED + name.toString()).toItemStack(), updateFinalBorderSize(true, n));
            n++;
        }
        for (Banners banner : Banners.getPositives()) {
            StringBuilder name = new StringBuilder();

            name.append("+ ");
            if (n == 3)
                name.append("1 block");
            if (n == 4)
                name.append("5 blocks");
            if (n == 5)
                name.append("10 blocks");
            inventory.setItem(finalSize[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.GREEN + name.toString()).toItemStack(), updateFinalBorderSize(false, n));
            n++;
        }

        MBorder border = setup.getGame().getGameInfo().getMRules().mBorder;

        inventory.setItem(22, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Taille de départ: " + ChatColor.DARK_AQUA + border.defaultSize + ChatColor.GREEN + " blocks").toItemStack());
        inventory.setItem(31, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Taille finale: " + ChatColor.DARK_AQUA + border.finalSize + ChatColor.GREEN + " blocks").toItemStack());

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            MRules rules = setup.getGame().getGameInfo().getMRules();
            if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId()))
                new IHostBorder(setup, player, inventory);
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updateFinalBorderSize(boolean isNegative, int n) {
        return e -> {
            MBorder border = setup.getGame().getGameInfo().getMRules().mBorder;
            if (isNegative) {
                if (n == 0)
                    if (border.finalSize - 20 <= 50)
                        border.finalSize = 50;
                    else
                        border.finalSize -= 20;
                if (n == 1)
                    if (border.finalSize - 10 <= 50)
                        border.finalSize = 50;
                    else
                        border.finalSize -= 10;
                if (n == 2)
                    if (border.finalSize - 2 <= 50)
                        border.finalSize = 50;
                    else
                        border.finalSize -= 2;
            }
            else {
                if (n == 3)
                    border.finalSize += 2;
                if (n == 4)
                    border.finalSize += 10;
                if (n == 5)
                    border.finalSize += 20;
            }
            updateInventory();
        };
    }

    private Consumer<InventoryClickEvent> updateDefaultBorderSize(boolean isNegative, int n) {
        return e -> {
            MBorder border = setup.getGame().getGameInfo().getMRules().mBorder;
            if (isNegative) {
                if (n == 0 || n == 3)
                    if (border.defaultSize - 20 <= 50)
                        border.defaultSize = 50;
                    else
                        border.defaultSize -= 20;
                if (n == 1 || n == 4)
                    if (border.defaultSize - 10 <= 50)
                        border.defaultSize = 50;
                    else
                        border.defaultSize -= 10;
                if (n == 2 || n == 5)
                    if (border.defaultSize - 2 <= 50)
                        border.defaultSize = 50;
                    else
                        border.defaultSize -= 2;
            }
                else {
                if (n == 0 || n == 3)
                    border.defaultSize += 2;
                if (n == 1 || n == 4)
                    border.defaultSize += 10;
                if (n == 2 || n == 5)
                    border.defaultSize += 20;
            }
            updateInventory();
        };
    }
}
