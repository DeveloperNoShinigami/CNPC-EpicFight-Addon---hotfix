/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.blaze3d.vertex.VertexFormatElement
 *  net.minecraft.Util
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.RenderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$ShaderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TransparencyStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.resources.ResourceLocation
 *  org.joml.Vector4f
 */
package noppes.npcs.shared.client.model.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.util.HashMap;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector4f;

public class CustomRenderStates
extends RenderStateShard {
    public static final Vector4f WHITE = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    public static VertexFormat POS_COL_TEX_LIGHT_FADE_NORMAL;
    public static VertexFormat POS_COL_TEX_NORMAL;
    public static final VertexFormat POS_TEX_NORMAL;
    protected static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY;
    protected static final RenderStateShard.TransparencyStateShard SUBTRACTIVE_TRANSPARENCY;
    private static final RenderType[] OBJ_RENDER_TYPES;
    public static final RenderType OBJ_OUTLINE_RENDER_TYPE;
    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_SHADER;
    public static ShaderInstance posTexNormalShader;
    private static final Function<ResourceLocation, RenderType> ENTITY_CUTOUT;

    public CustomRenderStates(String p_i225973_1_, Runnable p_i225973_2_, Runnable p_i225973_3_) {
        super(p_i225973_1_, p_i225973_2_, p_i225973_3_);
    }

    public static RenderType getObjVBORenderType(int blending, boolean glow) {
        return OBJ_RENDER_TYPES[blending << 1 | (glow ? 1 : 0)];
    }

    public static RenderType entityCutout(ResourceLocation p_110444_) {
        return ENTITY_CUTOUT.apply(p_110444_);
    }

    public static RenderType getObjRenderType(ResourceLocation texture, int blending, boolean glow) {
        if (POS_COL_TEX_LIGHT_FADE_NORMAL == null) {
            HashMap<String, VertexFormatElement> vertexFormatValues = new HashMap<String, VertexFormatElement>();
            vertexFormatValues.put("Position", DefaultVertexFormat.f_85804_);
            vertexFormatValues.put("Color", DefaultVertexFormat.f_85805_);
            vertexFormatValues.put("UV0", DefaultVertexFormat.f_85806_);
            vertexFormatValues.put("UV1", DefaultVertexFormat.f_85807_);
            vertexFormatValues.put("UV2", DefaultVertexFormat.f_85808_);
            vertexFormatValues.put("Normal", DefaultVertexFormat.f_85809_);
            vertexFormatValues.put("Padding", DefaultVertexFormat.f_85810_);
            POS_COL_TEX_LIGHT_FADE_NORMAL = new VertexFormat(ImmutableMap.copyOf(vertexFormatValues));
        }
        RenderStateShard.TransparencyStateShard TransparencyStateShard2 = f_110139_;
        if (blending == BLEND.ADD.getValue()) {
            TransparencyStateShard2 = ADDITIVE_TRANSPARENCY;
        } else if (blending == BLEND.SUB.getValue()) {
            TransparencyStateShard2 = SUBTRACTIVE_TRANSPARENCY;
        }
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.m_110628_().m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(texture, false, false)).m_110685_(TransparencyStateShard2).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
        return RenderType.m_173215_((String)"lm_obj_translucent_no_cull", (VertexFormat)POS_COL_TEX_LIGHT_FADE_NORMAL, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)renderTypeState);
    }

    public static RenderType getObjColorOnlyRenderType(ResourceLocation texture, int blending, boolean glow) {
        if (POS_COL_TEX_LIGHT_FADE_NORMAL == null) {
            HashMap<String, VertexFormatElement> vertexFormatValues = new HashMap<String, VertexFormatElement>();
            vertexFormatValues.put("Position", DefaultVertexFormat.f_85804_);
            vertexFormatValues.put("Color", DefaultVertexFormat.f_85805_);
            vertexFormatValues.put("Normal", DefaultVertexFormat.f_85809_);
            vertexFormatValues.put("Padding", DefaultVertexFormat.f_85810_);
            POS_COL_TEX_LIGHT_FADE_NORMAL = new VertexFormat(ImmutableMap.copyOf(vertexFormatValues));
        }
        RenderStateShard.TransparencyStateShard TransparencyStateShard2 = f_110139_;
        if (blending == BLEND.ADD.getValue()) {
            TransparencyStateShard2 = ADDITIVE_TRANSPARENCY;
        } else if (blending == BLEND.SUB.getValue()) {
            TransparencyStateShard2 = SUBTRACTIVE_TRANSPARENCY;
        }
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.m_110628_().m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(texture, false, false)).m_110685_(TransparencyStateShard2).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
        return RenderType.m_173215_((String)"lm_obj_translucent_no_cull", (VertexFormat)POS_COL_TEX_LIGHT_FADE_NORMAL, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)renderTypeState);
    }

    public static RenderType getObjOutlineRenderType(ResourceLocation texture) {
        if (POS_COL_TEX_LIGHT_FADE_NORMAL == null) {
            HashMap<String, VertexFormatElement> vertexFormatValues = new HashMap<String, VertexFormatElement>();
            vertexFormatValues.put("Position", DefaultVertexFormat.f_85804_);
            vertexFormatValues.put("Color", DefaultVertexFormat.f_85805_);
            vertexFormatValues.put("UV0", DefaultVertexFormat.f_85806_);
            vertexFormatValues.put("UV1", DefaultVertexFormat.f_85807_);
            vertexFormatValues.put("UV2", DefaultVertexFormat.f_85808_);
            vertexFormatValues.put("Normal", DefaultVertexFormat.f_85809_);
            vertexFormatValues.put("Padding", DefaultVertexFormat.f_85810_);
            POS_COL_TEX_LIGHT_FADE_NORMAL = new VertexFormat(ImmutableMap.copyOf(vertexFormatValues));
        }
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.m_110628_().m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(texture, false, false)).m_110661_(f_110110_).m_110663_(f_110111_).m_110675_(f_110124_).m_110691_(false);
        return RenderType.m_173215_((String)"lm_obj_outline_no_cull", (VertexFormat)POS_COL_TEX_LIGHT_FADE_NORMAL, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)renderTypeState);
    }

    public static RenderType getSpriteRenderType(ResourceLocation texture) {
        if (POS_COL_TEX_NORMAL == null) {
            HashMap<String, VertexFormatElement> vertexFormatValues = new HashMap<String, VertexFormatElement>();
            vertexFormatValues.put("Position", DefaultVertexFormat.f_85804_);
            vertexFormatValues.put("Color", DefaultVertexFormat.f_85805_);
            vertexFormatValues.put("UV0", DefaultVertexFormat.f_85806_);
            vertexFormatValues.put("Normal", DefaultVertexFormat.f_85809_);
            vertexFormatValues.put("Padding", DefaultVertexFormat.f_85810_);
            POS_COL_TEX_NORMAL = new VertexFormat(ImmutableMap.copyOf(vertexFormatValues));
        }
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.m_110628_().m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(texture, false, false)).m_110691_(true);
        return RenderType.m_173215_((String)"lm_sprite", (VertexFormat)POS_COL_TEX_NORMAL, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)renderTypeState);
    }

    static {
        POS_TEX_NORMAL = new VertexFormat(ImmutableMap.of((Object)"Position", (Object)DefaultVertexFormat.f_85804_, (Object)"UV0", (Object)DefaultVertexFormat.f_85806_, (Object)"Normal", (Object)DefaultVertexFormat.f_85809_, (Object)"Padding", (Object)DefaultVertexFormat.f_85810_));
        ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("lm_additive_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        });
        SUBTRACTIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("lm_subtractive_transparency", () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.DST_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        });
        OBJ_RENDER_TYPES = new RenderType[BLEND.values().length * 2];
        for (BLEND blend : BLEND.values()) {
            for (int glow = 0; glow < 2; ++glow) {
                CustomRenderStates.OBJ_RENDER_TYPES[blend.id * 2 + glow] = RenderType.m_173215_((String)("lm_obj_" + blend.toString() + (glow == 1 ? "_glow" : "")), (VertexFormat)POS_TEX_NORMAL, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_110685_(blend == BLEND.ADD ? ADDITIVE_TRANSPARENCY : (blend == BLEND.SUB ? SUBTRACTIVE_TRANSPARENCY : f_110139_)).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(false));
            }
        }
        OBJ_OUTLINE_RENDER_TYPE = RenderType.m_173215_((String)"lm_obj_outline_no_cull", (VertexFormat)POS_TEX_NORMAL, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_110663_(f_110111_).m_110661_(f_110110_).m_110675_(f_110124_).m_110691_(false));
        RENDERTYPE_ENTITY_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::m_172664_);
        posTexNormalShader = null;
        ENTITY_CUTOUT = Util.m_143827_(p_173202_ -> {
            RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_().m_173292_(new RenderStateShard.ShaderStateShard(() -> posTexNormalShader)).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(p_173202_, false, false)).m_110685_(f_110134_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
            return RenderType.m_173215_((String)"nop_entity_cutout", (VertexFormat)POS_TEX_NORMAL, (VertexFormat.Mode)VertexFormat.Mode.TRIANGLES, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)rendertype$compositestate);
        });
    }

    public static enum BLEND {
        NORMAL(0),
        ADD(1),
        SUB(2);

        public final int id;

        private BLEND(int value) {
            this.id = value;
        }

        public int getValue() {
            return this.id;
        }
    }
}

