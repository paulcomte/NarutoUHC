/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Objects;

public class Konan extends Akatsuki {

    public boolean hasUsedCapacity = false;

    public Konan(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KONAN);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1000000, 0, false, false));
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
        if (nSpace < 3) {
            player.sendMessage(Messages.ROLE_ITEMS_NEED_SPACE.replace("%n%", "1").replace("objets", "objet"));
            return;
        }

        player.getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK).addStoredEnchant(Enchantment.ARROW_DAMAGE, 5).toItemStack());
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        player.getInventory().addItem(new ItemStack(Material.LEAVES, 64));
        hasClaimed = true;
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Konan.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'akatsuki.");
        player.sendMessage(ChatColor.BLUE + "Vous souffrez d'un effet weakness 1.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "1 livre power 5, 64 flèches et 64 feuilles.");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "1 livre power 5, 64 flèches et 64 feuilles.");
    }
}
