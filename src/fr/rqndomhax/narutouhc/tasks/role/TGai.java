/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TGai extends BukkitRunnable {

    private int remainingTime = 10*60;
    private final Setup setup;
    private final GamePlayer player;

    public TGai(Setup setup, GamePlayer player) {
        this.setup = setup;
        this.player = player;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || player == null || player.isDead) {
            cancel();
            return;
        }
        if (remainingTime == 0) {
            Player p = Bukkit.getPlayer(player.uuid);
            if (p == null)
                player.isDead = true;
            else
                p.setMaxHealth(1);
            cancel();
        }
        remainingTime--;
    }

}
