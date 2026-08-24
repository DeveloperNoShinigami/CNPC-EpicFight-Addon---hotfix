package com.goodbird.cnpcefaddon.common.network;

import com.goodbird.cnpcefaddon.common.NpcPatchReloadListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/** Server-authoritative per-entity CNPC patch selection. */
public final class SPNpcPatchSelection {
    private final int entityId;
    private final String key;
    private final boolean active;

    public SPNpcPatchSelection(int entityId, ResourceLocation key, boolean active) {
        this.entityId = entityId;
        this.key = key == null ? "" : key.toString();
        this.active = active;
    }

    public static SPNpcPatchSelection fromBytes(FriendlyByteBuf buf) {
        return new SPNpcPatchSelection(buf.readInt(),
                ResourceLocation.tryParse(buf.readUtf(256)), buf.readBoolean());
    }

    public static void toBytes(SPNpcPatchSelection message, FriendlyByteBuf buf) {
        buf.writeInt(message.entityId);
        buf.writeUtf(message.key, 256);
        buf.writeBoolean(message.active);
    }

    public static void handle(SPNpcPatchSelection message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> NpcPatchReloadListener.applyClientSelection(
                message.entityId, message.active ? ResourceLocation.tryParse(message.key) : null));
        context.get().setPacketHandled(true);
    }
}
