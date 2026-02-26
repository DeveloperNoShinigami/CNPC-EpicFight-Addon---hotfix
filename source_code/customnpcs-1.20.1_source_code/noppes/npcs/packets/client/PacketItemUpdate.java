/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.packets.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.shared.common.PacketBasic;

public class PacketItemUpdate
extends PacketBasic {
    private final int id;
    private CompoundTag data;

    public PacketItemUpdate(int id, CompoundTag data) {
        this.id = id;
        this.data = data;
    }

    public static void encode(PacketItemUpdate msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.id);
        buf.m_130079_(msg.data);
    }

    public static PacketItemUpdate decode(FriendlyByteBuf buf) {
        return new PacketItemUpdate(buf.readInt(), buf.m_130260_());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void handle() {
        ItemStack stack = this.player.m_150109_().m_8020_(this.id);
        if (!stack.m_41619_()) {
            ((ItemStackWrapper)NpcAPI.Instance().getIItemStack(stack)).setMCNbt(this.data);
        }
    }
}

