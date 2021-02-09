/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.utils.Chrono;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;

public class CRules implements CommandExecutor {

    private final Setup setup;
    private final DecimalFormat format = new DecimalFormat("#.#");

    public CRules(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        GameRules rules = setup.getGame().getGameRules();

        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");

        sender.sendMessage(ChatColor.DARK_AQUA + "Annonce des rôles: " + ChatColor.GOLD + Chrono.timeToString(rules.rolesAnnounce));

        sender.sendMessage(ChatColor.DARK_AQUA + "Temps de préparation: " + ChatColor.GOLD + Chrono.timeToString(rules.preparationDuration));

        sender.sendMessage(ChatColor.DARK_AQUA + "Temps avant bordure: " + ChatColor.GOLD + Chrono.timeToString(rules.gameBorder.timeBeforeResize));

        sender.sendMessage(ChatColor.DARK_AQUA + "Taille de la bordure original: " + ChatColor.GOLD + rules.gameBorder.defaultSize + ChatColor.DARK_AQUA + " blocks.");

        sender.sendMessage(ChatColor.DARK_AQUA + "Taille de la bordure final: " + ChatColor.GOLD + rules.gameBorder.finalSize + ChatColor.DARK_AQUA + " blocks.");

        if (rules.gameBorder.speed == 1d)
            sender.sendMessage(ChatColor.DARK_AQUA + "Vitesse de la bordure: " + ChatColor.GOLD + format.format(rules.gameBorder.speed) + " block/s");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Vitesse de la bordure: " + ChatColor.GOLD + format.format(rules.gameBorder.speed) + " blocks/s");

        if (rules.gameBorder.damages == 1d)
            sender.sendMessage(ChatColor.DARK_AQUA + "Dégâts de la bordure: " + ChatColor.GOLD + format.format(rules.gameBorder.damages) + " coeur/s");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Dégâts de la bordure: " + ChatColor.GOLD + format.format(rules.gameBorder.damages) + " coeurs/s");

        sender.sendMessage(ChatColor.DARK_AQUA + "Centre de la bordure : " + ChatColor.GOLD + rules.gameBorder.center.getX() + ChatColor.DARK_AQUA + " / " + ChatColor.GOLD + rules.gameBorder.center.getZ());

        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        return true;
    }

}
