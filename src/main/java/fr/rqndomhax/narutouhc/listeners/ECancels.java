/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.listeners.world.ChunkUnloadListener;
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.utils.EasterEggs;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ECancels implements Listener {

    private final Setup setup;

    public ECancels(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        if (ChunkUnloadListener.keepChunk.contains(e.getChunk()))
            e.setCancelled(true);
        for (Villager villager : MVillagers.disconnectedPlayers.keySet())
            if (villager.getLocation().getChunk().equals(e.getChunk()))
                e.setCancelled(true);
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2))
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2))
            e.blockList().clear();
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2))
            e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        GameState gameState = setup.getGame().getGameState();
        if (gameState.equals(GameState.LOBBY_WAITING))
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        GameState gameState = setup.getGame().getGameState();
        e.setCancelled(gameState.equals(GameState.GAME_INVINCIBILITY) || gameState.equals(GameState.LOBBY_WAITING)
                || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2) || gameState.equals(GameState.GAME_TELEPORTATION_INVINCIBILITY));
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        GameState gameState = setup.getGame().getGameState();
        e.setCancelled(gameState.equals(GameState.GAME_INVINCIBILITY) || gameState.equals(GameState.LOBBY_WAITING)
                || gameState.equals(GameState.LOBBY_TELEPORTING) || gameState.equals(GameState.GAME_TELEPORTING2) || gameState.equals(GameState.GAME_TELEPORTATION_INVINCIBILITY));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;

        Material type = e.getBlock().getType();
        if (e.getBlock() == null || (!type.equals(Material.EMERALD_ORE) && !type.equals(Material.EMERALD_BLOCK) &&
                !type.equals(Material.IRON_ORE) && !type.equals(Material.IRON_BLOCK) &&
                !type.equals(Material.GOLD_ORE) && !type.equals(Material.GOLD_BLOCK) &&
                !type.equals(Material.COAL_ORE) && !type.equals(Material.COAL_BLOCK) &&
                !type.equals(Material.LAPIS_ORE) && !type.equals(Material.LAPIS_BLOCK) &&
                !type.equals(Material.REDSTONE_ORE) && !type.equals(Material.REDSTONE_BLOCK) &&
                !type.equals(Material.DIAMOND_ORE) && !type.equals(Material.DIAMOND_BLOCK) &&
                !type.equals(Material.WEB) &&
                !type.equals(Material.BOOKSHELF)))
            return;

        World currentWorld = Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name());
        if (!e.getPlayer().getWorld().equals(currentWorld))
            return;

        e.getBlock().setType(Material.AIR);
        e.getBlock().getState().update();
    }

   @EventHandler
    public void onMove(PlayerMoveEvent e) {
       GameState gameState = setup.getGame().getGameState();
       if (!gameState.equals(GameState.LOBBY_TELEPORTING)
               && !gameState.equals(GameState.LOBBY_WAITING)
               && !gameState.equals(GameState.GAME_TELEPORTING2))
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
        if (e.isCancelled())
            return;

        if (EasterEggs.Hyside.hysideChat(e)) {
            e.setCancelled(true);
            return;
        }

        if (setup.getGame().getGameRules().allowChat || setup.getGame().getGameState().equals(GameState.LOBBY_WAITING) || setup.getGame().getGameState().equals(GameState.GAME_FINISHED))
            return;

        e.setCancelled(true);
        e.getPlayer().sendMessage(Messages.CHAT_DISABLED);
   }

    @EventHandler
    private void onThunder(ThunderChangeEvent e){
        if(e.toThunderState())
            e.setCancelled(true);
    }

    @EventHandler
    private void onRain(WeatherChangeEvent e){
        if(e.toWeatherState())
            e.setCancelled(true);
    }

}
