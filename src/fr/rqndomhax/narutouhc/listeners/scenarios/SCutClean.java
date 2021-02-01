/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.scenarios;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import org.bukkit.Material;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class SCutClean implements Listener {

    private final Setup setup;

    public SCutClean(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if (!setup.getGame().getGameInfo().getMRules().activatedScenarios.contains(Scenarios.CUTCLEAN))
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

        if (!setup.getGame().getGameInfo().getMRules().activatedScenarios.contains(Scenarios.CUTCLEAN))
            return;

        if (!e.getBlock().getType().equals(Material.GRAVEL)
                && !e.getBlock().getType().equals(Material.GOLD_ORE)
                && !e.getBlock().getType().equals(Material.IRON_ORE))
            return;

        switch (e.getBlock().getType()) {
            case GOLD_ORE:
                if (!e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && !e.getPlayer().getItemInHand().getType().equals(Material.IRON_PICKAXE))
                    return;
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
                e.getBlock().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(3);
                break;
            case IRON_ORE:
                if (!e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && !e.getPlayer().getItemInHand().getType().equals(Material.IRON_PICKAXE) && !e.getPlayer().getItemInHand().getType().equals(Material.STONE_PICKAXE))
                    return;
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
                e.getBlock().getWorld().spawn(e.getBlock().getLocation(), ExperienceOrb.class).setExperience(3);
                break;
            case GRAVEL:
                int prob = new Random().nextInt(101);
                if (prob >= 65)
                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.FLINT));
                else
                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GRAVEL));
                break;
            default:
                break;
        }

        e.getBlock().setType(Material.AIR);
        e.getBlock().getState().update();
    }
}
