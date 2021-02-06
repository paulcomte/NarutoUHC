/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.akatsuki.IDeidara;
import fr.rqndomhax.narutouhc.inventories.role.shinobi.IShikamaru;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.game.MGamePublicRoles;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import java.util.HashSet;
import java.util.Set;

public class Deidara extends RoleInfo {

    boolean hasUsedCapacity = true;

    public Deidara(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.DEIDARA);
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

        player.openInventory(new IDeidara(setup, player, players, inventory_size*9).getInventory());

        hasUsedCapacity = true;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage("");
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Rôle " + ChatColor.BLACK + "-----");
        player.sendMessage("Vous êtes Deidara.");
        player.sendMessage("Votre but est de gagner avec l'akatsuki.");
        player.sendMessage("Pour ce faire, toutes les 20 minutes, vous pourrez faire spawn une tnt qui s'allumera automatiquement sur une personne se trouvant dans un rayon de 100 blocks autour de vous, avec la commande /na deidara");
        player.sendMessage("Un inventaire s'ouvrira et vous n'aurez qu'à cliquer sur le joueur de votre choix.");
        player.sendMessage("A votre lieu de mort, il y aura un bloc 3x3 de tnt qui s'allumera automatiquement.");
    }

    @Override
    public void onNewEpisode(int episode) {
        hasUsedCapacity = false;
    }

    @Override
    public void onPrematureDeath(Location deathLocation) {
        Set<TNTPrimed> tnts = new HashSet<>();

        for (double y = deathLocation.getY()-1; y <= deathLocation.getY()+1; y++)
            for (double x = deathLocation.getX()-1; x <= deathLocation.getX()+1; x++)
                for (double z = deathLocation.getZ()-1; z <= deathLocation.getZ()+1; z++)
                    tnts.add(deathLocation.getWorld().spawn(new Location(deathLocation.getWorld(), x, y, z), TNTPrimed.class));
        for (TNTPrimed tnt : tnts)
            tnt.setFuseTicks(0);
    }

    @Override
    public void onTeam() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null)
            return;

        StringBuilder sb = new StringBuilder();

        for (GamePlayer gamePlayer : MGamePublicRoles.akatsukis) {
            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;
            sb.append(p.getName());
            sb.append("  ");
        }
        player.sendMessage(ChatColor.BLACK + "----- " + ChatColor.GOLD + "Equipe " + ChatColor.BLACK + "-----");
        player.sendMessage(sb.toString());
    }
}
