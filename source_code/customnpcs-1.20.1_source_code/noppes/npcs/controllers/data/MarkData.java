/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.CapabilityManager
 *  net.minecraftforge.common.capabilities.CapabilityToken
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.event.AttachCapabilitiesEvent
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import noppes.npcs.api.entity.data.IMark;
import noppes.npcs.api.handler.data.IAvailability;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketMarkData;

public class MarkData
implements ICapabilityProvider {
    public static Capability<MarkData> MARKDATA_CAPABILITY = CapabilityManager.get((CapabilityToken)new CapabilityToken<MarkData>(){});
    private static final String NBTKEY = "cnpcmarkdata";
    private static final ResourceLocation CAPKEY = new ResourceLocation("customnpcs", "markdata");
    private LivingEntity entity;
    private LazyOptional<MarkData> instance = LazyOptional.of(() -> this);
    public List<Mark> marks = new ArrayList<Mark>();
    private static MarkData backup = new MarkData();

    public void setNBT(CompoundTag compound) {
        ArrayList<Mark> marks = new ArrayList<Mark>();
        ListTag list = compound.m_128437_("marks", 10);
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag c = list.m_128728_(i);
            Mark m = new Mark();
            m.type = c.m_128451_("type");
            m.color = c.m_128451_("color");
            m.availability.load(c.m_128469_("availability"));
            marks.add(m);
        }
        this.marks = marks;
    }

    public CompoundTag getNBT() {
        CompoundTag compound = new CompoundTag();
        ListTag list = new ListTag();
        for (Mark m : this.marks) {
            CompoundTag c = new CompoundTag();
            c.m_128405_("type", m.type);
            c.m_128405_("color", m.color);
            c.m_128365_("availability", (Tag)m.availability.save(new CompoundTag()));
            list.add((Object)c);
        }
        compound.m_128365_("marks", (Tag)list);
        return compound;
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == MARKDATA_CAPABILITY) {
            return this.instance.cast();
        }
        return LazyOptional.empty();
    }

    public static void register(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(CAPKEY, (ICapabilityProvider)new MarkData());
    }

    public void save() {
        this.entity.getPersistentData().m_128365_(NBTKEY, (Tag)this.getNBT());
    }

    public IMark addMark(int type) {
        Mark m = new Mark();
        m.type = type;
        this.marks.add(m);
        if (!this.entity.m_9236_().f_46443_) {
            this.syncClients();
        }
        return m;
    }

    public IMark addMark(int type, int color) {
        Mark m = new Mark();
        m.type = type;
        m.color = color;
        this.marks.add(m);
        if (!this.entity.m_9236_().f_46443_) {
            this.syncClients();
        }
        return m;
    }

    public static MarkData get(LivingEntity entity) {
        MarkData data = (MarkData)entity.getCapability(MARKDATA_CAPABILITY, null).orElse((Object)backup);
        if (data.entity == null) {
            data.entity = entity;
            data.setNBT(entity.getPersistentData().m_128469_(NBTKEY));
        }
        return data;
    }

    public void syncClients() {
        Packets.sendAll(new PacketMarkData(this.entity.m_19879_(), this.getNBT()));
    }

    public class Mark
    implements IMark {
        public int type = 0;
        public Availability availability = new Availability();
        public int color = 16772433;

        @Override
        public IAvailability getAvailability() {
            return this.availability;
        }

        @Override
        public int getColor() {
            return this.color;
        }

        @Override
        public void setColor(int color) {
            this.color = color;
        }

        @Override
        public int getType() {
            return this.type;
        }

        @Override
        public void setType(int type) {
            this.type = type;
        }

        @Override
        public void update() {
            MarkData.this.syncClients();
        }
    }
}

