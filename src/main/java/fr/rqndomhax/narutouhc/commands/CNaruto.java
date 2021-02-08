/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.role.shinobi.KakashiHatake;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CNaruto implements CommandExecutor {

    private final Setup setup;

    public CNaruto(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.NEED_PLAYER);
            return false;
        }

        Player player = (Player) sender;

        if (setup.getGame().getMainTask() == null || !setup.getGame().getMainTask().hasRoles) {
            player.sendMessage(Messages.ROLES_NOT_GAVE);
            return false;
        }

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null) {
            player.sendMessage(Messages.NOT_PLAYING);
            return false;
        }

        if (gamePlayer.role == null) {
            player.sendMessage(Messages.NO_ROLE);
            return false;
        }

        if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
            gamePlayer.role.onDesc();
            return true;
        }

        RoleInfo role = gamePlayer.role;
        if ((gamePlayer.role instanceof KakashiHatake) && ((KakashiHatake) gamePlayer.role).stolenRole != null)
            role = ((KakashiHatake) gamePlayer.role).stolenRole;

        if (args[0].equalsIgnoreCase("claim")) {
            role.onClaim();
            return true;
        }

        if (args[0].equalsIgnoreCase("team")) {
            role.onTeam();
            return true;
        }


        if (args[0].equalsIgnoreCase("compo")) {

            StringBuilder sb = new StringBuilder();

            for (GamePlayer gp : setup.getGame().getGamePlayers()) {
                if (gp.role == null)
                    continue;
                if (gp.isDead)
                    sb.append(ChatColor.STRIKETHROUGH + gp.role.getRole().getRoleName());
                else
                    sb.append(gp.role.getRole().getRoleName());
                sb.append(ChatColor.RESET + "\n");
            }

            player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôles " + ChatColor.BLACK + "-----");
            player.sendMessage(sb.toString());
            return true;
        }

        switch(role.getRole()) {
            case SHIKAMARU:
                if (!args[0].equalsIgnoreCase("shikamaru")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            case HINATA:
                if (!args[0].equalsIgnoreCase("hinata")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            case NEJI:
                if (!args[0].equalsIgnoreCase("neji")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            case SAKURA:
                if (!args[0].equalsIgnoreCase("sakura")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            case DEIDARA:
                if (!args[0].equalsIgnoreCase("deidara")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            case NAGATO:
                if (!args[0].equalsIgnoreCase("nagato")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            /*case OBITO:
                if (!args[0].equalsIgnoreCase("obito")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
             */ // TODO REMOVE
            case OROCHIMARU:
                if (!args[0].equalsIgnoreCase("orochimaru")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            case KABUTO:
                if (!args[0].equalsIgnoreCase("kabuto")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            case DANZO:
                if (!args[0].equalsIgnoreCase("danzo")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            /*case SASUKE:
                if (!args[0].equalsIgnoreCase("sasuke")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
             */
            default:
                player.sendMessage(Messages.NOT_YOUR_ROLE);
                return false;
        }
    }

}
