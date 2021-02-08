/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.enchant;

import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;
import java.util.function.Consumer;

public class IEnchant extends RInventory {

    private final Player player;
    public IActions currentAction = null;
    public Enchantment enchantmentEdit = null;
    private Enchants.EnchantsType category = Enchants.EnchantsType.COMBAT;

    public IEnchant(Player player) {
        super(null, IInfos.ENCHANT_HOST, 9*6);
        this.player = player;
        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < this.getInventory().getSize() ; this.setItem(i, null), i++);

        IInfos.placeInvBorders(this.getInventory());

        int[] bars = new int[]{3, 5, 48, 50};
        int[] oranges = new int[]{12, 14, 47, 51};

        for (Integer i : bars)
            this.setItem(i, IInfos.BARS);

        for (Integer i : oranges)
            this.setItem(i, IInfos.ORANGE_GLASS_BORDER);

        this.setItem(4, IInfos.ENCHANT_ITEM);

        this.setItem(11, IInfos.ENCHANT_RENAME);

        this.setItem(13, player.getItemInHand());

        if (player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().spigot().isUnbreakable())
            this.setItem(15, IInfos.ENCHANT_UNBREAKABLE_TRUE);
        else
            this.setItem(15, IInfos.ENCHANT_UNBREAKABLE_FALSE);

        setCategories();

        setItems();

        this.setItem(49, IInfos.RETURN_ITEM, e -> player.closeInventory());
    }

    private Consumer<InventoryClickEvent> updateUnbreakable() {
        return e -> {
            if (player.getItemInHand() == null) {
                player.closeInventory();
                return;
            }

            new ItemBuilder(player.getItemInHand()).toggleUnbreakable();
            updateInventory();
        };
    }

    private void setItems() {
        for (Enchants enchant : Enchants.values()) {

            if (!enchant.getType().equals(category))
                continue;

            int enchantLevel = getLevel(enchant);

            if (enchantLevel == 0)
                this.setItem(enchant.getSlot(), new ItemBuilder(Material.ENCHANTED_BOOK).setName(enchant.getName()).toItemStack(), updateItem(enchant));
            else
                this.setItem(enchant.getSlot(), new ItemBuilder(Material.ENCHANTED_BOOK).setName(enchant.getName()).addStoredEnchant(enchant.getEnchantment(), getLevel(enchant)).toItemStack(), updateItem(enchant));

        }
    }

    private int getLevel(Enchants enchant) {
        Map<Enchantment, Integer> enchants  = player.getItemInHand().getEnchantments();

        if (!enchants.containsKey(enchant.getEnchantment()))
            return 0;
        return enchants.get(enchant.getEnchantment());
    }

    private Consumer<InventoryClickEvent> updateItem(Enchants enchant) {
        return e -> {

            if (player.getItemInHand() == null) {
                player.closeInventory();
                return;
            }

            if (e.getClick().equals(ClickType.RIGHT)) {
                if (!player.getItemInHand().hasItemMeta())
                    return;

                int currentLevel = getLevel(enchant);

                if (currentLevel == 0)
                    return;

                updateEnchant(enchant.getEnchantment(), -1);
                updateInventory();
            }

            if (e.getClick().equals(ClickType.LEFT)) {
                Map<Enchantment, Integer> enchants  = player.getItemInHand().getEnchantments();

                int currentLevel = getLevel(enchant);

                if (currentLevel == 255)
                    return;

                updateEnchant(enchant.getEnchantment(), 1);
                updateInventory();
            }

            if (e.getClick().equals(ClickType.DROP)) {
                Map<Enchantment, Integer> enchants  = player.getItemInHand().getEnchantments();

                if (enchants.get(enchant.getEnchantment()) == 0)
                    return;

                player.getItemInHand().removeEnchantment(enchant.getEnchantment());
                updateInventory();
            }
        };
    }

    private void updateEnchant(Enchantment enchantment, int level) {

        if (player.getItemInHand().getEnchantmentLevel(enchantment) == 1 && level == -1)
            new ItemBuilder(player.getItemInHand()).removeEnchantment(enchantment);
        else
            new ItemBuilder(player.getItemInHand()).addUnsafeEnchantment(enchantment, player.getItemInHand().getEnchantmentLevel(enchantment) + level);
    }

    private void setCategories() {
        for (Enchants.EnchantsType category : Enchants.EnchantsType.values()) {

            if (category.equals(this.category))
                this.setItem(category.getSlot(), new ItemBuilder(category.getItem().clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateCategory(category));
            else
                this.setItem(category.getSlot(), category.getItem(), updateCategory(category));
        }
    }

    private Consumer<InventoryClickEvent> updateCategory(Enchants.EnchantsType category) {
        return e -> {

            if (category.equals(this.category))
                return;

            this.category = category;
            updateInventory();
        };
    }

}
