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

    public static String PREFIX = ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + ">> " + ChatColor.RESET;

    public static String PLUGIN_INIT_STARTED = "[Naruto UHC] PLUGIN INITIALIZING !";
    public static String PLUGIN_INIT_EVENTS = "[Naruto UHC] REGISTERING EVENTS !";
    public static String PLUGIN_INIT_COMMANDS = "[Naruto UHC] REGISTERING COMMANDS !";
    public static String PLUGIN_LAST_TASKS = "[Naruto UHC] FINISHING LAST TASKS !";
    public static String PLUGIN_INITIALIZED = "[Naruto UHC] PLUGIN INITIALIZED !";

    public static String PLAYER_NOT_EXIST = PREFIX + "Ce joueur n'est plus sur le serveur !";
    public static String ADMIN_PLAYER_ROLE_NOT_PRESENT = PREFIX + "Ce joueur ne possède aucun rôle !";
    public static String ADMIN_ROLES_NOT_ENABLE = PREFIX + "Les rôles admins n'ont pas été activés !";
    public static String NOT_IN_LOBBY = PREFIX + "Vous ne pouvez pas effectuer cette action en dehors de la phase lobby";
    public static String ADMIN_ROLE_ALREADY_GAVE = PREFIX + "Ce rôle a déjà été attribué à un joueur !";
    public static String ROLE_NOT_PRESENT = PREFIX + "Ce rôle n'a pas été activé";
    public static String ADMIN_ROLE_ADDED = PREFIX + "Vous avez bien ajouté le rôle %role% au joueur %player% !";
    public static String ADMIN_ROLE_REMOVED = PREFIX + "Vous avez bien supprimé le rôle %role% du joueur %player% !";

    public static String PLAYER_DEATH_WITH_ROLE = PREFIX + "%player% est mort, son rôle était %role%.";
    public static String PLAYER_DEATH_WITHOUT_ROLE = PREFIX + "%player% est mort.";

    public static String CREDITS = "Naruto UHC - Plugin by RqndomHax - https://github.com/rqndomhax";

    public static void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + "-----");
        sender.sendMessage("Naruto UHC is inspired by the Naruto's anime");
        sender.sendMessage(CREDITS);
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + "-----");
    }
}
