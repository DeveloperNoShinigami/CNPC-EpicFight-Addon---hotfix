/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.client.gui.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import noppes.npcs.client.gui.player.companion.GuiNpcCompanionTalents;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.constants.EnumCompanionStage;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNpcRoleCompanionUpdate;
import noppes.npcs.packets.server.SPacketNpcRoleSave;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiSliderNop;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ISliderListener;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiNpcCompanion
extends GuiNPCInterface2
implements ITextfieldListener,
ISliderListener {
    private RoleCompanion role;
    private List<GuiNpcCompanionTalents.GuiTalent> talents = new ArrayList<GuiNpcCompanionTalents.GuiTalent>();

    public GuiNpcCompanion(EntityNPCInterface npc) {
        super(npc);
        this.role = (RoleCompanion)npc.role;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.talents = new ArrayList<GuiNpcCompanionTalents.GuiTalent>();
        int y = this.guiTop + 4;
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 70, y, 90, 20, new String[]{EnumCompanionStage.BABY.name, EnumCompanionStage.CHILD.name, EnumCompanionStage.TEEN.name, EnumCompanionStage.ADULT.name, EnumCompanionStage.FULLGROWN.name}, this.role.stage.ordinal()));
        this.addLabel(new GuiLabel(0, "companion.stage", this.guiLeft + 4, y + 5));
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 162, y, 90, 20, "gui.update"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 2, this.guiLeft + 70, y += 22, 90, 20, new String[]{"gui.no", "gui.yes"}, this.role.canAge ? 1 : 0));
        this.addLabel(new GuiLabel(2, "companion.age", this.guiLeft + 4, y + 5));
        if (this.role.canAge) {
            this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 162, y, 140, 20, "" + this.role.ticksActive));
            this.getTextField((int)2).numbersOnly = true;
            this.getTextField(2).setMinMaxDefault(0, Integer.MAX_VALUE, 0);
        }
        this.talents.add(new GuiNpcCompanionTalents.GuiTalent(this.role, EnumCompanionTalent.INVENTORY, this.guiLeft + 4, y += 26));
        this.addSlider(new GuiSliderNop(this, 10, this.guiLeft + 30, y + 2, 100, 20, (float)this.role.getExp(EnumCompanionTalent.INVENTORY) / 5000.0f));
        this.talents.add(new GuiNpcCompanionTalents.GuiTalent(this.role, EnumCompanionTalent.ARMOR, this.guiLeft + 4, y += 26));
        this.addSlider(new GuiSliderNop(this, 11, this.guiLeft + 30, y + 2, 100, 20, (float)this.role.getExp(EnumCompanionTalent.ARMOR) / 5000.0f));
        this.talents.add(new GuiNpcCompanionTalents.GuiTalent(this.role, EnumCompanionTalent.SWORD, this.guiLeft + 4, y += 26));
        this.addSlider(new GuiSliderNop(this, 12, this.guiLeft + 30, y + 2, 100, 20, (float)this.role.getExp(EnumCompanionTalent.SWORD) / 5000.0f));
        for (GuiNpcCompanionTalents.GuiTalent gui : this.talents) {
            gui.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button;
        if (guibutton.id == 0) {
            button = guibutton;
            this.role.matureTo(EnumCompanionStage.values()[button.getValue()]);
            if (this.role.canAge) {
                this.role.ticksActive = this.role.stage.matureAge;
            }
            this.m_7856_();
        }
        if (guibutton.id == 1) {
            Packets.sendServer(new SPacketNpcRoleCompanionUpdate(this.role.stage));
        }
        if (guibutton.id == 2) {
            button = guibutton;
            this.role.canAge = button.getValue() == 1;
            this.m_7856_();
        }
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 2) {
            this.role.ticksActive = textfield.getInteger();
        }
    }

    @Override
    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.m_88315_(graphics, mouseX, mouseY, partialTicks);
        for (GuiNpcCompanionTalents.GuiTalent talent : new ArrayList<GuiNpcCompanionTalents.GuiTalent>(this.talents)) {
            talent.m_88315_(graphics, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void elementClicked() {
    }

    @Override
    public void save() {
        Packets.sendServer(new SPacketNpcRoleSave(this.role.save(new CompoundTag())));
    }

    @Override
    public void mouseDragged(GuiSliderNop slider) {
        if (slider.sliderValue <= 0.0f) {
            slider.m_93666_((Component)Component.m_237115_((String)"gui.disabled"));
            this.role.talents.remove((Object)EnumCompanionTalent.values()[slider.id - 10]);
        } else {
            slider.m_93666_((Component)Component.m_237115_((String)((int)(slider.sliderValue * 50.0f) * 100 + " exp")));
            this.role.setExp(EnumCompanionTalent.values()[slider.id - 10], (int)(slider.sliderValue * 50.0f) * 100);
        }
    }

    @Override
    public void mousePressed(GuiSliderNop slider) {
    }

    @Override
    public void mouseReleased(GuiSliderNop slider) {
    }
}

