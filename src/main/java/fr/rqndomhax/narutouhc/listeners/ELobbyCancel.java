/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ELobbyCancel implements Listener {

    private final Setup setup;

    public ELobbyCancel(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR))
            return;
        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING))
            return;
        if (e.getItem() == null || !e.getItem().equals(IInfos.MAIN_HOST_ITEM))
            return;
        e.getPlayer().openInventory(new IHost(setup, e.getPlayer()).getInventory());
        e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent e) {
        if (!setup.getGame().getGameState().equals(GameState.LOBBY_WAITING))
            return;
        if (e.getCurrentItem() == null)
            return;
        if (e.getCurrentItem().equals(IInfos.MAIN_HOST_ITEM))
            e.setCancelled(true);
    }

    @EventHandler
    public void onVillagerRightClick(PlayerInteractAtEntityEvent e) {

        if (!(e.getRightClicked() instanceof Villager))
            return;

        Villager villager = (Villager) e.getRightClicked();

        if (MVillagers.disconnectedPlayers.containsKey(villager))
            e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if (event.getFoodLevel() < player.getFoodLevel()) {
            player.setSaturation(10.0f);
            player.setExhaustion(0.0f);
        }
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
