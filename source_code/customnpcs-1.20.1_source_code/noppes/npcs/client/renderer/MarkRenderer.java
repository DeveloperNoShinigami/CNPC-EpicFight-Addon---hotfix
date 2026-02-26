/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraftforge.client.event.RenderLivingEvent$Post
 */
package noppes.npcs.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import noppes.npcs.controllers.data.MarkData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.model.Model2DRenderer;

public class MarkRenderer {
    public static final ResourceLocation markExclamation = new ResourceLocation("customnpcs", "textures/marks/exclamation.png");
    public static final ResourceLocation markQuestion = new ResourceLocation("customnpcs", "textures/marks/question.png");
    public static final ResourceLocation markPointer = new ResourceLocation("customnpcs", "textures/marks/pointer.png");
    public static final ResourceLocation markCross = new ResourceLocation("customnpcs", "textures/marks/cross.png");
    public static final ResourceLocation markSkull = new ResourceLocation("customnpcs", "textures/marks/skull.png");
    public static final ResourceLocation markStar = new ResourceLocation("customnpcs", "textures/marks/star.png");
    public static int displayList = -1;
    public static Model2DRenderer renderer = new Model2DRenderer(32, 32, 0, 0, 32, 32, markExclamation);

    public static void render(RenderLivingEvent.Post event, MarkData.Mark mark) {
        PoseStack matrixStack = event.getPoseStack();
        matrixStack.m_85836_();
        int color = mark.color;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        ResourceLocation location = markExclamation;
        if (mark.type == 1) {
            location = markQuestion;
        } else if (mark.type == 3) {
            location = markPointer;
        } else if (mark.type == 5) {
            location = markCross;
        } else if (mark.type == 4) {
            location = markSkull;
        } else if (mark.type == 6) {
            location = markStar;
        }
        matrixStack.m_85837_(0.0, (double)event.getEntity().m_20206_() + 0.6, 0.0);
        matrixStack.m_252781_(Axis.f_252495_.m_252977_(180.0f));
        matrixStack.m_252781_(Axis.f_252436_.m_252977_(event.getEntity().f_20885_));
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)livingEntity;
            if (npc.display.getSize() > 5) {
                matrixStack.m_85841_(4.0f, 4.0f, 4.0f);
            }
        }
        matrixStack.m_252880_(-0.5f, 0.0f, 0.0f);
        renderer.render(location, matrixStack, event.getMultiBufferSource().m_6299_(RenderType.m_110452_((ResourceLocation)location)), event.getPackedLight(), OverlayTexture.f_118083_, red, green, blue, 1.0f);
        matrixStack.m_85849_();
    }
}

