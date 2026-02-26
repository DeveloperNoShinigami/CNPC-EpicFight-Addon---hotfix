/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

import noppes.npcs.api.entity.data.INPCMelee;
import noppes.npcs.api.entity.data.INPCRanged;

public interface INPCStats {
    public int getMaxHealth();

    public void setMaxHealth(int var1);

    public float getResistance(int var1);

    public void setResistance(int var1, float var2);

    public int getCombatRegen();

    public void setCombatRegen(int var1);

    public int getHealthRegen();

    public void setHealthRegen(int var1);

    public INPCMelee getMelee();

    public INPCRanged getRanged();

    public boolean getImmune(int var1);

    public void setImmune(int var1, boolean var2);

    public void setCreatureType(int var1);

    public int getCreatureType();

    public int getRespawnType();

    public void setRespawnType(int var1);

    public int getRespawnTime();

    public void setRespawnTime(int var1);

    public boolean getHideDeadBody();

    public void setHideDeadBody(boolean var1);

    public int getAggroRange();

    public void setAggroRange(int var1);
}

