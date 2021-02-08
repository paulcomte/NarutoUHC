/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class HStatus {

    public static boolean kickPlayer(String[] args, CommandSender sender) {

        Player target = HManager.checkCommandArgs(args, sender, Messages.HOST_USAGE_KICK);

        if (target == null)
            return false;


        target.kickPlayer(Messages.PLAYER_KICKED);
        sender.sendMessage(Messages.PLAYER_NOW_KICKED.replace("%player%", target.getName()));

        return true;
    }

    public static boolean banPlayer(String[] args, CommandSender sender) {

        Player target = HManager.checkCommandArgs(args, sender, Messages.HOST_USAGE_BAN);

        if (target == null)
            return false;

        GameInfo.bannedPlayers.add(target.getUniqueId());
        target.kickPlayer(Messages.PLAYER_BANNED);
        sender.sendMessage(Messages.PLAYER_NOW_BANNED.replace("%player%", target.getName()));

        return true;
    }

    public static boolean unbanPlayer(String[] args, CommandSender sender) {

        Player player = HManager.checkCommandArgs(args, sender, Messages.HOST_USAGE_UNBAN);

        if (player != null) {
            sender.sendMessage(Messages.PLAYER_NOT_BANNED.replace("%player%", player.getName()));;
            return false;
        }

        for (UUID banned : GameInfo.bannedPlayers) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(banned);
            if (offlinePlayer == null) continue;
            if (offlinePlayer.getName().equalsIgnoreCase(args[1])) {
                GameInfo.bannedPlayers.remove(banned);
                sender.sendMessage(Messages.PLAYER_NOW_UNBANNED.replace("%player%", offlinePlayer.getName()));
                return true;
            }
        }

        sender.sendMessage(Messages.PLAYER_NOT_BANNED.replace("%player%",args[1]));
        return false;
    }

}
