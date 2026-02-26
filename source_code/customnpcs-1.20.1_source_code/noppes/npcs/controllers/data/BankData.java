/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.controllers.data;

import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.containers.ContainerNPCBankInterface;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiData;
import noppes.npcs.util.CustomNPCsScheduler;

public class BankData {
    public HashMap<Integer, NpcMiscInventory> itemSlots = new HashMap();
    public HashMap<Integer, Boolean> upgradedSlots = new HashMap();
    public int unlockedSlots = 0;
    public int bankId = -1;

    public BankData() {
        for (int i = 0; i < 6; ++i) {
            this.itemSlots.put(i, new NpcMiscInventory(54));
            this.upgradedSlots.put(i, false);
        }
    }

    public void readNBT(CompoundTag nbttagcompound) {
        this.bankId = nbttagcompound.m_128451_("DataBankId");
        this.unlockedSlots = nbttagcompound.m_128451_("UnlockedSlots");
        this.itemSlots = this.getItemSlots(nbttagcompound.m_128437_("BankInv", 10));
        this.upgradedSlots = NBTTags.getBooleanList(nbttagcompound.m_128437_("UpdatedSlots", 10));
    }

    private HashMap<Integer, NpcMiscInventory> getItemSlots(ListTag tagList) {
        HashMap<Integer, NpcMiscInventory> list = new HashMap<Integer, NpcMiscInventory>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            int slot = nbttagcompound.m_128451_("Slot");
            NpcMiscInventory inv = new NpcMiscInventory(54);
            inv.setFromNBT(nbttagcompound.m_128469_("BankItems"));
            list.put(slot, inv);
        }
        return list;
    }

    public void writeNBT(CompoundTag nbttagcompound) {
        nbttagcompound.m_128405_("DataBankId", this.bankId);
        nbttagcompound.m_128405_("UnlockedSlots", this.unlockedSlots);
        nbttagcompound.m_128365_("UpdatedSlots", (Tag)NBTTags.nbtBooleanList(this.upgradedSlots));
        nbttagcompound.m_128365_("BankInv", (Tag)this.nbtItemSlots(this.itemSlots));
    }

    private ListTag nbtItemSlots(HashMap<Integer, NpcMiscInventory> items) {
        ListTag list = new ListTag();
        for (int slot : items.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot);
            nbttagcompound.m_128365_("BankItems", (Tag)items.get(slot).getToNBT());
            list.add((Object)nbttagcompound);
        }
        return list;
    }

    public boolean isUpgraded(Bank bank, int slot) {
        if (bank.isUpgraded(slot)) {
            return true;
        }
        return bank.canBeUpgraded(slot) && this.upgradedSlots.get(slot) != false;
    }

    public void openBankGui(ServerPlayer player, EntityNPCInterface npc, int bankId, int slot) {
        Bank bank = BankController.getInstance().getBank(bankId);
        if (bank.getMaxSlots() <= slot) {
            return;
        }
        if (bank.startSlots > this.unlockedSlots) {
            this.unlockedSlots = bank.startSlots;
        }
        ItemStack currency = ItemStack.f_41583_;
        if (this.unlockedSlots <= slot) {
            currency = bank.currencyInventory.m_8020_(slot);
            NoppesUtilServer.openContainerGui(player, EnumGuiType.PlayerBankUnlock, buf -> {
                buf.writeInt(slot);
                buf.writeInt(bank.id);
            });
        } else if (this.isUpgraded(bank, slot)) {
            NoppesUtilServer.openContainerGui(player, EnumGuiType.PlayerBankLarge, buf -> {
                buf.writeInt(slot);
                buf.writeInt(bank.id);
            });
        } else if (bank.canBeUpgraded(slot)) {
            currency = bank.upgradeInventory.m_8020_(slot);
            NoppesUtilServer.openContainerGui(player, EnumGuiType.PlayerBankUprade, buf -> {
                buf.writeInt(slot);
                buf.writeInt(bank.id);
            });
        } else {
            NoppesUtilServer.openContainerGui(player, EnumGuiType.PlayerBankSmall, buf -> {
                buf.writeInt(slot);
                buf.writeInt(bank.id);
            });
        }
        ItemStack item = currency;
        CustomNPCsScheduler.runTack(() -> {
            CompoundTag compound = new CompoundTag();
            compound.m_128405_("MaxSlots", bank.getMaxSlots());
            compound.m_128405_("UnlockedSlots", this.unlockedSlots);
            if (item != null && !item.m_41619_()) {
                compound.m_128365_("Currency", (Tag)item.m_41739_(new CompoundTag()));
                ContainerNPCBankInterface container = this.getContainer((Player)player);
                if (container != null) {
                    container.setCurrency(item);
                }
            }
            Packets.send(player, new PacketGuiData(compound));
        }, 300);
    }

    private ContainerNPCBankInterface getContainer(Player player) {
        AbstractContainerMenu con = player.f_36096_;
        if (con == null || !(con instanceof ContainerNPCBankInterface)) {
            return null;
        }
        return (ContainerNPCBankInterface)con;
    }
}

