/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.syncher.SynchedEntityData$DataItem
 */
package noppes.npcs.client;

import java.util.List;
import net.minecraft.network.syncher.SynchedEntityData;

public interface ISynchedEntityData {
    public List<SynchedEntityData.DataItem<?>> getAll();
}

