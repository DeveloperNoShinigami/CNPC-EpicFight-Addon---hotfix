/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.ObjectSelectionList
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.shared.client.gui.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.common.util.NaturalOrderComparator;

public class GuiStringSlotNop<E extends ListEntry>
extends ObjectSelectionList {
    public HashSet<String> selectedList;
    private boolean multiSelect;
    private GuiBasic parent;

    public GuiStringSlotNop(Collection<String> list, GuiBasic parent, boolean multiSelect) {
        Minecraft minecraft = Minecraft.m_91087_();
        int n = parent.f_96543_;
        int n2 = parent.f_96544_;
        int n3 = parent.f_96544_ - 64;
        Objects.requireNonNull(parent.getFontRenderer());
        super(minecraft, n, n2, 32, n3, 9 + 3);
        this.selectedList = new HashSet();
        this.parent = parent;
        this.multiSelect = multiSelect;
        if (list != null) {
            this.setList(list);
        }
    }

    public void setList(Collection<String> l) {
        this.m_93516_();
        ArrayList<String> list = new ArrayList<String>(l);
        Collections.sort(list, new NaturalOrderComparator());
        for (String s : list) {
            this.m_7085_((AbstractSelectionList.Entry)new ListEntry(s));
        }
        this.m_6987_((AbstractSelectionList.Entry)((ListEntry)null));
    }

    public void setColoredList(Map<String, Integer> m) {
        this.m_93516_();
        ArrayList<String> list = new ArrayList<String>(m.keySet());
        Collections.sort(list, new NaturalOrderComparator());
        for (String s : list) {
            this.m_7085_((AbstractSelectionList.Entry)new ListEntry(s, m.get(s)));
        }
        this.m_6987_((AbstractSelectionList.Entry)((ListEntry)null));
    }

    public void setSelected(String s) {
        if (s == null) {
            this.m_6987_((AbstractSelectionList.Entry)((ListEntry)null));
        } else {
            for (Object e : this.m_6702_()) {
                if (!((ListEntry)((Object)e)).data.equals(s)) continue;
                this.m_6987_((AbstractSelectionList.Entry)((ListEntry)((Object)e)));
            }
        }
    }

    public String getSelectedString() {
        if (this.m_93511_() == null) {
            return null;
        }
        return ((ListEntry)this.m_93511_()).data;
    }

    protected boolean m_7987_(int i) {
        if (!this.multiSelect) {
            return super.m_7987_(i);
        }
        return this.selectedList.contains(((ListEntry)this.m_93500_((int)i)).data);
    }

    protected void m_7733_(GuiGraphics graphics) {
        this.parent.m_280273_(graphics);
    }

    public void clear() {
        this.m_93516_();
    }

    public class ListEntry
    extends ObjectSelectionList.Entry {
        public final String data;
        public final int color;
        private long prevTime = 0L;

        public ListEntry(String data) {
            this.data = data;
            this.color = 0xFFFFFF;
        }

        public ListEntry(String data, int color) {
            this.data = data;
            this.color = color;
        }

        public void m_6311_(GuiGraphics graphics, int index, int rowTop, int rowBottom, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTicks) {
            graphics.m_280488_(GuiStringSlotNop.this.parent.getFontRenderer(), this.data, rowBottom, rowTop, this.color);
        }

        public boolean m_6375_(double mouseX, double mouseY, int button) {
            long time = System.currentTimeMillis();
            ListEntry s = (ListEntry)GuiStringSlotNop.this.m_93511_();
            if (s == this && time - this.prevTime < 400L) {
                GuiStringSlotNop.this.parent.doubleClicked();
            }
            this.prevTime = time;
            GuiStringSlotNop.this.m_6987_((AbstractSelectionList.Entry)this);
            if (GuiStringSlotNop.this.selectedList.contains(this.data)) {
                GuiStringSlotNop.this.selectedList.remove(this.data);
            } else {
                GuiStringSlotNop.this.selectedList.add(this.data);
            }
            GuiStringSlotNop.this.parent.elementClicked();
            return true;
        }

        public Component m_142172_() {
            return Component.m_237113_((String)this.data);
        }
    }
}

