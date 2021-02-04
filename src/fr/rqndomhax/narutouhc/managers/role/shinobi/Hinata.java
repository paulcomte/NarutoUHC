/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role.shinobi;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Hinata extends RoleInfo {

    public int commandUses = 2;
    public boolean isNarutoDead = false;

    public Hinata(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.HINATA);
    }

    @Override
    public void onPlayerDeath(GamePlayer gamePlayer) {
        if (gamePlayer.role == null) return;
        if (!gamePlayer.role.getRole().equals(Roles.NARUTO)) {
            isNarutoDead = true;
            giveEffects();
        }
    }

    @Override
    public void onPlayerJoin() {
        giveEffects();
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Hinata.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, vous disposez de la commande \"/na hinata\" qui affichera une liste de joueurs présents dans un rayon de 50 blocks.");
        player.sendMessage("Utilisations restantes : " + ChatColor.GREEN + commandUses);
    }

    @Override
    public void giveEffects() {
        if (!isNarutoDead) return;

        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
    }

    @Override
    public void onCommand() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        if (commandUses == 0) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        commandUses--;
    }
}
