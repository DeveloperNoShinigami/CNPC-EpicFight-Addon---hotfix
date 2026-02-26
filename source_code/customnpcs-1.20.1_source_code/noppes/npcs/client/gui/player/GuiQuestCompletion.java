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
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import noppes.npcs.api.handler.data.IQuest;
import noppes.npcs.client.CustomNpcResourceListener;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketQuestCompletionCheck;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.listeners.ITopButtonListener;

public class GuiQuestCompletion
extends GuiNPCInterface
implements ITopButtonListener {
    private IQuest quest;
    private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/smallbg.png");

    public GuiQuestCompletion(IQuest quest) {
        this.imageWidth = 176;
        this.imageHeight = 222;
        this.quest = quest;
        this.drawDefaultBackground = false;
        this.title = "";
        this.closeOnEsc = false;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        String questTitle = I18n.m_118938_((String)this.quest.getName(), (Object[])new Object[0]);
        int left = (this.imageWidth - this.f_96547_.m_92895_(questTitle)) / 2;
        this.addLabel(new GuiLabel(0, questTitle, this.guiLeft + left, this.guiTop + 4));
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 38, this.guiTop + this.imageHeight - 24, 100, 20, I18n.m_118938_((String)"quest.complete", (Object[])new Object[0])));
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.m_280273_(graphics);
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.resource);
        graphics.m_280218_(this.resource, this.guiLeft, this.guiTop, 0, 0, this.imageWidth, this.imageHeight);
        graphics.m_280656_(this.guiLeft + 4, this.guiLeft + 170, this.guiTop + 13, -16777216 + CustomNpcResourceListener.getDefaultTextColor());
        this.drawQuestText(graphics);
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
    }

    private void drawQuestText(GuiGraphics graphics) {
        int xoffset = this.guiLeft + 4;
        TextBlockClient block = new TextBlockClient(this.quest.getCompleteText(), 172, true, this.player);
        int yoffset = this.guiTop + 20;
        for (int i = 0; i < block.lines.size(); ++i) {
            String text = ((Component)block.lines.get(i)).getString();
            Objects.requireNonNull(this.f_96547_);
            graphics.m_280056_(this.f_96547_, text, this.guiLeft + 4, this.guiTop + 16 + i * 9, CustomNpcResourceListener.getDefaultTextColor(), false);
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        if (guibutton.id == 0) {
            Packets.sendServer(new SPacketQuestCompletionCheck(this.quest.getId()));
            this.close();
        }
    }

    @Override
    public void save() {
    }
}

