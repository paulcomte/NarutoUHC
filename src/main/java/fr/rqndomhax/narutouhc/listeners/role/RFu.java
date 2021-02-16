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
import fr.rqndomhax.narutouhc.role.shinobi.Fu;
import fr.rqndomhax.narutouhc.role.shinobi.KakashiHatake;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RFu implements Listener {

    private final Setup setup;

    private final HashMap<Player, Set<Block>> webs = new HashMap<>();

    public RFu(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (e.isCancelled() || !setup.getGame().getGameRules().activatedRoles.contains(Roles.FU))
            return;

        if (e.getTo().getBlock() == null || !e.getTo().getBlock().getType().equals(Material.WEB)) {
            for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
                if (!effect.getType().equals(PotionEffectType.REGENERATION) || effect.getAmplifier() != 1)
                    continue;
                if (effect.getDuration() > 24000)
                    e.getPlayer().removePotionEffect(effect.getType());
            }
            return;
        }

        Set<Block> webs = this.webs.get(e.getPlayer());

        if (webs == null || webs.isEmpty())
            return;

        Block currentBlock = null;

        for (Block block : webs) {
            if (e.getTo().getBlock() == null || !e.getTo().getBlock().equals(block))
                continue;

            currentBlock = block;
            break;
        }

        if (currentBlock == null)
            return;

        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000, 1, false, false));
    }

    @EventHandler
    public void onWeb(BlockPlaceEvent e) {
        if (e.isCancelled())
            return;

        if (!setup.getGame().getGameRules().activatedRoles.contains(Roles.FU))
            return;

        Player player = e.getPlayer();

        if (player == null)
            return;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead || gamePlayer.role == null)
            return;

        RoleInfo tmp = gamePlayer.role;
        if((gamePlayer.role instanceof KakashiHatake) && ((KakashiHatake) gamePlayer.role).stolenRole != null)
            tmp = ((KakashiHatake) gamePlayer.role).stolenRole;

        if (!(tmp instanceof Fu))
            return;

        if (e.getItemInHand() == null)
            return;

        ItemStack clonedItem = ((Fu) tmp).item.clone();

        clonedItem.setAmount(e.getItemInHand().getAmount());

        if (!e.getItemInHand().equals((clonedItem)))
            return;

        ParticleEffect.HEART.display(e.getBlockPlaced().getLocation());
        player.getLocation().getWorld().playSound(e.getBlockPlaced().getLocation(), Sound.CAT_MEOW, 10, 1.5f);
        webs.computeIfAbsent(player, k -> new HashSet<>());
        webs.get(player).add(e.getBlock());
    }
}
