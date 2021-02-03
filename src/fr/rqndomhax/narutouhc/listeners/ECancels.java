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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
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

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        GameState gameState = setup.getGame().getGameInfo().getGameState();
        e.setCancelled(gameState.equals(GameState.GAME_INVINCIBILITY) || gameState.equals(GameState.LOBBY_WAITING)
                || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTATION_INVINCIBILITY));
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        GameState gameState = setup.getGame().getGameInfo().getGameState();
        e.setCancelled(gameState.equals(GameState.GAME_INVINCIBILITY) || gameState.equals(GameState.LOBBY_WAITING)
                || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTATION_INVINCIBILITY));
    }

   @EventHandler
    public void onMove(PlayerMoveEvent e) {
       if (!setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_TELEPORTING) && !setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING) && !setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_TELEPORTING))
            return;
       if (e.getTo().getY() > 220)
           return;

       MPlayer player = setup.getGame().getMPlayer(e.getPlayer().getUniqueId());
       if (player == null) return;

       if (player.location != null)
           e.setTo(player.location);
       else
           e.setTo(new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 230, 0));
   }

}
