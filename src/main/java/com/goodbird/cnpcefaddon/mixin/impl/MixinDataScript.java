package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import com.goodbird.cnpcefaddon.common.NpcPatchReloadListener;
import com.goodbird.cnpcefaddon.common.network.NetworkHandler;
import com.goodbird.cnpcefaddon.common.network.SPNpcPatchSelection;
import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataScript;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resources.ResourceLocation;

/** Refreshes the effective CNPC patch when the script subsystem is toggled. */
@Mixin(DataScript.class)
public abstract class MixinDataScript {
    @Shadow
    private EntityNPCInterface npc;

    @Shadow
    private boolean enabled;

    @Unique
    private boolean cnpcefaddon$enabledBeforeLoad;

    @Inject(method = "load", at = @At("HEAD"), remap = false)
    private void cnpcefaddon$captureScriptStateBeforeLoad(net.minecraft.nbt.CompoundTag data, CallbackInfo ci) {
        cnpcefaddon$enabledBeforeLoad = enabled;
    }

    /**
     * SPacketScriptSave(type=0) updates an NPC by calling DataScript.load()
     * directly. It does not call setEnabled(), so observing only setEnabled()
     * misses the GUI save/disable operation on the logical server.
     */
    @Inject(method = "load", at = @At("TAIL"), remap = false)
    private void cnpcefaddon$refreshPatchAfterScriptLoad(net.minecraft.nbt.CompoundTag data, CallbackInfo ci) {
        if (npc == null || npc.level().isClientSide() || npc.display == null
                || cnpcefaddon$enabledBeforeLoad == enabled) {
            return;
        }

        cnpcefaddon$refreshPatch("load", enabled);
    }

    @Inject(method = "setEnabled", at = @At("TAIL"), remap = false)
    private void cnpcefaddon$refreshPatchAfterScriptToggle(boolean enabled, CallbackInfo ci) {
        if (npc == null || npc.level().isClientSide() || npc.display == null) {
            return;
        }

        cnpcefaddon$refreshPatch("setEnabled", enabled);
    }

    @Unique
    private void cnpcefaddon$refreshPatch(String source, boolean scriptEnabled) {
        if (npc == null || npc.level().isClientSide() || npc.display == null) {
            return;
        }

        if (npc.display instanceof IDataDisplay display) {
            display.refreshEFPatch();
            ResourceLocation selected = NpcPatchReloadListener.providerFor(npc).resolveSelectedKey(npc);
            CNPCEpicFightAddon.LOGGER.info(
                    "[CNPC EFI diagnostic] script state changed: entity={} source={} enabled={} selectedKey={} fallbackPacket=true",
                    npc.getId(), source, scriptEnabled, selected);
            NetworkHandler.sendNearby(npc, new SPNpcPatchSelection(npc.getId(), selected, selected != null));
        }
    }
}
