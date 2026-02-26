/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

import noppes.npcs.api.entity.IPlayer;

public interface INPCDisplay {
    public String getName();

    public void setName(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getSkinUrl();

    public void setSkinUrl(String var1);

    public String getSkinPlayer();

    public void setSkinPlayer(String var1);

    public String getSkinTexture();

    public void setSkinTexture(String var1);

    public boolean getHasLivingAnimation();

    public void setHasLivingAnimation(boolean var1);

    public int getVisible();

    public void setVisible(int var1);

    public boolean isVisibleTo(IPlayer var1);

    public int getBossbar();

    public void setBossbar(int var1);

    public int getSize();

    public void setSize(int var1);

    public int getTint();

    public void setTint(int var1);

    public int getShowName();

    public void setShowName(int var1);

    public void setCapeTexture(String var1);

    public String getCapeTexture();

    public void setOverlayTexture(String var1);

    public String getOverlayTexture();

    public void setModelScale(int var1, float var2, float var3, float var4);

    public float[] getModelScale(int var1);

    public int getBossColor();

    public void setBossColor(int var1);

    public void setModel(String var1);

    public String getModel();

    public void setHitboxState(byte var1);

    public byte getHitboxState();
}

