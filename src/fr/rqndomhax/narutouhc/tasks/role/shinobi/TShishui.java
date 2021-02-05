/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.RoleType;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TShishui extends BukkitRunnable {

    private final Setup setup;
    private final List<GamePlayer> players = new ArrayList<>();
    private final GamePlayer shishui;
    int remainingTime = 15*60;

    public TShishui(Setup setup, GamePlayer shishui) {
        this.setup = setup;
        this.shishui = shishui;
        int n = new Random().nextInt(3);
        for (int i = 0; i < 3; i++) {
            Optional<GamePlayer> random;
            if (n != i)
                random = setup.getGame().getGamePlayers().stream().filter(p -> p != shishui && !p.isDead && p.role != null && p.role.getRole() != null && p.role.getRole().getRoleType() != null && !p.role.getRole().getRoleType().equals(RoleType.AKATSUKI)).findAny();
            else
                random = setup.getGame().getGamePlayers().stream().filter(p -> p != shishui && !p.isDead && p.role != null && p.role.getRole() != null && p.role.getRole().getRoleType() != null && p.role.getRole().getRoleType().equals(RoleType.AKATSUKI)).findAny();
            random.ifPresent(players::add);
        }
    }

    @Override
    public void run() {
        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED)) {
            cancel();
            return;
        }

        if (shishui == null || shishui.isDead) {
            cancel();
            return;
        }

        if (remainingTime == 0) {
            remainingTime = 15*60;
            if (players.size() == 0) {
                cancel();
                return;
            }

            Player player = Bukkit.getPlayer(shishui.uuid);
            if (player == null)
                return;

            player.sendMessage("===== Rôle =====");
            player.sendMessage(players.get(0).role.getRole().name());

            // TODO SEND MESSAGE WITH COLOR
            if (players.size() == 1) {
                cancel();
                return;
            }
            players.remove(0);
        }

        remainingTime--;
    }

}
