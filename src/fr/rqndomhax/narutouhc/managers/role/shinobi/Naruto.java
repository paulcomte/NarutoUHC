/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role.shinobi;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.builders.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Objects;

public class Naruto extends RoleInfo {

    public Naruto(MPlayer mPlayer) {
        super(mPlayer, Roles.NARUTO);
    }

    @Override
    public void onClaim() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
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
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 0, false, false));
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Naruto.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire vous disposez des effets speed 1 et fire resistance permanent ainsi qu'un livre fire aspect 1.");
        player.sendMessage("En plus de cela, si Sasuke choisit de rejoindre l'akatsuki, vous obtiendrez l'effect strength 1 permanent.");
    }

}
