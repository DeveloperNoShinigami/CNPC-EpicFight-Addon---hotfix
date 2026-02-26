/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NBTTags;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobGuard
extends JobInterface {
    public List<String> targets = new ArrayList<String>();

    public JobGuard(EntityNPCInterface npc) {
        super(npc);
    }

    public boolean isEntityApplicable(Entity entity) {
        if (entity instanceof Player || entity instanceof EntityNPCInterface) {
            return false;
        }
        return this.targets.contains(entity.m_6095_().m_20675_());
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128365_("GuardTargets", (Tag)NBTTags.nbtStringList(this.targets));
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.targets = NBTTags.getStringList(nbttagcompound.m_128437_("GuardTargets", 10));
    }

    @Override
    public int getType() {
        return 3;
    }
}

