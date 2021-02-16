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

public class KillerBee extends RoleInfo {

    public final ItemStack item = new ItemBuilder(Material.RABBIT_FOOT).setName(ChatColor.YELLOW + "Hachibi").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
    public long itemUsedStamp = 0;


    public KillerBee(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KILLER_BEE);
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
        if (nSpace < 2) {
            player.sendMessage(Messages.ROLE_ITEMS_NEED_SPACE.replace("%n%", "2"));
            return;
        }

        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        player.getInventory().addItem(item);
        hasClaimed = true;
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);
    }


    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Killer Bee.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez de l'effet strength 1.");
        player.sendMessage(ChatColor.BLUE + "Vous possédez en plus de cela un objet nommé \"Hachibi\".");
        player.sendMessage(ChatColor.BLUE + "Lorsque vous cliquez sur l'objet vous obtiendrez speed 1, jump boost 4 ainsi que nofall pendant 4 minutes.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "1 épée en diamant.");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "1 épée en diamant.");
    }
}
