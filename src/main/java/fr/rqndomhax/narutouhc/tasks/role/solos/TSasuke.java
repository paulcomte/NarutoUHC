/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.solos;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.role.solos.Sasuke;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class TSasuke extends BukkitRunnable {

    private final Setup setup;
    private final Sasuke sasuke;
    int remainingTime = 10*60;

    public TSasuke(Setup setup, Sasuke sasuke) {
        this.setup = setup;
        this.sasuke = sasuke;
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED)) {
            cancel();
            return;
        }

        if (sasuke == null || sasuke.getGamePlayer().isDead)
            return;

        Player player = Bukkit.getPlayer(sasuke.getGamePlayer().uuid);
        if (player == null)
            return;

        if (remainingTime == 0) {

            int random = new Random().nextInt(3);
            System.out.println("random = " + random);
            Set<GamePlayer> gamePlayers = new HashSet<>(setup.getGame().getGamePlayers());

            for (int i = 0; i <= 2; i++) {
                if (i == random) {
                    Optional<GamePlayer> gp = gamePlayers.stream().filter(o -> !o.isDead && o.role != null && o.role.getRole().equals(Roles.DANZO)).findFirst();
                    if (!gp.isPresent()) {
                        cancel();
                        return;
                    }
                    sasuke.list.add(gp.get());
                    gamePlayers.remove(gp.get());
                }
                else {
                    Optional<GamePlayer> gp = gamePlayers.stream().filter(o -> !o.isDead && o.role != null && !o.role.getRole().equals(Roles.DANZO) && !o.role.getRole().equals(Roles.SASUKE)).findFirst();
                    gp.ifPresent(gamePlayer -> {
                        sasuke.list.add(gamePlayer);
                        gamePlayers.remove(gamePlayer);
                    });
                }
            }

            sasuke.showList();
            cancel();
            return;
        }

        remainingTime--;
    }
}
