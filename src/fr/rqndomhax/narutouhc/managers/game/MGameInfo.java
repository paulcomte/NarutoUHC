/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.managers.game;

import fr.rqndomhax.narutouhc.core.Setup;
import fr.rqndomhax.narutouhc.infos.Maps;
import fr.rqndomhax.narutouhc.managers.MBorder;
import fr.rqndomhax.narutouhc.managers.MRules;
import fr.rqndomhax.narutouhc.managers.MTime;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class MGameInfo {

    private final MBorder mBorder;
    private GameState gameState = GameState.LOADING;
    private Maps currentMap = Maps.NO_PVP;
    private MTime mTime;
    private final MRules mRules;

    public MGameInfo(MBorder mBorder, MRules mRules) {
        this.mBorder = mBorder;
        this.mRules = mRules;
    }

    public MBorder getmBorder() {
        return mBorder;
    }

    public MRules getmRules() {
        return mRules;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public MTime startMTime(Setup setup) {
        if (this.mTime != null) return this.mTime;
        System.out.println("creating new mtime !");
        MTime mTime = new MTime(setup);
        this.mTime = mTime;
        return mTime;
    }

    public void removeMTime(Setup setup) {
        mTime.cancel();
        this.mTime = null;
        setup.getGame().getGameInfo().setGameState(GameState.LOBBY_WAITING);
        System.out.println("mtime deleted !");
    }

    public Maps getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(Maps currentMap) {
        this.currentMap = currentMap;
    }
}
