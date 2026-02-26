/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.api.wrapper.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.api.INbt;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.gui.IEntityDisplay;
import noppes.npcs.api.wrapper.NBTWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;

public class CustomGuiEntityDisplayWrapper
extends CustomGuiComponentWrapper
implements IEntityDisplay {
    private IEntity entity;
    private INbt entityData = new NBTWrapper(new CompoundTag());
    public int entityId = -1;
    private int rotation;
    public boolean isFollowingCursor = true;
    private float scale = 1.0f;
    private boolean showBackground = true;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;

    public CustomGuiEntityDisplayWrapper() {
    }

    public CustomGuiEntityDisplayWrapper(int id, IEntity entity, int x, int y) {
        this.setID(id);
        this.setEntity(entity);
        this.setPos(x, y);
    }

    @Override
    public IEntity getEntity() {
        return this.entity;
    }

    public INbt getEntityData() {
        return this.entityData;
    }

    @Override
    public IEntityDisplay setEntity(IEntity entity) {
        this.entity = entity;
        this.entityData = entity == null ? new NBTWrapper(new CompoundTag()) : entity.getEntityNbt();
        if (entity != null && entity.getMCEntity() instanceof Player) {
            this.entityId = entity.getMCEntity().m_19879_();
        }
        return this;
    }

    @Override
    public IEntityDisplay setEntitySyncedById(IEntity entity) {
        this.entity = entity;
        if (entity == null) {
            this.entityData = new NBTWrapper(new CompoundTag());
            this.entityId = -1;
        } else {
            this.entityId = entity.getMCEntity().m_19879_();
        }
        return this;
    }

    @Override
    public int getRotation() {
        return this.rotation;
    }

    @Override
    public IEntityDisplay setRotation(int rotation) {
        this.rotation = rotation;
        return this;
    }

    @Override
    public boolean isFollowingCursor() {
        return this.isFollowingCursor;
    }

    @Override
    public IEntityDisplay setFollowingCursor(boolean state) {
        this.isFollowingCursor = state;
        return this;
    }

    @Override
    public float getScale() {
        return this.scale;
    }

    @Override
    public IEntityDisplay setScale(float scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public boolean getBackground() {
        return this.showBackground;
    }

    @Override
    public IEntityDisplay setBackground(boolean bo) {
        this.showBackground = bo;
        return this;
    }

    @Override
    public int getType() {
        return 9;
    }

    @Override
    public CompoundTag toNBT(CompoundTag compound) {
        super.toNBT(compound);
        compound.m_128365_("entity", (Tag)this.entityData.getMCNBT());
        compound.m_128405_("entityId", this.entityId);
        compound.m_128405_("rotation", this.rotation);
        compound.m_128350_("scale", this.scale);
        compound.m_128379_("followCursor", this.isFollowingCursor);
        compound.m_128379_("background", this.showBackground);
        return compound;
    }

    @Override
    public CustomGuiComponentWrapper fromNBT(CompoundTag compound) {
        super.fromNBT(compound);
        this.entityData = NpcAPI.Instance().getINbt(compound.m_128469_("entity"));
        this.entityId = compound.m_128451_("entityId");
        this.setRotation(compound.m_128451_("rotation"));
        this.setScale(compound.m_128457_("scale"));
        this.setFollowingCursor(compound.m_128471_("followCursor"));
        this.setBackground(compound.m_128471_("background"));
        return this;
    }
}

