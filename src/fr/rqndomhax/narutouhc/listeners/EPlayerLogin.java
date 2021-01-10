/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.MPlayer;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.utils.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EPlayerLogin implements Listener {

    private final Setup setup;

    public EPlayerLogin(Setup setup) {
        this.setup = setup;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOADING)) {
            e.setKickMessage(Messages.SERVER_STARTING);
            return;
        }

        if (setup.getGame().getGameInfo().getmRules().bannedPlayers.contains(e.getPlayer().getUniqueId())) {
            e.setKickMessage(Messages.PLAYER_BANNED);
            return;
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.setJoinMessage(Messages.PLAYER_JOIN.replace("%player%", e.getPlayer().getName()));

        if (setup.getGame().getGameInfo().getmRules().gameHost == null)
            setup.getGame().getGameInfo().getmRules().gameHost = e.getPlayer().getUniqueId();

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING)) {
            setup.getGame().getGamePlayers().add(new MPlayer(e.getPlayer().getUniqueId()));
            setup.getGame().getGameInfo().startMTime(setup);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        e.setQuitMessage(Messages.PLAYER_LEFT.replace("%player%", e.getPlayer().getName()));

        if (Bukkit.getOnlinePlayers().size() == 1)
            setup.getGame().getGameInfo().getmRules().gameHost = null;

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.LOBBY_WAITING))
            setup.getGame().getGamePlayers().removeIf(p -> p.uuid == e.getPlayer().getUniqueId());
        setup.getGame().getGameInfo().removeMTime(setup);
    }
}
