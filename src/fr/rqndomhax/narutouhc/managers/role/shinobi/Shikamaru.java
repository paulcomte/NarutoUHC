/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role.shinobi;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Shikamaru extends RoleInfo {

    int commandUses = 2;
    boolean commandUsed = false;

    public Shikamaru(GamePlayer gamePlayer) {
        super(gamePlayer, Roles.SHIKAMARU);
    }

    @Override
    public void onCommand() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        if (commandUses == 0) {
            player.sendMessage(Messages.ROLE_NO_MORE_USES);
            return;
        }

        commandUses--;
    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Shikamaru.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, Vous donnerez des effets à une personne dans un rayon de 50 blocks grâce à votre capacité spéciale");
        player.sendMessage("Les effets sont " + ChatColor.GRAY + "\"Blindess 1" + ChatColor.RESET + " + " + ChatColor.DARK_GRAY + "Slowness 2\" " + ChatColor.RESET + "pendant 30 secondes");
        player.sendMessage("Cette capacité s'utilisera grâce à la commande \"/na shikamaru\"");
        player.sendMessage("Un inventaire s'ouvrira et vous devrez cliquer sur la personne de votre choix, parmis une liste de joueurs présents dans un rayon de 50 blocks");
        player.sendMessage("Une fois la commande utilisée, même si aucun joueur n'a été séléctionné, elle ne sera utilisable qu'au prochain épisode.");
        player.sendMessage("Utilisations restantes : " + ChatColor.GREEN + commandUses);
    }

    @Override
    public void onNewEpisode(int episode) {
        commandUsed = false;
    }
}
