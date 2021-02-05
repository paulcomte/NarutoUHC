/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.commands;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GamePlayer;
import fr.rqndomhax.narutouhc.role.shinobi.Minato;
import fr.rqndomhax.narutouhc.utils.tools.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class CTPNaruto implements CommandExecutor {

    private final Setup setup;

    public CTPNaruto(Setup setup) {
        this.setup = setup;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        gamePlayer.role = new Minato(gamePlayer);
        //player.teleport(new Location(Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()), 0, 120, 0));
        //player.teleport(new Location(Bukkit.getWorld("world"), -228, 151, 228));
        player.getInventory().addItem(new ItemBuilder(Material.BOW).setName(ChatColor.YELLOW + "Shuriken").addUnsafeEnchantment(Enchantment.DURABILITY, 1).hideEnchants().setDurability((short) 50).toItemStack());
        return true;
    }

}
