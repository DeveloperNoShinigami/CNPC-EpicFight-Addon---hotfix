/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiEntityDisplayWrapper;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;

public class CustomGuiEntityDisplay
extends AbstractWidget
implements IGuiComponent {
    private GuiCustom parent;
    public CustomGuiEntityDisplayWrapper component;
    private Entity entity;
    public int id;

    public CustomGuiEntityDisplay(GuiCustom parent, CustomGuiEntityDisplayWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getWidth(), component.getHeight(), (Component)Component.m_237119_());
        this.component = component;
        this.parent = parent;
        this.init();
    }

    public void init() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.m_93674_(this.component.getWidth());
        this.setHeight(this.component.getHeight());
        if (this.component.entityId != -1) {
            this.entity = Minecraft.m_91087_().f_91074_.m_20193_().m_6815_(this.component.entityId);
        } else if (!this.component.getEntityData().isEmpty()) {
            this.entity = EntityType.m_20642_((CompoundTag)this.component.getEntityData().getMCNBT(), (Level)Minecraft.m_91087_().f_91073_).orElse(null);
        }
        this.f_93623_ = this.component.getEnabled() && this.component.getVisible();
        this.f_93624_ = this.component.getVisible();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        boolean hovered;
        if (!this.f_93624_) {
            return;
        }
        if (this.component.getBackground()) {
            graphics.m_280024_(this.m_252754_(), this.m_252907_(), this.f_93618_ + this.m_252754_(), this.f_93619_ + this.m_252907_(), -1072689136, -804253680);
        }
        if (this.entity != null) {
            CustomGuiEntityDisplay.drawEntity(graphics, this.entity, this.m_252754_(), this.m_252907_(), this.component.getScale(), this.component.getRotation() / 2 + 180, mouseX, mouseY, (float)this.f_93618_ / 2.0f, (float)this.f_93619_ * 0.9f, this.component.isFollowingCursor);
        }
        boolean bl = hovered = mouseX >= this.m_252754_() && mouseY >= this.m_252907_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY < this.m_252907_() + this.f_93619_;
        if (hovered && this.component.hasHoverText()) {
            this.parent.hoverText = this.component.getHoverTextList();
        }
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    public boolean m_7979_(double mouseX, double mouseY, int mouseButton, double dx, double dy) {
        return true;
    }

    public static CustomGuiEntityDisplay fromComponent(GuiCustom parent, CustomGuiEntityDisplayWrapper component) {
        CustomGuiEntityDisplay btn = new CustomGuiEntityDisplay(parent, component);
        return btn;
    }

    public static void drawEntity(GuiGraphics graphics, Entity entity, int x, int y, float zoomed, int rotation, int xMouse, int yMouse, float guiLeft, float guiTop) {
        CustomGuiEntityDisplay.drawEntity(graphics, entity, x, y, zoomed, rotation, xMouse, yMouse, guiLeft, guiTop, true);
    }

    public static void drawEntity(GuiGraphics graphics, Entity entity, int x, int y, float zoomed, int rotation, int xMouse, int yMouse, float guiLeft, float guiTop, boolean followCursor) {
        EntityCustomNpc cnpc;
        EntityCustomNpc cnpc2;
        EntityNPCInterface npc = null;
        if (entity instanceof EntityNPCInterface) {
            npc = (EntityNPCInterface)entity;
        }
        LivingEntity livingEntity = null;
        if (entity instanceof LivingEntity) {
            livingEntity = (LivingEntity)entity;
        }
        float f3 = entity.m_146908_();
        float f4 = entity.m_146909_();
        float f2 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        if (livingEntity != null) {
            f2 = livingEntity.f_20883_;
            f5 = livingEntity.f_20886_;
            f6 = livingEntity.f_20885_;
        }
        float scale = 1.0f;
        if ((double)entity.m_20206_() > 2.4) {
            scale = 2.0f / entity.m_20206_();
        }
        float f7 = guiLeft + (float)x - (float)xMouse;
        float f8 = (guiTop + (float)y - 50.0f * scale * zoomed) * (entity.m_20206_() / entity.m_20192_()) - (float)yMouse;
        if (followCursor) {
            entity.m_146922_((float)Math.atan(f7 / 80.0f) * 40.0f + (float)rotation);
            entity.m_146926_(-((float)Math.atan(f8 / 40.0f)) * 20.0f);
        } else {
            entity.m_146922_((float)rotation);
            entity.m_146926_(0.0f);
        }
        if (livingEntity != null) {
            livingEntity.f_20885_ = livingEntity.f_20883_ = entity.m_146908_();
            livingEntity.f_20886_ = livingEntity.f_20883_;
        }
        int orientation = 0;
        int showname = 0;
        if (npc != null) {
            orientation = npc.ais.orientation;
            npc.ais.orientation = (int)entity.m_146908_();
            showname = npc.display.getShowName();
            npc.display.setShowName(1);
        }
        if (npc instanceof EntityCustomNpc && cnpc2.modelData.getEntity(cnpc2 = (EntityCustomNpc)npc) != null) {
            EntityUtil.Copy((LivingEntity)npc, cnpc2.modelData.getEntity(cnpc2));
        }
        float fs = 30.0f * scale * zoomed;
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.m_252880_(0.0f, 0.0f, 1050.0f);
        posestack.m_85841_(1.0f, 1.0f, -1.0f);
        RenderSystem.applyModelViewMatrix();
        PoseStack matrixStack = new PoseStack();
        matrixStack.m_252880_(guiLeft + (float)x, guiTop + (float)y, 0.0f);
        matrixStack.m_85841_(fs, fs, fs);
        matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0f));
        matrixStack.m_252781_(Axis.f_252403_.m_252977_(180.0f));
        matrixStack.m_252781_(Axis.f_252392_.m_252977_((float)rotation));
        Lighting.m_166384_();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.m_91087_().m_91290_();
        entityrenderdispatcher.m_114468_(false);
        RenderSystem.runAsFancy(() -> entityrenderdispatcher.m_114384_(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, matrixStack, (MultiBufferSource)graphics.m_280091_(), 0xF000F0));
        graphics.m_280262_();
        entityrenderdispatcher.m_114468_(true);
        posestack.m_85841_(1.0f, 1.0f, -1.0f);
        posestack.m_252880_(0.0f, 0.0f, -1050.0f);
        RenderSystem.applyModelViewMatrix();
        Lighting.m_84931_();
        matrixStack.m_85849_();
        entity.m_146922_(f3);
        entity.m_146926_(f4);
        if (livingEntity != null) {
            livingEntity.f_20883_ = f2;
            livingEntity.f_20886_ = f5;
            livingEntity.f_20885_ = f6;
        }
        if (npc != null) {
            npc.ais.orientation = orientation;
            npc.display.setShowName(showname);
        }
        if (npc instanceof EntityCustomNpc && cnpc.modelData.getEntity(cnpc = (EntityCustomNpc)npc) != null) {
            EntityUtil.Copy((LivingEntity)npc, cnpc.modelData.getEntity(cnpc));
        }
    }

    public void m_168797_(NarrationElementOutput p_169152_) {
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }
}

