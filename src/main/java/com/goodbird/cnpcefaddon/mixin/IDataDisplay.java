package com.goodbird.cnpcefaddon.mixin;

import net.minecraft.resources.ResourceLocation;

public interface IDataDisplay {
    default void setEFModel(ResourceLocation modelPath){
        setEFModel(modelPath, true);
    }
    void setEFModel(ResourceLocation modelPath, boolean server);
    ResourceLocation getEFModel();

    boolean hasEFModel();

    /** Re-evaluates the CNPC Epic Fight provider after script-persistent NBT changes. */
    void refreshEFPatch();
}
