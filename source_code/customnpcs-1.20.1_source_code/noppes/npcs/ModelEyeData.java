/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 */
package noppes.npcs;

import java.util.Random;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.client.parts.MpmPartData;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketEyeBlink;
import noppes.npcs.shared.common.util.ColorUtil;
import noppes.npcs.shared.common.util.NopVector2i;
import noppes.npcs.shared.common.util.NopVector3f;

public class ModelEyeData
extends MpmPartData {
    public static final ResourceLocation RESOURCE = new ResourceLocation("moreplayermodels", "eyes");
    public static final ResourceLocation RESOURCE_LEFT = new ResourceLocation("moreplayermodels", "eyes_left");
    public static final ResourceLocation RESOURCE_RIGHT = new ResourceLocation("moreplayermodels", "eyes_right");
    private final Random r = new Random();
    public boolean glint = true;
    public NopVector3f browThickness = new NopVector3f(1.0f, 0.4f, 1.0f);
    public NopVector2i eyePos = NopVector2i.ZERO;
    public boolean mirror = false;
    public int eyeSize = 0;
    public int skinType = 0;
    public boolean useLidTexture = false;
    public NopVector3f lidColor = ColorUtil.colorToRgb(11830381);
    public NopVector3f browColor = ColorUtil.colorToRgb(5982516);
    public long blinkStart = 0L;
    public boolean disableBlink = false;

    public ModelEyeData() {
        this.color = (new NopVector3f[]{ColorUtil.colorToRgb(8368696), ColorUtil.colorToRgb(16247203), ColorUtil.colorToRgb(0xA0A0FF), ColorUtil.colorToRgb(0xA7A7A7), ColorUtil.colorToRgb(10791096), ColorUtil.colorToRgb(0x4040FF), ColorUtil.colorToRgb(14188339), ColorUtil.colorToRgb(11685080), ColorUtil.colorToRgb(6724056), ColorUtil.colorToRgb(0xE5E533), ColorUtil.colorToRgb(55610), ColorUtil.colorToRgb(8375321), ColorUtil.colorToRgb(15892389), ColorUtil.colorToRgb(0x999999), ColorUtil.colorToRgb(5013401), ColorUtil.colorToRgb(8339378), ColorUtil.colorToRgb(3361970), ColorUtil.colorToRgb(6704179), ColorUtil.colorToRgb(6717235), ColorUtil.colorToRgb(0x993333), ColorUtil.colorToRgb(16445005), ColorUtil.colorToRgb(6085589), ColorUtil.colorToRgb(4882687)})[this.r.nextInt(23)];
    }

    @Override
    public CompoundTag getNbt() {
        CompoundTag compound = super.getNbt();
        compound.m_128379_("Glint", this.glint);
        compound.m_128379_("UseLidTexture", this.useLidTexture);
        compound.m_128379_("Mirror", this.mirror);
        compound.m_128379_("DisableBlink", this.disableBlink);
        compound.m_128405_("SkinType", this.skinType);
        compound.m_128405_("EyeSize", this.eyeSize);
        compound.m_128405_("SkinColor", ColorUtil.rgbToColor(this.lidColor));
        compound.m_128405_("BrowColor", ColorUtil.rgbToColor(this.browColor));
        compound.m_128405_("PositionX", this.eyePos.x);
        compound.m_128405_("PositionY", this.eyePos.y);
        compound.m_128405_("BrowThickness", (int)(this.browThickness.y * 10.0f));
        return compound;
    }

    @Override
    public void setNbt(CompoundTag compound) {
        super.setNbt(compound);
        this.glint = compound.m_128471_("Glint");
        this.useLidTexture = compound.m_128471_("UseLidTexture");
        this.mirror = compound.m_128471_("Mirror");
        this.disableBlink = compound.m_128471_("DisableBlink");
        this.skinType = compound.m_128451_("SkinType");
        this.eyeSize = compound.m_128451_("EyeSize");
        this.lidColor = ColorUtil.colorToRgb(compound.m_128451_("SkinColor"));
        this.browColor = ColorUtil.colorToRgb(compound.m_128451_("BrowColor"));
        this.eyePos = new NopVector2i(compound.m_128451_("PositionX"), compound.m_128451_("PositionY"));
        this.browThickness = new NopVector3f(1.0f, (float)compound.m_128451_("BrowThickness") / 10.0f, 1.0f);
    }

    public void update(LivingEntity player) {
        if (!player.m_6084_() || this.disableBlink) {
            return;
        }
        if (this.blinkStart < 0L) {
            ++this.blinkStart;
        } else if (this.blinkStart == 0L) {
            if (this.r.nextInt(140) == 1) {
                this.blinkStart = System.currentTimeMillis();
                if (!player.m_9236_().f_46443_) {
                    Packets.sendNearby((Entity)player, new PacketEyeBlink(player.m_19879_()));
                }
            }
        } else if (System.currentTimeMillis() - this.blinkStart > 300L) {
            this.blinkStart = -20L;
        }
    }

    @Override
    public ResourceLocation getUrlTexture() {
        ResourceLocation url = super.getUrlTexture();
        if (url == null) {
            return MissingTextureAtlasSprite.m_118071_();
        }
        return url;
    }
}

