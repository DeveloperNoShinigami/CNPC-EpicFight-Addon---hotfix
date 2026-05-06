package com.goodbird.cnpcefaddon.common.provider;

import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

import java.util.LinkedHashMap;
import java.util.Map;

public class NpcBranchPatchProvider extends MobPatchReloadListener.AbstractMobPatchProvider {
    private final Map<ResourceLocation, MobPatchReloadListener.AbstractMobPatchProvider> providers = new LinkedHashMap<>();
    private final MobPatchReloadListener.AbstractMobPatchProvider defaultProvider;

    public NpcBranchPatchProvider() {
        this.defaultProvider = new MobPatchReloadListener.NullPatchProvider();
    }

    public void addProvider(ResourceLocation resLoc, MobPatchReloadListener.AbstractMobPatchProvider newProv) {
        this.providers.put(resLoc, newProv);
    }

    @Override
    public EntityPatch<?> get(Entity entity) {
        if (entity instanceof EntityNPCInterface npc && npc.display != null) {
            IDataDisplay dataDisplay = (IDataDisplay) npc.display;
            if (dataDisplay.hasEFModel()) {
                MobPatchReloadListener.AbstractMobPatchProvider provider = this.providers.get(dataDisplay.getEFModel());
                if (provider != null) {
                    return provider.get(entity);
                }
            }
        }

        return this.defaultProvider.get(entity);
    }
}
