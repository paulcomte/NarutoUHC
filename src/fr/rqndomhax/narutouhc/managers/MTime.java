/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.tasks.TTeleporting;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class MTime extends BukkitRunnable {

    private final Setup setup;
    public int time = 0;

    private int timeBeforeResize;
    private int timeBeforeTP;
    private boolean borderTaskFinished = false;
    private boolean hasTeleported = false;

    public MTime(Setup setup) {
        this.setup = setup;
        updateVars();
        runTaskTimer(setup.getMain(), 0, 20);
    }

    private void updateVars() {
        timeBeforeTP = setup.getGame().getGameInfo().getmRules().startDuration;
        timeBeforeResize = setup.getGame().getGameInfo().getmBorder().timeBeforeResize;
    }

    @Override
    public void run() {

        if (!hasTeleported)
            checkTeleport();

        else if (!borderTaskFinished)
            checkBorderTask();

        time++;
    }

    private void checkTeleport() {

        int r = timeBeforeTP - time;

        if (r == 30 || r == 15 || r <= 5 && r > 0)
            MGameActions.sendInfos(setup.getGame().getGamePlayers(), r);

        else if (r == 0) {
            new TTeleporting(setup, setup.getGame().getGameInfo().getmRules().teleportingDuration);
            hasTeleported = true;
            time -= timeBeforeTP;
        }

    }

    private void checkBorderTask() {

        int r = timeBeforeResize - time;

        if (r == 30 || r == 15 || r <= 5 && r > 0)
            Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE.replace("%time%", String.valueOf(r)));

        else if (r == 0) {
            setup.getGame().getGameInfo().getmBorder().resizeBorder();
            Bukkit.broadcastMessage(Messages.WB_BORDER_RESIZING);
            borderTaskFinished = true;
            time -= timeBeforeResize;
        }

    }
}
