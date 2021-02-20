/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.shinobi.Neji;
import fr.rqndomhax.narutouhc.tasks.role.shinobi.TNeji;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;
import java.util.function.Consumer;

public class INeji extends RInventory {

    private final Setup setup;
    private final Neji neji;
    private final Set<GamePlayer> players;
    private final Player player;

    public INeji(Setup setup, Player player, Neji neji, Set<GamePlayer> players, int size) {
        super(null, "Analyse", size);
        this.setup = setup;
        this.player = player;
        this.neji = neji;
        this.players = players;
        updateInventory();
    }

    private void updateInventory() {
        int slot = 0;
        for (GamePlayer gamePlayer : players) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null || !p.isOnline())
                continue;

            this.setItem(slot, new ItemBuilder(Material.SKULL_ITEM, 1, (byte) 3).setName(p.getName()).setSkullOwner(p.getName()).toItemStack(), select(p, gamePlayer));
            slot++;
        }
    }

    private Consumer<InventoryClickEvent> select(Player selectedPlayer, GamePlayer selected) {
        return e -> {
            if (selectedPlayer == null || !selectedPlayer.isOnline()) {
                updateInventory();
                return;
            }
            player.closeInventory();
            neji.commandUses--;
            new TNeji(setup, neji, selected, player, selectedPlayer);
            player.playSound(player.getLocation(), "random.levelup", 2, 2f);
        };
    }

}
