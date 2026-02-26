/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 */
package noppes.npcs.client.gui;

import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.ai.EntityAIAnimation;
import noppes.npcs.entity.data.DataAI;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class SubGuiNpcMovement
extends GuiBasic
implements ITextfieldListener {
    private DataAI ai;

    public SubGuiNpcMovement(DataAI ai) {
        this.ai = ai;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        int y = this.guiTop + 4;
        this.addLabel(new GuiLabel(0, "movement.type", this.guiLeft + 4, y + 5, "guihint.movement.movetype"));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 0, this.guiLeft + 80, y, 100, 20, new String[]{"ai.standing", "ai.wandering", "ai.movingpath"}, this.ai.getMovingType()));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 15, this.guiLeft + 80, y += 22, 100, 20, new String[]{"movement.ground", "movement.flying", "movement.swimming"}, this.ai.movementType));
        this.addLabel(new GuiLabel(15, "movement.navigation", this.guiLeft + 4, y + 5, "guihint.movement.navigation"));
        if (this.ai.getMovingType() == 1) {
            this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 100, y += 22, 60, 20, "" + this.ai.walkingRange));
            this.getTextField((int)4).numbersOnly = true;
            this.getTextField(4).setMinMaxDefault(0, 1000, 10);
            this.addLabel(new GuiLabel(4, "gui.range", this.guiLeft + 4, y + 5));
            this.addTextField(new GuiTextFieldNop(10, (Screen)this, this.guiLeft + 100, y += 22, 60, 20, "" + this.ai.activeRange));
            this.getTextField((int)10).numbersOnly = true;
            this.getTextField(10).setMinMaxDefault(32, 1000, 10);
            this.addLabel(new GuiLabel(10, "gui.activerange", this.guiLeft + 4, y + 5));
            this.addButton(new GuiButtonNop((IGuiInterface)this, 5, this.guiLeft + 100, y += 22, 50, 20, new String[]{"gui.no", "gui.yes"}, this.ai.npcInteracting ? 1 : 0));
            this.addLabel(new GuiLabel(5, "movement.wanderinteract", this.guiLeft + 4, y + 5));
            this.addButton(new GuiButtonNop((IGuiInterface)this, 9, this.guiLeft + 80, y += 22, 80, 20, new String[]{"gui.no", "gui.yes"}, this.ai.movingPause ? 1 : 0));
            this.addLabel(new GuiLabel(9, "movement.pauses", this.guiLeft + 4, y + 5));
        } else if (this.ai.getMovingType() == 0) {
            this.addLabel(new GuiLabel(17, "spawner.posoffset", this.guiLeft + 4, y + 27, "guihint.movement.pos"));
            this.addLabel(new GuiLabel(7, "X:", this.guiLeft + 89, y + 27));
            this.addTextField(new GuiTextFieldNop(7, (Screen)this, this.guiLeft + 99, y += 22, 24, 20, "" + (int)this.ai.bodyOffsetX));
            this.getTextField((int)7).numbersOnly = true;
            this.getTextField(7).setMinMaxDefault(0, 10, 5);
            this.addLabel(new GuiLabel(8, "Y:", this.guiLeft + 125, y + 5));
            this.addTextField(new GuiTextFieldNop(8, (Screen)this, this.guiLeft + 135, y, 24, 20, "" + (int)this.ai.bodyOffsetY));
            this.getTextField((int)8).numbersOnly = true;
            this.getTextField(8).setMinMaxDefault(0, 100, 5);
            this.addLabel(new GuiLabel(9, "Z:", this.guiLeft + 161, y + 5));
            this.addTextField(new GuiTextFieldNop(9, (Screen)this, this.guiLeft + 171, y, 24, 20, "" + (int)this.ai.bodyOffsetZ));
            this.getTextField((int)9).numbersOnly = true;
            this.getTextField(9).setMinMaxDefault(0, 10, 5);
            this.addButton(new GuiButtonNop((IGuiInterface)this, 3, this.guiLeft + 80, y += 22, 100, 20, new String[]{"stats.normal", "movement.sitting", "movement.lying", "movement.hug", "movement.sneaking", "movement.dancing", "movement.aiming", "movement.crawling"}, this.ai.animationType));
            this.addLabel(new GuiLabel(3, "movement.animation", this.guiLeft + 4, y + 5, "guihint.movement.animation"));
            if (this.ai.animationType != 2) {
                this.addButton(new GuiButtonNop((IGuiInterface)this, 4, this.guiLeft + 80, y += 22, 80, 20, new String[]{"movement.body", "movement.manual", "movement.stalking", "movement.head"}, this.ai.getStandingType()));
                this.addLabel(new GuiLabel(1, "movement.rotation", this.guiLeft + 4, y + 5, "guihint.movement.rotation"));
            } else {
                this.addTextField(new GuiTextFieldNop(5, (Screen)this, this.guiLeft + 99, y += 22, 40, 20, "" + this.ai.orientation));
                this.getTextField((int)5).numbersOnly = true;
                this.getTextField(5).setMinMaxDefault(0, 359, 0);
                this.addLabel(new GuiLabel(6, "movement.rotation", this.guiLeft + 4, y + 5));
                this.addLabel(new GuiLabel(5, "(0-359)", this.guiLeft + 142, y + 5));
            }
            if (this.ai.getStandingType() == 1 || this.ai.getStandingType() == 3) {
                this.addTextField(new GuiTextFieldNop(5, (Screen)this, this.guiLeft + 165, y, 40, 20, "" + this.ai.orientation));
                this.getTextField((int)5).numbersOnly = true;
                this.getTextField(5).setMinMaxDefault(0, 359, 0);
                this.addLabel(new GuiLabel(5, "(0-359)", this.guiLeft + 207, y + 5));
            }
        }
        if (this.ai.getMovingType() != 0) {
            this.addButton(new GuiButtonNop((IGuiInterface)this, 12, this.guiLeft + 80, y += 22, 100, 20, new String[]{"stats.normal", "movement.sneaking", "movement.aiming", "movement.dancing", "movement.crawling", "movement.hug"}, EntityAIAnimation.getWalkingAnimationGuiIndex(this.ai.animationType)));
            this.addLabel(new GuiLabel(12, "movement.animation", this.guiLeft + 4, y + 5));
        }
        if (this.ai.getMovingType() == 2) {
            this.addButton(new GuiButtonNop((IGuiInterface)this, 8, this.guiLeft + 80, y += 22, 80, 20, new String[]{"ai.looping", "ai.backtracking"}, this.ai.movingPattern));
            this.addLabel(new GuiLabel(8, "movement.name", this.guiLeft + 4, y + 5));
            this.addButton(new GuiButtonNop((IGuiInterface)this, 9, this.guiLeft + 80, y += 22, 80, 20, new String[]{"gui.no", "gui.yes"}, this.ai.movingPause ? 1 : 0));
            this.addLabel(new GuiLabel(9, "movement.pauses", this.guiLeft + 4, y + 5));
        }
        this.addButton(new GuiButtonNop((IGuiInterface)this, 13, this.guiLeft + 100, y += 22, 50, 20, new String[]{"gui.no", "gui.yes"}, this.ai.stopAndInteract ? 1 : 0));
        this.addLabel(new GuiLabel(13, "movement.stopinteract", this.guiLeft + 4, y + 5, "guihint.movement.isignore"));
        this.addTextField(new GuiTextFieldNop(14, (Screen)this, this.guiLeft + 80, y += 22, 50, 18, "" + this.ai.getWalkingSpeed()));
        this.getTextField((int)14).numbersOnly = true;
        this.getTextField(14).setMinMaxDefault(0, 100, 4);
        this.addLabel(new GuiLabel(14, "stats.movespeed", this.guiLeft + 5, y + 5, "guihint.movement.speed"));
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 190, this.guiTop + 190, 60, 20, "gui.done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 0) {
            this.ai.setMovingType(button.getValue());
            if (this.ai.getMovingType() != 0) {
                this.ai.animationType = 0;
                this.ai.setStandingType(0);
                this.ai.bodyOffsetZ = 5.0f;
                this.ai.bodyOffsetY = 5.0f;
                this.ai.bodyOffsetX = 5.0f;
            }
            this.m_7856_();
        } else if (button.id == 3) {
            this.ai.animationType = button.getValue();
            this.m_7856_();
        } else if (button.id == 4) {
            this.ai.setStandingType(button.getValue());
            this.m_7856_();
        } else if (button.id == 5) {
            this.ai.npcInteracting = button.getValue() == 1;
        } else if (button.id == 8) {
            this.ai.movingPattern = button.getValue();
        } else if (button.id == 9) {
            this.ai.movingPause = button.getValue() == 1;
        } else if (button.id == 12) {
            if (button.getValue() == 0) {
                this.ai.animationType = 0;
            }
            if (button.getValue() == 1) {
                this.ai.animationType = 4;
            }
            if (button.getValue() == 2) {
                this.ai.animationType = 6;
            }
            if (button.getValue() == 3) {
                this.ai.animationType = 5;
            }
            if (button.getValue() == 4) {
                this.ai.animationType = 7;
            }
            if (button.getValue() == 5) {
                this.ai.animationType = 3;
            }
        } else if (button.id == 13) {
            this.ai.stopAndInteract = button.getValue() == 1;
        } else if (button.id == 15) {
            this.ai.movementType = button.getValue();
        } else if (button.id == 66) {
            this.close();
        }
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 7) {
            this.ai.bodyOffsetX = textfield.getInteger();
        } else if (textfield.id == 8) {
            this.ai.bodyOffsetY = textfield.getInteger();
        } else if (textfield.id == 9) {
            this.ai.bodyOffsetZ = textfield.getInteger();
        } else if (textfield.id == 5) {
            this.ai.orientation = textfield.getInteger();
        } else if (textfield.id == 4) {
            this.ai.walkingRange = textfield.getInteger();
        } else if (textfield.id == 10) {
            this.ai.activeRange = textfield.getInteger();
        } else if (textfield.id == 14) {
            this.ai.setWalkingSpeed(textfield.getInteger());
        }
    }
}

