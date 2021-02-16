/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Kakuzu extends Akatsuki {

    public Kakuzu(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KAKUZU);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        player.setMaxHealth(28);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Kakuzu.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'akatsuki.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez de 4 coeurs supplémentaires.");
    }
}
