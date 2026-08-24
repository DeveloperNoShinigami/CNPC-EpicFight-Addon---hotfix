package com.goodbird.cnpcefaddon.common.provider;

import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NpcBranchPatchProvider extends MobPatchReloadListener.AbstractMobPatchProvider {
    private final Map<ResourceLocation, MobPatchReloadListener.AbstractMobPatchProvider> providers = new LinkedHashMap<>();
    private final Map<ResourceLocation, NbtProvider> nbtProviders = new LinkedHashMap<>();
    private final Map<Integer, ResourceLocation> observedSelections = new LinkedHashMap<>();
    private final Map<Integer, ResourceLocation> clientSelections = new LinkedHashMap<>();
    private final Set<Integer> clientSelectionKnown = new HashSet<>();
    private final MobPatchReloadListener.AbstractMobPatchProvider defaultProvider;

    public NpcBranchPatchProvider() {
        this.defaultProvider = new MobPatchReloadListener.NullPatchProvider();
    }

    public void addProvider(ResourceLocation resLoc, MobPatchReloadListener.AbstractMobPatchProvider newProv) {
        this.providers.put(resLoc, newProv);
    }

    public boolean hasProviders() {
        return !this.providers.isEmpty();
    }

    public void addNbtProvider(ResourceLocation resLoc, CompoundTag matcher,
            MobPatchReloadListener.AbstractMobPatchProvider provider) {
        this.nbtProviders.put(resLoc, new NbtProvider(resLoc, matcher, provider));
    }

    @Override
    public EntityPatch<?> get(Entity entity) {
        if (entity instanceof EntityNPCInterface npc && npc.display != null) {
            IDataDisplay dataDisplay = (IDataDisplay) npc.display;
            ResourceLocation clientSelection = this.clientSelections.get(entity.getId());
            if (this.clientSelectionKnown.contains(entity.getId()) && clientSelection != null) {
                MobPatchReloadListener.AbstractMobPatchProvider selected = this.providers.get(clientSelection);
                if (selected != null) {
                    return selected.get(entity);
                }
            }
            CompoundTag entityNbt = entity.serializeNBT();
            NbtProvider selectedNbtProvider = this.findMatchingNbtProvider(entityNbt);
            if (selectedNbtProvider != null) {
                    NbtProvider nbtProvider = selectedNbtProvider;
                    return nbtProvider.provider.get(entity);
            }

            // NBT selectors intentionally win over a stale efModel value from
            // an older NPC save. Explicit efModel selection remains the
            // fallback when no NBT provider matches.
            if (dataDisplay.hasEFModel()) {
                MobPatchReloadListener.AbstractMobPatchProvider provider = this.providers.get(dataDisplay.getEFModel());
                if (provider != null) {
                    return provider.get(entity);
                }
            }

        }

        return this.defaultProvider.get(entity);
    }

    /**
     * Returns the datapack key selected for an NPC, including an NBT override.
     * The renderer cannot use the capability provider as its selector: Epic
     * Fight's RenderEngine asks for a renderer before it renders the entity,
     * while the old code only consulted DataDisplay#efModel. That made a
     * correctly-installed NBT capability visually invisible.
     */
    public ResourceLocation resolveSelectedKey(Entity entity) {
        if (!(entity instanceof EntityNPCInterface npc) || npc.display == null) {
            return null;
        }

        ResourceLocation clientSelection = this.clientSelections.get(entity.getId());
        if (this.clientSelectionKnown.contains(entity.getId()) && clientSelection != null) {
            return clientSelection;
        }

        NbtProvider nbtProvider = this.findMatchingNbtProvider(entity.serializeNBT());
        if (nbtProvider != null) {
            return nbtProvider.key;
        }

        IDataDisplay dataDisplay = (IDataDisplay) npc.display;
        return dataDisplay.hasEFModel() && this.providers.containsKey(dataDisplay.getEFModel())
                ? dataDisplay.getEFModel()
                : null;
    }

    /** Returns true only when this entity's effective CNPC patch selection changed. */
    public boolean observeSelection(Entity entity) {
        ResourceLocation selected = this.resolveSelectedKey(entity);
        ResourceLocation previous = this.observedSelections.put(entity.getId(), selected);
        if (selected == null ? previous != null : !selected.equals(previous)) {
            return true;
        }
        return false;
    }

    public void applyClientSelection(int entityId, ResourceLocation selectedKey) {
        this.clientSelectionKnown.add(entityId);
        if (selectedKey == null) {
            this.clientSelections.remove(entityId);
        } else {
            this.clientSelections.put(entityId, selectedKey);
        }
    }

    private NbtProvider findMatchingNbtProvider(CompoundTag entityNbt) {
        for (NbtProvider nbtProvider : this.nbtProviders.values()) {
            if (NbtUtils.compareNbt(nbtProvider.matcher, entityNbt, true)) {
                return nbtProvider;
            }
        }
        return null;
    }

    private record NbtProvider(ResourceLocation key, CompoundTag matcher,
            MobPatchReloadListener.AbstractMobPatchProvider provider) {
    }
}
