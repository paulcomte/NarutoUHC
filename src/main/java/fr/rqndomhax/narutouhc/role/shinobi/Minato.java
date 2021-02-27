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

public class Minato extends RoleInfo {

    public final ItemStack item = new ItemBuilder(Material.BOW).setName(ChatColor.YELLOW + "Kunaï").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().setDurability((short) 50).toItemStack();
    public long lastArrow = 0;

    public Minato(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.MINATO);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false, false));
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

        player.getInventory().addItem(item);
        hasClaimed = true;
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Minato.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez de l'effet speed 2.");
        player.sendMessage(ChatColor.BLUE + "Vous possédez aussi un arc nommé \"Shuriken\" permettant de vous téléporter à l'endroit où la flèche atterit.");
        player.sendMessage(ChatColor.BLUE + "Votre arc possède 20 secondes de cooldown.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "\"Shuriken\".");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "\"Shuriken\".");
    }
}
