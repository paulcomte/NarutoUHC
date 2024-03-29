/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.orochimaru;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.orochimaru.IKabuto;
import fr.rqndomhax.narutouhc.managers.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class Kabuto extends RoleInfo {

    boolean hasUsedCapacity = true;
    boolean isOrochimaruDead = false;

    public Kabuto(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.KABUTO);
    }

    @Override
    public void onCommand(Setup setup) {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        if (hasUsedCapacity) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        Set<GamePlayer> players = new HashSet<>();

        for (GamePlayer gamePlayer : setup.getGame().getGamePlayers()) {
            if (gamePlayer.equals(getGamePlayer()))
                continue;

            if (gamePlayer.isDead)
                continue;

            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;

            if (PlayerManager.getRadius(player.getLocation(), p.getLocation()) <= 50*50)
                players.add(gamePlayer);
        }

        int size = 0;
        int inventory_size = 1;

        for (GamePlayer ignored : players) {
            if (size == 9) {
                inventory_size++;
                size = 0;
            }
            size++;
        }

        player.openInventory(new IKabuto(setup, player, players, inventory_size*9).getInventory());

        hasUsedCapacity = true;
    }

    @Override
    public void onPlayerDeath(GamePlayer gamePlayer) {
        if (isOrochimaruDead)
            return;
        if (gamePlayer.role == null) return;
        if (gamePlayer.role.getRole().equals(Roles.OROCHIMARU)) {
            isOrochimaruDead = true;
            giveEffects();
            Player player = Bukkit.getPlayer(getGamePlayer().uuid);
            if (player != null)
                player.sendMessage(Messages.PREFIX + "Vous avez reçu l'effet resistance d'Orochimaru !");
        }
    }

    @Override
    public void giveEffects() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 0, false, false));
        if (isOrochimaruDead)
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 0, false, false));
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Kabuto.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec le camp orochimaru.");
        player.sendMessage(ChatColor.BLUE + "Vous disposez de l'effet speed 1.");
        player.sendMessage(ChatColor.BLUE + "Chaque épisode vous aurez la possibilité d'utiliser la commande /na kabuto.");
        player.sendMessage(ChatColor.BLUE + "Vous pourrez donner un effet de wither 2 pendant 5 secondes à un joueur se trouvant dans un rayon de 50 blocks autour de vous.");
        player.sendMessage(ChatColor.BLUE + "Si Orochimaru vient à mourir, vous obtiendrez son effet resistance 1.");
    }

    @Override
    public void onNewEpisode(int episode) {
        hasUsedCapacity = false;

        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player != null)
            player.sendMessage(Messages.ROLE_CAPACITY);
    }

    @Override
    public void onTeam() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        Messages.showList(player, MGamePublicRoles.orochimarus);
    }
}
