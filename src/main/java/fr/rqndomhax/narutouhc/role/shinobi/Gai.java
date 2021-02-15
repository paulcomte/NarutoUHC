/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.tasks.role.shinobi.TGai;
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
    public TGai task = null;

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
        player.sendMessage(Messages.ROLE_ITEMS_OBTAINED);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Gaï.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez d'un item: \"Huit portes\".");
        player.sendMessage(ChatColor.BLUE + "Cet item vous donnera l'effet force 2, speed 1 et fire resistance, mais aussi de 2 coeurs supplémentaires et de no fall");
        player.sendMessage(ChatColor.BLUE + "Pour une durée totale de 7 minutes.");
        player.sendMessage(ChatColor.BLUE + "Une fois le temps écoulé, vous tomberez à 0.5 coeurs permanents.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "\"Huit portes\".");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "\"Huit portes\".");
    }
}
