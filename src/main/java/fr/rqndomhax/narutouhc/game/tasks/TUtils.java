/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game.tasks;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public abstract class TUtils {

    static boolean hasMade = false;

    public static void checkEpisode(TMain mainTask) {
        if (mainTask.episode == 0)
            return;
        int remaining = ((5*60 * mainTask.episode) - (mainTask.time));

        if (remaining == 30)
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED_30S.replace("%episode%", String.valueOf(mainTask.episode)));

        if (remaining == 0) {
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED.replace("%episode%", String.valueOf(mainTask.episode)));
            mainTask.episode++;

            for (Player player : Bukkit.getOnlinePlayers()) {
                new Title(ChatColor.AQUA + "Épisode " + mainTask.episode, "", 10,10,10).send(player);
                player.playSound(player.getLocation(), Sound.LEVEL_UP,3,1);
            }

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

        if (!hasMade && r < 0) {
            mainTask.hasRoles = true;
            MGamePublicRoles.initAkatsukis(mainTask.getSetup());
            MGamePublicRoles.initOrochimarus(mainTask.getSetup());
            hasMade = true;
            return;
        }

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
            Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCED);
        }
    }

}
