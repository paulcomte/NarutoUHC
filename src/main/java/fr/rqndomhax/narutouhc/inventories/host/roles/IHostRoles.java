/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.roles;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.infos.Team;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class IHostRoles {

    private final Setup setup;
    private final Player player;
    private IHostRolesPage page;
    private final RInventory inventory;

    public IHostRoles(Setup setup, Player player, IHostRolesPage page, RInventory inventory) {
        this.setup = setup;
        this.player = player;
        this.page = page;
        this.inventory = inventory;

        update(page);
    }

    private void update(IHostRolesPage page) {
        this.page = page;
        for (int i = 0 ; i < inventory.getInventory().getSize() ; inventory.setItem(i, null), i++);
        inventory.setItem(4, new ItemBuilder(IInfos.MAIN_HOST_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack());
        switch (page) {
            case AKATSUKI:
                addAkatsukiItems();
                break;
            case OROCHIMARU:
                addOrochimaruItems();
                break;
            case SOLOS:
                addSoloItems();
                break;
            case SHINOBI:
            default:
                addShinobiItems();
                break;
        }
        player.updateInventory();
    }

    private void addShinobiItems() {
        int[] black = new int[]{0, 2, 6, 8, 18, 20, 26, 28, 34, 36, 44, 46, 52};
        int[] orange = new int[]{1, 7, 9, 11, 13, 15, 17, 19, 25, 27, 35, 37, 43, 45, 47, 51, 53};
        int[] bars = new int[]{3, 5, 48, 50};
        List<Integer> roles = new ArrayList<>(Arrays.asList(21, 22, 23, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42));

        for (Integer i : black)
            inventory.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            inventory.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);
        inventory.setItem(10, new ItemBuilder(IInfos.HOST_SHINOBI_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.SHINOBI));
        inventory.setItem(12, IInfos.HOST_AKATSUKI_ROLES, updateOnClick(IHostRolesPage.AKATSUKI));
        inventory.setItem(14, IInfos.HOST_OROCHIMARU_ROLES, updateOnClick(IHostRolesPage.OROCHIMARU));
        inventory.setItem(16, IInfos.HOST_SOLO_ROLES, updateOnClick(IHostRolesPage.SOLOS));
        inventory.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getTeam().equals(Team.SHINOBI))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            inventory.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.SHINOBI));
            roles.remove(0);
        }
    }

    private void addAkatsukiItems() {
        int[] black = new int[]{0, 2, 6, 8, 18, 20, 24, 26, 28, 34, 36, 38, 42, 44, 46, 52};
        int[] orange = new int[]{1, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 35, 37, 39, 41, 43, 45, 47, 51, 53};
        int[] bars = new int[]{3, 5, 48, 50};
        List<Integer> roles = new ArrayList<>(Arrays.asList(22, 29, 30, 31, 32, 33, 40));

        for (Integer i : black)
            inventory.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            inventory.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);
        inventory.setItem(10, IInfos.HOST_SHINOBI_ROLES, updateOnClick(IHostRolesPage.SHINOBI));
        inventory.setItem(12, new ItemBuilder(IInfos.HOST_AKATSUKI_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.AKATSUKI));
        inventory.setItem(14, IInfos.HOST_OROCHIMARU_ROLES, updateOnClick(IHostRolesPage.OROCHIMARU));
        inventory.setItem(16, IInfos.HOST_SOLO_ROLES, updateOnClick(IHostRolesPage.SOLOS));
        inventory.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getTeam().equals(Team.AKATSUKI))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            inventory.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.AKATSUKI));
            roles.remove(0);
        }
    }

    private void addOrochimaruItems() {
        int[] black = new int[]{0, 2, 6, 8, 18, 20, 22, 24, 26, 28, 34, 36, 38, 40, 42, 44, 46, 52};
        int[] orange = new int[]{1, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 33, 35, 37, 39, 41, 43, 45, 47, 51, 53};
        int[] bars = new int[]{3, 5, 31, 48, 50};
        List<Integer> roles = new ArrayList<>(Arrays.asList(30, 32));

        for (Integer i : black)
            inventory.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            inventory.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);
        inventory.setItem(10, IInfos.HOST_SHINOBI_ROLES, updateOnClick(IHostRolesPage.SHINOBI));
        inventory.setItem(12, IInfos.HOST_AKATSUKI_ROLES, updateOnClick(IHostRolesPage.AKATSUKI));
        inventory.setItem(14, new ItemBuilder(IInfos.HOST_OROCHIMARU_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.OROCHIMARU));
        inventory.setItem(16, IInfos.HOST_SOLO_ROLES, updateOnClick(IHostRolesPage.SOLOS));
        inventory.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getTeam().equals(Team.OROCHIMARU))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            inventory.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.OROCHIMARU));
            roles.remove(0);
        }
    }

    private void addSoloItems() {
        int[] black = new int[]{0, 2, 6, 8, 18, 20, 22, 24, 26, 28, 34, 36, 38, 40, 42, 44, 46, 52};
        int[] orange = new int[]{1, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 35, 37, 39, 41, 43, 45, 47, 51, 53};
        int[] bars = new int[]{3, 5, 30, 32, 48, 50};
        List<Integer> roles = new ArrayList<>(Arrays.asList(29, 31, 33));

        for (Integer i : black)
            inventory.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            inventory.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            inventory.setItem(i, IInfos.BARS);
        inventory.setItem(10, IInfos.HOST_SHINOBI_ROLES, updateOnClick(IHostRolesPage.SHINOBI));
        inventory.setItem(12, IInfos.HOST_AKATSUKI_ROLES, updateOnClick(IHostRolesPage.AKATSUKI));
        inventory.setItem(14, IInfos.HOST_OROCHIMARU_ROLES, updateOnClick(IHostRolesPage.OROCHIMARU));
        inventory.setItem(16, new ItemBuilder(IInfos.HOST_SOLO_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.SOLOS));
        inventory.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getTeam().equals(Team.DANZO) && !role.getTeam().equals(Team.SASUKE) && !role.getTeam().equals(Team.MADARA))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            inventory.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.SOLOS));
            roles.remove(0);
        }
    }

    private Consumer<InventoryClickEvent> returnMain() {
        return e -> {
            player.closeInventory();
            player.openInventory(new IHost(setup, player).getInventory());
        };
    }

    private Consumer<InventoryClickEvent> updateRole(Roles role, IHostRolesPage page) {
        return e -> {
            if (setup.getGame().getGameRules().activatedRoles.contains(role))
                setup.getGame().getGameRules().activatedRoles.remove(role);
            else
                setup.getGame().getGameRules().activatedRoles.add(role);
            update(page);
        };
    }

    private Consumer<InventoryClickEvent> updateOnClick(IHostRolesPage page) {
        return e -> {
            update(page);
        };
    }

}
