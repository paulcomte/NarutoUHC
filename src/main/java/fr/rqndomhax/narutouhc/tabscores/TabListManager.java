/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import com.mojang.authlib.GameProfile;
import fr.rqndomhax.narutouhc.game.Game;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;

public abstract class TabListManager {

    private static int cooldown;
    private static int siteCharIndex;
    private static Set<EntityPlayer> players;

    private static Game game = null;

    public static void registerTab(JavaPlugin plugin, Game game) {
        cooldown = 0;
        players = new HashSet<>();
        if (TabListManager.game == null)
            TabListManager.game = game;

        new BukkitRunnable() {

            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() == 0) return;

                PacketPlayOutPlayerListHeaderFooter headerFooter = sendHeaderFooter();
                if (headerFooter != null) {
                    for (Player player : Bukkit.getOnlinePlayers())
                        ((CraftPlayer) player).getHandle().playerConnection
                                .getPlayer().getHandle().playerConnection.sendPacket(headerFooter);
                }
                sendTabPlayers();
            }

        }.runTaskTimerAsynchronously(plugin, 0, 1);

    }

    private static PacketPlayOutPlayerListHeaderFooter sendHeaderFooter() {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        Object header = new ChatComponentText(
                ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "≫ " + ChatColor.BLACK + "" + ChatColor.BOLD + "Naruto" + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "≪ " +
                        "\n " +
                        "\n" + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "»" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                   " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "«");

        Object footer = new ChatComponentText(
                ChatColor.RESET + "" + ChatColor.DARK_GRAY + "»" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                   " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "«" +
                        "\n " +
                        "\n " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "● " + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Concept par " + ChatColor.WHITE + "" + ChatColor.BOLD + "Laynoks, Syknos et Losgateaux " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "●" +
                        "\n " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "● " + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Développé par " + ChatColor.WHITE + "" + ChatColor.BOLD + "RqndomHax " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "●" +
                        "\n" + colorAdAt() + "");

        try {

            Field headerF = packet.getClass().getDeclaredField("a");
            headerF.setAccessible(true);
            Field footerF = packet.getClass().getDeclaredField("b");
            footerF.setAccessible(true);

            headerF.set(packet, header);
            footerF.set(packet, footer);

            headerF.setAccessible(false);
            footerF.setAccessible(false);

            return packet;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void sendPlayers(UUID playerId, Player client, boolean isGamePlayer) {

        MinecraftServer server = MinecraftServer.getServer();
        String oldname = null;

        PlayerInteractManager interact = new PlayerInteractManager(
                server.getWorld());

        final CraftPlayer clientCP = ((CraftPlayer) client).getHandle().playerConnection
                .getPlayer();

        EntityPlayer newPlayer = null;

        for (EntityPlayer player : players)
            if (player.getBukkitEntity().getUniqueId().equals(playerId))
                newPlayer = player;

        if (newPlayer == null) {
            newPlayer = new EntityPlayer(server, (WorldServer) server.getWorld(),
                    MinecraftServer.getServer().aD().fillProfileProperties(new GameProfile(playerId, null), true),
                    interact);
            players.add(newPlayer);
        }

        if (isGamePlayer) {
            newPlayer.getBukkitEntity().setGameMode(GameMode.SURVIVAL);
            if (game.getGameRules().activatedScenarios.contains(Scenarios.NO_NAME_TAG)) {
                oldname = newPlayer.getName();
                changeName(newPlayer.getProfile(), RandomStringUtils.randomAlphanumeric(10));
            }
        }

        newPlayer.ping = 20;

        // Send packet remove
        clientCP.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                newPlayer));

        // Send Packet Add
        clientCP.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, newPlayer));

        if (game.getGameRules().activatedScenarios.contains(Scenarios.NO_NAME_TAG))
            changeName(newPlayer.getProfile(), oldname);
    }

    public static void sendTabPlayers() {
        HashMap<Player, Set<Player>> toClear = new HashMap<>();
        List<Player> spectators = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {

            Set<Player> players = getPlayers(player);
            toClear.put(player, players);

            if (game.getGamePlayer(player.getUniqueId()) == null)
                spectators.add(player);

            for (GamePlayer gamePlayer : game.getGamePlayers())
                if (!gamePlayer.uuid.equals(player.getUniqueId())) {
                    sendPlayers(gamePlayer.uuid, player, true);
                    removePlayer(players, gamePlayer.uuid);
                }
        }

        for (Player spectator : spectators) {
            Set<Player> players = toClear.get(spectator);
            for (Player spectator2 : spectators)
                if (!spectator.equals(spectator2)) {
                    sendPlayers(spectator.getUniqueId(), spectator2, false);
                    players.remove(spectator2);
                }
        }

        for (Player player : Bukkit.getOnlinePlayers())
            clearTab(player, toClear.get(player));
    }

    private static void clearTab(Player player, Set<Player> players) {
        final CraftPlayer clientCP = ((CraftPlayer) player).getHandle().playerConnection
                .getPlayer();
        for (Player target : players) {
            if (target.equals(player))
                continue;
            clientCP.getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                    ((CraftPlayer) target).getHandle()));
        }
    }

    private static Set<Player> getPlayers(Player player) {
        Set<Player> players = new HashSet<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(player))
                players.add(p);
        }
        return players;
    }

    private static void removePlayer(Set<Player> players, UUID playerID) {
        if (players == null)
            return;
        for (Player player : players)
            if (player.getUniqueId().equals(playerID)) {
                players.remove(player);
                break;
            }
    }

    private static void changeName(GameProfile profile, final String name) {
        if (name == null)
            return;
        try {
            Field field = profile.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(profile, name);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static String colorAdAt() {
        String ad = "github.com/RqndomHax";

        if (cooldown > 0) {
            cooldown--;
            return ChatColor.YELLOW + ad;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (siteCharIndex > 0) {
            formattedIp.append(ad, 0, siteCharIndex - 1);
            formattedIp.append(ChatColor.GOLD).append(ad.charAt(siteCharIndex - 1));
        } else
            formattedIp.append(ad, 0, siteCharIndex);

        formattedIp.append(ChatColor.RED).append(ad.charAt(siteCharIndex));

        if (siteCharIndex + 1 < ad.length()) {
            formattedIp.append(ChatColor.GOLD).append(ad.charAt(siteCharIndex + 1));

            if (siteCharIndex + 2 < ad.length())
                formattedIp.append(ChatColor.YELLOW).append(ad.substring(siteCharIndex + 2));

            siteCharIndex++;
        } else {
            siteCharIndex = 0;
            cooldown = 50;
        }

        return ChatColor.YELLOW + formattedIp.toString();
    }

}
