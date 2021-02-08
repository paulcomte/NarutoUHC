/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RInventoryHandler implements Listener {

    private final JavaPlugin javaPlugin;
    private final RInventoryManager rInventoryManager;
    public RInventoryHandler(JavaPlugin javaPlugin, RInventoryManager rInventoryManager) {
        this.javaPlugin = javaPlugin;
        this.rInventoryManager = rInventoryManager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClick(InventoryClickEvent e){
        if (e.getInventory() != null && e.getInventory().getHolder() instanceof RInventory) {
            RInventory rInventory = (RInventory) e.getInventory().getHolder();
            int slot = e.getRawSlot();
            e.setCancelled(true);
            if (rInventory.getMapShare().containsKey(slot)) {
                rInventory.getMapShare().get(slot).accept(e);
            }
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof RInventory) {
            RInventory rInventory = (RInventory) event.getInventory().getHolder();
            rInventory.getRunnableList().forEach(rInventoryRunnable -> rInventoryRunnable.getRunnable().run());
            this.rInventoryManager.put(rInventory);
            rInventory.onOpen(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof RInventory) {
            RInventory rInventory = (RInventory) event.getInventory().getHolder();
            this.rInventoryManager.remove(rInventory);
            Bukkit.getScheduler().runTask(javaPlugin, ()-> rInventory.onClose(event));
        }
    }


}
