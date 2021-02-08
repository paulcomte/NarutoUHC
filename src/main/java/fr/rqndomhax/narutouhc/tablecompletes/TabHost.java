/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tablecompletes;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.managers.GameRules;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TabHost implements TabCompleter {

    private final Setup setup;

    public TabHost(Setup setup) {
        this.setup = setup;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) return null;

        Player player = (Player) sender;

        ArrayList<String> arrayList = new ArrayList<>();

        GameRules rules = setup.getGame().getGameRules();

        if (rules.gameHost == null || !rules.gameHost.equals(player.getUniqueId()) && !rules.gameCoHost.contains(player.getUniqueId()))
            return null;

        if (args.length == 1)
            return Stream.of("delete", "promote", "ban", "unban", "kick", "say", "set").filter(a -> a.startsWith(args[0])).collect(Collectors.toList());

        switch (args[0].toLowerCase()) {
            case "delete":
                if (rules.gameHost == null || !rules.gameHost.equals(player.getUniqueId()))
                    return null;

                for (UUID coHost : rules.gameCoHost) {
                    Player p = Bukkit.getPlayer(coHost);

                    if (p != null)
                        arrayList.add(p.getName());
                }
                return arrayList.stream().filter(a -> a.startsWith(args[1])).collect(Collectors.toList());
            case "promote":
                if (rules.gameHost == null || !rules.gameHost.equals(player.getUniqueId()))
                    return null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.equals(player))
                        continue;

                    if (rules.gameCoHost != null && rules.gameCoHost.contains(p.getUniqueId()))
                        continue;

                    arrayList.add(p.getName());
                }
                return arrayList.stream().filter(a -> a.startsWith(args[1])).collect(Collectors.toList());
            case "ban":
            case "kick":
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.equals(player))
                        continue;

                    if (rules.gameHost != null && rules.gameHost.equals(player.getUniqueId())) {
                        arrayList.add(p.getName());
                        continue;
                    }

                    if (rules.gameCoHost == null || !rules.gameCoHost.contains(player.getUniqueId()))
                        arrayList.add(p.getName());
                }
                return arrayList.stream().filter(a -> a.startsWith(args[1])).collect(Collectors.toList());
            default:
                return null;
        }
    }
}
