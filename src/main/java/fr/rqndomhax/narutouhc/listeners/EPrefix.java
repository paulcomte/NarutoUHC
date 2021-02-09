/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EPrefix implements Listener {

    private final UUID developer = UUID.fromString("9f7be940-8a94-497b-a963-f4af0691c005");
    private final UUID gameDesigner = UUID.fromString("b1dc0e73-3ef9-4fe5-9c42-85b3a841ec53");
    private final UUID slave = UUID.fromString("c7038e0e-dafc-4bcc-9f0c-dbd43aa9b147");
    private final Set<UUID> bugHunters = new HashSet<>();
    private final Set<UUID> friends = new HashSet<>();
    private final Set<UUID> designers = new HashSet<>();

    public EPrefix() {
        designers.add(UUID.fromString("85d5eb05-33af-42a7-8586-1d11fb75480a"));
        designers.add(UUID.fromString("0485f3a6-6f90-4ef9-b7ea-fda13964bb7f"));

        friends.add(UUID.fromString("8c0acfca-7412-48fc-a3ef-460bd1cf312f"));
        friends.add(UUID.fromString("686fedbf-dce7-45f4-84b4-bc4b38a8945a"));
        friends.add(UUID.fromString("882d5509-3ed2-4a3e-90a3-fceab8dfe502"));
        friends.add(UUID.fromString("e3b240cf-136d-41f6-97fe-b82010bee96e"));

        bugHunters.add(UUID.fromString("87f99e50-ac88-49d7-8370-b1246106989e"));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled())
            return;
        boolean showInGray = true;

        UUID playerUUID = e.getPlayer().getUniqueId();

        if (developer.equals(playerUUID) || gameDesigner.equals(playerUUID) || slave.equals(playerUUID) || designers.contains(playerUUID) || friends.contains(playerUUID) || bugHunters.contains(playerUUID))
            showInGray = false;

        if (showInGray)
            e.setFormat(ChatColor.GRAY + e.getPlayer().getDisplayName() + ChatColor.BLACK + " » " + ChatColor.DARK_GRAY + e.getMessage());
        else
            e.setFormat(e.getPlayer().getDisplayName() + ChatColor.GOLD + " » " + ChatColor.WHITE + e.getMessage());
    }

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent e) {
        if (e.getPlayer().getUniqueId() == null)
            return;

        UUID playerUUID = e.getPlayer().getUniqueId();

        if (developer.equals(playerUUID))
            updatePrefix(e.getPlayer(), ChatColor.BLACK + "[" + ChatColor.GREEN + "Dev" + ChatColor.BLACK + "]");

        else if (gameDesigner.equals(playerUUID))
            updatePrefix(e.getPlayer(), ChatColor.BLACK + "[" + ChatColor.DARK_PURPLE + "Game Designer" + ChatColor.BLACK + "]");

        else if (slave.equals(playerUUID))
            updatePrefix(e.getPlayer(), ChatColor.BLACK + "[" + ChatColor.LIGHT_PURPLE + "Disciple" + ChatColor.BLACK + "]");

        else if (designers.contains(playerUUID))
            updatePrefix(e.getPlayer(), ChatColor.BLACK + "[" + ChatColor.RED + "Concepteur" + ChatColor.BLACK + "]");

        else if (friends.contains(playerUUID))
            updatePrefix(e.getPlayer(), ChatColor.BLACK + "[" + ChatColor.AQUA + "Ami" + ChatColor.BLACK + "]");

        else if (bugHunters.contains(playerUUID))
            updatePrefix(e.getPlayer(),  ChatColor.BLACK + "[" + ChatColor.GOLD + "Bugs hunter" + ChatColor.BLACK + "]");
    }

    private void updatePrefix(Player player, String prefix) {
        player.setDisplayName(prefix + ChatColor.RESET + " " + player.getName());
        player.setPlayerListName(prefix + ChatColor.RESET + " " + player.getName());
    }

}
