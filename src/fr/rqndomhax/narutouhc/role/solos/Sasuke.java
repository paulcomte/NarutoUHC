/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.solos;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Sasuke extends RoleInfo {

    double maxHealth = 20;

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

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Sasuke.");
        player.sendMessage("Contrairement aux autres rôles, vous pouvez choisir votre camp.");
        player.sendMessage("Vous pouvez être solo, rejoindre " + ChatColor.GOLD + "l'Akatsuki " + ChatColor.RESET + "ou le " + ChatColor.DARK_RED + "camp Orochimaru" + ChatColor.RESET + ".");
        player.sendMessage("A chaque fois que vous tuez quelqu'un d'un camp différent du vôtre vous obtiendrez " + ChatColor.RED + "1.5 coeurs permanent " + ChatColor.RESET + "en plus");
        player.sendMessage("\nSi vous choisissez d'être solo et que vous tuez Danzo, vous connaîtrez tous les rôles des joueurs de la partie.");
        player.sendMessage("De plus vous obtiendrez un livre " + ChatColor.DARK_PURPLE + "\"sharpness\" 3, \"protection\" 3 et \"power\" 3" + ChatColor.RESET + ".");
        player.sendMessage("\nSi vous rejoignez " + ChatColor.GOLD + "l'Akatsuki " + ChatColor.RESET + "ou " + ChatColor.DARK_RED + "Orocohimaru " + ChatColor.RESET + "et que vous tuez Danzo, un inventaire s'ouvrira avec la tête de tous les joueurs restants et vous pourrez connaître le rôle de 2 personnes, grâce à la commande /na sasuke, en cliquant sur deux d'entre elles.");
        player.sendMessage("\nVous avez également l'effet " + ChatColor.RED + "strength 1 permanent" + ChatColor.RESET + ".");
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
