/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.title;

import org.bukkit.Bukkit;

enum ServerPackage {

    MINECRAFT("net.minecraft.server." + getServerVersion()),
    CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion());

    private final String path;

    ServerPackage(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }

    public Class<?> getClass(String className) throws ClassNotFoundException {
        return Class.forName(this.toString() + "." + className);
    }

    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

}
