/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */
/*+
package fr.rqndomhax.narutouhc.managers;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.game.GameState;
import fr.rqndomhax.narutouhc.managers.game.MGameActions;
import fr.rqndomhax.narutouhc.managers.game.MGameBuild;
import fr.rqndomhax.narutouhc.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class MTime extends BukkitRunnable {

    private final Setup setup;
    public int rawTime = 0;
    public int episode = 1;
    public int rawEpisode = 0;

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
        checkStart();
        checkTeleport();
        checkInvincibility();
        checkPreparation();
        checkSecondTeleport();
        checkBorder();
        setup.getGame().getGameInfo().getMRules().preparationTime--;
        setup.getGame().getGameInfo().getMRules().rolesAnnounce--;

        if (setup.getGame().getGameInfo().getMRules().rolesAnnounce == 0) {
            setup.getRole().dispatchRoles();
            for (MPlayer gamePlayer : setup.getGame().getGamePlayers()) {
                if (gamePlayer.role == null) continue;
                gamePlayer.role.onRoleGiven();
            }
        }

        if (setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_PREPARATION) ||
                setup.getGame().getGameInfo().getGameState().equals(GameState.GAME_MEETUP)) {
            rawTime++;
            rawEpisode++;
        }
        checkEpisode();
    }

    private void checkEpisode() {
        if (rawEpisode == 1170)
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED_30S);
        else if (rawEpisode == 1200)
            Bukkit.broadcastMessage(Messages.EPISODE_FINISHED.replace("%episode%", String.valueOf(episode)));
        else if (rawEpisode == 1201) {
            episode++;
            for (MPlayer player : setup.getGame().getGamePlayers()) {
                if (player.isDead) continue;
                if (player.role == null) continue;
                player.role.onNewEpisode(episode);
            }
            Bukkit.broadcastMessage(Messages.EPISODE_STARTED.replace("%episode%", String.valueOf(episode)));
            rawEpisode = 0;
        }
    }

    private void checkBorder() {
        if (setup.getGame().getGameInfo().getMRules().narutoTeleportingDuration > 0)
            return;
        if (setup.getGame().getGameInfo().getMBorder().timeBeforeResize < 0)
            return;
        setup.getGame().getGameInfo().getMBorder().timeBeforeResize--;

        int r = setup.getGame().getGameInfo().getMBorder().timeBeforeResize;

        if (r == 60 || r == 30 || r == 15 || r <= 5 && r > 0 || r == 10 || r == 5*60 || r == 10*60 || r == 30*60 || r == 60*60) {
            if (r == 1)
                Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE.replace("%time%", String.valueOf(r))
                        .replace("secondes", "seconde"));
            else
                Bukkit.broadcastMessage(Messages.WB_TIME_BEFORE_BORDER_RESIZE.replace("%time%", String.valueOf(r)));
        }
        else if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.GAME_MEETUP);
            Bukkit.broadcastMessage(Messages.WB_BORDER_RESIZING);
            setup.getGame().getGameInfo().getMBorder().timeBeforeResize--;
        }
    }

    private void checkSecondTeleport() {
        if (setup.getGame().getGameInfo().getMRules().preparationTime > 0)
            return;
        if (setup.getGame().getGameInfo().getMRules().narutoTeleportingDuration < 0)
            return;
        setup.getGame().getGameInfo().getMRules().narutoTeleportingDuration--;

        int r = setup.getGame().getGameInfo().getMRules().narutoTeleportingDuration;

        if (r == 10 || r == 15 || r == 30 || r == 60 || r <= 5 && r > 0)
            MGameActions.sendInfos(setup.getGame().getGamePlayers(), r);
        else if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.GAME_BORDER);
            MGameBuild.removePlatform(setup.getGame().getGamePlayers());
            setup.getGame().getGameInfo().getMRules().narutoTeleportingDuration--;
        }
    }

    private void checkPreparation() {
        if (setup.getGame().getGameInfo().getMRules().invincibilityTime > 0)
            return;
        if (setup.getGame().getGameInfo().getMRules().preparationTime < 0)
            return;
        setup.getGame().getGameInfo().getMRules().preparationTime--;

        int r = setup.getGame().getGameInfo().getMRules().preparationTime;

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
            setup.getGame().getGameInfo().getMRules().preparationTime--;
        }
    }

    private void checkInvincibility() {
        if (setup.getGame().getGameInfo().getMRules().teleportingDuration > 0)
            return;
        if (setup.getGame().getGameInfo().getMRules().invincibilityTime < 0)
            return;
        setup.getGame().getGameInfo().getMRules().invincibilityTime--;

        int r = setup.getGame().getGameInfo().getMRules().invincibilityTime;

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
            setup.getGame().getGameInfo().getMRules().invincibilityTime--;
        }
    }

    private void checkTeleport() {
        if (setup.getGame().getGameInfo().getMRules().startDuration > 0)
            return;
        if (setup.getGame().getGameInfo().getMRules().teleportingDuration < 0)
            return;
        setup.getGame().getGameInfo().getMRules().teleportingDuration--;

        int r = setup.getGame().getGameInfo().getMRules().teleportingDuration;

        if (r == 10 || r == 15 || r == 30 || r == 60 || r <= 5 && r > 0)
            MGameActions.sendInfos(setup.getGame().getGamePlayers(), r);

        else if (r == 0) {
            setup.getGame().getGameInfo().setGameState(GameState.GAME_INVINCIBILITY);
            MGameBuild.removePlatform(setup.getGame().getGamePlayers());
            setup.getGame().getGameInfo().getMRules().startDuration--;
        }
    }
}
*/