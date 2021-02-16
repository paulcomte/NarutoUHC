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

import java.util.Arrays;
import java.util.Objects;

public class Fu extends RoleInfo {

    public final ItemStack item = new ItemBuilder(Material.WEB).setName(ChatColor.DARK_GRAY + "Prison de l'araignée").addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().toItemStack();

    public Fu(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.FU);
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

        for (int i = 0; i < 3; player.getInventory().addItem(item), i++);
        hasClaimed = true;
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Fû.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Vous dispoez de l'effet no fall.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez de 3 toiles d'araignées: \"Prison de l'araignée\".");
        player.sendMessage(ChatColor.BLUE + "Cet item vous donnera l'effet régénération 2 tant que vous restez en contact avec.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "3 toiles d'araignées: \"Prison de l'araignée\".");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "3 toiles d'araignées: \"Prison de l'araignée\".");
    }
}
