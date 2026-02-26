/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestCategory;
import noppes.npcs.packets.PacketServerBasic;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketGuiUpdate;

public class SPacketQuestSave
extends PacketServerBasic {
    private int categoryId;
    private CompoundTag data;

    public SPacketQuestSave(int categoryId, CompoundTag data) {
        this.data = data;
        this.categoryId = categoryId;
    }

    public SPacketQuestSave(FriendlyByteBuf buf) {
        this.categoryId = buf.readInt();
        this.data = buf.m_130260_();
    }

    public static SPacketQuestSave decode(FriendlyByteBuf buf) {
        return new SPacketQuestSave(buf);
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.GLOBAL_QUEST;
    }

    @Override
    public void handle() {
        QuestCategory category = QuestController.instance.categories.get(this.categoryId);
        if (category == null) {
            return;
        }
        Quest quest = new Quest(category);
        quest.readNBT(this.data);
        QuestController.instance.saveQuest(category, quest);
        Packets.send(this.player, new PacketGuiUpdate());
    }

    public static void encode(SPacketQuestSave msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.categoryId);
        buf.m_130079_(msg.data);
    }
}

