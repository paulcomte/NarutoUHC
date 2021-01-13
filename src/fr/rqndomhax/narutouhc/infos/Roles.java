/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

import fr.rqndomhax.narutouhc.managers.role.RoleInfo;
import fr.rqndomhax.narutouhc.managers.role.shinobi.*;

public enum Roles {

    MADARA(RoleType.SASUKE, Naruto.class),
    SASUKE(RoleType.SASUKE, Naruto.class),

    DANZO(RoleType.DANZO, Naruto.class),

    DEIDARA(RoleType.AKATSUKI, Naruto.class),
    NAGATO(RoleType.AKATSUKI, Naruto.class),
    Kakuzu(RoleType.AKATSUKI, Naruto.class),
    KISAME(RoleType.AKATSUKI, Naruto.class),
    OBITO(RoleType.AKATSUKI, Naruto.class),
    KONAN(RoleType.AKATSUKI, Naruto.class),
    HIDAN(RoleType.AKATSUKI, Naruto.class),

    NARUTO(RoleType.SHINOBI, Naruto.class),
    KAKASHI_HATAKE(RoleType.SHINOBI, KakashiHatake.class),
    SHIKAMARU(RoleType.SHINOBI, Shikamaru.class),
    HINATA(RoleType.SHINOBI, Hinata.class),
    MINATO(RoleType.SHINOBI, Minato.class),
    GAI(RoleType.SHINOBI, Gai.class),
    NEJI(RoleType.SHINOBI, Neji.class),
    SHISUI(RoleType.SHINOBI, Shishui.class),
    SAKURA(RoleType.SHINOBI, Sakura.class),
    ITACHI(RoleType.SHINOBI, Itachi.class),
    KILLER_BEE(RoleType.SHINOBI, KillerBee.class),
    GAARA(RoleType.SHINOBI, Gaara.class),
    FU(RoleType.SHINOBI, Fu.class),

    OROCHIMARU(RoleType.OROCHIMARU, Naruto.class),
    KABUTO(RoleType.OROCHIMARU, Naruto.class);

    private final RoleType roleType;
    private final Class<? extends RoleInfo> roleInfo;

    Roles(RoleType roleType, Class<? extends RoleInfo> roleInfo) {
        this.roleType = roleType;
        this.roleInfo = roleInfo;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public Class<? extends RoleInfo> getRoleInfo() {
        return roleInfo;
    }
}
