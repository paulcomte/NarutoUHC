/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.utils.builders;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PotionBuilder {

    private Potion potion;

    public PotionBuilder(PotionType potionType) {
        this.potion = new Potion(potionType);
    }

    public PotionBuilder setSplash(boolean b) {
        potion.setSplash(b);
        return this;
    }

    public ItemStack toItemStack() {
        return toItemStack(1);
    }

    public ItemStack toItemStack(int amount) {
        return potion.toItemStack(amount);
    }
}
