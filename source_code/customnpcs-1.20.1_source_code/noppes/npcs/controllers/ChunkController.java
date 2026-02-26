/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.ChunkPos
 */
package noppes.npcs.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import noppes.npcs.CustomNpcs;

public class ChunkController {
    public static ChunkController instance = new ChunkController();
    private HashMap<Long, List<UUID>> loaded = new HashMap();

    public ChunkController() {
        instance = this;
    }

    public void clear() {
        this.loaded = new HashMap();
    }

    public void unload(ServerLevel world, UUID id, int xChunk, int zChunk) {
        long i = ChunkPos.m_45589_((int)xChunk, (int)zChunk);
        List<UUID> list = this.loaded.get(i);
        if (list == null) {
            return;
        }
        list.remove(id);
        if (list.size() == 0) {
            world.m_8602_(xChunk, zChunk, false);
            this.loaded.remove(i);
        }
    }

    public void load(ServerLevel world, UUID id, int xChunk, int zChunk) {
        if (this.size() >= CustomNpcs.ChuckLoaders) {
            return;
        }
        long i = ChunkPos.m_45589_((int)xChunk, (int)zChunk);
        List<UUID> list = this.loaded.get(i);
        if (list == null) {
            list = new ArrayList<UUID>();
            this.loaded.put(i, list);
        }
        list.add(id);
        if (list.size() == 1) {
            world.m_8602_(xChunk, zChunk, true);
        }
    }

    public int size() {
        return this.loaded.size();
    }
}

