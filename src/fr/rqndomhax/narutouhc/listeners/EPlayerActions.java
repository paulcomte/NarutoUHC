/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.tasks.TDeath;
import fr.rqndomhax.narutouhc.utils.InventoryManager;
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
        MPlayer mPlayer = setup.getGame().getMPlayer(e.getDamager().getUniqueId());
        if (mPlayer == null) return;
        if (mPlayer.role == null) return;
        mPlayer.role.onHit(setup.getGame().getMPlayer(e.getEntity().getUniqueId()));
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player player = e.getEntity();
        MPlayer mPlayer = setup.getGame().getMPlayer(player.getUniqueId());

        player.setGameMode(GameMode.SPECTATOR);

        if (mPlayer == null) return;
        if (mPlayer.isDead) return;

        mPlayer.deathLocation = player.getLocation();

        InventoryManager.saveInventory(mPlayer.inventory, player);
        mPlayer.isDead = true;

        if (player.getKiller() != null)
            new TDeath(setup, mPlayer, setup.getGame().getMPlayer(player.getKiller().getUniqueId()), setup.getGame().getGameInfo().getMRules().timeBeforeDeath, e.getDroppedExp(), new ArrayList<>(e.getDrops()));
        else
            new TDeath(setup, mPlayer, null, setup.getGame().getGameInfo().getMRules().timeBeforeDeath, e.getDroppedExp(), new ArrayList<>(e.getDrops()));

        e.getDrops().clear();
        e.setDroppedExp(0);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {

        MPlayer mPlayer = setup.getGame().getMPlayer(e.getPlayer().getUniqueId());

        if (mPlayer == null) return;

        if (!mPlayer.isDead) return;

        e.setRespawnLocation(mPlayer.deathLocation);
    }


}
