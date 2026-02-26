/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

public interface INPCMelee {
    public int getStrength();

    public void setStrength(int var1);

    public int getDelay();

    public void setDelay(int var1);

    public int getRange();

    public void setRange(int var1);

    public int getKnockback();

    public void setKnockback(int var1);

    public int getEffectType();

    public int getEffectTime();

    public int getEffectStrength();

    public void setEffect(int var1, int var2, int var3);
}

