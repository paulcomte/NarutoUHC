/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */
package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.tasks.role.akatsuki.TObito;
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

public class Obito extends Akatsuki {

    public final ItemStack item = new ItemBuilder(Material.LEATHER).setName(ChatColor.BLACK + "Ninjutsu Spatio-Temporel").addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack();
    public boolean hasUsedCapacity = true;
    public TObito task = null; // TODO FIX OBITO WITH TABLIST

    public Obito(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.OBITO);
    }

    @Override
    public void onDeath(Setup setup) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player != null)
            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                onlinePlayer.showPlayer(player);
    }

    @Override
    public void onNewEpisode(int episode) {
        hasUsedCapacity = false;

        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player != null)
            player.sendMessage(Messages.ROLE_CAPACITY);
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
        player.sendMessage(ChatColor.BLUE + "Vous êtes Obito.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'akatsuki.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez d'un objet \"Ninjutsu Spatio-Temporel\" permettant de vous rendrez invisible, et d'obtenir l'effet speed 1.");
        player.sendMessage(ChatColor.BLUE + "L'invisibilité fonctionne même avec l'armure, ces effets ne dureront que 2 minutes, au premier coup donné, vous redeviendrez visible.");
        player.sendMessage(ChatColor.BLUE + "Vous ne pouvez utiliser cet objet qu'une fois par épisode.");
        if (!hasClaimed)
            player.sendMessage(ChatColor.GREEN + "/na claim: " + "\"Ninjutsu Spatio-Temporel\".");
        else
            player.sendMessage(ChatColor.RED + "/na claim: " + "\"Ninjutsu Spatio-Temporel\".");
    }
}