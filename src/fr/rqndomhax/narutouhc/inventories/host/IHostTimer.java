/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.managers.GameRules;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.Banners;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;
import java.util.function.Consumer;

public class IHostTimer {

    private final Setup setup;
    private final Player player;
    private final RInventory inventory;

    public IHostTimer(Setup setup, Player player, RInventory inventory) {
        this.inventory = inventory;
        this.setup = setup;
        this.player = player;

        updateInventory();
    }

    private void updateInventory() {
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);

        int[] bars = new int[]{3, 5, 48, 50};
        int[] row = new int[]{19, 20, 21, 23, 24, 25};

        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);

        inventory.setItem(4, new ItemBuilder(IInfos.MAIN_HOST_TIMERS.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        IInfos.placeInvBorders(inventory.getInventory());

        for (int i = 10 ; i < 17 ; inventory.setItem(i, IInfos.BARS), i++);
        for (int i = 37 ; i < 45 ; inventory.setItem(i, IInfos.BARS), i++);

        for (int i = 0 ; i < 2 ; i++) {
            int n = 0;
            for (Banners banner : Banners.values()) {
                StringBuilder name = new StringBuilder();
                if (n < 3)
                    name.append("- ");
                else
                    name.append("+ ");
                if (n == 0 || n == 5)
                    name.append("10 mins");
                if (n == 1 || n == 4)
                    name.append("5 mins");
                if (n == 2 || n == 3)
                    name.append("1 min");
                inventory.setItem(row[n]+(i*9), new ItemBuilder(Objects.requireNonNull(banner.getPattern()).clone()).setName(name.toString()).toItemStack(), updateTimer(i, n));
                n++;
            }
        }

        GameRules rules = setup.getGame().getGameRules();

        if (rules.rolesAnnounce > 60)
            inventory.setItem(22, new ItemBuilder(IInfos.HOST_TIMER_ROLES.clone()).setName(ChatColor.GREEN + "Annonce des rôles: " + ChatColor.DARK_AQUA + rules.rolesAnnounce / 60 + ChatColor.GREEN + " mins").toItemStack());
        else
            inventory.setItem(22, new ItemBuilder(IInfos.HOST_TIMER_ROLES.clone()).setName(ChatColor.GREEN + "Annonce des rôles: " + ChatColor.DARK_AQUA + rules.rolesAnnounce / 60 + ChatColor.GREEN + " min").toItemStack());

        if (rules.preparationDuration > 60)
            inventory.setItem(31, new ItemBuilder(IInfos.HOST_TIMER_TELEPORT.clone()).setName(ChatColor.GREEN + "Préparation: " + ChatColor.DARK_AQUA + rules.preparationDuration / 60 + ChatColor.GREEN + " mins").toItemStack());
        else
            inventory.setItem(31, new ItemBuilder(IInfos.HOST_TIMER_TELEPORT.clone()).setName(ChatColor.GREEN + "Préparation: " + ChatColor.DARK_AQUA + rules.preparationDuration / 60 + ChatColor.GREEN + " min").toItemStack());


        inventory.setItem(49, IInfos.RETURN_ITEM, e -> {
            player.closeInventory();
            player.openInventory(new IHost(setup, player).getInventory());
        });

        player.updateInventory();
    }

    private Consumer<InventoryClickEvent> updateTimer(int row, int n) {
        return e -> {

            GameRules rules = setup.getGame().getGameRules();

            if (row == 0) {
                if (n == 0)
                    if (rules.rolesAnnounce - 10*60 <= 60)
                        rules.rolesAnnounce = 60;
                    else
                        rules.rolesAnnounce -= 10*60;
                if (n == 1)
                    if (rules.rolesAnnounce - 5*60 <= 60)
                        rules.rolesAnnounce = 60;
                    else
                        rules.rolesAnnounce -= 5*60;
                if (n == 2)
                    if (rules.rolesAnnounce - 60 <= 60)
                        rules.rolesAnnounce = 60;
                    else
                        rules.rolesAnnounce -= 60;
                if (n == 3)
                    if (rules.rolesAnnounce + 60 >= 120*60)
                        rules.rolesAnnounce = 120*60;
                    else
                        rules.rolesAnnounce += 60;
                if (n == 4)
                    if (rules.rolesAnnounce + 5*60 >= 120*60)
                        rules.rolesAnnounce = 120*60;
                    else
                        rules.rolesAnnounce += 5*60;
                if (n == 5)
                    if (rules.rolesAnnounce + 10*60 >= 120*60)
                        rules.rolesAnnounce = 120*60;
                    else
                        rules.rolesAnnounce += 10*60;
            }
            else {
                if (n == 0)
                    if (rules.preparationDuration - 10 * 60 <= 60)
                        rules.preparationDuration = 60;
                    else
                        rules.preparationDuration -= 10 * 60;
                if (n == 1)
                    if (rules.preparationDuration - 5 * 60 <= 60)
                        rules.preparationDuration = 60;
                    else
                        rules.preparationDuration -= 5 * 60;
                if (n == 2)
                    if (rules.preparationDuration - 60 <= 60)
                        rules.preparationDuration = 60;
                    else
                        rules.preparationDuration -= 60;
                if (n == 3)
                    if (rules.preparationDuration + 60 >= 120 * 60)
                        rules.preparationDuration = 120 * 60;
                    else
                        rules.preparationDuration += 60;
                if (n == 4)
                    if (rules.preparationDuration + 5 * 60 >= 120 * 60)
                        rules.preparationDuration = 120 * 60;
                    else
                        rules.preparationDuration += 5 * 60;
                if (n == 5)
                    if (rules.preparationDuration + 10 * 60 >= 120 * 60)
                        rules.preparationDuration = 120 * 60;
                    else
                        rules.preparationDuration += 10 * 60;
            }

            updateInventory();
        };
    }

}
