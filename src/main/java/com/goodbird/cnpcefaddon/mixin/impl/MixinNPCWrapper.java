package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.CNPCEpicFightAddon;
import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.api.wrapper.EntityLivingWrapper;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.entity.EntityNPCInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(NPCWrapper.class)
public abstract class MixinNPCWrapper<T extends EntityNPCInterface> extends EntityLivingWrapper<T> {
    public MixinNPCWrapper(T entity) {
        super(entity);
    }

    @Unique
    public void setEFModel(String modelPath){
        ((IDataDisplay)entity.display).setEFModel(new ResourceLocation(modelPath));
    }

    /**
     * Writes a user-owned marker into Forge persistent entity NBT. EFI datapacks
     * match it below ForgeData, for example {ForgeData:{efi_role:"ranged"}}.
     */
    @Unique
    public void setEFNbtMarker(String key, String value) {
        entity.getPersistentData().putString(key, value);
        ((IDataDisplay) entity.display).refreshEFPatch();
    }
}
