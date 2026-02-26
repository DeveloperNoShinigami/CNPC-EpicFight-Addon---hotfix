/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.api.handler.data.IDialog;
import noppes.npcs.api.handler.data.IDialogCategory;
import noppes.npcs.controllers.data.Dialog;

public class DialogCategory
implements IDialogCategory {
    public int id = -1;
    public String title = "";
    public HashMap<Integer, Dialog> dialogs = new HashMap();

    public void readNBT(CompoundTag compound) {
        this.id = compound.m_128451_("Slot");
        this.title = compound.m_128461_("Title");
        ListTag dialogsList = compound.m_128437_("Dialogs", 10);
        if (dialogsList != null) {
            for (int ii = 0; ii < dialogsList.size(); ++ii) {
                Dialog dialog = new Dialog(this);
                CompoundTag comp = dialogsList.m_128728_(ii);
                dialog.readNBT(comp);
                dialog.id = comp.m_128451_("DialogId");
                this.dialogs.put(dialog.id, dialog);
            }
        }
    }

    public CompoundTag writeNBT(CompoundTag compound) {
        compound.m_128405_("Slot", this.id);
        compound.m_128359_("Title", this.title);
        ListTag dialogs = new ListTag();
        for (Dialog dialog : this.dialogs.values()) {
            dialogs.add((Object)dialog.save(new CompoundTag()));
        }
        compound.m_128365_("Dialogs", (Tag)dialogs);
        return compound;
    }

    @Override
    public List<IDialog> dialogs() {
        return new ArrayList<IDialog>(this.dialogs.values());
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public IDialog create() {
        return new Dialog(this);
    }
}

