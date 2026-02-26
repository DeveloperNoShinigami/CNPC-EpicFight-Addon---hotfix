/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.function.gui.GuiComponentClicked;
import noppes.npcs.api.gui.IButton;
import noppes.npcs.api.gui.ICustomGui;
import noppes.npcs.api.gui.ITexturedRect;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;

public class CustomGuiButtonWrapper
extends CustomGuiComponentWrapper
implements IButton {
    String label = "";
    int textureHoverOffset = -1;
    IItemStack item = ItemStackWrapper.AIR;
    private CustomGuiTexturedRectWrapper texture = new CustomGuiTexturedRectWrapper();
    GuiComponentClicked<IButton> onPress = null;

    public CustomGuiButtonWrapper() {
    }

    public CustomGuiButtonWrapper(int id, String label, int x, int y) {
        this.setID(id);
        this.setLabel(label);
        this.setPos(x, y);
        this.texture.setID(id);
        this.texture.setSize(this.getWidth(), this.getHeight());
        this.texture.setRepeatingTexture(200, 20, 3);
        this.texture.setTexture("textures/gui/widgets.png");
        this.texture.setTextureOffset(0, 46);
        this.setTextureHoverOffset(20);
    }

    public CustomGuiButtonWrapper(int id, String label, int x, int y, int width, int height) {
        this(id, label, x, y);
        this.setSize(width, height);
    }

    public CustomGuiButtonWrapper(int id, String label, int x, int y, int width, int height, String texture) {
        this(id, label, x, y, width, height);
        this.setTexture(texture);
        this.texture.setRepeatingTexture(width, height, 3);
        this.texture.setTextureOffset(0, 0);
        this.setTextureHoverOffset(height);
    }

    public CustomGuiButtonWrapper(int id, String label, int x, int y, int width, int height, String texture, int textureX, int textureY) {
        this(id, label, x, y, width, height, texture);
        this.setTextureOffset(textureX, textureY);
    }

    @Override
    public CustomGuiButtonWrapper setSize(int width, int height) {
        super.setSize(width, height);
        this.texture.setSize(width, height);
        if (this.textureHoverOffset <= 0) {
            this.textureHoverOffset = height;
        }
        return this;
    }

    @Override
    public int getTextureHoverOffset() {
        return this.textureHoverOffset;
    }

    @Override
    public IButton setTextureHoverOffset(int height) {
        this.textureHoverOffset = height;
        return this;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public IButton setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public CustomGuiTexturedRectWrapper getTextureRect() {
        return this.texture;
    }

    @Override
    public void setTextureRect(ITexturedRect rect) {
        this.texture = (CustomGuiTexturedRectWrapper)rect;
    }

    @Override
    public String getTexture() {
        return this.texture.getTexture();
    }

    @Override
    public boolean hasTexture() {
        return this.texture != null;
    }

    @Override
    public IButton setTexture(String texture) {
        this.texture.setTexture(texture);
        return this;
    }

    @Override
    public int getTextureX() {
        return this.texture.getTextureX();
    }

    @Override
    public int getTextureY() {
        return this.texture.getTextureY();
    }

    @Override
    public IButton setTextureOffset(int textureX, int textureY) {
        this.texture.setTextureOffset(textureX, textureY);
        return this;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public IItemStack getDisplayItem() {
        return this.item;
    }

    @Override
    public IButton setDisplayItem(IItemStack item) {
        this.item = item == null ? ItemStackWrapper.AIR : item;
        return this;
    }

    @Override
    public CompoundTag toNBT(CompoundTag nbt) {
        super.toNBT(nbt);
        nbt.m_128365_("texture", (Tag)this.texture.toNBT(new CompoundTag()));
        nbt.m_128405_("textureHoverOffset", this.textureHoverOffset);
        nbt.m_128359_("label", this.label);
        nbt.m_128365_("item", (Tag)this.item.getItemNbt().getMCNBT());
        return nbt;
    }

    @Override
    public CustomGuiComponentWrapper fromNBT(CompoundTag nbt) {
        super.fromNBT(nbt);
        this.setSize(nbt.m_128465_("size")[0], nbt.m_128465_("size")[1]);
        this.setTextureHoverOffset(nbt.m_128451_("textureHoverOffset"));
        this.setLabel(nbt.m_128461_("label"));
        this.texture.fromNBT(nbt.m_128469_("texture"));
        ItemStack it = ItemStack.m_41712_((CompoundTag)nbt.m_128469_("item"));
        this.item = NpcAPI.Instance().getIItemStack(it);
        return this;
    }

    @Override
    public CustomGuiButtonWrapper setOnPress(GuiComponentClicked<IButton> onPress) {
        this.onPress = onPress;
        return this;
    }

    public final void onPress(ICustomGui gui) {
        if (this.onPress != null) {
            this.onPress.onClick(gui, this);
        }
    }
}

