/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class Messages {

    public static String PREFIX = ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + ">> ";

    public static String PLUGIN_INIT_STARTED = "[Naruto UHC] PLUGIN INITIALIZING !";
    public static String PLUGIN_INIT_EVENTS = "[Naruto UHC] REGISTERING EVENTS !";
    public static String PLUGIN_INIT_COMMANDS = "[Naruto UHC] REGISTERING COMMANDS !";
    public static String PLUGIN_LAST_TASKS = "[Naruto UHC] FINISHING LAST TASKS !";
    public static String PLUGIN_INITIALIZED = "[Naruto UHC] PLUGIN INITIALIZED !";

    public static String PLAYER_DEATH_WITH_ROLE = "%player% est mort, son rôle était %role%.";
    public static String PLAYER_DEATH_WITHOUT_ROLE = "%player% est mort.";

    public static String CREDITS = "Naruto UHC - Plugin by RqndomHax - https://github.com/rqndomhax";

    public static void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + "-----");
        sender.sendMessage("Naruto UHC is inspired by the Naruto's anime");
        sender.sendMessage(CREDITS);
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + "-----");
    }
}
