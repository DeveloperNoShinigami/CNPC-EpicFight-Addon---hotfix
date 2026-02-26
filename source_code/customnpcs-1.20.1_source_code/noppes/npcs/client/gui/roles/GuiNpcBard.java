/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.select.GuiSoundSelection;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNpcJobSave;
import noppes.npcs.roles.JobBard;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiNpcBard
extends GuiNPCInterface2 {
    private JobBard job;

    public GuiNpcBard(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobBard)npc.job;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 55, this.guiTop + 15, 20, 20, "X"));
        this.addLabel(new GuiLabel(0, this.job.song, this.guiLeft + 80, this.guiTop + 20));
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 75, this.guiTop + 50, "gui.selectSound"));
        this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 75, this.guiTop + 72, new String[]{"bard.jukebox", "bard.background"}, this.job.isStreamer ? 0 : 1));
        this.addLabel(new GuiLabel(6, "bard.loops", this.guiLeft + 60, this.guiTop + 120));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 6, this.guiLeft + 160, this.guiTop + 115, 60, 20, new String[]{"gui.no", "gui.yes"}, this.job.isLooping ? 1 : 0));
        this.addLabel(new GuiLabel(2, "bard.ondistance", this.guiLeft + 60, this.guiTop + 143));
        this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 160, this.guiTop + 138, 40, 20, "" + this.job.minRange));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(2, 512, 5);
        this.addLabel(new GuiLabel(4, "bard.hasoff", this.guiLeft + 60, this.guiTop + 166));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 4, this.guiLeft + 160, this.guiTop + 161, 60, 20, new String[]{"gui.no", "gui.yes"}, this.job.hasOffRange ? 1 : 0));
        this.addLabel(new GuiLabel(3, "bard.offdistance", this.guiLeft + 60, this.guiTop + 189));
        this.addTextField(new GuiTextFieldNop(3, (Screen)this, this.guiLeft + 160, this.guiTop + 184, 40, 20, "" + this.job.maxRange));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(2, 512, 10);
        this.getLabel((int)3).enabled = this.job.hasOffRange;
        this.getTextField((int)3).enabled = this.job.hasOffRange;
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            this.setSubGui(new GuiSoundSelection(this.job.song));
            MusicController.Instance.stopMusic();
        }
        if (button.id == 1) {
            this.job.song = "";
            this.getLabel(0).m_93666_((Component)Component.m_237119_());
            MusicController.Instance.stopMusic();
        }
        if (button.id == 3) {
            this.job.isStreamer = button.getValue() == 0;
            this.m_7856_();
        }
        if (button.id == 4) {
            this.job.hasOffRange = button.getValue() == 1;
            this.m_7856_();
        }
        if (button.id == 6) {
            this.job.isLooping = button.getValue() == 1;
            this.m_7856_();
        }
    }

    @Override
    public void save() {
        this.job.minRange = this.getTextField(2).getInteger();
        this.job.maxRange = this.getTextField(3).getInteger();
        if (this.job.minRange > this.job.maxRange) {
            this.job.maxRange = this.job.minRange;
        }
        MusicController.Instance.stopMusic();
        Packets.sendServer(new SPacketNpcJobSave(this.job.save(new CompoundTag())));
    }

    @Override
    public void subGuiClosed(Screen subgui) {
        GuiSoundSelection gss = (GuiSoundSelection)subgui;
        if (gss.selectedResource != null) {
            this.job.song = gss.selectedResource.toString();
            this.getLabel(0).m_93666_((Component)Component.m_237115_((String)this.job.song));
        }
    }
}

