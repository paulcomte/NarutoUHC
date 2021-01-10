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
    public boolean adminRoles = true;
    public boolean showRoleOnDeath = true;
    public int maxPlayers = 26;
    public final List<Roles> activatedRoles = new ArrayList<>();
    public String gameTitle = "";
    public UUID gameHost = null;
    public Set<UUID> gameCoHost = new HashSet<>();
    public int rolesDispatching = 1800;
    public int startDuration = 10;
    public int playerDispatchingSize = 3000;
    public int invincibilityDuration = 30;
    public int teleportingDuration = 10;
    public int timeBeforeDeath = 5;

    public MRules() {
        activatedRoles.addAll(Arrays.asList(Roles.values()));
    }
}
