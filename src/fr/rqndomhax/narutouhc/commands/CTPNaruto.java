/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.akatsuki.Deidara;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CTPNaruto implements CommandExecutor {

    private final Setup setup;

    public CTPNaruto(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        gamePlayer.role = new Deidara(gamePlayer);
        //player.teleport(new Location(Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()), 0, 120, 0));
        //player.teleport(new Location(Bukkit.getWorld("world"), -228, 151, 228));
        return true;
    }

}
