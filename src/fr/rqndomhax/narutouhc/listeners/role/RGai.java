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
import fr.rqndomhax.narutouhc.role.shinobi.Gai;
import fr.rqndomhax.narutouhc.tasks.role.shinobi.TGai;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleEffect;

public class RGai implements Listener {

    private final Setup setup;

    public RGai(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onDoor(BlockPlaceEvent e) {
        if (!setup.getGame().getGameRules().activatedRoles.contains(Roles.GAI))
            return;

        Player player = e.getPlayer();

        if (player == null)
            return;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());


        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null || gamePlayer.role.getRole() == null || !gamePlayer.role.getRole().equals(Roles.GAI) || !(gamePlayer.role instanceof Gai))
            return;

        Gai role = (Gai) gamePlayer.role;

        if (e.getItemInHand() == null || !e.getItemInHand().hasItemMeta() || !e.getItemInHand().getItemMeta().hasDisplayName() || !e.getItemInHand().getType().equals(role.item.getType()) || !e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(role.item.getItemMeta().getDisplayName())) {
            return;
        }

        e.setCancelled(true);

        player.setItemInHand(null);

        ParticleEffect.EXPLOSION_HUGE.display(player.getLocation());
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600*20, 1, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600*20, 0, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600*20, 0, true, true));
        new TGai(setup, gamePlayer);
    }
}
