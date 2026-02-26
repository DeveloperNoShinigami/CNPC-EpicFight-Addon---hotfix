/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

import noppes.npcs.api.IContainer;
import noppes.npcs.api.handler.data.IQuest;

public interface IPlayerMail {
    public String getSender();

    public void setSender(String var1);

    public String getSubject();

    public void setSubject(String var1);

    public String[] getText();

    public void setText(String[] var1);

    public IQuest getQuest();

    public void setQuest(int var1);

    public IContainer getContainer();
}

