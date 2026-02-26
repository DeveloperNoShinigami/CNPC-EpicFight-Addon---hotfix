/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.api.handler.data.IDialogOption;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.db.DatabaseColumn;

public class DialogOption
implements IDialogOption {
    @DatabaseColumn(name="id", type=DatabaseColumn.Type.INT)
    public int id = -1;
    @DatabaseColumn(name="dialog", type=DatabaseColumn.Type.INT)
    public int dialogId = -1;
    @DatabaseColumn(name="option", type=DatabaseColumn.Type.VARCHAR)
    public String option = "Talk";
    @DatabaseColumn(name="text", type=DatabaseColumn.Type.TEXT)
    public String title = "Talk";
    @DatabaseColumn(name="type", type=DatabaseColumn.Type.SMALLINT)
    public int optionType = 1;
    @DatabaseColumn(name="color", type=DatabaseColumn.Type.SMALLINT)
    public int optionColor = 0xE0E0E0;
    @DatabaseColumn(name="command", type=DatabaseColumn.Type.TEXT)
    public String command = "";
    @DatabaseColumn(name="order", type=DatabaseColumn.Type.SMALLINT)
    public int slot = -1;

    public void readNBT(CompoundTag compound) {
        if (compound == null) {
            return;
        }
        this.title = compound.m_128461_("Title");
        this.dialogId = compound.m_128451_("Dialog");
        this.optionColor = compound.m_128451_("DialogColor");
        this.optionType = compound.m_128451_("OptionType");
        this.command = compound.m_128461_("DialogCommand");
        if (this.optionColor == 0) {
            this.optionColor = 0xE0E0E0;
        }
    }

    public CompoundTag writeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.m_128359_("Title", this.title);
        compound.m_128405_("OptionType", this.optionType);
        compound.m_128405_("Dialog", this.dialogId);
        compound.m_128405_("DialogColor", this.optionColor);
        compound.m_128359_("DialogCommand", this.command);
        return compound;
    }

    public boolean hasDialog() {
        if (this.dialogId <= 0 || this.optionType != 1) {
            return false;
        }
        return DialogController.instance.hasDialog(this.dialogId);
    }

    public Dialog getDialog() {
        if (!this.hasDialog()) {
            return null;
        }
        return DialogController.instance.dialogs.get(this.dialogId);
    }

    public boolean isAvailable(Player player) {
        if (this.optionType == 2) {
            return false;
        }
        if (this.optionType != 1) {
            return true;
        }
        Dialog dialog = this.getDialog();
        if (dialog == null) {
            return false;
        }
        return dialog.availability.isAvailable(player);
    }

    public boolean isValid() {
        if (this.optionType == 2) {
            return false;
        }
        return this.optionType != 1 || this.hasDialog();
    }

    public boolean canClose() {
        return this.optionType != 1 || !this.hasDialog();
    }

    @Override
    public int getSlot() {
        return this.slot;
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public int getType() {
        return this.optionType;
    }
}

