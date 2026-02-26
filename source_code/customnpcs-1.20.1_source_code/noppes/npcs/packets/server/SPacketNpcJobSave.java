/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketNpcJobSave
extends PacketServerBasic {
    private CompoundTag data;

    public SPacketNpcJobSave(CompoundTag data) {
        this.data = data;
    }

    @Override
    public boolean requiresNpc() {
        return true;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.NPC_ADVANCED;
    }

    public static void encode(SPacketNpcJobSave msg, FriendlyByteBuf buf) {
        buf.m_130079_(msg.data);
    }

    public static SPacketNpcJobSave decode(FriendlyByteBuf buf) {
        return new SPacketNpcJobSave(buf.m_130260_());
    }

    @Override
    protected void handle() {
        CompoundTag original = this.npc.job.save(new CompoundTag());
        Set names = this.data.m_128431_();
        for (String name : names) {
            original.m_128365_(name, this.data.m_128423_(name));
        }
        this.npc.job.load(original);
        this.npc.updateClient = true;
    }
}

