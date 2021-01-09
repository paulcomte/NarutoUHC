/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Messages {

    public static String PREFIX = ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + ">> " + ChatColor.RESET;

    public static String PLUGIN_INIT_STARTED = "[Naruto UHC] PLUGIN INITIALIZING !";
    public static String PLUGIN_CREATING_WORLDS = "[Naruto UHC] LOADING WORLDS !";
    public static String PLUGIN_DELETING_WORLD = "[Naruto UHC] DELETING OLD WORLD !";
    public static String PLUGIN_INTERNAL_ERROR = "[Naruto UHC] AN INTERNAL ERROR HAS OCCURED !";
    public static String PLUGIN_INIT_EVENTS = "[Naruto UHC] REGISTERING EVENTS !";
    public static String PLUGIN_INIT_COMMANDS = "[Naruto UHC] REGISTERING COMMANDS !";
    public static String PLUGIN_LAST_TASKS = "[Naruto UHC] FINISHING LAST TASKS !";
    public static String PLUGIN_INITIALIZED = "[Naruto UHC] PLUGIN INITIALIZED !";
    public static String SERVER_STARTING = "THE SERVER IS STARTING, PLEASE TRY AGAIN LATER !";
    public static String SERVER_RELAODING = "THE SERVER IS RELOADING !";
    public static String PLUGIN_MAP_NOT_PRESENT = "[Naruto UHC] You need to have 'NARUTO_UNIVERSE' map in your server folder !";

    public static String PLAYER_NOT_EXIST = PREFIX + "Ce joueur n'est pas connecté sur le serveur !";
    public static String NOT_IN_LOBBY = PREFIX + "Vous ne pouvez pas effectuer cette action en dehors de la phase lobby";

    public static String WB_TIME_BEFORE_BORDER_RESIZE = PREFIX + "Réduction de la bordure dans %time% s.";
    public static String WB_BORDER_RESIZING = PREFIX + "La réduction de la bordure vient de commencer, dirigez vous vers le centre !";

    public static String ADMIN_ROLES_NOT_ENABLE = PREFIX + "Les rôles admins n'ont pas été activés !";
    public static String ADMIN_PLAYER_ROLE_NOT_PRESENT = PREFIX + "Ce joueur ne possède aucun rôle !";
    public static String ADMIN_ROLE_ALREADY_GAVE = PREFIX + "Ce rôle a déjà été attribué à un joueur !";
    public static String ROLE_NOT_PRESENT = PREFIX + "Ce rôle n'a pas été activé";
    public static String ADMIN_ROLE_ADDED = PREFIX + "Vous avez bien ajouté le rôle %role% au joueur %player% !";
    public static String ADMIN_ROLE_REMOVED = PREFIX + "Vous avez bien supprimé le rôle %role% du joueur %player% !";

    public static String PLAYER_LEFT = "("+ ChatColor.DARK_RED + "-" + ChatColor.WHITE +") %player%";
    public static String PLAYER_JOIN = "("+ ChatColor.GREEN + "-" + ChatColor.WHITE +") %player%";

    public static String CREDITS = "Naruto UHC - Plugin by RqndomHax - https://github.com/rqndomhax";

    public static void showDeath(MPlayer player, boolean showRoleOnDeath) {
        Bukkit.broadcastMessage(ChatColor.YELLOW + "----------------------");
        if (player.role == null || showRoleOnDeath)
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.DARK_GREEN + Bukkit.getOfflinePlayer(player.uuid).getName() + ChatColor.DARK_GREEN + " est mort");
        else
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.DARK_GREEN + Bukkit.getOfflinePlayer(player.uuid).getName() + ChatColor.DARK_GREEN + " est mort et il était "
                + ChatColor.BOLD + "" + ChatColor.DARK_GREEN + player.role);
        Bukkit.broadcastMessage(ChatColor.YELLOW + "----------------------");
    }

    public static void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + "-----");
        sender.sendMessage("Naruto UHC is inspired by the Naruto's anime");
        sender.sendMessage(CREDITS);
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.DARK_AQUA + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.DARK_PURPLE + "-----");
    }

    public static void sendMessagesToPlayers(Setup setup, String message) {
        for (MPlayer mPlayer : setup.getGame().getGamePlayers()) {
            if (mPlayer == null) continue;
            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;
            player.sendMessage(message);
        }
    }
}
