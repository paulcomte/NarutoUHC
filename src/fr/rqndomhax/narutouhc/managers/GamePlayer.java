/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GamePlayer {

    public final UUID uuid;
    public RoleInfo role = null;
    public boolean isDead = false;
    public List<UUID> kills = new ArrayList<>();
    public ItemStack[] inventory = new ItemStack[40];
    public Location location;
    public Location deathLocation;

    public GamePlayer(UUID uuid) {
        this.uuid = uuid;
    }
}
