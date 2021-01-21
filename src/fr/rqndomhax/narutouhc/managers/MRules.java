/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MRules {

    public UUID startInventoryInEdit = null;
    public UUID deathInventoryInEdit = null;
    public boolean showDeathMessage = true;
    public boolean showRoleOnDeath = true;
    public boolean adminRoles = true;
    public boolean naturalRegeneration = false;
    public boolean allowWhispers = true;
    public boolean allowChat = false;
    public int maxPlayers = 26;
    public int groupSize = 6;
    public ItemStack[] startInventory = new ItemStack[40];
    public ItemStack[] deathInventory = new ItemStack[40];
    public final List<Roles> activatedRoles = new ArrayList<>();
    public final List<Scenarios> activatedScenarios = new ArrayList<>();
    public int absorption = 4;
    public int maxAbsorption = 10;
    public DayCycle dayCycle = DayCycle.NORMAL;
    public String gameTitle = ChatColor.BLACK + "" + ChatColor.BOLD + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC";
    public UUID gameHost = null;
    public Set<UUID> gameCoHost = new HashSet<>();
    public Set<UUID> bannedPlayers = new HashSet<>();
    public int rolesDispatching = 1800;
    public int playerDispatchingSize = 3000;

    public int startDuration = 10;

    public int invincibilityFinished = 30;
    public int preparationTime = 40*60;
    public int rolesAnnounce = 35*60;

    public int teleportingDuration = 15;
    public int narutoTeleportingDuration = 15;

    public int timeBeforeDeath = 10;

    public MRules() {
        activatedRoles.addAll(Arrays.asList(Roles.values()));
    }
}
