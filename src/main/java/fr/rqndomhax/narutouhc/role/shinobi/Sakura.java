/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.shinobi.ISakura;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Sakura extends RoleInfo {

    boolean hasUsedCapacity = false;

    public Sakura(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.SAKURA);
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


        player.openInventory(new ISakura(setup, player, players, inventory_size*9).getInventory());

        hasUsedCapacity = true;
    }

    @Override
    public void onNewEpisode(int episode) {
        hasUsedCapacity = false;
        // TODO SEND MESSAGE PLAYER GOT CAPACITY BACK
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Sakura.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage(ChatColor.BLUE + "Chaque épisode vous aurez la possibilité d'utiliser la commande /na sakura.");
        player.sendMessage(ChatColor.BLUE + "Vous pourrez donner 1 minute de régénaration 2, et de 2 minutes d'apsorption (2 coeurs).");
    }
}
