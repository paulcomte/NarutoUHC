/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.rules;

import org.bukkit.ChatColor;

public enum DayCycle {

    NORMAL(ChatColor.GREEN + "Cycle classique"),
    NIGHT(ChatColor.DARK_BLUE + "Nuit éternelle"),
    DAY(ChatColor.YELLOW + "Jour éternelle");

    private final String description;

    DayCycle(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
