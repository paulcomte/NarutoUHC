/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

public class TObito extends BukkitRunnable {

    private int remainingTime = 30;
    private final Setup setup;
    private final GamePlayer obito;
    private final GamePlayer selected;

    public TObito(Setup setup, GamePlayer obito, GamePlayer selected, Player player, Player selectedPlayer) {
        this.setup = setup;
        this.obito = obito;
        this.selected = selected;
        Location location = new Location(Bukkit.getWorld(setup.getGame().getGameRules().currentMap.name()), 0, 230, 0);
        player.teleport(location);
        selectedPlayer.teleport(location);
        runTaskTimer(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED))
            remainingTime = 0;

        if (remainingTime == 0) {
            if (obito != null && !obito.isDead) {
                Player player = Bukkit.getPlayer(obito.uuid);
                if (player == null) {
                    Villager villager = MVillagers.getVillager(obito);
                    if (villager != null)
                        randomTeleport(villager);
                }
            }
            if (selected != null && !selected.isDead) {
                Player player = Bukkit.getPlayer(selected.uuid);
                if (player == null) {
                    Villager villager = MVillagers.getVillager(selected);
                    if (villager != null)
                        randomTeleport(villager);
                }
            }
            MGameBuild.removeObito(setup);
            cancel();
        }
        remainingTime--;
    }

    private void randomTeleport(Entity entity) {
        // TODO RANDOM TELEPORT
    }
}
