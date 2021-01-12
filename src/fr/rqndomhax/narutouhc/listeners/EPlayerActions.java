/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.tasks.TDeath;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
    public void onPlayerDeath(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player) || e.getFinalDamage() < ((Player) e).getHealth()) return;

        Player player = (Player) e.getEntity();

        if (e.getDamager() instanceof Player)
            e.setCancelled(createDeath(player, setup.getGame().getMPlayer(player.getUniqueId()), (Player) e.getDamager()));

        else if ((e.getDamager() instanceof Arrow) && ((Arrow) e.getDamager()).getShooter() instanceof Player)
            e.setCancelled(createDeath(player, setup.getGame().getMPlayer(player.getUniqueId()), (Player) ((Arrow) e.getDamager()).getShooter()));

        else
            e.setCancelled(createDeath(player, setup.getGame().getMPlayer(player.getUniqueId()), null));
    }

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof Player) && e.getFinalDamage() < ((Player) e).getHealth()) return;

        Player player = (Player) e.getEntity();

        e.setCancelled(createDeath(player, setup.getGame().getMPlayer(player.getUniqueId()), null));
    }

    private boolean createDeath(Player player, MPlayer mPlayer, Player killer) {
        if (mPlayer == null) return false;

        mPlayer.deathLocation = player.getLocation();

        player.setGameMode(GameMode.SPECTATOR);

        mPlayer.deathInventory.saveInventory(player);
        mPlayer.isDead = true;

        if (killer != null)
            new TDeath(setup, mPlayer, setup.getGame().getMPlayer(killer.getUniqueId()), setup.getGame().getGameInfo().getMRules().timeBeforeDeath);
        else
            new TDeath(setup, mPlayer, null, setup.getGame().getGameInfo().getMRules().timeBeforeDeath);

        return true;
    }
}
