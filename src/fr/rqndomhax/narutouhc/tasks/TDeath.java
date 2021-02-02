/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
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
    private final MPlayer mPlayer;
    private final MPlayer killer;
    private final int droppedExp;
    private final List<ItemStack> drops;
    public int timeLeft;

    public TDeath(Setup setup, MPlayer mPlayer, MPlayer killer, int timeLeft, int droppedExp, List<ItemStack> drops) {
        this.setup = setup;
        this.mPlayer = mPlayer;
        this.killer = killer;
        this.timeLeft = timeLeft;
        this.droppedExp = droppedExp;
        this.drops = drops;
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (mPlayer == null) {
            cancel();
            return;
        }

        if (!mPlayer.isDead) {
            cancel();
            return;
        }

        if (timeLeft <= 5 && timeLeft > 0 || timeLeft == 10 || timeLeft == 15 || timeLeft == 30 || timeLeft == 60)
            MGameActions.sendInfo(mPlayer, timeLeft);

        if (timeLeft == 0) {
            Messages.showDeath(mPlayer, setup.getGame().getGameInfo().getMRules().showRoleOnDeath);
            mPlayer.deathLocation.getWorld().strikeLightningEffect(mPlayer.deathLocation);
            drops.forEach(drop -> mPlayer.deathLocation.getWorld().dropItemNaturally(mPlayer.deathLocation, drop));
            mPlayer.deathLocation.getWorld().spawn(mPlayer.deathLocation, ExperienceOrb.class).setExperience(droppedExp);
            InventoryManager.dropInventory(setup.getGame().getGameInfo().getMRules().deathInventory, mPlayer.deathLocation, true);

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player != null)
                player.setHealth(0);

            if (killer != null)
                MGameActions.addKill(killer, mPlayer);
            cancel();
        }

        timeLeft--;
    }

}
