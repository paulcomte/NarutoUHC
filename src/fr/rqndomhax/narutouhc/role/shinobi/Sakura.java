/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.shinobi;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.IShikamaru;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
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

            if (gamePlayer.isDead)
                continue;

            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;

            players.add(gamePlayer);
        }

        int size = 0;
        int inventory_size = 1;

        for (GamePlayer gamePlayer : players) {
            if (size == 9) {
                inventory_size++;
                size = 0;
            }
            size++;
        }


        new IShikamaru(setup, player, players, inventory_size*9);

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

        player.sendMessage("Vous êtes Sakura.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, vous disposez de la commande \"/na sakura\", vous pourrez donner 1 minute de régénération 2 par épisode, ainsi que 2 minutes d'absorption (2 coeurs), à la personne de votre choix, ou à vous-même,");
        player.sendMessage("un inventaire s'ouvrira pour sélectionner cette personne,");
        player.sendMessage("toutefois si vous quitter l'inventaire, vous ne pourrez plus utiliser la commande pour le reste de l'épisode.");
    }
}
