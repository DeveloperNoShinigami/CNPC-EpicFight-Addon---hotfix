/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.mainmenu;

import net.minecraft.nbt.CompoundTag;
import noppes.npcs.client.gui.SubGuiNpcMovement;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.constants.EnumMenuType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataAI;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketMenuGet;
import noppes.npcs.packets.server.SPacketMenuSave;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiButtonYesNo;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiData;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class GuiNpcAI
extends GuiNPCInterface2
implements ITextfieldListener,
IGuiData {
    private String[] tacts = new String[]{"aitactics.rush", "aitactics.stagger", "aitactics.orbit", "aitactics.hitandrun", "aitactics.ambush", "aitactics.stalk", "gui.none"};
    private DataAI ai;

    public GuiNpcAI(EntityNPCInterface npc) {
        super(npc, 3);
        this.ai = npc.ais;
        Packets.sendServer(new SPacketMenuGet(EnumMenuType.AI));
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(0, "ai.enemyresponse", this.guiLeft + 5, this.guiTop + 17, "guihint.npconfoundenemy"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 86, this.guiTop + 10, 60, 20, new String[]{"gui.retaliate", "gui.panic", "gui.retreat", "gui.nothing"}, this.npc.ais.onAttack));
        this.addLabel(new GuiLabel(1, "ai.door", this.guiLeft + 5, this.guiTop + 40, "guihint.npcdoorinteract"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 1, this.guiLeft + 86, this.guiTop + 35, 60, 20, new String[]{"gui.break", "gui.open", "gui.disabled"}, this.npc.ais.doorInteract));
        this.addLabel(new GuiLabel(12, "ai.swim", this.guiLeft + 5, this.guiTop + 65, "guihint.npccanswim"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 7, this.guiLeft + 86, this.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ais.canSwim ? 1 : 0));
        this.addLabel(new GuiLabel(13, "ai.shelter", this.guiLeft + 5, this.guiTop + 90, "guihint.npcshelterfrom"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 9, this.guiLeft + 86, this.guiTop + 85, 60, 20, new String[]{"gui.darkness", "gui.sunlight", "gui.disabled"}, this.npc.ais.findShelter));
        this.addLabel(new GuiLabel(14, "ai.clearlos", this.guiLeft + 5, this.guiTop + 115, "guihint.npcmustseetarget"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 10, this.guiLeft + 86, this.guiTop + 110, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ais.directLOS ? 1 : 0));
        this.addButton(new GuiButtonYesNo((IGuiInterface)this, 23, this.guiLeft + 86, this.guiTop + 135, 60, 20, this.ai.attackInvisible));
        this.addLabel(new GuiLabel(23, "stats.attackInvisible", this.guiLeft + 5, this.guiTop + 140));
        this.addLabel(new GuiLabel(10, "ai.avoidwater", this.guiLeft + 150, this.guiTop + 17, "guihint.avoidwater"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 5, this.guiLeft + 230, this.guiTop + 10, 60, 20, new String[]{"gui.no", "gui.yes"}, this.ai.avoidsWater ? 1 : 0));
        this.addLabel(new GuiLabel(11, "ai.return", this.guiLeft + 150, this.guiTop + 40, "guihint.return"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 6, this.guiLeft + 230, this.guiTop + 35, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ais.returnToStart ? 1 : 0));
        this.addLabel(new GuiLabel(17, "ai.leapattarget", this.guiLeft + 150, this.guiTop + 65, "guihint.leapontarget"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 15, this.guiLeft + 230, this.guiTop + 60, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ais.canLeap ? 1 : 0));
        this.addLabel(new GuiLabel(2, "ai.movement", this.guiLeft + 4, this.guiTop + 165, "guihint.movement"));
        this.addButton(new GuiButtonNop(this, 2, this.guiLeft + 86, this.guiTop + 160, 60, 20, "selectServer.edit"));
        this.addLabel(new GuiLabel(25, "ai.mountcontrol", this.guiLeft + 150, this.guiTop + 90));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 16, this.guiLeft + 230, this.guiTop + 85, 60, 20, new String[]{"gui.no", "gui.yes"}, this.npc.ais.mountControl ? 1 : 0));
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            this.ai.onAttack = button.getValue();
            this.m_7856_();
        } else if (button.id == 1) {
            this.ai.doorInteract = button.getValue();
        } else if (button.id == 2) {
            this.setSubGui(new SubGuiNpcMovement(this.ai));
        } else if (button.id == 5) {
            this.npc.ais.setAvoidsWater(button.getValue() == 1);
        } else if (button.id == 6) {
            this.ai.returnToStart = button.getValue() == 1;
        } else if (button.id == 7) {
            this.ai.canSwim = button.getValue() == 1;
        } else if (button.id == 9) {
            this.ai.findShelter = button.getValue();
        } else if (button.id == 10) {
            this.ai.directLOS = button.getValue() == 1;
        } else if (button.id == 15) {
            this.ai.canLeap = button.getValue() == 1;
        } else if (button.id == 16) {
            this.ai.mountControl = button.getValue() == 1;
        } else if (button.id == 23) {
            this.ai.attackInvisible = ((GuiButtonYesNo)button).getBoolean();
        }
    }

    @Override
    public void save() {
        Packets.sendServer(new SPacketMenuSave(EnumMenuType.AI, this.ai.save(new CompoundTag())));
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        this.ai.readToNBT(compound);
        this.m_7856_();
    }
}

