/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.entity.IProjectile;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ThrowableWrapper;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityProjectile;

public class ProjectileWrapper<T extends EntityProjectile>
extends ThrowableWrapper<T>
implements IProjectile {
    public ProjectileWrapper(T entity) {
        super(entity);
    }

    @Override
    public IItemStack getItem() {
        return NpcAPI.Instance().getIItemStack(((EntityProjectile)this.entity).getItemDisplay());
    }

    @Override
    public void setItem(IItemStack item) {
        if (item == null) {
            ((EntityProjectile)this.entity).setThrownItem(ItemStack.f_41583_);
        } else {
            ((EntityProjectile)this.entity).setThrownItem(item.getMCItemStack());
        }
    }

    @Override
    public boolean getHasGravity() {
        return ((EntityProjectile)this.entity).hasGravity();
    }

    @Override
    public void setHasGravity(boolean bo) {
        ((EntityProjectile)this.entity).setHasGravity(bo);
    }

    @Override
    public int getAccuracy() {
        return ((EntityProjectile)this.entity).accuracy;
    }

    @Override
    public void setAccuracy(int accuracy) {
        ((EntityProjectile)this.entity).accuracy = accuracy;
    }

    @Override
    public void setHeading(IEntity entity) {
        this.setHeading(entity.getX(), entity.getMCEntity().m_20191_().f_82289_ + (double)(entity.getHeight() / 2.0f), entity.getZ());
    }

    @Override
    public void setHeading(double x, double y, double z) {
        float varF = ((EntityProjectile)this.entity).hasGravity() ? (float)Math.sqrt((x -= ((EntityProjectile)this.entity).m_20185_()) * x + (z -= ((EntityProjectile)this.entity).m_20189_()) * z) : 0.0f;
        float angle = ((EntityProjectile)this.entity).getAngleForXYZ(x, y -= ((EntityProjectile)this.entity).m_20186_(), z, varF, false);
        float acc = 20.0f - (float)Mth.m_14143_((float)((float)((EntityProjectile)this.entity).accuracy / 5.0f));
        ((EntityProjectile)this.entity).m_6686_(x, y, z, angle, acc);
    }

    @Override
    public void setHeading(float yaw, float pitch) {
        ((EntityProjectile)this.entity).f_19859_ = yaw;
        ((EntityProjectile)this.entity).f_19860_ = pitch;
        ((EntityProjectile)this.entity).m_146922_(yaw);
        ((EntityProjectile)this.entity).m_146926_(pitch);
        double varX = -Mth.m_14031_((float)(yaw / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(pitch / 180.0f * (float)Math.PI));
        double varZ = Mth.m_14089_((float)(yaw / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(pitch / 180.0f * (float)Math.PI));
        double varY = -Mth.m_14031_((float)(pitch / 180.0f * (float)Math.PI));
        float acc = 20.0f - (float)Mth.m_14143_((float)((float)((EntityProjectile)this.entity).accuracy / 5.0f));
        ((EntityProjectile)this.entity).m_6686_(varX, varY, varZ, -pitch, acc);
    }

    @Override
    public int getType() {
        return 7;
    }

    @Override
    public boolean typeOf(int type) {
        return type == 7 ? true : super.typeOf(type);
    }

    @Override
    public void enableEvents() {
        if (!((EntityProjectile)this.entity).scripts.contains(ScriptContainer.Current)) {
            ((EntityProjectile)this.entity).scripts.add(ScriptContainer.Current);
        }
    }
}

