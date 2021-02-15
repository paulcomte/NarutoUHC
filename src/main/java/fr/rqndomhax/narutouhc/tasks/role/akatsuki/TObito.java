/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TObito extends BukkitRunnable {

    private int remainingTime = 2*60;
    private final Setup setup;
    private final GamePlayer gamePlayer;

    public TObito(Setup setup, GamePlayer gamePlayer) {
        this.setup = setup;
        this.gamePlayer = gamePlayer;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || gamePlayer == null || gamePlayer.isDead) {
            cancel();
            return;
        }
        if (remainingTime == 0) {
            Player player = Bukkit.getPlayer(gamePlayer.uuid);
            if (player == null) {
                cancel();
                return;
            }

            for (PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.INVISIBILITY) && effect.getAmplifier() == 0)
                    player.removePotionEffect(effect.getType());
                if (effect.getType().equals(PotionEffectType.SPEED) && effect.getAmplifier() == 0)
                    player.removePotionEffect(effect.getType());
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                onlinePlayer.showPlayer(player);
            cancel();
        }
        remainingTime--;
    }

}
