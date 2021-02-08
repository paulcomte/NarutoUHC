/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.scenarios;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.rules.Drops;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.Random;

public class SDrop implements Listener {

    private final Setup setup;

    public SDrop(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Optional<Drops> flintDrop = setup.getGame().getGameRules().drops.stream().filter(d -> d.equals(Drops.FLINTS)).findAny();
        double percentage = 0;

        if (!flintDrop.isPresent())
            return;
        if (e.getBlock().getType().equals(Material.GRAVEL)) {
            int prob = new Random().nextInt(101);
            percentage = flintDrop.get().getPercentage();
            if (e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().getEnchants().containsKey(Enchantment.LUCK)) {
                switch(e.getPlayer().getItemInHand().getItemMeta().getEnchants().get(Enchantment.LUCK)) {
                    case 1:
                        percentage += 4;
                        break;
                    case 2:
                        percentage += 15;
                        break;
                    case 3:
                        percentage += 100;
                        break;
                    default: break;
                }
                    percentage += 4;
            }
            if (prob >= 100 - percentage)
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.FLINT));
            else
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GRAVEL));
            e.getBlock().setType(Material.AIR);
            e.getBlock().getState().update();
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        Optional<Drops> pearlDrop = setup.getGame().getGameRules().drops.stream().filter(d -> d.equals(Drops.PEARLS)).findAny();

        if (!pearlDrop.isPresent())
            return;
        if (e.getEntityType().equals(EntityType.ENDERMAN)) {
            e.getDrops().clear();
            int prob = new Random().nextInt(101);
            if (prob >= 100 - pearlDrop.get().getPercentage())
                e.getDrops().add(new ItemStack(Material.ENDER_PEARL));
        }
    }

}
