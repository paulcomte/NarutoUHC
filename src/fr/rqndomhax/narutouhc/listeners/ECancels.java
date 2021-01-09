/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ECancels implements Listener {

    private final Setup setup;

    public ECancels(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        GameState gameState = setup.getGame().getGameInfo().getGameState();
        if (!gameState.equals(GameState.LOBBY_WAITING) && !gameState.equals(GameState.LOBBY_TELEPORTING) && !gameState.equals(GameState.GAME_TELEPORTING))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        GameState gameState = setup.getGame().getGameInfo().getGameState();
        if (!gameState.equals(GameState.LOBBY_WAITING) && !gameState.equals(GameState.LOBBY_TELEPORTING) && !gameState.equals(GameState.GAME_TELEPORTING))
            return;
        e.setCancelled(true);
    }

   @EventHandler
    public void onPvp(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) return;

        GameState gameState = setup.getGame().getGameInfo().getGameState();
       if (!gameState.equals(GameState.LOBBY_WAITING) && !gameState.equals(GameState.LOBBY_TELEPORTING) && !gameState.equals(GameState.GAME_TELEPORTING))
           return;

       e.setCancelled(true);
   }


}
