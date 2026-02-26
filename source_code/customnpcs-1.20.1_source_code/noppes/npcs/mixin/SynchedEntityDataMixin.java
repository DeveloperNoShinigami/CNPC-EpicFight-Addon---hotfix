/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$DataItem
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package noppes.npcs.mixin;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.network.syncher.SynchedEntityData;
import noppes.npcs.client.ISynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={SynchedEntityData.class})
public class SynchedEntityDataMixin
implements ISynchedEntityData {
    @Shadow
    private final Int2ObjectMap<SynchedEntityData.DataItem<?>> f_135345_ = new Int2ObjectOpenHashMap();
    @Shadow
    private final ReadWriteLock f_135346_ = new ReentrantReadWriteLock();

    @Override
    @Nullable
    public List<SynchedEntityData.DataItem<?>> getAll() {
        ArrayList list = null;
        this.f_135346_.readLock().lock();
        for (SynchedEntityData.DataItem dataitem : this.f_135345_.values()) {
            if (list == null) {
                list = Lists.newArrayList();
            }
            list.add(new SynchedEntityData.DataItem(dataitem.m_135396_(), (Object)dataitem.m_253123_()));
        }
        this.f_135346_.readLock().unlock();
        return list;
    }
}

