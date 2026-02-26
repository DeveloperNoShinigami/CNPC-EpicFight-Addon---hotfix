/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.gui.player.GuiRecipes;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;

public class GuiNpcCarpentryBench
extends GuiContainerNPCInterface<ContainerCarpentryBench> {
    private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/carpentry.png");
    private ContainerCarpentryBench container;
    private GuiButtonNop button;

    public GuiNpcCarpentryBench(ContainerCarpentryBench container, Inventory inv, Component titleIn) {
        super(null, container, inv, titleIn);
        this.container = container;
        this.title = "";
        this.f_97727_ = 180;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.button = new GuiButtonNop(this, 0, this.guiLeft + 158, this.guiTop + 4, 12, 20, "...");
        this.addButton(this.button);
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        this.setScreen(new GuiRecipes());
    }

    @Override
    protected void m_7286_(GuiGraphics graphics, float partialTicks, int x, int y) {
        this.button.f_93623_ = RecipeController.instance != null && !RecipeController.instance.anvilRecipes.isEmpty();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        int l = (this.f_96543_ - this.f_97726_) / 2;
        int i1 = (this.f_96544_ - this.f_97727_) / 2;
        String title = I18n.m_118938_((String)"block.customnpcs.npccarpentybench", (Object[])new Object[0]);
        graphics.m_280218_(this.resource, l, i1, 0, 0, this.f_97726_, this.f_97727_);
        graphics.m_280056_(this.f_96547_, title, this.guiLeft + 4, this.guiTop + 4, CustomNpcResourceListener.getDefaultTextColor(), false);
        graphics.m_280056_(this.f_96547_, I18n.m_118938_((String)"container.inventory", (Object[])new Object[0]), this.guiLeft + 4, this.guiTop + 87, CustomNpcResourceListener.getDefaultTextColor(), false);
    }

    @Override
    public void save() {
    }
}

