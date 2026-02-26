/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraftforge.network.PlayMessages$OpenContainer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package noppes.npcs.mixin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.PlayMessages;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={PlayMessages.OpenContainer.class})
public interface MixinOpenContainer {
    @Invoker(value="<init>", remap=false)
    public static PlayMessages.OpenContainer OpenContainer(MenuType<?> id, int windowId, Component name, FriendlyByteBuf additionalData) {
        return null;
    }
}

