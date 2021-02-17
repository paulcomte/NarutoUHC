/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.utils.tools.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TCenter extends BukkitRunnable {

    private final Setup setup;

    public TCenter(Setup setup) {
        this.setup = setup;
        runTaskTimerAsynchronously(setup.getMain(), 0, 1);
    }

    @Override
    public void run() {

        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED)) {
            cancel();
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode().equals(GameMode.SPECTATOR))
                continue;

            ActionBar.send(player, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Centre " + updateArrow(player, setup.getGame().getGameRules().gameBorder.getLocation()));
        }
    }

    private String updateArrow(Player player, Location target) {
        Location location = player.getLocation();
        target.setY(location.getY());
        Vector dirToMiddle = target.toVector().subtract(player.getEyeLocation().toVector()).normalize();
        Integer distance = (int)Math.round(target.distance(location));
        Vector playerDirection = player.getEyeLocation().getDirection();
        double angle = dirToMiddle.angle(playerDirection);
        double det = dirToMiddle.getX() * playerDirection.getZ() - dirToMiddle.getZ() * playerDirection.getX();
        angle *= Math.signum(det);
        String arrow;
        if (angle > -0.39269908169872414D && angle < 0.39269908169872414D)
            arrow = "⬆";
        else if (angle > -1.1780972450961724D && angle < -0.39269908169872414D)
            arrow = "⬈";
        else if (angle < 1.1780972450961724D && angle > 0.39269908169872414D)
            arrow = "⬉";
        else if (angle > 1.1780972450961724D && angle < 1.9634954084936207D)
            arrow = "←";
        else if (angle < -1.1780972450961724D && angle > -1.9634954084936207D)
            arrow = "➡";
        else if (angle < -1.9634954084936207D && angle > -2.748893571891069D)
            arrow = "⬊";
        else if (angle > 1.9634954084936207D && angle < 2.748893571891069D)
            arrow = "⬋";
        else
            arrow = "⬇";

        return distance + " " + ChatColor.BOLD + arrow;
    }
}
