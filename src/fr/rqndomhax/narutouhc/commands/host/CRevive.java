/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CRevive implements CommandExecutor {

    private final Setup setup;

    public CRevive(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (args.length != 1)
            return false;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            GameRules rules = setup.getGame().getGameRules();

            if (!rules.gameHost.equals(player.getUniqueId()) && !rules.gameCoHost.contains(player.getUniqueId())) {
                player.sendMessage(Messages.COMMAND_ONLY_HOST);
                return false;
            }
        }

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(args[0]);

        if (gamePlayer == null) {
            sender.sendMessage(Messages.PLAYER_NOT_PLAYING);
            return false;
        }

        if (!gamePlayer.isDead) {
            sender.sendMessage(Messages.PLAYER_NOT_DEAD);
            return false;
        }

        Player player = Bukkit.getPlayer(gamePlayer.uuid);

        revive(player, gamePlayer);
        sender.sendMessage(Messages.PLAYER_NOW_RESURRECTED.replace("%player%", player.getName()));
        return true;
    }

    public void revive(Player player, GamePlayer gamePlayer) {
        InventoryManager.giveInventory(gamePlayer.inventory, player);
        gamePlayer.isDead = false;
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(MGameActions.teleportToRandomLocation(Bukkit.getWorld(setup.getGame().getGameRules().currentMap.name())));
        player.sendMessage(Messages.PLAYER_RESURRECTED);
    }
}
