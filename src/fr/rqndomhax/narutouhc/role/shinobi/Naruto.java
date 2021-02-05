/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Objects;

public class Naruto extends RoleInfo {

    public Naruto(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.NARUTO);
    }

    @Override
    public void onClaim() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (hasClaimed) {
            player.sendMessage(Messages.ROLE_NO_ITEMS);
            return;
        }

        long nSpace = Arrays.stream(player.getInventory().getContents()).filter(Objects::isNull).count();
        if (nSpace < 1) {
            player.sendMessage(Messages.ROLE_ITEMS_NEED_SPACE.replace("%n%", "1").replace("objets", "objet"));
            return;
        }

        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.FIRE_ASPECT, 1).toItemStack());
        hasClaimed = true;
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 0, false, false));
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Naruto.");
        player.sendMessage("Votre but est de gagner avec " + ChatColor.LIGHT_PURPLE + "l'alliance shinobi.");
        player.sendMessage("Pour ce faire vous disposez des effets" + ChatColor.AQUA + "speed 1 et " + ChatColor.GOLD + "fire resistance" + ChatColor.RESET + " permanent ainsi qu'un livre " + ChatColor.GOLD + "fire aspect 1" + ChatColor.RESET + ".");
        player.sendMessage("En plus de cela, si " + ChatColor.DARK_PURPLE + "Sasuke" + ChatColor.RESET + " choisit de rejoindre l'akatsuki, vous obtiendrez l'effet " + ChatColor.RED + "strength 1" + ChatColor.RESET + " permanent.");
    }

}
