/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.serverping;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.MGameStatus;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPing implements Listener {

    private final Setup setup;
    public static Pings currentPing = Pings.START;

    public ServerPing(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {

        switch (currentPing) {
            case START:
                e.setMotd(ChatColor.DARK_RED + "Démarrage du serveur...");
                e.setMaxPlayers(0);
                break;
            case WORLD_GENERATING:
                e.setMotd(ChatColor.RED + "Génération des mondes...");
                e.setMaxPlayers(0);
                break;
            case LOBBY_GENERATING:
                e.setMotd(ChatColor.DARK_PURPLE + "Génération du lobby...");
                e.setMaxPlayers(0);
                break;
            case LOBBY_WAITING:
                e.setMotd(ChatColor.GOLD + "En attente de joueurs");
                e.setMaxPlayers(setup.getGame().getGameRules().activatedRoles.size());
                break;
            case LOBBY_STARTING:
                e.setMotd(ChatColor.LIGHT_PURPLE + "Démarrage de la partie en cours...");
                e.setMaxPlayers(setup.getGame().getGameRules().activatedRoles.size());
                break;
            case PREPARATION:
                if (!setup.getGame().getGameRules().allowSpectators)
                    e.setMotd(ChatColor.DARK_GREEN + "Phase de préparation des joueurs\n" + ChatColor.YELLOW + "Spectateurs: " + ChatColor.RED + "OFF");
                else
                    e.setMotd(ChatColor.DARK_GREEN + "Phase de préparation des joueurs\n" + ChatColor.YELLOW + "Spectateurs: " + ChatColor.GREEN + "ON");
                break;
            case MEETUP:
                if (!setup.getGame().getGameRules().allowSpectators)
                    e.setMotd(ChatColor.AQUA + "Phase de combat\n" + ChatColor.YELLOW + "Spectateurs: " + ChatColor.RED + "OFF");
                else
                    e.setMotd(ChatColor.DARK_GREEN + "Phase de combat\n" + ChatColor.YELLOW + "Spectateurs: " + ChatColor.GREEN + "ON");
                break;
            case FINISHED:
                if (MGameStatus.winners == null)
                    e.setMotd(ChatColor.DARK_AQUA + "Fin de la partie !\n" + ChatColor.YELLOW + "Equipe gaganate: " + ChatColor.DARK_RED + "Aucune");
                else
                    e.setMotd(ChatColor.DARK_AQUA + "Fin de la partie !\n" + ChatColor.YELLOW + "Equipe gaganate: " + ChatColor.AQUA + MGameStatus.winners.getTeamName());
                break;
            default:
                break;
        }
    }

}
