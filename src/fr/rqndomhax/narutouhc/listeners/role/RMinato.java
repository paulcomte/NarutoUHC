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
import fr.rqndomhax.narutouhc.role.shinobi.Minato;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashMap;

public class RMinato implements Listener {
    private final Setup setup;

    private final HashMap<Player, Arrow> arrows = new HashMap<>();

    public RMinato(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent e) {
        if (!setup.getGame().getGameRules().activatedRoles.contains(Roles.MINATO))
            return;

        if (!(e.getEntity() instanceof Arrow))
            return;

        Arrow arrow = (Arrow) e.getEntity();

        if (arrow == null)
            return;

        if (!(arrow.getShooter() instanceof Player))
            return;

        Player player = (Player) e.getEntity().getShooter();

        if (player == null)
            return;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null || gamePlayer.role.getRole() == null || !gamePlayer.role.getRole().equals(Roles.MINATO))
            return;

        Minato role = (Minato) gamePlayer.role;

        if (role == null || player.getItemInHand() == null || !player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() || !player.getItemInHand().getItemMeta().getDisplayName().equals(role.item.getItemMeta().getDisplayName()) || !player.getItemInHand().getType().equals(role.item.getType()))
            return;

        arrows.put(player, arrow);
    }

    @EventHandler
    public void onProjectile(ProjectileHitEvent e) {
        if (!setup.getGame().getGameRules().activatedRoles.contains(Roles.MINATO))
            return;
        if (!(e.getEntity() instanceof Arrow))
            return;

        Arrow arrow = (Arrow) e.getEntity();

        if (arrow == null)
            return;

        if (!(arrow.getShooter() instanceof Player))
            return;

        Player player = (Player) e.getEntity().getShooter();

        if (player == null)
            return;

        Arrow value = arrows.get(player);

        if (value != null && value.equals(arrow)) {
            arrows.remove(player);
            arrow.getLocation().setPitch(player.getLocation().getPitch());
            arrow.getLocation().setYaw(player.getLocation().getYaw());
            player.teleport(arrow.getLocation());
            // TODO SOUND EFFECT
        }
    }
}
