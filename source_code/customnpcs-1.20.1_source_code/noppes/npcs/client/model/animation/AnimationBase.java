/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.world.entity.Entity
 */
package noppes.npcs.client.model.animation;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;

public interface AnimationBase {
    public void animatePre(float var1, float var2, float var3, float var4, float var5, Entity var6, HumanoidModel var7, int var8);

    public void animatePost(float var1, float var2, float var3, float var4, float var5, Entity var6, HumanoidModel var7, int var8);
}

