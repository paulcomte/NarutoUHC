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
import fr.rqndomhax.narutouhc.tasks.role.shinobi.TShishui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Shishui extends RoleInfo {

    public Shishui(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.SHISUI);
    }

    @Override
    public void onRoleGiven(Setup setup) {
        new TShishui(setup, getGamePlayer());
        super.onRoleGiven(setup);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Shishui.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, une fois toutes les 15 minutes, 3 pseudos aléatoires seront affichés dans votre chat,");
        player.sendMessage("l'un des trois sera membre de l'akatsuki.");
    }
}
