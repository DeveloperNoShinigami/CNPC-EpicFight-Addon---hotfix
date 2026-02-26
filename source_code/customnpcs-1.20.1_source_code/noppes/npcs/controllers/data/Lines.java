/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.controllers.data.Line;

public class Lines {
    private static final Random random = new Random();
    private int lastLine = -1;
    public HashMap<Integer, Line> lines = new HashMap();

    public CompoundTag save() {
        CompoundTag compound = new CompoundTag();
        ListTag nbttaglist = new ListTag();
        for (int slot : this.lines.keySet()) {
            Line line = this.lines.get(slot);
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot);
            nbttagcompound.m_128359_("Line", line.getText());
            nbttagcompound.m_128359_("Song", line.getSound());
            nbttaglist.add((Object)nbttagcompound);
        }
        compound.m_128365_("Lines", (Tag)nbttaglist);
        return compound;
    }

    public void readNBT(CompoundTag compound) {
        ListTag nbttaglist = compound.m_128437_("Lines", 10);
        HashMap<Integer, Line> map = new HashMap<Integer, Line>();
        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound = nbttaglist.m_128728_(i);
            Line line = new Line();
            line.setText(nbttagcompound.m_128461_("Line"));
            line.setSound(nbttagcompound.m_128461_("Song"));
            map.put(nbttagcompound.m_128451_("Slot"), line);
        }
        this.lines = map;
    }

    public Line getLine(boolean isRandom) {
        if (this.lines.isEmpty()) {
            return null;
        }
        if (isRandom) {
            int i = random.nextInt(this.lines.size());
            for (Map.Entry<Integer, Line> e : this.lines.entrySet()) {
                if (--i >= 0) continue;
                return e.getValue().copy();
            }
        }
        ++this.lastLine;
        while (true) {
            this.lastLine %= 8;
            Line line = this.lines.get(this.lastLine);
            if (line != null) {
                return line.copy();
            }
            ++this.lastLine;
        }
    }

    public boolean isEmpty() {
        return this.lines.isEmpty();
    }
}

