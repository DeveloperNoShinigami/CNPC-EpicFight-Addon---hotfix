/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.DoubleTag
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntArrayTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.api.wrapper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.INbt;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.util.NBTJsonUtil;

public class NBTWrapper
implements INbt {
    private CompoundTag compound;

    public NBTWrapper(CompoundTag compound) {
        this.compound = compound;
    }

    @Override
    public void remove(String key) {
        this.compound.m_128473_(key);
    }

    @Override
    public boolean has(String key) {
        return this.compound.m_128441_(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return this.compound.m_128471_(key);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        this.compound.m_128379_(key, value);
    }

    @Override
    public short getShort(String key) {
        return this.compound.m_128448_(key);
    }

    @Override
    public void setShort(String key, short value) {
        this.compound.m_128376_(key, value);
    }

    @Override
    public int getInteger(String key) {
        return this.compound.m_128451_(key);
    }

    @Override
    public void setInteger(String key, int value) {
        this.compound.m_128405_(key, value);
    }

    @Override
    public byte getByte(String key) {
        return this.compound.m_128445_(key);
    }

    @Override
    public void setByte(String key, byte value) {
        this.compound.m_128344_(key, value);
    }

    @Override
    public long getLong(String key) {
        return this.compound.m_128454_(key);
    }

    @Override
    public void setLong(String key, long value) {
        this.compound.m_128356_(key, value);
    }

    @Override
    public double getDouble(String key) {
        return this.compound.m_128459_(key);
    }

    @Override
    public void setDouble(String key, double value) {
        this.compound.m_128347_(key, value);
    }

    @Override
    public float getFloat(String key) {
        return this.compound.m_128457_(key);
    }

    @Override
    public void setFloat(String key, float value) {
        this.compound.m_128350_(key, value);
    }

    @Override
    public String getString(String key) {
        return this.compound.m_128461_(key);
    }

    @Override
    public void putString(String key, String value) {
        this.compound.m_128359_(key, value);
    }

    @Override
    public byte[] getByteArray(String key) {
        return this.compound.m_128463_(key);
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        this.compound.m_128382_(key, value);
    }

    @Override
    public int[] getIntegerArray(String key) {
        return this.compound.m_128465_(key);
    }

    @Override
    public void setIntegerArray(String key, int[] value) {
        this.compound.m_128385_(key, value);
    }

    @Override
    public Object[] getList(String key, int type) {
        ListTag list = this.compound.m_128437_(key, type);
        Object[] nbts = new Object[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            if (list.m_7264_() == 10) {
                nbts[i] = NpcAPI.Instance().getINbt(list.m_128728_(i));
                continue;
            }
            if (list.m_7264_() == 8) {
                nbts[i] = list.m_128778_(i);
                continue;
            }
            if (list.m_7264_() == 6) {
                nbts[i] = list.m_128772_(i);
                continue;
            }
            if (list.m_7264_() == 5) {
                nbts[i] = Float.valueOf(list.m_128775_(i));
                continue;
            }
            if (list.m_7264_() == 3) {
                nbts[i] = list.m_128763_(i);
                continue;
            }
            if (list.m_7264_() != 11) continue;
            nbts[i] = list.m_128767_(i);
        }
        return nbts;
    }

    @Override
    public int getListType(String key) {
        Tag b = this.compound.m_128423_(key);
        if (b == null) {
            return 0;
        }
        if (b.m_7060_() != 9) {
            throw new CustomNPCsException("NBT tag " + key + " isn't a list", new Object[0]);
        }
        return ((ListTag)b).m_7264_();
    }

    @Override
    public void setList(String key, Object[] value) {
        ListTag list = new ListTag();
        for (Object nbt : value) {
            if (nbt instanceof INbt) {
                list.add((Object)((INbt)nbt).getMCNBT());
                continue;
            }
            if (nbt instanceof String) {
                list.add((Object)StringTag.m_129297_((String)((String)nbt)));
                continue;
            }
            if (nbt instanceof Double) {
                list.add((Object)DoubleTag.m_128500_((double)((Double)nbt)));
                continue;
            }
            if (nbt instanceof Float) {
                list.add((Object)FloatTag.m_128566_((float)((Float)nbt).floatValue()));
                continue;
            }
            if (nbt instanceof Integer) {
                list.add((Object)IntTag.m_128679_((int)((Integer)nbt)));
                continue;
            }
            if (!(nbt instanceof int[])) continue;
            list.add((Object)new IntArrayTag((int[])nbt));
        }
        this.compound.m_128365_(key, (Tag)list);
    }

    @Override
    public INbt getCompound(String key) {
        return NpcAPI.Instance().getINbt(this.compound.m_128469_(key));
    }

    @Override
    public void setCompound(String key, INbt value) {
        if (value == null) {
            throw new CustomNPCsException("Value cant be null", new Object[0]);
        }
        this.compound.m_128365_(key, (Tag)value.getMCNBT());
    }

    @Override
    public String[] getKeys() {
        return this.compound.m_128431_().toArray(new String[this.compound.m_128431_().size()]);
    }

    @Override
    public int getType(String key) {
        return this.compound.m_128423_(key).m_7060_();
    }

    @Override
    public CompoundTag getMCNBT() {
        return this.compound;
    }

    @Override
    public String toJsonString() {
        return NBTJsonUtil.Convert(this.compound);
    }

    @Override
    public boolean isEqual(INbt nbt) {
        if (nbt == null) {
            return false;
        }
        return this.compound.equals((Object)nbt.getMCNBT());
    }

    @Override
    public void clear() {
        for (String name : this.compound.m_128431_()) {
            this.compound.m_128473_(name);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.compound.m_128456_();
    }

    @Override
    public void merge(INbt nbt) {
        this.compound.m_128391_(nbt.getMCNBT());
    }

    @Override
    public void mcSetTag(String key, Tag base) {
        this.compound.m_128365_(key, base);
    }

    @Override
    public Tag mcGetTag(String key) {
        return this.compound.m_128423_(key);
    }
}

