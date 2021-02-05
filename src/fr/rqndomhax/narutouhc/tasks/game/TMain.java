/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TMain extends BukkitRunnable {

    private final Setup setup;
    public boolean lastTaskFinished = true;
    public boolean hasRoles = false;
    public boolean lobbyRemoved = false;
    public List<Tasks> remainingTasks = new ArrayList<>();
    public Task task = null;
    int rawTime = 0;
    public int time = 0;
    public int roleRemainingTime = 0;
    public int episode = 0;

    public TMain(Setup setup) {
        this.setup = setup;
        remainingTasks.addAll(Arrays.asList(Tasks.values()));
        if (setup.getGame().getGameRules().activatedScenarios.contains(Scenarios.MEETUP)) {
            remainingTasks.remove(Tasks.WAIT);
            remainingTasks.remove(Tasks.INVINCIBILITY);
            remainingTasks.remove(Tasks.PREPARATION);
        }
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        TUtils.checkEpisode(this);
        if (!hasRoles)
            TUtils.checkRoles(this);
        if (lastTaskFinished) {
            try {
                if (!Tasks.startNextTask(this))
                    cancel();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        else
            if (task != null)
                task.loop();
        // TODO - REMOVE
        // MGameStatus.checkWin(setup);
        rawTime++;
    }

    public Setup getSetup() {
        return setup;
    }
}
