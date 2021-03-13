/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
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

        if (args.length < 1 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("role")) {
            gamePlayer.role.onDesc();
            return true;
        }

        if (gamePlayer.role instanceof KakashiHatake && args[0].equalsIgnoreCase("kakashi")) {
            if (((KakashiHatake) gamePlayer.role).stolenRole == null)
                gamePlayer.role.onDesc();
            else
                ((KakashiHatake) gamePlayer.role).stolenRole.onDesc();
            return true;
        }

        if (args[0].equalsIgnoreCase("team")) {
            gamePlayer.role.onTeam();
            return true;
        }

        if (args[0].equalsIgnoreCase("compo"))
            return showCompo(player);

        if (gamePlayer.isDead) {
            player.sendMessage(Messages.PLAYER_DEAD);
            return false;
        }

        if (args[0].equalsIgnoreCase("claim")) {
            gamePlayer.role.onClaim();
            return true;
        }

        RoleInfo role = gamePlayer.role;
        if ((gamePlayer.role instanceof KakashiHatake) && ((KakashiHatake) gamePlayer.role).stolenRole != null)
            role = ((KakashiHatake) gamePlayer.role).stolenRole;

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
            case SASUKE:
                if (!args[0].equalsIgnoreCase("sasuke")) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                role.onCommand(setup);
                return true;
            default:
                player.sendMessage(Messages.NOT_YOUR_ROLE);
                return false;
        }
    }

    private boolean showCompo(Player player) {
        StringBuilder sb = new StringBuilder();
        boolean hasTeam = false;
        boolean first = true;
        boolean hasSolos = false;

        StringBuilder solo = new StringBuilder();

        solo.append(ChatColor.GOLD + "Solos\n");

        for (GamePlayer gp : setup.getGame().getGamePlayers()) {
            if (gp.role == null || gp.role.getRole().getTeam() == null)
                continue;

            if (!gp.role.getRole().equals(Roles.DANZO) && !gp.role.getRole().equals(Roles.MADARA) && !gp.role.getRole().equals(Roles.SASUKE))
                continue;

            hasSolos = true;

            if (gp.isDead)
                solo.append(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + gp.role.getRole().getRoleName() + " ");
            else
                solo.append(ChatColor.DARK_GRAY + "" + gp.role.getRole().getRoleName() + " ");
        }

        for (Team team : Team.values()) {

            if (team.equals(Team.DANZO) || team.equals(Team.MADARA) || team.equals(Team.SASUKE))
                continue;

            for (GamePlayer gp : setup.getGame().getGamePlayers()) {
                if (gp.role == null || !gp.role.getRole().getTeam().equals(team))
                    continue;

                if (gp.role.getRole().equals(Roles.SASUKE))
                    continue;

                if (!hasTeam && first) {
                    sb.append(ChatColor.GOLD + team.getTeamName() + "\n");
                    hasTeam = true;
                    first = false;
                }
                else if (!hasTeam && !first) {
                    sb.append("\n\n" + ChatColor.GOLD + team.getTeamName() + "\n");
                    hasTeam = true;
                }
                if (gp.isDead)
                    sb.append(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + gp.role.getRole().getRoleName() + " ");
                else
                    sb.append(ChatColor.DARK_GRAY + "" + gp.role.getRole().getRoleName() + " ");
            }
            hasTeam = false;
        }

        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôles " + ChatColor.BLACK + "-----");
        if (hasSolos) {
            if (!first)
                sb.append("\n");
            sb.append(solo.toString());
        }
        player.sendMessage(sb.toString());
        return true;
    }

}
