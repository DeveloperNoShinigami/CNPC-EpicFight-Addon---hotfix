package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import com.goodbird.cnpcefaddon.mixin.IMixinCapabilityDispatcher;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcEquipmentBridge;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcEpicFightCombatBridge;
import com.goodbird.cnpcefaddon.common.NpcPatchReloadListener;
import com.goodbird.cnpcefaddon.common.patch.INpcPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;

@Mixin(value = DataDisplay.class, priority = 1001)
public class MixinDataDisplay implements IDataDisplay {
    @Unique
    private static final String cNPC_EpicFight_Addon$NBT_MARKERS = "cnpcEfiNbtMarkers";
    // Runtime TACZ/Epic Arsenal state is not patch-selection configuration.
    // Sending it through CNPC display sync makes every shot look like an NBT
    // selector change and rebuilds the client patch/animator.
    @Unique
    private static final String cNPC_EpicFight_Addon$EPIC_ARSENAL_LAST_SHOT = "epicarsenal:lastShotTick";
    @Shadow(remap = false)
    EntityNPCInterface npc;
    @Unique
    private ResourceLocation cNPC_EpicFight_Addon$efModelResLoc = null;
    @Unique
    private boolean cNPC_EpicFight_Addon$refreshingPatch = false;
    @Unique
    private CompoundTag cNPC_EpicFight_Addon$lastMarkerSnapshot = new CompoundTag();

    @Inject(method = "save", at = @At("HEAD"), remap = false)
    public void writeToNBT(CompoundTag nbttagcompound, CallbackInfoReturnable<CompoundTag> cir) {
        if (hasEFModel())
            nbttagcompound.putString("efModel", cNPC_EpicFight_Addon$efModelResLoc.toString());
        else
            nbttagcompound.remove("efModel");
        // CNPC's normal display update does not include Entity#getPersistentData().
        // Carry the EFI marker payload through the existing display sync so an
        // NBT-selected patch is reconstructed on tracking clients as well.
        CompoundTag markerPayload = cNPC_EpicFight_Addon$markerPayload();
        if (!markerPayload.isEmpty())
            nbttagcompound.put(cNPC_EpicFight_Addon$NBT_MARKERS, markerPayload);
        else
            nbttagcompound.remove(cNPC_EpicFight_Addon$NBT_MARKERS);
    }

    @Inject(method = "readToNBT", at = @At("HEAD"), remap = false)
    public void readFromNBT(CompoundTag nbttagcompound, CallbackInfo ci) {
        boolean cNPC_EpicFight_Addon$patchSelectionChanged = false;
        // The marker payload is an authoritative snapshot. An omitted marker
        // means the previous EFI marker was removed, such as when its script is
        // disabled. Treating omission as a delta leaves the old NBT provider
        // active on the client and blocks fallback to the configured patch.
        CompoundTag incomingMarkers = nbttagcompound.contains(cNPC_EpicFight_Addon$NBT_MARKERS, 10)
                ? nbttagcompound.getCompound(cNPC_EpicFight_Addon$NBT_MARKERS).copy()
                : new CompoundTag();
        incomingMarkers.remove(cNPC_EpicFight_Addon$EPIC_ARSENAL_LAST_SHOT);
        if (!cNPC_EpicFight_Addon$lastMarkerSnapshot.equals(incomingMarkers)) {
            for (String key : cNPC_EpicFight_Addon$lastMarkerSnapshot.getAllKeys()) {
                npc.getPersistentData().remove(key);
            }
            npc.getPersistentData().merge(incomingMarkers);
            cNPC_EpicFight_Addon$lastMarkerSnapshot = incomingMarkers.copy();
            cNPC_EpicFight_Addon$patchSelectionChanged = true;
            CNPCEpicFightAddon.LOGGER.info(
                    "[CNPC EFI diagnostic] marker snapshot changed: id={} side={} present={} keys={}",
                    npc.getId(), npc.level().isClientSide() ? "CLIENT" : "SERVER",
                    !incomingMarkers.isEmpty(), incomingMarkers.getAllKeys());
        }
        if (nbttagcompound.contains("efModel")) {
            String model = nbttagcompound.getString("efModel");
            ResourceLocation incomingModel = model.isBlank() ? null : new ResourceLocation(model);
            if (incomingModel == null ? cNPC_EpicFight_Addon$efModelResLoc != null
                    : !incomingModel.equals(cNPC_EpicFight_Addon$efModelResLoc)) {
                cNPC_EpicFight_Addon$efModelResLoc = incomingModel;
                cNPC_EpicFight_Addon$patchSelectionChanged = true;
            }
        }
        if (cNPC_EpicFight_Addon$patchSelectionChanged) {
            // This mixin is common code and must not reference net.minecraft.client.Minecraft.
            // Both sides can rebuild the capability here; the client-only render synchronization
            // is handled by the client event/mixin path.
            // On the server, readToNBT is also reached by CNPC's updateClient()
            // serializer. Rebuilding the provider from that callback causes a
            // refresh -> updateClient -> readToNBT loop and repeatedly puts TACZ
            // into draw state. Server selection/equipment tick paths perform the
            // authoritative refresh; only the receiving client rebuilds here.
            if (npc.level().isClientSide()) {
                cNPC_EpicFight_Addon$updateModelCap();
            }
            // GUI-save / direct-sync path: entity is already live on the server.
            // Push the updated EF patch to clients so living/weapon motions apply immediately.
            if (npc.isKilled()) {
                LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(npc, LivingEntityPatch.class);
                if (patch != null) {
                    patch.onDeath(new LivingDeathEvent(npc, npc.level().damageSources().generic()));
                }
            }
        }
    }

    @Unique
    private CompoundTag cNPC_EpicFight_Addon$markerPayload() {
        CompoundTag markers = npc.getPersistentData().copy();
        markers.remove(cNPC_EpicFight_Addon$EPIC_ARSENAL_LAST_SHOT);
        return markers;
    }

    @Override
    public void setEFModel(ResourceLocation modelPath, boolean server) {
        cNPC_EpicFight_Addon$efModelResLoc = modelPath;
        // A server-side script/UI update must always rebuild the entity-local
        // provider. The boolean is retained for CNPC's client mirror path.
        if (server || !npc.level().isClientSide()) {
            cNPC_EpicFight_Addon$updateModelCap();
            if (!npc.level().isClientSide() && npc.isAddedToWorld()) {
                npc.updateClient();
                CnpcEpicFightCombatBridge.resyncAfterClientUpdate(npc);
            }
        }
    }

    @Unique
    public ResourceLocation getEFModel() {
        return cNPC_EpicFight_Addon$efModelResLoc;
    }

    @Unique
    public boolean hasEFModel() {
        return cNPC_EpicFight_Addon$efModelResLoc != null;
    }

    @Override
    public void refreshEFPatch() {
        cNPC_EpicFight_Addon$updateModelCap();
        if (!npc.level().isClientSide() && npc.isAddedToWorld()) {
            npc.updateClient();
            CnpcEpicFightCombatBridge.resyncAfterClientUpdate(npc);
        }
    }

    @Unique
    private void cNPC_EpicFight_Addon$updateModelCap() {
        if (cNPC_EpicFight_Addon$refreshingPatch) {
            return;
        }
        // The integrated client receives CNPC patch definitions after entity
        // spawn data. Do not construct an advanced patch from the incomplete
        // pre-sync table: Epic Fight would install empty living motions and
        // the client animator can fail before the real provider arrives.
        if (npc.level().isClientSide() && !NpcPatchReloadListener.isClientDataReady()) {
            return;
        }
        cNPC_EpicFight_Addon$refreshingPatch = true;
        try {
        // EFI also registers a provider for the CustomNPC entity type. Rebind
        // the CNPC branch immediately before a refresh so a datapack reload or
        // EFI reload cannot make a valid CNPC selection resolve to null.
        NpcPatchReloadListener.bindEntityPatchProvider(NpcPatchReloadListener.providerFor(npc));
        ICapabilityProvider[] caps = ((IMixinCapabilityDispatcher) (Object) ((com.goodbird.cnpcefaddon.mixin.impl.MixinCapabilityProvider) (Object) npc)
                .invokeGetCapabilities()).getCaps();
        EntityPatchProvider newProvider = new EntityPatchProvider(npc);
        EntityPatch<?> newPatch = newProvider.get();
        if (newPatch != null) {
            ((EntityPatch) newPatch).onConstructed(npc);
            ((EntityPatch) newPatch).onJoinWorld(npc, new EntityJoinLevelEvent(npc, npc.level()));
        }
        // A null EntityPatchProvider is unsafe: EntityPatchProvider implements
        // NonNullSupplier and Forge will throw when its LazyOptional is read.
        // Remove the old provider entirely when the selection is cleared.
        boolean hasFoundAny = false;
        if (newPatch != null && newProvider.hasCapability()) {
            for (int i = 0; i < caps.length; i++) {
                if (caps[i] instanceof EntityPatchProvider) {
                    caps[i] = newProvider;
                    hasFoundAny = true;
                    break;
                }
            }
            if (!hasFoundAny) {
                ICapabilityProvider[] newCaps = new ICapabilityProvider[caps.length + 1];
                System.arraycopy(caps, 0, newCaps, 0, caps.length);
                newCaps[caps.length] = newProvider;
                ((IMixinCapabilityDispatcher) (Object) ((MixinCapabilityProvider) npc).invokeGetCapabilities())
                        .setCaps(newCaps);
            }
        } else {
            ICapabilityProvider[] filtered = java.util.Arrays.stream(caps)
                    .filter(cap -> !(cap instanceof EntityPatchProvider))
                    .toArray(ICapabilityProvider[]::new);
            ((IMixinCapabilityDispatcher) (Object) ((MixinCapabilityProvider) npc).invokeGetCapabilities())
                    .setCaps(filtered);
        }
        if (newPatch == null) {
            CNPCEpicFightAddon.LOGGER.info("CNPC EFI patch removed: id={} selectedKey={} efModel={}",
                    npc.getId(), cNPC_EpicFight_Addon$selectedKey(), cNPC_EpicFight_Addon$efModelResLoc);
        } else {
            CNPCEpicFightAddon.LOGGER.info("CNPC EFI patch applied: id={} selectedKey={}",
                    npc.getId(), cNPC_EpicFight_Addon$selectedKey());
            CNPCEpicFightAddon.LOGGER.info(
                    "[CNPC EFI diagnostic] patch state: id={} side={} provider={} patch={} armature={} renderer={} model={}",
                    npc.getId(), npc.level().isClientSide() ? "CLIENT" : "SERVER",
                    newProvider.getClass().getSimpleName(), newPatch.getClass().getSimpleName(),
                    newPatch instanceof INpcPatch && ((INpcPatch) newPatch).getArmature() != null
                            ? ((INpcPatch) newPatch).getArmature().getClass().getSimpleName() : "null",
                    cNPC_EpicFight_Addon$selectedKey(), cNPC_EpicFight_Addon$efModelResLoc);
            CnpcEquipmentBridge.observe(npc);
            CnpcEpicFightCombatBridge.resync(npc);
            if (npc.level().isClientSide()) {
                // The server packet may have arrived before this replacement
                // patch/animator existed. Apply the native gun map again to the
                // newly-created client animator.
                if (newPatch instanceof LivingEntityPatch<?> livingPatch) {
                    com.nameless.indestructible.world.capability.EpicArsenalMobMotionBridge
                            .resyncNativeGunMotionsClient(livingPatch);
                }
            }
        }
        } finally {
            cNPC_EpicFight_Addon$refreshingPatch = false;
        }
    }

    @Unique
    private ResourceLocation cNPC_EpicFight_Addon$selectedKey() {
        return NpcPatchReloadListener.providerFor(npc) == null
                ? cNPC_EpicFight_Addon$efModelResLoc
                : NpcPatchReloadListener.providerFor(npc).resolveSelectedKey(npc);
    }

}
