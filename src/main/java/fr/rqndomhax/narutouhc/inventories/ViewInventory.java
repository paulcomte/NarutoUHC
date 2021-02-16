/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.utils.Chrono;
import fr.rqndomhax.narutouhc.utils.TranslateEffect;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class ViewInventory extends RInventory {

    public ViewInventory(Setup setup, Player target, GamePlayer gamePlayer) {
        super(null, target.getName(), 54);

        for(int i = 0;i<36;i++){
            ItemStack item = target.getInventory().getContents()[i];
            if(item != null && item.getType() != Material.AIR){
                setItem(i, item);
            }
        }

        this.setItem(36, target.getInventory().getHelmet());
        this.setItem(37, target.getInventory().getChestplate());
        this.setItem(38, target.getInventory().getLeggings());
        this.setItem(39, target.getInventory().getBoots());

        this.setItem(45, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3).setSkullOwner(target.getName()).setName(ChatColor.GREEN + "Pseudo: " + ChatColor.YELLOW + "" + target.getName()).setLore(ChatColor.GOLD + "Mort: " + (gamePlayer.isDead ? ChatColor.GREEN + "Oui" : ChatColor.RED + "Non") + "\n" + ChatColor.RED + "Rôle: " + (gamePlayer.role == null ? "Aucun" : gamePlayer.role.getRole().getRoleName())).toItemStack());
        this.setItem(46, new ItemBuilder(Material.APPLE, (int) target.getHealth()).setName(ChatColor.RED + "Vie: " + (int)target.getHealth() + " / " + (int)target.getMaxHealth()).toItemStack());
        this.setItem(47, new ItemBuilder(Material.COOKED_BEEF, target.getFoodLevel()).setName(ChatColor.GOLD + "Faim: " + target.getFoodLevel()).toItemStack());
        ItemBuilder itemBuilder = new ItemBuilder(Material.POTION, 1, (byte) 8265).setName(ChatColor.YELLOW + "Effets").addItemFlag(ItemFlag.HIDE_POTION_EFFECTS);

        if(target.getActivePotionEffects().isEmpty())
            itemBuilder.setLore(ChatColor.RED + "Aucun effet");
        else
            for(PotionEffect potionEffect : target.getActivePotionEffects())
                itemBuilder.setLore(ChatColor.WHITE + "- " + ChatColor.AQUA + "" + TranslateEffect.translate(potionEffect.getType()) + " " + (potionEffect.getAmplifier()+1) + " " + ChatColor.WHITE + ": " + Chrono.timeToDigitalString((potionEffect.getDuration()/20)));
        this.setItem(48, itemBuilder.toItemStack());
        setItem(49, new ItemBuilder(Material.DIAMOND, gamePlayer.diamonds).setName(ChatColor.AQUA + "Diamants minés: " + ChatColor.WHITE + "" + gamePlayer.diamonds).toItemStack());
        setItem(50, new ItemBuilder(Material.GOLD_INGOT, gamePlayer.golds).setName(ChatColor.GOLD + "Ors minés: " + ChatColor.WHITE + "" + gamePlayer.golds).toItemStack());
    }


}
