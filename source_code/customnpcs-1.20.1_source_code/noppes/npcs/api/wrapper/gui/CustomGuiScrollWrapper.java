/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.api.wrapper.gui;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.function.gui.GuiComponentClicked;
import noppes.npcs.api.gui.ICustomGui;
import noppes.npcs.api.gui.IScroll;
import noppes.npcs.api.wrapper.gui.CustomGuiComponentWrapper;

public class CustomGuiScrollWrapper
extends CustomGuiComponentWrapper
implements IScroll {
    private int[] selection = new int[0];
    private String[] list;
    private boolean multiSelect = false;
    private boolean hasSearch = true;
    private GuiComponentClicked<IScroll> onClick;
    private GuiComponentClicked<IScroll> onDoubleClick;

    public CustomGuiScrollWrapper() {
    }

    public CustomGuiScrollWrapper(int id, int x, int y, int width, int height, String[] list) {
        this.setID(id);
        this.setPos(x, y);
        this.setSize(width, height);
        this.setList(list);
    }

    @Override
    public String[] getList() {
        return this.list;
    }

    @Override
    public CustomGuiScrollWrapper setList(String[] list) {
        this.list = list;
        return this;
    }

    @Override
    public int getDefaultSelection() {
        if (this.selection.length == 0) {
            return -1;
        }
        if (this.selection.length > 1) {
            throw new CustomNPCsException("You have multiple selections, use getSelection instead", new Object[0]);
        }
        return this.selection[0];
    }

    @Override
    public CustomGuiScrollWrapper setDefaultSelection(int selection) {
        this.selection = new int[]{selection};
        return this;
    }

    @Override
    public int[] getSelection() {
        return this.selection;
    }

    @Override
    public CustomGuiScrollWrapper setSelection(int ... selection) {
        if (selection == null) {
            selection = new int[]{};
        }
        this.selection = selection;
        return this;
    }

    @Override
    public String[] getSelectionList() {
        if (this.selection.length == 0) {
            return new String[0];
        }
        return (String[])Arrays.stream(this.selection).filter(i -> i >= 0 && i < this.list.length).mapToObj(i -> this.list[i]).toArray(String[]::new);
    }

    @Override
    public CustomGuiScrollWrapper setSelectionList(String ... list) {
        this.selection = IntStream.range(0, list.length).map(i -> Arrays.asList(this.list).indexOf(list[i])).toArray();
        return this;
    }

    @Override
    public boolean isMultiSelect() {
        return this.multiSelect;
    }

    @Override
    public CustomGuiScrollWrapper setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
        return this;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public CompoundTag toNBT(CompoundTag compound) {
        super.toNBT(compound);
        compound.m_128385_("selection", this.selection);
        compound.m_128365_("list", (Tag)Arrays.stream(this.list).map(StringTag::m_129297_).collect(Collectors.toCollection(ListTag::new)));
        compound.m_128379_("multiSelect", this.multiSelect);
        compound.m_128379_("hasSearch", this.hasSearch);
        return compound;
    }

    @Override
    public CustomGuiComponentWrapper fromNBT(CompoundTag compound) {
        super.fromNBT(compound);
        this.setSelection(compound.m_128465_("selection"));
        this.setList((String[])compound.m_128437_("list", 8).stream().map(Tag::m_7916_).toArray(String[]::new));
        this.setMultiSelect(compound.m_128471_("multiSelect"));
        this.setHasSearch(compound.m_128471_("hasSearch"));
        return this;
    }

    @Override
    public CustomGuiScrollWrapper setOnClick(GuiComponentClicked<IScroll> onClick) {
        this.onClick = onClick;
        return this;
    }

    @Override
    public CustomGuiScrollWrapper setOnDoubleClick(GuiComponentClicked<IScroll> onDoubleClick) {
        this.onDoubleClick = onDoubleClick;
        return this;
    }

    @Override
    public boolean getHasSearch() {
        return this.hasSearch;
    }

    @Override
    public CustomGuiScrollWrapper setHasSearch(boolean bo) {
        this.hasSearch = bo;
        return this;
    }

    public final void onClick(ICustomGui gui) {
        if (this.onClick != null) {
            this.onClick.onClick(gui, this);
        }
    }

    public final void onDoubleClick(ICustomGui gui) {
        if (this.onDoubleClick != null) {
            this.onDoubleClick.onClick(gui, this);
        }
    }
}

