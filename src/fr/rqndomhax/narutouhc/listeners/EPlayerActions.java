/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EPlayerActions implements Listener {

    private final Setup setup;

    public EPlayerActions(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {

        e.setDeathMessage("");

        if (!setup.getGame().getGameInfo().getmRules().showDeathMessage) return;

        MPlayer player = setup.getGame().getMPlayer(e.getEntity().getUniqueId());

        if (player == null)
            return;

        player.deathInventory.saveInventory(e.getEntity());
        player.isDead = true;

        if (setup.getGame().getGameInfo().getmRules().showRoleOnDeath) {
            if (player.role != null) {
                Bukkit.broadcastMessage(Messages.PLAYER_DEATH_WITH_ROLE
                        .replace("%player%", e.getEntity().getName())
                        .replace("%role%", player.role.name()));
                return;
            }
            Bukkit.broadcastMessage(Messages.PLAYER_DEATH_WITH_ROLE_NONE
                    .replace("%player%", e.getEntity().getName()));
        }
        else
            Bukkit.broadcastMessage(Messages.PLAYER_DEATH_WITHOUT_ROLE
                    .replace("%player", e.getEntity().getName()));

    }
}
