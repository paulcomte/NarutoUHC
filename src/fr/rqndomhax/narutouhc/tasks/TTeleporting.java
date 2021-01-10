/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import org.bukkit.scheduler.BukkitRunnable;

public class TTeleporting extends BukkitRunnable {

    private final Setup setup;
    public int i;

    public TTeleporting(Setup setup, int i) {
        this.setup = setup;
        this.i = i;
        runTaskTimer(setup.getMain(), 0, 20);
        MGameActions.teleportPlayers1(setup.getGame().getGameInfo().getmRules().playerDispatchingSize, setup.getGame().getGamePlayers());
        MGameActions.removeLobby();
    }

    @Override
    public void run() {

        if (i == 10 || i == 15 || i == 30 || i == 60 || i <= 5 && i > 0)
            MGameActions.sendInfos(setup.getGame().getGamePlayers(), i);

        if (i == 0) {

            setup.getGame().getGameInfo().setGameState(GameState.GAME_INVINCIBILITY);

            MGameActions.removePlatform(setup.getGame().getGamePlayers());
            new TInvincibility(setup, setup.getGame().getGameInfo().getmRules().invincibilityDuration);

            cancel();
            return;
        }
        i--;
    }

}
