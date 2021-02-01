/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CHeal implements CommandExecutor {

    private final Setup setup;

    public CHeal(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        MRules rules = setup.getGame().getGameInfo().getMRules();
        GameState gameState = setup.getGame().getGameInfo().getGameState();

        if (sender instanceof Player && !rules.gameHost.equals(((Player) sender).getUniqueId()) && !rules.gameCoHost.contains(((Player) sender).getUniqueId())) {
            sender.sendMessage(Messages.COMMAND_ONLY_HOST);
            return false;
        }

        if (gameState.equals(GameState.LOBBY_WAITING) || gameState.equals(GameState.LOBBY_TELEPORTING)) {
            sender.sendMessage(Messages.NOT_IN_GAME);
            return false;
        }

        for (MPlayer mPlayer : setup.getGame().getGamePlayers()) {

            Player player = Bukkit.getPlayer(mPlayer.uuid);
            if (player == null) continue;

            if (player.getHealth() < player.getMaxHealth())
                player.setHealth(player.getMaxHealth());
            if (player.getFoodLevel() < 20)
                player.setFoodLevel(20);
        }

        Bukkit.broadcastMessage(Messages.PLAYERS_HEALED);
        return true;
    }

}
