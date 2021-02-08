/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game;

import fr.rqndomhax.narutouhc.game.tasks.TMain;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.Game;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EpisodeManager {

    public static void checkEpisode(TMain mainTask) {
        if (mainTask.episode == 0)
            return;
        int remaining = ((20*60 * mainTask.episode) - (mainTask.time));

        if (remaining == 30)
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED_30S.replace("%episode%", String.valueOf(mainTask.episode)));

        if (remaining == 0) {
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED.replace("%episode%", String.valueOf(mainTask.episode)));
            mainTask.episode++;

            for (Player player : Bukkit.getOnlinePlayers()) {
                new Title(ChatColor.AQUA + "Épisode " + mainTask.episode, "", 10,10,10);
                player.playSound(player.getLocation(), Sound.ORB_PICKUP,3,0);
            }

            for (GamePlayer player : mainTask.getSetup().getGame().getGamePlayers()) {

                if (player.isDead) continue;
                if (player.role == null) continue;
                player.role.onNewEpisode(mainTask.episode);

            }
        }
    }
}
