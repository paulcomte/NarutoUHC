/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import fr.rqndomhax.narutouhc.game.Game;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameRules;
import fr.rqndomhax.narutouhc.utils.tools.PlayerList;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public abstract class TabListManager {

    private static int cooldown;
    private static int siteCharIndex;

    public static HashMap<Player, PlayerList> playersTablist = new HashMap<>();
    private static Game game = null;

    public static void registerTab(JavaPlugin plugin, Game game) {
        if (TabListManager.game == null)
            TabListManager.game = game;
        new BukkitRunnable() {

            @Override
            public void run() {
                for (PlayerList playerList : playersTablist.values()) {
                    playerList.setHeaderFooter(

                            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "≫ " + ChatColor.BLACK + "" + ChatColor.BOLD + "Naruto" + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "≪ " +
                            "\n " +
                            "\n" + ChatColor.RESET + "" + ChatColor.DARK_GRAY +"»" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                   " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "«",

                            ChatColor.RESET + "" + ChatColor.DARK_GRAY +"»" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "                                   " + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "«" +
                                    "\n " +
                                    "\n " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "● " + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Concept par " + ChatColor.WHITE + "" + ChatColor.BOLD + "Laynoks, Syknos et Losgateaux " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "●" +
                                    "\n " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "● " + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Développé par " + ChatColor.WHITE + "" + ChatColor.BOLD + "RqndomHax " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.BOLD + "●" +
                                    "\n" + colorAdAt() + "");

                    for (int i = 0; i < game.getGamePlayers().size(); i++)
                        playerList.updateSlot(i, game.getGamePlayers().get(i).name, true);
                }

            }

        }.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    public static void initTab(Player player) {
        if (playersTablist.containsKey(player))
            return;

        PlayerList playerList = new PlayerList(player, PlayerList.SIZE_DEFAULT);

        playerList.initTable();
        playersTablist.put(player, playerList);
        for (int i = 0; i < TabListManager.game.getGamePlayers().size(); i++)
            playerList.updateSlot(i, TabListManager.game.getGamePlayers().get(i).name, true);
    }

    public static void unregisterTab(Player player) {
        if (!playersTablist.containsKey(player))
            return;
        playersTablist.get(player).clearCustomTabs();
        playersTablist.remove(player);
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
