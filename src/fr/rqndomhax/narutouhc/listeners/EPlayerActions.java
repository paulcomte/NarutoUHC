/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.tasks.TDeath;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;

public class EPlayerActions implements Listener {

    private final Setup setup;

    public EPlayerActions(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPlayerHitEvent(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getDamager().getUniqueId());
        if (gamePlayer == null) return;
        if (gamePlayer.role == null) return;
        gamePlayer.role.onHit(setup.getGame().getGamePlayer(e.getEntity().getUniqueId()));
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player player = e.getEntity();
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        player.setGameMode(GameMode.SPECTATOR);

        if (gamePlayer == null) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            return;
        }
        if (gamePlayer.isDead) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            return;
        }

        gamePlayer.deathLocation = player.getLocation();

        InventoryManager.saveInventory(gamePlayer.inventory, player);
        gamePlayer.isDead = true;

        if (player.getKiller() != null)
            new TDeath(setup, gamePlayer, setup.getGame().getGamePlayer(player.getKiller().getUniqueId()), setup.getGame().getGameRules().timeBeforeDeath, e.getDroppedExp(), new ArrayList<>(e.getDrops()));
        else
            new TDeath(setup, gamePlayer, null, setup.getGame().getGameRules().timeBeforeDeath, e.getDroppedExp(), new ArrayList<>(e.getDrops()));

        e.getDrops().clear();
        e.setDroppedExp(0);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());

        if (gamePlayer == null) return;

        if (!gamePlayer.isDead) return;

        e.setRespawnLocation(gamePlayer.deathLocation);
    }


}
