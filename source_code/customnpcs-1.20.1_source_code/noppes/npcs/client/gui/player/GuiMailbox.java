/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.ConfirmScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.player.GuiMailmanWrite;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.controllers.data.PlayerMailData;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketPlayerMailDelete;
import noppes.npcs.packets.server.SPacketPlayerMailGet;
import noppes.npcs.packets.server.SPacketPlayerMailOpen;
import noppes.npcs.packets.server.SPacketPlayerMailRead;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.gui.listeners.IGuiData;

public class GuiMailbox
extends GuiNPCInterface
implements IGuiData,
ICustomScrollListener {
    private GuiCustomScrollNop scroll;
    private PlayerMailData data;
    private PlayerMail selected;

    public GuiMailbox() {
        this.imageWidth = 256;
        this.setBackground("menubg.png");
        Packets.sendServer(new SPacketPlayerMailGet());
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
            this.scroll.setSize(165, 186);
        }
        this.scroll.guiLeft = this.guiLeft + 4;
        this.scroll.guiTop = this.guiTop + 4;
        this.addScroll(this.scroll);
        String title = I18n.m_118938_((String)"mailbox.name", (Object[])new Object[0]);
        int x = (this.imageWidth - this.f_96547_.m_92895_(title)) / 2;
        this.addLabel(new GuiLabel(0, title, this.guiLeft + x, this.guiTop - 8));
        if (this.selected != null) {
            this.addLabel(new GuiLabel(3, I18n.m_118938_((String)"mailbox.sender", (Object[])new Object[0]) + ":", this.guiLeft + 170, this.guiTop + 6));
            this.addLabel(new GuiLabel(1, this.selected.sender, this.guiLeft + 174, this.guiTop + 18));
            this.addLabel(new GuiLabel(2, I18n.m_118938_((String)"mailbox.timesend", (Object[])new Object[]{this.getTimePast()}), this.guiLeft + 174, this.guiTop + 30));
        }
        this.addButton(new GuiButtonNop(this, 0, this.guiLeft + 4, this.guiTop + 192, 82, 20, "mailbox.read"));
        this.addButton(new GuiButtonNop(this, 1, this.guiLeft + 88, this.guiTop + 192, 82, 20, "selectServer.delete"));
        this.getButton(1).setEnabled(this.selected != null);
    }

    private String getTimePast() {
        if (this.selected.timePast > 86400000L) {
            int days = (int)(this.selected.timePast / 86400000L);
            if (days == 1) {
                return days + " " + I18n.m_118938_((String)"mailbox.day", (Object[])new Object[0]);
            }
            return days + " " + I18n.m_118938_((String)"mailbox.days", (Object[])new Object[0]);
        }
        if (this.selected.timePast > 3600000L) {
            int hours = (int)(this.selected.timePast / 3600000L);
            if (hours == 1) {
                return hours + " " + I18n.m_118938_((String)"mailbox.hour", (Object[])new Object[0]);
            }
            return hours + " " + I18n.m_118938_((String)"mailbox.hours", (Object[])new Object[0]);
        }
        int minutes = (int)(this.selected.timePast / 60000L);
        if (minutes == 1) {
            return minutes + " " + I18n.m_118938_((String)"mailbox.minutes", (Object[])new Object[0]);
        }
        return minutes + " " + I18n.m_118938_((String)"mailbox.minutes", (Object[])new Object[0]);
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (!this.scroll.hasSelected()) {
            return;
        }
        if (id == 0) {
            GuiMailmanWrite.parent = this;
            GuiMailmanWrite.mail = this.selected;
            Packets.sendServer(new SPacketPlayerMailOpen(this.selected.time, this.selected.sender));
            this.selected = null;
            this.scroll.clearSelection();
        }
        if (id == 1) {
            ConfirmScreen guiyesno = new ConfirmScreen(bo -> {
                if (bo && this.selected != null) {
                    Packets.sendServer(new SPacketPlayerMailDelete(this.selected.time, this.selected.sender));
                    this.selected = null;
                }
                NoppesUtil.openGUI((Player)this.player, this);
            }, (Component)Component.m_237115_((String)""), (Component)Component.m_237115_((String)"gui.deleteMessage"));
            this.setScreen((Screen)guiyesno);
        }
    }

    @Override
    public boolean m_6375_(double i, double j, int k) {
        super.m_6375_(i, j, k);
        this.scroll.m_6375_(i, j, k);
        return true;
    }

    @Override
    public void save() {
    }

    @Override
    public void setGuiData(CompoundTag compound) {
        PlayerMailData data = new PlayerMailData();
        data.loadNBTData(compound);
        ArrayList<String> list = new ArrayList<String>();
        Collections.sort(data.playermail, (o1, o2) -> {
            if (o1.time == o2.time) {
                return 0;
            }
            return o1.time > o2.time ? -1 : 1;
        });
        for (PlayerMail mail : data.playermail) {
            list.add(mail.subject);
        }
        this.data = data;
        this.scroll.clear();
        this.selected = null;
        this.scroll.setUnsortedList(list);
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop guiCustomScroll) {
        this.selected = this.data.playermail.get(guiCustomScroll.getSelectedIndex());
        this.m_7856_();
        if (this.selected != null && !this.selected.beenRead) {
            this.selected.beenRead = true;
            Packets.sendServer(new SPacketPlayerMailRead(this.selected.time, this.selected.sender));
        }
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
    }
}

