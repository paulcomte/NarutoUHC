/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.solos.Sasuke;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;
import java.util.function.Consumer;

public class ISasuke extends RInventory {

    private final Set<GamePlayer> players;
    private final Player player;
    private final Sasuke sasuke;

    public ISasuke(Player player, Set<GamePlayer> players, int size, Sasuke sasuke) {
        super(null, "Analyse", size);
        this.player = player;
        this.players = players;
        this.sasuke = sasuke;
        updateInventory();
    }

    private void updateInventory() {
        int slot = 0;
        for (GamePlayer gamePlayer : players) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null || !p.isOnline())
                continue;

            this.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setName(p.getName()).setSkullOwner(p.getName()).toItemStack(), select(gamePlayer));
            slot++;
        }
    }

    private Consumer<InventoryClickEvent> select(GamePlayer selected) {
        return e -> {
            player.sendMessage(ChatColor.DARK_AQUA + "Rôle de " + ChatColor.GOLD + selected.name + ChatColor.DARK_AQUA + ": " + ChatColor.BLUE + ChatColor.UNDERLINE + selected.role.getRole().getRoleName());
            sasuke.usagesLeft--;
            if (sasuke.usagesLeft == 0)
                player.closeInventory();
        };
    }

}

