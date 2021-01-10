/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class HRank {

    public static boolean promoteHost(MRules rules, String[] args, CommandSender sender) {

        Player coHost = HManager.checkCommandArgs(rules, args, sender, Messages.HOST_USAGE_PROMOTE);

        if (coHost == null) {
            if (args.length == 2)
                sender.sendMessage(Messages.PLAYER_NOT_CONNECTED.replace("%player%", args[1]));
            return false;
        }

        if (rules.gameCoHost.contains(coHost.getUniqueId())) {
            sender.sendMessage(Messages.HOST_ALREADY_CO_HOST.replace("%player%", coHost.getName()));
            return false;
        }

        rules.gameCoHost.add(coHost.getUniqueId());
        sender.sendMessage(Messages.HOST_NOW_CO_HOST.replace("%player%", coHost.getName()));
        return true;
    }

    public static boolean deleteHost(MRules rules, String[] args, CommandSender sender) {

        Player coHost = HManager.checkCommandArgs(rules, args, sender, Messages.HOST_USAGE_DELETE);

        if (coHost == null) {
            if (args.length == 2)
                sender.sendMessage(Messages.PLAYER_NOT_CONNECTED.replace("%player%", args[1]));
            return false;
        }

        if (!rules.gameCoHost.contains(coHost.getUniqueId())) {
            sender.sendMessage(Messages.HOST_ALREADY_CO_HOST.replace("%player%", coHost.getName()));
            return false;
        }

        rules.gameCoHost.remove(coHost.getUniqueId());
        sender.sendMessage(Messages.HOST_NOW_DELETED_CO_HOST.replace("%player%", coHost.getName()));
        return true;
    }

}
