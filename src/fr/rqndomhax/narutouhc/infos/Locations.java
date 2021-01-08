/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.infos;

public enum Locations {
    KONOHA(500, -1400),
    SUNA(-1000, -200),
    KIRI(2100, -700),
    KUMO(2000, -2400),
    IWA(-200, -1500),
    AME(-200, -1200),
    AKATSUKI(-50, 300);

    private int x;
    private int y;

    Locations(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
