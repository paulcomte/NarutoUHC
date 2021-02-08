/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class KakashiHatake extends RoleInfo {

    public RoleInfo stolenRole = null;

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

        if (gamePlayer.equals(getGamePlayer()) || gamePlayer.role == null || gamePlayer.role.getRole() == null) return;
        try {
            stolenRole = (RoleInfo) gamePlayer.role.getRole().getRoleInfo().getDeclaredConstructors()[0].newInstance(getGamePlayer());
            stolenRole.onDesc();
            stolenRole.giveEffects();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Kakashi.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Vous obtiendrez le pouvoir de la première personne que vous touchez.");
        player.sendMessage(ChatColor.BLUE + "Même si vous obtenez la capacité d'un autre clan, vous resterez membre de l'alliance shinobi.");
        if (stolenRole != null) {
            player.sendMessage(ChatColor.GOLD + "Rôle volé : " + stolenRole.getRole().getRoleName());
            player.sendMessage(ChatColor.GOLD + "Vous pouvez connaître sa description avec /na kakashi");
        }
    }

    @Override
    public void onClaim() {
        if (stolenRole != null)
            stolenRole.onClaim();
    }

    @Override
    public void onPrematureDeath(Location deathLocation) {
        if (stolenRole != null)
            stolenRole.onPrematureDeath(deathLocation);
    }

    @Override
    public void onInit(Setup setup) {
        if (stolenRole != null)
            stolenRole.onInit(setup);
    }

    @Override
    public void onNewEpisode(int episode) {
        if (stolenRole != null)
            stolenRole.onNewEpisode(episode);
    }

    @Override
    public void onPlayerDeath(GamePlayer gamePlayer) {
        if (stolenRole != null)
            stolenRole.onPlayerDeath(gamePlayer);
    }

    @Override
    public void onRoleGiven(Setup setup) {
        if (stolenRole != null)
            stolenRole.onRoleGiven(setup);
    }

    @Override
    public void onPlayerJoin() {
        if (stolenRole != null)
            stolenRole.onPlayerJoin();
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
    public void onDeath(Setup setup) {
        if (stolenRole != null)
            stolenRole.onDeath(setup);
    }
}
