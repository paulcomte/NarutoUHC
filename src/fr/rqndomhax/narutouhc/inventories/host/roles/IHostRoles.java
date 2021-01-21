/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.inventories.host.roles;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.RoleType;
import fr.rqndomhax.narutouhc.infos.Roles;
import fr.rqndomhax.narutouhc.inventories.IInfos;
import fr.rqndomhax.narutouhc.inventories.host.IHost;
import fr.rqndomhax.narutouhc.utils.ItemBuilder;
import fr.rqndomhax.narutouhc.utils.inventory.RInventory;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class IHostRoles extends RInventory {

    private final Setup setup;
    private final Player player;
    private IHostRolesPage page;

    public IHostRoles(Setup setup, Player player, IHostRolesPage page) {
        super(null, IInfos.MAIN_HOST_ROLES_NAME, 9*6);
        this.setup = setup;
        this.player = player;
        this.page = page;

        update(page);
    }

    private void update(IHostRolesPage page) {
        this.page = page;
        for (int i = 0 ; i < this.getInventory().getSize() ; this.setItem(i, null), i++);
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
            this.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            this.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            this.setItem(i, IInfos.BARS);
        this.setItem(10, new ItemBuilder(IInfos.HOST_SHINOBI_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.SHINOBI));
        this.setItem(12, IInfos.HOST_AKATSUKI_ROLES, updateOnClick(IHostRolesPage.AKATSUKI));
        this.setItem(14, IInfos.HOST_OROCHIMARU_ROLES, updateOnClick(IHostRolesPage.OROCHIMARU));
        this.setItem(16, IInfos.HOST_SOLO_ROLES, updateOnClick(IHostRolesPage.SOLOS));
        this.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getRoleType().equals(RoleType.SHINOBI))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameInfo().getMRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            this.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.SHINOBI));
            roles.remove(0);
        }
    }

    private void addAkatsukiItems() {
        int[] black = new int[]{0, 2, 6, 8, 18, 20, 24, 26, 28, 34, 36, 38, 42, 44, 46, 52};
        int[] orange = new int[]{1, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 35, 37, 39, 41, 43, 45, 47, 51, 53};
        int[] bars = new int[]{3, 5, 48, 50};
        List<Integer> roles = new ArrayList<>(Arrays.asList(22, 29, 30, 31, 32, 33, 40));

        for (Integer i : black)
            this.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            this.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            this.setItem(i, IInfos.BARS);
        this.setItem(10, IInfos.HOST_SHINOBI_ROLES, updateOnClick(IHostRolesPage.SHINOBI));
        this.setItem(12, new ItemBuilder(IInfos.HOST_AKATSUKI_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.AKATSUKI));
        this.setItem(14, IInfos.HOST_OROCHIMARU_ROLES, updateOnClick(IHostRolesPage.OROCHIMARU));
        this.setItem(16, IInfos.HOST_SOLO_ROLES, updateOnClick(IHostRolesPage.SOLOS));
        this.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getRoleType().equals(RoleType.AKATSUKI))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameInfo().getMRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            this.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.AKATSUKI));
            roles.remove(0);
        }
    }

    private void addOrochimaruItems() {
        int[] black = new int[]{0, 2, 6, 8, 18, 20, 22, 24, 26, 28, 34, 36, 38, 42, 44, 46, 52};
        int[] orange = new int[]{1, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 35, 37, 39, 41, 43, 45, 47, 51, 53};
        int[] bars = new int[]{3, 5, 31, 48, 50};
        List<Integer> roles = new ArrayList<>(Arrays.asList(30, 32));

        for (Integer i : black)
            this.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            this.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            this.setItem(i, IInfos.BARS);
        this.setItem(10, IInfos.HOST_SHINOBI_ROLES, updateOnClick(IHostRolesPage.SHINOBI));
        this.setItem(12, IInfos.HOST_AKATSUKI_ROLES, updateOnClick(IHostRolesPage.AKATSUKI));
        this.setItem(14, new ItemBuilder(IInfos.HOST_OROCHIMARU_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.OROCHIMARU));
        this.setItem(16, IInfos.HOST_SOLO_ROLES, updateOnClick(IHostRolesPage.SOLOS));
        this.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getRoleType().equals(RoleType.OROCHIMARU))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameInfo().getMRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            this.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.OROCHIMARU));
            roles.remove(0);
        }
    }

    private void addSoloItems() {
        int[] black = new int[]{0, 2, 6, 8, 18, 20, 22, 24, 26, 28, 34, 36, 38, 42, 44, 46, 52};
        int[] orange = new int[]{1, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 35, 37, 39, 41, 43, 45, 47, 51, 53};
        int[] bars = new int[]{3, 5, 30, 32, 48, 50};
        List<Integer> roles = new ArrayList<>(Arrays.asList(29, 31, 33));

        for (Integer i : black)
            this.setItem(i, IInfos.BLACK_GLASS_BORDER);
        for (Integer i : orange)
            this.setItem(i, IInfos.ORANGE_GLASS_BORDER);
        for (Integer i : bars)
            this.setItem(i, IInfos.BARS);
        this.setItem(10, IInfos.HOST_SHINOBI_ROLES, updateOnClick(IHostRolesPage.SHINOBI));
        this.setItem(12, IInfos.HOST_AKATSUKI_ROLES, updateOnClick(IHostRolesPage.AKATSUKI));
        this.setItem(14, IInfos.HOST_OROCHIMARU_ROLES, updateOnClick(IHostRolesPage.OROCHIMARU));
        this.setItem(16, new ItemBuilder(IInfos.HOST_SOLO_ROLES.clone()).addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants().toItemStack(), updateOnClick(IHostRolesPage.SOLOS));
        this.setItem(49, IInfos.RETURN_ITEM, returnMain());

        for (Roles role : Roles.values()) {
            if (!role.getRoleType().equals(RoleType.DANZO) && !role.getRoleType().equals(RoleType.SASUKE))
                continue;
            if (roles.size() == 0)
                return;
            ItemBuilder item = new ItemBuilder(role.getItem().clone());
            StringBuilder itemName = new StringBuilder();

            itemName.append(role.getItem().getItemMeta().getDisplayName());
            if (setup.getGame().getGameInfo().getMRules().activatedRoles.contains(role)) {
                itemName.append(ChatColor.GREEN + " ✔");
                item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 1).hideEnchants();
            }
            else
                itemName.append(ChatColor.DARK_RED + " ✘");

            this.setItem(roles.get(0), item.setName(itemName.toString()).toItemStack(), updateRole(role, IHostRolesPage.SOLOS));
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
            if (setup.getGame().getGameInfo().getMRules().activatedRoles.contains(role))
                setup.getGame().getGameInfo().getMRules().activatedRoles.remove(role);
            else
                setup.getGame().getGameInfo().getMRules().activatedRoles.add(role);
            update(page);
        };
    }

    private Consumer<InventoryClickEvent> updateOnClick(IHostRolesPage page) {
        return e -> {
            update(page);
        };
    }

}
