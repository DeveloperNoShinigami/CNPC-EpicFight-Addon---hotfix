package com.goodbird.cnpcefaddon.common.provider;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
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
import java.util.UUID;

public class NpcBranchPatchProvider extends MobPatchReloadListener.AbstractMobPatchProvider {
    private final Map<ResourceLocation, MobPatchReloadListener.AbstractMobPatchProvider> providers = new LinkedHashMap<>();
    private final Map<ResourceLocation, NbtProvider> nbtProviders = new LinkedHashMap<>();
    private final Map<Integer, ResourceLocation> observedSelections = new LinkedHashMap<>();
    private final Map<Integer, ResourceLocation> clientSelections = new LinkedHashMap<>();
    private final Set<Integer> clientSelectionKnown = new HashSet<>();
    private final Map<Integer, UUID> clientSelectionEntityUuids = new LinkedHashMap<>();
    private final Map<UUID, String> lastDiagnosticState = new LinkedHashMap<>();
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
            UUID previousUuid = this.clientSelectionEntityUuids.put(entity.getId(), entity.getUUID());
            if (previousUuid != null && !previousUuid.equals(entity.getUUID())) {
                this.clientSelections.remove(entity.getId());
                this.clientSelectionKnown.remove(entity.getId());
            }
            IDataDisplay dataDisplay = (IDataDisplay) npc.display;
            ResourceLocation clientSelection = this.clientSelections.get(entity.getId());
            if (this.clientSelectionKnown.contains(entity.getId()) && clientSelection != null) {
                MobPatchReloadListener.AbstractMobPatchProvider selected = this.providers.get(clientSelection);
                if (selected != null) {
                    return selected.get(entity);
                }
            }
            // An enabled NBT selector is the most specific override. When its
            // script is disabled, this branch returns no match and the saved
            // CNPC display selection below becomes the fallback.
            CompoundTag entityNbt = selectionNbt(entity);
            NbtProvider selectedNbtProvider = this.findMatchingNbtProvider(entityNbt, entity);
            if (selectedNbtProvider != null) {
                return selectedNbtProvider.provider.get(entity);
            }

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

        UUID previousUuid = this.clientSelectionEntityUuids.put(entity.getId(), entity.getUUID());
        if (previousUuid != null && !previousUuid.equals(entity.getUUID())) {
            this.clientSelections.remove(entity.getId());
            this.clientSelectionKnown.remove(entity.getId());
        }

        ResourceLocation clientSelection = this.clientSelections.get(entity.getId());
        if (this.clientSelectionKnown.contains(entity.getId())) {
            // A server packet with no selected key is an authoritative clear.
            // Do not fall through to the mirrored ForgeData marker, or a
            // disabled script can immediately resurrect the old NBT override.
            if (clientSelection != null) {
                logSelection(entity, "client-selection", clientSelection);
                return clientSelection;
            }
            IDataDisplay clearedDisplay = (IDataDisplay) npc.display;
            ResourceLocation manualFallback = clearedDisplay.hasEFModel()
                    && this.providers.containsKey(clearedDisplay.getEFModel())
                    ? clearedDisplay.getEFModel() : null;
            logSelection(entity, manualFallback == null ? "client-clear-fallback-none" : "client-clear-manual",
                    manualFallback);
            return manualFallback;
        }

        NbtProvider nbtProvider = this.findMatchingNbtProvider(selectionNbt(entity), entity);
        if (nbtProvider != null) {
            logSelection(entity, "nbt-override", nbtProvider.key);
            return nbtProvider.key;
        }

        IDataDisplay dataDisplay = (IDataDisplay) npc.display;
        ResourceLocation selected = dataDisplay.hasEFModel() && this.providers.containsKey(dataDisplay.getEFModel())
                ? dataDisplay.getEFModel()
                : null;
        logSelection(entity, selected == null ? "fallback-none" : "manual-cnpc", selected);
        return selected;
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
        CNPCEpicFightAddon.LOGGER.info("[CNPC EFI diagnostic] client selection updated: entityId={} selectedKey={} active={}",
                entityId, selectedKey, selectedKey != null);
    }

    private void logSelection(Entity entity, String source, ResourceLocation selected) {
        String state = source + "|" + selected;
        if (state.equals(this.lastDiagnosticState.put(entity.getUUID(), state))) {
            return;
        }
        CNPCEpicFightAddon.LOGGER.info("[CNPC EFI diagnostic] effective provider: entity={} uuid={} side={} source={} selectedKey={} nbtKeys={} manual={}",
                entity.getId(), entity.getUUID(), entity.level().isClientSide() ? "CLIENT" : "SERVER",
                source, selected, selectionNbt(entity).getCompound("ForgeData").getAllKeys(),
                entity instanceof EntityNPCInterface npc && npc.display != null
                        ? ((IDataDisplay) npc.display).getEFModel() : null);
    }

    private NbtProvider findMatchingNbtProvider(CompoundTag entityNbt, Entity entity) {
        // CNPC's script marker is persistent by design. Disabling the script
        // must nevertheless disable its selector immediately; otherwise the
        // old ForgeData value survives and appears to ignore the refresh.
        // Explicit efModel selection is handled before this method and remains
        // valid even while CNPC scripts are disabled.
        if (entity instanceof EntityNPCInterface
                && !((EntityNPCInterface) entity).script.getEnabled()) {
            return null;
        }
        for (NbtProvider nbtProvider : this.nbtProviders.values()) {
            if (NbtUtils.compareNbt(nbtProvider.matcher, entityNbt, true)) {
                return nbtProvider;
            }
        }
        return null;
    }

    /**
     * Builds only the selector-facing NBT without invoking Entity#serializeNBT.
     * Serializing an entity can ask Epic Fight for its patch while the provider
     * is itself resolving that patch, causing recursive LazyOptional access
     * during world/entity saves.
     */
    private CompoundTag selectionNbt(Entity entity) {
        CompoundTag result = new CompoundTag();
        result.put("ForgeData", entity.getPersistentData().copy());
        return result;
    }

    private record NbtProvider(ResourceLocation key, CompoundTag matcher,
            MobPatchReloadListener.AbstractMobPatchProvider provider) {
    }
}
