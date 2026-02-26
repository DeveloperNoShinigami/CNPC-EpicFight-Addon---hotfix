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
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketQuestCompletionCheckAll
extends PacketServerBasic {
    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketQuestCompletionCheckAll msg, FriendlyByteBuf buf) {
    }

    public static SPacketQuestCompletionCheckAll decode(FriendlyByteBuf buf) {
        return new SPacketQuestCompletionCheckAll();
    }

    @Override
    protected void handle() {
        PlayerQuestData playerdata = PlayerData.get((Player)this.player).questData;
        playerdata.checkQuestCompletion((Player)this.player, -1);
    }
}

