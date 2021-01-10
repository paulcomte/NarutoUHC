/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.infos.Roles;

import java.util.*;

public class MRules {

    public boolean showDeathMessage = true;
    public boolean showRoleOnDeath = true;
    public boolean adminRoles = true;
    public boolean naturalRegeneration = false;
    public boolean allowWhispers = true;
    public int maxPlayers = 26;
    public final List<Roles> activatedRoles = new ArrayList<>();
    public String gameTitle = "";
    public UUID gameHost = null;
    public Set<UUID> gameCoHost = new HashSet<>();
    public Set<UUID> bannedPlayers = new HashSet<>();
    public int rolesDispatching = 1800;
    public int playerDispatchingSize = 3000;

    public int startDuration = 10;

    public int invincibilityFinished = 30;
    public int preparationTime = 30*60;
    public int rolesAnnounce = 35*60;

    public int teleportingDuration = 10;
    public int narutoTeleportingDuration = 10;


    public int timeBeforeDeath = 5;

    public MRules() {
        activatedRoles.addAll(Arrays.asList(Roles.values()));
    }
}
