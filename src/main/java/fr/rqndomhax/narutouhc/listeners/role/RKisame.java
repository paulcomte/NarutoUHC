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
import fr.rqndomhax.narutouhc.role.akatsuki.Kisame;
import fr.rqndomhax.narutouhc.role.shinobi.KakashiHatake;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RKisame implements Listener {

    private final Setup setup;

    public RKisame(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (e.isCancelled() || !setup.getGame().getGameRules().activatedRoles.contains(Roles.KISAME))
            return;

        Player player = e.getPlayer();

        if (player == null)
            return;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null)
            return;

        RoleInfo tmp = PlayerManager.getRole(gamePlayer.role);

        if (!(tmp instanceof Kisame))
            return;

        if (e.getTo().getBlock() == null || !e.getTo().getBlock().getType().equals(Material.WATER) && !e.getTo().getBlock().getType().equals(Material.STATIONARY_WATER)) {
            for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
                if (effect.getDuration() <= 24000)
                    continue;
                if (effect.getType().equals(PotionEffectType.SPEED) && effect.getAmplifier() == 0)
                    e.getPlayer().removePotionEffect(effect.getType());
                if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE) && effect.getAmplifier() == 0)
                    e.getPlayer().removePotionEffect(effect.getType());
            }
            return;
        }

        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false));
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
    }
}
