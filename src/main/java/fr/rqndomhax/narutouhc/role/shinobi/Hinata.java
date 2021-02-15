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

    public int commandUses = 3;
    long narutoDeathStamp;
    public boolean isNarutoDead = false;

    public Hinata(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.HINATA);
    }

    @Override
    public void onPlayerDeath(GamePlayer gamePlayer) {
        if (gamePlayer.role == null) return;
        if (gamePlayer.role.getRole().equals(Roles.NARUTO)) {
            isNarutoDead = true;
            narutoDeathStamp = System.currentTimeMillis();
            giveEffects();
        }
    }

    @Override
    public void onPlayerJoin() {
        giveEffects();
    }

    @Override
    public void giveEffects() {
        if (!isNarutoDead) return;

        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        long currentStamp = System.currentTimeMillis();
        int duration = (int) ((narutoDeathStamp - currentStamp) / 1000);
        if (duration >= -600)
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration*20, 0, false, false));
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
                if (DistanceRadius.getRadius(player.getLocation(), villager.getLocation()) <= 100*100) {
                    left--;
                    playerNames.add(ChatColor.DARK_GRAY + "(OFF) " + ChatColor.RESET + villager.getCustomName());
                }
                continue;
            }

            if (DistanceRadius.getRadius(player.getLocation(), p.getLocation()) <= 100*100)
                playerNames.add(p.getName());

            left--;
        }

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(playerNames.toString().replace("[", "").replace("]", ""));
        commandUses--;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Hinata.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Trois fois vous aurez la possibilité d'utiliser La commande /na hinata");
        player.sendMessage(ChatColor.BLUE + "Cette commande vous permettra de connaître les joueurs présents autour de vous dans un rayon de 50 blocks.");
        player.sendMessage(ChatColor.BLUE + "En plus de cela si Naruto vient à mourrir vous obtiendrez l'effet strength 1 pendant 10 minutes.");
        if (commandUses == 0)
            player.sendMessage(ChatColor.BLUE + "Utilisations restantes: " + ChatColor.RED + commandUses);
        else
            player.sendMessage(ChatColor.BLUE + "Utilisations restantes: " + ChatColor.GREEN + commandUses);
    }
}
