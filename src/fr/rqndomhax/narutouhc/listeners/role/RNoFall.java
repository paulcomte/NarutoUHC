/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class RNoFall implements Listener {

    private final Setup setup;

    public RNoFall(Setup setup) {
        this.setup = setup;
    }

    public void onFall(EntityDamageEvent e) {
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            return;

        if ((e.getEntity() instanceof Player))
            return;

        Player player = (Player) e.getEntity();

        if (player == null)
            return;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null || gamePlayer.role.getRole() == null)
            return;

        Roles role = gamePlayer.role.getRole();

        if (role.equals(Roles.GAARA) || role.equals(Roles.FU) || role.equals(Roles.GAI))
            e.setCancelled(true);
    }
}
