/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EScenarios implements Listener {

    private final Setup setup;

    public EScenarios(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {

        GameState gameState = setup.getGame().getGameInfo().getGameState();
        if (!gameState.equals(GameState.LOBBY_WAITING) && !gameState.equals(GameState.LOBBY_TELEPORTING) && !gameState.equals(GameState.GAME_TELEPORTING))
            return;

        
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        GameState gameState = setup.getGame().getGameInfo().getGameState();
        if (!gameState.equals(GameState.LOBBY_WAITING) && !gameState.equals(GameState.LOBBY_TELEPORTING) && !gameState.equals(GameState.GAME_TELEPORTING))
            return;

        switch(e.getEntityType()) {
            case CHICKEN:
                updateDrop(e.getDrops(), Material.RAW_CHICKEN, Material.COOKED_CHICKEN);
                break;
            case SHEEP:
                updateDrop(e.getDrops(), Material.MUTTON, Material.COOKED_MUTTON);
                break;
            case RABBIT:
                updateDrop(e.getDrops(), Material.RABBIT, Material.COOKED_RABBIT);
                break;
            case COW:
            case MUSHROOM_COW:
                updateDrop(e.getDrops(), Material.RAW_BEEF, Material.COOKED_BEEF);
                break;
            case PIG:
                updateDrop(e.getDrops(), Material.PORK, Material.GRILLED_PORK);
                break;
        }

    }

    private void updateDrop(List<ItemStack> drops, Material filter, Material newDrop) {
        for (ItemStack drop : drops)
            if (drop.getType() == filter)
                drop.setType(newDrop);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        GameState gameState = setup.getGame().getGameInfo().getGameState();
        if (!gameState.equals(GameState.LOBBY_WAITING) && !gameState.equals(GameState.LOBBY_TELEPORTING) && !gameState.equals(GameState.GAME_TELEPORTING))
            return;

        if (!e.getBlock().getType().equals(Material.GRAVEL)
                && !e.getBlock().getType().equals(Material.GOLD_ORE)
                && !e.getBlock().getType().equals(Material.IRON_ORE))
            return;

        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        e.getBlock().getState().update();

        switch (e.getBlock().getType()) {
            case GOLD_ORE:
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
                e.getBlock().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(3);
                break;
            case IRON_ORE:
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_ORE));
                e.getBlock().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(3);
                break;
            case GRAVEL:
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.FLINT));
                break;
            default:
                break;
        }
    }
}
