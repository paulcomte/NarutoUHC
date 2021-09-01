/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class HManager {

    public static Player checkCommandArgs(String[] args, CommandSender sender, String usage) {

        if (sender instanceof Player && !GameInfo.gameHost.equals(((Player) sender).getUniqueId())) {
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

        if (sender instanceof Player) {
            sender.sendMessage(Messages.COMMAND_ONLY_CONSOLE);
            return false;
        }

        if (args.length == 1) {
            sender.sendMessage(Messages.HOST_USAGE_SET);
            return false;
        }

        if (GameInfo.gameHost != null && Bukkit.getPlayer(GameInfo.gameHost) != null) {
            sender.sendMessage(Messages.HOST_NEED_OFFLINE.replace("%player%", Bukkit.getPlayer(GameInfo.gameHost).getName()));
            return false;
        }

        GameInfo.tmpGameHost = args[1];
        Player player = Bukkit.getPlayer(GameInfo.tmpGameHost);
        if (player != null) {
            GameInfo.gameHost = player.getUniqueId();
            GameInfo.tmpGameHost = null;
            player.sendMessage(Messages.HOST_SET);
            if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING))
                MGameActions.clearPlayerLobby(player);
        }
        if (player != null)
            sender.sendMessage(Messages.HOST_NOW_SET.replace("%player%", player.getName()));
        else
            sender.sendMessage(Messages.HOST_NOW_SET.replace("%player%", GameInfo.tmpGameHost));

        return true;
    }

    public static boolean sendAnnounce(String[] args, CommandSender sender) {

        if (sender instanceof Player
                && !GameInfo.gameHost.equals(((Player) sender).getUniqueId())
                && !GameInfo.gameCoHost.contains(((Player) sender).getUniqueId())) {
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
