/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;
import java.util.Random;

public class TShisui extends BukkitRunnable {

    private final Setup setup;
    private final GamePlayer shisui;
    final int random;
    int n = 0;
    int remainingTime = 15*60;

    public TShisui(Setup setup, GamePlayer shisui) {
        this.setup = setup;
        this.shisui = shisui;
        random = new Random().nextInt(3);
    }

    @Override
    public void run() {
        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED)) {
            cancel();
            return;
        }

        if (shisui == null || shisui.isDead)
            return;

        if (remainingTime == 0) {
            Player player = Bukkit.getPlayer(shisui.uuid);
            if (player == null)
                return;

            GamePlayer newPlayer = getNewInfo();

            if (newPlayer == null) {
                cancel();
                return;
            }

            player.sendMessage(Messages.SEPARATORS);
            player.sendMessage(newPlayer.name);
            remainingTime = 15*60;
        }

        remainingTime--;
    }

    private GamePlayer getNewInfo() {
        if (n == 4)
            return null;

        Optional<GamePlayer> newPlayer;

        if (n != random)
            newPlayer = setup.getGame().getGamePlayers().stream().filter(p -> p != shisui && !p.isDead && p.role != null && p.role.getRole() != null && p.role.getRole().getTeam() != null && !p.role.getRole().getTeam().equals(Team.AKATSUKI)).findAny();
        else
            newPlayer = setup.getGame().getGamePlayers().stream().filter(p -> p != shisui && !p.isDead && p.role != null && p.role.getRole() != null && p.role.getRole().getTeam() != null && p.role.getRole().getTeam().equals(Team.AKATSUKI)).findAny();

        if (!newPlayer.isPresent()) {
            n++;
            return getNewInfo();
        }

        return newPlayer.get();
    }

}
