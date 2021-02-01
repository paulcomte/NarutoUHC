/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.managers.game.GameState;
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

        if (sender instanceof Player) {
            Player player = (Player) sender;
            MRules rules = setup.getGame().getGameInfo().getMRules();

            if (!rules.gameHost.equals(player.getUniqueId()) && !rules.gameCoHost.contains(player.getUniqueId())) {
                player.sendMessage(Messages.COMMAND_ONLY_HOST);
                return false;
            }
            if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING) || setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_TELEPORTING)) {
                player.sendMessage(Messages.NOT_IN_GAME);
                return false;
            }
        }

        if (args.length != 1 || args[0].equalsIgnoreCase("help")) {
            return showHelp(sender);
        }
        if (args[0].equalsIgnoreCase("b") || args[0].equalsIgnoreCase("border") || args[0].equalsIgnoreCase("bordure"))
            return forceBorder(sender);
        if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("t") || args[0].equalsIgnoreCase("teleport"))
            return forceTeleport(sender);
        if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("roles") || args[0].equalsIgnoreCase("role"))
            return forceRole(sender);
        return showHelp(sender);
    }

    private boolean showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "border" + ChatColor.DARK_AQUA + " : pour forcer la bordure.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "tp" + ChatColor.DARK_AQUA + " : pour forcer la téléportation sur l'univers de naruto.");
        sender.sendMessage(ChatColor.DARK_AQUA + "/force " + ChatColor.GOLD + "role" + ChatColor.DARK_AQUA + " : pour forcer l'affichage des rôles.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        return false;
    }

    private boolean forceBorder(CommandSender sender) {
        GameState gameState = setup.getGame().getGameInfo().getGameState();

        if (setup.getGame().getGameInfo().getMBorder().timeBeforeResize <= 5) {

        }

        if (gameState.equals(GameState.GAME_PREPARATION) || gameState.equals(GameState.GAME_INVINCIBILITY)) {
            sender.sendMessage(Messages.FORCE_WRONG_MAP_BORDER);
            return false;
        }
        return true;
    }

    private boolean forceRole(CommandSender sender) {
        return true;
    }

    private boolean forceTeleport(CommandSender sender) {

        return true;
    }
}
