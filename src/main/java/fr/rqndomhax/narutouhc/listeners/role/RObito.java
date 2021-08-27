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
import fr.rqndomhax.narutouhc.role.akatsuki.Obito;
import fr.rqndomhax.narutouhc.role.shinobi.KakashiHatake;
import fr.rqndomhax.narutouhc.tabscores.TabListManager;
import fr.rqndomhax.narutouhc.tasks.role.akatsuki.TObito;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.HashSet;
import java.util.Set;

public class RObito implements Listener {

    private final Setup setup;

    public RObito(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() == null || (!(e.getEntity() instanceof Player)))
            return;

        Player player = (Player) e.getEntity();

        boolean hasEffect = false;

        for (PotionEffect effect : player.getActivePotionEffects())
            if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {
                hasEffect = true;
                break;
            }

        if (hasEffect)
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {

        if (e.isCancelled())
            return;

        if (e.getDamager() == null || (!(e.getDamager() instanceof Player)))
            return;

        Player player = (Player) e.getDamager();

        boolean hasEffect = false;

        for (PotionEffect effect : player.getActivePotionEffects())
            if (effect.getType().equals(PotionEffectType.INVISIBILITY)) {
                hasEffect = true;
                break;
            }

        if (!hasEffect)
            return;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null)
            return;

        RoleInfo tmp = PlayerManager.getRole(gamePlayer.role);

        if (!(tmp instanceof Obito))
            return;

        if (((Obito) tmp).task == null)
            return;

        ((Obito) tmp).task.cancel();
        ((Obito) tmp).task = null;

        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().equals(PotionEffectType.INVISIBILITY) && effect.getAmplifier() == 0)
                player.removePotionEffect(effect.getType());
            if (effect.getType().equals(PotionEffectType.SPEED) && effect.getAmplifier() == 0)
                player.removePotionEffect(effect.getType());
        }

        if (player.getActivePotionEffects().stream().noneMatch(effect -> effect.getType().equals(PotionEffectType.SPEED)))
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 0, false, false));

        if (player.getActivePotionEffects().stream().noneMatch(effect -> effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)))
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10*20, 0, false, false));

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(player.getLocation(), "mob.guardian.death", 2, 0.4f);
            onlinePlayer.showPlayer(player);
        }

    }

    @EventHandler
    public void onHide(PlayerJoinEvent e) {

        if (!setup.getGame().getGameRules().activatedRoles.contains(Roles.OBITO))
            return;

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            RoleInfo tmp = PlayerManager.getRole(gamePlayer.role);
            if (tmp instanceof Obito) {
                Player player = Bukkit.getPlayer(gamePlayer.uuid);
                if (player == null)
                    continue;
                if (((Obito) tmp).task != null)
                    e.getPlayer().hidePlayer(player);
                else
                    e.getPlayer().showPlayer(player);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null)
            return;

        RoleInfo tmp = PlayerManager.getRole(gamePlayer.role);

        if (!(tmp instanceof Obito))
            return;

        if (((Obito) tmp).task != null) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2 * 60 * 20, 0, false, false));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2 * 60 * 20, 0, false, false));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                onlinePlayer.hidePlayer(e.getPlayer());
            return;
        }

        for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
            if (effect.getType().equals(PotionEffectType.INVISIBILITY) && effect.getAmplifier() == 0)
                e.getPlayer().removePotionEffect(effect.getType());
            if (effect.getType().equals(PotionEffectType.SPEED) && effect.getAmplifier() == 0)
                e.getPlayer().removePotionEffect(effect.getType());
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            onlinePlayer.showPlayer(e.getPlayer());
    }

    @EventHandler
    public void onItem(PlayerInteractEvent e) {

        if (!setup.getGame().getGameRules().activatedRoles.contains(Roles.OBITO))
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

        if (!(tmp instanceof Obito))
            return;

        if (!player.getItemInHand().equals(Roles.OBITO.getRoleItem()))
            return;

        if (((Obito) tmp).hasUsedCapacity) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        ((Obito) tmp).hasUsedCapacity = true;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2 * 60 * 20, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2 * 60 * 20, 0, false, false));
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(player.getLocation(), "mob.guardian.curse", 2, 0.6f);
            onlinePlayer.hidePlayer(player);
        }

        ParticleEffect.SMOKE_LARGE.display(player.getLocation());

        if (((Obito) tmp).task != null)
            ((Obito) tmp).task.cancel();

        ((Obito) tmp).task = new TObito(setup, gamePlayer);
    }
}
