/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class MTime extends BukkitRunnable {

    private final Setup setup;
    public int rawTime = 0;
    public int time = 0;

    public MTime(Setup setup) {
        this.setup = setup;
        updateVars();
        runTaskTimer(setup.getMain(), 0, 20);
    }

    private void updateVars() {
        Bukkit.getWorlds().forEach(world -> world.setGameRuleValue("naturalRegeneration", String.valueOf(setup.getGame().getGameInfo().getMRules().naturalRegeneration)));
    }

    @Override
    public void run() {

        switch (setup.getGame().getGameInfo().getGameState()) {
            case LOBBY_WAITING:
                checkStart();
                break;
            case LOBBY_TELEPORTING:
                checkTeleport();
                break;
            case GAME_INVINCIBILITY:
                checkInvincibility();
                break;
            case GAME_PREPARATION:
                checkPreparation();
                break;
            case GAME_TELEPORTING:
                checkSecondTeleport();
            default: break;
        }

        if (rawTime == setup.getGame().getGameInfo().getMRules().rolesAnnounce) {
            setup.getRole().dispatchRoles();
            for (MPlayer gamePlayer : setup.getGame().getGamePlayers()) {
                if (gamePlayer.role == null) continue;
                gamePlayer.role.onRoleGiven();
            }
        }

        time++;
        if (setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_PREPARATION) ||
                setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_PVP) )
            rawTime++;
    }

    private void checkSecondTeleport() {
        int r = setup.getGame().getGameInfo().getMRules().narutoTeleportingDuration - time;

        if (r == 10 || r == 15 || r == 30 || r == 60 || r <= 5 && r > 0)
            MGameActions.sendInfos(setup.getGame().getGamePlayers(), r);
        else if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.GAME_PVP);
            MGameBuild.removePlatform(setup.getGame().getGamePlayers());
        }
    }

    private void checkPreparation() {
        int r = setup.getGame().getGameInfo().getMRules().preparationTime - time;

        if (r == 60 || r == 30 || r == 15 || r <= 5 && r > 0 || r == 10 || r == 5*60 || r == 10*60 || r == 30*60 || r == 60*60) {
            if (r == 1)
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP.replace("%time%", String.valueOf(r))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.NARUTO_MAP_TP.replace("%time%", String.valueOf(r)));
        }
        else if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.GAME_TELEPORTING);
            Bukkit.getOnlinePlayers().forEach(player -> player.teleport(new Location(Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()), 0, Bukkit.getWorld(Maps.NARUTO_UNIVERSE.name()).getHighestBlockAt(0, 0).getY(), 0)));
            time -= setup.getGame().getGameInfo().getMRules().preparationTime;
        }
    }

    private void checkInvincibility() {
        int r = setup.getGame().getGameInfo().getMRules().invincibilityFinished - time;

        if (r == 30 || r == 60 || r == 15 || r == 10 || r <= 5 && r > 0) {
            if (r == 1)
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(r))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED_IN
                        .replace("%time%", String.valueOf(r)));
        }
        if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.GAME_PREPARATION);
            Bukkit.broadcastMessage(Messages.INVINCIBILITY_FINISHED);
            time -= setup.getGame().getGameInfo().getMRules().invincibilityFinished;
        }
    }

    private void checkStart() {
        int r = setup.getGame().getGameInfo().getMRules().startDuration - time;

        if (r == 60 ||r == 30 || r == 15 || r <= 5 && r > 0)
            MGameActions.sendInfos(setup.getGame().getGamePlayers(), r);
        else if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.LOBBY_TELEPORTING);
            MGameActions.teleportPlayers1(setup.getGame().getGameInfo().getMRules().playerDispatchingSize, setup.getGame().getGamePlayers());
            MGameBuild.removeLobby();
            time -= setup.getGame().getGameInfo().getMRules().startDuration;
        }
    }

    private void checkTeleport() {

        int r = setup.getGame().getGameInfo().getMRules().teleportingDuration - time;

        if (r == 10 || r == 15 || r == 30 || r == 60 || r <= 5 && r > 0)
            MGameActions.sendInfos(setup.getGame().getGamePlayers(), r);

        else if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.GAME_INVINCIBILITY);
            MGameBuild.removePlatform(setup.getGame().getGamePlayers());
            time -= setup.getGame().getGameInfo().getMRules().startDuration;
        }
    }

    private void checkBorderTask() {

        int r = setup.getGame().getGameInfo().getMBorder().timeBeforeResize - time;

        if (r == 30 || r == 15 || r <= 5 && r > 0)
            Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE.replace("%time%", String.valueOf(r)));

        else if (r == 0) {
            setup.getGame().getGameInfo().getMBorder().resizeBorder();
            Bukkit.broadcastMessage(Messages.WB_BORDER_RESIZING);
            time -= setup.getGame().getGameInfo().getMBorder().timeBeforeResize;
        }

    }
}
