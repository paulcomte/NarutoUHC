/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EPlayerActions implements Listener {

    private final Setup setup;

    public EPlayerActions(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        if (!setup.getGameInfo().getmRules().showDeathMessage) return;

        if (setup.getGameInfo().getmRules().showRoleOnDeath)
            Bukkit.broadcastMessage(Messages.PLAYER_DEATH_WITH_ROLE
                    .replace("%player%", e.getEntity().getName())
                    .replace("%role%", "TODO"));
        else
            Bukkit.broadcastMessage(Messages.PLAYER_DEATH_WITHOUT_ROLE
                    .replace("%player", e.getEntity().getName()));

    }
}
