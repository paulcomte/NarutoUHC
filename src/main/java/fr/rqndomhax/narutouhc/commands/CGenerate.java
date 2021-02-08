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

    private boolean generationAlreadyStarted;
    WorldGeneration worldGeneration;
    private final Setup setup;

    public CGenerate(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (generationAlreadyStarted) {
            if (worldGeneration != null) {
                if (worldGeneration.taskFinished)
                    sender.sendMessage("§cLe serveur est déjà pré-chargé.");
                else
                    sender.sendMessage("§cLe serveur est déjà en cours de pré-génération.");
            }
            return false;
        }
        generationAlreadyStarted = true;
        sender.sendMessage("§aDébut de la prégénération.");
        WorldGeneration worldGeneration = new WorldGeneration(Bukkit.getWorld(Maps.NO_PVP.name()), setup.getMain());
        return true;
    }
}
