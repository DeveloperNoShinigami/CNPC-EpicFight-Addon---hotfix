/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.CustomNpcs;
import noppes.npcs.api.entity.data.role.IJobBard;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.MusicManagerMixin;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.shared.client.util.NoppesStringUtils;

public class JobBard
extends JobInterface
implements IJobBard {
    public int minRange = 2;
    public int maxRange = 64;
    public boolean isStreamer = true;
    public boolean isLooping = false;
    public boolean hasOffRange = true;
    public String song = "";

    public JobBard(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128359_("BardSong", this.song);
        nbttagcompound.m_128405_("BardMinRange", this.minRange);
        nbttagcompound.m_128405_("BardMaxRange", this.maxRange);
        nbttagcompound.m_128379_("BardStreamer", this.isStreamer);
        nbttagcompound.m_128379_("BardLoops", this.isLooping);
        nbttagcompound.m_128379_("BardHasOff", this.hasOffRange);
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.song = nbttagcompound.m_128461_("BardSong");
        this.minRange = nbttagcompound.m_128451_("BardMinRange");
        this.maxRange = nbttagcompound.m_128451_("BardMaxRange");
        this.isStreamer = nbttagcompound.m_128471_("BardStreamer");
        this.isLooping = nbttagcompound.m_128471_("BardLoops");
        this.hasOffRange = nbttagcompound.m_128471_("BardHasOff");
    }

    public void aiStep() {
        List list;
        if (!this.npc.isClientSide() || this.song.isEmpty()) {
            return;
        }
        if (!MusicController.Instance.isPlaying(this.song)) {
            List list2 = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_((double)this.minRange, (double)(this.minRange / 2), (double)this.minRange));
            if (!list2.contains(CustomNpcs.proxy.getPlayer())) {
                return;
            }
            if (this.isStreamer) {
                MusicController.Instance.playStreaming(this.song, (Entity)this.npc, this.isLooping);
            } else {
                MusicController.Instance.playMusic(this.song, (Entity)this.npc, this.isLooping);
            }
        } else if (MusicController.Instance.playingEntity != this.npc) {
            Player player = CustomNpcs.proxy.getPlayer();
            if (this.npc.m_20280_((Entity)player) < MusicController.Instance.playingEntity.m_20280_((Entity)player)) {
                MusicController.Instance.playingEntity = this.npc;
            }
        } else if (this.hasOffRange && !(list = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_((double)this.maxRange, (double)(this.maxRange / 2), (double)this.maxRange))).contains(CustomNpcs.proxy.getPlayer())) {
            MusicController.Instance.stopMusic();
        }
        if (MusicController.Instance.isPlaying(this.song)) {
            ((MusicManagerMixin)Minecraft.m_91087_().m_91397_()).nextSongDelay(12000);
        }
    }

    @Override
    public void killed() {
        this.delete();
    }

    @Override
    public void delete() {
        if (this.npc.m_9236_().f_46443_ && this.hasOffRange && MusicController.Instance.isPlaying(this.song)) {
            MusicController.Instance.stopMusic();
        }
    }

    @Override
    public String getSong() {
        return NoppesStringUtils.cleanResource(this.song);
    }

    @Override
    public void setSong(String song) {
        this.song = NoppesStringUtils.cleanResource(song);
        this.npc.updateClient = true;
    }

    @Override
    public int getType() {
        return 1;
    }
}

