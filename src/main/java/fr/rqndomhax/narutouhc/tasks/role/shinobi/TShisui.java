/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.shinobi;

import com.sun.org.apache.xpath.internal.operations.String;
import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TShisui extends BukkitRunnable {

    private final Setup setup;
    private final List<GamePlayer> knownPlayers = new ArrayList<>();
    private final GamePlayer shisui;
    int remainingTime = 15*60;

    public TShisui(Setup setup, GamePlayer shisui) {
        this.setup = setup;
        this.shisui = shisui;

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.role == null || gamePlayer.isDead)
                continue;
            knownPlayers.add(gamePlayer);
        }
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {
        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED)) {
            cancel();
            return;
        }

        if (shisui == null || shisui.isDead)
            return;

        Player player = Bukkit.getPlayer(shisui.uuid);
        if (player == null)
            return;

        if (remainingTime == 0) {
            StringBuilder sb = new StringBuilder();

            int random = new Random().nextInt(3);

            for (int i = 0; i <= 2; i++) {
                if (i == random) {
                    Optional<GamePlayer> gp = knownPlayers.stream().filter(o -> !o.isDead && o.role != null && o.role.getRole().getTeam().equals(Team.AKATSUKI)).findFirst();
                    if (!gp.isPresent()) {
                        cancel();
                        return;
                    }
                    knownPlayers.remove(gp.get());
                    sb.append(gp.get().name);
                }
                else {
                    Optional<GamePlayer> gp = knownPlayers.stream().filter(o -> !o.isDead && o.role != null && !o.role.getRole().equals(Roles.SHISUI) && !o.role.getRole().getTeam().equals(Team.AKATSUKI)).findFirst();
                    gp.ifPresent(gamePlayer -> {
                        knownPlayers.remove(gp.get());
                        sb.append(gamePlayer.name);
                    });
                }
            }

            player.sendMessage(Messages.SEPARATORS);
            player.sendMessage(sb.toString());
            if (!hasNew()) {
                cancel();
                return;
            }
            remainingTime = 15*60;
            return;
        }

        remainingTime--;
    }

    private boolean hasNew() {
        return knownPlayers.stream().anyMatch(o -> !o.isDead && o.role != null && o.role.getRole().getTeam().equals(Team.AKATSUKI));
    }
}
