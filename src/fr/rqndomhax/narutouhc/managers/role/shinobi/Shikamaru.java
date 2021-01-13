/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.role.shinobi;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Shikamaru extends RoleInfo {

    public Shikamaru(MPlayer mPlayer) {
        super(mPlayer, Roles.SHIKAMARU);
    }

    @Override
    public void onClaim() {

    }

    @Override
    public void onChose() {

    }

    @Override
    public void giveEffects() {

    }

    @Override
    public void onHit(MPlayer mPlayer) {

    }

    @Override
    public void onDesc() {
        Player player = Bukkit.getPlayer(getMPlayer().uuid);
        if (player == null) return;

        player.sendMessage("Vous êtes Shikamaru.");
        player.sendMessage("Votre but est de gagner avec l'alliance shinobi.");
        player.sendMessage("Pour ce faire, Vous donnerez des effets à une personne dans un rayon de 50 blocks grâce à votre capacité spéciale");
        player.sendMessage("Les effets sont \"Blindess 1 + Slowness 2\" pendant 30 secondes");
        player.sendMessage("Cette capacité s'utilisera grâce à la commande \"/na shikamaru\"");
        player.sendMessage("Un inventaire s'ouvrira et vous devrez cliquer sur la personne de votre choix, parmis une liste de joueurs présents dans un rayon de 50 blocks");
        player.sendMessage("Une fois la commande utilisée, même si aucun joueur n'a été séléctionné, elle ne sera utilisable qu'au prochain épisode.");
        player.sendMessage("Vous ne pourrez effectuer la commande que 2 fois !");

    }
}
