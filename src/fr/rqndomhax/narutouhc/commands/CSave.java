/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.host.IHostDeathInventory;
import fr.rqndomhax.narutouhc.inventories.host.IHostStartInventory;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.InventoryManager;
import fr.rqndomhax.narutouhc.utils.Messages;
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

        MRules rules = setup.getGame().getGameInfo().getMRules();
        Player player = (Player) sender;

        if (!rules.gameHost.equals(player.getUniqueId()) && !rules.gameCoHost.contains(player.getUniqueId())) {
            player.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (!setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING)) {
            player.sendMessage(Messages.NOT_IN_LOBBY);
            return false;
        }

        if (rules.startInventoryInEdit != null && rules.startInventoryInEdit.equals(player.getUniqueId())) {
            InventoryManager.saveInventory(rules.startInventory, player);
            MGameActions.clearPlayerLobby(setup, player);
            rules.startInventoryInEdit = null;
            player.sendMessage(Messages.HOST_INVENTORY_BEGINNING_SAVED);
            player.openInventory(new IHostStartInventory(setup, player).getInventory());
            return true;
        }
        else if (rules.deathInventoryInEdit != null && rules.deathInventoryInEdit.equals(player.getUniqueId())) {
            InventoryManager.saveInventory(rules.deathInventory, player);
            MGameActions.clearPlayerLobby(setup, player);
            rules.deathInventoryInEdit = null;
            player.sendMessage(Messages.HOST_INVENTORY_DEATH_SAVED);
            player.openInventory(new IHostDeathInventory(setup, player).getInventory());
            return true;
        }
        else {
            player.sendMessage(Messages.HOST_INVENTORY_NOT_EDIT);
            return false;
        }
    }

}
