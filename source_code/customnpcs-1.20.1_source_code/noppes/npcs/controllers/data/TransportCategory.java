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
import java.util.Vector;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.controllers.data.TransportLocation;

public class TransportCategory {
    public int id = -1;
    public String title = "";
    public HashMap<Integer, TransportLocation> locations = new HashMap();

    public Vector<TransportLocation> getDefaultLocations() {
        Vector<TransportLocation> list = new Vector<TransportLocation>();
        for (TransportLocation loc : this.locations.values()) {
            if (!loc.isDefault()) continue;
            list.add(loc);
        }
        return list;
    }

    public void readNBT(CompoundTag compound) {
        this.id = compound.m_128451_("CategoryId");
        this.title = compound.m_128461_("CategoryTitle");
        ListTag locs = compound.m_128437_("CategoryLocations", 10);
        if (locs == null || locs.size() == 0) {
            return;
        }
        for (int ii = 0; ii < locs.size(); ++ii) {
            TransportLocation location = new TransportLocation();
            location.readNBT(locs.m_128728_(ii));
            location.category = this;
            this.locations.put(location.id, location);
        }
    }

    public void writeNBT(CompoundTag compound) {
        compound.m_128405_("CategoryId", this.id);
        compound.m_128359_("CategoryTitle", this.title);
        ListTag locs = new ListTag();
        for (TransportLocation location : this.locations.values()) {
            locs.add((Object)location.writeNBT());
        }
        compound.m_128365_("CategoryLocations", (Tag)locs);
    }
}

