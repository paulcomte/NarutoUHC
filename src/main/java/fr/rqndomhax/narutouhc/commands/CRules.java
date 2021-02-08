/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GameRules;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CRules implements CommandExecutor {

    private final Setup setup;

    public CRules(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        GameRules rules = setup.getGame().getGameRules();

        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");

        if (rules.rolesAnnounce > 60)
            sender.sendMessage(ChatColor.DARK_AQUA + "Annonce des rôles : " + ChatColor.GOLD + rules.rolesAnnounce / 60 + ChatColor.DARK_AQUA + " minutes.");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Annonce des rôles : " + ChatColor.GOLD + rules.rolesAnnounce / 60 + ChatColor.DARK_AQUA + " minute.");

        if (rules.preparationDuration > 60)
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps de préparation : " + ChatColor.GOLD + rules.preparationDuration / 60 + ChatColor.DARK_AQUA + " minutes.");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps de préparation : " + ChatColor.GOLD + rules.preparationDuration / 60 + ChatColor.DARK_AQUA + " minute.");

        if (rules.gameBorder.timeBeforeResize > 60)
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps avant bordure : " + ChatColor.GOLD + rules.gameBorder.timeBeforeResize / 60 + ChatColor.DARK_AQUA + " minutes.");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps avant bordure : " + ChatColor.GOLD + rules.gameBorder.timeBeforeResize / 60 + ChatColor.DARK_AQUA + " minute.");

        sender.sendMessage(ChatColor.DARK_AQUA + "Taille de la bordure original : " + ChatColor.GOLD + rules.gameBorder.defaultSize + ChatColor.DARK_AQUA + " blocks.");
        sender.sendMessage(ChatColor.DARK_AQUA + "Taille de la bordure final : " + ChatColor.GOLD + rules.gameBorder.finalSize + ChatColor.DARK_AQUA + " blocks.");
        sender.sendMessage(ChatColor.DARK_AQUA + "Centre de la bordure : " + ChatColor.GOLD + rules.gameBorder.center.getX() + ChatColor.DARK_AQUA + " / " + ChatColor.GOLD + rules.gameBorder.center.getZ());
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        return true;
    }

}
