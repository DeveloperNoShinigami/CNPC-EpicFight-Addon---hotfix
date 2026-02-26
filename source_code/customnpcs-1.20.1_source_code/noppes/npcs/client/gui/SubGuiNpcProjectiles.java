/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 */
package noppes.npcs.client.gui;

import java.util.ArrayList;
import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.api.constants.PotionEffectType;
import noppes.npcs.entity.data.DataRanged;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonBiDirectional;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class SubGuiNpcProjectiles
extends GuiBasic
implements ITextfieldListener {
    private DataRanged stats;
    private static final String[] potionNames;
    private String[] trailNames = new String[]{"gui.none", "Smoke", "Portal", "Redstone", "Lightning", "LargeSmoke", "Magic", "Enchant"};

    public SubGuiNpcProjectiles(DataRanged stats) {
        this.stats = stats;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(1, "effect.minecraft.strength", this.guiLeft + 5, this.guiTop + 15, "guihint.npcstrength"));
        this.addTextField(new GuiTextFieldNop(1, (Screen)this, this.guiLeft + 45, this.guiTop + 10, 50, 18, "" + this.stats.getStrength()));
        this.getTextField((int)1).numbersOnly = true;
        this.getTextField(1).setMinMaxDefault(0, Integer.MAX_VALUE, 5);
        this.addLabel(new GuiLabel(2, "enchantment.minecraft.knockback", this.guiLeft + 110, this.guiTop + 15, "guihint.npcknockbackprojectile"));
        this.addTextField(new GuiTextFieldNop(2, (Screen)this, this.guiLeft + 150, this.guiTop + 10, 50, 18, "" + this.stats.getKnockback()));
        this.getTextField((int)2).numbersOnly = true;
        this.getTextField(2).setMinMaxDefault(0, 3, 0);
        this.addLabel(new GuiLabel(3, "stats.size", this.guiLeft + 5, this.guiTop + 45, "guihint.npcsizeprojectile"));
        this.addTextField(new GuiTextFieldNop(3, (Screen)this, this.guiLeft + 45, this.guiTop + 40, 50, 18, "" + this.stats.getSize()));
        this.getTextField((int)3).numbersOnly = true;
        this.getTextField(3).setMinMaxDefault(5, 20, 10);
        this.addLabel(new GuiLabel(4, "stats.speed", this.guiLeft + 5, this.guiTop + 75, "guihint.npcspeedprojectile"));
        this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 45, this.guiTop + 70, 50, 18, "" + this.stats.getSpeed()));
        this.getTextField((int)4).numbersOnly = true;
        this.getTextField(4).setMinMaxDefault(1, 50, 10);
        this.addLabel(new GuiLabel(5, "stats.hasgravity", this.guiLeft + 5, this.guiTop + 105, "guihint.npcgravity"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 60, this.guiTop + 100, 60, 20, new String[]{"gui.no", "gui.yes"}, this.stats.getHasGravity() ? 1 : 0));
        if (!this.stats.getHasGravity()) {
            this.addButton(new GuiButtonNop((IGuiInterface)this, 1, this.guiLeft + 140, this.guiTop + 100, 60, 20, new String[]{"gui.constant", "gui.accelerate"}, this.stats.getAccelerate() ? 1 : 0));
        }
        this.addLabel(new GuiLabel(6, "stats.explosive", this.guiLeft + 5, this.guiTop + 135, "guihint.npcexplodes"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 3, this.guiLeft + 60, this.guiTop + 130, 60, 20, new String[]{"gui.none", "gui.small", "gui.medium", "gui.large"}, this.stats.getExplodeSize() % 4));
        int effect = this.stats.getEffectType();
        if (effect == 666) {
            effect = potionNames.length - 1;
        }
        this.addLabel(new GuiLabel(7, "stats.rangedeffect", this.guiLeft + 5, this.guiTop + 165, "guihint.npceffectprojectile"));
        this.addButton(new GuiButtonBiDirectional((IGuiInterface)this, 4, this.guiLeft + 40, this.guiTop + 160, 100, 20, potionNames, effect));
        if (this.stats.getEffectType() != 0) {
            this.addTextField(new GuiTextFieldNop(5, (Screen)this, this.guiLeft + 140, this.guiTop + 160, 60, 18, "" + this.stats.getEffectTime()));
            this.getTextField((int)5).numbersOnly = true;
            this.getTextField(5).setMinMaxDefault(1, 99999, 5);
            if (this.stats.getEffectType() != 666) {
                this.addButton(new GuiButtonNop((IGuiInterface)this, 10, this.guiLeft + 210, this.guiTop + 160, 40, 20, new String[]{"stats.regular", "stats.amplified"}, this.stats.getEffectStrength() % 2));
            }
        }
        this.addLabel(new GuiLabel(8, "stats.trail", this.guiLeft + 5, this.guiTop + 195, "guihint.npctrailprojectile"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 5, this.guiLeft + 60, this.guiTop + 190, 60, 20, this.trailNames, this.stats.getParticle()));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 7, this.guiLeft + 220, this.guiTop + 10, 30, 20, new String[]{"2D", "3D"}, this.stats.getRender3D() ? 1 : 0));
        if (this.stats.getRender3D()) {
            this.addLabel(new GuiLabel(10, "stats.spin", this.guiLeft + 160, this.guiTop + 45));
            this.addButton(new GuiButtonNop((IGuiInterface)this, 8, this.guiLeft + 220, this.guiTop + 40, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.getSpins() ? 1 : 0));
            this.addLabel(new GuiLabel(11, "stats.stick", this.guiLeft + 160, this.guiTop + 75));
            this.addButton(new GuiButtonNop((IGuiInterface)this, 9, this.guiLeft + 220, this.guiTop + 70, 30, 20, new String[]{"gui.no", "gui.yes"}, this.stats.getSticks() ? 1 : 0));
        }
        this.addButton(new GuiButtonNop((IGuiInterface)this, 6, this.guiLeft + 140, this.guiTop + 190, 60, 20, new String[]{"stats.noglow", "stats.glows"}, this.stats.getGlows() ? 1 : 0));
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 210, this.guiTop + 190, 40, 20, "gui.done"));
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 1) {
            this.stats.setStrength(textfield.getInteger());
        } else if (textfield.id == 2) {
            this.stats.setKnockback(textfield.getInteger());
        } else if (textfield.id == 3) {
            this.stats.setSize(textfield.getInteger());
        } else if (textfield.id == 4) {
            this.stats.setSpeed(textfield.getInteger());
        } else if (textfield.id == 5) {
            this.stats.setEffect(this.stats.getEffectType(), this.stats.getEffectStrength(), textfield.getInteger());
        }
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            this.stats.setHasGravity(button.getValue() == 1);
            this.m_7856_();
        }
        if (button.id == 1) {
            this.stats.setAccelerate(button.getValue() == 1);
        }
        if (button.id == 3) {
            this.stats.setExplodeSize(button.getValue());
        }
        if (button.id == 4) {
            int effect = button.getValue();
            if (effect == potionNames.length - 1) {
                effect = 666;
            }
            this.stats.setEffect(effect, this.stats.getEffectStrength(), this.stats.getEffectTime());
            this.m_7856_();
        }
        if (button.id == 5) {
            this.stats.setParticle(button.getValue());
        }
        if (button.id == 6) {
            this.stats.setGlows(button.getValue() == 1);
        }
        if (button.id == 7) {
            this.stats.setRender3D(button.getValue() == 1);
            this.m_7856_();
        }
        if (button.id == 8) {
            this.stats.setSpins(button.getValue() == 1);
        }
        if (button.id == 9) {
            this.stats.setSticks(button.getValue() == 1);
        }
        if (button.id == 10) {
            this.stats.setEffect(this.stats.getEffectType(), button.getValue(), this.stats.getEffectTime());
        }
        if (button.id == 66) {
            this.close();
        }
    }

    static {
        ArrayList<String> list = new ArrayList<String>();
        list.add("gui.none");
        for (int i = 1; i < 33; ++i) {
            list.add(PotionEffectType.getMCType(i).m_19481_());
        }
        list.add("block.minecraft.fire");
        potionNames = list.toArray(new String[list.size()]);
    }
}

