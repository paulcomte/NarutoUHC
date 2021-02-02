/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
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
    List<Tasks> remainingTasks = new ArrayList<>();
    Task task = null;
    int rawTime = 0;
    public int time = 0;
    int roleRemainingTime = 0;
    public int episode = 0;

    public TMain(Setup setup) {
        this.setup = setup;
        remainingTasks.addAll(Arrays.asList(Tasks.values()));
        roleRemainingTime = setup.getGame().getGameInfo().getMRules().rolesAnnounce;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (!hasRoles)
            checkRoles();
        checkEpisode();
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

    private void checkEpisode() {
        if (episode == 0)
            return;
        int remaining = ((20*60 * episode) - (time));

        if (remaining == 30)
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED_30S);

        if (remaining == 0) {
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED.replace("%episode%", String.valueOf(episode)));
            episode++;
            for (MPlayer player : setup.getGame().getGamePlayers()) {
                if (player.isDead) continue;
                if (player.role == null) continue;
                player.role.onNewEpisode(episode);
            }
        }
    }

    private void checkRoles() {
        if (episode == 0)
            return;
        int r = roleRemainingTime - time;

        if (r < 0) {
            hasRoles = true;
            return;
        }

        if (r == 45*60 | r == 30*60 || r == 15*60 || r == 10*60 || r == 5*60 || r == 60) {
            if (r == 60)
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r/60))
                        .replace("secondes", "minute"));
            else
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r/60))
                        .replace("secondes", "minutes"));
        }

        if (r == 45 ||r == 30 || r == 15 || r == 10 || r <= 5 && r > 0) {
            if (r == 1)
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCE_IN
                        .replace("%time%", String.valueOf(r)));
        }

        if (r == 0) {
            setup.getRole().dispatchRoles();
            hasRoles = true;
            Bukkit.broadcastMessage(Messages.ROLES_ANNOUNCED);
        }
    }
}
