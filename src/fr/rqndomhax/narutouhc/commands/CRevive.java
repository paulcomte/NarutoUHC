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
            MRules rules = setup.getGame().getGameInfo().getMRules();

            if (!rules.gameHost.equals(player.getUniqueId()) && !rules.gameCoHost.contains(player.getUniqueId())) {
                player.sendMessage(Messages.COMMAND_ONLY_HOST);
                return false;
            }
        }

        MPlayer mPlayer = setup.getGame().getMPlayer(args[0]);

        if (mPlayer == null) {
            sender.sendMessage(Messages.PLAYER_NOT_PLAYING);
            return false;
        }

        if (!mPlayer.isDead) {
            sender.sendMessage(Messages.PLAYER_NOT_DEAD);
            return false;
        }

        Player player = Bukkit.getPlayer(mPlayer.uuid);

        revive(player, mPlayer);
        sender.sendMessage(Messages.PLAYER_NOW_RESURRECTED.replace("%player%", player.getName()));
        return true;
    }

    public void revive(Player player, MPlayer mPlayer) {
        InventoryManager.giveInventory(mPlayer.inventory, player);
        mPlayer.isDead = false;
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(MGameActions.teleportToRandomLocation(Bukkit.getWorld(setup.getGame().getGameInfo().getMRules().currentMap.name())));
        player.sendMessage(Messages.PLAYER_RESURRECTED);
    }
}
