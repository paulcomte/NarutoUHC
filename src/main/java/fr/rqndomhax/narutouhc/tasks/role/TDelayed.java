/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TDelayed extends BukkitRunnable {

    private final GamePlayer incoming;
    private final GamePlayer itachi;
    int remainingTime = 10*60;


    public TDelayed(GamePlayer incoming, GamePlayer itachi, JavaPlugin plugin) {
        this.incoming = incoming;
        this.itachi = itachi;

        runTaskTimerAsynchronously(plugin, 0, 20);
    }

    @Override
    public void run() {

        if (remainingTime == 0) {
            MGamePublicRoles.itachiAkatsukis.add(incoming);

            Player p = Bukkit.getPlayer(itachi.uuid);

            if (p != null)
                p.sendMessage(Messages.PREFIX + "Un nouveau joueur a rejoint votre équipe !");
            cancel();
            return;
        }

        remainingTime--;
    }

}
