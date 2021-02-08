/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new Setup(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.kickPlayer("Server reloading");
        super.onDisable();
    }
}
