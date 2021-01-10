/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CHost implements CommandExecutor {

    private final Setup setup;

    public CHost(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        MRules rules = setup.getGame().getGameInfo().getMRules();

        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {

           if (!(sender instanceof Player) || rules.gameHost.equals(((Player) sender).getUniqueId())
                   || rules.gameCoHost.contains(((Player) sender).getUniqueId())) {
                Messages.showHostHelp(sender);
                return true;
            }

            sender.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        switch (args[0]) {
            case "del":
            case "delete":
                return HRank.deleteHost(rules, args, sender);
            case "promote":
                return HRank.promoteHost(rules, args, sender);
            case "ban":
                return HStatus.banPlayer(rules, args, sender);
            case "unban":
                return HStatus.unbanPlayer(rules, args, sender);
            case "kick":
                return HStatus.kickPlayer(rules, args, sender);
            case "an":
            case "say":
                return HManager.sendAnnounce(rules, args, sender);
            case "set":
                return HManager.setHost(rules, args, sender);
            default:
                if (sender instanceof Player
                        && !rules.gameHost.equals(((Player) sender).getUniqueId())
                        && !rules.gameCoHost.contains(((Player) sender).getUniqueId()))
                    Messages.showHostHelp(sender);
                else
                    sender.sendMessage(Messages.COMMAND_ONLY_HOST);
                return false;
        }

    }

}
