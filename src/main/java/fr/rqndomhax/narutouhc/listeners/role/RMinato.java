/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.role;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.role.shinobi.KakashiHatake;
import fr.rqndomhax.narutouhc.role.shinobi.Minato;
import fr.rqndomhax.narutouhc.utils.Chrono;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Sound;
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

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null || gamePlayer.role.getRole() == null)
            return;

        RoleInfo tmp = gamePlayer.role;
        if ((gamePlayer.role instanceof KakashiHatake) && ((KakashiHatake) gamePlayer.role).stolenRole != null)
            tmp = ((KakashiHatake) gamePlayer.role).stolenRole;

        if (!(tmp instanceof Minato))
            return;

        Minato role = (Minato) tmp;

        if (player.getItemInHand() == null || !player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() || !player.getItemInHand().getItemMeta().getDisplayName().equals(role.item.getItemMeta().getDisplayName()) || !player.getItemInHand().getType().equals(role.item.getType()))
            return;

        int lastArrow = (int) ((role.lastArrow - System.currentTimeMillis()) / 1000);

        if (lastArrow >= -20) {
            player.sendMessage(Messages.ROLE_ITEM_COOLDOWN.replace("%time%", Chrono.timeToString(20 - Math.abs(lastArrow))));
            return;
        }
        role.lastArrow = System.currentTimeMillis();

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
            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
        }
    }
}
