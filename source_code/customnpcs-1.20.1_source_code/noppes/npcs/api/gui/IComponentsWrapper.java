/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui;

import java.util.List;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.gui.IAssetsSelector;
import noppes.npcs.api.gui.IButton;
import noppes.npcs.api.gui.IButtonList;
import noppes.npcs.api.gui.IColoredLine;
import noppes.npcs.api.gui.ICustomGuiComponent;
import noppes.npcs.api.gui.IEntityDisplay;
import noppes.npcs.api.gui.IItemRenderer;
import noppes.npcs.api.gui.IItemSlot;
import noppes.npcs.api.gui.ILabel;
import noppes.npcs.api.gui.IScroll;
import noppes.npcs.api.gui.ISlider;
import noppes.npcs.api.gui.ITextArea;
import noppes.npcs.api.gui.ITextField;
import noppes.npcs.api.gui.ITexturedRect;
import noppes.npcs.api.item.IItemStack;

public interface IComponentsWrapper {
    public IButton addButton(int var1, String var2, int var3, int var4);

    public IButton addButton(int var1, String var2, int var3, int var4, int var5, int var6);

    public IButtonList addButtonList(int var1, int var2, int var3, int var4, int var5);

    public IButton addTexturedButton(int var1, String var2, int var3, int var4, int var5, int var6, String var7);

    public IButton addTexturedButton(int var1, String var2, int var3, int var4, int var5, int var6, String var7, int var8, int var9);

    public ILabel addLabel(int var1, String var2, int var3, int var4, int var5, int var6);

    public ILabel addLabel(int var1, String var2, int var3, int var4, int var5, int var6, int var7);

    public ITextField addTextField(int var1, int var2, int var3, int var4, int var5);

    public ITextArea addTextArea(int var1, int var2, int var3, int var4, int var5);

    public IScroll addScroll(int var1, int var2, int var3, int var4, int var5, String[] var6);

    public ISlider addSlider(int var1, int var2, int var3, int var4, int var5, String var6);

    public IEntityDisplay addEntityDisplay(int var1, int var2, int var3, IEntity var4);

    public IColoredLine addColoredLine(int var1, int var2, int var3, int var4, int var5, int var6, float var7);

    public IItemRenderer addItemRenderer(int var1, int var2, int var3, int var4, int var5, IItemStack var6);

    public IAssetsSelector addAssetsSelector(int var1, int var2, int var3, int var4, int var5);

    public ITexturedRect addTexturedRect(int var1, String var2, int var3, int var4, int var5, int var6);

    public ITexturedRect addTexturedRect(int var1, String var2, int var3, int var4, int var5, int var6, int var7, int var8);

    public List<ICustomGuiComponent> getComponents();

    public ICustomGuiComponent getComponent(int var1);

    public void addComponent(ICustomGuiComponent var1);

    public void removeComponent(int var1);

    public List<IItemSlot> getSlots();

    public List<IItemSlot> getPlayerSlots();

    public IItemSlot addItemSlot(int var1, int var2);

    public IItemSlot addItemSlot(int var1, int var2, IItemStack var3);

    public void removeItemSlot(IItemSlot var1);

    @Deprecated
    public void showPlayerInventory(int var1, int var2);

    public IItemSlot[] showPlayerInventory(int var1, int var2, boolean var3);
}

