/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.inventories.host.inventory.IHostDeathInventory;
import fr.rqndomhax.narutouhc.inventories.host.inventory.IHostStartInventory;
import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CSave implements CommandExecutor {

    private final Setup setup;

    public CSave(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.NEED_PLAYER);
            return false;
        }

        GameRules rules = setup.getGame().getGameRules();
        Player player = (Player) sender;

        if (!rules.gameHost.equals(player.getUniqueId()) && !rules.gameCoHost.contains(player.getUniqueId())) {
            player.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            player.sendMessage(Messages.NOT_IN_LOBBY);
            return false;
        }

        if (rules.startInventoryInEdit != null && rules.startInventoryInEdit.equals(player.getUniqueId())) {

            InventoryManager.saveInventory(rules.startInventory, player);
            MGameActions.clearPlayerLobby(setup.getGame().getGameRules(), player);
            rules.startInventoryInEdit = null;

            player.sendMessage(Messages.HOST_INVENTORY_BEGINNING_SAVED);

            IHost host = new IHost(setup, player);
            player.openInventory(host.getInventory());
            new IHostStartInventory(setup, player, host);

            return true;
        }
        else if (rules.deathInventoryInEdit != null && rules.deathInventoryInEdit.equals(player.getUniqueId())) {
            InventoryManager.saveInventory(rules.deathInventory, player);
            MGameActions.clearPlayerLobby(setup.getGame().getGameRules(), player);

            rules.deathInventoryInEdit = null;

            player.sendMessage(Messages.HOST_INVENTORY_DEATH_SAVED);

            IHost host = new IHost(setup, player);
            player.openInventory(host.getInventory());
            new IHostDeathInventory(setup, player, host);

            return true;
        }
        else {
            player.sendMessage(Messages.HOST_INVENTORY_NOT_EDIT);
            return false;
        }
    }

}
