/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtIo
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import noppes.npcs.CustomNpcs;
import noppes.npcs.controllers.data.Bank;

public class BankController {
    public HashMap<Integer, Bank> banks;
    private String filePath = "";
    private static BankController instance;

    public BankController() {
        instance = this;
        this.banks = new HashMap();
        this.loadBanks();
        if (this.banks.isEmpty()) {
            Bank bank = new Bank();
            bank.id = 0;
            bank.name = "Default Bank";
            for (int i = 0; i < 6; ++i) {
                bank.slotTypes.put(i, 0);
            }
            this.banks.put(bank.id, bank);
        }
    }

    public static BankController getInstance() {
        if (BankController.newInstance()) {
            instance = new BankController();
        }
        return instance;
    }

    private static boolean newInstance() {
        if (instance == null) {
            return true;
        }
        File file = CustomNpcs.getLevelSaveDirectory();
        if (file == null) {
            return false;
        }
        return !BankController.instance.filePath.equals(file.getAbsolutePath());
    }

    private void loadBanks() {
        File saveDir = CustomNpcs.getLevelSaveDirectory();
        if (saveDir == null) {
            return;
        }
        this.filePath = saveDir.getAbsolutePath();
        try {
            File file = new File(saveDir, "bank.dat");
            if (file.exists()) {
                this.loadBanks(file);
            }
        }
        catch (Exception e) {
            try {
                File file = new File(saveDir, "bank.dat_old");
                if (file.exists()) {
                    this.loadBanks(file);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void loadBanks(File file) throws IOException {
        this.loadBanks(NbtIo.m_128939_((InputStream)new FileInputStream(file)));
    }

    public void loadBanks(CompoundTag nbttagcompound1) throws IOException {
        HashMap<Integer, Bank> banks = new HashMap<Integer, Bank>();
        ListTag list = nbttagcompound1.m_128437_("Data", 10);
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                CompoundTag nbttagcompound = list.m_128728_(i);
                Bank bank = new Bank();
                bank.readAdditionalSaveData(nbttagcompound);
                banks.put(bank.id, bank);
            }
        }
        this.banks = banks;
    }

    public CompoundTag getNBT() {
        ListTag list = new ListTag();
        for (Bank bank : this.banks.values()) {
            CompoundTag nbtfactions = new CompoundTag();
            bank.addAdditionalSaveData(nbtfactions);
            list.add((Object)nbtfactions);
        }
        CompoundTag nbttagcompound = new CompoundTag();
        nbttagcompound.m_128365_("Data", (Tag)list);
        return nbttagcompound;
    }

    public Bank getBank(int bankId) {
        Bank bank = this.banks.get(bankId);
        if (bank != null) {
            return bank;
        }
        return this.banks.values().iterator().next();
    }

    public void saveBanks() {
        try {
            File saveDir = CustomNpcs.getLevelSaveDirectory();
            File file = new File(saveDir, "bank.dat_new");
            File file1 = new File(saveDir, "bank.dat_old");
            File file2 = new File(saveDir, "bank.dat");
            NbtIo.m_128947_((CompoundTag)this.getNBT(), (OutputStream)new FileOutputStream(file));
            if (file1.exists()) {
                file1.delete();
            }
            file2.renameTo(file1);
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
            if (file.exists()) {
                file.delete();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBank(Bank bank) {
        if (bank.id < 0) {
            bank.id = this.getUnusedId();
        }
        this.banks.put(bank.id, bank);
        this.saveBanks();
    }

    public int getUnusedId() {
        int id = 0;
        while (this.banks.containsKey(id)) {
            ++id;
        }
        return id;
    }

    public void removeBank(int bank) {
        if (bank < 0 || this.banks.size() <= 1) {
            return;
        }
        this.banks.remove(bank);
        this.saveBanks();
    }
}

