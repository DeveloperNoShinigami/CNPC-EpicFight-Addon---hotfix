/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.player;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.MouseHelperMixin;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketDialogSelected;
import noppes.npcs.packets.server.SPacketQuestCompletionCheckAll;
import noppes.npcs.shared.client.gui.listeners.IGuiClose;
import noppes.npcs.shared.client.util.NoppesStringUtils;

public class GuiDialogInteract
extends GuiNPCInterface
implements IGuiClose {
    private Dialog dialog;
    private int selected = 0;
    private List<TextBlockClient> lines = new ArrayList<TextBlockClient>();
    private List<Integer> options = new ArrayList<Integer>();
    private int rowStart = 0;
    private int rowTotal = 0;
    private int dialogHeight = 180;
    private ResourceLocation wheel;
    private ResourceLocation[] wheelparts;
    private ResourceLocation indicator;
    private boolean isGrabbed = false;
    private double selectedX = 0.0;
    private double selectedY = 0.0;

    public GuiDialogInteract(EntityNPCInterface npc, Dialog dialog) {
        super(npc);
        this.dialog = dialog;
        this.appendDialog(dialog);
        this.imageHeight = 238;
        this.wheel = this.getResource("wheel.png");
        this.indicator = this.getResource("indicator.png");
        this.wheelparts = new ResourceLocation[]{this.getResource("wheel1.png"), this.getResource("wheel2.png"), this.getResource("wheel3.png"), this.getResource("wheel4.png"), this.getResource("wheel5.png"), this.getResource("wheel6.png")};
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.isGrabbed = false;
        this.grabMouse(this.dialog.showWheel);
        this.guiTop = this.f_96544_ - this.imageHeight;
        this.calculateRowHeight();
    }

    public void grabMouse(boolean grab) {
        if (grab && !this.isGrabbed) {
            MouseHelperMixin mouse = (MouseHelperMixin)Minecraft.m_91087_().f_91067_;
            mouse.setGrabbed(false);
            double xpos = 0.0;
            double ypos = 0.0;
            mouse.setX(xpos);
            mouse.setY(ypos);
            InputConstants.m_84833_((long)this.f_96541_.m_91268_().m_85439_(), (int)212995, (double)xpos, (double)ypos);
            this.isGrabbed = true;
        } else if (!grab && this.isGrabbed) {
            Minecraft.m_91087_().f_91067_.m_91602_();
            this.isGrabbed = false;
        }
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (!this.dialog.hideNPC) {
            int l = -80;
            int i1 = this.imageHeight;
            this.drawNpc(graphics, (LivingEntity)this.npc, l, i1, 1.4f, 0);
        }
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        graphics.m_280168_().m_85836_();
        graphics.m_280168_().m_252880_(0.0f, 0.5f, 100.065f);
        int count = 0;
        for (TextBlockClient block : new ArrayList<TextBlockClient>(this.lines)) {
            int size = ClientProxy.Font.width(block.getName() + ": ");
            this.drawString(graphics, block.getName() + ": ", -4 - size, block.color, count);
            for (Component line : block.lines) {
                this.drawString(graphics, line.getString(), 0, block.color, count);
                ++count;
            }
            ++count;
        }
        if (!this.options.isEmpty()) {
            if (!this.dialog.showWheel) {
                this.drawLinedOptions(graphics, mouseY);
            } else {
                this.drawWheel(graphics);
            }
        }
        graphics.m_280168_().m_85849_();
    }

    private void drawWheel(GuiGraphics graphics) {
        int yoffset = this.guiTop + this.dialogHeight + 14;
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.wheel);
        graphics.m_280218_(this.wheel, this.f_96543_ / 2 - 31, yoffset, 0, 0, 63, 40);
        this.selectedX = this.f_96541_.f_91067_.m_91589_() * 0.5;
        this.selectedY = -this.f_96541_.f_91067_.m_91594_() * 0.5;
        int limit = 80;
        if (this.selectedX > (double)limit) {
            this.selectedX = limit;
        }
        if (this.selectedX < (double)(-limit)) {
            this.selectedX = -limit;
        }
        if (this.selectedY > (double)limit) {
            this.selectedY = limit;
        }
        if (this.selectedY < (double)(-limit)) {
            this.selectedY = -limit;
        }
        this.selected = 1;
        if (this.selectedY < -20.0) {
            ++this.selected;
        }
        if (this.selectedY > 54.0) {
            --this.selected;
        }
        if (this.selectedX < 0.0) {
            this.selected += 3;
        }
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.wheelparts[this.selected]);
        graphics.m_280218_(this.wheelparts[this.selected], this.f_96543_ / 2 - 31, yoffset, 0, 0, 85, 55);
        for (int slot : this.dialog.options.keySet()) {
            DialogOption option = this.dialog.options.get(slot);
            if (option == null || option.optionType == 2 || option.hasDialog() && !option.getDialog().availability.isAvailable((Player)this.player)) continue;
            int color = option.optionColor;
            if (slot == this.selected) {
                color = 8622040;
            }
            int height = ClientProxy.Font.height(option.title);
            if (slot == 0) {
                graphics.m_280488_(this.f_96547_, option.title, this.f_96543_ / 2 + 13, yoffset - height, color);
            }
            if (slot == 1) {
                graphics.m_280488_(this.f_96547_, option.title, this.f_96543_ / 2 + 33, yoffset - height / 2 + 14, color);
            }
            if (slot == 2) {
                graphics.m_280488_(this.f_96547_, option.title, this.f_96543_ / 2 + 27, yoffset + 27, color);
            }
            if (slot == 3) {
                graphics.m_280488_(this.f_96547_, option.title, this.f_96543_ / 2 - 13 - ClientProxy.Font.width(option.title), yoffset - height, color);
            }
            if (slot == 4) {
                graphics.m_280488_(this.f_96547_, option.title, this.f_96543_ / 2 - 33 - ClientProxy.Font.width(option.title), yoffset - height / 2 + 14, color);
            }
            if (slot != 5) continue;
            graphics.m_280488_(this.f_96547_, option.title, this.f_96543_ / 2 - 27 - ClientProxy.Font.width(option.title), yoffset + 27, color);
        }
        RenderSystem.setShader(GameRenderer::m_172817_);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)this.indicator);
        graphics.m_280218_(this.indicator, this.f_96543_ / 2 + (int)this.selectedX / 4 - 2, yoffset + 16 - (int)this.selectedY / 6, 0, 0, 8, 8);
    }

    private void drawLinedOptions(GuiGraphics graphics, int j) {
        int selected;
        graphics.m_280656_(this.guiLeft - 60, this.guiLeft + this.imageWidth + 120, this.guiTop + this.dialogHeight - ClientProxy.Font.height(null) / 3, -1);
        int offset = this.dialogHeight;
        if (j >= this.guiTop + offset && (selected = (j - (this.guiTop + offset)) / ClientProxy.Font.height(null)) < this.options.size()) {
            this.selected = selected;
        }
        if (this.selected >= this.options.size()) {
            this.selected = 0;
        }
        if (this.selected < 0) {
            this.selected = 0;
        }
        for (int k = 0; k < this.options.size(); ++k) {
            int id = this.options.get(k);
            DialogOption option = this.dialog.options.get(id);
            int y = this.guiTop + offset + k * ClientProxy.Font.height(null);
            if (this.selected == k) {
                graphics.m_280488_(this.f_96547_, ">", this.guiLeft - 60, y, 0xE0E0E0);
            }
            graphics.m_280488_(this.f_96547_, NoppesStringUtils.formatText(option.title, new Object[]{this.player, this.npc}), this.guiLeft - 30, y, option.optionColor);
        }
    }

    private void drawString(GuiGraphics graphics, String text, int left, int color, int count) {
        int height = count - this.rowStart;
        ClientProxy.Font.draw(graphics, text, this.guiLeft + left, this.guiTop + height * ClientProxy.Font.height(null), color);
    }

    private int getSelected() {
        if (this.selected <= 0) {
            return 0;
        }
        if (this.selected < this.options.size()) {
            return this.selected;
        }
        return this.options.size() - 1;
    }

    @Override
    public boolean m_7933_(int key, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (key == this.f_96541_.f_91066_.f_92085_.getKey().m_84873_() || key == InputConstants.m_84851_((String)"key.keyboard.up").m_84873_()) {
            --this.selected;
        }
        if (key == this.f_96541_.f_91066_.f_92087_.getKey().m_84873_() || key == InputConstants.m_84851_((String)"key.keyboard.down").m_84873_()) {
            ++this.selected;
        }
        if (key == InputConstants.m_84851_((String)"key.keyboard.enter").m_84873_() || key == InputConstants.m_84851_((String)"key.keyboard.keypad.enter").m_84873_()) {
            this.handleDialogSelection();
        }
        if (this.closeOnEsc && (key == InputConstants.m_84851_((String)"key.keyboard.escape").m_84873_() || this.isInventoryKey(key))) {
            Packets.sendServer(new SPacketDialogSelected(this.dialog.id, -1));
            this.closed();
            this.m_7379_();
        }
        return true;
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        if ((this.selected == -1 && this.options.isEmpty() || this.selected >= 0) && k == 0) {
            this.handleDialogSelection();
        }
        return true;
    }

    private void handleDialogSelection() {
        int optionId = -1;
        if (this.dialog.showWheel) {
            optionId = this.selected;
        } else if (!this.options.isEmpty()) {
            optionId = this.options.get(this.selected);
        }
        Packets.sendServer(new SPacketDialogSelected(this.dialog.id, optionId));
        if (this.dialog == null || !this.dialog.hasOtherOptions() || this.options.isEmpty()) {
            if (this.closeOnEsc) {
                this.closed();
                this.m_7379_();
            }
            return;
        }
        DialogOption option = this.dialog.options.get(optionId);
        if (option == null || option.optionType != 1) {
            if (this.closeOnEsc) {
                this.closed();
                this.m_7379_();
            }
            return;
        }
        this.lines.add(new TextBlockClient(this.player.m_5446_().getString(), option.title, 280, option.optionColor, new Object[]{this.player, this.npc}));
        this.calculateRowHeight();
        NoppesUtil.clickSound();
    }

    private void closed() {
        this.grabMouse(false);
        Packets.sendServer(new SPacketQuestCompletionCheckAll());
    }

    public void appendDialog(Dialog dialog) {
        this.closeOnEsc = !dialog.disableEsc;
        this.dialog = dialog;
        this.options = new ArrayList<Integer>();
        if (dialog.sound != null && !dialog.sound.isEmpty()) {
            BlockPos pos = this.npc.m_20183_();
            MusicController.Instance.playSoundDialog(SoundSource.VOICE, dialog.sound, pos, 1.0f, 1.0f);
        }
        this.lines.add(new TextBlockClient(this.npc.m_20203_(), dialog.text, 280, 0xE0E0E0, new Object[]{this.player, this.npc}));
        for (int slot : dialog.options.keySet()) {
            DialogOption option = dialog.options.get(slot);
            if (option == null || !option.isAvailable((Player)this.player)) continue;
            this.options.add(slot);
        }
        this.calculateRowHeight();
        this.grabMouse(dialog.showWheel);
    }

    private void calculateRowHeight() {
        if (this.dialog.showWheel) {
            this.dialogHeight = this.imageHeight - 58;
        } else {
            this.dialogHeight = this.imageHeight - 3 * ClientProxy.Font.height(null) - 4;
            if (this.dialog.options.size() > 3) {
                this.dialogHeight -= (this.dialog.options.size() - 3) * ClientProxy.Font.height(null);
            }
        }
        this.rowTotal = 0;
        for (TextBlockClient block : this.lines) {
            this.rowTotal += block.lines.size() + 1;
        }
        int max = this.dialogHeight / ClientProxy.Font.height(null);
        this.rowStart = this.rowTotal - max;
        if (this.rowStart < 0) {
            this.rowStart = 0;
        }
    }

    @Override
    public void setClose(CompoundTag data) {
        this.grabMouse(false);
    }

    @Override
    public void save() {
    }
}

