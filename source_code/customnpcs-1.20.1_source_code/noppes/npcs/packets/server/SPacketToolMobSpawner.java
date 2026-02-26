/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BaseSpawner
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.SpawnData
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.SpawnerBlockEntity
 *  net.minecraftforge.server.permission.nodes.PermissionNode
 */
package noppes.npcs.packets.server;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NBTTags;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.BaseSpawnerMixin;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketToolMobSpawner
extends PacketServerBasic {
    private boolean createSpawner;
    private boolean server;
    private BlockPos pos;
    private String name = "";
    private int tab = -1;
    private CompoundTag clone = new CompoundTag();

    public SPacketToolMobSpawner(boolean createSpawner, BlockPos pos, String name, int tab) {
        this.server = true;
        this.createSpawner = createSpawner;
        this.pos = pos;
        this.name = name;
        this.tab = tab;
    }

    public SPacketToolMobSpawner(boolean createSpawner, BlockPos pos, CompoundTag clone) {
        this.server = false;
        this.createSpawner = createSpawner;
        this.pos = pos;
        this.clone = clone;
    }

    public SPacketToolMobSpawner(boolean createSpawner, boolean server, BlockPos pos, String name, int tab, CompoundTag clone) {
        this.createSpawner = createSpawner;
        this.server = server;
        this.pos = pos;
        this.name = name;
        this.tab = tab;
        this.clone = clone;
    }

    public SPacketToolMobSpawner(FriendlyByteBuf buf) {
        this.createSpawner = buf.readBoolean();
        this.server = buf.readBoolean();
        this.pos = buf.m_130135_();
        this.name = buf.m_130136_(Short.MAX_VALUE);
        this.tab = buf.readInt();
        this.clone = buf.m_130260_();
    }

    public static SPacketToolMobSpawner decode(FriendlyByteBuf buf) {
        return new SPacketToolMobSpawner(buf);
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return item.m_41720_() == CustomItems.cloner;
    }

    @Override
    public PermissionNode<Boolean> getPermission() {
        if (this.createSpawner) {
            return CustomNpcsPermissions.SPAWNER_CREATE;
        }
        return CustomNpcsPermissions.SPAWNER_MOB;
    }

    @Override
    public void handle() {
        if (this.server) {
            this.clone = ServerCloneController.Instance.getCloneData(this.player.m_20203_(), this.name, this.tab);
        }
        if (this.clone == null || this.clone.m_128456_()) {
            return;
        }
        if (this.createSpawner) {
            SPacketToolMobSpawner.createMobSpawner(this.pos, this.clone, (Player)this.player);
        } else {
            Entity entity = SPacketToolMobSpawner.spawnClone(this.clone, (double)this.pos.m_123341_() + 0.5, this.pos.m_123342_() + 1, (double)this.pos.m_123343_() + 0.5, this.player.m_9236_());
            if (entity == null) {
                this.player.m_213846_((Component)Component.m_237113_((String)"Failed to create an entity out of your clone"));
            }
        }
    }

    public static Entity spawnClone(CompoundTag compound, double x, double y, double z, Level world) {
        ServerCloneController.Instance.cleanTags(compound);
        compound.m_128365_("Pos", (Tag)NBTTags.nbtDoubleList(x, y, z));
        Entity entity = (Entity)EntityType.m_20642_((CompoundTag)compound, (Level)world).get();
        if (entity == null) {
            return null;
        }
        if (entity instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            npc.ais.setStartPos(npc.m_20183_());
        }
        world.m_7967_(entity);
        return entity;
    }

    public static void createMobSpawner(BlockPos pos, CompoundTag comp, Player player) {
        ServerCloneController.Instance.cleanTags(comp);
        if (comp.m_128461_("id").equalsIgnoreCase("entityhorse")) {
            player.m_213846_((Component)Component.m_237113_((String)"Currently you cant create horse spawner, its a minecraft bug"));
            return;
        }
        player.m_9236_().m_46597_(pos, Blocks.f_50085_.m_49966_());
        SpawnerBlockEntity tile = (SpawnerBlockEntity)player.m_9236_().m_7702_(pos);
        BaseSpawner logic = tile.m_59801_();
        if (!comp.m_128425_("id", 8)) {
            comp.m_128359_("id", "Pig");
        }
        comp.m_128385_("StartPosNew", new int[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()});
        ((BaseSpawnerMixin)logic).callSetNextSpawnData(player.m_9236_(), pos, new SpawnData(comp, Optional.empty()));
    }

    public static void encode(SPacketToolMobSpawner msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.createSpawner);
        buf.writeBoolean(msg.server);
        buf.m_130064_(msg.pos);
        buf.m_130070_(msg.name);
        buf.writeInt(msg.tab);
        buf.m_130079_(msg.clone);
    }
}

