/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TList extends BukkitRunnable {

    private final List<GamePlayer> knownPlayers = new ArrayList<>();
    private final Set<GamePlayer> players;
    int remainingTime = 10*60;


    public TList(Set<GamePlayer> players, JavaPlugin plugin) {
        this.players = players;

        for (GamePlayer gamePlayer : players) {
            if (gamePlayer.role == null || gamePlayer.isDead)
                continue;
            if (gamePlayer.role.getRole().getTeam().equals(Team.AKATSUKI) || gamePlayer.role.getRole().equals(Roles.ITACHI))
                knownPlayers.add(gamePlayer);
        }

        runTaskTimerAsynchronously(plugin, 0, 20);
    }

    @Override
    public void run() {

        if (remainingTime == 0) {
            MGamePublicRoles.akatsukis.add(knownPlayers.get(0));
            knownPlayers.remove(0);

            for (GamePlayer gamePlayer : players) {
                if (gamePlayer.role == null)
                    continue;

                if (gamePlayer.role.getRole().getTeam().equals(Team.AKATSUKI) || gamePlayer.role.getRole().equals(Roles.ITACHI)) {
                    Player p = Bukkit.getPlayer(gamePlayer.uuid);

                    if (p != null)
                        p.sendMessage(Messages.PREFIX + "Un nouveau joueur a rejoint votre équipe !");
                }
            }
            if (knownPlayers.size() == 0) {
                cancel();
                return;
            }
            remainingTime = 10*60;
        }

        remainingTime--;
    }

}
