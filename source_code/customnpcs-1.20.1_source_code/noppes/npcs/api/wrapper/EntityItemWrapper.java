/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper;

import java.util.UUID;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IEntityItem;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.EntityWrapper;
import noppes.npcs.mixin.ItemEntityMixin;

public class EntityItemWrapper<T extends ItemEntity>
extends EntityWrapper<T>
implements IEntityItem {
    public EntityItemWrapper(T entity) {
        super(entity);
    }

    @Override
    public String getOwner() {
        if (((ItemEntity)this.entity).m_19749_() == null) {
            return null;
        }
        return ((ItemEntity)this.entity).m_19749_().toString();
    }

    @Override
    public void setOwner(String name) {
        ((ItemEntity)this.entity).m_32052_(UUID.fromString(name));
    }

    @Override
    public int getPickupDelay() {
        return ((ItemEntityMixin)this.entity).pickupDelay();
    }

    @Override
    public void setPickupDelay(int delay) {
        ((ItemEntity)this.entity).m_32010_(delay);
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public long getAge() {
        return ((ItemEntity)this.entity).m_32059_();
    }

    @Override
    public void setAge(long age) {
        age = Math.max(Math.min(age, Integer.MAX_VALUE), Integer.MIN_VALUE);
        ((ItemEntityMixin)this.entity).age((int)age);
    }

    @Override
    public int getLifeSpawn() {
        return ((ItemEntity)this.entity).lifespan;
    }

    @Override
    public void setLifeSpawn(int age) {
        ((ItemEntity)this.entity).lifespan = age;
    }

    @Override
    public IItemStack getItem() {
        return NpcAPI.Instance().getIItemStack(((ItemEntity)this.entity).m_32055_());
    }

    @Override
    public void setItem(IItemStack item) {
        ItemStack stack = item == null ? ItemStack.f_41583_ : item.getMCItemStack();
        ((ItemEntity)this.entity).m_32045_(stack);
    }
}

