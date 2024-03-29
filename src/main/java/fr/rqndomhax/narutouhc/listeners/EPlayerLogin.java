/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.*;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MGameActions;
import fr.rqndomhax.narutouhc.managers.MVillagers;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.tabscores.GameScoreboard;
import fr.rqndomhax.narutouhc.tabscores.TabListManager;
import fr.rqndomhax.narutouhc.utils.Messages;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EPlayerLogin implements Listener {

    private final Setup setup;
    private final Location initLocation = new Location(Bukkit.getWorld(Maps.NO_PVP.name()), 0, 230, 0);

    public EPlayerLogin(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {

        if (setup.getGame().getGameState().equals(GameState.LOADING)) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Messages.SERVER_STARTING);
            return;
        }

        if (!e.getPlayer().isOp() && GameInfo.gameHost == null) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Messages.NOT_ALLOWED);
            return;
        }

        if (GameInfo.bannedPlayers.contains(e.getPlayer().getUniqueId())) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Messages.PLAYER_BANNED);
            return;
        }

        if (setup.getGame().getGameRules().hasWhitelist && !Bukkit.getWhitelistedPlayers().contains(e.getPlayer()) && !e.getPlayer().isOp() && (GameInfo.gameHost == null || !GameInfo.gameHost.equals(e.getPlayer().getUniqueId())) && (GameInfo.tmpGameHost == null || !GameInfo.tmpGameHost.equals(e.getPlayer().getName()))) {
            e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Messages.NOT_ALLOWED);
            return;
        }

        if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING) && (setup.getGame().getGamePlayers().size() == setup.getGame().getGameRules().activatedRoles.size()) && !e.getPlayer().isOp()) {
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, Messages.FULL);
            return;
        }

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());

        if (gamePlayer == null || gamePlayer.isDead) {
            if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING))
                return;
            if (!setup.getGame().getGameRules().allowSpectators && !e.getPlayer().isOp())
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Messages.NOT_ALLOWED);
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage(null);

        GameScoreboard.newGameScoreboard(e.getPlayer());

        if (GameInfo.gameHost == null && (e.getPlayer().isOp() || e.getPlayer().getName().equals(GameInfo.tmpGameHost))) {
            GameInfo.gameHost = e.getPlayer().getUniqueId();
            GameInfo.tmpGameHost = null;
            setup.getGame().getGameRules().hasWhitelist = true;
        }

        if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            e.setJoinMessage(Messages.PLAYER_JOIN.replace("%player%", e.getPlayer().getName()));
            if (setup.getGame().getMainTask() != null) {
                MGameActions.sendInfos(setup.getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage " + ChatColor.RED + "annulé", Instrument.BASS_DRUM, true, 0, Note.Tone.B);
                setup.getGame().removeTask();
            }

            GamePlayer gamePlayer = new GamePlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName());
            setup.getGame().getGamePlayers().add(gamePlayer);
            MGameActions.clearPlayerLobby(e.getPlayer());

            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getPlayer().teleport(initLocation);
                }
            }.runTaskLater(setup.getMain(), 2);

            return;
        }

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());

        if (gamePlayer == null) {
            e.getPlayer().sendMessage(Messages.GAME_ALREADY_STARTED);
            MGameActions.clearPlayer(e.getPlayer());
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getUniqueId() != e.getPlayer().getUniqueId() && !player.getGameMode().equals(GameMode.SPECTATOR)).findAny().ifPresent(player -> e.getPlayer().teleport(player.getLocation()));
            PlayerManager.setVisibilityToPlayers(e.getPlayer(), true);
            return;
        }

        PlayerManager.setNameTagVisible(e.getPlayer(), !setup.getGame().getGameRules().activatedScenarios.contains(Scenarios.NO_NAME_TAG));

        if (gamePlayer.isDead) {
            MGameActions.clearPlayer(e.getPlayer());
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.getPlayer().teleport(gamePlayer.deathLocation);
            return;
        }

        if (gamePlayer.role != null)
            gamePlayer.role.giveEffects();

        Villager villager = MVillagers.getVillager(gamePlayer);

        if (villager != null) {
            Location location = villager.getLocation();
            MVillagers.deleteVillager(villager);
            e.getPlayer().teleport(location);
        }

        SActions.giveScenariosEffect(setup.getGame().getGameRules().activatedScenarios, gamePlayer);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        e.setQuitMessage(null);
        ((CraftPlayer) e.getPlayer()).getHandle().getProfile().getProperties();

        GameScoreboard.removeGameScoreboard(e.getPlayer());

        TabListManager.tabPlayers.remove(e.getPlayer());

        PlayerManager.setNameTagVisible(e.getPlayer(), true);

        if (Bukkit.getOnlinePlayers().size() == 1) {
            setup.getGame().getGameRules().hasWhitelist = false;
            GameInfo.gameHost = null;
        }

        GameInfo.gameCoHost.remove(e.getPlayer().getUniqueId());

        if (setup.getGame().getGameState().equals(GameState.LOBBY_WAITING)) {
            e.setQuitMessage(Messages.PLAYER_LEFT.replace("%player%", e.getPlayer().getName()));
            if (setup.getGame().getMainTask() != null) {
                MGameActions.sendInfos(setup.getGame().getGamePlayers(), ChatColor.BLACK + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC", ChatColor.DARK_AQUA + "Démarrage " + ChatColor.RED + "annulé", Instrument.BASS_DRUM, true, 0, Note.Tone.B);
                setup.getGame().removeTask();
            }
            setup.getGame().getGamePlayers().removeIf(player -> player.uuid == e.getPlayer().getUniqueId());

            if (setup.getGame().getGameRules().deathInventoryInEdit != null && setup.getGame().getGameRules().deathInventoryInEdit.equals(e.getPlayer().getUniqueId()))
                setup.getGame().getGameRules().deathInventoryInEdit = null;

            if (setup.getGame().getGameRules().startInventoryInEdit != null && setup.getGame().getGameRules().startInventoryInEdit.equals(e.getPlayer().getUniqueId()))
                setup.getGame().getGameRules().startInventoryInEdit = null;
            return;
        }

        GamePlayer player = setup.getGame().getGamePlayer(e.getPlayer().getUniqueId());
        if (player != null && !player.isDead && !player.isInDead){
            InventoryManager.saveInventory(player.inventory, e.getPlayer(), false);
            MVillagers.createVillager(e.getPlayer().getLocation(), player);
        }
    }
}
