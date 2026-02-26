/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 */
package noppes.npcs.client.gui.global;

import net.minecraft.client.gui.screens.Screen;
import noppes.npcs.client.gui.SubGuiColorSelector;
import noppes.npcs.client.gui.select.GuiDialogSelection;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;
import noppes.npcs.shared.client.gui.listeners.ITextfieldListener;

public class SubGuiNpcDialogOption
extends GuiBasic
implements ITextfieldListener {
    private DialogOption option;
    public static int LastColor = 0xE0E0E0;

    public SubGuiNpcDialogOption(DialogOption option) {
        this.option = option;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(66, "dialog.editoption", this.guiLeft, this.guiTop + 4, this.imageWidth, 0));
        this.addLabel(new GuiLabel(0, "gui.title", this.guiLeft + 4, this.guiTop + 20));
        this.addTextField(new GuiTextFieldNop(0, (Screen)this, this.guiLeft + 40, this.guiTop + 15, 196, 20, this.option.title));
        Object color = Integer.toHexString(this.option.optionColor);
        while (((String)color).length() < 6) {
            color = "0" + (String)color;
        }
        this.addLabel(new GuiLabel(2, "gui.color", this.guiLeft + 4, this.guiTop + 45));
        this.addButton(new GuiButtonNop(this, 2, this.guiLeft + 62, this.guiTop + 40, 92, 20, (String)color));
        this.getButton(2).setFGColor(this.option.optionColor);
        this.addLabel(new GuiLabel(1, "dialog.optiontype", this.guiLeft + 4, this.guiTop + 67));
        this.addButton(new GuiButtonNop((IGuiInterface)this, 1, this.guiLeft + 62, this.guiTop + 62, 92, 20, new String[]{"gui.close", "dialog.dialog", "gui.disabled", "menu.role", "block.minecraft.command_block"}, this.option.optionType));
        if (this.option.optionType == 1) {
            Dialog dialog;
            this.addButton(new GuiButtonNop(this, 3, this.guiLeft + 4, this.guiTop + 84, "availability.selectdialog"));
            if (this.option.dialogId >= 0 && (dialog = DialogController.instance.dialogs.get(this.option.dialogId)) != null) {
                this.getButton(3).setDisplayText(dialog.title);
            }
        }
        if (this.option.optionType == 4) {
            this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 4, this.guiTop + 84, 248, 20, this.option.command));
            this.getTextField(4).m_94199_(Short.MAX_VALUE);
            this.addLabel(new GuiLabel(4, "advMode.command", this.guiLeft + 4, this.guiTop + 110));
            this.addLabel(new GuiLabel(5, "advMode.nearestPlayer", this.guiLeft + 4, this.guiTop + 125));
            this.addLabel(new GuiLabel(6, "advMode.randomPlayer", this.guiLeft + 4, this.guiTop + 140));
            this.addLabel(new GuiLabel(7, "advMode.allPlayers", this.guiLeft + 4, this.guiTop + 155));
            this.addLabel(new GuiLabel(8, "dialog.commandoptionplayer", this.guiLeft + 4, this.guiTop + 170));
        }
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 82, this.guiTop + 190, 98, 20, "gui.done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        GuiButtonNop button = guibutton;
        if (button.id == 1) {
            this.option.optionType = button.getValue();
            this.m_7856_();
        }
        if (button.id == 2) {
            this.setSubGui(new SubGuiColorSelector(this.option.optionColor));
        }
        if (button.id == 3) {
            this.setSubGui(new GuiDialogSelection(this.option.dialogId));
        }
        if (button.id == 66) {
            this.close();
        }
    }

    @Override
    public void unFocused(GuiTextFieldNop textfield) {
        if (textfield.id == 0) {
            if (textfield.isEmpty()) {
                textfield.m_94144_(this.option.title);
            } else {
                this.option.title = textfield.m_94155_();
            }
        }
        if (textfield.id == 4) {
            this.option.command = textfield.m_94155_();
        }
    }

    @Override
    public void subGuiClosed(Screen subgui) {
        Dialog dialog;
        if (subgui instanceof SubGuiColorSelector) {
            LastColor = this.option.optionColor = ((SubGuiColorSelector)subgui).color;
        }
        if (subgui instanceof GuiDialogSelection && (dialog = ((GuiDialogSelection)subgui).selectedDialog) != null) {
            this.option.dialogId = dialog.id;
        }
        this.m_7856_();
    }
}

