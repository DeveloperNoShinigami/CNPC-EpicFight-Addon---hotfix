/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.client.gui;

import noppes.npcs.Resistances;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiSliderNop;
import noppes.npcs.shared.client.gui.listeners.ISliderListener;

public class SubGuiNpcResistanceProperties
extends GuiBasic
implements ISliderListener {
    private Resistances resistances;

    public SubGuiNpcResistanceProperties(Resistances resistances) {
        this.resistances = resistances;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(0, "enchantment.minecraft.knockback", this.guiLeft + 4, this.guiTop + 15, "guihint.npcknockbackresist"));
        this.addSlider(new GuiSliderNop(this, 0, this.guiLeft + 94, this.guiTop + 10, (int)(this.resistances.knockback * 100.0f - 100.0f) + "%", this.resistances.knockback / 2.0f));
        this.addLabel(new GuiLabel(1, "item.minecraft.arrow", this.guiLeft + 4, this.guiTop + 37, "guihint.npcarrowsresist"));
        this.addSlider(new GuiSliderNop(this, 1, this.guiLeft + 94, this.guiTop + 32, (int)(this.resistances.arrow * 100.0f - 100.0f) + "%", this.resistances.arrow / 2.0f));
        this.addLabel(new GuiLabel(2, "stats.melee", this.guiLeft + 4, this.guiTop + 59, "guihint.npcmeleeresist"));
        this.addSlider(new GuiSliderNop(this, 2, this.guiLeft + 94, this.guiTop + 54, (int)(this.resistances.melee * 100.0f - 100.0f) + "%", this.resistances.melee / 2.0f));
        this.addLabel(new GuiLabel(3, "stats.explosion", this.guiLeft + 4, this.guiTop + 81, "guihint.npcexplosionresist"));
        this.addSlider(new GuiSliderNop(this, 3, this.guiLeft + 94, this.guiTop + 76, (int)(this.resistances.explosion * 100.0f - 100.0f) + "%", this.resistances.explosion / 2.0f));
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id == 66) {
            this.close();
        }
    }

    @Override
    public void mouseDragged(GuiSliderNop slider) {
        slider.setString((int)(slider.sliderValue * 200.0f - 100.0f) + "%");
    }

    @Override
    public void mousePressed(GuiSliderNop slider) {
    }

    @Override
    public void mouseReleased(GuiSliderNop slider) {
        if (slider.id == 0) {
            this.resistances.knockback = slider.sliderValue * 2.0f;
        }
        if (slider.id == 1) {
            this.resistances.arrow = slider.sliderValue * 2.0f;
        }
        if (slider.id == 2) {
            this.resistances.melee = slider.sliderValue * 2.0f;
        }
        if (slider.id == 3) {
            this.resistances.explosion = slider.sliderValue * 2.0f;
        }
    }
}

