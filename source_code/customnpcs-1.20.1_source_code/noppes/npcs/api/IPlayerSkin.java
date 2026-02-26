/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api;

import java.util.List;

public interface IPlayerSkin {
    public boolean isMale();

    public IPlayerSkin setMale(boolean var1);

    public int getBodyType();

    public IPlayerSkin setBodyType(int var1);

    public int getBodyColor();

    public IPlayerSkin setBodyColor(int var1);

    public int getHairType();

    public IPlayerSkin setHairType(int var1);

    public int getHairColor();

    public IPlayerSkin setHairColor(int var1);

    public int getFaceType();

    public IPlayerSkin setFaceType(int var1);

    public int getEyesColor();

    public IPlayerSkin setEyesColor(int var1);

    public int getPantsType();

    public IPlayerSkin setPantsType(int var1);

    public int getJacketType();

    public IPlayerSkin setJacketType(int var1);

    public int getShoesType();

    public IPlayerSkin setShoesType(int var1);

    public List<Integer> getPeculiarities();

    public IPlayerSkin setPeculiarities(List<Integer> var1);
}

