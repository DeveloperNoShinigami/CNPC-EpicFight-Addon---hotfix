/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.ChunkPos
 */
package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import noppes.npcs.controllers.ChunkController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobChunkLoader
extends JobInterface {
    private List<ChunkPos> chunks = new ArrayList<ChunkPos>();
    private int ticks = 20;
    private long playerLastSeen = -1L;

    public JobChunkLoader(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.m_128356_("ChunkPlayerLastSeen", this.playerLastSeen);
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        this.playerLastSeen = compound.m_128454_("ChunkPlayerLastSeen");
    }

    @Override
    public boolean aiShouldExecute() {
        --this.ticks;
        if (this.ticks > 0) {
            return false;
        }
        this.ticks = 20;
        List players = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_(48.0, 48.0, 48.0));
        if (!players.isEmpty()) {
            this.playerLastSeen = System.currentTimeMillis();
        }
        if (this.playerLastSeen < 0L) {
            return false;
        }
        if (System.currentTimeMillis() > this.playerLastSeen + 600000L) {
            ChunkController.instance.unload((ServerLevel)this.npc.m_9236_(), this.npc.m_20148_(), this.npc.m_146902_().f_45578_, this.npc.m_146902_().f_45579_);
            this.chunks.clear();
            this.playerLastSeen = -1L;
            return false;
        }
        double x = this.npc.m_20185_() / 16.0;
        double z = this.npc.m_20189_() / 16.0;
        ArrayList<ChunkPos> list = new ArrayList<ChunkPos>();
        list.add(new ChunkPos(Mth.m_14107_((double)x), Mth.m_14107_((double)z)));
        list.add(new ChunkPos(Mth.m_14165_((double)x), Mth.m_14165_((double)z)));
        list.add(new ChunkPos(Mth.m_14107_((double)x), Mth.m_14165_((double)z)));
        list.add(new ChunkPos(Mth.m_14165_((double)x), Mth.m_14107_((double)z)));
        for (ChunkPos chunk : list) {
            if (!this.chunks.contains(chunk)) {
                ChunkController.instance.load((ServerLevel)this.npc.m_9236_(), this.npc.m_20148_(), chunk.f_45578_, chunk.f_45579_);
            }
            this.chunks.remove(chunk);
        }
        for (ChunkPos chunk : this.chunks) {
            ChunkController.instance.unload((ServerLevel)this.npc.m_9236_(), this.npc.m_20148_(), chunk.f_45578_, chunk.f_45579_);
        }
        this.chunks = list;
        return false;
    }

    @Override
    public boolean aiContinueExecute() {
        return false;
    }

    @Override
    public void reset() {
        if (this.npc.m_9236_() instanceof ServerLevel) {
            ChunkController.instance.unload((ServerLevel)this.npc.m_9236_(), this.npc.m_20148_(), this.npc.m_146902_().f_45578_, this.npc.m_146902_().f_45579_);
            this.chunks.clear();
            this.playerLastSeen = 0L;
        }
    }

    @Override
    public void delete() {
    }

    @Override
    public int getType() {
        return 8;
    }
}

