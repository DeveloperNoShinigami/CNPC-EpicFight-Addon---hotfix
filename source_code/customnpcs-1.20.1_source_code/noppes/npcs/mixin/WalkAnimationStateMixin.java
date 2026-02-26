/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.WalkAnimationState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package noppes.npcs.mixin;

import net.minecraft.world.entity.WalkAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={WalkAnimationState.class})
public interface WalkAnimationStateMixin {
    @Accessor
    public float getSpeedOld();

    @Accessor
    public void setSpeedOld(float var1);

    @Accessor
    public void setPosition(float var1);
}

