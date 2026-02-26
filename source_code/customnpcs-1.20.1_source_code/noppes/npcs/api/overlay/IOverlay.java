/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.overlay;

import java.util.Collection;
import net.minecraft.nbt.CompoundTag;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.overlay.ILabel;
import noppes.npcs.api.overlay.IOverlayComponent;
import noppes.npcs.api.overlay.IRenderItemOverlay;
import noppes.npcs.api.overlay.ITexturedRect;

public interface IOverlay {
    public int getId();

    public void setLinkSide(int var1);

    public int getLinkSide();

    public ILabel addLabel(int var1, String var2, int var3, int var4);

    public ITexturedRect addTexturedRect(int var1, String var2, int var3, int var4, int var5, int var6);

    public ITexturedRect addTexturedRectCrop(int var1, String var2, int var3, int var4, int var5, int var6, int var7, int var8);

    public ITexturedRect addTexturedRectCrop(int var1, String var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10);

    public IOverlayComponent getComponent(int var1);

    public IRenderItemOverlay addRenderItem(int var1, int var2, int var3, IItemStack var4);

    public Collection<IOverlayComponent> getComponents();

    public void removeComponent(int var1);

    public void clear();

    public CompoundTag toNbt();

    public void fromNbt(CompoundTag var1);
}

