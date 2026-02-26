/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.player.GuiQuestCompletion;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketQuestCompletionCheck;
import noppes.npcs.shared.common.PacketBasic;

public class PacketQuestCompletion
extends PacketBasic {
    private final int id;

    public PacketQuestCompletion(int id) {
        this.id = id;
    }

    public static void encode(PacketQuestCompletion msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
    }

    public static PacketQuestCompletion decode(FriendlyByteBuf buf) {
        return new PacketQuestCompletion(buf.readInt());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        Quest quest = QuestController.instance.get(this.id);
        if (!quest.getCompleteText().isEmpty()) {
            NoppesUtil.openGUI(this.player, new GuiQuestCompletion(quest));
        } else {
            Packets.sendServer(new SPacketQuestCompletionCheck(this.id));
        }
    }
}

