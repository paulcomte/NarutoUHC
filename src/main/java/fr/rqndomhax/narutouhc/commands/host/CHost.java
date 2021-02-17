/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.ChatColor;
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

        GameRules rules = setup.getGame().getGameRules();

        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {

           if (!(sender instanceof Player) || GameInfo.gameHost.equals(((Player) sender).getUniqueId())
                   || GameInfo.gameCoHost.contains(((Player) sender).getUniqueId()))
                return showHostHelp(sender);

            sender.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        GameState gameState = setup.getGame().getGameState();

        switch (args[0].toLowerCase()) {
            case "del":
            case "delete":
                return HRank.deleteHost(gameState, args, sender);
            case "promote":
                return HRank.promoteHost(rules, gameState, args, sender);
            case "ban":
                return HStatus.banPlayer(args, sender);
            case "unban":
                return HStatus.unbanPlayer(args, sender);
            case "kick":
                return HStatus.kickPlayer(args, sender);
            case "an":
            case "say":
                return HManager.sendAnnounce(args, sender);
            case "set":
                return HManager.setHost(setup, args, sender);
            default:
                if (sender instanceof Player) {
                    if ((GameInfo.gameHost.equals(((Player) sender).getUniqueId()) || GameInfo.gameCoHost.contains(((Player) sender).getUniqueId())))
                        return showHostHelp(sender);
                    sender.sendMessage(Messages.COMMAND_ONLY_HOST);
                    return false;
                }
                return showHostHelp(sender);
        }

    }

    private boolean showHostHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "\n----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----\n");
        sender.sendMessage(ChatColor.DARK_AQUA + "/host " + ChatColor.GOLD + "delete " + ChatColor.LIGHT_PURPLE + "<player> " + ChatColor.DARK_AQUA + ": pour supprimer un co-hôte.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/host " + ChatColor.GOLD + "promote " + ChatColor.LIGHT_PURPLE + "<player> " + ChatColor.DARK_AQUA + ": pour ajouter un co-hôte.");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_AQUA + "/host " + ChatColor.GOLD + "ban " + ChatColor.LIGHT_PURPLE + "<player> " + ChatColor.DARK_AQUA + ": pour bannir un joueur.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/host " + ChatColor.GOLD + "ban " + ChatColor.LIGHT_PURPLE + "<player> " + ChatColor.DARK_AQUA + ": pour annuler le ban d'un joueur.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/host " + ChatColor.GOLD + "kick " + ChatColor.LIGHT_PURPLE + "<player> " + ChatColor.DARK_AQUA + ": pour expulser un joueur.");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.DARK_AQUA + "/host " + ChatColor.GOLD + "say " + ChatColor.LIGHT_PURPLE + "<message> " + ChatColor.DARK_AQUA + ": pour faire une annonce.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "\n----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        sender.sendMessage("");
        return false;
    }

}
