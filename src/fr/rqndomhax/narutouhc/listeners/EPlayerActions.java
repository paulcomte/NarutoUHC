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
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EPlayerActions implements Listener {

    private final Setup setup;

    public EPlayerActions(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {

        MPlayer mPlayer = setup.getGame().getMPlayer(e.getPlayer().getUniqueId());

        if (mPlayer == null) return;

        if (!mPlayer.isDead) return;

        e.setRespawnLocation(mPlayer.deathLocation);
    }

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;

        Player player = (Player) e.getEntity();

        if (e.getFinalDamage() < player.getHealth())
            return;

        MPlayer mPlayer = setup.getGame().getMPlayer(player.getUniqueId());

        if (mPlayer == null) return;

        mPlayer.deathLocation = e.getEntity().getLocation();

        e.setCancelled(true);

        player.setGameMode(GameMode.SPECTATOR);

        mPlayer.deathInventory.saveInventory(player);
        mPlayer.isDead = true;
        new TDeath(setup, mPlayer, setup.getGame().getMPlayer(e.getEntity().getUniqueId()), setup.getGame().getGameInfo().getmRules().timeBeforeDeath);
    }
}
