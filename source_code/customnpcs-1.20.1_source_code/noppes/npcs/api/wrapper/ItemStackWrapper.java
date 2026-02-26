/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  com.google.gson.JsonParseException
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.item.WritableBookItem
 *  net.minecraft.world.item.WrittenBookItem
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.common.IPlantable
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.CapabilityManager
 *  net.minecraftforge.common.capabilities.CapabilityToken
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.capabilities.ICapabilitySerializable
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.event.AttachCapabilitiesEvent
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.api.wrapper;

import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.ItemStackEmptyWrapper;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.INbt;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IMob;
import noppes.npcs.api.entity.data.IData;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemArmorWrapper;
import noppes.npcs.api.wrapper.ItemBlockWrapper;
import noppes.npcs.api.wrapper.ItemBookWrapper;
import noppes.npcs.api.wrapper.ItemScriptedWrapper;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.items.ItemScripted;

public class ItemStackWrapper
implements IItemStack,
ICapabilitySerializable<CompoundTag> {
    private Map<String, Object> tempData = new HashMap<String, Object>();
    public static Capability<ItemStackWrapper> ITEMSCRIPTEDDATA_CAPABILITY = CapabilityManager.get((CapabilityToken)new CapabilityToken<ItemStackWrapper>(){});
    private LazyOptional<ItemStackWrapper> instance = LazyOptional.of(() -> this);
    private static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public ItemStack item;
    private CompoundTag storedData = new CompoundTag();
    public static ItemStackWrapper AIR = new ItemStackEmptyWrapper();
    private final IData tempdata = new IData(){

        @Override
        public void put(String key, Object value) {
            ItemStackWrapper.this.tempData.put(key, value);
        }

        @Override
        public Object get(String key) {
            return ItemStackWrapper.this.tempData.get(key);
        }

        @Override
        public void remove(String key) {
            ItemStackWrapper.this.tempData.remove(key);
        }

        @Override
        public boolean has(String key) {
            return ItemStackWrapper.this.tempData.containsKey(key);
        }

        @Override
        public void clear() {
            ItemStackWrapper.this.tempData.clear();
        }

        @Override
        public String[] getKeys() {
            return ItemStackWrapper.this.tempData.keySet().toArray(new String[ItemStackWrapper.this.tempData.size()]);
        }
    };
    private final IData storeddata = new IData(){

        @Override
        public void put(String key, Object value) {
            if (value instanceof Number) {
                ItemStackWrapper.this.storedData.m_128347_(key, ((Number)value).doubleValue());
            } else if (value instanceof String) {
                ItemStackWrapper.this.storedData.m_128359_(key, (String)value);
            }
        }

        @Override
        public Object get(String key) {
            if (!ItemStackWrapper.this.storedData.m_128441_(key)) {
                return null;
            }
            Tag base = ItemStackWrapper.this.storedData.m_128423_(key);
            if (base instanceof NumericTag) {
                return ((NumericTag)base).m_7061_();
            }
            return base.m_7916_();
        }

        @Override
        public void remove(String key) {
            ItemStackWrapper.this.storedData.m_128473_(key);
        }

        @Override
        public boolean has(String key) {
            return ItemStackWrapper.this.storedData.m_128441_(key);
        }

        @Override
        public void clear() {
            ItemStackWrapper.this.storedData = new CompoundTag();
        }

        @Override
        public String[] getKeys() {
            return ItemStackWrapper.this.storedData.m_128431_().toArray(new String[ItemStackWrapper.this.storedData.m_128431_().size()]);
        }
    };
    private static final ResourceLocation key = new ResourceLocation("customnpcs", "itemscripteddata");

    protected ItemStackWrapper(ItemStack item) {
        this.item = item;
    }

    @Override
    public IData getTempdata() {
        return this.tempdata;
    }

    @Override
    public IData getStoreddata() {
        return this.storeddata;
    }

    @Override
    public int getStackSize() {
        return this.item.m_41613_();
    }

    @Override
    public void setStackSize(int size) {
        if (size > this.getMaxStackSize()) {
            throw new CustomNPCsException("Can't set the stacksize bigger than MaxStacksize", new Object[0]);
        }
        this.item.m_41764_(size);
    }

    @Override
    public void setAttribute(String name, double value) {
        this.setAttribute(name, value, -1);
    }

    @Override
    public void setAttribute(String name, double value, int slot) {
        if (slot < -1 || slot > 5) {
            throw new CustomNPCsException("Slot has to be between -1 and 5, given was: " + slot, new Object[0]);
        }
        CompoundTag compound = this.item.m_41783_();
        if (compound == null) {
            compound = new CompoundTag();
            this.item.m_41751_(compound);
        }
        ListTag nbttaglist = compound.m_128437_("AttributeModifiers", 10);
        ListTag newList = new ListTag();
        UUID uuid = null;
        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag c = nbttaglist.m_128728_(i);
            if (!c.m_128461_("AttributeName").equals(name)) {
                newList.add((Object)c);
                continue;
            }
            uuid = c.m_128342_("UUID");
        }
        if (value != 0.0) {
            CompoundTag nbttagcompound = new AttributeModifier(name, value, AttributeModifier.Operation.ADDITION).m_22219_();
            nbttagcompound.m_128359_("AttributeName", name);
            if (slot >= 0) {
                nbttagcompound.m_128359_("Slot", EquipmentSlot.values()[slot].m_20751_());
            }
            if (uuid != null) {
                nbttagcompound.m_128362_("UUID", uuid);
            }
            newList.add((Object)nbttagcompound);
        }
        compound.m_128365_("AttributeModifiers", (Tag)newList);
    }

    @Override
    public double getAttribute(String name) {
        CompoundTag compound = this.item.m_41783_();
        if (compound == null) {
            return 0.0;
        }
        Multimap map = this.item.m_41638_(EquipmentSlot.MAINHAND);
        for (Map.Entry entry : map.entries()) {
            if (!((Attribute)entry.getKey()).m_22087_().equals(name)) continue;
            AttributeModifier mod = (AttributeModifier)entry.getValue();
            return mod.m_22218_();
        }
        return 0.0;
    }

    @Override
    public boolean hasAttribute(String name) {
        CompoundTag compound = this.item.m_41783_();
        if (compound == null) {
            return false;
        }
        ListTag nbttaglist = compound.m_128437_("AttributeModifiers", 10);
        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag c = nbttaglist.m_128728_(i);
            if (!c.m_128461_("AttributeName").equals(name)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void addEnchantment(String id, int strenght) {
        Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
        if (ench == null) {
            throw new CustomNPCsException("Unknown enchant id:" + id, new Object[0]);
        }
        this.item.m_41663_(ench, strenght);
    }

    @Override
    public boolean isEnchanted() {
        return this.item.m_41793_();
    }

    @Override
    public boolean hasEnchant(String id) {
        Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
        if (ench == null) {
            throw new CustomNPCsException("Unknown enchant id:" + id, new Object[0]);
        }
        if (!this.isEnchanted()) {
            return false;
        }
        ListTag list = this.item.m_41785_();
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag compound = list.m_128728_(i);
            if (!compound.m_128461_("id").equalsIgnoreCase(id)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEnchant(String id) {
        Enchantment ench = (Enchantment)ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id));
        if (ench == null) {
            throw new CustomNPCsException("Unknown enchant id:" + id, new Object[0]);
        }
        if (!this.isEnchanted()) {
            return false;
        }
        ListTag list = this.item.m_41785_();
        ListTag newList = new ListTag();
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag compound = list.m_128728_(i);
            if (compound.m_128461_("id").equalsIgnoreCase(id)) continue;
            newList.add((Object)compound);
        }
        if (list.size() == newList.size()) {
            return false;
        }
        this.item.m_41783_().m_128365_("ench", (Tag)newList);
        return true;
    }

    @Override
    public boolean isBlock() {
        Block block = Block.m_49814_((Item)this.item.m_41720_());
        return block != null && block != Blocks.f_50016_;
    }

    @Override
    public boolean hasCustomName() {
        return this.item.m_41788_();
    }

    @Override
    public void setCustomName(String name) {
        this.item.m_41714_((Component)Component.m_237115_((String)name));
    }

    @Override
    public String getDisplayName() {
        return this.item.m_41786_().getString();
    }

    @Override
    public String getItemName() {
        return this.item.m_41720_().m_7626_(this.item).getString();
    }

    @Override
    public String getName() {
        return ForgeRegistries.ITEMS.getKey((Object)this.item.m_41720_()).toString();
    }

    @Override
    public INbt getNbt() {
        CompoundTag compound = this.item.m_41783_();
        if (compound == null) {
            compound = new CompoundTag();
            this.item.m_41751_(compound);
        }
        return NpcAPI.Instance().getINbt(compound);
    }

    @Override
    public boolean hasNbt() {
        CompoundTag compound = this.item.m_41783_();
        return compound != null && !compound.m_128456_();
    }

    @Override
    public ItemStack getMCItemStack() {
        return this.item;
    }

    public static ItemStack MCItem(IItemStack item) {
        if (item == null) {
            return ItemStack.f_41583_;
        }
        return item.getMCItemStack();
    }

    @Override
    public void damageItem(int damage, IMob living) {
        if (living != null) {
            this.item.m_41622_(damage, living == null ? null : (LivingEntity)living.getMCEntity(), e -> e.m_21166_(EquipmentSlot.MAINHAND));
        } else if (this.item.m_41763_()) {
            if (this.item.m_41773_() <= damage) {
                this.item.m_41774_(1);
                this.item.m_41721_(0);
            } else {
                this.item.m_41721_(this.item.m_41773_() - damage);
            }
        }
    }

    @Override
    public boolean isBook() {
        return false;
    }

    @Override
    public int getFoodLevel() {
        if (this.item.m_41720_().m_41473_() != null) {
            return this.item.m_41720_().m_41473_().m_38744_();
        }
        return 0;
    }

    @Override
    public IItemStack copy() {
        return ItemStackWrapper.createNew(this.item.m_41777_());
    }

    @Override
    public int getMaxStackSize() {
        return this.item.m_41741_();
    }

    @Override
    public boolean isDamageable() {
        return this.item.m_41763_();
    }

    @Override
    public int getDamage() {
        return this.item.m_41773_();
    }

    @Override
    public void setDamage(int value) {
        this.item.m_41721_(value);
    }

    @Deprecated
    public int getItemDamage() {
        return this.item.m_41773_();
    }

    @Deprecated
    public void setItemDamage(int value) {
        this.item.m_41721_(value);
    }

    @Override
    public int getMaxDamage() {
        return this.item.m_41776_();
    }

    @Override
    public INbt getItemNbt() {
        CompoundTag compound = new CompoundTag();
        this.item.m_41739_(compound);
        return NpcAPI.Instance().getINbt(compound);
    }

    @Override
    public double getAttackDamage() {
        Multimap map = this.item.m_41638_(EquipmentSlot.MAINHAND);
        double damage = 0.0;
        for (Map.Entry entry : map.entries()) {
            if (entry.getKey() != Attributes.f_22281_) continue;
            AttributeModifier mod = (AttributeModifier)entry.getValue();
            damage = mod.m_22218_();
        }
        return damage + (double)EnchantmentHelper.m_44833_((ItemStack)this.item, (MobType)MobType.f_21640_);
    }

    @Override
    public boolean isEmpty() {
        return this.item.m_41619_();
    }

    @Override
    public int getType() {
        if (this.item.m_41720_() instanceof IPlantable) {
            return 5;
        }
        if (this.item.m_41720_() instanceof SwordItem) {
            return 4;
        }
        return 0;
    }

    @Override
    public boolean isWearable() {
        for (EquipmentSlot slot : VALID_EQUIPMENT_SLOTS) {
            if (!this.item.m_41720_().canEquip(this.item, slot, (Entity)EntityNPCInterface.CommandPlayer)) continue;
            return true;
        }
        return false;
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == ITEMSCRIPTEDDATA_CAPABILITY) {
            return this.instance.cast();
        }
        return LazyOptional.empty();
    }

    public static void register(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStackWrapper wrapper = ItemStackWrapper.createNew((ItemStack)event.getObject());
        event.addCapability(key, (ICapabilityProvider)wrapper);
    }

    private static ItemStackWrapper createNew(ItemStack item) {
        if (item == null || item.m_41619_()) {
            return AIR;
        }
        if (item.m_41720_() instanceof ItemScripted) {
            return new ItemScriptedWrapper(item);
        }
        if (item.m_41720_() == Items.f_42615_ || item.m_41720_() == Items.f_42614_ || item.m_41720_() instanceof WritableBookItem || item.m_41720_() instanceof WrittenBookItem) {
            return new ItemBookWrapper(item);
        }
        if (item.m_41720_() instanceof ArmorItem) {
            return new ItemArmorWrapper(item);
        }
        Block block = Block.m_49814_((Item)item.m_41720_());
        if (block != Blocks.f_50016_) {
            return new ItemBlockWrapper(item);
        }
        return new ItemStackWrapper(item);
    }

    @Override
    public String[] getLore() {
        CompoundTag compound = this.item.m_41737_("display");
        if (compound == null || compound.m_128435_("Lore") != 9) {
            return new String[0];
        }
        ListTag nbttaglist = compound.m_128437_("Lore", 8);
        if (nbttaglist.isEmpty()) {
            return new String[0];
        }
        ArrayList<String> lore = new ArrayList<String>();
        for (int i = 0; i < nbttaglist.size(); ++i) {
            lore.add(nbttaglist.m_128778_(i));
        }
        return lore.toArray(new String[lore.size()]);
    }

    @Override
    public void setLore(String[] lore) {
        CompoundTag compound = this.item.m_41698_("display");
        if (lore == null || lore.length == 0) {
            compound.m_128473_("Lore");
            return;
        }
        ListTag nbtlist = new ListTag();
        for (String s : lore) {
            try {
                Component.Serializer.m_130701_((String)s);
            }
            catch (JsonParseException jsonparseexception) {
                s = Component.Serializer.m_130703_((Component)Component.m_237115_((String)s));
            }
            nbtlist.add((Object)StringTag.m_129297_((String)s));
        }
        compound.m_128365_("Lore", (Tag)nbtlist);
    }

    public CompoundTag serializeNBT() {
        return this.getMCNbt();
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.setMCNbt(nbt);
    }

    public CompoundTag getMCNbt() {
        CompoundTag compound = new CompoundTag();
        if (!this.storedData.m_128456_()) {
            compound.m_128365_("StoredData", (Tag)this.storedData);
        }
        return compound;
    }

    public void setMCNbt(CompoundTag compound) {
        this.storedData = compound == null ? new CompoundTag() : compound.m_128469_("StoredData");
    }

    @Override
    public void removeNbt() {
        this.item.m_41751_(null);
    }

    @Override
    public boolean compare(IItemStack item, boolean ignoreNBT) {
        if (item == null) {
            item = AIR;
        }
        return NoppesUtilPlayer.compareItems(this.getMCItemStack(), item.getMCItemStack(), false, ignoreNBT);
    }
}

