/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.tasks.role.shinobi.TShisui;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Shisui extends RoleInfo {

    public Shisui(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.SHISUI);
    }

    @Override
    public void onRoleGiven(Setup setup) {
        new TShisui(setup, getGamePlayer());
        super.onRoleGiven(setup);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Shisui.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Une fois toutes les 15 minutes, 3 pseudos aléatoires seont affichés dans votre chat.");
        player.sendMessage(ChatColor.BLUE + "L'un des trois sera membre de l'akatsuki."); // TODO FIX TIMER
    }
}
