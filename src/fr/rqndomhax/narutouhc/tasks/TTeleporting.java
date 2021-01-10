/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TTeleporting extends BukkitRunnable {

    private final Setup setup;
    public int i;

    public TTeleporting(Setup setup, int i) {
        this.setup = setup;
        this.i = i;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (i == 10 || i == 15 || i == 30 || i == 60 || i <= 5 && i > 0)
            sendInfos();

        if (i == 0) {
            setup.getGame().getGameInfo().setGameState(setup.getGame().getGameInfo().getGameState().nextGameState());

            if (setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_INVINCIBILITY)) {
                MGameActions.removePlatform(setup.getGame().getGamePlayers());
                new TTimer(setup, setup.getGame().getGameInfo().getmRules().invincibilityTime);
            }

            cancel();
            return;
        }
        i--;
    }

    private void sendInfos() {
        for (MPlayer mPlayer : setup.getGame().getGamePlayers()) {
            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;

            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
            player.sendTitle(ChatColor.GOLD + String.valueOf(i), "");
        }
    }

}
