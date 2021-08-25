/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.tablecompletes;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.game.GamePlayer;
import fr.rqndomhax.narutouhc.role.RoleInfo;
import fr.rqndomhax.narutouhc.role.shinobi.KakashiHatake;
import fr.rqndomhax.narutouhc.utils.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabNaruto implements TabCompleter {

    private final Setup setup;

    public TabNaruto(Setup setup) {
        this.setup = setup;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] args) {

        if (!(sender instanceof Player)) return null;

        Player player = (Player) sender;

        GamePlayer gamePlayer = setup.getGame().getGamePlayer(player.getUniqueId());

        if (gamePlayer == null || gamePlayer.role == null)
            return null;

        if (args.length > 1)
            return null;


        RoleInfo role = PlayerManager.getRole(gamePlayer.role);

        List<String> list = new ArrayList<>();

        list.add("claim");
        list.add("team");
        list.add("compo");
        switch (role.getRole()) {
            case SHIKAMARU:
                list.add("shikamaru");
                break;
            case HINATA:
                list.add("hinata");
                break;
            case NEJI:
                list.add("neji");
                break;
            case SAKURA:
                list.add("sakura");
                break;
            case DEIDARA:
                list.add("deidara");
                break;
            case NAGATO:
                list.add("nagato");
                break;
            case KAKASHI_HATAKE:
                list.add("kakashi");
                break;
            case OROCHIMARU:
                list.add("orochimaru");
                break;
            case KABUTO:
                list.add("kabuto");
                break;
            case DANZO:
                list.add("danzo");
                break;
            case SASUKE:
                list.add("sasuke");
                break;
        }

        return list.stream().filter(a -> a.startsWith(args[0])).collect(Collectors.toList());
    }
}
