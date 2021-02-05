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
import xyz.xenondevs.particle.ParticleEffect;

public class TNejiParticles extends BukkitRunnable {

    private final Setup setup;
    private final GamePlayer neji;
    private final GamePlayer current;
    private Player selectedPlayer;
    private Player nejiPlayer;
    public ParticleEffect effect = ParticleEffect.WATER_BUBBLE;

    public TNejiParticles(GamePlayer neji, GamePlayer current, Setup setup) {
        this.neji = neji;
        this.setup = setup;
        this.current = current;
        runTaskTimerAsynchronously(setup.getMain(), 0, 1);
    }

    @Override
    public void run() {
        if (setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || neji == null || current == null || current.isDead) {
            cancel();
            return;
        }
        if (nejiPlayer == null) {
            nejiPlayer = Bukkit.getPlayer(neji.uuid);
            return;
        }
        effect.display(selectedPlayer.getLocation(), nejiPlayer);
    }

}
