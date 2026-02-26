/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.Event
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package noppes.npcs.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import noppes.npcs.event.SlotItemChangeEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Slot.class})
public abstract class SlotMixin {
    @Shadow
    public int f_40219_;
    @Shadow
    @Final
    public Container f_40218_;

    @Shadow
    public abstract ItemStack m_7993_();

    @Shadow
    public abstract void m_5852_(ItemStack var1);

    @Inject(method={"mayPlace"}, at={@At(value="HEAD")}, cancellable=true)
    public void mayPlace(ItemStack newStack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack oldStack = this.m_7993_();
        SlotItemChangeEvent event = new SlotItemChangeEvent(this.f_40219_, oldStack, newStack, this.f_40218_);
        MinecraftForge.EVENT_BUS.post((Event)event);
        this.m_5852_(event.getSlotStack());
        if (event.isCanceled()) {
            cir.setReturnValue((Object)false);
        }
    }
}

