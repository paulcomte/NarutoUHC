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
import fr.rqndomhax.narutouhc.game.tasks.TBorder;
import fr.rqndomhax.narutouhc.game.tasks.TMain;
import fr.rqndomhax.narutouhc.game.tasks.TPreparation;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CForce implements CommandExecutor {

    private final Setup setup;

    public CForce(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        GameState gameState = setup.getGame().getGameState();
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!GameInfo.gameHost.equals(player.getUniqueId()) && !GameInfo.gameCoHost.contains(player.getUniqueId())) {
                player.sendMessage(Messages.COMMAND_ONLY_HOST);
                return false;
            }
            if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING)) {
                player.sendMessage(Messages.NOT_IN_GAME);
                return false;
            }
            if (gameState.equals(GameState.GAME_FINISHED)) {
                player.sendMessage(Messages.GAME_FINISHED);
                return false;
            }
        }

        if (args.length != 1 || args[0].equalsIgnoreCase("help"))
            return showHelp(sender);

        TMain mainTask = setup.getGame().getMainTask();
        if (args[0].equalsIgnoreCase("b") || args[0].equalsIgnoreCase("border") || args[0].equalsIgnoreCase("bordure"))
            return forceBorder(sender, gameState, mainTask);
        if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("teleport"))
            return forceTeleport(sender, gameState, mainTask);
        if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("roles") || args[0].equalsIgnoreCase("role"))
            return forceRole(sender, mainTask);
        return showHelp(sender);
    }

    private boolean showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "\n----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----\n");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "border " + ChatColor.DARK_AQUA + ": pour forcer la bordure.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "tp " + ChatColor.DARK_AQUA + ": pour forcer la téléportation sur l'univers de naruto.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "role " + ChatColor.DARK_AQUA + ": pour forcer l'affichage des rôles.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "\n----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        sender.sendMessage("");
        return false;
    }

    private boolean forceBorder(CommandSender sender, GameState gameState, TMain mainTask) {
        if (gameState.equals(GameState.GAME_MEETUP)) {
            sender.sendMessage(Messages.BORDER_ALREADY_ACTIVATED);
            return false;
        }
        if (!gameState.equals(GameState.GAME_BORDER)) {
            sender.sendMessage(Messages.FORCE_WRONG_MAP_BORDER);
            return false;
        }

        if (mainTask == null || mainTask.task.getClass() != TBorder.class) {
            sender.sendMessage(Messages.BORDER_ALREADY_ACTIVATED);
            return false;
        }

        TBorder border = (TBorder) mainTask.task;

        if (border.remainingTime <= 10) {
            sender.sendMessage(Messages.BORDER_ALREADY_ACTIVATED);
            return false;
        }

        border.remainingTime = 10;
        return true;
    }

    private boolean forceRole(CommandSender sender, TMain mainTask) {
        if (mainTask.hasRoles) {
            sender.sendMessage(Messages.ROLES_ALREADY_ACTIVATED);
            return false;
        }

        if (mainTask.roleRemainingTime - mainTask.time <= 10) {
            sender.sendMessage(Messages.ROLES_ALREADY_ACTIVATED);
            return false;
        }

        mainTask.roleRemainingTime = mainTask.time + 10;
        return true;
    }

    private boolean forceTeleport(CommandSender sender, GameState gameState, TMain mainTask) {
        if (gameState.equals(GameState.GAME_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTATION_INVINCIBILITY) || gameState.equals(GameState.GAME_BORDER) || gameState.equals(GameState.GAME_MEETUP)) {
            sender.sendMessage(Messages.TELEPORT_ALREADY_ACTIVATED);
            return false;
        }
        if (!gameState.equals(GameState.GAME_PREPARATION)) {
            sender.sendMessage(Messages.TELEPORT_FORCE_WRONG);
            return false;
        }

        if (mainTask == null || mainTask.task.getClass() != TPreparation.class) {
            sender.sendMessage(Messages.TELEPORT_ALREADY_ACTIVATED);
            return false;
        }

        TPreparation preparation = (TPreparation) mainTask.task;

        if (preparation.remainingTime <= 10) {
            sender.sendMessage(Messages.TELEPORT_ALREADY_ACTIVATED);
            return false;
        }

        preparation.remainingTime = 10;
        return true;
    }
}
