/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MRules;
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
        MRules rules = setup.getGame().getGameInfo().getMRules();
        MBorder border = setup.getGame().getGameInfo().getMBorder();

        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");

        if (rules.rolesAnnounce > 60)
            sender.sendMessage(ChatColor.DARK_AQUA + "Annonce des rôles : " + ChatColor.GOLD + rules.rolesAnnounce / 60 + ChatColor.DARK_AQUA + " minutes.");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Annonce des rôles : " + ChatColor.GOLD + rules.rolesAnnounce / 60 + ChatColor.DARK_AQUA + " minute.");

        if (rules.preparationTime > 60)
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps de préparation : " + ChatColor.GOLD + rules.preparationTime / 60 + ChatColor.DARK_AQUA + " minutes.");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps de préparation : " + ChatColor.GOLD + rules.preparationTime / 60 + ChatColor.DARK_AQUA + " minute.");

        if (border.timeBeforeResize > 60)
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps avant bordure : " + ChatColor.GOLD + border.timeBeforeResize / 60 + ChatColor.DARK_AQUA + " minutes.");
        else
            sender.sendMessage(ChatColor.DARK_AQUA + "Temps avant bordure : " + ChatColor.GOLD + border.timeBeforeResize / 60 + ChatColor.DARK_AQUA + " minute.");

        sender.sendMessage(ChatColor.DARK_AQUA + "Taille de la bordure original : " + ChatColor.GOLD + border.defaultSize + ChatColor.DARK_AQUA + " blocks.");
        sender.sendMessage(ChatColor.DARK_AQUA + "Taille de la bordure final : " + ChatColor.GOLD + border.finalSize + ChatColor.DARK_AQUA + " blocks.");
        sender.sendMessage(ChatColor.DARK_AQUA + "Centre de la bordure : " + ChatColor.GOLD + border.center.getX() + ChatColor.DARK_AQUA + " / " + ChatColor.GOLD + border.center.getZ());
        sender.sendMessage(ChatColor.DARK_PURPLE + "----- " + ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "UHC " + ChatColor.BLACK + "-----");
        return true;
    }

}