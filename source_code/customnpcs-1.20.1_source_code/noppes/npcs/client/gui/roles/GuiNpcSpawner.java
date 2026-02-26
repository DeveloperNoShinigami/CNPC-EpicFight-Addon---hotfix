/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.roles;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.client.gui.GuiNpcMobSpawnerSelector;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNpcJobSave;
import noppes.npcs.packets.server.SPacketNpcJobSpawnerSet;
import noppes.npcs.roles.JobSpawner;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiNpcSpawner
extends GuiNPCInterface2
implements ITextfieldListener {
    private JobSpawner job;
    private int slot = -1;

    public GuiNpcSpawner(EntityNPCInterface npc) {
        super(npc);
        this.job = (JobSpawner)npc.job;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        int y = this.guiTop + 6;
        this.addButton(new GuiButtonNop(this, 20, this.guiLeft + 25, y, 20, 20, "X"));
        this.addLabel(new GuiLabel(0, "1:", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 50, y, this.job.getTitle(1)));
        this.addButton(new GuiButtonNop(this, 21, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiLabel(1, "2:", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 50, y, this.job.getTitle(2)));
        this.addButton(new GuiButtonNop(this, 22, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiLabel(2, "3:", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 2, this.guiLeft + 50, y, this.job.getTitle(3)));
        this.addButton(new GuiButtonNop(this, 23, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiLabel(3, "4:", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 50, y, this.job.getTitle(4)));
        this.addButton(new GuiButtonNop(this, 24, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiLabel(4, "5:", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 4, this.guiLeft + 50, y, this.job.getTitle(5)));
        this.addButton(new GuiButtonNop(this, 25, this.guiLeft + 25, y += 23, 20, 20, "X"));
        this.addLabel(new GuiLabel(5, "6:", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 5, this.guiLeft + 50, y, this.job.getTitle(6)));
        this.addLabel(new GuiLabel(6, "spawner.diesafter", this.guiLeft + 4, (y += 23) + 5));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 26, this.guiLeft + 115, y, 40, 20, new String[]{"gui.yes", "gui.no"}, this.job.doesntDie ? 1 : 0));
        this.addLabel(new GuiLabel(11, "spawner.despawn", this.guiLeft + 170, y + 5));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 11, this.guiLeft + 335, y, 40, 20, new String[]{"gui.no", "gui.yes"}, this.job.despawnOnTargetLost ? 1 : 0));
        this.addLabel(new GuiLabel(7, I18n.m_118938_((String)"spawner.posoffset", (Object[])new Object[0]) + " X:", this.guiLeft + 4, (y += 23) + 5));
        this.addTextField(new GuiTextFieldNop(7, (Screen)this, this.guiLeft + 99, y, 24, 20, "" + this.job.xOffset));
        this.getTextField((int)7).numbersOnly = true;
        this.getTextField(7).setMinMaxDefault(-9, 9, 0);
        this.addLabel(new GuiLabel(8, "Y:", this.guiLeft + 125, y + 5));
        this.addTextField(new GuiTextFieldNop(8, (Screen)this, this.guiLeft + 135, y, 24, 20, "" + this.job.yOffset));
        this.getTextField((int)8).numbersOnly = true;
        this.getTextField(8).setMinMaxDefault(-9, 9, 0);
        this.addLabel(new GuiLabel(9, "Z:", this.guiLeft + 161, y + 5));
        this.addTextField(new GuiTextFieldNop(9, (Screen)this, this.guiLeft + 171, y, 24, 20, "" + this.job.zOffset));
        this.getTextField((int)9).numbersOnly = true;
        this.getTextField(9).setMinMaxDefault(-9, 9, 0);
        this.addLabel(new GuiLabel(10, "spawner.type", this.guiLeft + 4, (y += 23) + 5));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 10, this.guiLeft + 80, y, 100, 20, new String[]{"spawner.one", "spawner.all", "spawner.random"}, this.job.spawnType));
    }

    @Override
    public void elementClicked() {
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id >= 0 && button.id < 6) {
            this.slot = button.id + 1;
            this.setSubGui(new GuiNpcMobSpawnerSelector());
        }
        if (button.id >= 20 && button.id < 26) {
            int slot = button.id - 19;
            this.job.remove(slot);
            this.m_7856_();
        }
        if (button.id == 26) {
            boolean bl = this.job.doesntDie = button.getValue() == 1;
        }
        if (button.id == 10) {
            this.job.spawnType = button.getValue();
        }
        if (button.id == 11) {
            this.job.despawnOnTargetLost = button.getValue() == 1;
        }
    }

    @Override
    public void subGuiClosed(Screen gui) {
        GuiNpcMobSpawnerSelector selector = (GuiNpcMobSpawnerSelector)gui;
        String selected = selector.getSelected();
        if (selected != null) {
            this.job.setJobCompound(this.slot, selector.activeTab, selected);
            Packets.sendServer(new SPacketNpcJobSpawnerSet(selector.activeTab, selected, this.slot));
        }
        this.m_7856_();
    }

    @Override
    public void save() {
        CompoundTag compound = this.job.save(new CompoundTag());
        Packets.sendServer(new SPacketNpcJobSave(compound));
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 7) {
            this.job.xOffset = textfield.getInteger();
        }
        if (textfield.id == 8) {
            this.job.yOffset = textfield.getInteger();
        }
        if (textfield.id == 9) {
            this.job.zOffset = textfield.getInteger();
        }
    }
}

