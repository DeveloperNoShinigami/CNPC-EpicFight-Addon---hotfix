/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import java.util.HashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class PlayerDialogData {
    public HashSet<Integer> dialogsRead = new HashSet();

    public void loadNBTData(CompoundTag compound) {
        HashSet<Integer> dialogsRead = new HashSet<Integer>();
        if (compound == null) {
            return;
        }
        ListTag list = compound.m_128437_("DialogData", 10);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbttagcompound = list.m_128728_(i);
            dialogsRead.add(nbttagcompound.m_128451_("Dialog"));
        }
        this.dialogsRead = dialogsRead;
    }

    public void saveNBTData(CompoundTag compound) {
        ListTag list = new ListTag();
        for (int dia : this.dialogsRead) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Dialog", dia);
            list.add((Object)nbttagcompound);
        }
        compound.m_128365_("DialogData", (Tag)list);
    }
}

