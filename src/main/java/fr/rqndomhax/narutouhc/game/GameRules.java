/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.game;

import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.managers.rules.DayCycle;
import fr.rqndomhax.narutouhc.managers.rules.Drops;
import fr.rqndomhax.narutouhc.managers.rules.Scenarios;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameRules {

    public GameBorder gameBorder = new GameBorder();

    public UUID startInventoryInEdit = null;
    public UUID deathInventoryInEdit = null;

    public boolean showDeathMessages = true;
    public boolean showRoleOnDeath = true;

    public boolean allowSpectators = true;
    public boolean spectatorsAfterBorder = true;
    public boolean hasWhitelist = false;

    public boolean adminRoles = true;
    public boolean naturalRegeneration = false;

    public boolean allowWhispers = true;
    public boolean allowChat = false;

    public int maxGroupSize = 5;

    public ItemStack[] startInventory = new ItemStack[40];
    public ItemStack[] deathInventory = new ItemStack[40];

    public Set<Roles> activatedRoles = new HashSet<>();
    public Set<Scenarios> activatedScenarios = new HashSet<>();
    public Set<Drops> drops = new HashSet<>(Arrays.asList(Drops.values()));

    public DayCycle dayCycle = DayCycle.NORMAL;

    public String gameTitle = ChatColor.BLACK + "" + ChatColor.BOLD + "Naruto " + ChatColor.GOLD + "" + ChatColor.BOLD + "UHC";
    public int maxCoHost = 4;

    public int startDuration = 10;

    public int invincibilityDuration = 30;
    public int preparationDuration = 40*60;
    public int rolesAnnounce = 35*60;

    public int teleportingDuration = 15;
    public int narutoTeleportingDuration = 15;

    public int timeBeforeDeath = 10;

    public GameRules() {
        activatedRoles.addAll(Arrays.asList(Roles.values()));
        deathInventory[0] = new ItemStack(Material.GOLDEN_APPLE);
    }
}
