/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.DefaultPlayerSkin
 *  net.minecraft.core.UUIDUtil
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix4f
 */
package noppes.npcs.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.io.File;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.MatrixStackMixin;
import noppes.npcs.shared.client.util.ImageDownloadAlt;
import noppes.npcs.shared.client.util.ResourceDownloader;
import noppes.npcs.shared.common.util.LogWriter;
import org.joml.Matrix4f;

public class RenderNPCInterface<T extends EntityNPCInterface, M extends EntityModel<T>>
extends LivingEntityRenderer<T, M> {
    public static int LastTextureTick;
    public static EntityNPCInterface currentNpc;

    public RenderNPCInterface(EntityRendererProvider.Context manager, M model, float f) {
        super(manager, model, f);
    }

    public void renderNameTag(T npc, Component text, PoseStack matrixStack, MultiBufferSource buffer, int light) {
        if (npc == null || !this.m_6512_((LivingEntity)npc) || this.f_114476_ == null) {
            return;
        }
        double d0 = this.f_114476_.m_114471_(npc);
        if (d0 > 512.0) {
            return;
        }
        matrixStack.m_85836_();
        Vec3 renderOffset = this.m_7860_((Entity)npc, 0.0f);
        matrixStack.m_85837_(-renderOffset.m_7096_(), -renderOffset.m_7098_(), -renderOffset.m_7094_());
        if (((EntityNPCInterface)((Object)npc)).messages != null) {
            float height = ((EntityNPCInterface)((Object)npc)).baseSize.f_20378_ / 5.0f * (float)((EntityNPCInterface)((Object)npc)).display.getSize();
            float offset = npc.m_20206_() * (1.2f + (!((EntityNPCInterface)((Object)npc)).display.showName() ? 0.0f : (((EntityNPCInterface)((Object)npc)).display.getTitle().isEmpty() ? 0.15f : 0.25f)));
            matrixStack.m_252880_(0.0f, offset, 0.0f);
            ((EntityNPCInterface)((Object)npc)).messages.renderMessages(matrixStack, buffer, 0.666667f * height, ((EntityNPCInterface)((Object)npc)).isInRange(this.f_114476_.f_114358_.m_90592_(), 4.0), light);
            matrixStack.m_252880_(0.0f, -offset, 0.0f);
        }
        if (((EntityNPCInterface)((Object)npc)).display.showName()) {
            this.renderLivingLabel(npc, matrixStack, buffer, light);
        }
        matrixStack.m_85849_();
    }

    protected void renderLivingLabel(T npc, PoseStack matrixStack, MultiBufferSource buffer, int light) {
        float scale = ((EntityNPCInterface)((Object)npc)).baseSize.f_20378_ / 5.0f * (float)((EntityNPCInterface)((Object)npc)).display.getSize();
        float height = npc.m_20206_() - 0.06f * scale;
        matrixStack.m_85836_();
        Font fontrenderer = this.m_114481_();
        float f2 = 0.01666667f * scale;
        matrixStack.m_252880_(0.0f, height, 0.0f);
        matrixStack.m_252781_(this.f_114476_.m_253208_());
        int color = ((EntityNPCInterface)((Object)npc)).getFaction().color;
        matrixStack.m_252880_(0.0f, scale / 6.5f * 2.0f, 0.0f);
        float f1 = Minecraft.m_91087_().f_91066_.m_92141_(0.25f);
        int j = (int)(f1 * 255.0f) << 24;
        matrixStack.m_85841_(-f2, -f2, f2);
        Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
        float y = 0.0f;
        boolean nearby = ((EntityNPCInterface)((Object)npc)).isInRange(this.f_114476_.f_114358_.m_90592_(), 8.0);
        if (!((EntityNPCInterface)((Object)npc)).display.getTitle().isEmpty() && nearby) {
            MutableComponent title = Component.m_237113_((String)"<").m_7220_((Component)Component.m_237115_((String)((EntityNPCInterface)((Object)npc)).display.getTitle())).m_130946_(">");
            float f3 = 0.6f;
            matrixStack.m_252880_(0.0f, 4.0f, 0.0f);
            matrixStack.m_85841_(f3, f3, f3);
            fontrenderer.m_272077_((Component)title, (float)(-fontrenderer.m_92852_((FormattedText)title) / 2), 0.0f, color, false, matrix4f, buffer, Font.DisplayMode.NORMAL, j, light);
            matrixStack.m_85841_(1.0f / f3, 1.0f / f3, 1.0f / f3);
            y = -10.0f;
        }
        Component name = ((EntityNPCInterface)((Object)npc)).m_7755_();
        fontrenderer.m_272077_(name, (float)(-fontrenderer.m_92852_((FormattedText)name) / 2), y, color, false, matrix4f, buffer, nearby ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, j, light);
        if (nearby) {
            fontrenderer.m_272077_(name, (float)(-fontrenderer.m_92852_((FormattedText)name) / 2), y, color, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, light);
        }
        matrixStack.m_85849_();
    }

    protected void renderColor(EntityNPCInterface npc) {
        if (npc.f_20916_ <= 0 && npc.f_20919_ <= 0) {
            float red = (float)(npc.display.getTint() >> 16 & 0xFF) / 255.0f;
            float green = (float)(npc.display.getTint() >> 8 & 0xFF) / 255.0f;
            float blue = (float)(npc.display.getTint() & 0xFF) / 255.0f;
            RenderSystem.setShaderColor((float)red, (float)green, (float)blue, (float)1.0f);
        }
    }

    protected void setupRotations(T npc, PoseStack matrixScale, float f, float f1, float f2) {
        if (((EntityNPCInterface)((Object)npc)).m_6084_() && ((EntityNPCInterface)((Object)npc)).m_5803_()) {
            matrixScale.m_252781_(Axis.f_252436_.m_252977_((float)((EntityNPCInterface)((Object)npc)).ais.orientation));
            matrixScale.m_252781_(Axis.f_252403_.m_252977_(this.m_6441_((LivingEntity)npc)));
            matrixScale.m_252781_(Axis.f_252436_.m_252977_(270.0f));
        } else if (((EntityNPCInterface)((Object)npc)).m_6084_() && ((EntityNPCInterface)((Object)npc)).currentAnimation == 7) {
            matrixScale.m_252781_(Axis.f_252436_.m_252977_(270.0f - f1));
            float scale = (float)((EntityCustomNpc)((Object)npc)).display.getSize() / 5.0f;
            matrixScale.m_252880_(-scale + ((EntityCustomNpc)((Object)npc)).modelData.getLegsY() * scale, 0.14f, 0.0f);
            matrixScale.m_252781_(Axis.f_252403_.m_252977_(270.0f));
            matrixScale.m_252781_(Axis.f_252436_.m_252977_(270.0f));
        } else {
            super.m_7523_(npc, matrixScale, f, f1, f2);
        }
    }

    protected void scale(T npc, PoseStack matrixScale, float f) {
        this.renderColor((EntityNPCInterface)((Object)npc));
        int size = ((EntityNPCInterface)((Object)npc)).display.getSize();
        matrixScale.m_85841_(((EntityNPCInterface)((Object)npc)).scaleX / 5.0f * (float)size, ((EntityNPCInterface)((Object)npc)).scaleY / 5.0f * (float)size, ((EntityNPCInterface)((Object)npc)).scaleZ / 5.0f * (float)size);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void render(T npc, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        if (((EntityNPCInterface)((Object)npc)).isKilled()) {
            this.f_114477_ = 0.0f;
        }
        if (((EntityNPCInterface)((Object)npc)).isKilled() && ((EntityNPCInterface)((Object)npc)).stats.hideKilledBody && ((EntityNPCInterface)((Object)npc)).f_20919_ > 20) {
            return;
        }
        float xOffset = 0.0f;
        float yOffset = ((EntityNPCInterface)((Object)npc)).currentAnimation == 0 ? ((EntityNPCInterface)((Object)npc)).ais.bodyOffsetY / 10.0f - 0.5f : 0.0f;
        float zOffset = 0.0f;
        if (((EntityNPCInterface)((Object)npc)).m_6084_()) {
            if (((EntityNPCInterface)((Object)npc)).m_5803_()) {
                xOffset = (float)(-Math.cos(Math.toRadians(180 - ((EntityNPCInterface)((Object)npc)).ais.orientation)));
                zOffset = (float)(-Math.sin(Math.toRadians(((EntityNPCInterface)((Object)npc)).ais.orientation)));
                yOffset += 0.14f;
            } else if (((EntityNPCInterface)((Object)npc)).currentAnimation == 1 || npc.m_20159_()) {
                yOffset -= 0.5f - ((EntityCustomNpc)((Object)npc)).modelData.getLegsY() * 0.8f;
            }
        }
        xOffset = xOffset / 5.0f * (float)((EntityNPCInterface)((Object)npc)).display.getSize();
        yOffset = yOffset / 5.0f * (float)((EntityNPCInterface)((Object)npc)).display.getSize();
        zOffset = zOffset / 5.0f * (float)((EntityNPCInterface)((Object)npc)).display.getSize();
        if (((EntityNPCInterface)((Object)npc)).display.getBossbar() != 1 && (((EntityNPCInterface)((Object)npc)).display.getBossbar() != 2 || !((EntityNPCInterface)((Object)npc)).isAttacking()) || ((EntityNPCInterface)((Object)npc)).isKilled() || ((EntityNPCInterface)((Object)npc)).f_20919_ > 20 || ((EntityNPCInterface)((Object)npc)).canNpcSee((Entity)Minecraft.m_91087_().f_91074_)) {
            // empty if block
        }
        if (((EntityNPCInterface)((Object)npc)).ais.getStandingType() == 3 && !((EntityNPCInterface)((Object)npc)).isWalking() && !((EntityNPCInterface)((Object)npc)).isInteracting()) {
            ((EntityNPCInterface)((Object)npc)).f_20884_ = ((EntityNPCInterface)((Object)npc)).f_20883_ = (float)((EntityNPCInterface)((Object)npc)).ais.orientation;
        }
        this.f_114477_ = npc.m_20205_() * 0.8f;
        int stackSize = ((MatrixStackMixin)matrixStack).getStack().size();
        try {
            currentNpc = npc;
            super.m_7392_(npc, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        }
        catch (Throwable e) {
            while (((MatrixStackMixin)matrixStack).getStack().size() > stackSize) {
                matrixStack.m_85849_();
            }
            LogWriter.except(e);
        }
        finally {
            currentNpc = null;
        }
    }

    protected float getBob(T npc, float limbSwingAmount) {
        if (((EntityNPCInterface)((Object)npc)).isKilled() || !((EntityNPCInterface)((Object)npc)).display.getHasLivingAnimation()) {
            return 0.0f;
        }
        return super.m_6930_(npc, limbSwingAmount);
    }

    public ResourceLocation getTextureLocation(T npc) {
        if (((EntityNPCInterface)((Object)npc)).textureLocation == null) {
            if (((EntityNPCInterface)((Object)npc)).display.skinType == 0) {
                ((EntityNPCInterface)((Object)npc)).textureLocation = new ResourceLocation(((EntityNPCInterface)((Object)npc)).display.getSkinTexture());
            } else {
                if (LastTextureTick < 5) {
                    return DefaultPlayerSkin.m_118626_();
                }
                if (((EntityNPCInterface)((Object)npc)).display.skinType == 1 && ((EntityNPCInterface)((Object)npc)).display.playerProfile != null) {
                    Minecraft minecraft = Minecraft.m_91087_();
                    Map map = minecraft.m_91109_().m_118815_(((EntityNPCInterface)((Object)npc)).display.playerProfile);
                    ((EntityNPCInterface)((Object)npc)).textureLocation = map.containsKey(MinecraftProfileTexture.Type.SKIN) ? minecraft.m_91109_().m_118825_((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN) : DefaultPlayerSkin.m_118627_((UUID)UUIDUtil.m_235875_((GameProfile)((EntityNPCInterface)((Object)npc)).display.playerProfile));
                } else if (((EntityNPCInterface)((Object)npc)).display.skinType == 2 && !((EntityNPCInterface)((Object)npc)).display.getSkinUrl().isEmpty()) {
                    try {
                        boolean fixSkin = npc instanceof EntityCustomNpc && ((EntityCustomNpc)((Object)npc)).modelData.getEntity((EntityNPCInterface)((Object)npc)) == null;
                        File file = ResourceDownloader.getUrlFile(((EntityNPCInterface)((Object)npc)).display.getSkinUrl(), fixSkin);
                        ((EntityNPCInterface)((Object)npc)).textureLocation = ResourceDownloader.getUrlResourceLocation(((EntityNPCInterface)((Object)npc)).display.getSkinUrl(), fixSkin);
                        this.loadSkin(file, ((EntityNPCInterface)((Object)npc)).textureLocation, ((EntityNPCInterface)((Object)npc)).display.getSkinUrl(), fixSkin);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        if (((EntityNPCInterface)((Object)npc)).textureLocation == null) {
            return DefaultPlayerSkin.m_118626_();
        }
        return ((EntityNPCInterface)((Object)npc)).textureLocation;
    }

    private void loadSkin(File file, ResourceLocation resource, String par1Str, boolean fix64) {
        TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
        AbstractTexture object = texturemanager.m_174786_(resource, null);
        if (object == null) {
            ResourceDownloader.load(new ImageDownloadAlt(file, par1Str, resource, DefaultPlayerSkin.m_118626_(), fix64, () -> {}));
        }
    }
}

