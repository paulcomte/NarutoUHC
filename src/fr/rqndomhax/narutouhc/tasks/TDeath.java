/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TDeath extends BukkitRunnable {

    private final Setup setup;
    private final MPlayer mPlayer;
    private final MPlayer killer;
    public int timeLeft;

    public TDeath(Setup setup, MPlayer mPlayer, MPlayer killer, int timeLeft) {
        this.setup = setup;
        this.mPlayer = mPlayer;
        this.killer = killer;
        this.timeLeft = timeLeft;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (mPlayer == null) {
            cancel();
            return;
        }

        if (!mPlayer.isDead) {
            cancel();
            return;
        }

        if (timeLeft <= 5 && timeLeft > 0 || timeLeft == 10 || timeLeft == 15 || timeLeft == 30 || timeLeft == 60)
            MGameActions.sendInfo(mPlayer, timeLeft);

        if (timeLeft == 0) {
            Messages.showDeath(mPlayer, setup.getGame().getGameInfo().getmRules().showRoleOnDeath);
            mPlayer.deathLocation.getWorld().strikeLightningEffect(mPlayer.deathLocation);

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player != null)
                player.setHealth(0);

            if (killer != null)
                killer.kills.add(mPlayer.uuid);
            cancel();
        }

        timeLeft--;
    }

}
