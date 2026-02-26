/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance$Attenuation
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.controllers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class MusicController {
    public static MusicController Instance;
    public SoundInstance playing;
    public ResourceLocation playingResource;
    public Entity playingEntity;
    SimpleSoundInstance dialogSound = null;

    public MusicController() {
        Instance = this;
    }

    public void stopMusic() {
        SoundManager handler = Minecraft.m_91087_().m_91106_();
        if (this.playing != null) {
            handler.m_120399_(this.playing);
        }
        handler.m_120386_(null, SoundSource.MUSIC);
        handler.m_120386_(null, SoundSource.AMBIENT);
        handler.m_120386_(null, SoundSource.RECORDS);
        this.playingResource = null;
        this.playingEntity = null;
        this.playing = null;
    }

    public void playStreaming(String music, Entity entity, boolean isLooping) {
        if (this.isPlaying(music)) {
            return;
        }
        this.stopMusic();
        this.playingEntity = entity;
        this.playingResource = new ResourceLocation(music);
        SoundManager handler = Minecraft.m_91087_().m_91106_();
        this.playing = new SimpleSoundInstance(this.playingResource, SoundSource.RECORDS, 4.0f, 1.0f, SoundInstance.m_235150_(), isLooping, 0, SoundInstance.Attenuation.LINEAR, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), false);
        handler.m_120367_(this.playing);
    }

    public void playMusic(String music, Entity entity, boolean isLooping) {
        if (this.isPlaying(music)) {
            return;
        }
        this.stopMusic();
        this.playingResource = new ResourceLocation(music);
        this.playingEntity = entity;
        SoundManager handler = Minecraft.m_91087_().m_91106_();
        this.playing = new SimpleSoundInstance(this.playingResource, SoundSource.MUSIC, 1.0f, 1.0f, SoundInstance.m_235150_(), isLooping, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, false);
        handler.m_120367_(this.playing);
    }

    public boolean isPlaying(String music) {
        ResourceLocation resource = new ResourceLocation(music);
        if (this.playingResource == null || !this.playingResource.equals((Object)resource)) {
            return false;
        }
        return Minecraft.m_91087_().m_91106_().m_120403_(this.playing);
    }

    public void playSoundDialog(SoundSource cat, String music, BlockPos pos, float volume, float pitch) {
        if (this.dialogSound != null) {
            Minecraft.m_91087_().m_91106_().m_120399_((SoundInstance)this.dialogSound);
        }
        this.dialogSound = new SimpleSoundInstance(new ResourceLocation(music), cat, volume, pitch, SoundInstance.m_235150_(), false, 0, SoundInstance.Attenuation.LINEAR, (double)((float)pos.m_123341_() + 0.5f), (double)pos.m_123342_(), (double)((float)pos.m_123343_() + 0.5f), false);
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)this.dialogSound);
    }

    public void playSound(SoundSource cat, String music, BlockPos pos, float volume, float pitch) {
        SimpleSoundInstance rec = new SimpleSoundInstance(new ResourceLocation(music), cat, volume, pitch, SoundInstance.m_235150_(), false, 0, SoundInstance.Attenuation.LINEAR, (double)((float)pos.m_123341_() + 0.5f), (double)pos.m_123342_(), (double)((float)pos.m_123343_() + 0.5f), false);
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)rec);
    }
}

