/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.ChestBlock
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.StemBlock
 *  net.minecraft.world.level.block.StemGrownBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.storage.loot.BuiltInLootTables
 *  net.minecraft.world.level.storage.loot.LootParams$Builder
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParams
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.roles;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.data.role.IJobFarmer;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.controllers.MassBlockController;
import noppes.npcs.controllers.data.BlockData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobFarmer
extends JobInterface
implements MassBlockController.IMassBlock,
IJobFarmer {
    public int chestMode = 1;
    private List<BlockPos> trackedBlocks = new ArrayList<BlockPos>();
    private int ticks = 0;
    private int walkTicks = 0;
    private int blockTicks = 800;
    private boolean waitingForBlocks = false;
    private BlockPos ripe = null;
    private BlockPos chest = null;
    private ItemStack holding = ItemStack.f_41583_;

    public JobFarmer(EntityNPCInterface npc) {
        super(npc);
        this.overrideMainHand = true;
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
    public CompoundTag save(CompoundTag compound) {
        compound.m_128405_("JobChestMode", this.chestMode);
        if (!this.holding.m_41619_()) {
            compound.m_128365_("JobHolding", (Tag)this.holding.m_41739_(new CompoundTag()));
        }
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        this.chestMode = compound.m_128451_("JobChestMode");
        this.holding = ItemStack.m_41712_((CompoundTag)compound.m_128469_("JobHolding"));
        this.blockTicks = 1100;
    }

    public void setHolding(ItemStack item) {
        this.holding = item;
        this.npc.setJobData(this.itemToString(this.holding));
    }

    @Override
    public boolean aiShouldExecute() {
        if (!this.holding.m_41619_()) {
            if (this.chestMode == 0) {
                this.setHolding(ItemStack.f_41583_);
            } else if (this.chestMode == 1) {
                if (this.chest == null) {
                    this.dropItem(this.holding);
                    this.setHolding(ItemStack.f_41583_);
                } else {
                    this.chest();
                }
            } else if (this.chestMode == 2) {
                this.dropItem(this.holding);
                this.setHolding(ItemStack.f_41583_);
            }
            return false;
        }
        if (this.ripe != null) {
            this.pluck();
            return false;
        }
        if (!this.waitingForBlocks && this.blockTicks++ > 1200) {
            this.blockTicks = 0;
            this.waitingForBlocks = true;
            MassBlockController.Queue(this);
        }
        if (this.ticks++ < 100) {
            return false;
        }
        this.ticks = 0;
        return true;
    }

    private void dropItem(ItemStack item) {
        ItemEntity entityitem = new ItemEntity(this.npc.m_9236_(), this.npc.m_20185_(), this.npc.m_20186_(), this.npc.m_20189_(), item);
        entityitem.m_32060_();
        this.npc.m_9236_().m_7967_((Entity)entityitem);
    }

    private void chest() {
        BlockPos pos = this.chest;
        this.npc.m_21573_().m_26519_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 1.0);
        this.npc.m_21563_().m_24950_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 10.0f, (float)this.npc.m_8132_());
        if (this.npc.nearPosition(pos) || this.walkTicks++ > 400) {
            Container inventory;
            if (this.walkTicks < 400) {
                this.npc.m_6674_(InteractionHand.MAIN_HAND);
            }
            this.npc.m_21573_().m_26573_();
            this.ticks = 100;
            this.walkTicks = 0;
            BlockState state = this.npc.m_9236_().m_8055_(pos);
            BlockEntity tile = this.npc.m_9236_().m_7702_(pos);
            Container container = inventory = tile instanceof Container ? (Container)tile : null;
            if (state.m_60734_() instanceof ChestBlock) {
                inventory = ChestBlock.m_51511_((ChestBlock)((ChestBlock)state.m_60734_()), (BlockState)state, (Level)this.npc.m_9236_(), (BlockPos)pos, (boolean)true);
            }
            if (inventory != null) {
                int i;
                for (i = 0; !this.holding.m_41619_() && i < inventory.m_6643_(); ++i) {
                    this.holding = this.mergeStack(inventory, i, this.holding);
                }
                for (i = 0; !this.holding.m_41619_() && i < inventory.m_6643_(); ++i) {
                    ItemStack item = inventory.m_8020_(i);
                    if (!item.m_41619_()) continue;
                    inventory.m_6836_(i, this.holding);
                    this.holding = ItemStack.f_41583_;
                }
                if (!this.holding.m_41619_()) {
                    this.dropItem(this.holding);
                    this.holding = ItemStack.f_41583_;
                }
            } else {
                this.chest = null;
            }
            this.setHolding(this.holding);
        }
    }

    private ItemStack mergeStack(Container inventory, int slot, ItemStack item) {
        ItemStack item2 = inventory.m_8020_(slot);
        if (!NoppesUtilPlayer.compareItems(item, item2, false, false)) {
            return item;
        }
        int size = item2.m_41741_() - item2.m_41613_();
        if (size >= item.m_41613_()) {
            item2.m_41764_(item2.m_41613_() + item.m_41613_());
            return ItemStack.f_41583_;
        }
        item2.m_41764_(item2.m_41741_());
        item.m_41764_(item.m_41613_() - size);
        if (item.m_41619_()) {
            return ItemStack.f_41583_;
        }
        return item;
    }

    private void pluck() {
        BlockPos pos = this.ripe;
        this.npc.m_21573_().m_26519_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 1.0);
        this.npc.m_21563_().m_24950_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 10.0f, (float)this.npc.m_8132_());
        if (this.npc.nearPosition(pos) || this.walkTicks++ > 400) {
            if (this.walkTicks > 400) {
                pos = NoppesUtilServer.GetClosePos(pos, this.npc.m_9236_());
                this.npc.m_6021_((double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5);
            }
            this.ripe = null;
            this.npc.m_21573_().m_26573_();
            this.ticks = 90;
            this.walkTicks = 0;
            this.npc.m_6674_(InteractionHand.MAIN_HAND);
            BlockState state = this.npc.m_9236_().m_8055_(pos);
            Block b = state.m_60734_();
            if (b instanceof CropBlock && ((CropBlock)b).m_52307_(state)) {
                CropBlock crop = (CropBlock)b;
                Item item = crop.m_7397_((BlockGetter)this.npc.m_9236_(), pos, state).m_41720_();
                LootParams.Builder builder = new LootParams.Builder((ServerLevel)this.npc.m_9236_()).m_287286_(LootContextParams.f_81460_, (Object)Vec3.m_82512_((Vec3i)pos)).m_287286_(LootContextParams.f_81463_, (Object)this.npc.m_21205_()).m_287286_(LootContextParams.f_81461_, (Object)state).m_287289_(LootContextParams.f_81462_, (Object)this.npc.m_9236_().m_7702_(pos));
                LootTable loottable = this.npc.m_20194_().m_278653_().m_278676_(b.m_60589_());
                ObjectArrayList l = loottable.m_287195_(builder.m_287235_(LootContextParamSets.f_81421_));
                this.npc.m_9236_().m_7731_(pos, crop.m_52289_(0), 2);
                if (l.isEmpty()) {
                    this.holding = ItemStack.f_41583_;
                } else if (l.size() == 1) {
                    this.holding = (ItemStack)l.get(0);
                } else {
                    ObjectArrayList fl = l.stream().filter(t -> t.m_41720_() != item).collect(Collectors.toList());
                    if (fl.isEmpty()) {
                        fl = l;
                    }
                    this.holding = (ItemStack)fl.get(this.npc.m_217043_().m_188503_(fl.size()));
                }
                this.holding.m_41764_(1);
            }
            if (b instanceof StemGrownBlock) {
                b = this.npc.m_9236_().m_8055_(pos).m_60734_();
                this.npc.m_9236_().m_7471_(pos, false);
                this.holding = new ItemStack((ItemLike)b);
            }
            this.setHolding(this.holding);
        }
    }

    @Override
    public boolean aiContinueExecute() {
        return false;
    }

    @Override
    public void aiUpdateTask() {
        Iterator<BlockPos> ite = this.trackedBlocks.iterator();
        while (ite.hasNext() && this.ripe == null) {
            BlockPos pos = ite.next();
            BlockState state = this.npc.m_9236_().m_8055_(pos);
            Block b = state.m_60734_();
            if ((b instanceof CropBlock && ((CropBlock)b).m_52307_(state) || b instanceof StemGrownBlock) && b.m_60589_() != BuiltInLootTables.f_78712_) {
                this.ripe = pos;
                continue;
            }
            ite.remove();
        }
        boolean bl = this.npc.ais.returnToStart = this.ripe == null;
        if (this.ripe != null) {
            this.npc.m_21573_().m_26573_();
            this.npc.m_21563_().m_24950_((double)this.ripe.m_123341_(), (double)this.ripe.m_123342_(), (double)this.ripe.m_123343_(), 10.0f, (float)this.npc.m_8132_());
        }
    }

    @Override
    public boolean isPlucking() {
        return this.ripe != null || !this.holding.m_41619_();
    }

    @Override
    public EntityNPCInterface getNpc() {
        return this.npc;
    }

    @Override
    public int getRange() {
        return 16;
    }

    @Override
    public void processed(List<BlockData> list) {
        ArrayList<BlockPos> trackedBlocks = new ArrayList<BlockPos>();
        BlockPos chest = null;
        for (BlockData data : list) {
            BlockEntity tile = this.npc.m_9236_().m_7702_(data.pos);
            Block b = data.state.m_60734_();
            if (tile instanceof RandomizableContainerBlockEntity) {
                if (chest != null && !(this.npc.m_20275_(chest.m_123341_(), chest.m_123342_(), chest.m_123343_()) > this.npc.m_20275_(data.pos.m_123341_(), data.pos.m_123342_(), data.pos.m_123343_()))) continue;
                chest = data.pos;
                continue;
            }
            if (!(b instanceof CropBlock) && !(b instanceof StemBlock) || trackedBlocks.contains(data.pos)) continue;
            trackedBlocks.add(data.pos);
        }
        this.chest = chest;
        this.trackedBlocks = trackedBlocks;
        this.waitingForBlocks = false;
    }

    @Override
    public EnumSet<Goal.Flag> getFlags() {
        return EnumSet.of(Goal.Flag.MOVE);
    }

    @Override
    public int getType() {
        return 11;
    }
}

