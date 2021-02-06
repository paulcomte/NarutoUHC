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
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class Gai extends RoleInfo {

    public final ItemStack item = new ItemBuilder(Material.IRON_DOOR).setName(ChatColor.DARK_AQUA + "Huit portes").addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().toItemStack();

    public Gai(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.GAI);
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
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Gaï.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, vous disposez d'un item, \"Huit portes\".");
        player.sendMessage("Cet item vous donnera l'effet \"" + ChatColor.DARK_RED + "force 2" + ChatColor.RESET + ", " + ChatColor.AQUA + "speed 1" + ChatColor.RESET + "et " + ChatColor.GOLD + "fire resistance " + ChatColor.RESET + ".");
        player.sendMessage("Ainsi que \"" + ChatColor.RED + "2 coeurs supplémentaires" + ChatColor.RESET + " et " + ChatColor.DARK_PURPLE + "no fall" + ChatColor.RESET + ".");
        player.sendMessage("Pour une durée totale de 10 minutes.");
        player.sendMessage("Une fois ces 10 minutes passées, vous tomberez à " + ChatColor.RED + "0.5 coeurs permanents" + ChatColor.RESET + ".");
        if (!hasClaimed)
            player.sendMessage("Pour le récupérer faites \"/na claim\"");
    }
}
