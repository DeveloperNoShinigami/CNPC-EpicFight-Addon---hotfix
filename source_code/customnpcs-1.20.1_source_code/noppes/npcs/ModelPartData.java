/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 */
package noppes.npcs;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class ModelPartData {
    private static Map<String, ResourceLocation> resources = new HashMap<String, ResourceLocation>();
    public int color = 0xFFFFFF;
    public int colorPattern = 0xFFFFFF;
    public byte type = 0;
    public byte pattern = 0;
    public boolean playerTexture = false;
    public String name;
    private ResourceLocation location;

    public ModelPartData(String name) {
        this.name = name;
    }

    public CompoundTag save() {
        CompoundTag compound = new CompoundTag();
        compound.m_128344_("Type", this.type);
        compound.m_128405_("Color", this.color);
        compound.m_128379_("PlayerTexture", this.playerTexture);
        compound.m_128344_("Pattern", this.pattern);
        return compound;
    }

    public void load(CompoundTag compound) {
        if (!compound.m_128441_("Type")) {
            this.type = (byte)-1;
            return;
        }
        this.type = compound.m_128445_("Type");
        this.color = compound.m_128451_("Color");
        this.playerTexture = compound.m_128471_("PlayerTexture");
        this.pattern = compound.m_128445_("Pattern");
        this.location = null;
    }

    public ResourceLocation getResource() {
        if (this.location != null) {
            return this.location;
        }
        String texture = this.name + "/" + this.type;
        this.location = resources.get(texture);
        if (this.location != null) {
            return this.location;
        }
        this.location = new ResourceLocation("moreplayermodels:textures/" + texture + ".png");
        resources.put(texture, this.location);
        return this.location;
    }

    public void setType(int type) {
        this.type = (byte)type;
        this.location = null;
    }

    public String toString() {
        return "Color: " + this.color + " Type: " + this.type;
    }

    public String getColor() {
        Object str = Integer.toHexString(this.color);
        while (((String)str).length() < 6) {
            str = "0" + (String)str;
        }
        return str;
    }
}

