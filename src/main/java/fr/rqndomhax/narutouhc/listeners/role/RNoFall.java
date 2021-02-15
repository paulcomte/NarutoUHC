/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.role.akatsuki.Konan;
import fr.rqndomhax.narutouhc.role.shinobi.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class RNoFall implements Listener {

    private final Setup setup;

    public RNoFall(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onFall(EntityDamageEvent e) {
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
            return;

        if (!(e.getEntity() instanceof Player))
            return;

        Player player = (Player) e.getEntity();

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null || gamePlayer.role.getRole() == null)
            return;

        RoleInfo tmp = gamePlayer.role;
        if ((gamePlayer.role instanceof KakashiHatake) && ((KakashiHatake) gamePlayer.role).stolenRole != null)
            tmp = ((KakashiHatake) gamePlayer.role).stolenRole;

        if ((tmp instanceof Gai && ((Gai) tmp).task != null) || (tmp instanceof Fu) || (tmp instanceof Gaara) || (tmp instanceof Konan)) {
            e.setCancelled(true);
            return;
        }

        if (tmp instanceof KillerBee) {
            if ((System.currentTimeMillis() - ((KillerBee) tmp).itemUsedStamp) / 1000 <= 4*60)
                e.setCancelled(true);
        }
    }
}
