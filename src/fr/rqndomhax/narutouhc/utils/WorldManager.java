/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils;

import java.io.File;

public abstract class WorldManager {

    public static void deletePlayerData(File root) {
        check(root);
    }

    private static void check(File path) {
        if (!path.isDirectory())
            return;

        File[] entries = path.listFiles();
        for (File entry : entries) {
            if (entry == null || !entry.exists() || !entry.isDirectory())
                continue;
            check(entry);
            if (entry.getName().equals("playerdata"))
                delete(entry);
        }
    }

    private static void delete(File path) {
        File[] entries = path.listFiles();

        for (File entry : entries) {
            if (entry != null && entry.exists() && !entry.isDirectory())
                entry.delete();
        }
    }

}
