/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class HRank {

    public static boolean promoteHost(GameRules rules, GameState gameState, String[] args, CommandSender sender) {
        Player coHost = HManager.checkCommandArgs(rules, args, sender, Messages.HOST_USAGE_PROMOTE);

        if (coHost == null)
            return false;

        if (rules.gameCoHost.contains(coHost.getUniqueId())) {
            sender.sendMessage(Messages.HOST_ALREADY_CO_HOST.replace("%player%", coHost.getName()));
            return false;
        }

        if (rules.gameCoHost.size() >= rules.maxCoHost) {
            sender.sendMessage(Messages.HOST_TOO_MANY_CO_HOST);
            return false;
        }

        rules.gameCoHost.add(coHost.getUniqueId());
        sender.sendMessage(Messages.HOST_NOW_CO_HOST.replace("%player%", coHost.getName()));
        coHost.sendMessage(Messages.HOST_PROMOTED);
        if (gameState.equals(GameState.LOBBY_WAITING))
            MGameActions.clearPlayerLobby(rules, coHost);
        return true;
    }

    public static boolean deleteHost(GameRules rules, GameState gameState, String[] args, CommandSender sender) {

        Player coHost = HManager.checkCommandArgs(rules, args, sender, Messages.HOST_USAGE_DELETE);

        if (coHost == null)
            return false;

        if (!rules.gameCoHost.contains(coHost.getUniqueId())) {
            sender.sendMessage(Messages.HOST_ALREADY_CO_HOST.replace("%player%", coHost.getName()));
            return false;
        }

        rules.gameCoHost.remove(coHost.getUniqueId());
        sender.sendMessage(Messages.HOST_NOW_DELETED_CO_HOST.replace("%player%", coHost.getName()));
        coHost.sendMessage(Messages.HOST_DEMOTED);
        if (gameState.equals(GameState.LOBBY_WAITING))
            MGameActions.clearPlayerLobby(rules, coHost);
        return true;
    }

}
