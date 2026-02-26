/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.RenderStateShard$DepthTestStateShard
 *  net.minecraft.client.renderer.RenderStateShard$ShaderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TransparencyStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.client.gui.custom.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.wrapper.gui.CustomGuiColoredLineWrapper;
import noppes.npcs.client.gui.custom.GuiCustom;
import noppes.npcs.client.gui.custom.interfaces.IGuiComponent;

public class CustomGuiColoredLine
extends AbstractWidget
implements IGuiComponent {
    private static final RenderStateShard.ShaderStateShard RENDERTYPE_GUI_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::m_172811_);
    private static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.lineWidth((float)10.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.lineWidth((float)1.0f);
        RenderSystem.defaultBlendFunc();
    });
    private static final RenderStateShard.DepthTestStateShard LEQUAL_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("<=", 515);
    private static final RenderType type = RenderType.m_173215_((String)"gui", (VertexFormat)DefaultVertexFormat.f_85815_, (VertexFormat.Mode)VertexFormat.Mode.LINES, (int)256, (boolean)false, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173292_(RENDERTYPE_GUI_SHADER).m_110685_(TRANSLUCENT_TRANSPARENCY).m_110663_(LEQUAL_DEPTH_TEST).m_110691_(false));
    private GuiCustom parent;
    public CustomGuiColoredLineWrapper component;
    public int id;

    public CustomGuiColoredLine(GuiCustom parent, CustomGuiColoredLineWrapper component) {
        super(component.getPosX(), component.getPosY(), component.getXEnd() - component.getPosX(), component.getYEnd() - component.getPosY(), (Component)Component.m_237119_());
        this.component = component;
        this.parent = parent;
        this.init();
    }

    public void init() {
        this.id = this.component.getID();
        this.m_252865_(this.component.getPosX());
        this.m_253211_(this.component.getPosY());
        this.m_93674_(this.component.getXEnd() - this.component.getPosX());
        this.setHeight(this.component.getYEnd() - this.component.getPosY());
        this.f_93623_ = true;
        this.f_93624_ = true;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void onRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.f_93624_) {
            int color = this.component.getColor();
            int r = color >> 24 & 0xFF;
            int g = color >> 16 & 0xFF;
            int b = color >> 8 & 0xFF;
            int a = color & 0xFF;
            double dx = this.component.getXEnd() - this.m_252754_();
            double dy = this.component.getYEnd() - this.m_252907_();
            double length = Math.sqrt(dx * dx + dy * dy);
            double nx = -dy / length * (double)this.component.getThickness() / 2.0;
            double ny = dx / length * (double)this.component.getThickness() / 2.0;
            VertexConsumer builder = graphics.m_280091_().m_6299_(RenderType.m_285907_());
            builder.m_5483_((double)this.component.getXEnd() + nx, (double)this.component.getYEnd() + ny, (double)((float)this.id * 0.01f)).m_6122_(r, g, b, a).m_5752_();
            builder.m_5483_((double)this.component.getXEnd() - nx, (double)this.component.getYEnd() - ny, (double)((float)this.id * 0.01f)).m_6122_(r, g, b, a).m_5752_();
            builder.m_5483_((double)this.m_252754_() - nx, (double)this.m_252907_() - ny, (double)((float)this.id * 0.01f)).m_6122_(r, g, b, a).m_5752_();
            builder.m_5483_((double)this.m_252754_() + nx, (double)this.m_252907_() + ny, (double)((float)this.id * 0.01f)).m_6122_(r, g, b, a).m_5752_();
            graphics.m_280262_();
        }
    }

    protected int getYImage(boolean p_getYImage_1_) {
        return 0;
    }

    public boolean m_7979_(double mouseX, double mouseY, int mouseButton, double dx, double dy) {
        return true;
    }

    public static CustomGuiColoredLine fromComponent(GuiCustom parent, CustomGuiColoredLineWrapper component) {
        CustomGuiColoredLine line = new CustomGuiColoredLine(parent, component);
        return line;
    }

    @Override
    public ICustomGuiComponent component() {
        return this.component;
    }

    protected void m_87963_(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {
    }

    public void m_168797_(NarrationElementOutput p_169152_) {
    }
}

