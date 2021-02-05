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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class KakashiHatake extends RoleInfo {

    private RoleInfo stolenRole = null;

    public KakashiHatake(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KAKASHI_HATAKE);
    }

    @Override
    public void giveEffects() {
        if (stolenRole != null)
            stolenRole.giveEffects();
    }

    @Override
    public void onHit(GamePlayer gamePlayer) {
        if (gamePlayer == null || stolenRole != null) return;

        if (gamePlayer.equals(getGamePlayer())) return;
        try {
            stolenRole = (RoleInfo) gamePlayer.role.getRole().getRoleInfo().getDeclaredConstructors()[0].newInstance(getGamePlayer());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        stolenRole.onDesc();
        stolenRole.giveEffects();
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Kakashi.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, Vous obtiendrez le pouvoir de la première personne que vous touchez.");
        player.sendMessage("Même si vous obtenez la capacité d’un autre clan, vous resterez tout de même membre de " + ChatColor.LIGHT_PURPLE + "l’alliance shinobi" + ChatColor.RESET + ".");
    }

    @Override
    public void onCommand(Setup setup) {
        if (stolenRole != null)
            stolenRole.onCommand(setup);
    }

    @Override
    public void onKill(GamePlayer killed) {
        if (stolenRole != null)
            stolenRole.onKill(killed);
    }

    @Override
    public void onDeath() {
        if (stolenRole != null)
            stolenRole.onDeath();
    }
}
