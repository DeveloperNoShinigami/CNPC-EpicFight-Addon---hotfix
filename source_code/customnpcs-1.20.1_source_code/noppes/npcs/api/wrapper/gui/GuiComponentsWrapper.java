/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.api.wrapper.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.gui.IColoredLine;
import noppes.npcs.api.gui.IComponentsWrapper;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.gui.IItemRenderer;
import noppes.npcs.api.gui.IItemSlot;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemScriptedWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiAssetsSelectorWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonListWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiColoredLineWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiEntityDisplayWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiItemRendererWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiItemSlotWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiLabelWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiScrollWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiSliderWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextAreaWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTextFieldWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiTexturedRectWrapper;
import noppes.npcs.api.wrapper.gui.GuiComponentsScrollableWrapper;

public class GuiComponentsWrapper
implements IComponentsWrapper {
    private List<ICustomGuiComponent> components = new ArrayList<ICustomGuiComponent>();
    private List<IItemSlot> slots = new ArrayList<IItemSlot>();
    private List<IItemSlot> playerSlots = new ArrayList<IItemSlot>();
    public int slotId = 0;
    protected IPlayer player;

    public GuiComponentsWrapper(IPlayer player) {
        this.player = player;
    }

    @Override
    public CustomGuiButtonWrapper addButton(int id, String label, int x, int y) {
        CustomGuiButtonWrapper component = new CustomGuiButtonWrapper(id, label, x, y);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiButtonWrapper addButton(int id, String label, int x, int y, int width, int height) {
        CustomGuiButtonWrapper component = new CustomGuiButtonWrapper(id, label, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiButtonListWrapper addButtonList(int id, int x, int y, int width, int height) {
        CustomGuiButtonListWrapper component = new CustomGuiButtonListWrapper(id, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiButtonWrapper addTexturedButton(int id, String label, int x, int y, int width, int height, String texture) {
        CustomGuiButtonWrapper component = new CustomGuiButtonWrapper(id, label, x, y, width, height, texture);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiButtonWrapper addTexturedButton(int id, String label, int x, int y, int width, int height, String texture, int textureX, int textureY) {
        CustomGuiButtonWrapper component = new CustomGuiButtonWrapper(id, label, x, y, width, height, texture, textureX, textureY);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiLabelWrapper addLabel(int id, String label, int x, int y, int width, int height) {
        CustomGuiLabelWrapper component = new CustomGuiLabelWrapper(id, label, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiLabelWrapper addLabel(int id, String label, int x, int y, int width, int height, int color) {
        CustomGuiLabelWrapper component = new CustomGuiLabelWrapper(id, label, x, y, width, height, color);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiTextFieldWrapper addTextField(int id, int x, int y, int width, int height) {
        CustomGuiTextFieldWrapper component = new CustomGuiTextFieldWrapper(id, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiTextAreaWrapper addTextArea(int id, int x, int y, int width, int height) {
        CustomGuiTextAreaWrapper component = new CustomGuiTextAreaWrapper(id, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiTexturedRectWrapper addTexturedRect(int id, String texture, int x, int y, int width, int height) {
        CustomGuiTexturedRectWrapper component = new CustomGuiTexturedRectWrapper(id, texture, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiTexturedRectWrapper addTexturedRect(int id, String texture, int x, int y, int width, int height, int textureX, int textureY) {
        CustomGuiTexturedRectWrapper component = new CustomGuiTexturedRectWrapper(id, texture, x, y, width, height, textureX, textureY);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiScrollWrapper addScroll(int id, int x, int y, int width, int height, String ... list) {
        CustomGuiScrollWrapper component = new CustomGuiScrollWrapper(id, x, y, width, height, list);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiSliderWrapper addSlider(int id, int x, int y, int width, int height, String format) {
        CustomGuiSliderWrapper component = new CustomGuiSliderWrapper(id, format, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiEntityDisplayWrapper addEntityDisplay(int id, int x, int y, IEntity entity) {
        CustomGuiEntityDisplayWrapper component = new CustomGuiEntityDisplayWrapper(id, entity, x, y);
        this.addComponent(component);
        return component;
    }

    @Override
    public CustomGuiAssetsSelectorWrapper addAssetsSelector(int id, int x, int y, int width, int height) {
        CustomGuiAssetsSelectorWrapper component = new CustomGuiAssetsSelectorWrapper(id, x, y, width, height);
        this.addComponent(component);
        return component;
    }

    @Override
    public IColoredLine addColoredLine(int id, int xStart, int yStart, int xEnd, int yEnd, int color, float thickness) {
        CustomGuiColoredLineWrapper line = new CustomGuiColoredLineWrapper(id, xStart, yStart, xEnd, yEnd, color, thickness);
        this.components.add(line);
        return line;
    }

    @Override
    public IItemRenderer addItemRenderer(int id, int x, int y, int width, int height, IItemStack stack) {
        CustomGuiItemRendererWrapper rendererWrapper = new CustomGuiItemRendererWrapper(id, x, y, width, height, stack);
        this.components.add(rendererWrapper);
        return rendererWrapper;
    }

    @Override
    public ICustomGuiComponent getComponent(int componentID) {
        for (ICustomGuiComponent component : this.components) {
            if (component.getID() != componentID) continue;
            return component;
        }
        return null;
    }

    @Override
    public void addComponent(ICustomGuiComponent component) {
        if (this.components.stream().anyMatch(t -> t.getID() == component.getID())) {
            throw new CustomNPCsException("This gui already contains component id:" + component.getID(), new Object[0]);
        }
        this.components.add(component);
    }

    @Override
    public void removeComponent(int componentID) {
        this.components.removeIf(c -> c.getID() == componentID);
    }

    @Override
    public List<ICustomGuiComponent> getComponents() {
        return this.components;
    }

    public CompoundTag getComponentNbt() {
        CompoundTag comp = new CompoundTag();
        ListTag list = new ListTag();
        for (ICustomGuiComponent iCustomGuiComponent : this.components) {
            list.add((Object)((CustomGuiComponentWrapper)iCustomGuiComponent).toNBT(new CompoundTag()));
        }
        comp.m_128365_("components", (Tag)list);
        list = new ListTag();
        for (ICustomGuiComponent iCustomGuiComponent : this.slots) {
            list.add((Object)((CustomGuiComponentWrapper)iCustomGuiComponent).toNBT(new CompoundTag()));
        }
        comp.m_128365_("slots", (Tag)list);
        list = new ListTag();
        for (ICustomGuiComponent iCustomGuiComponent : this.playerSlots) {
            list.add((Object)((CustomGuiComponentWrapper)iCustomGuiComponent).toNBT(new CompoundTag()));
        }
        comp.m_128365_("playerSlots", (Tag)list);
        return comp;
    }

    private List<IItemSlot> getNbtSlots(CompoundTag tag, String key) {
        ArrayList<IItemSlot> slots = new ArrayList<IItemSlot>();
        ListTag list = tag.m_128437_(key, 10);
        for (Tag b : list) {
            CustomGuiItemSlotWrapper component = (CustomGuiItemSlotWrapper)CustomGuiComponentWrapper.createFromNBT((CompoundTag)b);
            slots.add(component);
        }
        return slots;
    }

    public void setComponentNbt(CompoundTag comp) {
        ArrayList<ICustomGuiComponent> components = new ArrayList<ICustomGuiComponent>();
        ListTag list = comp.m_128437_("components", 10);
        for (Tag b : list) {
            components.add(CustomGuiComponentWrapper.createFromNBT((CompoundTag)b));
        }
        this.components = components;
        this.slots = this.getNbtSlots(comp, "slots");
        this.playerSlots = this.getNbtSlots(comp, "playerSlots");
    }

    public ICustomGuiComponent getComponentUuid(UUID id) {
        for (ICustomGuiComponent comp : this.components) {
            if (!comp.getUniqueID().equals(id)) continue;
            return comp;
        }
        return null;
    }

    @Override
    public List<IItemSlot> getSlots() {
        return this.slots;
    }

    @Override
    public List<IItemSlot> getPlayerSlots() {
        return this.playerSlots;
    }

    @Override
    public IItemSlot addItemSlot(int x, int y) {
        return this.addItemSlot(x, y, ItemScriptedWrapper.AIR);
    }

    @Override
    public IItemSlot addItemSlot(int x, int y, IItemStack stack) {
        CustomGuiItemSlotWrapper slot = new CustomGuiItemSlotWrapper(x, y, stack);
        GuiComponentsWrapper w = this;
        GuiComponentsWrapper guiComponentsWrapper = this;
        if (guiComponentsWrapper instanceof GuiComponentsScrollableWrapper) {
            GuiComponentsScrollableWrapper scroll = (GuiComponentsScrollableWrapper)guiComponentsWrapper;
            w = scroll.parent;
        }
        slot.setID(w.slotId++);
        this.slots.add(slot);
        return slot;
    }

    @Override
    public void removeItemSlot(IItemSlot slot) {
        this.slots.removeIf(s -> s.getID() == slot.getID());
    }

    @Override
    public void showPlayerInventory(int x, int y) {
        this.showPlayerInventory(x, y, true);
    }

    @Override
    public IItemSlot[] showPlayerInventory(int x, int y, boolean full) {
        ArrayList<IItemSlot> playerSlots = new ArrayList<IItemSlot>();
        if (full) {
            for (int row = 0; row < 3; ++row) {
                for (int col = 0; col < 9; ++col) {
                    CustomGuiItemSlotWrapper slot = new CustomGuiItemSlotWrapper(x + col * 18, y + row * 18, (Player)this.player.getMCEntity());
                    slot.setID(9 + row * 9 + col);
                    playerSlots.add(slot);
                }
            }
            y += 58;
        }
        for (int col = 0; col < 9; ++col) {
            CustomGuiItemSlotWrapper slot = new CustomGuiItemSlotWrapper(x + col * 18, y, (Player)this.player.getMCEntity());
            slot.setID(col);
            playerSlots.add(slot);
        }
        this.playerSlots = playerSlots;
        return this.playerSlots.toArray(new IItemSlot[0]);
    }
}

