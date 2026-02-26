/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.ModelEyeData;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.client.parts.MpmPart;
import noppes.npcs.client.parts.MpmPartData;
import noppes.npcs.constants.BodyPart;
import noppes.npcs.constants.EnumParts;

public abstract class ModelDataShared {
    public ModelPartConfig arm1 = new ModelPartConfig();
    public ModelPartConfig arm2 = new ModelPartConfig();
    public ModelPartConfig body = new ModelPartConfig();
    public ModelPartConfig leg1 = new ModelPartConfig();
    public ModelPartConfig leg2 = new ModelPartConfig();
    public ModelPartConfig head = new ModelPartConfig();
    protected ResourceLocation entityName = null;
    protected LivingEntity entity;
    public CompoundTag extra = new CompoundTag();
    public ListTag oldPartData = new ListTag();
    public List<MpmPartData> mpmParts = new ArrayList<MpmPartData>();
    public List<BodyPart> hiddenParts = new ArrayList<BodyPart>();
    public int wingMode = 0;
    public String url = "";
    public String displayName = "";
    public long lastEdited = System.currentTimeMillis();
    public int inLove = 0;
    public int animationTime = -1;
    public int modelType = 0;
    public int moveAnimation = 16;
    public int prevMoveAnimation = 16;
    public boolean startMoveAnimation = false;
    public int animation = 0;
    public int prevAnimation = 0;
    public boolean startAnimation = false;
    public int animationStart = 0;
    public float sleepRotation;

    public CompoundTag save() {
        CompoundTag compound = new CompoundTag();
        if (this.entityName != null) {
            compound.m_128359_("EntityName", this.entityName.toString());
        }
        compound.m_128365_("ArmsConfig", (Tag)this.arm1.writeToNBT());
        compound.m_128365_("Arms2Config", (Tag)this.arm2.writeToNBT());
        compound.m_128365_("BodyConfig", (Tag)this.body.writeToNBT());
        compound.m_128365_("LegsConfig", (Tag)this.leg1.writeToNBT());
        compound.m_128365_("Legs2Config", (Tag)this.leg2.writeToNBT());
        compound.m_128365_("HeadConfig", (Tag)this.head.writeToNBT());
        compound.m_128365_("ExtraData", (Tag)this.extra);
        compound.m_128405_("WingMode", this.wingMode);
        compound.m_128359_("CustomSkinUrl", this.url);
        compound.m_128359_("DisplayName", this.displayName);
        compound.m_128405_("Animation", this.animation);
        compound.m_128405_("MoveAnimation", this.moveAnimation);
        compound.m_128405_("ModelType", this.modelType);
        compound.m_128356_("LastEdited", this.lastEdited);
        compound.m_128365_("Parts", (Tag)this.oldPartData);
        ListTag list = new ListTag();
        for (MpmPartData e : this.mpmParts) {
            list.add((Object)e.getNbt());
        }
        compound.m_128365_("NewParts", (Tag)list);
        return compound;
    }

    public void load(CompoundTag compound) {
        int i;
        String rl = compound.m_128461_("EntityName");
        this.setEntity(rl.isEmpty() ? null : new ResourceLocation(rl));
        this.arm1.readFromNBT(compound.m_128469_("ArmsConfig"));
        this.arm2.readFromNBT(compound.m_128469_("Arms2Config"));
        this.body.readFromNBT(compound.m_128469_("BodyConfig"));
        this.leg1.readFromNBT(compound.m_128469_("LegsConfig"));
        this.leg2.readFromNBT(compound.m_128469_("Legs2Config"));
        this.head.readFromNBT(compound.m_128469_("HeadConfig"));
        this.extra = compound.m_128469_("ExtraData");
        this.wingMode = compound.m_128451_("WingMode");
        this.url = compound.m_128461_("CustomSkinUrl");
        this.displayName = compound.m_128461_("DisplayName");
        this.animation = compound.m_128451_("Animation");
        this.moveAnimation = compound.m_128451_("MoveAnimation");
        this.modelType = compound.m_128451_("ModelType");
        this.lastEdited = compound.m_128454_("LastEdited");
        ArrayList<MpmPartData> mpmParts = new ArrayList<MpmPartData>();
        ListTag list = compound.m_128437_("NewParts", 10);
        for (i = 0; i < list.size(); ++i) {
            MpmPartData part = new MpmPartData();
            part.setNbt(list.m_128728_(i));
            if (part.partId.equals((Object)ModelEyeData.RESOURCE) || part.partId.equals((Object)ModelEyeData.RESOURCE_RIGHT) || part.partId.equals((Object)ModelEyeData.RESOURCE_LEFT)) {
                part = new ModelEyeData();
                part.setNbt(list.m_128728_(i));
            }
            mpmParts.add(part);
        }
        this.mpmParts = mpmParts;
        this.oldPartData = compound.m_128437_("Parts", 10);
        if (this.mpmParts.isEmpty()) {
            for (i = 0; i < list.size(); ++i) {
                this.mpmParts.add(EnumParts.convertOldPart(list.m_128728_(i)));
            }
        }
        this.refreshParts();
        this.updateTransate();
    }

    public void setMoveAnimation(int ani) {
        this.startMoveAnimation = this.moveAnimation != ani;
        this.moveAnimation = ani;
    }

    public int getMoveAnimtion(LivingEntity player) {
        if (player.m_20159_()) {
            return 1;
        }
        if (player.m_5803_()) {
            return 2;
        }
        if (this.moveAnimation == 16 && player.m_6047_()) {
            return 4;
        }
        return this.moveAnimation;
    }

    public boolean isMovementAnimation(int ani) {
        return ani == 2 || ani == 7 || ani == 4 || ani == 1 || ani == 14 || ani == 15 || ani == 16 || ani == 18 || ani == 17;
    }

    public void setAnimation(int ani) {
        if (this.isMovementAnimation(ani)) {
            this.setMoveAnimation(ani);
            return;
        }
        this.animationTime = -1;
        this.animation = ani;
        this.lastEdited = System.currentTimeMillis();
        boolean bl = this.startAnimation = this.animation != ani;
        if (this.animation == 10) {
            this.animationTime = 80;
        }
        if (this.animation == 13 || this.animation == 12) {
            this.animationTime = 60;
        }
        this.animationStart = this.getOwner() == null || ani == 0 ? -1 : this.getOwner().f_19797_;
    }

    public void updateTransate() {
        for (EnumParts part : EnumParts.values()) {
            float y;
            float x;
            ModelPartConfig body;
            ModelPartConfig config = this.getPartConfig(part);
            if (config == null) continue;
            if (part == EnumParts.HEAD) {
                config.setTranslate(0.0f, this.getBodyY(), 0.0f);
                continue;
            }
            if (part == EnumParts.ARM_LEFT) {
                body = this.getPartConfig(EnumParts.BODY);
                x = (1.0f - body.scaleX) * 0.25f + (1.0f - config.scaleX) * 0.0625f;
                y = this.getBodyY() + (1.0f - config.scaleY) * -0.125f;
                config.setTranslate(-x, y, 0.0f);
                if (config.notShared) continue;
                ModelPartConfig arm = this.getPartConfig(EnumParts.ARM_RIGHT);
                arm.copyValues(config);
                continue;
            }
            if (part == EnumParts.ARM_RIGHT) {
                body = this.getPartConfig(EnumParts.BODY);
                x = (1.0f - body.scaleX) * 0.25f + (1.0f - config.scaleX) * 0.0625f;
                y = this.getBodyY() + (1.0f - config.scaleY) * -0.125f;
                config.setTranslate(x, y, 0.0f);
                continue;
            }
            if (part == EnumParts.LEG_LEFT) {
                config.setTranslate(-(1.0f - config.scaleX) * 0.118f, this.getLegsY(), -(1.0f - config.scaleZ) * 0.00625f);
                if (config.notShared) continue;
                ModelPartConfig leg = this.getPartConfig(EnumParts.LEG_RIGHT);
                leg.copyValues(config);
                continue;
            }
            if (part == EnumParts.LEG_RIGHT) {
                config.setTranslate((1.0f - config.scaleX) * 0.118f, this.getLegsY(), -(1.0f - config.scaleZ) * 0.00625f);
                continue;
            }
            if (part != EnumParts.BODY) continue;
            config.setTranslate(0.0f, this.getBodyY(), 0.0f);
        }
    }

    public void setEntity(ResourceLocation resourceLocation) {
        this.entityName = resourceLocation;
        this.clearEntity();
        this.extra = new CompoundTag();
    }

    public ResourceLocation getEntityName() {
        return this.entityName;
    }

    public boolean hasEntity() {
        return this.entityName != null;
    }

    public float offsetY() {
        if (this.entity == null) {
            return -this.getBodyY();
        }
        return this.entity.m_20206_() - 1.8f;
    }

    public void clearEntity() {
        this.entity = null;
    }

    public ModelPartConfig getPartConfig(EnumParts type) {
        if (type == EnumParts.BODY) {
            return this.body;
        }
        if (type == EnumParts.ARM_LEFT) {
            return this.arm1;
        }
        if (type == EnumParts.ARM_RIGHT) {
            return this.arm2;
        }
        if (type == EnumParts.LEG_LEFT) {
            return this.leg1;
        }
        if (type == EnumParts.LEG_RIGHT) {
            return this.leg2;
        }
        return this.head;
    }

    public abstract LivingEntity getOwner();

    public float getBodyY() {
        if (this.entity != null) {
            return this.entity.m_20206_();
        }
        return (1.0f - this.body.scaleY) * 0.75f + this.getLegsY();
    }

    public float getLegsY() {
        ModelPartConfig legs = this.leg1;
        if (this.leg1.notShared && this.leg2.scaleY > this.leg1.scaleY) {
            legs = this.leg2;
        }
        return (1.0f - legs.scaleY) * 0.75f;
    }

    public void refreshParts() {
        this.hiddenParts = this.mpmParts.stream().flatMap(part -> {
            MpmPart p = part.getPart();
            if (p != null) {
                return p.hiddenParts.stream();
            }
            return Stream.empty();
        }).distinct().collect(Collectors.toList());
    }
}

