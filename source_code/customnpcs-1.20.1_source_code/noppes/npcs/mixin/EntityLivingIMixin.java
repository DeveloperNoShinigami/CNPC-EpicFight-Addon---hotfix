/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package noppes.npcs.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={LivingEntity.class})
public interface EntityLivingIMixin {
    @Accessor(value="jumping")
    public boolean jumping();

    @Accessor(value="useItemRemaining")
    public void useItemRemaining(int var1);

    @Accessor(value="animStep")
    public float animStep();

    @Accessor(value="animStep")
    public void animStep(float var1);

    @Accessor(value="animStepO")
    public float animStepO();

    @Accessor(value="animStepO")
    public void animStepO(float var1);

    @Accessor(value="swimAmount")
    public float swimAmount();

    @Accessor(value="swimAmount")
    public void swimAmount(float var1);

    @Accessor(value="swimAmountO")
    public float swimAmountO();

    @Accessor(value="swimAmountO")
    public void swimAmountO(float var1);

    @Accessor(value="lastHurtByPlayerTime")
    public int lastHurtByPlayerTime();

    @Accessor(value="lastHurtByPlayerTime")
    public void lastHurtByPlayerTime(int var1);
}

