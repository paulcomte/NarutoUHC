/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ECancels implements Listener {

    private final Setup setup;

    public ECancels(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        GameState gameState = setup.getGame().getGameInfo().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        GameState gameState = setup.getGame().getGameInfo().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING))
            e.setCancelled(true);
    }

   @EventHandler(priority = EventPriority.HIGHEST)
   public void onDamage(EntityDamageEvent e) {
        e.setCancelled(MGameActions.isCancelled(setup.getGame().getGameInfo().getGameState(), e.getEntity()));
   }

   @EventHandler
    public void onMove(PlayerMoveEvent e) {
       if (!setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_TELEPORTING) && !setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING))
            return;
       if (e.getTo().getY() > 110)
           return;
       MPlayer player = setup.getGame().getMPlayer(e.getPlayer().getUniqueId());
       if (player == null) return;

       if (player.location != null)
           e.setTo(player.location);
       else
           e.setTo(new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 120, 0));
   }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }

}