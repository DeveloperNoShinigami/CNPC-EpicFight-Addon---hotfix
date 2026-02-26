/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data.role;

import noppes.npcs.api.entity.data.INPCJob;

public interface IJobPuppet
extends INPCJob {
    public boolean getIsAnimated();

    public void setIsAnimated(boolean var1);

    public int getAnimationSpeed();

    public void setAnimationSpeed(int var1);

    public IJobPuppetPart getPart(int var1);

    public static interface IJobPuppetPart {
        public int getRotationX();

        public int getRotationY();

        public int getRotationZ();

        public void setRotation(int var1, int var2, int var3);
    }
}

