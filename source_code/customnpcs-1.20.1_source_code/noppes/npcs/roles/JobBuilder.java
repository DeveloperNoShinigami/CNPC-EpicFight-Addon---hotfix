/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.roles;

import java.util.Stack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.data.role.IJobBuilder;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.blocks.tiles.TileBuilder;
import noppes.npcs.controllers.data.BlockData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobBuilder
extends JobInterface
implements IJobBuilder {
    public TileBuilder build = null;
    private BlockPos possibleBuildPos = null;
    private Stack<BlockData> placingList = null;
    private BlockData placing = null;
    private int tryTicks = 0;
    private int ticks = 0;

    public JobBuilder(EntityNPCInterface npc) {
        super(npc);
        this.overrideMainHand = true;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        if (this.build != null) {
            compound.m_128405_("BuildX", this.build.m_58899_().m_123341_());
            compound.m_128405_("BuildY", this.build.m_58899_().m_123342_());
            compound.m_128405_("BuildZ", this.build.m_58899_().m_123343_());
            if (this.placingList != null && !this.placingList.isEmpty()) {
                ListTag list = new ListTag();
                for (BlockData data : this.placingList) {
                    list.add((Object)data.getNBT());
                }
                if (this.placing != null) {
                    list.add((Object)this.placing.getNBT());
                }
                compound.m_128365_("Placing", (Tag)list);
            }
        }
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.m_128441_("BuildX")) {
            this.possibleBuildPos = new BlockPos(compound.m_128451_("BuildX"), compound.m_128451_("BuildY"), compound.m_128451_("BuildZ"));
        }
        if (this.possibleBuildPos != null && compound.m_128441_("Placing")) {
            Stack<BlockData> placing = new Stack<BlockData>();
            ListTag list = compound.m_128437_("Placing", 10);
            for (int i = 0; i < list.size(); ++i) {
                BlockData data = BlockData.getData(list.m_128728_(i));
                if (data == null) continue;
                placing.add(data);
            }
            this.placingList = placing;
        }
        this.npc.ais.doorInteract = 1;
    }

    @Override
    public IItemStack getMainhand() {
        String name = this.npc.getJobData();
        ItemStack item = this.stringToItem(name);
        if (item.m_41619_()) {
            return this.npc.inventory.weapons.get(0);
        }
        return NpcAPI.Instance().getIItemStack(item);
    }

    @Override
    public boolean aiShouldExecute() {
        if (this.possibleBuildPos != null) {
            BlockEntity tile = this.npc.m_9236_().m_7702_(this.possibleBuildPos);
            if (tile instanceof TileBuilder) {
                this.build = (TileBuilder)tile;
            } else {
                this.placingList.clear();
            }
            this.possibleBuildPos = null;
        }
        return this.build != null;
    }

    @Override
    public void aiUpdateTask() {
        if (this.build.finished && this.placingList == null || !this.build.enabled || this.build.m_58901_()) {
            this.build = null;
            this.npc.m_21573_().m_26519_((double)this.npc.getStartXPos(), this.npc.getStartYPos(), (double)this.npc.getStartZPos(), 1.0);
            return;
        }
        if (this.ticks++ < 10) {
            return;
        }
        this.ticks = 0;
        if ((this.placingList == null || this.placingList.isEmpty()) && this.placing == null) {
            this.placingList = this.build.getBlock();
            this.npc.setJobData("");
            return;
        }
        if (this.placing == null) {
            this.placing = this.placingList.pop();
            if (this.placing.state.m_60734_() == Blocks.f_50454_) {
                this.placing = null;
                return;
            }
            this.tryTicks = 0;
            this.npc.setJobData(this.blockToString(this.placing));
        }
        this.npc.m_21573_().m_26519_((double)this.placing.pos.m_123341_(), (double)(this.placing.pos.m_123342_() + 1), (double)this.placing.pos.m_123343_(), 1.0);
        if (this.tryTicks++ > 40 || this.npc.nearPosition(this.placing.pos)) {
            BlockPos blockPos = this.placing.pos;
            this.placeBlock();
            if (this.tryTicks > 40) {
                blockPos = NoppesUtilServer.GetClosePos(blockPos, this.npc.m_9236_());
                this.npc.m_6021_((double)blockPos.m_123341_() + 0.5, blockPos.m_123342_(), (double)blockPos.m_123343_() + 0.5);
            }
        }
    }

    private String blockToString(BlockData data) {
        if (data.state.m_60734_() == Blocks.f_50016_) {
            return ForgeRegistries.ITEMS.getKey((Object)Items.f_42385_).toString();
        }
        return this.itemToString(data.getStack());
    }

    @Override
    public void stop() {
        this.reset();
    }

    @Override
    public void reset() {
        this.build = null;
        this.npc.setJobData("");
    }

    public void placeBlock() {
        BlockEntity tile;
        if (this.placing == null) {
            return;
        }
        this.npc.m_21573_().m_26573_();
        this.npc.m_6674_(InteractionHand.MAIN_HAND);
        this.npc.m_9236_().m_7731_(this.placing.pos, this.placing.state, 2);
        if (this.placing.state.m_60734_() instanceof EntityBlock && this.placing.tile != null && (tile = this.npc.m_9236_().m_7702_(this.placing.pos)) != null) {
            try {
                tile.m_142466_(this.placing.tile);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.placing = null;
    }

    @Override
    public boolean isBuilding() {
        return this.build != null && this.build.enabled && !this.build.finished && this.build.started;
    }

    @Override
    public int getType() {
        return 10;
    }
}

