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
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.PageController;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IHostScenarios {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostScenarios(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        IInfos.placeInvBorders(inventory.getInventory());
        inventory.setItem(4, IInfos.MAIN_HOST_SCENARIOS);

        int[] bars = new int[]{3, 5, 19, 28, 25, 34, 48, 50};
        int[] slots = new int[]{12, 13, 14, 20, 21, 23, 24, 28, 29, 30, 31, 32, 33, 39, 40, 41};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            MRules rules = setup.getGame().getGameInfo().getMRules();
            player.closeInventory();
            if (rules.gameHost.equals(player.getUniqueId()) || rules.gameCoHost.contains(player.getUniqueId()))
                player.openInventory(new IHost(setup, player).getInventory());
        });

        inventory.setPageController(pageController -> {
            pageController.setBoard(slots);
            pageController.setItemStacks(generateBoard());
        });

        inventory.update(() -> {
            PageController pageController = inventory.getPageController();
            inventory.setItem(47, new ItemBuilder(Material.ARROW).setName("("+(pageController.getPage()+1)+"/"+pageController.getMaxPage()+")").toItemStack(), e -> {
                pageController.previousPage();
            });

            inventory.setItem(51, new ItemBuilder(Material.ARROW).setName("("+(pageController.getPage()+1)+"/"+pageController.getMaxPage()+")").toItemStack(), e -> {
                pageController.nextPage();
            });
        }, 1);
    }

    private List<RInventoryData> generateBoard() {
        List<RInventoryData> items = new ArrayList<>();

        for (Scenarios scenario : Scenarios.values()) {
            ItemBuilder item = new ItemBuilder(scenario.getItem().clone());
            StringBuilder name = new StringBuilder();

            name.append(scenario.getDescription());

            if (setup.getGame().getGameInfo().getMRules().activatedScenarios.contains(scenario)) {
                name.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                name.append(ChatColor.DARK_RED + " ✘");
            items.add(new RInventoryData(item.setName(name.toString()).toItemStack(), updateConfig(scenario)));
        }
        return items;
    }

    private Consumer<InventoryClickEvent> updateConfig(Scenarios scenario) {
        return e -> {
            MRules rules = setup.getGame().getGameInfo().getMRules();

            if (rules.activatedScenarios.contains(scenario))
                rules.activatedScenarios.remove(scenario);
            else
                rules.activatedScenarios.add(scenario);
            updateInventory();
        };
    }

    private ItemStack getBorderDisconnects() {
        if (setup.getGame().getGameInfo().getMRules().spectatorsAfterBorder)
            return new ItemBuilder(IInfos.HOST_SPECTATORS_AFTER_BORDER.clone()).setName("Déconnexion bordure " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_SPECTATORS_AFTER_BORDER.clone()).setName("Déconnexion bordure " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeBorderDisconnects() {
        return e -> {
            setup.getGame().getGameInfo().getMRules().spectatorsAfterBorder = !setup.getGame().getGameInfo().getMRules().spectatorsAfterBorder;
            updateInventory();
        };
    }

    private ItemStack getSpectators() {
        if (setup.getGame().getGameInfo().getMRules().allowSpectators)
            return new ItemBuilder(IInfos.HOST_SPECTATORS.clone()).setName("Spectateurs " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_SPECTATORS.clone()).setName("Spectateurs " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeSpectators() {
        return e -> {
            setup.getGame().getGameInfo().getMRules().allowSpectators = !setup.getGame().getGameInfo().getMRules().allowSpectators;
            updateInventory();
        };
    }

    private ItemStack getWhitelist() {
        if (setup.getGame().getGameInfo().getMRules().hasWhitelist)
            return new ItemBuilder(IInfos.HOST_WHITELIST.clone()).setName("Whitelist " + ChatColor.GREEN + " ✔").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
        return new ItemBuilder(IInfos.HOST_WHITELIST.clone()).setName("Whitelist " + ChatColor.DARK_RED + " ✘").toItemStack();
    }

    private Consumer<InventoryClickEvent> changeWhitelist() {
        return e -> {
            setup.getGame().getGameInfo().getMRules().hasWhitelist = !setup.getGame().getGameInfo().getMRules().hasWhitelist;
            updateInventory();
        };
    }

}
