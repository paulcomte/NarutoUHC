/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;
import java.util.function.Consumer;

public class IShikamaru extends RInventory {

    private final Setup setup;
    private final Set<GamePlayer> players;
    private final Player player;

    public IShikamaru(Setup setup, Player player, Set<GamePlayer> players, int size) {
        super(null, "Debuff", size);
        this.setup = setup;
        this.player = player;
        this.players = players;

        updateInventory();
    }

    private void updateInventory() {
        int slot = 0;
        for (GamePlayer gamePlayer : players) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;

            this.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setName(p.getName()).setSkullOwner(p.getName()).toItemStack(), select(p));
            slot++;
        }
    }

    private Consumer<InventoryClickEvent> select(Player selected) {
        return e -> {
            if (selected == null) {
                updateInventory();
                return;
            }
            player.closeInventory();
            // TODO SEND CONFIRMATION MESSAGE
            selected.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30*20, 0, false, false));
            selected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30*20, 1, false, false));
        };
    }

}
