/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.common;

import fr.rqndomhax.narutouhc.utils.ActionBar;
import fr.rqndomhax.narutouhc.utils.msg.ProgressBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import javax.sound.sampled.Line;
import java.util.logging.Level;

public class WorldGeneration {

    private World world;
    private int size;
    private BukkitTask task;
    private int nChunk;
    private int last;
    private final JavaPlugin plugin;
    private long startTime;

    public WorldGeneration(World world, JavaPlugin plugin) {
        System.out.println("world size = " + world.getWorldBorder().getSize());
        this.size = 1000 + 100;
        this.plugin = plugin;
        this.world = world;
        world.setGameRuleValue("randomTickSpeed", "0");
        this.load();
    }

    private void load() {
        Bukkit.getLogger().log(Level.INFO, "Starting pregeneration");
        new Thread(() ->
        {
            this.startTime = System.currentTimeMillis();
            this.task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                private int todo = size * 2 * (size * 2) / 256;
                private int x = -size;
                private int z = -size;

                @Override
                public void run() {
                    for (int i = 0; i < 50; ++i) {
                        Chunk chunk = world.getChunkAt(world.getBlockAt(this.x, 64, this.z));
                        chunk.load(true);
                        chunk.load(false);
                        int percentage = nChunk * 100 / this.todo;
                        if (percentage > last) {
                            last = percentage;
                            sendMessage(percentage);
                            Bukkit.getLogger().log(Level.INFO, "[" + world.getName() + "] pre-generated at " + percentage + "%");
                        }
                        this.z += 16;
                        if (this.z >= size) {
                            this.z = -size;
                            this.x += 16;
                        }
                        if (this.x >= size) {
                            task.cancel();
                            int calculedTime = Math.round(((System.currentTimeMillis() - startTime) / 1000L));
                            Bukkit.getLogger().log(Level.INFO, "Finished pre-generation after " + calculedTime + "s");
                            return;
                        }
                        nChunk++;
                    }
                }
            }, 1L, 1L);
        }).start();
    }

    private void sendMessage(int percentage) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ActionBar.send(player, ChatColor.YELLOW + "Prégénération: " + ChatColor.GREEN + percentage + "%"
                    + " §8[§r" + ProgressBar.getProgressBar(percentage, 100, 40, "|", ChatColor.YELLOW, ChatColor.GRAY) + "§8]");
        }
    }
}