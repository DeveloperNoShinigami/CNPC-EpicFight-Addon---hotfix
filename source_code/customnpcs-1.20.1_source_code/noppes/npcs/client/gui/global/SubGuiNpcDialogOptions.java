/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.client.gui.global;

import noppes.npcs.client.gui.global.SubGuiNpcDialogOption;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.shared.client.gui.components.GuiBasic;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;

public class SubGuiNpcDialogOptions
extends GuiBasic {
    private Dialog dialog;

    public SubGuiNpcDialogOptions(Dialog dialog) {
        this.dialog = dialog;
        this.setBackground("menubg.png");
        this.imageWidth = 256;
        this.imageHeight = 216;
    }

    @Override
    public void m_7856_() {
        super.m_7856_();
        this.addLabel(new GuiLabel(66, "dialog.options", this.guiLeft, this.guiTop + 4, this.imageWidth, 0));
        for (int i = 0; i < 6; ++i) {
            Object optionString = "";
            DialogOption option = this.dialog.options.get(i);
            if (option != null && option.optionType != 2) {
                optionString = (String)optionString + option.title;
            }
            this.addLabel(new GuiLabel(i + 10, i + 1 + ": ", this.guiLeft + 4, this.guiTop + 16 + i * 32));
            this.addLabel(new GuiLabel(i, (String)optionString, this.guiLeft + 14, this.guiTop + 12 + i * 32));
            this.addButton(new GuiButtonNop(this, i, this.guiLeft + 13, this.guiTop + 21 + i * 32, 60, 20, "selectServer.edit"));
        }
        this.addButton(new GuiButtonNop(this, 66, this.guiLeft + 82, this.guiTop + 194, 98, 20, "gui.done"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        int id = guibutton.id;
        if (id < 6) {
            DialogOption option = this.dialog.options.get(id);
            if (option == null) {
                option = new DialogOption();
                this.dialog.options.put(id, option);
                option.optionColor = SubGuiNpcDialogOption.LastColor;
            }
            this.setSubGui(new SubGuiNpcDialogOption(option));
        }
        if (id == 66) {
            this.close();
        }
    }
}

