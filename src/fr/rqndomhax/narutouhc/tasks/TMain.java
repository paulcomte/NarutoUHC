/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TMain extends BukkitRunnable {

    private final Setup setup;
    public boolean lastTaskFinished = true;
    public boolean isAlive = true;
    List<Tasks> remainingTasks = new ArrayList<>();
    int rawTime = 0;
    public int time = 0;
    public int episode = 0;

    public TMain(Setup setup) {
        this.setup = setup;
        remainingTasks.addAll(Arrays.asList(Tasks.values()));
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (lastTaskFinished) {
            try {
                if (!startNextTask())
                    cancel();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        System.out.println(rawTime);
        rawTime++;
    }

    public Setup getSetup() {
        return setup;
    }

    private boolean startNextTask() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if (remainingTasks.isEmpty())
            return false;
        remainingTasks.get(0).getRunnable().getDeclaredConstructors()[0].newInstance(this);
        remainingTasks.remove(0);
        return true;
    }
}
