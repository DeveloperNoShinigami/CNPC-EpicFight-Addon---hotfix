/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.Nameable
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.util.FakePlayer
 *  net.minecraftforge.fluids.IFluidBlock
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.api.wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.IContainer;
import noppes.npcs.api.INbt;
import noppes.npcs.api.IPos;
import noppes.npcs.api.IWorld;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.block.IBlock;
import noppes.npcs.api.entity.data.IData;
import noppes.npcs.api.wrapper.BlockFluidContainerWrapper;
import noppes.npcs.api.wrapper.BlockPosWrapper;
import noppes.npcs.api.wrapper.BlockScriptedDoorWrapper;
import noppes.npcs.api.wrapper.BlockScriptedWrapper;
import noppes.npcs.blocks.BlockScripted;
import noppes.npcs.blocks.BlockScriptedDoor;
import noppes.npcs.blocks.tiles.TileNpcEntity;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.EntityIMixin;
import noppes.npcs.shared.common.util.LRUHashMap;

public class BlockWrapper
implements IBlock {
    private static final Map<String, BlockWrapper> blockCache = new LRUHashMap<String, BlockWrapper>(400);
    protected final IWorld level;
    protected final Block block;
    protected final BlockPos pos;
    protected final BlockPosWrapper bPos;
    protected BlockEntity tile;
    protected TileNpcEntity storage;
    private final IData tempdata = new IData(){

        @Override
        public void remove(String key) {
            if (BlockWrapper.this.storage == null) {
                return;
            }
            BlockWrapper.this.storage.tempData.remove(key);
        }

        @Override
        public void put(String key, Object value) {
            if (BlockWrapper.this.storage == null) {
                return;
            }
            BlockWrapper.this.storage.tempData.put(key, value);
        }

        @Override
        public boolean has(String key) {
            if (BlockWrapper.this.storage == null) {
                return false;
            }
            return BlockWrapper.this.storage.tempData.containsKey(key);
        }

        @Override
        public Object get(String key) {
            if (BlockWrapper.this.storage == null) {
                return null;
            }
            return BlockWrapper.this.storage.tempData.get(key);
        }

        @Override
        public void clear() {
            if (BlockWrapper.this.storage == null) {
                return;
            }
            BlockWrapper.this.storage.tempData.clear();
        }

        @Override
        public String[] getKeys() {
            return BlockWrapper.this.storage.tempData.keySet().toArray(new String[BlockWrapper.this.storage.tempData.size()]);
        }
    };
    private final IData storeddata = new IData(){

        @Override
        public void put(String key, Object value) {
            CompoundTag compound = this.getNBT();
            if (compound == null) {
                return;
            }
            if (value instanceof Number) {
                compound.m_128347_(key, ((Number)value).doubleValue());
            } else if (value instanceof String) {
                compound.m_128359_(key, (String)value);
            }
        }

        @Override
        public Object get(String key) {
            CompoundTag compound = this.getNBT();
            if (compound == null) {
                return null;
            }
            if (!compound.m_128441_(key)) {
                return null;
            }
            Tag base = compound.m_128423_(key);
            if (base instanceof NumericTag) {
                return ((NumericTag)base).m_7061_();
            }
            return base.m_7916_();
        }

        @Override
        public void remove(String key) {
            CompoundTag compound = this.getNBT();
            if (compound == null) {
                return;
            }
            compound.m_128473_(key);
        }

        @Override
        public boolean has(String key) {
            CompoundTag compound = this.getNBT();
            if (compound == null) {
                return false;
            }
            return compound.m_128441_(key);
        }

        @Override
        public void clear() {
            if (BlockWrapper.this.tile == null) {
                return;
            }
            BlockWrapper.this.tile.getPersistentData().m_128365_("CustomNPCsData", (Tag)new CompoundTag());
        }

        private CompoundTag getNBT() {
            if (BlockWrapper.this.tile == null) {
                return null;
            }
            CompoundTag compound = BlockWrapper.this.tile.getPersistentData().m_128469_("CustomNPCsData");
            if (compound.m_128456_() && !BlockWrapper.this.tile.getPersistentData().m_128441_("CustomNPCsData")) {
                BlockWrapper.this.tile.getPersistentData().m_128365_("CustomNPCsData", (Tag)compound);
            }
            return compound;
        }

        @Override
        public String[] getKeys() {
            CompoundTag compound = this.getNBT();
            if (compound == null) {
                return new String[0];
            }
            return compound.m_128431_().toArray(new String[compound.m_128431_().size()]);
        }
    };

    protected BlockWrapper(Level level, Block block, BlockPos pos) {
        this.level = NpcAPI.Instance().getIWorld((ServerLevel)level);
        this.block = block;
        this.pos = pos;
        this.bPos = new BlockPosWrapper(pos);
        this.setTile(level.m_7702_(pos));
    }

    @Override
    public int getX() {
        return this.pos.m_123341_();
    }

    @Override
    public int getY() {
        return this.pos.m_123342_();
    }

    @Override
    public int getZ() {
        return this.pos.m_123343_();
    }

    @Override
    public IPos getPos() {
        return this.bPos;
    }

    @Override
    public Object getProperty(String name) {
        BlockState state = this.getMCBlockState();
        for (Property p : state.m_61147_()) {
            if (!p.m_61708_().equalsIgnoreCase(name)) continue;
            return state.m_61143_(p);
        }
        throw new CustomNPCsException("Unknown property: " + name, new Object[0]);
    }

    @Override
    public void setProperty(String name, Object val) {
        if (!(val instanceof Comparable)) {
            throw new CustomNPCsException("Not a valid property value: " + val, new Object[0]);
        }
        BlockState state = this.getMCBlockState();
        for (Property p : state.m_61147_()) {
            if (!p.m_61708_().equalsIgnoreCase(name)) continue;
            this.setPropertyValue(state, p, (Comparable)val);
            return;
        }
        throw new CustomNPCsException("Unknown property: " + name, new Object[0]);
    }

    private <T extends Comparable<T>> void setPropertyValue(BlockState state, Property<T> p, Comparable<?> c) {
        this.level.getMCLevel().m_7731_(this.pos, (BlockState)state.m_61124_(p, (Comparable)p.m_61709_().cast(c)), 3);
    }

    @Override
    public String[] getProperties() {
        Collection props = this.getMCBlockState().m_61147_();
        ArrayList<String> list = new ArrayList<String>();
        for (Property prop : props) {
            list.add(prop.m_61708_());
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public void remove() {
        this.level.getMCLevel().m_7471_(this.pos, false);
    }

    @Override
    public boolean isRemoved() {
        BlockState state = this.level.getMCLevel().m_8055_(this.pos);
        if (state == null) {
            return true;
        }
        return state.m_60734_() != this.block;
    }

    @Override
    public boolean isAir() {
        return this.level.getMCLevel().m_8055_(this.pos).m_60795_();
    }

    @Override
    public BlockWrapper setBlock(String name) {
        Block block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
        if (block == null) {
            return this;
        }
        this.level.getMCLevel().m_7731_(this.pos, block.m_49966_(), 2);
        return new BlockWrapper((Level)this.level.getMCLevel(), block, this.pos);
    }

    @Override
    public BlockWrapper setBlock(IBlock block) {
        this.level.getMCLevel().m_7731_(this.pos, block.getMCBlock().m_49966_(), 2);
        return new BlockWrapper((Level)this.level.getMCLevel(), block.getMCBlock(), this.pos);
    }

    @Override
    public boolean isContainer() {
        if (this.tile == null || !(this.tile instanceof Container)) {
            return false;
        }
        return ((Container)this.tile).m_6643_() > 0;
    }

    @Override
    public IContainer getContainer() {
        if (!this.isContainer()) {
            throw new CustomNPCsException("This block is not a container", new Object[0]);
        }
        return NpcAPI.Instance().getIContainer((Container)this.tile);
    }

    @Override
    public IData getTempdata() {
        return this.tempdata;
    }

    @Override
    public IData getStoreddata() {
        return this.storeddata;
    }

    @Override
    public String getName() {
        return ForgeRegistries.BLOCKS.getKey((Object)this.block).toString();
    }

    @Override
    public String getDisplayName() {
        if (this.tile == null || !(this.tile instanceof Nameable)) {
            return this.getName();
        }
        return ((Nameable)this.tile).m_5446_().getString();
    }

    @Override
    public IWorld getWorld() {
        return this.level;
    }

    @Override
    public Block getMCBlock() {
        return this.block;
    }

    @Deprecated
    public static IBlock createNew(Level level, BlockPos pos, BlockState state) {
        Block block = state.m_60734_();
        String key = state.toString() + pos.toString();
        BlockWrapper b = blockCache.get(key);
        if (b != null) {
            b.setTile(level.m_7702_(pos));
            return b;
        }
        b = block instanceof BlockScripted ? new BlockScriptedWrapper(level, block, pos) : (block instanceof BlockScriptedDoor ? new BlockScriptedDoorWrapper(level, block, pos) : (block instanceof IFluidBlock ? new BlockFluidContainerWrapper(level, block, pos) : new BlockWrapper(level, block, pos)));
        blockCache.put(key, b);
        return b;
    }

    public static void clearCache() {
        blockCache.clear();
    }

    @Override
    public boolean hasTileEntity() {
        return this.tile != null;
    }

    protected void setTile(BlockEntity tile) {
        this.tile = tile;
        if (tile instanceof TileNpcEntity) {
            this.storage = (TileNpcEntity)tile;
        }
    }

    @Override
    public INbt getBlockEntityNBT() {
        CompoundTag compound = this.tile.m_187482_();
        return NpcAPI.Instance().getINbt(compound);
    }

    @Override
    public void setTileEntityNBT(INbt nbt) {
        this.tile.m_142466_(nbt.getMCNBT());
        this.tile.m_6596_();
        BlockState state = this.level.getMCLevel().m_8055_(this.pos);
        this.level.getMCLevel().m_7260_(this.pos, state, state, 3);
    }

    @Override
    public BlockEntity getMCTileEntity() {
        return this.tile;
    }

    @Override
    public BlockState getMCBlockState() {
        return this.level.getMCLevel().m_8055_(this.pos);
    }

    @Override
    public void blockEvent(int type, int data) {
        this.level.getMCLevel().m_7696_(this.pos, this.getMCBlock(), type, data);
    }

    @Override
    public void interact(int side) {
        FakePlayer player = EntityNPCInterface.GenericPlayer;
        ServerLevel w = this.level.getMCLevel();
        ((EntityIMixin)player).setLevel((Level)w);
        player.m_6034_((double)this.pos.m_123341_(), (double)this.pos.m_123342_(), (double)this.pos.m_123343_());
        this.getMCBlockState().m_60664_((Level)w, (Player)EntityNPCInterface.CommandPlayer, InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.f_82478_, Direction.m_122376_((int)side), this.pos, true));
    }
}

