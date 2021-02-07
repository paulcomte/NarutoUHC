/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */
/* TODO REMOVE
package fr.rqndomhax.narutouhc.role.solos;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Sasuke extends RoleInfo {

    double maxHealth = 20;

    Team selectedTeam = null;

    public Sasuke(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.SASUKE);
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 0, false, false));
        player.setMaxHealth(maxHealth);
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);

        if (player == null) return;

        if (selectedTeam == null) {
            int random = new Random().nextInt(101);

            if (random >= 90)
                selectedTeam = Team.SASUKE;
            if (random >= 70 && random < 90)
                selectedTeam = Team.OROCHIMARU;
            if (random >= 35 && random < 70)
                selectedTeam = Team.AKATSUKI;
            if (random < 35)
                selectedTeam = Team.SHINOBI;
            getRole().setTeam(selectedTeam);
        }

        switch (selectedTeam) {
            case SASUKE:
                player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
                player.sendMessage(ChatColor.BLUE + "Vous êtes Sasuke Solo.");
                player.sendMessage(ChatColor.BLUE + "Votre but est de gagner seul.");
                player.sendMessage(ChatColor.BLUE + "Si vous tuez Danzo, vous connaîtrez tous les rôles des joueurs de la partie.");
                player.sendMessage(ChatColor.BLUE + "De plus vous obtenez un livre \"sharpness 3\", \"protection 3\" et \"power 2\".");
                player.sendMessage(ChatColor.BLUE + "Après 20 minutes de l'annonce des rôles, 3 pseudos aléatoires seront affichés dans votre chat, l'un des trois sera Danzô.");
                player.sendMessage(ChatColor.BLUE + "Vous avez également l'effet strength 1 permanent.");
                break;
            case AKATSUKI:
                player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
                player.sendMessage(ChatColor.BLUE + "Vous êtes Sasuke de l'Akatsuki.");
                player.sendMessage(ChatColor.BLUE + "Si vous tuez Danzo, un inventaire s'ouvrira avec la tête de tous les joueurs restants.");
                player.sendMessage(ChatColor.BLUE + "Si vous tuez Danzo, un inventaire s'ouvrira avec la tête de tous les joueurs restants.");
                break;
        }
    }

    @Override
    public void onKill(GamePlayer killed) {
        if (killed.role == null || killed.role.getRole() == null || killed.role.getRole().getTeam() == null)
            return;

        if (killed.role.getRole().getTeam().equals(getRole().getTeam()))
            return;

        maxHealth += 3;
        giveEffects();
    }
}
 */
