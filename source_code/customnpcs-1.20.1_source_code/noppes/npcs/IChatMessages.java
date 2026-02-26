/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.renderer.MultiBufferSource
 */
package noppes.npcs;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import noppes.npcs.entity.EntityNPCInterface;

public interface IChatMessages {
    public void addMessage(String var1, EntityNPCInterface var2);

    public void renderMessages(PoseStack var1, MultiBufferSource var2, float var3, boolean var4, int var5);
}

