/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import fr.rqndomhax.narutouhc.role.akatsuki.Obito;
import fr.rqndomhax.narutouhc.tasks.role.akatsuki.TObito;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;
import java.util.function.Consumer;

public class IObito extends RInventory {

    private final Setup setup;
    private final Set<GamePlayer> players;
    private final GamePlayer obito;
    private final Player player;

    public IObito(Setup setup, GamePlayer obito, Player player, Set<GamePlayer> players, int size) {
        super(null, "BOOM", size);
        this.setup = setup;
        this.obito = obito;
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
            MGameBuild.placeObito(setup);
            new TObito(setup, obito, selected, player, selectedPlayer);
        };
    }
}
