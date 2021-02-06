/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.inventories.role.akatsuki.INagato;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Nagato extends RoleInfo {

    public boolean hasUsedCapacity = false;

    public Nagato(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.NAGATO);
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

            if (!gamePlayer.isDead)
                continue;

            if (gamePlayer.role == null || gamePlayer.role.getRole() == null || gamePlayer.role.getRole().getTeam() == null || !gamePlayer.role.getRole().getTeam().equals(Team.AKATSUKI))
                return;

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

        player.openInventory(new INagato(setup, player, players, inventory_size*9, this).getInventory());

        hasUsedCapacity = true;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Nagato.");
        player.sendMessage("Votre but est de gagner avec l'akatsuki.");
        player.sendMessage("Pour ce faire, une fois dans la partie, et quand vous le voulez, vous pourrez ressusciter une personne de l'akatsuki.");
        player.sendMessage("Avec la commande /na nagato.");
        player.sendMessage("Un inventaire s'ouvrira et vous devrez cliquer sur la personne de votre choix.");
        player.sendMessage("La personne ressuscitée apparaitra avec un full fer protection 1, une épée en fer sharpness 1, une pomee dorée, et 64 steaks.");
    }
}
