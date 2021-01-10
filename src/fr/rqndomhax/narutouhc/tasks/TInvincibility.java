/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TInvincibility extends BukkitRunnable {

    private final Setup setup;
    public int i;

    public TInvincibility(Setup setup, int i) {
        this.setup = setup;
        this.i = i;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (i == 30 || i == 60 || i == 15 || i == 10 || i <= 5 && i > 0) {
            if (i == 1)
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(i))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(i)));
        }
        if (i == 0) {
            setup.getGame().getGameInfo().setGameState(setup.getGame().getGameInfo().getGameState().nextGameState());
            Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED);
            cancel();
            return;
        }
        i--;
    }
}
