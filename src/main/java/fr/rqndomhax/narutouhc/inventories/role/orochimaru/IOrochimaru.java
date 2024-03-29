/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role.orochimaru;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.function.Consumer;

public class IOrochimaru extends RInventory {

    private final Setup setup;
    private final Set<GamePlayer> players;
    private final Player player;

    public IOrochimaru(Setup setup, Player player, Set<GamePlayer> players, int size) {
        super(null, "Venin", size);
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
            runTask(selected);
        };
    }

    private void runTask(Player selected) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.playSound(player.getLocation(), "mob.wither.shoot", 2f,  1.8f);
                selected.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 7*20, 1, false, false));
            }
        }.runTaskLater(setup.getMain(), 5*20);
    }

}
