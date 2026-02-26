/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.controllers.data.TransportLocation;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNpcTransportGet;
import noppes.npcs.packets.server.SPacketTransportCategoriesGet;
import noppes.npcs.packets.server.SPacketTransportSave;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiData;
import noppes.npcs.shared.client.gui.listeners.IScrollData;

public class GuiNpcTransporter
extends GuiNPCInterface2
implements IScrollData,
IGuiData {
    private GuiCustomScrollNop scroll;
    public TransportLocation location = new TransportLocation();
    private Map<String, Integer> data = new HashMap<String, Integer>();

    public GuiNpcTransporter(EntityNPCInterface npc) {
        super(npc);
        Packets.sendServer(new SPacketTransportCategoriesGet());
        Packets.sendServer(new SPacketNpcTransportGet());
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        Vector<String> list = new Vector<String>();
        list.addAll(this.data.keySet());
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
            this.scroll.setSize(143, 208);
        }
        this.scroll.guiLeft = this.guiLeft + 214;
        this.scroll.guiTop = this.guiTop + 4;
        this.addScroll(this.scroll);
        this.addLabel(new GuiLabel(0, "gui.name", this.guiLeft + 4, this.f_96544_ + 8));
        this.addTextField(new GuiTextFieldNop(0, (Screen)this, this.guiLeft + 60, this.guiTop + 3, 140, 20, this.location.name));
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 4, this.guiTop + 31, new String[]{"transporter.discovered", "transporter.start", "transporter.interaction"}, this.location.type));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            this.location.type = button.getValue();
        }
    }

    @Override
    public void save() {
        if (!this.scroll.hasSelected()) {
            return;
        }
        String name = this.getTextField(0).m_94155_();
        if (!name.isEmpty()) {
            this.location.name = name;
        }
        this.location.pos = this.player.m_20183_();
        this.location.dimension = this.player.m_20193_().m_46472_();
        int cat = this.data.get(this.scroll.getSelected());
        Packets.sendServer(new SPacketTransportSave(cat, this.location.writeNBT()));
    }

    @Override
    public void setData(Vector<String> list, Map<String, Integer> data) {
        this.data = data;
        this.scroll.setList(list);
    }

    @Override
    public void setSelected(String selected) {
        this.scroll.setSelected(selected);
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        TransportLocation loc = new TransportLocation();
        loc.readNBT(compound);
        this.location = loc;
        this.m_7856_();
    }
}

