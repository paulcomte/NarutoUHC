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
import fr.rqndomhax.narutouhc.inventories.enchant.IEnchant;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Material;
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

        if (!GameInfo.gameCoHost.contains(player.getUniqueId()) && !GameInfo.gameHost.equals(player.getUniqueId())) {
            player.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            player.sendMessage(Messages.NOT_IN_LOBBY);
            return false;
        }

        if ((rules.startInventoryInEdit != null && rules.startInventoryInEdit.equals(player.getUniqueId()))
                || (rules.deathInventoryInEdit != null && rules.deathInventoryInEdit.equals(player.getUniqueId()))) {
            if (player.getItemInHand() == null || player.getItemInHand().getType() == null || player.getItemInHand().getType().equals(Material.AIR)) {
                player.sendMessage(Messages.NEED_ITEM_IN_HAND);
                return false;
            }
            player.openInventory(new IEnchant(player).getInventory());
            return true;
        }
        player.sendMessage(Messages.HOST_INVENTORY_NOT_EDIT);
        return true;
    }

}
