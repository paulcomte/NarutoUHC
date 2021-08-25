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
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.UUID;

public abstract class TabListManager {

    private static int cooldown;
    private static int siteCharIndex;

    private static Game game = null;

    public static void registerTab(JavaPlugin plugin, Game game) {
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
            }

        }.runTaskTimerAsynchronously(plugin, 0, 1);

    }

    public static void sendPlayers(String playerName, UUID playerId, Player client) {

        MinecraftServer server = MinecraftServer.getServer();
        WorldServer worldserver = (WorldServer) server.getWorld();
        GameProfile profile = new GameProfile(playerId, playerName);

        PlayerInteractManager interact = new PlayerInteractManager(
                server.getWorld());

        final EntityPlayer lui = new EntityPlayer(server, worldserver, profile,
                interact);

        // Remove

        Player target = Bukkit.getPlayer(playerId);
        PacketPlayOutPlayerInfo removePacket = null;

        if (target == null || !target.isOnline())
            removePacket = new PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                    lui);
        else
            removePacket = new PacketPlayOutPlayerInfo(
                    PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                    ((CraftPlayer) target).getHandle());

        // Add
        final PacketPlayOutPlayerInfo addPacket = new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, lui);

        final CraftPlayer clientCP = ((CraftPlayer) client).getHandle().playerConnection
                    .getPlayer();

        // Send Packet Remove
        clientCP.getHandle().playerConnection.sendPacket(removePacket);

        // Send Packet Add
        clientCP.getHandle().playerConnection.sendPacket(addPacket);
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
        } else {
            formattedIp.append(ad, 0, siteCharIndex);
        }

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
