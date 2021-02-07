/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Akatsuki extends RoleInfo {

    public Akatsuki(GamePlayer gamePlayer, Roles role) {
        super(gamePlayer, role);
    }

    @Override
    public void onTeam() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        StringBuilder sb = new StringBuilder();

        for (GamePlayer gamePlayer : MGamePublicRoles.akatsukis) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;
            sb.append(p.getName());
            sb.append("  ");
        }
        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(sb.toString());
    }
}
