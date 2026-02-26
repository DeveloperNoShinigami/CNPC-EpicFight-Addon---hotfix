/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.util.Mth
 */
package noppes.npcs.roles;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.entity.data.role.IJobPuppet;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.util.ValueUtil;

public class JobPuppet
extends JobInterface
implements IJobPuppet {
    public PartConfig head = new PartConfig();
    public PartConfig larm = new PartConfig();
    public PartConfig rarm = new PartConfig();
    public PartConfig body = new PartConfig();
    public PartConfig lleg = new PartConfig();
    public PartConfig rleg = new PartConfig();
    public PartConfig head2 = new PartConfig();
    public PartConfig larm2 = new PartConfig();
    public PartConfig rarm2 = new PartConfig();
    public PartConfig body2 = new PartConfig();
    public PartConfig lleg2 = new PartConfig();
    public PartConfig rleg2 = new PartConfig();
    public boolean whileStanding = true;
    public boolean whileAttacking = false;
    public boolean whileMoving = false;
    public boolean animate = false;
    public int animationSpeed = 4;
    private int prevTicks = 0;
    private int startTick = 0;
    private float val = 0.0f;
    private float valNext = 0.0f;

    public JobPuppet(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public IJobPuppet.IJobPuppetPart getPart(int part) {
        if (part == 0) {
            return this.head;
        }
        if (part == 1) {
            return this.larm;
        }
        if (part == 2) {
            return this.rarm;
        }
        if (part == 3) {
            return this.body;
        }
        if (part == 4) {
            return this.lleg;
        }
        if (part == 5) {
            return this.rleg;
        }
        if (part == 6) {
            return this.head2;
        }
        if (part == 7) {
            return this.larm2;
        }
        if (part == 8) {
            return this.rarm2;
        }
        if (part == 9) {
            return this.body2;
        }
        if (part == 10) {
            return this.lleg2;
        }
        if (part == 11) {
            return this.rleg2;
        }
        throw new CustomNPCsException("Unknown part " + part, new Object[0]);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.m_128365_("PuppetHead", (Tag)this.head.writeNBT());
        compound.m_128365_("PuppetLArm", (Tag)this.larm.writeNBT());
        compound.m_128365_("PuppetRArm", (Tag)this.rarm.writeNBT());
        compound.m_128365_("PuppetBody", (Tag)this.body.writeNBT());
        compound.m_128365_("PuppetLLeg", (Tag)this.lleg.writeNBT());
        compound.m_128365_("PuppetRLeg", (Tag)this.rleg.writeNBT());
        compound.m_128365_("PuppetHead2", (Tag)this.head2.writeNBT());
        compound.m_128365_("PuppetLArm2", (Tag)this.larm2.writeNBT());
        compound.m_128365_("PuppetRArm2", (Tag)this.rarm2.writeNBT());
        compound.m_128365_("PuppetBody2", (Tag)this.body2.writeNBT());
        compound.m_128365_("PuppetLLeg2", (Tag)this.lleg2.writeNBT());
        compound.m_128365_("PuppetRLeg2", (Tag)this.rleg2.writeNBT());
        compound.m_128379_("PuppetStanding", this.whileStanding);
        compound.m_128379_("PuppetAttacking", this.whileAttacking);
        compound.m_128379_("PuppetMoving", this.whileMoving);
        compound.m_128379_("PuppetAnimate", this.animate);
        compound.m_128405_("PuppetAnimationSpeed", this.animationSpeed);
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        this.head.readNBT(compound.m_128469_("PuppetHead"));
        this.larm.readNBT(compound.m_128469_("PuppetLArm"));
        this.rarm.readNBT(compound.m_128469_("PuppetRArm"));
        this.body.readNBT(compound.m_128469_("PuppetBody"));
        this.lleg.readNBT(compound.m_128469_("PuppetLLeg"));
        this.rleg.readNBT(compound.m_128469_("PuppetRLeg"));
        this.head2.readNBT(compound.m_128469_("PuppetHead2"));
        this.larm2.readNBT(compound.m_128469_("PuppetLArm2"));
        this.rarm2.readNBT(compound.m_128469_("PuppetRArm2"));
        this.body2.readNBT(compound.m_128469_("PuppetBody2"));
        this.lleg2.readNBT(compound.m_128469_("PuppetLLeg2"));
        this.rleg2.readNBT(compound.m_128469_("PuppetRLeg2"));
        this.whileStanding = compound.m_128471_("PuppetStanding");
        this.whileAttacking = compound.m_128471_("PuppetAttacking");
        this.whileMoving = compound.m_128471_("PuppetMoving");
        this.setIsAnimated(compound.m_128471_("PuppetAnimate"));
        this.setAnimationSpeed(compound.m_128451_("PuppetAnimationSpeed"));
    }

    @Override
    public boolean aiShouldExecute() {
        return false;
    }

    private float calcRotation(float r, float r2, float partialTicks) {
        if (!this.animate) {
            return r;
        }
        if (this.prevTicks != this.npc.f_19797_) {
            float speed = 0.0f;
            if (this.animationSpeed == 0) {
                speed = 40.0f;
            } else if (this.animationSpeed == 1) {
                speed = 24.0f;
            } else if (this.animationSpeed == 2) {
                speed = 13.0f;
            } else if (this.animationSpeed == 3) {
                speed = 10.0f;
            } else if (this.animationSpeed == 4) {
                speed = 7.0f;
            } else if (this.animationSpeed == 5) {
                speed = 4.0f;
            } else if (this.animationSpeed == 6) {
                speed = 3.0f;
            } else if (this.animationSpeed == 7) {
                speed = 2.0f;
            }
            int ticks = this.npc.f_19797_ - this.startTick;
            this.val = 1.0f - (Mth.m_14089_((float)((float)ticks / speed * (float)Math.PI / 2.0f)) + 1.0f) / 2.0f;
            this.valNext = 1.0f - (Mth.m_14089_((float)((float)(ticks + 1) / speed * (float)Math.PI / 2.0f)) + 1.0f) / 2.0f;
            this.prevTicks = this.npc.f_19797_;
        }
        float f = this.val + (this.valNext - this.val) * partialTicks;
        return r + (r2 - r) * f;
    }

    public float getRotationX(PartConfig part1, PartConfig part2, float partialTicks) {
        return this.calcRotation(part1.rotationX, part2.rotationX, partialTicks);
    }

    public float getRotationY(PartConfig part1, PartConfig part2, float partialTicks) {
        return this.calcRotation(part1.rotationY, part2.rotationY, partialTicks);
    }

    public float getRotationZ(PartConfig part1, PartConfig part2, float partialTicks) {
        return this.calcRotation(part1.rotationZ, part2.rotationZ, partialTicks);
    }

    @Override
    public void reset() {
        this.val = 0.0f;
        this.valNext = 0.0f;
        this.prevTicks = 0;
        this.startTick = this.npc.f_19797_;
    }

    @Override
    public void delete() {
    }

    public boolean isActive() {
        if (!this.npc.m_6084_()) {
            return false;
        }
        return this.whileAttacking && this.npc.isAttacking() || this.whileMoving && this.npc.isWalking() || this.whileStanding && !this.npc.isWalking();
    }

    @Override
    public boolean getIsAnimated() {
        return this.animate;
    }

    @Override
    public void setIsAnimated(boolean bo) {
        this.animate = bo;
        if (!bo) {
            this.val = 0.0f;
            this.valNext = 0.0f;
            this.prevTicks = 0;
        } else {
            this.startTick = this.npc.f_19797_;
        }
        this.npc.updateClient = true;
    }

    @Override
    public int getAnimationSpeed() {
        return this.animationSpeed;
    }

    @Override
    public void setAnimationSpeed(int speed) {
        this.animationSpeed = ValueUtil.CorrectInt(speed, 0, 7);
        this.npc.updateClient = true;
    }

    @Override
    public int getType() {
        return 9;
    }

    public class PartConfig
    implements IJobPuppet.IJobPuppetPart {
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float rotationZ = 0.0f;
        public boolean disabled = false;

        public CompoundTag writeNBT() {
            CompoundTag compound = new CompoundTag();
            compound.m_128350_("RotationX", this.rotationX);
            compound.m_128350_("RotationY", this.rotationY);
            compound.m_128350_("RotationZ", this.rotationZ);
            compound.m_128379_("Disabled", this.disabled);
            return compound;
        }

        public void readNBT(CompoundTag compound) {
            this.rotationX = ValueUtil.correctFloat(compound.m_128457_("RotationX"), -1.0f, 1.0f);
            this.rotationY = ValueUtil.correctFloat(compound.m_128457_("RotationY"), -1.0f, 1.0f);
            this.rotationZ = ValueUtil.correctFloat(compound.m_128457_("RotationZ"), -1.0f, 1.0f);
            this.disabled = compound.m_128471_("Disabled");
        }

        @Override
        public int getRotationX() {
            return (int)((this.rotationX + 1.0f) * 180.0f);
        }

        @Override
        public int getRotationY() {
            return (int)((this.rotationY + 1.0f) * 180.0f);
        }

        @Override
        public int getRotationZ() {
            return (int)((this.rotationZ + 1.0f) * 180.0f);
        }

        @Override
        public void setRotation(int x, int y, int z) {
            this.disabled = false;
            this.rotationX = ValueUtil.correctFloat((float)x / 180.0f - 1.0f, -1.0f, 1.0f);
            this.rotationY = ValueUtil.correctFloat((float)y / 180.0f - 1.0f, -1.0f, 1.0f);
            this.rotationZ = ValueUtil.correctFloat((float)z / 180.0f - 1.0f, -1.0f, 1.0f);
            JobPuppet.this.npc.updateClient = true;
        }
    }
}

