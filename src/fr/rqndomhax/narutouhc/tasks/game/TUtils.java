/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.game;

import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;

public abstract class TUtils {

    public static void checkEpisode(TMain mainTask) {
        if (mainTask.episode == 0)
            return;
        int remaining = ((20*60 * mainTask.episode) - (mainTask.time));

        if (remaining == 30)
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED_30S.replace("%episode%", String.valueOf(mainTask.episode)));

        if (remaining == 0) {
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED.replace("%episode%", String.valueOf(mainTask.episode)));
            mainTask.episode++;
            for (GamePlayer player : mainTask.getSetup().getGame().getGamePlayers()) {
                if (player.isDead) continue;
                if (player.role == null) continue;
                player.role.onNewEpisode(mainTask.episode);
            }
        }
    }

    public static void checkRoles(TMain mainTask) {
        if (mainTask.episode == 0)
            return;
        int r = mainTask.roleRemainingTime - mainTask.time;

        if (r == 45*60 | r == 30*60 || r == 15*60 || r == 10*60 || r == 5*60 || r == 60) {
            if (r == 60)
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r/60))
                        .replace("secondes", "minute"));
            else
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r/60))
                        .replace("secondes", "minutes"));
        }

        if (r == 45 ||r == 30 || r == 15 || r == 10 || r <= 5 && r > 0) {
            if (r == 1)
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r)));
        }

        if (r == 0) {
            mainTask.getSetup().getGame().getGameRole().dispatchRoles();
            mainTask.hasRoles = true;
            Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCED);
        }
    }

}
