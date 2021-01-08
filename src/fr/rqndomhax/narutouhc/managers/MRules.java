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
    public int maxPlayers = 26;
    public final List<Roles> activedRoles = new ArrayList<>();
    public String gameTitle = "";
    public UUID gameHost = null;
    public Set<UUID> gameCoHost = new HashSet<>();

    public MRules() {
        activedRoles.addAll(Arrays.asList(Roles.values()));
    }
}
