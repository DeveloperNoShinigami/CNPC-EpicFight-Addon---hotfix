/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketRemoteFreeze;
import noppes.npcs.packets.server.SPacketRemoteMenuOpen;
import noppes.npcs.packets.server.SPacketRemoteNpcDelete;
import noppes.npcs.packets.server.SPacketRemoteNpcReset;
import noppes.npcs.packets.server.SPacketRemoteNpcTp;
import noppes.npcs.packets.server.SPacketRemoteNpcsGet;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.listeners.IScrollData;

public class GuiNpcRemoteEditor
extends GuiNPCInterface
implements IScrollData {
    private GuiCustomScrollNop scroll;
    private Map<String, Integer> data = new HashMap<String, Integer>();

    public GuiNpcRemoteEditor() {
        this.imageWidth = 256;
        this.setBackground("menubg.png");
        Packets.sendServer(new SPacketRemoteNpcsGet());
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
            this.scroll.setSize(165, 208);
        }
        this.scroll.guiLeft = this.guiLeft + 4;
        this.scroll.guiTop = this.guiTop + 4;
        this.addScroll(this.scroll);
        String title = I18n.m_118938_((String)"remote.title", (Object[])new Object[0]);
        int x = (this.imageWidth - this.f_96547_.m_92895_(title)) / 2;
        this.addLabel(new GuiLabel(0, title, this.guiLeft + x, this.guiTop - 8));
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 170, this.guiTop + 6, 82, 20, "selectServer.edit"));
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 170, this.guiTop + 28, 82, 20, "selectServer.delete"));
        this.addButton(new GuiButtonNop(this, 2, this.guiLeft + 170, this.guiTop + 50, 82, 20, "gui.reset"));
        this.addButton(new GuiButtonNop(this, 4, this.guiLeft + 170, this.guiTop + 72, 82, 20, "remote.tp"));
        this.addButton(new GuiButtonNop(this, 5, this.guiLeft + 170, this.guiTop + 110, 82, 20, "remote.resetall"));
        this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 170, this.guiTop + 132, 82, 20, "remote.freeze"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 3) {
            Packets.sendServer(new SPacketRemoteFreeze());
        }
        if (id == 5) {
            for (int ids : this.data.values()) {
                Packets.sendServer(new SPacketRemoteNpcReset(ids));
                Entity entity = this.player.m_9236_().m_6815_(ids);
                if (entity == null || !(entity instanceof EntityNPCInterface)) continue;
                ((EntityNPCInterface)entity).reset();
            }
        }
        if (!this.data.containsKey(this.scroll.getSelected())) {
            return;
        }
        if (id == 0) {
            Packets.sendServer(new SPacketRemoteMenuOpen(this.data.get(this.scroll.getSelected())));
        }
        if (id == 1) {
            ConfirmScreen guiyesno = new ConfirmScreen(bo -> {
                if (bo) {
                    Packets.sendServer(new SPacketRemoteNpcDelete(this.data.get(this.scroll.getSelected())));
                }
                NoppesUtil.openGUI((Player)this.player, this);
            }, (Component)Component.m_237115_((String)""), (Component)Component.m_237115_((String)"gui.deleteMessage"));
            this.setScreen((Screen)guiyesno);
        }
        if (id == 2) {
            Packets.sendServer(new SPacketRemoteNpcReset(this.data.get(this.scroll.getSelected())));
            Entity entity = this.player.m_9236_().m_6815_(this.data.get(this.scroll.getSelected()).intValue());
            if (entity != null && entity instanceof EntityNPCInterface) {
                ((EntityNPCInterface)entity).reset();
            }
        }
        if (id == 4) {
            Packets.sendServer(new SPacketRemoteNpcTp(this.data.get(this.scroll.getSelected())));
            this.close();
        }
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        this.scroll.m_6375_(i, j, k);
        return super.m_6375_(i, j, k);
    }

    @Override
    public void save() {
    }

    @Override
    public void setData(Vector<String> list, Map<String, Integer> data) {
        this.scroll.setList(list);
        this.data = data;
    }

    @Override
    public void setSelected(String selected) {
        this.getButton(3).setDisplayText(selected);
    }
}

