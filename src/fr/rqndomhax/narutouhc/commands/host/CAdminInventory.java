/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.ViewInventory;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.text.View;

public class CAdminInventory implements CommandExecutor {

    private final Setup setup;

    public CAdminInventory(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.NEED_PLAYER);
            return false;
        }

        Player player = (Player) sender;

        if (setup.getGame().getGameRules().gameHost == null || !setup.getGame().getGameRules().gameHost.equals(player.getUniqueId()) && !setup.getGame().getGameRules().gameCoHost.contains(player.getUniqueId())) {
            player.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(Messages.PREFIX + "Veuillez respecter la sytnaxe suivante: /ainv <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(Messages.PREFIX + "Le joueur " + args[0] + "n'est pas connecté !");
            return false;
        }

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(target.getUniqueId());

        if (gamePlayer == null) {
            player.sendMessage(Messages.PLAYER_NOT_PLAYING);
            return false;
        }

        player.openInventory(new ViewInventory(setup, target, gamePlayer).getInventory());

        return true;
    }

}
