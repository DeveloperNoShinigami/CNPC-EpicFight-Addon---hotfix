/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketBanksGet;
import noppes.npcs.packets.server.SPacketNpcRoleSave;
import noppes.npcs.roles.RoleBank;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.gui.listeners.IScrollData;

public class GuiNpcBankSetup
extends GuiNPCInterface2
implements IScrollData,
ICustomScrollListener {
    private GuiCustomScrollNop scroll;
    private Map<String, Integer> data = new HashMap<String, Integer>();
    private RoleBank role;

    public GuiNpcBankSetup(EntityNPCInterface npc) {
        super(npc);
        this.role = (RoleBank)npc.role;
        Packets.sendServer(new SPacketBanksGet());
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
        }
        this.scroll.setSize(200, 152);
        this.scroll.guiLeft = this.guiLeft + 85;
        this.scroll.guiTop = this.guiTop + 20;
        this.addScroll(this.scroll);
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
    }

    @Override
    public void setData(Vector<String> list, Map<String, Integer> data) {
        String name = null;
        Bank bank = this.role.getBank();
        if (bank != null) {
            name = bank.name;
        }
        this.data = data;
        this.scroll.setList(list);
        if (name != null) {
            this.setSelected(name);
        }
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        if (k == 0 && this.scroll != null) {
            this.scroll.m_6375_(i, j, k);
        }
        return super.m_6375_(i, j, k);
    }

    @Override
    public void setSelected(String selected) {
        this.scroll.setSelected(selected);
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop guiCustomScroll) {
        if (guiCustomScroll.id == 0) {
            this.role.bankId = this.data.get(this.scroll.getSelected());
            this.save();
        }
    }

    @Override
    public void save() {
        Packets.sendServer(new SPacketNpcRoleSave(this.role.save(new CompoundTag())));
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
    }
}

