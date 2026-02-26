/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.client.gui.model;

import java.util.ArrayList;
import java.util.List;
import noppes.npcs.client.controllers.Preset;
import noppes.npcs.client.controllers.PresetController;
import noppes.npcs.client.gui.model.GuiCreationScreenInterface;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;

public class GuiCreationLoad
extends GuiCreationScreenInterface
implements ICustomScrollListener {
    private List<String> list = new ArrayList<String>();
    private GuiCustomScrollNop scroll;

    public GuiCreationLoad(EntityNPCInterface npc) {
        super(npc);
        this.active = 5;
        this.xOffset = 60;
        PresetController.instance.load();
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
        }
        this.list.clear();
        for (Preset preset : PresetController.instance.presets.values()) {
            this.list.add(preset.name);
        }
        this.scroll.setList(this.list);
        this.scroll.guiLeft = this.guiLeft;
        this.scroll.guiTop = this.guiTop + 45;
        this.scroll.setSize(100, this.imageHeight - 96);
        this.addScroll(this.scroll);
        this.addButton(new GuiButtonNop(this, 10, this.guiLeft, this.guiTop + this.imageHeight - 46, 120, 20, "gui.remove"));
    }

    @Override
    public void buttonEvent(GuiButtonNop btn) {
        if (btn.id == 10 && this.scroll.hasSelected()) {
            PresetController.instance.removePreset(this.scroll.getSelected());
            this.m_7856_();
        }
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop scroll) {
        Preset preset = PresetController.instance.getPreset(scroll.getSelected());
        this.playerdata.load(preset.data.save());
        this.m_7856_();
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
    }
}

