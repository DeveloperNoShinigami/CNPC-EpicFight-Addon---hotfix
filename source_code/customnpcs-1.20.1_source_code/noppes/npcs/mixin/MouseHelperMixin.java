/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MouseHandler
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package noppes.npcs.mixin;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={MouseHandler.class})
public interface MouseHelperMixin {
    @Accessor(value="activeButton")
    public int getActiveButton();

    @Accessor(value="mouseGrabbed")
    public void setGrabbed(boolean var1);

    @Accessor(value="xpos")
    public void setX(double var1);

    @Accessor(value="ypos")
    public void setY(double var1);
}

