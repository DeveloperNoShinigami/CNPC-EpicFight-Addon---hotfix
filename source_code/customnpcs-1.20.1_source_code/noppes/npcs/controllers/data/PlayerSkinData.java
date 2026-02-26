/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.controllers.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.api.IPlayerSkin;

public class PlayerSkinData
implements IPlayerSkin {
    private boolean isMale = true;
    private int body;
    private int bodyColor;
    private int hair;
    private int hairColor;
    private int face;
    private int eyesColor;
    private int leg;
    private int jacket;
    private int shoes;
    private List<Integer> peculiarities;
    private boolean isActive;
    private ResourceLocation cacheResLoc = null;
    private boolean hasChanged;
    private static boolean skinsNeedResync;

    @Override
    public boolean isMale() {
        return this.isMale;
    }

    @Override
    public PlayerSkinData setMale(boolean male) {
        this.isMale = male;
        this.markChanged();
        return this;
    }

    public String getGender() {
        return this.isMale ? "male" : "female";
    }

    @Override
    public int getBodyType() {
        return this.body;
    }

    @Override
    public PlayerSkinData setBodyType(int body) {
        this.body = body;
        this.markChanged();
        return this;
    }

    @Override
    public int getBodyColor() {
        return this.bodyColor;
    }

    @Override
    public PlayerSkinData setBodyColor(int bodyColor) {
        this.bodyColor = bodyColor;
        this.markChanged();
        return this;
    }

    @Override
    public int getHairType() {
        return this.hair;
    }

    @Override
    public PlayerSkinData setHairType(int hair) {
        this.hair = hair;
        this.markChanged();
        return this;
    }

    @Override
    public int getHairColor() {
        return this.hairColor;
    }

    @Override
    public PlayerSkinData setHairColor(int hairColor) {
        this.hairColor = hairColor;
        this.markChanged();
        return this;
    }

    @Override
    public int getFaceType() {
        return this.face;
    }

    @Override
    public PlayerSkinData setFaceType(int face) {
        this.face = face;
        this.markChanged();
        return this;
    }

    @Override
    public int getEyesColor() {
        return this.eyesColor;
    }

    @Override
    public PlayerSkinData setEyesColor(int eyesColor) {
        this.eyesColor = eyesColor;
        this.markChanged();
        return this;
    }

    @Override
    public int getPantsType() {
        return this.leg;
    }

    @Override
    public PlayerSkinData setPantsType(int leg) {
        this.leg = leg;
        this.markChanged();
        return this;
    }

    @Override
    public int getJacketType() {
        return this.jacket;
    }

    @Override
    public PlayerSkinData setJacketType(int jacket) {
        this.jacket = jacket;
        this.markChanged();
        return this;
    }

    @Override
    public int getShoesType() {
        return this.shoes;
    }

    @Override
    public PlayerSkinData setShoesType(int shoes) {
        this.shoes = shoes;
        this.markChanged();
        return this;
    }

    @Override
    public List<Integer> getPeculiarities() {
        return this.peculiarities;
    }

    @Override
    public PlayerSkinData setPeculiarities(List<Integer> peculiarities) {
        this.peculiarities = peculiarities;
        this.markChanged();
        return this;
    }

    public void markChanged() {
        this.calculateResLoc();
        skinsNeedResync = true;
        this.hasChanged = true;
        this.isActive = true;
    }

    public boolean hasChanged() {
        return this.hasChanged;
    }

    public void markSynced() {
        this.hasChanged = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    private void calculateResLoc() {
        StringBuilder path = new StringBuilder("textures/entity/custom/");
        path.append(this.getGender()).append("_");
        path.append(this.getBodyType()).append("_");
        path.append(this.getBodyColor()).append("_");
        path.append(this.getHairType()).append("_");
        path.append(this.getHairColor()).append("_");
        path.append(this.getFaceType()).append("_");
        path.append(this.getEyesColor()).append("_");
        path.append(this.getPantsType()).append("_");
        path.append(this.getJacketType()).append("_");
        path.append(this.getShoesType());
        for (int id : this.peculiarities) {
            path.append("_").append(id);
        }
        path.append(".png");
        this.cacheResLoc = new ResourceLocation("customnpcs", path.toString());
    }

    public ResourceLocation getResLoc() {
        if (this.cacheResLoc == null) {
            this.calculateResLoc();
        }
        return this.cacheResLoc;
    }

    @OnlyIn(value=Dist.CLIENT)
    public ResourceLocation getPartResLocByNumber(ResourceManager textureManager, String name, int partNum) {
        ResourceLocation loc = new ResourceLocation("customnpcs", "textures/entity/custom/" + this.getGender() + "/" + name + "/" + partNum + ".png");
        if (textureManager.m_213713_(loc).isEmpty()) {
            loc = new ResourceLocation("customnpcs", "textures/entity/custom/" + this.getGender() + "/" + name + "/0.png");
        }
        if (!textureManager.m_213713_(loc).isEmpty()) {
            return loc;
        }
        return null;
    }

    public CompoundTag saveNBTData(CompoundTag tag) {
        tag.m_128379_("isMale", this.isMale);
        tag.m_128405_("body", this.body);
        tag.m_128405_("bodyColor", this.bodyColor);
        tag.m_128405_("hair", this.hair);
        tag.m_128405_("hairColor", this.hairColor);
        tag.m_128405_("face", this.face);
        tag.m_128405_("eyesColor", this.eyesColor);
        tag.m_128405_("leg", this.leg);
        tag.m_128405_("jacket", this.jacket);
        tag.m_128405_("shoes", this.shoes);
        tag.m_128408_("peculiarities", this.peculiarities);
        tag.m_128379_("isActive", this.isActive);
        return tag;
    }

    public void loadNBTData(CompoundTag tag) {
        this.isMale = tag.m_128471_("isMale");
        this.body = tag.m_128451_("body");
        this.bodyColor = tag.m_128451_("bodyColor");
        this.hair = tag.m_128451_("hair");
        this.hairColor = tag.m_128451_("hairColor");
        this.face = tag.m_128451_("face");
        this.eyesColor = tag.m_128451_("eyesColor");
        this.leg = tag.m_128451_("leg");
        this.jacket = tag.m_128451_("jacket");
        this.shoes = tag.m_128451_("shoes");
        this.peculiarities = Arrays.stream(tag.m_128465_("peculiarities")).boxed().collect(Collectors.toList());
        this.isActive = tag.m_128471_("isActive");
    }

    public static boolean needsAnyResync() {
        return skinsNeedResync;
    }

    public static void resyncPerformed() {
        skinsNeedResync = false;
    }
}

