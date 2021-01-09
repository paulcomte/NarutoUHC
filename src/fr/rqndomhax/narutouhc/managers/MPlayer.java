/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.utils.PlayerInventoryManager;
import org.bukkit.Location;

import java.util.UUID;

public class MPlayer {

    public final UUID uuid;
    public Roles role = null;
    public boolean hasRedeemed = false;
    public boolean isDead = false;
    public int kills = 0;
    public final PlayerInventoryManager deathInventory = new PlayerInventoryManager();
    public Location location;

    public MPlayer(UUID uuid) {
        this.uuid = uuid;
    }
}
