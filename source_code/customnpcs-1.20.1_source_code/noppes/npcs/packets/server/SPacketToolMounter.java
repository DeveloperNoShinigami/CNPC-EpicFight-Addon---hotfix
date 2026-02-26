/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketToolMounter
extends PacketServerBasic {
    private int type;
    private String name = "";
    private int tab = -1;
    private CompoundTag compound = new CompoundTag();

    private SPacketToolMounter(int type, String name, int tab, CompoundTag compound) {
        this.type = type;
        this.name = name;
        this.tab = tab;
        this.compound = compound;
    }

    public SPacketToolMounter(int type, String name, int tab) {
        this.type = type;
        this.name = name;
        this.tab = tab;
    }

    public SPacketToolMounter(int type, CompoundTag compound) {
        this.type = type;
        this.compound = compound;
    }

    public SPacketToolMounter() {
        this.type = 3;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.mount;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        return CustomNpcsPermissions.TOOL_MOUNTER;
    }

    public static void encode(SPacketToolMounter msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.type);
        buf.m_130070_(msg.name);
        buf.writeInt(msg.tab);
        buf.m_130079_(msg.compound);
    }

    public static SPacketToolMounter decode(FriendlyByteBuf buf) {
        return new SPacketToolMounter(buf.readInt(), buf.m_130136_(Short.MAX_VALUE), buf.readInt(), buf.m_130260_());
    }

    @Override
    protected void handle() {
        PlayerData data = PlayerData.get((Player)this.player);
        if (data.mounted == null) {
            return;
        }
        if (this.type == 0) {
            Entity entity = (Entity)EntityType.m_20642_((CompoundTag)this.compound, (Level)this.player.m_9236_()).get();
            entity.m_6034_(data.mounted.m_20185_(), data.mounted.m_20186_(), data.mounted.m_20189_());
            this.player.m_9236_().m_7967_(entity);
            entity.m_7998_(data.mounted, true);
        } else if (this.type == 1) {
            Entity entity = (Entity)EntityType.m_20642_((CompoundTag)ServerCloneController.Instance.getCloneData(this.player.m_20203_(), this.name, this.tab), (Level)this.player.m_9236_()).get();
            entity.m_6034_(data.mounted.m_20185_(), data.mounted.m_20186_(), data.mounted.m_20189_());
            this.player.m_9236_().m_7967_(entity);
            entity.m_7998_(data.mounted, true);
        } else if (this.type == 2) {
            ResourceLocation loc = EntityUtil.getAllEntities(this.player.m_9236_(), false).get(this.name);
            EntityType type = (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(loc);
            Entity entity = type.m_20615_(this.player.m_9236_());
            if (entity == null) {
                return;
            }
            entity.m_6034_(data.mounted.m_20185_(), data.mounted.m_20186_(), data.mounted.m_20189_());
            this.player.m_9236_().m_7967_(entity);
            entity.m_7998_(data.mounted, true);
        } else {
            this.player.m_7998_(data.mounted, true);
        }
    }
}

