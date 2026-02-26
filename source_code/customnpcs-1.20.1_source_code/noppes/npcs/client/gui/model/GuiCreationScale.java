/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package noppes.npcs.client.gui.model;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.resources.language.I18n;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.client.gui.model.GuiCreationScreenInterface;
import noppes.npcs.constants.EnumParts;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiCustomScrollNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiSliderNop;
import noppes.npcs.shared.client.gui.listeners.ICustomScrollListener;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ISliderListener;

public class GuiCreationScale
extends GuiCreationScreenInterface
implements ISliderListener,
ICustomScrollListener {
    private GuiCustomScrollNop scroll;
    private List<EnumParts> data = new ArrayList<EnumParts>();
    private static EnumParts selected = EnumParts.HEAD;

    public GuiCreationScale(EntityNPCInterface npc) {
        super(npc);
        this.active = 3;
        this.xOffset = 140;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        if (this.scroll == null) {
            this.scroll = new GuiCustomScrollNop(this, 0);
        }
        ArrayList<String> list = new ArrayList<String>();
        EnumParts[] parts = new EnumParts[]{EnumParts.HEAD, EnumParts.BODY, EnumParts.ARM_LEFT, EnumParts.ARM_RIGHT, EnumParts.LEG_LEFT, EnumParts.LEG_RIGHT};
        this.data.clear();
        for (EnumParts part : parts) {
            ModelPartConfig config;
            if (part == EnumParts.ARM_RIGHT) {
                config = this.playerdata.getPartConfig(EnumParts.ARM_LEFT);
                if (!config.notShared) continue;
            }
            if (part == EnumParts.LEG_RIGHT) {
                config = this.playerdata.getPartConfig(EnumParts.LEG_LEFT);
                if (!config.notShared) continue;
            }
            this.data.add(part);
            list.add(I18n.m_118938_((String)("part." + part.name), (Object[])new Object[0]));
        }
        this.scroll.setUnsortedList(list);
        this.scroll.setSelected(I18n.m_118938_((String)("part." + GuiCreationScale.selected.name), (Object[])new Object[0]));
        this.scroll.guiLeft = this.guiLeft;
        this.scroll.guiTop = this.guiTop + 46;
        this.scroll.setSize(100, this.imageHeight - 74);
        this.addScroll(this.scroll);
        ModelPartConfig config = this.playerdata.getPartConfig(selected);
        int y = this.guiTop + 65;
        this.addLabel(new GuiLabel(10, "scale.width", this.guiLeft + 102, y + 5, 0xFFFFFF));
        this.addSlider(new GuiSliderNop(this, 10, this.guiLeft + 150, y, 100, 20, config.scaleX - 0.5f));
        this.addLabel(new GuiLabel(11, "scale.height", this.guiLeft + 102, (y += 22) + 5, 0xFFFFFF));
        this.addSlider(new GuiSliderNop(this, 11, this.guiLeft + 150, y, 100, 20, config.scaleY - 0.5f));
        this.addLabel(new GuiLabel(12, "scale.depth", this.guiLeft + 102, (y += 22) + 5, 0xFFFFFF));
        this.addSlider(new GuiSliderNop(this, 12, this.guiLeft + 150, y, 100, 20, config.scaleZ - 0.5f));
        if (selected == EnumParts.ARM_LEFT || selected == EnumParts.LEG_LEFT) {
            this.addLabel(new GuiLabel(13, "scale.shared", this.guiLeft + 102, (y += 22) + 5, 0xFFFFFF));
            this.addButton(new GuiButtonNop((IGuiInterface)this, 13, this.guiLeft + 150, y, 50, 20, new String[]{"gui.no", "gui.yes"}, config.notShared ? 0 : 1));
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop btn) {
        if (btn.id == 13) {
            boolean bo;
            this.playerdata.getPartConfig((EnumParts)GuiCreationScale.selected).notShared = bo = btn.getValue() == 0;
            this.m_7856_();
        }
    }

    @Override
    public void mouseDragged(GuiSliderNop slider) {
        super.mouseDragged(slider);
        if (slider.id >= 10 && slider.id <= 12) {
            int percent = (int)(50.0f + slider.sliderValue * 100.0f);
            slider.setString(percent + "%");
            ModelPartConfig config = this.playerdata.getPartConfig(selected);
            if (slider.id == 10) {
                config.scaleX = slider.sliderValue + 0.5f;
            }
            if (slider.id == 11) {
                config.scaleY = slider.sliderValue + 0.5f;
            }
            if (slider.id == 12) {
                config.scaleZ = slider.sliderValue + 0.5f;
            }
            this.updateTransate();
        }
    }

    private void updateTransate() {
        for (EnumParts part : EnumParts.values()) {
            float y;
            float x;
            ModelPartConfig body;
            ModelPartConfig config = this.playerdata.getPartConfig(part);
            if (config == null) continue;
            if (part == EnumParts.HEAD) {
                config.setTranslate(0.0f, this.playerdata.getBodyY(), 0.0f);
                continue;
            }
            if (part == EnumParts.ARM_LEFT) {
                body = this.playerdata.getPartConfig(EnumParts.BODY);
                x = (1.0f - body.scaleX) * 0.25f + (1.0f - config.scaleX) * 0.075f;
                y = this.playerdata.getBodyY() + (1.0f - config.scaleY) * -0.1f;
                config.setTranslate(-x, y, 0.0f);
                if (config.notShared) continue;
                ModelPartConfig arm = this.playerdata.getPartConfig(EnumParts.ARM_RIGHT);
                arm.copyValues(config);
                continue;
            }
            if (part == EnumParts.ARM_RIGHT) {
                body = this.playerdata.getPartConfig(EnumParts.BODY);
                x = (1.0f - body.scaleX) * 0.25f + (1.0f - config.scaleX) * 0.075f;
                y = this.playerdata.getBodyY() + (1.0f - config.scaleY) * -0.1f;
                config.setTranslate(x, y, 0.0f);
                continue;
            }
            if (part == EnumParts.LEG_LEFT) {
                config.setTranslate(config.scaleX * 0.125f - 0.113f, this.playerdata.getLegsY(), 0.0f);
                if (config.notShared) continue;
                ModelPartConfig leg = this.playerdata.getPartConfig(EnumParts.LEG_RIGHT);
                leg.copyValues(config);
                continue;
            }
            if (part == EnumParts.LEG_RIGHT) {
                config.setTranslate((1.0f - config.scaleX) * 0.125f, this.playerdata.getLegsY(), 0.0f);
                continue;
            }
            if (part != EnumParts.BODY) continue;
            config.setTranslate(0.0f, this.playerdata.getBodyY(), 0.0f);
        }
    }

    @Override
    public void scrollClicked(double i, double j, int k, GuiCustomScrollNop scroll) {
        if (scroll.hasSelected()) {
            selected = this.data.get(scroll.getSelectedIndex());
            this.m_7856_();
        }
    }

    @Override
    public void scrollDoubleClicked(String selection, GuiCustomScrollNop scroll) {
    }
}

