/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.solos;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.tasks.role.solos.TDanzo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Danzo extends RoleInfo {

    public double maxHealth = 20;

    public Danzo(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.DANZO);
    }

    @Override
    public void onCommand(Setup setup) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        Messages.showList(player, MGamePublicRoles.uchihas);
    }

    @Override
    public void onKill(GamePlayer killed) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (killed.role == null || player == null)
            return;

        Roles role = killed.role.getRole();

        if (role.equals(Roles.MADARA) || role.equals(Roles.ITACHI) || role.equals(Roles.OBITO) || role.equals(Roles.SHISUI) || killed.role.getRole().equals(Roles.SASUKE)) {
            maxHealth += 5;
            giveEffects();
            player.sendMessage(Messages.PREFIX + "Vous avez reçu 2.5 coeurs supplémentaires !");
        }
    }

    @Override
    public void onInit(Setup setup) {
        new TDanzo(setup, this);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 0, false, false));
        player.setMaxHealth(maxHealth);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Danzo.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner seul");
        player.sendMessage(ChatColor.BLUE + "Vous disposez de l'effet resistance 1.");
        player.sendMessage(ChatColor.BLUE + "Lorsque vous tuez un Uchiha vous obtenez 2.5 coeurs supplémentaires.");
        player.sendMessage(ChatColor.BLUE + "Toutes les 10 minutes, vous connaîtrez le pseudo d'un Uchiha (Sasuke, Madara, Itachi, Obito, Shisui).");
        player.sendMessage(ChatColor.BLUE + "Vous pouvez consulter votre liste d'Uchiha connu avec /na danzo.");
    }

}
