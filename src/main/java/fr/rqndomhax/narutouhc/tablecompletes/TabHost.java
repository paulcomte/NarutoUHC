/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tablecompletes;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GameInfo;
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

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) return null;

        Player player = (Player) sender;

        ArrayList<String> arrayList = new ArrayList<>();

        if (GameInfo.gameHost == null || !GameInfo.gameHost.equals(player.getUniqueId()) && !GameInfo.gameCoHost.contains(player.getUniqueId()))
            return null;

        if (args.length == 1)
            return Stream.of("delete", "promote", "ban", "unban", "kick", "say", "set").filter(a -> a.startsWith(args[0])).collect(Collectors.toList());

        switch (args[0].toLowerCase()) {
            case "delete":
                if (GameInfo.gameHost == null || !GameInfo.gameHost.equals(player.getUniqueId()))
                    return null;

                for (UUID coHost : GameInfo.gameCoHost) {
                    Player p = Bukkit.getPlayer(coHost);

                    if (p != null)
                        arrayList.add(p.getName());
                }
                return arrayList.stream().filter(a -> a.startsWith(args[1])).collect(Collectors.toList());
            case "promote":
                if (GameInfo.gameHost == null || !GameInfo.gameHost.equals(player.getUniqueId()))
                    return null;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.equals(player))
                        continue;

                    if (GameInfo.gameCoHost != null && GameInfo.gameCoHost.contains(p.getUniqueId()))
                        continue;

                    arrayList.add(p.getName());
                }
                return arrayList.stream().filter(a -> a.startsWith(args[1])).collect(Collectors.toList());
            case "ban":
            case "kick":
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.equals(player))
                        continue;

                    if (GameInfo.gameHost != null && GameInfo.gameHost.equals(player.getUniqueId())) {
                        arrayList.add(p.getName());
                        continue;
                    }

                    if (!GameInfo.gameCoHost.contains(player.getUniqueId()))
                        arrayList.add(p.getName());
                }
                return arrayList.stream().filter(a -> a.startsWith(args[1])).collect(Collectors.toList());
            default:
                return null;
        }
    }
}
