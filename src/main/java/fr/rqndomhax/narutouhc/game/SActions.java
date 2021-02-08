/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game;

import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public abstract class SActions {

    public static void giveScenariosEffect(Set<Scenarios> activatedScenarios, Set<GamePlayer> players) {
        if (activatedScenarios.contains(Scenarios.CAT_EYES))
            giveNightVision(players);
    }

    private static void giveNightVision(Set<GamePlayer> players) {
        for (GamePlayer gamePlayer : players) {

            if (gamePlayer == null) continue;
            Player player = Bukkit.getPlayer(gamePlayer.uuid);
            if (player == null) continue;

            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0, false, false));
        }
    }

}
