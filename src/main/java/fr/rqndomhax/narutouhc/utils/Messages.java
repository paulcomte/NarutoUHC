/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Messages {

    public static String PREFIX = ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC ❙ " + ChatColor.RESET;
    public static String SEPARATORS = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "                         " + ChatColor.DARK_GRAY + "[" + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC" + ChatColor.DARK_GRAY + "]" + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "                         ";

    public static String PLUGIN_INIT_STARTED = "[Naruto UHC] PLUGIN INITIALIZING !";
    public static String PLUGIN_CREATING_WORLDS = "[Naruto UHC] LOADING WORLDS !";
    public static String PLUGIN_GENERATING_LOBBY = "[Naruto UHC] GENERATING LOBBY !";
    public static String PLUGIN_DELETING_WORLD = "[Naruto UHC] DELETING OLD WORLD !";
    public static String PLUGIN_INTERNAL_ERROR = "[Naruto UHC] AN INTERNAL ERROR HAS OCCURED !";
    public static String PLUGIN_INIT_EVENTS = "[Naruto UHC] REGISTERING EVENTS !";
    public static String PLUGIN_INIT_COMMANDS = "[Naruto UHC] REGISTERING COMMANDS !";
    public static String PLUGIN_LAST_TASKS = "[Naruto UHC] FINISHING LAST TASKS !";
    public static String PLUGIN_INITIALIZED = "[Naruto UHC] PLUGIN INITIALIZED !";
    public static String SERVER_STARTING = "Server starting !";
    public static String PLUGIN_MAP_NOT_PRESENT = "[Naruto UHC] You need to have 'NARUTO_UNIVERSE' map in your server folder !";
    public static String WHISPERS_OFF = PREFIX + "Les messages privés sont désactivés !";
    public static String CANNOT_CREATE_VILLAGER = "[Naruto UHC] CANNOT SPAWN NPC !";
    public static String CANNOT_INIT = "[Naruto UHC] AN ERROR HAS OCCURRED DURING PLUGIN INITIALIZATION !";
    public static String CANNOT_LOAD_WORLD_BORDER = "[Naruto UHC] CANNOT GET WORLD BORDER !";

    public static String CANNOT_LOAD_CONFIG = PREFIX + "Chargement de la configuration impossible !";

    public static String NEED_PLAYER = PREFIX + "Seul un joueur peut effectuer cette action !";
    public static String NEED_ITEM_IN_HAND = PREFIX + "Vous devez tenir un objet en main !";
    public static String PLAYER_NOT_EXIST = PREFIX + "Ce joueur n'est pas connecté sur le serveur !";
    public static String NOT_ALLOWED = PREFIX + "Vous n'êtes pas autorisé à rejoindre la partie !";
    public static String FULL = PREFIX + "La partie est complète !";
    public static String NOT_IN_LOBBY = PREFIX + "Vous ne pouvez pas effectuer cette action en dehors de la phase lobby !";
    public static String NOT_IN_GAME = PREFIX + "Vous ne pouvez pas effectuer cette action car la partie n'est pas en cours !";
    public static String GAME_ALREADY_STARTED = PREFIX + "La partie a déjà commencée !";
    public static String GAME_FINISHED = PREFIX + "Vous ne pouvez pas effectuer cette action car la partie est terminée !";

    public static String INVINCIBILITY_FINISHED_IN = PREFIX + "La phase d'invincibilité sera terminée dans %time% secondes";
    public static String INVINCIBILITY_FINISHED = PREFIX + "La phase d'invincibilité vient de se terminer !";

    public static String NARUTO_MAP_TP = PREFIX + "Téléportation dans l'univers de naruto dans %time% secondes !";
    public static String NARUTO_MAP_TPING = PREFIX + "Téléportation dans l'univers de naruto !";

    public static String WB_TIME_BEFORE_BORDER_RESIZE = PREFIX + "Réduction de la bordure dans %time% secondes !";
    public static String WB_BORDER_RESIZING = PREFIX + "La réduction de la bordure vient de commencer, dirigez vous vers le centre !";

    public static String ROLES_ANNOUNCE_IN = PREFIX + "Annonce des rôles dans %time% secondes";

    public static String ADMIN_ROLES_NOT_ENABLE = PREFIX + "Les rôles admins n'ont pas été activés !";
    public static String ADMIN_PLAYER_ROLE_NOT_PRESENT = PREFIX + "Ce joueur ne possède aucun rôle !";
    public static String ADMIN_ROLE_ALREADY_GAVE = PREFIX + "Ce rôle a déjà été attribué à un joueur !";
    public static String ROLE_NOT_PRESENT = PREFIX + "Ce rôle n'a pas été activé !";
    public static String ADMIN_ROLE_ADDED = PREFIX + "Vous avez bien ajouté le rôle %role% au joueur %player% !";
    public static String ADMIN_ROLE_REMOVED = PREFIX + "Vous avez bien supprimé le rôle %role% du joueur %player% !";

    public static String PLAYER_LEFT = "("+ ChatColor.DARK_RED + "-" + ChatColor.WHITE +") %player%";
    public static String PLAYER_JOIN = "("+ ChatColor.GREEN + "+" + ChatColor.WHITE +") %player%";

    public static String GAME_DESIGNER_CREDITS = "Un mode de jeu conçu par Laynoks - Syknos - Losgateaux - RqndomHax";
    public static String HELPER_CREDITS = "Un grand merci à Spat'";
    public static String DEV_CREDITS = "Naruto UHC - Plugin développé par RqndomHax - https://github.com/rqndomhax";

    public static String COMMAND_ONLY_HOST = PREFIX + "Seul l'hôte de la partie peut effectuer cette action !";
    public static String COMMAND_ONLY_CONSOLE = PREFIX + "Seul la console peut effectuer cette action !";
    public static String CHAT_DISABLED = PREFIX + "Le chat est désactivé en partie !";

    public static String HOST_INVENTORY_BEGINNING_SAVED = PREFIX + "L'inventaire de départ a bien été mis à jour !";
    public static String HOST_INVENTORY_DEATH_SAVED = PREFIX + "L'inventaire de mort a bien été mis à jour !";
    public static String HOST_INVENTORY_CANCEL = PREFIX + "L'inventaire n'a pas été mis à jour !";
    public static String HOST_INVENTORY_EDIT = PREFIX + "Modification de l'inventaire\n" + ChatColor.GREEN + "/save pour sauvegarder l'inventaire\n" + ChatColor.RED + "/cancel pour annuler la modification de l'inventaire";
    public static String HOST_INVENTORY_NOT_EDIT = PREFIX + "Vous n'éditez aucun inventaire !";

    public static String HOST_NEED_OFFLINE = PREFIX + "L'hôte %player% doit être déconnecté afin d'effectuer cette commande !";
    public static String HOST_NOW_SET = PREFIX + "Le joueur %player% est maintenant l'hôte de la partie !";
    public static String HOST_SET = PREFIX + "Vous êtes maintenant l'hôte de la partie !";

    public static String HOST_USAGE_SET = PREFIX + "Veuillez respecter la syntaxe suivante : /h set <player>";
    public static String HOST_USAGE_DELETE = PREFIX + "Veuillez respecter la syntaxe suivante : /h delete <player>";
    public static String HOST_USAGE_PROMOTE = PREFIX + "Veuillez respecter la syntaxe suivante : /h promote <player>";
    public static String HOST_USAGE_BAN = PREFIX + "Veuillez respecter la syntaxe suivante : /h ban <player>";
    public static String HOST_USAGE_UNBAN = PREFIX + "Veuillez respecter la syntaxe suivante : /h unban <player>";
    public static String HOST_USAGE_KICK = PREFIX + "Veuillez respecter la syntaxe suivante : /h kick <player>";
    public static String HOST_USAGE_AN = PREFIX + "Veuillez respecter la syntaxe suivante : /h an <texte>";
    public static String PLAYER_USAGE_WHISPER = PREFIX + "Veuillez respecter la syntaxe suivante : /msg <player> <message>";

    public static String HOST_NEED_ANOTHER_TEAM = PREFIX + "Vous devez avoir un minimum de 2 camps différents.";
    public static String HOST_NEED_MORE_PLAYERS = PREFIX + "Vous devez avoir le même nombre de joueur que de rôle afin de lancer la partie.";

    public static String HOST_INVENTORY_ALREADY_IN_EDIT = PREFIX + "Cette inventaire est déjà en cours de modification !";
    
    public static String PLAYER_NOT_CONNECTED = PREFIX + "Le joueur %player% n'est pas en ligne !";

    public static String PLAYER_BANNED = PREFIX + "Vous avez été banni(e) de la partie !";
    public static String PLAYER_NOT_BANNED = PREFIX + "Le joueur %player% n'est pas banni(e) !";

    public static String NOT_PLAYING = PREFIX + "Vous ne jouez pas au sein de cette partie !";

    public static String PLAYER_NOW_BANNED = PREFIX + "Le joueur %player% a été banni(e) avec succès !";
    public static String PLAYER_NOW_UNBANNED = PREFIX + "Le joueur %player% n'est maintenant plus banni(e)";

    public static String PLAYERS_HEALED = PREFIX + "Tous les joueurs ont été soignés !";
    public static String PLAYER_NOT_PLAYING = PREFIX + "Ce joueur ne joue pas au sein de la partie !";
    public static String PLAYER_DEAD = PREFIX + "Vous ne pouvez pas effectuer cette action car vous êtes mort !";
    public static String PLAYER_NOT_DEAD = PREFIX + "Ce joueur n'est pas mort !";
    public static String PLAYER_HAS_BEEN_REVIVED = PREFIX + "Le joueur %player% a été ressuscité par un hôte !";
    public static String PLAYER_RESURRECTED = PREFIX + "Vous avez été ressuscité !";
    public static String PLAYER_NOW_RESURRECTED = PREFIX + "Vous avez ressuscité %player% !";

    public static String PLAYER_KICKED = PREFIX + "Vous avez été expulsé(e) de cette partie !";
    public static String PLAYER_NOW_KICKED = PREFIX + "Le joueur %player% a été expulsé(e) avec succès !";

    public static String HOST_TOO_MANY_CO_HOST = PREFIX + "Le nombre maximum de co-hôte a déjà été atteint !";
    public static String HOST_ALREADY_NOT_CO_HOST = PREFIX + "Le joueur %player% n'est pas un co-hôte !";
    public static String HOST_ALREADY_CO_HOST = PREFIX + "Le joueur %player% est déjà un co-hôte !";
    public static String HOST_NOW_CO_HOST = PREFIX + "Le joueur %player% est maintenant un co-hôte !";
    public static String HOST_NOW_DELETED_CO_HOST = PREFIX + "Le joueur %player% n'est plus un co-hôte !";
    public static String HOST_DEMOTED = PREFIX + "Vous avez perdu votre rang de co-hôte !";
    public static String HOST_PROMOTED = PREFIX + "Vous êtes maintenant un co-hôte !";

    public static String ROLE_CAPACITY = PREFIX + "Vous pouvez de nouveau utiliser votre capacité spéciale";
    public static String ROLE_CLAIM = PREFIX + "Vous avez accès à votre /na claim";
    public static String NO_ROLE = PREFIX + "Vous ne possédez pas de rôle !";
    public static String NOT_YOUR_ROLE = PREFIX + "Cette commande n'appartient pas à votre rôle !";
    public static String HIDDEN_COMPO = PREFIX + "La composition est cachée !";
    public static String ROLES_NOT_GAVE = PREFIX + "Les rôles n'ont pas été encore attribués !";
    public static String ROLE_NO_ITEMS = PREFIX + "Votre rôle ne dispose d'aucun équipement supplémentaire !";
    public static String ROLE_ITEMS_NEED_SPACE = PREFIX + "Votre inventaire doit avoir la place nécessaire pour récupérer %n% objets";
    public static String ROLE_ITEMS_OBTAINED = PREFIX + "Vous avez reçu l'équipement de votre rôle !";
    public static String ROLE_NO_MORE_USES = PREFIX + "Votre rôle ne dispose d'aucune utilisation restante !";
    public static String ROLE_ITEM_COOLDOWN = PREFIX + "L'objet ne peut pas être de nouveau utilisé avant %time% !";

    public static String FORCE_WRONG_MAP_BORDER = PREFIX + "Vous ne pouvez pas forcer la bordure !";
    public static String BORDER_ALREADY_ACTIVATED = PREFIX + "La bordure a déjà été activée !";
    public static String ROLES_ALREADY_ACTIVATED = PREFIX + "L'annonce des rôles a déjà activée !";
    public static String TELEPORT_ALREADY_ACTIVATED = PREFIX + "La téléportation dans l'univers de naruto a déjà été activée !";
    public static String TELEPORT_FORCE_WRONG = PREFIX + "Vous devez être en phase de préparation pour forcer la téléportation !";

    public static String EPISODE_FINISHED_30S = PREFIX + "L'épisode %episode% se termine dans 30 secondes !";
    public static String EPISODE_FINISHED = ChatColor.BLACK + "-------- Fin Episode %episode% --------";

    public static void showDeath(GamePlayer player, boolean showRoleOnDeath) {
        Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                         ");
        if (player.role == null || !showRoleOnDeath)
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.DARK_GREEN + ChatColor.MAGIC + Bukkit.getOfflinePlayer(player.uuid).getName() + ChatColor.RESET + ChatColor.BOLD + ChatColor.DARK_GREEN + " est mort");
        else
            Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.DARK_GREEN + ChatColor.MAGIC + Bukkit.getOfflinePlayer(player.uuid).getName() + ChatColor.RESET + ChatColor.BOLD + ChatColor.DARK_GREEN + " est mort et il était "
                + ChatColor.BOLD + player.role.getRole().getRoleName());
        Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "                         ");
    }

    public static void showList(Player player, List<GamePlayer> list) {
        StringBuilder sb = new StringBuilder();

        for (GamePlayer gamePlayer : list) {
            if (gamePlayer.isDead)
                sb.append(ChatColor.STRIKETHROUGH).append(gamePlayer.name).append(ChatColor.RESET);
            else
                sb.append(gamePlayer.name);
            sb.append("  ");
        }
        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(sb.toString());
    }

    public static void showHelp(CommandSender sender) {
        sender.sendMessage(Messages.SEPARATORS);
        sender.sendMessage("Naruto UHC est un mode de jeu inspiré de l'animé de Naruto");
        sender.sendMessage(ChatColor.GREEN + GAME_DESIGNER_CREDITS);
        sender.sendMessage(ChatColor.GREEN + HELPER_CREDITS);
        sender.sendMessage(ChatColor.GOLD + DEV_CREDITS);
    }
}
