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
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.tools.DistanceRadius;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Hinata extends RoleInfo {

    public int commandUses = 2;
    public boolean isNarutoDead = false;
    public boolean hasRedeemed = false;

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
    public void giveEffects() {
        if (!isNarutoDead || hasRedeemed) return;

        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0, false, false));
        hasRedeemed = true;
    }

    @Override
    public void onCommand(Setup setup) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (commandUses == 0) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        int left = 10;

        List<String> playerNames = new ArrayList<>();

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.equals(getGamePlayer()))
                continue;

            if (left == 0)
                break;
            if (gamePlayer.isDead)
                continue;

            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null) {
                Villager villager = MVillagers.getVillager(gamePlayer);
                if (villager == null)
                    continue;
                if (DistanceRadius.getRadius(player.getLocation(), villager.getLocation()) <= 50) {
                    left--;
                    playerNames.add("[OFF] " + villager.getCustomName());
                }
                continue;
            }

            if (DistanceRadius.getRadius(player.getLocation(), p.getLocation()) <= 50)
                playerNames.add(p.getName());

            left--;
        }

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "INFOS " + ChatColor.BLACK + "-----");
        player.sendMessage(playerNames.toString().replace("[", "").replace("]", ""));
        commandUses--;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Hinata.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, vous disposez de la commande \"/na hinata\" qui affichera une liste de joueurs présents dans un rayon de 50 blocks.");
        player.sendMessage("Utilisations restantes : " + ChatColor.GREEN + commandUses);
    }
}
