/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.controllers.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import noppes.npcs.api.entity.data.role.IRoleTransporter;
import noppes.npcs.controllers.data.TransportCategory;

public class TransportLocation
implements IRoleTransporter.ITransportLocation {
    public int id = -1;
    public String name = "default name";
    public BlockPos pos;
    public int type = 0;
    public ResourceKey<Level> dimension = ResourceKey.m_135785_((ResourceKey)Registries.f_256858_, (ResourceLocation)Level.f_46428_.m_135782_());
    public TransportCategory category;

    public void readNBT(CompoundTag compound) {
        if (compound == null) {
            return;
        }
        this.id = compound.m_128451_("Id");
        this.pos = new BlockPos((int)compound.m_128459_("PosX"), (int)compound.m_128459_("PosY"), (int)compound.m_128459_("PosZ"));
        this.type = compound.m_128451_("Type");
        this.dimension = ResourceKey.m_135785_((ResourceKey)Registries.f_256858_, (ResourceLocation)new ResourceLocation(compound.m_128461_("DimensionType")));
        this.name = compound.m_128461_("Name");
    }

    public CompoundTag writeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("Id", this.id);
        compound.m_128347_("PosX", (double)this.pos.m_123341_());
        compound.m_128347_("PosY", (double)this.pos.m_123342_());
        compound.m_128347_("PosZ", (double)this.pos.m_123343_());
        compound.m_128405_("Type", this.type);
        compound.m_128359_("DimensionType", this.dimension.m_135782_().toString());
        compound.m_128359_("Name", this.name);
        return compound;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getDimension() {
        return this.dimension.m_135782_().toString();
    }

    @Override
    public int getX() {
        return this.pos.m_123341_();
    }

    @Override
    public int getY() {
        return this.pos.m_123342_();
    }

    @Override
    public int getZ() {
        return this.pos.m_123343_();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public boolean isDefault() {
        return this.type == 1;
    }
}

