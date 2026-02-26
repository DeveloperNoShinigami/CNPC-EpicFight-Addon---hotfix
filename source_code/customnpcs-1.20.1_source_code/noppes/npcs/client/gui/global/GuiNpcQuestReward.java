/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.global;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerNpcQuestReward;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiNpcQuestReward
extends GuiContainerNPCInterface<ContainerNpcQuestReward>
implements ITextfieldListener {
    private Quest quest;
    private ResourceLocation resource;

    public GuiNpcQuestReward(ContainerNpcQuestReward container, Inventory inv, Component titleIn) {
        super(NoppesUtil.getLastNpc(), container, inv, titleIn);
        this.quest = NoppesUtilServer.getEditingQuest((Player)this.player);
        this.resource = this.getResource("questreward.png");
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(0, "quest.randomitem", this.guiLeft + 4, this.guiTop + 4));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 4, this.guiTop + 14, 60, 20, new String[]{"gui.no", "gui.yes"}, this.quest.randomReward ? 1 : 0));
        this.addButton(new GuiButtonNop(this, 5, this.guiLeft, this.guiTop + this.f_97727_, 98, 20, "gui.back"));
        this.addLabel(new GuiLabel(1, "quest.exp", this.guiLeft + 4, this.guiTop + 45));
        this.addTextField(new GuiTextFieldNop(0, (Screen)this, this.guiLeft + 4, this.guiTop + 55, 60, 20, "" + this.quest.rewardExp));
        this.getTextField((int)0).numbersOnly = true;
        this.getTextField(0).setMinMaxDefault(0, 99999, 0);
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 5) {
            NoppesUtil.openGUI((Player)this.player, GuiNPCManageQuest.Instance);
        }
        if (id == 0) {
            this.quest.randomReward = guibutton.getValue() == 1;
        }
    }

    @Override
    protected void m_7286_(GuiGraphics graphics, float partialTicks, int x, int y) {
        super.m_7286_(graphics, partialTicks, x, y);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        int l = (this.f_96543_ - this.f_97726_) / 2;
        int i1 = (this.f_96544_ - this.f_97727_) / 2;
        graphics.m_280218_(this.resource, l, i1, 0, 0, this.f_97726_, this.f_97727_);
    }

    @Override
    public void save() {
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        this.quest.rewardExp = textfield.getInteger();
    }
}

