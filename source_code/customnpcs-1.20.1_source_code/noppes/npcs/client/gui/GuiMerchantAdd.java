/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiWrapper;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

@OnlyIn(value=Dist.CLIENT)
public class GuiMerchantAdd
extends AbstractContainerScreen<ContainerManageRecipes>
implements IGuiInterface {
    public GuiMerchantAdd(ContainerManageRecipes container, Inventory inv, Component titleIn) {
        super((AbstractContainerMenu)container, inv, titleIn);
    }

    protected void m_7286_(GuiGraphics p_283065_, float p_97788_, int p_97789_, int p_97790_) {
    }

    @Override
    public void buttonEvent(GuiButtonNop button) {
    }

    @Override
    public void save() {
    }

    @Override
    public boolean hasSubGui() {
        return false;
    }

    @Override
    public Screen getSubGui() {
        return null;
    }

    @Override
    public int getWidth() {
        return this.f_96543_;
    }

    @Override
    public int getHeight() {
        return this.f_96544_;
    }

    @Override
    public Screen getParent() {
        return null;
    }

    @Override
    public void elementClicked() {
    }

    @Override
    public void subGuiClosed(Screen subgui) {
    }

    @Override
    public GuiWrapper getWrapper() {
        return null;
    }

    @Override
    public void initGui() {
        this.m_7856_();
    }
}

