/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.solos;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.tasks.role.solos.TDanzo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Danzo extends RoleInfo {

    public double maxHealth = 20;
    public final List<GamePlayer> uchiwas = new ArrayList<>();

    public Danzo(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.DANZO);
    }

    @Override
    public void onKill(GamePlayer killed) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (killed.role == null || player == null)
            return;

        if (killed.role.getRole().equals(Roles.MADARA) || killed.role.getRole().equals(Roles.ITACHI) || killed.role.getRole().equals(Roles.SHISUI)) {
            maxHealth += 3;
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

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Danzo.");
        player.sendMessage("Votre but est de gagner seul.");
        player.sendMessage("Pour ce faire vous disposez de l'effet " + ChatColor.BLACK + "résistance 1 " + ChatColor.RESET + "et lorsque vous tuez un Uchiwa (Sasuke, Madara, Itachi, Obito ou Shisui,");
        player.sendMessage("Vous obtenez " + ChatColor.RED + "2.5 coeurs supplémentaires" + ChatColor.RESET + ".");
        player.sendMessage("Toutes les 10 minutes, vous connaîtrez le pseudo d'un Uchiwa, il s'ajoutera dans votre liste que vous pouvez consulter en faisant /na danzo.");
    }

}
