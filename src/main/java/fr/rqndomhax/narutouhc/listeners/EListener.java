/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

public class EListener implements Listener {
    
    private final Setup setup;

    public EListener(Setup setup) {
        this.setup = setup;
    }

    @EventHandler(ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {
        for (int i = 0; i < 4; i++)
            if (event.getLine(i).matches("^[a-zA-Z0-9ÀÁÂÄÇÈÉÊËÌÍÎÏÒÓÔÖÙÚÛÜàáâäçèéêëîïôöûü &]*$") && event.getLine(i).length() > 20)
                event.setCancelled(true);
    }

    @EventHandler
    public void onMine(BlockBreakEvent e) {
        if (e.getBlock().getType() == null)
            return;

        if (e.getBlock().getType().equals(Material.DIAMOND_ORE) && !e.isCancelled()) {
            GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());
            
            if (gamePlayer != null && !gamePlayer.isDead)
                gamePlayer.diamonds += 1;
        }
        if (e.getBlock().getType().equals(Material.GOLD_ORE) && !e.isCancelled()) {
            GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());

            if (gamePlayer != null && !gamePlayer.isDead)
                gamePlayer.golds += 1;
        }

    }
}
