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
import fr.rqndomhax.narutouhc.role.shinobi.KillerBee;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleEffect;

public class RKillerBee implements Listener {

    private final Setup setup;

    public RKillerBee(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {

        if (!setup.getGame().getGameRules().activatedRoles.contains(Roles.KILLER_BEE))
            return;

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR))
            return;

        Player player = e.getPlayer();

        if (player == null)
            return;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null)
            return;

        RoleInfo tmp = PlayerManager.getRole(gamePlayer.role);

        if (!(tmp instanceof KillerBee))
            return;

        if (!player.getItemInHand().equals(((KillerBee) tmp).item))
            return;

        e.setCancelled(true);

        player.setItemInHand(null);

        ParticleEffect.EXPLOSION_HUGE.display(player.getLocation());
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4*60*20, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 4*60*20, 3, false, false));
        ((KillerBee) tmp).itemUsedStamp = System.currentTimeMillis();
    }
}
