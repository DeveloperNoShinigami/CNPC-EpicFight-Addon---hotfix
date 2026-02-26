/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.NBTTags;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.roles.RoleInterface;

public class DataTransform {
    public CompoundTag display;
    public CompoundTag ai;
    public CompoundTag advanced;
    public CompoundTag inv;
    public CompoundTag stats;
    public CompoundTag role;
    public CompoundTag job;
    public boolean hasDisplay;
    public boolean hasAi;
    public boolean hasAdvanced;
    public boolean hasInv;
    public boolean hasStats;
    public boolean hasRole;
    public boolean hasJob;
    public boolean isActive;
    private EntityNPCInterface npc;
    public boolean editingModus = false;

    public DataTransform(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public CompoundTag save(CompoundTag compound) {
        compound.m_128379_("TransformIsActive", this.isActive);
        this.writeOptions(compound);
        if (this.hasDisplay) {
            compound.m_128365_("TransformDisplay", (Tag)this.display);
        }
        if (this.hasAi) {
            compound.m_128365_("TransformAI", (Tag)this.ai);
        }
        if (this.hasAdvanced) {
            compound.m_128365_("TransformAdvanced", (Tag)this.advanced);
        }
        if (this.hasInv) {
            compound.m_128365_("TransformInv", (Tag)this.inv);
        }
        if (this.hasStats) {
            compound.m_128365_("TransformStats", (Tag)this.stats);
        }
        if (this.hasRole) {
            compound.m_128365_("TransformRole", (Tag)this.role);
        }
        if (this.hasJob) {
            compound.m_128365_("TransformJob", (Tag)this.job);
        }
        return compound;
    }

    public CompoundTag writeOptions(CompoundTag compound) {
        compound.m_128379_("TransformHasDisplay", this.hasDisplay);
        compound.m_128379_("TransformHasAI", this.hasAi);
        compound.m_128379_("TransformHasAdvanced", this.hasAdvanced);
        compound.m_128379_("TransformHasInv", this.hasInv);
        compound.m_128379_("TransformHasStats", this.hasStats);
        compound.m_128379_("TransformHasRole", this.hasRole);
        compound.m_128379_("TransformHasJob", this.hasJob);
        compound.m_128379_("TransformEditingModus", this.editingModus);
        return compound;
    }

    public void readToNBT(CompoundTag compound) {
        this.isActive = compound.m_128471_("TransformIsActive");
        this.readOptions(compound);
        this.display = this.hasDisplay ? compound.m_128469_("TransformDisplay") : this.getDisplay();
        this.ai = this.hasAi ? compound.m_128469_("TransformAI") : this.npc.ais.save(new CompoundTag());
        this.advanced = this.hasAdvanced ? compound.m_128469_("TransformAdvanced") : this.getAdvanced();
        this.inv = this.hasInv ? compound.m_128469_("TransformInv") : this.npc.inventory.save(new CompoundTag());
        this.stats = this.hasStats ? compound.m_128469_("TransformStats") : this.npc.stats.save(new CompoundTag());
        this.job = this.hasJob ? compound.m_128469_("TransformJob") : this.getJob();
        this.role = this.hasRole ? compound.m_128469_("TransformRole") : this.getRole();
    }

    public CompoundTag getJob() {
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("NpcJob", this.npc.job.getType());
        this.npc.job.save(compound);
        return compound;
    }

    public CompoundTag getRole() {
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("Role", this.npc.role.getType());
        this.npc.role.save(compound);
        return compound;
    }

    public CompoundTag getDisplay() {
        CompoundTag compound = this.npc.display.save(new CompoundTag());
        if (this.npc instanceof EntityCustomNpc) {
            compound.m_128365_("ModelData", (Tag)((EntityCustomNpc)this.npc).modelData.save());
        }
        return compound;
    }

    public CompoundTag getAdvanced() {
        JobInterface jopType = this.npc.job;
        RoleInterface roleType = this.npc.role;
        this.npc.job = JobInterface.NONE;
        this.npc.role = RoleInterface.NONE;
        CompoundTag compound = this.npc.advanced.save(new CompoundTag());
        this.npc.job = jopType;
        this.npc.role = roleType;
        return compound;
    }

    public void readOptions(CompoundTag compound) {
        boolean hadDisplay = this.hasDisplay;
        boolean hadAI = this.hasAi;
        boolean hadAdvanced = this.hasAdvanced;
        boolean hadInv = this.hasInv;
        boolean hadStats = this.hasStats;
        boolean hadRole = this.hasRole;
        boolean hadJob = this.hasJob;
        this.hasDisplay = compound.m_128471_("TransformHasDisplay");
        this.hasAi = compound.m_128471_("TransformHasAI");
        this.hasAdvanced = compound.m_128471_("TransformHasAdvanced");
        this.hasInv = compound.m_128471_("TransformHasInv");
        this.hasStats = compound.m_128471_("TransformHasStats");
        this.hasRole = compound.m_128471_("TransformHasRole");
        this.hasJob = compound.m_128471_("TransformHasJob");
        this.editingModus = compound.m_128471_("TransformEditingModus");
        if (this.hasDisplay && !hadDisplay) {
            this.display = this.getDisplay();
        }
        if (this.hasAi && !hadAI) {
            this.ai = this.npc.ais.save(new CompoundTag());
        }
        if (this.hasStats && !hadStats) {
            this.stats = this.npc.stats.save(new CompoundTag());
        }
        if (this.hasInv && !hadInv) {
            this.inv = this.npc.inventory.save(new CompoundTag());
        }
        if (this.hasAdvanced && !hadAdvanced) {
            this.advanced = this.getAdvanced();
        }
        if (this.hasJob && !hadJob) {
            this.job = this.getJob();
        }
        if (this.hasRole && !hadRole) {
            this.role = this.getRole();
        }
    }

    public boolean isValid() {
        return this.hasAdvanced || this.hasAi || this.hasDisplay || this.hasInv || this.hasStats || this.hasJob || this.hasRole;
    }

    public CompoundTag processAdvanced(CompoundTag compoundAdv, CompoundTag compoundRole, CompoundTag compoundJob) {
        if (this.hasAdvanced) {
            compoundAdv = this.advanced;
        }
        if (this.hasRole) {
            compoundRole = this.role;
        }
        if (this.hasJob) {
            compoundJob = this.job;
        }
        Set names = compoundRole.m_128431_();
        for (String name : names) {
            compoundAdv.m_128365_(name, compoundRole.m_128423_(name));
        }
        names = compoundJob.m_128431_();
        for (String name : names) {
            compoundAdv.m_128365_(name, compoundJob.m_128423_(name));
        }
        return compoundAdv;
    }

    public void transform(boolean isActive) {
        CompoundTag compound;
        if (this.isActive == isActive) {
            return;
        }
        if (this.hasDisplay) {
            compound = this.getDisplay();
            this.npc.display.readToNBT(NBTTags.NBTMerge(compound, this.display));
            if (this.npc instanceof EntityCustomNpc) {
                ((EntityCustomNpc)this.npc).modelData.load(NBTTags.NBTMerge(compound.m_128469_("ModelData"), this.display.m_128469_("ModelData")));
            }
            this.display = compound;
        }
        if (this.hasStats) {
            compound = this.npc.stats.save(new CompoundTag());
            this.npc.stats.readToNBT(NBTTags.NBTMerge(compound, this.stats));
            this.stats = compound;
        }
        if (this.hasAdvanced || this.hasJob || this.hasRole) {
            CompoundTag compoundAdv = this.getAdvanced();
            CompoundTag compoundRole = this.getRole();
            CompoundTag compoundJob = this.getJob();
            CompoundTag compound2 = this.processAdvanced(compoundAdv, compoundRole, compoundJob);
            this.npc.advanced.readToNBT(compound2);
            if (this.npc.role.getType() != 0) {
                this.npc.role.load(NBTTags.NBTMerge(compoundRole, compound2));
            }
            if (this.npc.job.getType() != 0) {
                this.npc.job.load(NBTTags.NBTMerge(compoundJob, compound2));
            }
            if (this.hasAdvanced) {
                this.advanced = compoundAdv;
            }
            if (this.hasRole) {
                this.role = compoundRole;
            }
            if (this.hasJob) {
                this.job = compoundJob;
            }
        }
        if (this.hasAi) {
            compound = this.npc.ais.save(new CompoundTag());
            this.npc.ais.readToNBT(NBTTags.NBTMerge(compound, this.ai));
            this.ai = compound;
            this.npc.setCurrentAnimation(0);
        }
        if (this.hasInv) {
            compound = this.npc.inventory.save(new CompoundTag());
            this.npc.inventory.load(NBTTags.NBTMerge(compound, this.inv));
            this.inv = compound;
        }
        this.npc.updateAI = true;
        this.isActive = isActive;
        this.npc.updateClient = true;
    }
}

