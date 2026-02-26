/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.item;

import noppes.npcs.api.item.IItemStack;

public interface IItemBook
extends IItemStack {
    public String[] getText();

    public void setText(String[] var1);

    public String getAuthor();

    public void setAuthor(String var1);

    public String getTitle();

    public void setTitle(String var1);
}

