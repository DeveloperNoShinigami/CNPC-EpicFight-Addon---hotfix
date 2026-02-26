/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs;

import net.minecraft.nbt.CompoundTag;

public class ModelPartConfig {
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float scaleZ = 1.0f;
    public float transX = 0.0f;
    public float transY = 0.0f;
    public float transZ = 0.0f;
    public boolean notShared = false;

    public CompoundTag writeToNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128350_("ScaleX", this.scaleX);
        compound.m_128350_("ScaleY", this.scaleY);
        compound.m_128350_("ScaleZ", this.scaleZ);
        compound.m_128350_("TransX", this.transX);
        compound.m_128350_("TransY", this.transY);
        compound.m_128350_("TransZ", this.transZ);
        compound.m_128379_("NotShared", this.notShared);
        return compound;
    }

    public void readFromNBT(CompoundTag compound) {
        this.scaleX = this.checkValue(compound.m_128457_("ScaleX"), 0.0f, 100.0f);
        this.scaleY = this.checkValue(compound.m_128457_("ScaleY"), 0.0f, 100.0f);
        this.scaleZ = this.checkValue(compound.m_128457_("ScaleZ"), 0.0f, 100.0f);
        this.transX = this.checkValue(compound.m_128457_("TransX"), -1.0f, 1.0f);
        this.transY = this.checkValue(compound.m_128457_("TransY"), -1.0f, 1.0f);
        this.transZ = this.checkValue(compound.m_128457_("TransZ"), -1.0f, 1.0f);
        this.notShared = compound.m_128471_("NotShared");
    }

    public String toString() {
        return "ScaleX: " + this.scaleX + " - ScaleY: " + this.scaleY + " - ScaleZ: " + this.scaleZ;
    }

    public void setScale(float x, float y, float z) {
        this.scaleX = x;
        this.scaleY = y;
        this.scaleZ = z;
    }

    public void setScale(float x, float y) {
        this.scaleZ = this.scaleX = x;
        this.scaleY = y;
    }

    public float checkValue(float given, float min, float max) {
        if (given < min) {
            return min;
        }
        if (given > max) {
            return max;
        }
        return given;
    }

    public void setTranslate(float transX, float transY, float transZ) {
        this.transX = transX;
        this.transY = transY;
        this.transZ = transZ;
    }

    public void copyValues(ModelPartConfig config) {
        this.scaleX = config.scaleX;
        this.scaleY = config.scaleY;
        this.scaleZ = config.scaleZ;
        this.transX = config.transX;
        this.transY = config.transY;
        this.transZ = config.transZ;
    }
}

