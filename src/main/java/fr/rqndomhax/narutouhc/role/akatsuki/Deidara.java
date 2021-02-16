/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.role.akatsuki;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.role.akatsuki.IDeidara;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import java.util.HashSet;
import java.util.Set;

public class Deidara extends Akatsuki {

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
            if (gamePlayer.equals(getGamePlayer()))
                continue;

            if (gamePlayer.isDead)
                continue;

            Player p = Bukkit.getPlayer(gamePlayer.uuid);

            if (p == null)
                continue;

            if (PlayerManager.getRadius(player.getLocation(), p.getLocation()) <= 100*100)
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

        player.openInventory(new IDeidara(setup, player, players, inventory_size*9).getInventory());

        hasUsedCapacity = true;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player == null) return;

        player.sendMessage(Messages.SEPARATORS);
        player.sendMessage(ChatColor.BLUE + "Vous êtes Deidara.");
        player.sendMessage(ChatColor.BLUE + "Votre but est de gagner avec l'akatsuki.");
        player.sendMessage(ChatColor.BLUE + "Chaque épisode vous aurez la possibilité d'utiliser La commande /na deidara");
        player.sendMessage(ChatColor.BLUE + "Vous pourrez faire spawn une tnt sur une personne se trouvant dans un rayon de 100 blocks autour de vous.");
        player.sendMessage(ChatColor.BLUE + "Lors de votre mort, un bloc de 3*3 de tnt s'allumera automatiquement.");
    }

    @Override
    public void onNewEpisode(int episode) {
        hasUsedCapacity = false;

        Player player = Bukkit.getPlayer(getGamePlayer().uuid);
        if (player != null)
            player.sendMessage(Messages.ROLE_CAPACITY);
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
}
