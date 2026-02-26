/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraftforge.common.ForgeHooks
 */
package noppes.npcs.entity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeHooks;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.data.INPCInventory;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.util.ValueUtil;

public class DataInventory
extends SimpleContainer
implements INPCInventory {
    public Map<Integer, IItemStack> drops = new HashMap<Integer, IItemStack>();
    public Map<Integer, Float> dropchance = new HashMap<Integer, Float>();
    public Map<Integer, IItemStack> weapons = new HashMap<Integer, IItemStack>();
    public Map<Integer, IItemStack> armor = new HashMap<Integer, IItemStack>();
    private int minExp = 0;
    private int maxExp = 0;
    public int lootMode = 0;
    private EntityNPCInterface npc;

    public DataInventory(EntityNPCInterface npc) {
        super(new ItemStack[0]);
        this.npc = npc;
    }

    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128405_("MinExp", this.minExp);
        nbttagcompound.m_128405_("MaxExp", this.maxExp);
        nbttagcompound.m_128365_("NpcInv", (Tag)NBTTags.nbtIItemStackMap(this.drops));
        nbttagcompound.m_128365_("Armor", (Tag)NBTTags.nbtIItemStackMap(this.armor));
        nbttagcompound.m_128365_("Weapons", (Tag)NBTTags.nbtIItemStackMap(this.weapons));
        nbttagcompound.m_128365_("DropChance", (Tag)NBTTags.nbtFloatMap(this.dropchance));
        nbttagcompound.m_128405_("LootMode", this.lootMode);
        return nbttagcompound;
    }

    public void load(CompoundTag nbttagcompound) {
        this.minExp = nbttagcompound.m_128451_("MinExp");
        this.maxExp = nbttagcompound.m_128451_("MaxExp");
        this.drops = NBTTags.getIItemStackMap(nbttagcompound.m_128437_("NpcInv", 10));
        this.armor = NBTTags.getIItemStackMap(nbttagcompound.m_128437_("Armor", 10));
        this.weapons = NBTTags.getIItemStackMap(nbttagcompound.m_128437_("Weapons", 10));
        this.dropchance = NBTTags.getFloatIntegerMap(nbttagcompound.m_128437_("DropChance", 10));
        this.lootMode = nbttagcompound.m_128451_("LootMode");
    }

    @Override
    public IItemStack getArmor(int slot) {
        return this.armor.get(slot);
    }

    @Override
    public void setArmor(int slot, IItemStack item) {
        this.armor.put(slot, item);
        this.npc.updateClient = true;
    }

    @Override
    public IItemStack getRightHand() {
        return this.weapons.get(0);
    }

    @Override
    public void setRightHand(IItemStack item) {
        this.weapons.put(0, item);
        this.npc.updateClient = true;
    }

    @Override
    public IItemStack getProjectile() {
        return this.weapons.get(1);
    }

    @Override
    public void setProjectile(IItemStack item) {
        this.weapons.put(1, item);
        this.npc.updateAI = true;
    }

    @Override
    public IItemStack getLeftHand() {
        return this.weapons.get(2);
    }

    @Override
    public void setLeftHand(IItemStack item) {
        this.weapons.put(2, item);
        this.npc.updateClient = true;
    }

    @Override
    public IItemStack getDropItem(int slot) {
        if (slot < 0 || slot > 20) {
            throw new CustomNPCsException("Bad slot number: " + slot, new Object[0]);
        }
        IItemStack item = this.npc.inventory.drops.get(slot);
        if (item == null) {
            return ItemStackWrapper.AIR;
        }
        return NpcAPI.Instance().getIItemStack(item.getMCItemStack());
    }

    @Override
    public void setDropItem(int slot, IItemStack item, float chance) {
        if (slot < 0 || slot > 20) {
            throw new CustomNPCsException("Bad slot number: " + slot, new Object[0]);
        }
        chance = ValueUtil.correctFloat(chance, 1.0f, 100.0f);
        if (item == null || item.isEmpty()) {
            this.dropchance.remove(slot);
            this.drops.remove(slot);
        } else {
            this.dropchance.put(slot, Float.valueOf(chance));
            this.drops.put(slot, item);
        }
    }

    @Override
    public IItemStack[] getItemsRNG() {
        ArrayList<IItemStack> list = new ArrayList<IItemStack>();
        for (int i : this.drops.keySet()) {
            float chance;
            IItemStack item = this.drops.get(i);
            if (item == null || item.isEmpty()) continue;
            float dchance = 100.0f;
            if (this.dropchance.containsKey(i)) {
                dchance = this.dropchance.get(i).floatValue();
            }
            if (!((chance = (float)this.npc.m_9236_().f_46441_.m_188503_(100) + dchance) >= 100.0f)) continue;
            list.add(item);
        }
        return list.toArray(new IItemStack[list.size()]);
    }

    public void dropStuff(NpcEvent.DiedEvent event, Entity entity, DamageSource damagesource) {
        int var2;
        ArrayList<ItemEntity> list = new ArrayList<ItemEntity>();
        if (event.droppedItems != null) {
            for (IItemStack item : event.droppedItems) {
                ItemEntity e = this.getItemEntity(item.getMCItemStack().m_41777_());
                if (e == null) continue;
                list.add(e);
            }
        }
        int enchant = 0;
        if (damagesource.m_7639_() instanceof Player) {
            enchant = EnchantmentHelper.m_44930_((LivingEntity)((LivingEntity)damagesource.m_7639_()));
        }
        if (!ForgeHooks.onLivingDrops((LivingEntity)this.npc, (DamageSource)damagesource, list, (int)enchant, (boolean)true)) {
            for (ItemEntity item : list) {
                if (this.lootMode == 1 && entity instanceof Player) {
                    Player player = (Player)entity;
                    item.m_32010_(2);
                    this.npc.m_9236_().m_7967_((Entity)item);
                    ItemStack stack = item.m_32055_();
                    int i = stack.m_41613_();
                    if (!player.m_150109_().m_36054_(stack)) continue;
                    entity.m_9236_().m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2f, ((player.m_217043_().m_188501_() - player.m_217043_().m_188501_()) * 0.7f + 1.0f) * 2.0f);
                    player.m_7938_((Entity)item, i);
                    if (stack.m_41613_() > 0) continue;
                    item.m_142687_(Entity.RemovalReason.DISCARDED);
                    continue;
                }
                this.npc.m_9236_().m_7967_((Entity)item);
            }
        }
        for (int exp = event.expDropped; exp > 0; exp -= var2) {
            var2 = ExperienceOrb.m_20782_((int)exp);
            if (this.lootMode == 1 && entity instanceof Player) {
                this.npc.m_9236_().m_7967_((Entity)new ExperienceOrb(entity.m_9236_(), entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), var2));
                continue;
            }
            this.npc.m_9236_().m_7967_((Entity)new ExperienceOrb(this.npc.m_9236_(), this.npc.m_20185_(), this.npc.m_20186_(), this.npc.m_20189_(), var2));
        }
    }

    public ItemEntity getItemEntity(ItemStack itemstack) {
        if (itemstack == null || itemstack.m_41619_()) {
            return null;
        }
        ItemEntity entityitem = new ItemEntity(this.npc.m_9236_(), this.npc.m_20185_(), this.npc.m_20186_() - (double)0.3f + (double)this.npc.m_20192_(), this.npc.m_20189_(), itemstack);
        entityitem.m_32010_(40);
        float f2 = this.npc.m_217043_().m_188501_() * 0.5f;
        float f4 = this.npc.m_217043_().m_188501_() * 3.141593f * 2.0f;
        entityitem.m_20334_((double)(-Mth.m_14031_((float)f4) * f2), (double)0.2f, (double)(Mth.m_14089_((float)f4) * f2));
        return entityitem;
    }

    public int m_6643_() {
        return 15;
    }

    public ItemStack m_8020_(int i) {
        if (i < 4) {
            return ItemStackWrapper.MCItem(this.getArmor(i));
        }
        if (i < 7) {
            return ItemStackWrapper.MCItem(this.weapons.get(i - 4));
        }
        return ItemStackWrapper.MCItem(this.drops.get(i - 7));
    }

    public ItemStack m_7407_(int par1, int limbSwingAmount) {
        Map<Integer, IItemStack> var3;
        int i = 0;
        if (par1 >= 7) {
            var3 = this.drops;
            par1 -= 7;
        } else if (par1 >= 4) {
            var3 = this.weapons;
            par1 -= 4;
            i = 1;
        } else {
            var3 = this.armor;
            i = 2;
        }
        ItemStack var4 = null;
        if (var3.get(par1) != null) {
            if (var3.get(par1).getMCItemStack().m_41613_() <= limbSwingAmount) {
                var4 = var3.get(par1).getMCItemStack();
                var3.put(par1, null);
            } else {
                var4 = var3.get(par1).getMCItemStack().m_41620_(limbSwingAmount);
                if (var3.get(par1).getMCItemStack().m_41613_() == 0) {
                    var3.put(par1, null);
                }
            }
        }
        if (i == 1) {
            this.weapons = var3;
        }
        if (i == 2) {
            this.armor = var3;
        }
        if (var4 == null) {
            return ItemStack.f_41583_;
        }
        return var4;
    }

    public ItemStack m_8016_(int par1) {
        Map<Integer, IItemStack> var2;
        int i = 0;
        if (par1 >= 7) {
            var2 = this.drops;
            par1 -= 7;
        } else if (par1 >= 4) {
            var2 = this.weapons;
            par1 -= 4;
            i = 1;
        } else {
            var2 = this.armor;
            i = 2;
        }
        if (var2.get(par1) != null) {
            ItemStack var3 = var2.get(par1).getMCItemStack();
            var2.put(par1, null);
            if (i == 1) {
                this.weapons = var2;
            }
            if (i == 2) {
                this.armor = var2;
            }
            return var3;
        }
        return ItemStack.f_41583_;
    }

    public void m_6836_(int par1, ItemStack limbSwingAmountItemStack) {
        Map<Integer, IItemStack> var3;
        int i = 0;
        if (par1 >= 7) {
            var3 = this.drops;
            par1 -= 7;
        } else if (par1 >= 4) {
            var3 = this.weapons;
            par1 -= 4;
            i = 1;
        } else {
            var3 = this.armor;
            i = 2;
        }
        var3.put(par1, NpcAPI.Instance().getIItemStack(limbSwingAmountItemStack));
        if (i == 1) {
            this.weapons = var3;
        }
        if (i == 2) {
            this.armor = var3;
        }
    }

    public int m_6893_() {
        return 64;
    }

    public boolean m_6542_(Player var1) {
        return true;
    }

    public boolean m_7013_(int i, ItemStack itemstack) {
        return true;
    }

    public void m_6596_() {
    }

    public void m_5856_(Player player) {
    }

    public void m_5785_(Player player) {
    }

    @Override
    public int getExpMin() {
        return this.npc.inventory.minExp;
    }

    @Override
    public int getExpMax() {
        return this.npc.inventory.maxExp;
    }

    @Override
    public int getExpRNG() {
        int exp = this.minExp;
        if (this.maxExp - this.minExp > 0) {
            exp += this.npc.m_9236_().f_46441_.m_188503_(this.maxExp - this.minExp);
        }
        return exp;
    }

    @Override
    public void setExp(int min, int max) {
        this.npc.inventory.minExp = min = Math.min(min, max);
        this.npc.inventory.maxExp = max;
    }

    public boolean m_7983_() {
        for (int slot = 0; slot < this.m_6643_(); ++slot) {
            ItemStack item = this.m_8020_(slot);
            if (NoppesUtilServer.IsItemStackNull(item) || item.m_41619_()) continue;
            return false;
        }
        return true;
    }

    public void m_6211_() {
    }
}

