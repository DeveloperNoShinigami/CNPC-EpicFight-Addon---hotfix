/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

public interface INPCRanged {
    public int getStrength();

    public void setStrength(int var1);

    public int getSpeed();

    public void setSpeed(int var1);

    public int getBurst();

    public void setBurst(int var1);

    public int getBurstDelay();

    public void setBurstDelay(int var1);

    public int getKnockback();

    public void setKnockback(int var1);

    public int getSize();

    public void setSize(int var1);

    public boolean getRender3D();

    public void setRender3D(boolean var1);

    public boolean getSpins();

    public void setSpins(boolean var1);

    public boolean getSticks();

    public void setSticks(boolean var1);

    public boolean getHasGravity();

    public void setHasGravity(boolean var1);

    public boolean getAccelerate();

    public void setAccelerate(boolean var1);

    public int getExplodeSize();

    public void setExplodeSize(int var1);

    public int getEffectType();

    public int getEffectTime();

    public int getEffectStrength();

    public void setEffect(int var1, int var2, int var3);

    public boolean getGlows();

    public void setGlows(boolean var1);

    public int getParticle();

    public void setParticle(int var1);

    public String getSound(int var1);

    public void setSound(int var1, String var2);

    public int getShotCount();

    public void setShotCount(int var1);

    public boolean getHasAimAnimation();

    public void setHasAimAnimation(boolean var1);

    public int getAccuracy();

    public void setAccuracy(int var1);

    public int getRange();

    public void setRange(int var1);

    public int getDelayMin();

    public int getDelayMax();

    public int getDelayRNG();

    public void setDelay(int var1, int var2);

    public int getFireType();

    public void setFireType(int var1);

    public int getMeleeRange();

    public void setMeleeRange(int var1);
}

