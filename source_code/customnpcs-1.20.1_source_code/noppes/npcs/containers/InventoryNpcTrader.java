/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.containers;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.containers.ContainerNPCTrader;

public class InventoryNpcTrader
implements Container {
    private String inventoryTitle;
    private int slotsCount;
    public final NonNullList<ItemStack> inventoryContents;
    private ContainerNPCTrader con;

    public InventoryNpcTrader(String s, int i, ContainerNPCTrader con) {
        this.con = con;
        this.inventoryTitle = s;
        this.slotsCount = i;
        this.inventoryContents = NonNullList.m_122780_((int)i, (Object)ItemStack.f_41583_);
    }

    public ItemStack m_8020_(int i) {
        ItemStack toBuy = (ItemStack)this.inventoryContents.get(i);
        if (NoppesUtilServer.IsItemStackNull(toBuy)) {
            return ItemStack.f_41583_;
        }
        return toBuy.m_41777_();
    }

    public ItemStack m_7407_(int i, int j) {
        ItemStack stack = (ItemStack)this.inventoryContents.get(i);
        if (!NoppesUtilServer.IsItemStackNull(stack)) {
            return stack.m_41777_();
        }
        return ItemStack.f_41583_;
    }

    public void m_6836_(int i, ItemStack itemstack) {
        if (!itemstack.m_41619_()) {
            this.inventoryContents.set(i, (Object)itemstack.m_41777_());
        }
        this.m_6596_();
    }

    public int m_6643_() {
        return this.slotsCount;
    }

    public int m_6893_() {
        return 64;
    }

    public boolean m_6542_(Player entityplayer) {
        return true;
    }

    public ItemStack m_8016_(int i) {
        return (ItemStack)this.inventoryContents.set(i, (Object)ItemStack.f_41583_);
    }

    public boolean m_7013_(int i, ItemStack itemstack) {
        return true;
    }

    public void m_6596_() {
        this.con.m_6199_(this);
    }

    public void m_5856_(Player player) {
    }

    public void m_5785_(Player player) {
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

