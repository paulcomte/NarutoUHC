/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tasks.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.role.shinobi.Neji;
import fr.rqndomhax.narutouhc.tasks.role.Timers;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TNeji extends BukkitRunnable {

    private final Setup setup;
    private final Neji neji;
    private final GamePlayer selected;
    private final Player nejiPlayer;
    private final Player selectedPlayer;
    int remainingTime = Timers.NEJI;

    public TNeji(Setup setup, Neji neji, GamePlayer selected, Player nejiPlayer, Player selectedPlayer) {
        this.setup = setup;
        this.neji = neji;
        this.selected = selected;
        this.nejiPlayer = nejiPlayer;
        this.selectedPlayer = selectedPlayer;
        nejiPlayer.sendMessage(Messages.PREFIX + "Début de l'analyse de " + selectedPlayer.getName());
        runTaskTimerAsynchronously(setup.getMain(), 0, 20);
    }

    @Override
    public void run() {

        if (setup.getGame().getGameState().equals(GameState.GAME_FINISHED) || nejiPlayer == null || neji == null || neji.getGamePlayer().isDead) {
            cancel();
            return;
        }

        if (selectedPlayer == null || remainingTime == 0) {
            if (selected.role.getRole().getTeam().equals(Team.AKATSUKI) || selected.role.getRole().getTeam().equals(Team.OROCHIMARU)) {
                nejiPlayer.sendMessage(Messages.PREFIX + "Le joueur " + selected.name + " est " + ChatColor.RED + "suspect " + ChatColor.RESET + "!");
                nejiPlayer.playSound(nejiPlayer.getLocation(), "mob.wither.death", 2, 0f);
            }
            else {
                nejiPlayer.sendMessage(Messages.PREFIX + "Le joueur " + selected.name + " est " + ChatColor.GREEN + "gentil " + ChatColor.RESET + "!");
                nejiPlayer.playSound(nejiPlayer.getLocation(), "portal.trigger", 2, 2f);
            }
            cancel();
            return;
        }

        if (PlayerManager.getRadius(nejiPlayer.getLocation(), selectedPlayer.getLocation()) > 15*15) {
            nejiPlayer.sendMessage(Messages.PREFIX + "Le joueur " + selected.name + " s'est " + ChatColor.DARK_RED + "échappé " + ChatColor.RESET + "!");
            nejiPlayer.playSound(nejiPlayer.getLocation(), "random.break", 2, 1f);
            cancel();
            return;
        }

        remainingTime--;
    }

}
