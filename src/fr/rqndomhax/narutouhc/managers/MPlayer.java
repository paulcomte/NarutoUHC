/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.utils.PlayerInventoryManager;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MPlayer {

    public final UUID uuid;
    public RoleInfo role = null;
    public boolean isDead = false;
    public List<UUID> kills = new ArrayList<>();
    public final PlayerInventoryManager deathInventory;
    public Location location;
    public Location deathLocation;

    public MPlayer(UUID uuid) {
        this.uuid = uuid;
        deathInventory = new PlayerInventoryManager();
    }
}
