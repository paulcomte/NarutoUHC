/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

public enum Team {
    SHINOBI("Shinobi"),
    AKATSUKI("Akatsuki"),
    OROCHIMARU("Orochimaru"),
    MADARA("Solo"),
    SASUKE("Solo"),
    DANZO("Solo");

    private final String teamName;

    Team(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
