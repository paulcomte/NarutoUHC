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
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.solos.Danzo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TDanzo extends BukkitRunnable {

    int remainingTime = 10*60;
    List<GamePlayer> knownUchihas = new ArrayList<>();
    private final Setup setup;
    private final Danzo danzo;

    public TDanzo(Setup setup, Danzo danzo) {
        this.setup = setup;
        this.danzo = danzo;

        for (GamePlayer gp : setup.getGame().getGamePlayers()) {
            if (gp.role == null)
                continue;

            Roles role = gp.role.getRole();

            if (role.equals(Roles.SASUKE) || role.equals(Roles.MADARA) || role.equals(Roles.ITACHI) || role.equals(Roles.OBITO) || role.equals(Roles.SHISUI))
                knownUchihas.add(gp);
        }
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED)) {
            cancel();
            return;
        }

        if (danzo == null || danzo.getGamePlayer().isDead)
            return;

        Player player = Bukkit.getPlayer(danzo.getGamePlayer().uuid);
        if (player == null)
            return;

        if (remainingTime == 0) {
            GamePlayer incoming = getNew();

            if (incoming == null) {
                cancel();
                return;
            }

            MGamePublicRoles.uchihas.add(incoming);
            player.sendMessage(Messages.PREFIX + "Vous avez découvert un nouvel Uchiha !");

            if (knownUchihas.size() == 0) {
                cancel();
                return;
            }
            remainingTime = 10*60;
        }

        remainingTime--;
    }

    private GamePlayer getNew() {
        if (knownUchihas.size() == 0)
            return null;

        GamePlayer incoming = knownUchihas.get(0);

        if (incoming == null || incoming.isDead) {
            knownUchihas.remove(0);
            return getNew();
        }

        knownUchihas.remove(0);
        return incoming;
    }
}
