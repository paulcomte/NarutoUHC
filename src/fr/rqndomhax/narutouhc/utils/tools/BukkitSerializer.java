/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.tools;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

public abstract class BukkitSerializer {

    public static String inventoryToString(ItemStack[] array) {
        try {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);

            data.writeInt(array.length);

            for (int i = 0; i < array.length; i++)
                data.writeObject(array[i]);

            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ItemStack[] stringToItemArray(String inventoryData) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(inventoryData));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);

            ItemStack[] items = new ItemStack[data.readInt()];

            for (int i = 0; i < items.length; i++)
                items[i] = (ItemStack) data.readObject();

            data.close();
            return items;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
