/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.roles;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.data.role.IRoleTrader;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;
import noppes.npcs.util.NBTJsonUtil;

public class RoleTrader
extends RoleInterface
implements IRoleTrader {
    public String marketName = "";
    public NpcMiscInventory inventoryCurrency = new NpcMiscInventory(36);
    public NpcMiscInventory inventorySold = new NpcMiscInventory(18);
    public boolean ignoreDamage = false;
    public boolean ignoreNBT = false;
    public boolean toSave = false;

    public RoleTrader(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128359_("TraderMarket", this.marketName);
        this.writeNBT(nbttagcompound);
        if (this.toSave && !this.npc.isClientSide()) {
            RoleTrader.save(this, this.marketName);
        }
        this.toSave = false;
        return nbttagcompound;
    }

    public CompoundTag writeNBT(CompoundTag nbttagcompound) {
        nbttagcompound.m_128365_("TraderCurrency", (Tag)this.inventoryCurrency.getToNBT());
        nbttagcompound.m_128365_("TraderSold", (Tag)this.inventorySold.getToNBT());
        nbttagcompound.m_128379_("TraderIgnoreDamage", this.ignoreDamage);
        nbttagcompound.m_128379_("TraderIgnoreNBT", this.ignoreNBT);
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.marketName = nbttagcompound.m_128461_("TraderMarket");
        this.readNBT(nbttagcompound);
    }

    public void readNBT(CompoundTag nbttagcompound) {
        this.inventoryCurrency.setFromNBT(nbttagcompound.m_128469_("TraderCurrency"));
        this.inventorySold.setFromNBT(nbttagcompound.m_128469_("TraderSold"));
        this.ignoreDamage = nbttagcompound.m_128471_("TraderIgnoreDamage");
        this.ignoreNBT = nbttagcompound.m_128471_("TraderIgnoreNBT");
    }

    @Override
    public void interact(Player player) {
        this.npc.say(player, this.npc.advanced.getInteractLine());
        try {
            RoleTrader.load(this, this.marketName);
        }
        catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTrader, this.npc);
    }

    public boolean hasCurrency(ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        for (ItemStack item : this.inventoryCurrency.items) {
            if (item.m_41619_() || !NoppesUtilPlayer.compareItems(item, itemstack, this.ignoreDamage, this.ignoreNBT)) continue;
            return true;
        }
        return false;
    }

    @Override
    public IItemStack getSold(int slot) {
        return NpcAPI.Instance().getIItemStack(this.inventorySold.m_8020_(slot));
    }

    @Override
    public IItemStack getCurrency1(int slot) {
        return NpcAPI.Instance().getIItemStack(this.inventoryCurrency.m_8020_(slot));
    }

    @Override
    public IItemStack getCurrency2(int slot) {
        return NpcAPI.Instance().getIItemStack(this.inventoryCurrency.m_8020_(slot + 18));
    }

    @Override
    public void set(int slot, IItemStack currency, IItemStack currency2, IItemStack sold) {
        if (sold == null) {
            throw new CustomNPCsException("Sold item was null", new Object[0]);
        }
        if (slot >= 18 || slot < 0) {
            throw new CustomNPCsException("Invalid slot: " + slot, new Object[0]);
        }
        if (currency == null) {
            currency = currency2;
            currency2 = null;
        }
        if (currency != null) {
            this.inventoryCurrency.items.set(slot, (Object)currency.getMCItemStack());
        } else {
            this.inventoryCurrency.items.set(slot, (Object)ItemStack.f_41583_);
        }
        if (currency2 != null) {
            this.inventoryCurrency.items.set(slot + 18, (Object)currency2.getMCItemStack());
        } else {
            this.inventoryCurrency.items.set(slot + 18, (Object)ItemStack.f_41583_);
        }
        this.inventorySold.items.set(slot, (Object)sold.getMCItemStack());
    }

    @Override
    public void remove(int slot) {
        if (slot >= 18 || slot < 0) {
            throw new CustomNPCsException("Invalid slot: " + slot, new Object[0]);
        }
        this.inventoryCurrency.items.set(slot, (Object)ItemStack.f_41583_);
        this.inventoryCurrency.items.set(slot + 18, (Object)ItemStack.f_41583_);
        this.inventorySold.items.set(slot, (Object)ItemStack.f_41583_);
    }

    @Override
    public void setMarket(String name) {
        this.marketName = name;
        RoleTrader.load(this, name);
    }

    @Override
    public String getMarket() {
        return this.marketName;
    }

    public static void save(RoleTrader r, String name) {
        if (name.isEmpty()) {
            return;
        }
        File file = RoleTrader.getFile(name + "_new");
        File file1 = RoleTrader.getFile(name);
        try {
            NBTJsonUtil.SaveFile(file, r.writeNBT(new CompoundTag()));
            if (file1.exists()) {
                file1.delete();
            }
            file.renameTo(file1);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void load(RoleTrader role, String name) {
        if (role.npc.m_9236_().f_46443_) {
            return;
        }
        File file = RoleTrader.getFile(name);
        if (!file.exists()) {
            return;
        }
        try {
            role.readNBT(NBTJsonUtil.LoadFile(file));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static File getFile(String name) {
        File dir = new File(CustomNpcs.getLevelSaveDirectory(), "markets");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return new File(dir, name.toLowerCase() + ".json");
    }

    public static void setMarket(EntityNPCInterface npc, String marketName) {
        if (marketName.isEmpty()) {
            return;
        }
        if (!RoleTrader.getFile(marketName).exists()) {
            RoleTrader.save((RoleTrader)npc.role, marketName);
        }
        RoleTrader.load((RoleTrader)npc.role, marketName);
    }

    @Override
    public int getType() {
        return 1;
    }
}

