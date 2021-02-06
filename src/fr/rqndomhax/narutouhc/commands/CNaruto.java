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
import fr.rqndomhax.narutouhc.role.akatsuki.Nagato;
import fr.rqndomhax.narutouhc.role.akatsuki.Obito;
import fr.rqndomhax.narutouhc.role.orochimaru.Kabuto;
import fr.rqndomhax.narutouhc.role.orochimaru.Orochimaru;
import fr.rqndomhax.narutouhc.role.shinobi.Hinata;
import fr.rqndomhax.narutouhc.role.shinobi.Neji;
import fr.rqndomhax.narutouhc.role.shinobi.Sakura;
import fr.rqndomhax.narutouhc.role.shinobi.Shikamaru;
import fr.rqndomhax.narutouhc.role.solos.Danzo;
import fr.rqndomhax.narutouhc.role.solos.Sasuke;
import fr.rqndomhax.narutouhc.utils.Messages;
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

        switch(args[0].toLowerCase()) {
            case "claim":
                gamePlayer.role.onClaim();
                return true;
            case "shikamaru":
                if (!(gamePlayer.role instanceof Shikamaru)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;
            case "hinata":
                if (!(gamePlayer.role instanceof Hinata)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;
            case "neji":
                if (!(gamePlayer.role instanceof Neji)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;
            case "sakura":
                if (!(gamePlayer.role instanceof Sakura)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;

            case "deidara":
                if (!(gamePlayer.role instanceof Deidara)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;
            case "nagato":
                if (!(gamePlayer.role instanceof Nagato)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;
            case "obito":
                if (!(gamePlayer.role instanceof Obito)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;

            case "orochimaru":
                if (!(gamePlayer.role instanceof Orochimaru)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;
            case "kabuto":
                if (!(gamePlayer.role instanceof Kabuto)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;

            case "danzo":
                if (!(gamePlayer.role instanceof Danzo)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;

            case "sasuke":
                if (!(gamePlayer.role instanceof Sasuke)) {
                    player.sendMessage(Messages.NOT_YOUR_ROLE);
                    return false;
                }
                gamePlayer.role.onCommand(setup);
                return true;
        }

        return false;
    }

}
