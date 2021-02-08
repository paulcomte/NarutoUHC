/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.solos;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.role.solos.Danzo;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TDanzo extends BukkitRunnable {

    int remainingTime = 10*60;
    List<GamePlayer> knownUchiwas = new ArrayList<>();
    private final Setup setup;
    private final Danzo danzo;

    public TDanzo(Setup setup, Danzo danzo) {
        this.setup = setup;
        this.danzo = danzo;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (danzo == null || danzo.getGamePlayer() == null || setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || knownUchiwas.size() == 0) {
            cancel();
            return;
        }

        if (danzo.getGamePlayer().isDead || Bukkit.getPlayer(danzo.getGamePlayer().uuid) == null)
            return;

        if (remainingTime == 0) {
            GamePlayer uchiwa = getNextUchiwa();
            // TODO SEND UCHIWA
            remainingTime = 10*60;
        }

        remainingTime--;
    }

    private GamePlayer getNextUchiwa() {
        if (knownUchiwas.size() == 0)
            return null;

        GamePlayer uchiwa = knownUchiwas.get(0);

        knownUchiwas.remove(0);

        if (uchiwa == null || uchiwa.isDead)
            return getNextUchiwa();

        return uchiwa;
    }
}
