/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.api.wrapper;

import java.util.ArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.api.item.IItemBook;
import noppes.npcs.api.wrapper.ItemStackWrapper;

public class ItemBookWrapper
extends ItemStackWrapper
implements IItemBook {
    protected ItemBookWrapper(ItemStack item) {
        super(item);
    }

    @Override
    public String getTitle() {
        return this.getTag().m_128461_("title");
    }

    @Override
    public void setTitle(String title) {
        this.getTag().m_128359_("title", title);
    }

    @Override
    public String getAuthor() {
        return this.getTag().m_128461_("author");
    }

    @Override
    public void setAuthor(String author) {
        this.getTag().m_128359_("author", author);
    }

    @Override
    public String[] getText() {
        ArrayList<String> list = new ArrayList<String>();
        ListTag pages = this.getTag().m_128437_("pages", 8);
        for (int i = 0; i < pages.size(); ++i) {
            list.add(pages.m_128778_(i));
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public void setText(String[] pages) {
        ListTag list = new ListTag();
        if (pages != null && pages.length > 0) {
            for (String page : pages) {
                list.add((Object)StringTag.m_129297_((String)page));
            }
        }
        this.getTag().m_128365_("pages", (Tag)list);
    }

    private CompoundTag getTag() {
        CompoundTag comp = this.item.m_41783_();
        if (comp == null) {
            comp = new CompoundTag();
            this.item.m_41751_(comp);
        }
        return comp;
    }

    @Override
    public boolean isBook() {
        return true;
    }

    @Override
    public int getType() {
        return 1;
    }
}

