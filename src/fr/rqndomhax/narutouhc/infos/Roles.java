/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

public enum Roles {

    MADARA(RoleType.SASUKE),
    SASUKE(RoleType.SASUKE),

    DANZO(RoleType.DANZO),

    DEIDARA(RoleType.AKATSUKI),
    NAGATO(RoleType.AKATSUKI),
    Kakuzu(RoleType.AKATSUKI),
    KISAME(RoleType.AKATSUKI),
    OBITO(RoleType.AKATSUKI),
    KONAN(RoleType.AKATSUKI),
    HIDAN(RoleType.AKATSUKI),

    NARUTO(RoleType.SHINOBI),
    KAKASHI_HATAKE(RoleType.SHINOBI),
    SHIKAMARU(RoleType.SHINOBI),
    HINATA(RoleType.SHINOBI),
    MINATO(RoleType.SHINOBI),
    GAI(RoleType.SHINOBI),
    NEJI(RoleType.SHINOBI),
    SHISUI(RoleType.SHINOBI),
    SAKURA(RoleType.SHINOBI),
    ITACHI(RoleType.SHINOBI),
    KILLER_BEE(RoleType.SHINOBI),
    GAARA(RoleType.SHINOBI),
    FU(RoleType.SHINOBI),

    OROCHIMARU(RoleType.OROCHIMARU),
    KABUTO(RoleType.OROCHIMARU);

    private final RoleType roleType;

    Roles(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }
}
