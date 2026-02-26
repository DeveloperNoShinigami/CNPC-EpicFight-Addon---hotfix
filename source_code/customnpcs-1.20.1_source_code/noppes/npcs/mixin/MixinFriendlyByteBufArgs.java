/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package noppes.npcs.mixin;

import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={FriendlyByteBuf.class})
public class MixinFriendlyByteBufArgs {
    @ModifyVariable(method={"readByteArray(I)[B"}, argsOnly=true, at=@At(value="LOAD"))
    private int adjustReadByteCapacity(int capacity) {
        if (capacity == 32600) {
            return ((FriendlyByteBuf)this).readableBytes();
        }
        return capacity;
    }
}

