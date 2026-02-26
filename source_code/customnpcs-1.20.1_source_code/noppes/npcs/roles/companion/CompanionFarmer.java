/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.roles.companion;

import net.minecraft.nbt.CompoundTag;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.roles.companion.CompanionJobInterface;

public class CompanionFarmer
extends CompanionJobInterface {
    public boolean isStanding = false;

    @Override
    public CompoundTag getNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128379_("CompanionFarmerStanding", this.isStanding);
        return compound;
    }

    @Override
    public void setNBT(CompoundTag compound) {
        this.isStanding = compound.m_128471_("CompanionFarmerStanding");
    }

    @Override
    public EnumCompanionJobs getType() {
        return EnumCompanionJobs.FARMER;
    }

    @Override
    public boolean isSelfSufficient() {
        return this.isStanding;
    }

    @Override
    public void onUpdate() {
    }
}

