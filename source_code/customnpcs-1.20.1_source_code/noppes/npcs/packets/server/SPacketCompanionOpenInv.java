/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.packets.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketCompanionOpenInv
extends PacketServerBasic {
    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    public static void encode(SPacketCompanionOpenInv msg, FriendlyByteBuf buf) {
    }

    public static SPacketCompanionOpenInv decode(FriendlyByteBuf buf) {
        return new SPacketCompanionOpenInv();
    }

    @Override
    protected void handle() {
        if (this.npc.role.getType() != 6 || this.player != this.npc.getOwner()) {
            return;
        }
        NoppesUtilServer.sendOpenGui((Player)this.player, EnumGuiType.CompanionInv, this.npc);
    }
}

