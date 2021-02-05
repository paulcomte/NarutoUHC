/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.tasks.role.Timers;
import fr.rqndomhax.narutouhc.utils.tools.DistanceRadius;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TNeji extends BukkitRunnable {

    private final Setup setup;
    private final GamePlayer neji;
    private final GamePlayer selected;
    private final Player nejiPlayer;
    private final Player selectedPlayer;
    int remainingTime = Timers.NEJI;

    public TNeji(Setup setup, GamePlayer neji, GamePlayer selected, Player nejiPlayer, Player selectedPlayer) {
        this.setup = setup;
        this.neji = neji;
        this.selected = selected;
        this.nejiPlayer = nejiPlayer;
        this.selectedPlayer = selectedPlayer;
        // TODO SEND MESSAGE ANALYZING
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || nejiPlayer == null || neji == null || neji.isDead) {
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
