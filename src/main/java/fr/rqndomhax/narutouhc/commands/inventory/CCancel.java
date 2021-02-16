/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.inventory;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.inventories.host.inventory.IHostDeathInventory;
import fr.rqndomhax.narutouhc.inventories.host.inventory.IHostStartInventory;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CCancel implements CommandExecutor {

    private final Setup setup;

    public CCancel(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.NEED_PLAYER);
            return false;
        }

        GameRules rules = setup.getGame().getGameRules();

        if (rules == null) {
            sender.sendMessage(Messages.HOST_NEED_MORE_PLAYERS);
        }

        Player player = (Player) sender;

        if (!GameInfo.gameHost.equals(player.getUniqueId()) && !GameInfo.gameCoHost.contains(player.getUniqueId())) {
            player.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            player.sendMessage(Messages.NOT_IN_LOBBY);
            return false;
        }

        if (rules.startInventoryInEdit != null && rules.startInventoryInEdit.equals(player.getUniqueId())) {
            rules.startInventoryInEdit = null;
            MGameActions.clearPlayerLobby(player);
            IHost host = new IHost(setup, player);
            player.openInventory(host.getInventory());
            new IHostStartInventory(setup, player, host);
            player.sendMessage(Messages.HOST_INVENTORY_CANCEL);
            return true;
        }
        else if (rules.deathInventoryInEdit != null && rules.deathInventoryInEdit.equals(player.getUniqueId())) {
            rules.deathInventoryInEdit = null;
            MGameActions.clearPlayerLobby(player);
            IHost host = new IHost(setup, player);
            player.openInventory(host.getInventory());
            new IHostDeathInventory(setup, player, host);
            player.sendMessage(Messages.HOST_INVENTORY_CANCEL);
            return true;
        }
        else {
            player.sendMessage(Messages.HOST_INVENTORY_NOT_EDIT);
            return false;
        }
    }
}
