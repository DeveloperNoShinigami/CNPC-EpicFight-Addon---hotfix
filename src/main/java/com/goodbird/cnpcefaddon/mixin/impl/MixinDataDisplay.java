package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import com.goodbird.cnpcefaddon.mixin.IMixinCapabilityDispatcher;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcEquipmentBridge;
import com.goodbird.cnpcefaddon.common.compatibility.CnpcEpicFightCombatBridge;
import com.goodbird.cnpcefaddon.common.NpcPatchReloadListener;
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
    @Shadow(remap = false)
    EntityNPCInterface npc;
    @Unique
    private ResourceLocation cNPC_EpicFight_Addon$efModelResLoc = null;
    @Unique
    private boolean cNPC_EpicFight_Addon$refreshingPatch = false;

    @Inject(method = "save", at = @At("HEAD"), remap = false)
    public void writeToNBT(CompoundTag nbttagcompound, CallbackInfoReturnable<CompoundTag> cir) {
        if (hasEFModel())
            nbttagcompound.putString("efModel", cNPC_EpicFight_Addon$efModelResLoc.toString());
        // CNPC's normal display update does not include Entity#getPersistentData().
        // Carry the EFI marker payload through the existing display sync so an
        // NBT-selected patch is reconstructed on tracking clients as well.
        if (!npc.getPersistentData().isEmpty())
            nbttagcompound.put(cNPC_EpicFight_Addon$NBT_MARKERS, npc.getPersistentData().copy());
    }

    @Inject(method = "readToNBT", at = @At("HEAD"), remap = false)
    public void readFromNBT(CompoundTag nbttagcompound, CallbackInfo ci) {
        boolean cNPC_EpicFight_Addon$patchSelectionChanged = false;
        if (nbttagcompound.contains(cNPC_EpicFight_Addon$NBT_MARKERS, 10)) {
            npc.getPersistentData().merge(nbttagcompound.getCompound(cNPC_EpicFight_Addon$NBT_MARKERS));
            cNPC_EpicFight_Addon$patchSelectionChanged = true;
        }
        if (nbttagcompound.contains("efModel")) {
            cNPC_EpicFight_Addon$efModelResLoc = new ResourceLocation(nbttagcompound.getString("efModel"));
            cNPC_EpicFight_Addon$patchSelectionChanged = true;
        }
        if (cNPC_EpicFight_Addon$patchSelectionChanged) {
            // This mixin is common code and must not reference net.minecraft.client.Minecraft.
            // Both sides can rebuild the capability here; the client-only render synchronization
            // is handled by the client event/mixin path.
            cNPC_EpicFight_Addon$updateModelCap();
            // GUI-save / direct-sync path: entity is already live on the server.
            // Push the updated EF patch to clients so living/weapon motions apply immediately.
            if (!npc.level().isClientSide() && npc.isAddedToWorld()) {
                npc.updateClient();
            }
            if (npc.isKilled()) {
                LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(npc, LivingEntityPatch.class);
                if (patch != null) {
                    patch.onDeath(new LivingDeathEvent(npc, npc.level().damageSources().generic()));
                }
            }
        }
    }

    @Override
    public void setEFModel(ResourceLocation modelPath, boolean server) {
        cNPC_EpicFight_Addon$efModelResLoc = modelPath;
        if (server) {
            cNPC_EpicFight_Addon$updateModelCap();
            npc.updateClient();
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
        }
    }

    @Unique
    private void cNPC_EpicFight_Addon$updateModelCap() {
        if (cNPC_EpicFight_Addon$refreshingPatch) {
            return;
        }
        cNPC_EpicFight_Addon$refreshingPatch = true;
        try {
        // EFI also registers a provider for the CustomNPC entity type. Rebind
        // the CNPC branch immediately before a refresh so a datapack reload or
        // EFI reload cannot make a valid CNPC selection resolve to null.
        NpcPatchReloadListener.bindEntityPatchProvider();
        ICapabilityProvider[] caps = ((IMixinCapabilityDispatcher) (Object) ((com.goodbird.cnpcefaddon.mixin.impl.MixinCapabilityProvider) (Object) npc)
                .invokeGetCapabilities()).getCaps();
        EntityPatchProvider newProvider = new EntityPatchProvider(npc);
        EntityPatch<?> newPatch = newProvider.get();
        if (newPatch == null) {
            CNPCEpicFightAddon.LOGGER.warn("CNPC EFI patch selection returned null: id={} selectedKey={} efModel={}",
                    npc.getId(), cNPC_EpicFight_Addon$selectedKey(), cNPC_EpicFight_Addon$efModelResLoc);
            return;
        }
        ((EntityPatch) newPatch).onConstructed(npc);
        ((EntityPatch) newPatch).onJoinWorld(npc, new EntityJoinLevelEvent(npc, npc.level()));
        if (newProvider.hasCapability()) {
            boolean hasFoundAny = false;
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
            // Install first. resync() must see the newly selected provider so
            // its armature, motion map, and held-item capability are retained.
            CnpcEquipmentBridge.observe(npc);
            CnpcEpicFightCombatBridge.resync(npc);

        }
        } finally {
            cNPC_EpicFight_Addon$refreshingPatch = false;
        }
    }

    @Unique
    private ResourceLocation cNPC_EpicFight_Addon$selectedKey() {
        return NpcPatchReloadListener.branchPatchProvider == null
                ? cNPC_EpicFight_Addon$efModelResLoc
                : NpcPatchReloadListener.branchPatchProvider.resolveSelectedKey(npc);
    }

}
