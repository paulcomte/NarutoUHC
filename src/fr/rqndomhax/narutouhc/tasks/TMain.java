/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.MGameStatus;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TMain extends BukkitRunnable {

    private final Setup setup;
    public boolean lastTaskFinished = true;
    public boolean isAlive = true;
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
        if (setup.getGame().getGameInfo().getMRules().activatedScenarios.contains(Scenarios.MEETUP)) {
            remainingTasks.remove(Tasks.WAIT);
            remainingTasks.remove(Tasks.INVINCIBILITY);
            remainingTasks.remove(Tasks.PREPARATION);
        }
        roleRemainingTime = setup.getGame().getGameInfo().getMRules().rolesAnnounce;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        TUtils.checkEpisode(this);
        if (!hasRoles)
            TUtils.checkRoles(this);
        if (lastTaskFinished) {
            try {
                if (!startNextTask())
                    cancel();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        else
            if (task != null)
                task.loop();
        MGameStatus.checkWin(setup);
        rawTime++;
    }

    public Setup getSetup() {
        return setup;
    }

    private boolean startNextTask() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (remainingTasks.isEmpty())
            return false;
        task = (Task) remainingTasks.get(0).getRunnable().getDeclaredConstructors()[0].newInstance(this);
        remainingTasks.remove(0);
        return true;
    }
}
