/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import fr.rqndomhax.narutouhc.utils.Timers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TList extends BukkitRunnable {

    private final List<GamePlayer> knownPlayers = new ArrayList<>();
    public final List<GamePlayer> players;
    private final JavaPlugin plugin;
    public final Set<TDelayed> delayedTasks = new HashSet<>();
    int remainingTime = Timers.TEAM_LENGTH;


    public TList(List<GamePlayer> players, JavaPlugin plugin) {
        this.players = players;
        this.plugin = plugin;

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
            GamePlayer incoming = getNew();

            if (incoming == null) {
                cancel();
                return;
            }

            MGamePublicRoles.akatsukis.add(incoming);

            for (GamePlayer gamePlayer : players) {
                if (gamePlayer.role == null)
                    continue;

                RoleInfo tmp = PlayerManager.getRole(gamePlayer.role);

                if (tmp.getRole().equals(Roles.ITACHI)) {
                    delayedTasks.add(new TDelayed(incoming, gamePlayer, plugin));
                    continue;
                }

                if (!gamePlayer.role.getRole().getTeam().equals(Team.AKATSUKI))
                    continue;

                Player p = Bukkit.getPlayer(gamePlayer.uuid);

                if (p != null)
                    p.sendMessage(Messages.PREFIX + "Un nouveau joueur a rejoint votre équipe !");
            }

            if (knownPlayers.size() == 0) {
                cancel();
                return;
            }
            remainingTime = Timers.TEAM_LENGTH;
        }

        remainingTime--;
    }

    public void stopTasks() {
        for (TDelayed delayed : delayedTasks)
            if (delayed != null)
                delayed.cancel();
    }

    private GamePlayer getNew() {
        if (knownPlayers.size() == 0)
            return null;

        GamePlayer incoming = knownPlayers.get(0);

        if (incoming == null || incoming.isDead) {
            knownPlayers.remove(0);
            return getNew();
        }

        knownPlayers.remove(0);
        return incoming;
    }

}
