/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package noppes.npcs.mixin;

import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import noppes.npcs.CustomNpcs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={LeavesBlock.class})
public class LeavesBlockMixin {
    @Inject(at={@At(value="HEAD")}, method={"isRandomlyTicking"}, cancellable=true)
    private void setupAnimPre(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!CustomNpcs.LeavesDecayEnabled) {
            cir.setReturnValue((Object)false);
            cir.cancel();
        }
    }
}

