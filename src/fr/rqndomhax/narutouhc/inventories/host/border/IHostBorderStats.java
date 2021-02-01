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
import fr.rqndomhax.narutouhc.utils.tools.Banners;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.function.Consumer;

public class IHostBorderStats {
    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostBorderStats(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int[] bars = new int[]{3, 5, 48, 50};
        int[] speed = new int[]{19, 20, 21, 23, 24, 25};
        int[] damage = new int[]{28, 29, 30, 32, 33, 34};
        int n = 0;

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.HOST_BORDER_SPEED_AND_DAMAGE.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(inventory.getInventory());

        for (Banners banner : Banners.getNegatives()) {
            StringBuilder name = new StringBuilder();

            name.append("- ");
            if (n == 0)
                name.append("5 blocks/sec");
            if (n == 1)
                name.append("1 block/sec");
            if (n == 2)
                name.append("0.1 block/sec");
            inventory.setItem(speed[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.RED + name.toString()).toItemStack(), updateSpeed(true, n));
            n++;
        }
        for (Banners banner : Banners.getPositives()) {
            StringBuilder name = new StringBuilder();

            name.append("+ ");
            if (n == 3)
                name.append("0.1 block/sec");
            if (n == 4)
                name.append("1 block/sec");
            if (n == 5)
                name.append("5 blocks/sec");
            inventory.setItem(speed[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.GREEN + name.toString()).toItemStack(), updateSpeed(false, n));
            n++;
        }

        n = 0;
        for (Banners banner : Banners.getNegatives()) {
            StringBuilder name = new StringBuilder();

            name.append("- ");
            if (n == 0)
                name.append("5 coeurs/sec");
            if (n == 1)
                name.append("1 coeur/sec");
            if (n == 2)
                name.append("0.1 coeur/sec");
            inventory.setItem(damage[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.RED + name.toString()).toItemStack(), updateDamage(true, n));
            n++;
        }
        for (Banners banner : Banners.getPositives()) {
            StringBuilder name = new StringBuilder();

            name.append("+ ");
            if (n == 3)
                name.append("0.1 coeur/sec");
            if (n == 4)
                name.append("1 coeur/sec");
            if (n == 5)
                name.append("5 coeur/sec");
            inventory.setItem(damage[n], new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(ChatColor.GREEN + name.toString()).toItemStack(), updateDamage(false, n));
            n++;
        }

        MBorder border = setup.getGame().getGameInfo().getMBorder();
        DecimalFormat format = new DecimalFormat("#.#");

        if (border.speed > 1)
            inventory.setItem(22, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Vitesse de la bordure: " + ChatColor.DARK_AQUA + format.format(border.speed) + ChatColor.GREEN + " blocks/sec").toItemStack());
        else
            inventory.setItem(22, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Vitesse de la bordure: " + ChatColor.DARK_AQUA + format.format(border.speed) + ChatColor.GREEN + " block/sec").toItemStack());
        if (border.damage > 1)
            inventory.setItem(31, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Dégâts de la bordure: " + ChatColor.DARK_AQUA + format.format(border.damage) + ChatColor.GREEN + " coeurs/sec").toItemStack());
        else
            inventory.setItem(31, new ItemBuilder(Material.IRON_FENCE).setName(ChatColor.GREEN + "Dégâts de la bordure: " + ChatColor.DARK_AQUA + format.format(border.damage) + ChatColor.GREEN + " coeur/sec").toItemStack());

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            MRules rules = setup.getGame().getGameInfo().getMRules();
            if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId()))
                new IHostBorder(setup, player, inventory);
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updateSpeed(boolean isNegative, int n) {
        return e -> {
            MBorder border = setup.getGame().getGameInfo().getMBorder();
            if (isNegative) {
                if (n == 0)
                    if (border.speed - 5d <= 0.5d)
                        border.speed = 0.5d;
                    else
                        border.speed -= 5d;
                if (n == 1)
                    if (border.speed - 1d <= 0.5d)
                        border.speed = 0.5d;
                    else
                        border.speed -= 1d;
                if (n == 2)
                    if (border.speed - 0.1d <= 0.5d)
                        border.speed = 0.5d;
                    else
                        border.speed -= 0.1d;
            }
            else {
                if (n == 3)
                    if (border.speed + 0.1d >= 20d)
                        border.speed = 20d;
                    else
                        border.speed += 0.1d;
                if (n == 4)
                    if (border.speed + 1d >= 20d)
                        border.speed = 20d;
                    else
                        border.speed += 1d;
                if (n == 5)
                    if (border.speed + 5d >= 20d)
                        border.speed = 20d;
                    else
                        border.speed += 5d;
            }
            updateInventory();
        };
    }

    private Consumer<InventoryClickEvent> updateDamage(boolean isNegative, int n) {
        return e -> {
            MBorder border = setup.getGame().getGameInfo().getMBorder();
            if (isNegative) {
                if (n == 0)
                    if (border.damage - 5d <= 0.1d)
                        border.damage = 0.1d;
                    else
                        border.damage -= 5d;
                if (n == 1)
                    if (border.damage - 1d <= 0.1d)
                        border.damage = 0.1d;
                    else
                        border.damage -= 1d;
                if (n == 2)
                    if (border.damage - 0.1d <= 0.1d)
                        border.damage = 0.1d;
                    else
                        border.damage -= 0.1d;
            }
            else {
                if (n == 3)
                    if (border.damage + 0.1d >= 20d)
                        border.damage = 20d;
                    else
                        border.damage += 0.1d;
                if (n == 4)
                    if (border.damage + 1d >= 20d)
                        border.damage = 20d;
                    else
                        border.damage += 1d;
                if (n == 5)
                    if (border.damage + 5d >= 20d)
                        border.damage = 20d;
                    else
                        border.damage += 5d;
            }
            updateInventory();
        };
    }

}
