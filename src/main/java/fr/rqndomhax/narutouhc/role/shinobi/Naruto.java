/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Naruto extends RoleInfo {

    public boolean isSasuke = false;

    public Naruto(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.NARUTO);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 0, false, false));
        if (isSasuke)
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Naruto.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez de l'effet fire resistance.");
        if (isSasuke)
            player.sendMessage(ChatColor.BLUE + "Comme Sasuke est membre de l'Akatsuki, vous possédez l'effet strength 1.");
    }

}
