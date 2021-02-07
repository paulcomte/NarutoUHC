/*
 * Copyright (c) 2021.
 *  Discord : _Paul#6918
 *  Author : RqndomHax
 *  Github: https://github.com/RqndomHax
 */

package fr.rqndomhax.narutouhc.listeners.world;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.HashSet;
import java.util.Set;

public class ChunkUnloadListener implements Listener {

    public static final Set<Chunk> keepChunk = new HashSet<>();

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        if (ChunkUnloadListener.keepChunk.contains(e.getChunk()))
            e.setCancelled(true);
    }

}
