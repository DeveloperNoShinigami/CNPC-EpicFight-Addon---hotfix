/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.DoubleTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 */
package noppes.npcs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.controllers.IScriptHandler;
import noppes.npcs.controllers.ScriptContainer;

public class NBTTags {
    public static void getItemStackList(ListTag tagList, NonNullList<ItemStack> items) {
        items.clear();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            try {
                items.set(nbttagcompound.m_128445_("Slot") & 0xFF, (Object)ItemStack.m_41712_((CompoundTag)nbttagcompound));
                continue;
            }
            catch (ClassCastException e) {
                items.set(nbttagcompound.m_128451_("Slot"), (Object)ItemStack.m_41712_((CompoundTag)nbttagcompound));
            }
        }
    }

    public static Map<Integer, IItemStack> getIItemStackMap(ListTag tagList) {
        HashMap<Integer, IItemStack> list = new HashMap<Integer, IItemStack>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            ItemStack item = ItemStack.m_41712_((CompoundTag)nbttagcompound);
            if (item.m_41619_()) continue;
            try {
                list.put(nbttagcompound.m_128445_("Slot") & 0xFF, NpcAPI.Instance().getIItemStack(item));
                continue;
            }
            catch (ClassCastException e) {
                list.put(nbttagcompound.m_128451_("Slot"), NpcAPI.Instance().getIItemStack(item));
            }
        }
        return list;
    }

    public static ItemStack[] getItemStackArray(ListTag tagList) {
        ItemStack[] list = new ItemStack[tagList.size()];
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list[nbttagcompound.m_128445_((String)"Slot") & 0xFF] = ItemStack.m_41712_((CompoundTag)nbttagcompound);
        }
        return list;
    }

    public static NonNullList<Ingredient> getIngredientList(ListTag tagList) {
        NonNullList list = NonNullList.m_122779_();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.add(nbttagcompound.m_128445_("Slot") & 0xFF, (Object)Ingredient.m_43927_((ItemStack[])new ItemStack[]{ItemStack.m_41712_((CompoundTag)nbttagcompound)}));
        }
        return list;
    }

    public static ArrayList<int[]> getIntegerArraySet(ListTag tagList) {
        ArrayList<int[]> set = new ArrayList<int[]>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag compound = tagList.m_128728_(i);
            set.add(compound.m_128465_("Array"));
        }
        return set;
    }

    public static HashMap<Integer, Boolean> getBooleanList(ListTag tagList) {
        HashMap<Integer, Boolean> list = new HashMap<Integer, Boolean>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128451_("Slot"), nbttagcompound.m_128471_("Boolean"));
        }
        return list;
    }

    public static HashMap<Integer, Integer> getIntegerIntegerMap(ListTag tagList) {
        HashMap<Integer, Integer> list = new HashMap<Integer, Integer>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128451_("Slot"), nbttagcompound.m_128451_("Integer"));
        }
        return list;
    }

    public static HashMap<Integer, Float> getFloatIntegerMap(ListTag tagList) {
        HashMap<Integer, Float> list = new HashMap<Integer, Float>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128451_("Slot"), Float.valueOf(nbttagcompound.m_128457_("Integer")));
        }
        return list;
    }

    public static HashMap<Integer, Long> getIntegerLongMap(ListTag tagList) {
        HashMap<Integer, Long> list = new HashMap<Integer, Long>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128451_("Slot"), nbttagcompound.m_128454_("Long"));
        }
        return list;
    }

    public static HashSet<Integer> getIntegerSet(ListTag tagList) {
        HashSet<Integer> list = new HashSet<Integer>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.add(nbttagcompound.m_128451_("Integer"));
        }
        return list;
    }

    public static List<Integer> getIntegerList(ListTag tagList) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.add(nbttagcompound.m_128451_("Integer"));
        }
        return list;
    }

    public static HashMap<String, String> getStringStringMap(ListTag tagList) {
        HashMap<String, String> list = new HashMap<String, String>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128461_("Slot"), nbttagcompound.m_128461_("Value"));
        }
        return list;
    }

    public static HashMap<Integer, String> getIntegerStringMap(ListTag tagList) {
        HashMap<Integer, String> list = new HashMap<Integer, String>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128451_("Slot"), nbttagcompound.m_128461_("Value"));
        }
        return list;
    }

    public static HashMap<String, Integer> getStringIntegerMap(ListTag tagList) {
        HashMap<String, Integer> list = new HashMap<String, Integer>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128461_("Slot"), nbttagcompound.m_128451_("Value"));
        }
        return list;
    }

    public static HashMap<String, Vector<String>> getVectorMap(ListTag tagList) {
        HashMap<String, Vector<String>> map = new HashMap<String, Vector<String>>();
        for (int i = 0; i < tagList.size(); ++i) {
            Vector<String> values = new Vector<String>();
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            ListTag list = nbttagcompound.m_128437_("Values", 10);
            for (int j = 0; j < list.size(); ++j) {
                CompoundTag value = list.m_128728_(j);
                values.add(value.m_128461_("Value"));
            }
            map.put(nbttagcompound.m_128461_("Key"), values);
        }
        return map;
    }

    public static List<String> getStringList(ListTag tagList) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            String line = nbttagcompound.m_128461_("Line");
            list.add(line);
        }
        return list;
    }

    public static List<ResourceLocation> getResourceLocationList(ListTag tagList) {
        ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            ResourceLocation line = new ResourceLocation(nbttagcompound.m_128461_("Line"));
            list.add(line);
        }
        return list;
    }

    public static String[] getStringArray(ListTag tagList, int size) {
        String[] arr = new String[size];
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            String line = nbttagcompound.m_128461_("Value");
            int slot = nbttagcompound.m_128451_("Slot");
            arr[slot] = line;
        }
        return arr;
    }

    public static ListTag nbtIntegerArraySet(List<int[]> set) {
        ListTag nbttaglist = new ListTag();
        if (set == null) {
            return nbttaglist;
        }
        for (int[] arr : set) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128385_("Array", arr);
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtItemStackList(NonNullList<ItemStack> inventory) {
        ListTag nbttaglist = new ListTag();
        for (int slot = 0; slot < inventory.size(); ++slot) {
            ItemStack item = (ItemStack)inventory.get(slot);
            if (item.m_41619_()) continue;
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128344_("Slot", (byte)slot);
            item.m_41739_(nbttagcompound);
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtIItemStackMap(Map<Integer, IItemStack> inventory) {
        ListTag nbttaglist = new ListTag();
        if (inventory == null) {
            return nbttaglist;
        }
        for (int slot : inventory.keySet()) {
            IItemStack item = inventory.get(slot);
            if (item == null) continue;
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128344_("Slot", (byte)slot);
            item.getMCItemStack().m_41739_(nbttagcompound);
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtItemStackArray(ItemStack[] inventory) {
        ListTag nbttaglist = new ListTag();
        if (inventory == null) {
            return nbttaglist;
        }
        for (int slot = 0; slot < inventory.length; ++slot) {
            ItemStack item = inventory[slot];
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128344_("Slot", (byte)slot);
            if (item != null) {
                item.m_41739_(nbttagcompound);
            }
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtIngredientList(NonNullList<Ingredient> inventory) {
        ListTag nbttaglist = new ListTag();
        if (inventory == null) {
            return nbttaglist;
        }
        for (int slot = 0; slot < inventory.size(); ++slot) {
            Ingredient ingredient = (Ingredient)inventory.get(slot);
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128344_("Slot", (byte)slot);
            if (ingredient != null && ingredient.m_43908_().length > 0) {
                ingredient.m_43908_()[0].m_41739_(nbttagcompound);
            }
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtBooleanList(HashMap<Integer, Boolean> updatedSlots) {
        ListTag nbttaglist = new ListTag();
        if (updatedSlots == null) {
            return nbttaglist;
        }
        HashMap<Integer, Boolean> inventory2 = updatedSlots;
        for (Integer slot : inventory2.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot.intValue());
            nbttagcompound.m_128379_("Boolean", inventory2.get(slot).booleanValue());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtIntegerIntegerMap(Map<Integer, Integer> lines) {
        ListTag nbttaglist = new ListTag();
        if (lines == null) {
            return nbttaglist;
        }
        for (int slot : lines.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot);
            nbttagcompound.m_128405_("Integer", lines.get(slot).intValue());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtFloatMap(Map<Integer, Float> lines) {
        ListTag nbttaglist = new ListTag();
        if (lines == null) {
            return nbttaglist;
        }
        for (int slot : lines.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot);
            nbttagcompound.m_128350_("Integer", lines.get(slot).floatValue());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtIntegerLongMap(HashMap<Integer, Long> lines) {
        ListTag nbttaglist = new ListTag();
        if (lines == null) {
            return nbttaglist;
        }
        for (int slot : lines.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot);
            nbttagcompound.m_128356_("Long", lines.get(slot).longValue());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtIntegerCollection(Collection<Integer> set) {
        ListTag nbttaglist = new ListTag();
        if (set == null) {
            return nbttaglist;
        }
        for (int slot : set) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Integer", slot);
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtVectorMap(HashMap<String, Vector<String>> map) {
        ListTag list = new ListTag();
        if (map == null) {
            return list;
        }
        for (String key : map.keySet()) {
            CompoundTag compound = new CompoundTag();
            compound.m_128359_("Key", key);
            ListTag values = new ListTag();
            for (String value : map.get(key)) {
                CompoundTag comp = new CompoundTag();
                comp.m_128359_("Value", value);
                values.add((Object)comp);
            }
            compound.m_128365_("Values", (Tag)values);
            list.add((Object)compound);
        }
        return list;
    }

    public static ListTag nbtStringStringMap(HashMap<String, String> map) {
        ListTag nbttaglist = new ListTag();
        if (map == null) {
            return nbttaglist;
        }
        for (String slot : map.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128359_("Slot", slot);
            nbttagcompound.m_128359_("Value", map.get(slot));
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtStringIntegerMap(Map<String, Integer> map) {
        ListTag nbttaglist = new ListTag();
        if (map == null) {
            return nbttaglist;
        }
        for (String slot : map.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128359_("Slot", slot);
            nbttagcompound.m_128405_("Value", map.get(slot).intValue());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static Tag nbtIntegerStringMap(Map<Integer, String> map) {
        ListTag nbttaglist = new ListTag();
        if (map == null) {
            return nbttaglist;
        }
        for (int slot : map.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot);
            nbttagcompound.m_128359_("Value", map.get(slot));
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtStringArray(String[] list) {
        ListTag nbttaglist = new ListTag();
        if (list == null) {
            return nbttaglist;
        }
        for (int i = 0; i < list.length; ++i) {
            if (list[i] == null) continue;
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128359_("Value", list[i]);
            nbttagcompound.m_128405_("Slot", i);
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtStringList(List<String> list) {
        ListTag nbttaglist = new ListTag();
        for (String s : list) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128359_("Line", s);
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtResourceLocationList(List<ResourceLocation> list) {
        ListTag nbttaglist = new ListTag();
        for (ResourceLocation s : list) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128359_("Line", s.toString());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    public static ListTag nbtDoubleList(double ... par1ArrayOfDouble) {
        ListTag nbttaglist = new ListTag();
        double[] adouble = par1ArrayOfDouble;
        int i = par1ArrayOfDouble.length;
        for (int j = 0; j < i; ++j) {
            double d1 = adouble[j];
            nbttaglist.add((Object)DoubleTag.m_128500_((double)d1));
        }
        return nbttaglist;
    }

    public static CompoundTag NBTMerge(CompoundTag data, CompoundTag merge) {
        CompoundTag compound = data.m_6426_();
        Set names = merge.m_128431_();
        for (String name : names) {
            Tag base = merge.m_128423_(name);
            if (base.m_7060_() == 10) {
                base = NBTTags.NBTMerge(compound.m_128469_(name), (CompoundTag)base);
            }
            compound.m_128365_(name, base);
        }
        return compound;
    }

    public static List<ScriptContainer> GetScript(ListTag list, IScriptHandler handler) {
        ArrayList<ScriptContainer> scripts = new ArrayList<ScriptContainer>();
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag compoundd = list.m_128728_(i);
            ScriptContainer script = new ScriptContainer(handler);
            script.load(compoundd);
            scripts.add(script);
        }
        return scripts;
    }

    public static ListTag NBTScript(List<ScriptContainer> scripts) {
        ListTag list = new ListTag();
        for (ScriptContainer script : scripts) {
            CompoundTag compound = new CompoundTag();
            script.save(compound);
            list.add((Object)compound);
        }
        return list;
    }

    public static TreeMap<Long, String> GetLongStringMap(ListTag tagList) {
        TreeMap<Long, String> list = new TreeMap<Long, String>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            list.put(nbttagcompound.m_128454_("Long"), nbttagcompound.m_128461_("String"));
        }
        return list;
    }

    public static ListTag NBTLongStringMap(Map<Long, String> map) {
        ListTag nbttaglist = new ListTag();
        if (map == null) {
            return nbttaglist;
        }
        for (long slot : map.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128356_("Long", slot);
            nbttagcompound.m_128359_("String", map.get(slot));
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }
}

