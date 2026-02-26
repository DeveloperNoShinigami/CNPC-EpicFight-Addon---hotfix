package com.goodbird.cnpcefaddon.common;

import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import noppes.npcs.entity.EntityNPCInterface;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.data.conditions.entity.HasCustomTag;

import java.util.List;

public class ResLocPredicate extends HasCustomTag {
    public ResourceLocation resourceLocation;

    public ResLocPredicate(ResourceLocation resLoc) {
        super(new ListTag());
        this.resourceLocation = resLoc;
    }

    @Override
    public Condition<Entity> read(CompoundTag tag) {
        return null;
    }

    @Override
    public CompoundTag serializePredicate() {
        return null;
    }

    @Override
    public boolean predicate(Entity target) {
        if (!(target instanceof EntityNPCInterface))
            return false;
        EntityNPCInterface npc = (EntityNPCInterface) target;
        if (npc.display != null && ((IDataDisplay) npc.display).hasEFModel()) {
            return resourceLocation.equals(((IDataDisplay) npc.display).getEFModel());
        }
        return false;
    }

    @Override
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
