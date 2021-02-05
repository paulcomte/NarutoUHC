/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role;

import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.utils.tools.DistanceRadius;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TNeji extends BukkitRunnable {

    private final GamePlayer neji;
    private final GamePlayer selected;
    private final Player nejiPlayer;
    private final Player selectedPlayer;
    int remainingTime = Timers.NEJI;

    public TNeji(GamePlayer neji, GamePlayer selected, Player nejiPlayer, Player selectedPlayer, JavaPlugin plugin) {
        this.neji = neji;
        this.selected = selected;
        this.nejiPlayer = nejiPlayer;
        this.selectedPlayer = selectedPlayer;
        // TODO SEND MESSAGE ANALYZING
        runTaskTimerAsynchronously(plugin, 0, 20);
    }

    @Override
    public void run() {

        if (nejiPlayer == null || neji == null || neji.isDead) {
            cancel();
            return;
        }

        if (selectedPlayer == null || remainingTime == 0) {
            // TODO NEJI VERIFIED
            cancel();
            return;
        }

        if (DistanceRadius.getRadius(nejiPlayer.getLocation(), selectedPlayer.getLocation()) > 10) {
            // TODO NEJI CANCEL
            cancel();
            return;
        }

        remainingTime--;
    }

}
