/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class HManager {

    public static Player checkCommandArgs(GameRules rules, String[] args, CommandSender sender, String usage) {

        if (sender instanceof Player && !rules.gameHost.equals(((Player) sender).getUniqueId())) {
            sender.sendMessage(Messages.COMMAND_ONLY_HOST);
            return null;
        }

        if (args.length != 2) {
            sender.sendMessage(usage);
            return null;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_CONNECTED.replace("%player%", args[1]));
            return null;
        }
        return player;
    }

    public static boolean setHost(Setup setup, String[] args, CommandSender sender) {

        GameRules rules = setup.getGame().getGameRules();

        if (sender instanceof Player) {
            sender.sendMessage(Messages.COMMAND_ONLY_CONSOLE);
            return false;
        }

        if (args.length == 1) {
            sender.sendMessage(Messages.HOST_USAGE_SET);
            return false;
        }

        if (Bukkit.getPlayer(rules.gameHost) != null) {
            sender.sendMessage(Messages.HOST_NEED_OFFLINE.replace("%player%", Bukkit.getPlayer(rules.gameHost).getName()));
            return false;
        }

        Player player = Bukkit.getPlayer(args[1]);

        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_EXIST);
            return false;
        }

        rules.gameHost = player.getUniqueId();
        player.sendMessage(Messages.HOST_SET);
        if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING))
            MGameActions.clearPlayerLobby(setup.getGame().getGameRules(), player);
        sender.sendMessage(Messages.HOST_NOW_SET.replace("%player%", player.getName()));

        return true;
    }

    public static boolean sendAnnounce(GameRules rules, String[] args, CommandSender sender) {

        if (sender instanceof Player
                && !rules.gameHost.equals(((Player) sender).getUniqueId())
                && !rules.gameCoHost.contains(((Player) sender).getUniqueId())) {
            sender.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (args.length == 1) {
            sender.sendMessage(Messages.HOST_USAGE_AN);
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1 ; i < args.length ; stringBuilder.append(args[i]).append(" "), i++);
        Bukkit.broadcastMessage("\n" + Messages.PREFIX + stringBuilder);
        Bukkit.broadcastMessage("");

        return true;
    }

}
