/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CWhisper implements CommandExecutor {

    private final Setup setup;

    public CWhisper(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        GameState gameState = setup.getGame().getGameState();

        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_FINISHED))
            return sendMessage(sender, args);

        if (!setup.getGame().getGameRules().allowWhispers) {
            sender.sendMessage(Messages.WHISPERS_OFF);
            return false;
        }

        return sendMessage(sender, args);
    }

    private boolean sendMessage(CommandSender sender, String[] args) {

        if (args.length < 2) {
            sender.sendMessage(Messages.PLAYER_USAGE_WHISPER);
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(Messages.PLAYER_NOT_EXIST.replace("%player%", args[0]));
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 1 ; i < args.length ; stringBuilder.append(args[i]).append(" "), i++);

        String message = stringBuilder.toString();

        if (!(sender instanceof Player) || !sender.equals(player))
            sender.sendMessage(ChatColor.BLACK + "[" + ChatColor.GREEN + "moi " + ChatColor.BLACK + "» " + ChatColor.GREEN + "" + player.getName() + ChatColor.BLACK + "] " + ChatColor.GRAY + message);
        player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GREEN + sender.getName() + " " + ChatColor.BLACK + "» " + ChatColor.GREEN + "moi" + ChatColor.BLACK + "] " + ChatColor.GRAY + message);

        return true;
    }

}
