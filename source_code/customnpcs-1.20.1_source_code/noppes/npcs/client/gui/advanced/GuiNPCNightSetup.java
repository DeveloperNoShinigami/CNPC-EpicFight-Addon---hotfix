/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.advanced;

import net.minecraft.nbt.CompoundTag;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.constants.EnumMenuType;
import noppes.npcs.controllers.data.DataTransform;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketMenuGet;
import noppes.npcs.packets.server.SPacketMenuSave;
import noppes.npcs.packets.server.SPacketNpcTransform;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.listeners.IGuiData;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiNPCNightSetup
extends GuiNPCInterface2
implements IGuiData {
    private DataTransform data;

    public GuiNPCNightSetup(EntityNPCInterface npc) {
        super(npc);
        this.data = npc.transform;
        Packets.sendServer(new SPacketMenuGet(EnumMenuType.TRANSFORM));
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(0, "menu.display", this.guiLeft + 4, this.guiTop + 25));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 104, this.guiTop + 20, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasDisplay ? 1 : 0));
        this.addLabel(new GuiLabel(1, "menu.stats", this.guiLeft + 4, this.guiTop + 47));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 1, this.guiLeft + 104, this.guiTop + 42, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasStats ? 1 : 0));
        this.addLabel(new GuiLabel(2, "menu.ai", this.guiLeft + 4, this.guiTop + 69));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 2, this.guiLeft + 104, this.guiTop + 64, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasAi ? 1 : 0));
        this.addLabel(new GuiLabel(3, "menu.inventory", this.guiLeft + 4, this.guiTop + 91));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 3, this.guiLeft + 104, this.guiTop + 86, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasInv ? 1 : 0));
        this.addLabel(new GuiLabel(4, "menu.advanced", this.guiLeft + 4, this.guiTop + 113));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 4, this.guiLeft + 104, this.guiTop + 108, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasAdvanced ? 1 : 0));
        this.addLabel(new GuiLabel(5, "role.name", this.guiLeft + 4, this.guiTop + 135));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 5, this.guiLeft + 104, this.guiTop + 130, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasRole ? 1 : 0));
        this.addLabel(new GuiLabel(6, "job.name", this.guiLeft + 4, this.guiTop + 157));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 6, this.guiLeft + 104, this.guiTop + 152, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.hasJob ? 1 : 0));
        this.addLabel(new GuiLabel(10, "advanced.editingmode", this.guiLeft + 170, this.guiTop + 9));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 10, this.guiLeft + 244, this.guiTop + 4, 50, 20, new String[]{"gui.no", "gui.yes"}, this.data.editingModus ? 1 : 0));
        if (this.data.editingModus) {
            this.addButton(new GuiButtonNop(this, 11, this.guiLeft + 170, this.guiTop + 34, "advanced.loadday"));
            this.addButton(new GuiButtonNop(this, 12, this.guiLeft + 170, this.guiTop + 56, "advanced.loadnight"));
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            boolean bl = this.data.hasDisplay = button.getValue() == 1;
        }
        if (button.id == 1) {
            boolean bl = this.data.hasStats = button.getValue() == 1;
        }
        if (button.id == 2) {
            boolean bl = this.data.hasAi = button.getValue() == 1;
        }
        if (button.id == 3) {
            boolean bl = this.data.hasInv = button.getValue() == 1;
        }
        if (button.id == 4) {
            boolean bl = this.data.hasAdvanced = button.getValue() == 1;
        }
        if (button.id == 5) {
            boolean bl = this.data.hasRole = button.getValue() == 1;
        }
        if (button.id == 6) {
            boolean bl = this.data.hasJob = button.getValue() == 1;
        }
        if (button.id == 10) {
            this.data.editingModus = button.getValue() == 1;
            this.save();
            this.m_7856_();
        }
        if (button.id == 11) {
            Packets.sendServer(new SPacketNpcTransform(false));
        }
        if (button.id == 12) {
            Packets.sendServer(new SPacketNpcTransform(true));
        }
    }

    @Override
    public void save() {
        Packets.sendServer(new SPacketMenuSave(EnumMenuType.TRANSFORM, this.data.writeOptions(new CompoundTag())));
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.data.readOptions(compound);
        this.m_7856_();
    }
}

