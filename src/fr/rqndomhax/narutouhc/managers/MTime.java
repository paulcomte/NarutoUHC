/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class MTime extends BukkitRunnable {

    private final Setup setup;
    public int time = 0;

    private int timeBeforeResize;
    private boolean borderTaskFinished = false;

    public MTime(Setup setup) {
        this.setup = setup;
        updateVars();
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    private void updateVars() {
        timeBeforeResize = setup.getGame().getGameInfo().getmBorder().timeBeforeResize;
    }

    @Override
    public void run() {

        if (!borderTaskFinished)
            checkBorderTask();

        time++;
    }

    private void checkBorderTask() {
        int r = timeBeforeResize - time;
        if (r == 30 || r == 15 || r <= 5 && r > 0)
            Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE.replace("%time%", String.valueOf(r)));
        else if (time == setup.getGame().getGameInfo().getmBorder().timeBeforeResize) {
            setup.getGame().getGameInfo().getmBorder().resizeBorder(setup);
            borderTaskFinished = true;
            Bukkit.broadcastMessage(Messages.WB_BORDER_RESIZING);
        }
    }

}
