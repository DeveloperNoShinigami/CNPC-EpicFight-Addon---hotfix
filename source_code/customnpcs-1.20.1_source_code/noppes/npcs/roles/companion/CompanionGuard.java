/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles.companion;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.companion.CompanionJobInterface;

public class CompanionGuard
extends CompanionJobInterface {
    public boolean isStanding = false;

    @Override
    public CompoundTag getNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128379_("CompanionGuardStanding", this.isStanding);
        return compound;
    }

    @Override
    public void setNBT(CompoundTag compound) {
        this.isStanding = compound.m_128471_("CompanionGuardStanding");
    }

    public boolean isEntityApplicable(Entity entity) {
        if (entity instanceof Player || entity instanceof EntityNPCInterface) {
            return false;
        }
        if (entity instanceof Creeper) {
            return false;
        }
        return entity instanceof Monster;
    }

    @Override
    public boolean isSelfSufficient() {
        return this.isStanding;
    }

    @Override
    public EnumCompanionJobs getType() {
        return EnumCompanionJobs.GUARD;
    }
}

