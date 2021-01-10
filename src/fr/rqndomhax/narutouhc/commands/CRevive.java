/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
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

        MPlayer mPlayer = setup.getGame().getMPlayer(args[0]);

        if (mPlayer == null) return false;

        if (!mPlayer.isDead) return false;

        Player player = Bukkit.getPlayer(mPlayer.uuid);

        mPlayer.deathInventory.giveInventory(player);
        mPlayer.isDead = false;
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(MGameActions.teleportToRandomLocation(setup));
        return true;
    }
}
