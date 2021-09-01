/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.utils.Chrono;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import fr.rqndomhax.narutouhc.utils.tools.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class THidan extends BukkitRunnable {

    private int remainingTime = 7*60;
    private final Setup setup;
    private final Set<GamePlayer> akatsukis = new HashSet<>();
    private final GamePlayer hidan;

    public THidan(Setup setup, GamePlayer hidan) {
        this.setup = setup;
        this.hidan = hidan;

        for (GamePlayer gp : setup.getGame().getGamePlayers()) {
            if (gp.role == null || !gp.role.getRole().getTeam().equals(Team.AKATSUKI))
                continue;
            akatsukis.add(gp);
        }
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup == null || setup.getGame() == null || setup.getGame().getGameState() == null || setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || hidan == null || !hidan.isDead) {
            cancel();
            return;
        }

        Player player = Bukkit.getPlayer(hidan.uuid);
        if (player == null)
            return;

        if (akatsukis.stream().allMatch(o -> o.isDead))
            return;

        int current = 7*60 - remainingTime;
        int full = 7*60;

        ActionBar.displayProgressBar("Régénération", Chrono.timeToString(current), current, full, player);

        if (remainingTime == 0) {
            ItemStack[] inventory = hidan.inventory.clone();
            for(int slot = 0; slot < 36; slot++){
                ItemStack item = inventory[slot];
                if (item == null)
                    continue;
                if (item.getType().equals(Material.GOLDEN_APPLE))
                    inventory[slot] = null;
            }

            PlayerManager.revive(setup, player, hidan, inventory);
        }
        remainingTime--;
    }

}
