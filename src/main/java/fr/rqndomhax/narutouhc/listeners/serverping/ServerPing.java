/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.serverping;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.managers.MGameStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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

        String hostName = "RqndomHax";
        if (GameInfo.gameHost != null)
            hostName = Bukkit.getOfflinePlayer(GameInfo.gameHost).getName();

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
                if (GameInfo.gameHost != null) {
                    e.setMotd(ChatColor.GOLD + "En attente de joueurs..." +
                            "\n" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                }
                else
                    e.setMotd(ChatColor.GOLD + "En attente d'hôte...");
                e.setMaxPlayers(setup.getGame().getGameRules().activatedRoles.size());
                break;
            case LOBBY_STARTING:
                e.setMotd(ChatColor.LIGHT_PURPLE + "Démarrage de la partie en cours..." +
                        "\n" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                e.setMaxPlayers(setup.getGame().getGameRules().activatedRoles.size());
                break;
            case PREPARATION:
                if (!setup.getGame().getGameRules().allowSpectators)
                    e.setMotd(ChatColor.DARK_GREEN + "Phase de préparation des joueurs - " + ChatColor.YELLOW + "Spectateurs: " + ChatColor.RED + "OFF" +
                            "\n" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                else
                    e.setMotd(ChatColor.DARK_GREEN + "Phase de préparation des joueurs - " + ChatColor.YELLOW + "Spectateurs: " + ChatColor.GREEN + "ON" +
                            "\n" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                break;
            case MEETUP:
                if (!setup.getGame().getGameRules().allowSpectators)
                    e.setMotd(ChatColor.AQUA + "Phase de combat" +
                            "\n" + ChatColor.YELLOW + "Spectateurs: " + ChatColor.RED + "OFF" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                else
                    e.setMotd(ChatColor.DARK_GREEN + "Phase de combat" +
                            "\n" + ChatColor.YELLOW + "Spectateurs: " + ChatColor.GREEN + "ON" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                break;
            case FINISHED:
                if (MGameStatus.winners == null)
                    e.setMotd(ChatColor.DARK_AQUA + "Fin de la partie !" + ChatColor.YELLOW + "Equipe gaganate: " + ChatColor.DARK_RED + "Aucune" +
                            "\n" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                else
                    e.setMotd(ChatColor.DARK_AQUA + "Fin de la partie !" + ChatColor.YELLOW + "Equipe gaganate: " + ChatColor.AQUA + MGameStatus.winners.getTeamName() +
                            "\n" + ChatColor.GOLD + "Hôte: " + ChatColor.DARK_AQUA + hostName);
                break;
            default:
                break;
        }
    }

}
