/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import com.mojang.authlib.properties.Property;
import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.SActions;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.role.shinobi.KakashiHatake;
import fr.rqndomhax.narutouhc.tabscores.TabListManager;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public abstract class PlayerManager {

    private static Scoreboard noNameTagScoreboard = null;

    public static void revive(Setup setup, Player player, GamePlayer gamePlayer, ItemStack[] inventory) {
        gamePlayer.isDead = false;
        gamePlayer.isInDead = false;
        player.teleport(MGameActions.teleportToRandomLocation(Bukkit.getWorld(GameInfo.currentMap.name())));
        InventoryManager.giveInventory(inventory, player);
        if (gamePlayer.role != null)
            gamePlayer.role.giveEffects();
        SActions.giveScenariosEffect(setup.getGame().getGameRules().activatedScenarios, gamePlayer);
        player.setGameMode(GameMode.SURVIVAL);
        setVisibilityToPlayers(player, false);
        player.sendMessage(Messages.PLAYER_RESURRECTED);
    }

    public static double getRadius(Location firstLocation, Location secondLocation) {
        Location first = firstLocation.clone();
        first.setY(secondLocation.getY());
        if (!firstLocation.getWorld().equals(secondLocation.getWorld()))
            return Double.MAX_VALUE;
        return first.distanceSquared(secondLocation);
    }

    public static RoleInfo getRole(RoleInfo role) {
        if ((role instanceof KakashiHatake) && ((KakashiHatake) role).stolenRole != null)
            role = ((KakashiHatake) role).stolenRole;
        return role;
    }

    public static Player getPlayer(int index) {
        int n = 0;
        for (Player player : Bukkit.getOnlinePlayers())
            if (n++ == index)
                return (player);
        return null;
    }

    public static void setVisibilityToPlayers(Player target, boolean hidePlayer) {
        for (Player player : Bukkit.getOnlinePlayers())
            if (player != target)
                if (hidePlayer)
                    player.hidePlayer(target);
                else
                    player.showPlayer(target);
    }

    public static Property getRandomSkin(Set<Player> players, Setup setup) {
        if (players.size() == 0) {
            setup.getGame().getGameRules().activatedScenarios.remove(Scenarios.RANDOM_SKIN);
            return null;
        }

        Optional<Player> player = players.stream().findAny();

        if ((!((CraftPlayer) player.get()).getHandle().getProfile().getProperties().get("textures").iterator().hasNext())) {
            players.remove(player.get());
            return getRandomSkin(players, setup);
        }

        return ((CraftPlayer) player.get()).getHandle().getProfile().getProperties().get("textures").iterator().next();
    }

    public static void setNameTagVisible(Player target, boolean isVisible) {
        if (noNameTagScoreboard == null) {
            if (isVisible)
                return;
            noNameTagScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        }

        Team team = noNameTagScoreboard.getTeam("Hide");
        if (team == null) {
            if (isVisible)
                return;
            team = noNameTagScoreboard.registerNewTeam("Hide");
            team.setNameTagVisibility(NameTagVisibility.NEVER);
        }

        if (team.getEntries().contains(target.getName()) && isVisible) {
            team.removeEntry(target.getName());
            return;
        }

        if (!isVisible)
            team.addEntry(target.getName());
    }

}
