/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TDeath extends BukkitRunnable {

    private final Setup setup;
    private final GamePlayer gamePlayer;
    private final GamePlayer killer;
    private final int droppedExp;
    private final List<ItemStack> drops;
    public int timeLeft;

    public TDeath(Setup setup, GamePlayer gamePlayer, GamePlayer killer, int timeLeft, int droppedExp, List<ItemStack> drops) {
        this.setup = setup;
        this.gamePlayer = gamePlayer;
        this.killer = killer;
        this.timeLeft = timeLeft;
        this.droppedExp = droppedExp;
        this.drops = drops;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || gamePlayer == null || !gamePlayer.isDead) {
            cancel();
            return;
        }

        if (timeLeft <= 5 && timeLeft > 0 || timeLeft == 10 || timeLeft == 15 || timeLeft == 30 || timeLeft == 60)
            MGameActions.sendInfo(gamePlayer, timeLeft);

        if (timeLeft == 0) {
            if (gamePlayer.role != null)
                gamePlayer.role.onDeath(setup);
            Messages.showDeath(gamePlayer, setup.getGame().getGameRules().showRoleOnDeath);
            gamePlayer.deathLocation.getWorld().strikeLightningEffect(gamePlayer.deathLocation);
            drops.forEach(drop -> gamePlayer.deathLocation.getWorld().dropItemNaturally(gamePlayer.deathLocation, drop));
            gamePlayer.deathLocation.getWorld().spawn(gamePlayer.deathLocation, ExperienceOrb.class).setExperience(droppedExp);
            InventoryManager.dropInventory(setup.getGame().getGameRules().deathInventory, gamePlayer.deathLocation, true);

            Player player = Bukkit.getPlayer(gamePlayer.uuid);
            if (player != null)
                player.setHealth(0);

            if (killer != null)
                MGameActions.addKill(killer, gamePlayer);
            cancel();
        }

        timeLeft--;
    }

}
