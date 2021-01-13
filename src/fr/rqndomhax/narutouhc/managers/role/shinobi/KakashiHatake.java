/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class KakashiHatake extends RoleInfo {

    private RoleInfo stolenRole = null;

    public KakashiHatake(MPlayer mPlayer) {
        super(mPlayer, Roles.KAKASHI_HATAKE);
    }

    @Override
    public void onClaim() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.ROLE_NO_ITEMS);
    }

    @Override
    public void giveEffects() {
        if (stolenRole != null)
            stolenRole.giveEffects();
    }

    @Override
    public void onHit(MPlayer mPlayer) {
        if (mPlayer == null || stolenRole != null) return;

        if (mPlayer.equals(getMPlayer())) return;
        try {
            stolenRole = (RoleInfo) mPlayer.role.getRole().getRoleInfo().getDeclaredConstructors()[0].newInstance(getMPlayer());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        stolenRole.onDesc();
        stolenRole.giveEffects();
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Kakashi.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, Vous obtiendrez le pouvoir de la première personne que vous touchez.");
        player.sendMessage("Même si vous obtenez la capacité d’un autre clan, vous resterez tout de même membre de l’alliance shinobi");
    }

    @Override
    public void onCommand() {
        if (stolenRole != null)
            stolenRole.onCommand();
    }

    @Override
    public void onKill(MPlayer killed) {
        if (stolenRole != null)
            stolenRole.onKill(killed);
    }

    @Override
    public void onDeath() {
        if (stolenRole != null)
            stolenRole.onDeath();
    }
}
