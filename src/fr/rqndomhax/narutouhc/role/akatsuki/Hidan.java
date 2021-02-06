/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.tasks.role.akatsuki.THidan;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Hidan extends RoleInfo {

    public Hidan(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.HIDAN);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Hidan.");
        player.sendMessage("Votre but est de gagner avec l'akatsuki.");
        player.sendMessage("Pour ce faire, si vous mourrez vous ressuscitez 10 minutes après, tant qu'un membre de l'akatsuki est toujours en vie.");
        player.sendMessage("Cependant vous ressuscitez avec un full fer protection 1, une épée en fer sharpness 1, 64 steaks, 128 pierre et 5 pommes dorée.");
    }

    @Override
    public void onDeath(Setup setup) {
        new THidan(setup, getGamePlayer());
    }
}
