/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.host.IHostEnchant;
import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CEnchant implements CommandExecutor {

    private final Setup setup;

    public CEnchant(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.NEED_PLAYER);
            return false;
        }

        Player player = (Player) sender;
        GameRules rules = setup.getGame().getGameRules();

        if (!rules.gameCoHost.contains(player.getUniqueId()) && !rules.gameHost.equals(player.getUniqueId())) {
            player.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            player.sendMessage(Messages.NOT_IN_LOBBY);
            return false;
        }

        if ((rules.startInventoryInEdit != null && rules.startInventoryInEdit.equals(player.getUniqueId()))
                || (rules.deathInventoryInEdit != null && rules.deathInventoryInEdit.equals(player.getUniqueId()))) {
            if (player.getItemInHand() == null) {
                player.sendMessage(Messages.NEED_ITEM_IN_HAND);
                return false;
            }
            player.openInventory(new IHostEnchant(setup, player).getInventory());
            return true;
        }
        player.sendMessage(Messages.HOST_INVENTORY_NOT_EDIT);
        return true;
    }

}
