/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game;

import fr.rqndomhax.narutouhc.role.RoleInfo;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayer {

    public final UUID uuid;
    public String name;
    public RoleInfo role = null;
    public boolean isInDead = false;
    public boolean isDead = false;
    public List<UUID> kills = new ArrayList<>();
    public int diamonds = 0;
    public int golds = 0;
    public ItemStack[] inventory = new ItemStack[40];
    public Location location;
    public Location deathLocation;

    public GamePlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
