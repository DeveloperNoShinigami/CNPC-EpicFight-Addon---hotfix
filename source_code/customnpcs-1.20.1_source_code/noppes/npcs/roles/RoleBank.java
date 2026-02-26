/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.data.Bank;
import noppes.npcs.controllers.data.BankData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RoleBank
extends RoleInterface {
    public int bankId = -1;

    public RoleBank(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128405_("RoleBankID", this.bankId);
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.bankId = nbttagcompound.m_128451_("RoleBankID");
    }

    @Override
    public void interact(Player player) {
        NoppesUtilServer.setEditingNpc(player, this.npc);
        BankData data = PlayerDataController.instance.getBankData(player, this.bankId).getBankOrDefault(this.bankId);
        data.openBankGui((ServerPlayer)player, this.npc, this.bankId, 0);
        this.npc.say(player, this.npc.advanced.getInteractLine());
    }

    public Bank getBank() {
        Bank bank = BankController.getInstance().banks.get(this.bankId);
        if (bank != null) {
            return bank;
        }
        return BankController.getInstance().banks.values().iterator().next();
    }

    @Override
    public int getType() {
        return 3;
    }
}

