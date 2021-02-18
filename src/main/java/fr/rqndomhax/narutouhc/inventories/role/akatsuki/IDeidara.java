/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;
import java.util.function.Consumer;

public class IDeidara extends RInventory {

    private final Setup setup;
    private final Set<GamePlayer> players;
    private final Player player;

    public IDeidara(Setup setup, Player player, Set<GamePlayer> players, int size) {
        super(null, "BOOM", size);
        this.setup = setup;
        this.player = player;
        this.players = players;

        updateInventory();
    }

    private void updateInventory() {
        int slot = 0;
        for (GamePlayer gamePlayer : players) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null || !p.isOnline())
                continue;

            this.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setName(p.getName()).setSkullOwner(p.getName()).toItemStack(), select(p));
            slot++;
        }
    }

    private Consumer<InventoryClickEvent> select(Player selected) {
        return e -> {
            if (selected == null || !selected.isOnline()) {
                updateInventory();
                return;
            }
            player.closeInventory();
            player.sendMessage(Messages.PREFIX + "Vous avez utilisé votre effet sur " + selected.getName());
            player.playSound(player.getLocation(), "random.explode", 2f,  1.3f);
            TNTPrimed tnt = selected.getLocation().getWorld().spawn(selected.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(0);
        };
    }

}
