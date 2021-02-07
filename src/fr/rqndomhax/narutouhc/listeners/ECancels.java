/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ECancels implements Listener {

    private final Setup setup;

    public ECancels(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        if (MGameActions.needLoadedChunks != null && MGameActions.needLoadedChunks.contains(e.getChunk()))
            e.setCancelled(true);
        for (Villager villager : MVillagers.disconnectedPlayers.keySet())
            if (villager.getLocation().getChunk().equals(e.getChunk()))
                e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING))
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        GameState gameState = setup.getGame().getGameState();
        e.setCancelled(gameState.equals(GameState.GAME_INVINCIBILITY) || gameState.equals(GameState.LOBBY_WAITING)
                || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTATION_INVINCIBILITY));
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        GameState gameState = setup.getGame().getGameState();
        e.setCancelled(gameState.equals(GameState.GAME_INVINCIBILITY) || gameState.equals(GameState.LOBBY_WAITING)
                || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTATION_INVINCIBILITY));
    }

   @EventHandler
    public void onMove(PlayerMoveEvent e) {
       GameState gameState = setup.getGame().getGameState();
       if (!gameState.equals(GameState.LOBBY_TELEPORTING)
               && !gameState.equals(GameState.LOBBY_WAITING)
               && !gameState.equals(GameState.GAME_TELEPORTING))
            return;

       if (e.getTo().getY() > 220)
           return;

       GamePlayer player = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());
       if (player == null) return;

       if (player.location != null)
           e.setTo(player.location);
       else
           e.setTo(new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 230, 0));
   }

   @EventHandler
   public void onChat(AsyncPlayerChatEvent e) {
        if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING) || setup.getGame().getGameRules().allowChat || setup.getGame().getGameState().equals(GameState.GAME_FINISHED))
            return;

        e.setCancelled(true);
        e.getPlayer().sendMessage(Messages.CHAT_DISABLED);
   }

}
