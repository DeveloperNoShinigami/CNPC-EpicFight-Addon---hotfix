/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.controllers.data.BankData;

public class PlayerBankData {
    public HashMap<Integer, BankData> banks = new HashMap();

    public void loadNBTData(CompoundTag compound) {
        HashMap<Integer, BankData> banks = new HashMap<Integer, BankData>();
        ListTag list = compound.m_128437_("BankData", 10);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag nbttagcompound = list.m_128728_(i);
            BankData data = new BankData();
            data.readNBT(nbttagcompound);
            banks.put(data.bankId, data);
        }
        this.banks = banks;
    }

    public void saveNBTData(CompoundTag playerData) {
        ListTag list = new ListTag();
        for (BankData data : this.banks.values()) {
            CompoundTag nbttagcompound = new CompoundTag();
            data.writeNBT(nbttagcompound);
            list.add((Object)nbttagcompound);
        }
        playerData.m_128365_("BankData", (Tag)list);
    }

    public BankData getBank(int bankId) {
        return this.banks.get(bankId);
    }

    public BankData getBankOrDefault(int bankId) {
        BankData data = this.banks.get(bankId);
        if (data != null) {
            return data;
        }
        Bank bank = BankController.getInstance().getBank(bankId);
        return this.banks.get(bank.id);
    }

    public boolean hasBank(int bank) {
        return this.banks.containsKey(bank);
    }

    public void loadNew(int bank) {
        BankData data = new BankData();
        data.bankId = bank;
        this.banks.put(bank, data);
    }
}

