/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraftforge.common.capabilities.CapabilityDispatcher
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package noppes.npcs.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={CapabilityDispatcher.class})
public class CapabilityDispatcherMixin {
    @Inject(method={"serializeNBT()Lnet/minecraft/nbt/CompoundTag;"}, at={@At(value="RETURN")}, cancellable=true, remap=false)
    public void serializeNBT(CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag tag = (CompoundTag)cir.getReturnValue();
        if (tag.m_128441_("customnpcs:itemscripteddata") && tag.m_128469_("customnpcs:itemscripteddata").m_128456_()) {
            tag.m_128473_("customnpcs:itemscripteddata");
        }
        cir.setReturnValue((Object)tag);
    }
}

