/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package noppes.npcs.client.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcMenu;
import noppes.npcs.entity.EntityNPCInterface;

public abstract class GuiContainerNPCInterface2<T extends AbstractContainerMenu>
extends GuiContainerNPCInterface<T> {
    private ResourceLocation background = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
    private final ResourceLocation defaultBackground = new ResourceLocation("customnpcs", "textures/gui/menubg.png");
    private GuiNpcMenu menu;
    public int menuYOffset = 0;

    public GuiContainerNPCInterface2(EntityNPCInterface npc, T cont, Inventory inv, Component titleIn) {
        this(npc, cont, inv, titleIn, -1);
    }

    public GuiContainerNPCInterface2(EntityNPCInterface npc, T cont, Inventory inv, Component titleIn, int activeMenu) {
        super(npc, cont, inv, titleIn);
        this.f_97726_ = 420;
        this.menu = new GuiNpcMenu(this, activeMenu, npc);
        this.title = "";
    }

    public void setBackground(String texture) {
        this.background = new ResourceLocation("customnpcs", "textures/gui/" + texture);
    }

    @Override
    public ResourceLocation getResource(String texture) {
        return new ResourceLocation("customnpcs", "textures/gui/" + texture);
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.menu.initGui(this.guiLeft, this.guiTop + this.menuYOffset, this.f_97726_);
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        if (!this.hasSubGui()) {
            this.menu.mouseClicked(i, j, k);
        }
        return super.m_6375_(i, j, k);
    }

    public void delete() {
        this.npc.delete();
        this.setScreen(null);
        this.f_96541_.f_91067_.m_91601_();
    }

    @Override
    protected void m_7286_(GuiGraphics graphics, float partialTicks, int x, int y) {
        PoseStack matrixStack = graphics.m_280168_();
        this.m_280273_(graphics);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.background);
        graphics.m_280218_(this.background, this.guiLeft, this.guiTop, 0, 0, 256, 256);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.defaultBackground);
        graphics.m_280218_(this.defaultBackground, this.guiLeft + this.f_97726_ - 200, this.guiTop, 26, 0, 200, 220);
        this.menu.drawElements(graphics, this.f_96547_, x, y, this.f_96541_, partialTicks);
        super.m_7286_(graphics, partialTicks, x, y);
    }
}

