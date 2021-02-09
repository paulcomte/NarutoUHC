/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.tools;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public abstract class ProgressBar {

    public static void displayProgressBar(String prefix, String end, int current, int full) {
        for (Player player : Bukkit.getOnlinePlayers())
            displayProgressBar(prefix, end, current, full, player);
    }

    public static void displayProgressBar(String prefix, String end, int current, int full, Player player) {
        if (player == null)
            return;
        StringBuilder text = new StringBuilder();

        text.append(ChatColor.GRAY);

        text.append(prefix);

        text.append(": ");

        int timeRemaining = full - current;

        if (timeRemaining < 0 || timeRemaining > full)
            return;

        int fullDisplay = 30;
        int timeIncompleted = (int) (fullDisplay * (timeRemaining / (double) full));
        int timeCompleted = fullDisplay - timeIncompleted;

        text.append(ChatColor.GREEN);
        for (int i = 0; i < timeCompleted; i++)
            text.append('|');

        text.append(ChatColor.RED);
        for (int i = 0; i < timeIncompleted; i++)
            text.append('|');

        text.append(ChatColor.GRAY);

        text.append(' ');

        text.append(end);

        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(text.toString()), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
