/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.common.WorldGeneration;
import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CGenerate implements CommandExecutor {

    private boolean preload;
    private final Setup setup;

    public CGenerate(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (preload) {
            sender.sendMessage("§cLe serveur est déjà pré-chargé.");
            return false;
        }
        preload = true;
        sender.sendMessage("§aDébut de la prégénération.");
        new WorldGeneration(Bukkit.getWorld(Maps.NO_PVP.name()), setup.getMain());
        return true;
    }
}
