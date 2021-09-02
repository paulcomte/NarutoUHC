/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tabscores;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.*;
import com.mojang.authlib.properties.Property;
import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.Game;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.game.GameState;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
    public static Property randomSkin = null;

    private static Game game = null;

    public static final HashMap<Player, Set<UUID>> tabPlayers = new HashMap<>();

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
                if (headerFooter != null)
                    for (Player player : Bukkit.getOnlinePlayers())
                        ((CraftPlayer) player).getHandle().playerConnection
                                .getPlayer().getHandle().playerConnection.sendPacket(headerFooter);
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

    public static void activatePlayerList(Setup setup, ProtocolManager protocolManager) {
        if (setup.getGame().getGameRules().activatedScenarios.contains(Scenarios.RANDOM_SKIN))
            PlayerManager.getRandomSkin(new HashSet<>(Bukkit.getOnlinePlayers()), setup);
        protocolManager.addPacketListener(
                new PacketAdapter(setup.getMain(), ListenerPriority.NORMAL,
                        PacketType.Play.Server.PLAYER_INFO) {
                    @Override
                    public void onPacketSending(PacketEvent event) {

                        if (event.isCancelled())
                            return;

                        if (!tabPlayers.containsKey(event.getPlayer()))
                            tabPlayers.put(event.getPlayer(), new HashSet<>());

                        Set<UUID> players = tabPlayers.get(event.getPlayer());

                        EnumWrappers.PlayerInfoAction action = event.getPacket().getPlayerInfoAction().read(0);
                        List<PlayerInfoData> datas = event.getPacket().getPlayerInfoDataLists().read(0);
                        List<PlayerInfoData> newDatas = new ArrayList<>();
                        GamePlayer gameplayer = setup.getGame().getGamePlayer(event.getPlayer().getUniqueId());

                        for (PlayerInfoData data : datas) {
                            // Skip player's packet
                            if (data.getProfile().getUUID().equals(event.getPlayer().getUniqueId())) {
                                if (gameplayer != null)
                                    gameplayer.wrappedGameProfile = data.getProfile();
                                newDatas.add(data);
                                continue;
                            }
                            event.setCancelled(checkPlayer(setup, newDatas, gameplayer, players, data, action == EnumWrappers.PlayerInfoAction.ADD_PLAYER));
                        }

                        tabPlayers.put(event.getPlayer(), players);

                        for (GamePlayer gamePlayer : game.getGamePlayers()) {
                            // Check if gamePlayer is not player receiving packet
                            if (gameplayer.uuid.equals(event.getPlayer().getUniqueId()))
                                continue;
                            // Check if gamePlayer is contained in players
                            if (players.contains(gamePlayer.uuid))
                                continue;
                            // Check if newData already contains the gamePlayer
                            if (newDatas.stream().anyMatch(a -> a.getProfile() != null && a.getProfile().getUUID().equals(gameplayer.uuid)))
                                continue;
                            createDataFromGamePlayer(newDatas, gamePlayer);
                        }

                        event.getPacket().getPlayerInfoDataLists().write(0, newDatas);
                    }
                });
    }

    private static void createData(List<PlayerInfoData> newData, PlayerInfoData data) {
        WrappedGameProfile newProfile = new WrappedGameProfile(data.getProfile().getUUID(), data.getProfile().getName());
        newProfile.getProperties().putAll(data.getProfile().getProperties());
        createDataFromProfile(newProfile, newData);
    }

    private static void createDataFromProfile(WrappedGameProfile profile, List<PlayerInfoData> newData) {
        if (profile == null)
            return;
        if (game.getGameRules().activatedScenarios.contains(Scenarios.RANDOM_SKIN)) {
            if (profile.getProperties().containsKey("textures"))
                profile.getProperties().remove("textures", profile.getProperties().get("textures").iterator().next());
            profile.getProperties().put("textures", new WrappedSignedProperty(randomSkin.getName(), randomSkin.getValue(), randomSkin.getSignature()));
        }
        newData.add(new PlayerInfoData(profile, 20, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(profile.getName())));
    }

    private static void createDataFromGamePlayer(List<PlayerInfoData> newData, GamePlayer gamePlayer) {
        Player player = Bukkit.getPlayer(gamePlayer.uuid);

        WrappedGameProfile profile = gamePlayer.wrappedGameProfile;
        if (player != null)
            profile = WrappedGameProfile.fromPlayer(player);
        createDataFromProfile(profile, newData);
    }

    private static boolean checkPlayer(Setup setup, List<PlayerInfoData> newData, GamePlayer gamePlayer, Set<UUID> players, PlayerInfoData data, boolean doesAdd) {

        if (setup.getGame().getGamePlayer(data.getProfile().getUUID()) == null) {
            if (gamePlayer == null) {
                newData.add(data);
                return false;
            }
            return true;
        }

        if (players.contains(data.getProfile().getUUID()) || !doesAdd)
            return true;

        createData(newData, data);
        players.add(data.getProfile().getUUID());
        return false;
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

    public static void reloadPlayer(Player target) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (target.equals(player))
                continue;
            player.hidePlayer(target);
            player.showPlayer(target);
        }
    }

}
