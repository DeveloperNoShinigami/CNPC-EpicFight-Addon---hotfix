/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.util.ITeleporter
 */
package noppes.npcs.packets.server;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomTeleporter;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketDimensionTeleport
extends PacketServerBasic {
    private ResourceLocation id;

    public SPacketDimensionTeleport(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.teleporter;
    }

    public static void encode(SPacketDimensionTeleport msg, FriendlyByteBuf buf) {
        buf.m_130085_(msg.id);
    }

    public static SPacketDimensionTeleport decode(FriendlyByteBuf buf) {
        return new SPacketDimensionTeleport(buf.m_130281_());
    }

    @Override
    protected void handle() {
        ResourceKey dimension = ResourceKey.m_135785_((ResourceKey)Registries.f_256858_, (ResourceLocation)this.id);
        ServerLevel level = this.player.m_20194_().m_129880_(dimension);
        BlockPos coords = level.m_220360_();
        if (coords == null) {
            coords = level.m_220360_();
            if (!level.m_46859_(coords)) {
                coords = level.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, coords);
            } else {
                while (level.m_46859_(coords) && coords.m_123342_() > 0) {
                    coords = coords.m_7495_();
                }
                if (coords.m_123342_() == 0) {
                    coords = level.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, coords);
                }
            }
        }
        SPacketDimensionTeleport.teleportPlayer(this.player, coords.m_123341_(), coords.m_123342_(), coords.m_123343_(), (ResourceKey<Level>)dimension);
    }

    public static void teleportPlayer(ServerPlayer player, double x, double y, double z, ResourceKey<Level> dimension) {
        if (player.m_9236_().m_46472_() != dimension) {
            MinecraftServer server = player.m_20194_();
            ServerLevel wor = server.m_129880_(dimension);
            if (wor == null) {
                player.m_213846_((Component)Component.m_237113_((String)"Broken transporter. Dimenion does not exist"));
                return;
            }
            player.m_7678_(x, y, z, player.m_146908_(), player.m_146909_());
            player.changeDimension(wor, (ITeleporter)new CustomTeleporter(wor, new Vec3(x, y, z), player.m_146908_(), player.m_146909_()));
        } else {
            player.f_8906_.m_9774_(x, y, z, player.m_146908_(), player.m_146909_());
        }
    }
}

