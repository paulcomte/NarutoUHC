/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.config;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.managers.config.HostConfig;
import fr.rqndomhax.narutouhc.managers.config.MConfig;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.utils.inventory.PageController;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.inventory.RInventoryData;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IHostConfigs {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostConfigs(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateMainInventory();
    }

    private void updateMainInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);
        IInfos.placeInvBorders(inventory.getInventory());

        inventory.setItem(4, IInfos.MAIN_HOST_CONFIGS);

        ItemStack stand = new ItemBuilder(Material.ARMOR_STAND).setName("").toItemStack();

        inventory.setItem(12, stand);

        inventory.setItem(14, stand);

        int[] bars = new int[]{3, 5, 11, 15, 38, 40, 42, 48, 50};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);


        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            player.closeInventory();
            if (GameInfo.gameHost.equals(player.getUniqueId()) || GameInfo.gameCoHost.contains(player.getUniqueId()))
                player.openInventory(new IHost(setup, player).getInventory());
        });

        inventory.setItem(13, new ItemBuilder(setup.getGame().getCurrentConfig().getLogo().getItem()).setName(setup.getGame().getCurrentConfig().getName()).toItemStack());

        inventory.setItem(39, IInfos.HOST_CREATE, e -> {
            MConfig.saveConfig(setup.getGame().getCurrentConfig().copy(), true);
            updateMainInventory();
        });

        inventory.setItem(41, IInfos.HOST_SAVE, e -> {
            MConfig.saveConfig(setup.getGame().getCurrentConfig(), true);
            updateMainInventory();
        });

        updateInventory();
    }

    private void updateInventory() {
        int[] slots = new int[]{19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 35};

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

        for (HostConfig config : MConfig.configurations) {
            if (setup.getGame().getCurrentConfig() != null && setup.getGame().getCurrentConfig().equals(config))
                continue;

            items.add(new RInventoryData(new ItemBuilder(config.getLogo().getItem()).setName(config.getName()).toItemStack(), updateConfig(config)));
        }
        return items;
    }

    private Consumer<InventoryClickEvent> updateConfig(HostConfig config) {
        return e -> {
            if (e.getClick().equals(ClickType.CONTROL_DROP)) {
                MConfig.deleteConfig(config);
                updateMainInventory();
                return;
            }

            if (e.getClick().equals(ClickType.LEFT)) {
                setup.getGame().setCurrentConfig(config);
                updateMainInventory();
                return;
            }

            if (e.getClick().equals(ClickType.RIGHT))
                new IHostEditConfig(setup, player, inventory, config);
        };
    }

}
