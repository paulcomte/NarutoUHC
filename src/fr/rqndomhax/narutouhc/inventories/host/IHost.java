/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.border.IHostBorder;
import fr.rqndomhax.narutouhc.inventories.host.inventory.IHostInventories;
import fr.rqndomhax.narutouhc.inventories.host.roles.IHostRoles;
import fr.rqndomhax.narutouhc.inventories.host.roles.IHostRolesPage;
import fr.rqndomhax.narutouhc.inventories.host.world.IHostWorld;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
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
        this.setItem(4, IInfos.MAIN_HOST_CONFIGS);
        this.setItem(11, IInfos.MAIN_HOST_SCENARIOS, openScenariosConfig());
        this.setItem(15, IInfos.MAIN_HOST_BORDER_CONFIG, openBorderConfig());
        this.setItem(21, IInfos.MAIN_HOST_INVENTORIES, openInventoriesConfig());
        this.setItem(23, IInfos.MAIN_HOST_WORLD, openWorldConfig());
        this.setItem(31, IInfos.MAIN_HOST_ROLES, openRolesConfig());
        this.setItem(38, IInfos.MAIN_HOST_TIMERS, openTimerConfig());
        this.setItem(42, IInfos.MAIN_HOST_HOST, openHostConfig());
        if (setup.getGame().getMainTask() == null)
            this.setItem(49, IInfos.MAIN_HOST_START, onButtonClick(false));
        else
            this.setItem(49, IInfos.MAIN_HOST_STOP, onButtonClick(true));
    }

    private Consumer<InventoryClickEvent> openScenariosConfig() {
        return e -> {
            new IHostScenarios(setup, player, this);
        };
    }

    private Consumer<InventoryClickEvent> openHostConfig() {
        return e -> {
            new IHostConfig(setup, player, this);
        };
    }

    private Consumer<InventoryClickEvent> openTimerConfig() {
        return e -> {
            new IHostTimer(setup, player, this);
        };
    }

    private Consumer<InventoryClickEvent> openBorderConfig() {
        return e -> {
            new IHostBorder(setup, player, this);
        };
    }

    private Consumer<InventoryClickEvent> onButtonClick(boolean isStarting) {
        return e -> {
            if (isStarting) {
                setup.getGame().removeTask();
                this.setItem(49, IInfos.MAIN_HOST_START, onButtonClick(false));
                MGameActions.sendInfos(setup.getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage " + ChatColor.RED + "annulé", Instrument.BASS_DRUM, true, 0, Note.Tone.B);
            }
            else {/* TODO REPLACE
                if (Bukkit.getOnlinePlayers().size() != setup.getGame().getGameInfo().getMRules().activatedRoles.size()) {
                    player.sendMessage(Messages.HOST_NEED_MORE_PLAYERS);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                    return;
                }
                if (!MGameStatus.hasAtLeastOneDifferentCamp(setup)) {
                    player.sendMessage(Messages.HOST_NEED_ANOTHER_TEAM);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, 1f);
                    return;
                }
                */
                setup.getGame().startTask(setup);
                this.setItem(49, IInfos.MAIN_HOST_STOP, onButtonClick(true));
            }
            player.updateInventory();
        };
    }

    private Consumer<InventoryClickEvent> openRolesConfig() {
        return e -> {
            new IHostRoles(setup, player, IHostRolesPage.SHINOBI, this);
        };
    }

    private Consumer<InventoryClickEvent> openInventoriesConfig() {
        return e -> {
            new IHostInventories(setup, player, this);
        };
    }

    private Consumer<InventoryClickEvent> openWorldConfig() {

        return e ->  {
            new IHostWorld(setup, player, this);
        };
    }

    private Consumer<InventoryClickEvent> openHostConfigs() {

        return e -> {
            new IHostConfigs(setup, player, this);
        };
    }

}
