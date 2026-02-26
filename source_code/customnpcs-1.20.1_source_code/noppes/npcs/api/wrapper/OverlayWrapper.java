/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.api.wrapper;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Collection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.overlay.ILabel;
import noppes.npcs.api.overlay.IOverlay;
import noppes.npcs.api.overlay.IOverlayComponent;
import noppes.npcs.api.overlay.IRenderItemOverlay;
import noppes.npcs.api.overlay.ITexturedRect;
import noppes.npcs.api.wrapper.OverlayComponentWrapper;
import noppes.npcs.api.wrapper.OverlayLabelWrapper;
import noppes.npcs.api.wrapper.OverlayRenderItemWrapper;
import noppes.npcs.api.wrapper.OverlayTexturedRectWrapper;

public class OverlayWrapper
implements IOverlay {
    private final Int2ObjectOpenHashMap<IOverlayComponent> components;
    private int id;
    private int linkSide = 5;

    public OverlayWrapper(int id) {
        this.id = id;
        this.components = new Int2ObjectOpenHashMap();
    }

    @Override
    public Collection<IOverlayComponent> getComponents() {
        return this.components.values();
    }

    @Override
    public ILabel addLabel(int id, String text, int x, int y) {
        OverlayLabelWrapper label = new OverlayLabelWrapper(id, x, y, text);
        this.components.put(id, (Object)label);
        return label;
    }

    @Override
    public ITexturedRect addTexturedRect(int id, String texture, int x, int y, int width, int height) {
        OverlayTexturedRectWrapper rect = new OverlayTexturedRectWrapper(id, x, y, texture, width, height);
        this.components.put(id, (Object)rect);
        return rect;
    }

    @Override
    public ITexturedRect addTexturedRectCrop(int id, String texture, int x, int y, int width, int height, int textureX, int textureY) {
        OverlayTexturedRectWrapper rect = new OverlayTexturedRectWrapper(id, x, y, texture, width, height, textureX, textureY);
        this.components.put(id, (Object)rect);
        return rect;
    }

    @Override
    public ITexturedRect addTexturedRectCrop(int id, String texture, int x, int y, int width, int height, int textureX, int textureY, int textureMaxX, int textureMaxY) {
        OverlayTexturedRectWrapper rect = new OverlayTexturedRectWrapper(id, x, y, texture, width, height, textureX, textureY, textureMaxX, textureMaxY);
        this.components.put(id, (Object)rect);
        return rect;
    }

    @Override
    public IOverlayComponent getComponent(int id) {
        return (IOverlayComponent)this.components.get(id);
    }

    @Override
    public IRenderItemOverlay addRenderItem(int id, int x, int y, IItemStack item) {
        OverlayRenderItemWrapper itemOverlay = new OverlayRenderItemWrapper(id, x, y, item);
        this.components.put(id, (Object)itemOverlay);
        return itemOverlay;
    }

    @Override
    public void removeComponent(int id) {
        this.components.remove(id);
    }

    @Override
    public void clear() {
        this.components.clear();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setLinkSide(int side) {
        this.linkSide = Math.min(9, Math.max(1, side));
    }

    @Override
    public int getLinkSide() {
        return this.linkSide;
    }

    @Override
    public void fromNbt(CompoundTag tagCompound) {
        this.id = tagCompound.m_128451_("id");
        this.linkSide = tagCompound.m_128451_("linkSide");
        this.components.clear();
        ListTag list = tagCompound.m_128437_("components", 10);
        block5: for (int i = 0; i < list.size(); ++i) {
            CompoundTag compound = list.m_128728_(i);
            int type = compound.m_128451_("type");
            OverlayComponentWrapper component = null;
            switch (type) {
                case 0: {
                    component = new OverlayLabelWrapper(0, 0, 0, "");
                    break;
                }
                case 1: {
                    component = new OverlayTexturedRectWrapper(0, 0, 0, "", 0, 0);
                    break;
                }
                case 2: {
                    component = new OverlayRenderItemWrapper(0, 0, 0, null);
                    break;
                }
                default: {
                    continue block5;
                }
            }
            component.fromNbt(compound);
            this.components.put(component.getId(), (Object)component);
        }
    }

    @Override
    public CompoundTag toNbt() {
        CompoundTag compound = new CompoundTag();
        compound.m_128405_("id", this.id);
        compound.m_128405_("linkSide", this.linkSide);
        ListTag list = new ListTag();
        for (IOverlayComponent component : this.components.values()) {
            CompoundTag comp = new CompoundTag();
            component.toNbt(comp);
            list.add((Object)comp);
        }
        compound.m_128365_("components", (Tag)list);
        return compound;
    }
}

