/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.solos;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Madara extends RoleInfo {

    public Madara(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.MADARA);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 0, false, false));
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Madara.");
        player.sendMessage("Votre but est de gagner seul.");
        player.sendMessage("Pour ce faire vous disposez des effets suivants: " + ChatColor.RED + "strength 1 " + ChatColor.RESET + ", " + ChatColor.BLACK + "résistance 1 " + ChatColor.RESET + ", " + ChatColor.AQUA + "speed 1 " + ChatColor.RESET + "et " + ChatColor.GOLD + "fire resistance" + ChatColor.RESET + ".");
    }

}
