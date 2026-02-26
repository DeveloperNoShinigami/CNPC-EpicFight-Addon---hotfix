/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import java.util.HashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.EventHooks;
import noppes.npcs.api.event.RoleEvent;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.shared.client.util.NoppesStringUtils;

public class SPacketFollowerHire
extends PacketServerBasic {
    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    public static void encode(SPacketFollowerHire msg, FriendlyByteBuf buf) {
    }

    public static SPacketFollowerHire decode(FriendlyByteBuf buf) {
        return new SPacketFollowerHire();
    }

    @Override
    protected void handle() {
        if (this.npc.role.getType() != 2) {
            return;
        }
        if (this.npc.role.getType() != 2) {
            return;
        }
        AbstractContainerMenu con = this.player.f_36096_;
        if (con == null || !(con instanceof ContainerNPCFollowerHire)) {
            return;
        }
        ContainerNPCFollowerHire container = (ContainerNPCFollowerHire)con;
        RoleFollower role = (RoleFollower)this.npc.role;
        SPacketFollowerHire.followerBuy(role, (Container)container.currencyMatrix, this.player, this.npc);
    }

    public static void followerBuy(RoleFollower role, Container currencyInv, ServerPlayer player, EntityNPCInterface npc) {
        ItemStack currency = currencyInv.m_8020_(0);
        if (currency == null || currency.m_41619_()) {
            return;
        }
        HashMap<ItemStack, Integer> cd = new HashMap<ItemStack, Integer>();
        for (int slot = 0; slot < role.inventory.items.size(); ++slot) {
            ItemStack is = (ItemStack)role.inventory.items.get(slot);
            if (is.m_41619_() || is.m_41720_() != currency.m_41720_()) continue;
            int days = 1;
            if (role.rates.containsKey(slot)) {
                days = role.rates.get(slot);
            }
            cd.put(is, days);
        }
        if (cd.size() == 0) {
            return;
        }
        int stackSize = currency.m_41613_();
        int days = 0;
        int possibleDays = 0;
        int possibleSize = stackSize;
        while (true) {
            for (ItemStack item : cd.keySet()) {
                int newStackSize;
                int size;
                int posDays;
                int rDays = (Integer)cd.get(item);
                int rValue = item.m_41613_();
                if (rValue > stackSize || possibleDays > (posDays = (size = stackSize - (newStackSize = stackSize % rValue)) / rValue * rDays)) continue;
                possibleDays = posDays;
                possibleSize = newStackSize;
            }
            if (stackSize == possibleSize) break;
            stackSize = possibleSize;
            days += possibleDays;
            possibleDays = 0;
        }
        RoleEvent.FollowerHireEvent event = new RoleEvent.FollowerHireEvent((Player)player, npc.wrappedNPC, days);
        if (EventHooks.onNPCRole(npc, event)) {
            return;
        }
        if (event.days == 0) {
            return;
        }
        if (stackSize <= 0) {
            currencyInv.m_6836_(0, ItemStack.f_41583_);
        } else {
            currencyInv.m_6836_(0, currency.m_41620_(stackSize));
        }
        npc.say((Player)player, new Line(NoppesStringUtils.formatText(role.dialogHire.replace("{days}", "" + days), new Object[]{player, npc})));
        role.setOwner((Player)player);
        role.addDays(days);
    }
}

