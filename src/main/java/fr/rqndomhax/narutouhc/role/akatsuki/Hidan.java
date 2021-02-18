/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.tasks.role.akatsuki.THidan;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Hidan extends Akatsuki {

    public Hidan(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.HIDAN);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Hidan.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'akatsuki.");
        player.sendMessage(ChatColor.BLUE + "Si vous mourrez, vous ressuscitez 7 minutes après, tant qu'un membre de l'akatsuki est toujours en vie.");
        player.sendMessage(ChatColor.BLUE + "Vous ressuscitez avec un full fer protection 1, une épée en fer sharpness 1, 64 steaks, 128 pierre, 1 seau d'eau, et 2 pommes dorée.");
    }

    @Override
    public void onDeath(Setup setup) {
        new THidan(setup, getGamePlayer());
    }
}
