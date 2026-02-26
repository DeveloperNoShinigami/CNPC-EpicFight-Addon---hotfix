/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package noppes.npcs.api.handler.data;

import net.minecraft.network.chat.Component;

public interface IQuestObjective {
    public int getProgress();

    public void setProgress(int var1);

    public int getMaxProgress();

    public boolean isCompleted();

    public String getText();

    public Component getMCText();
}

