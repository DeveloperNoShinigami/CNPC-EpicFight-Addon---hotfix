/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.EmptyBlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package noppes.npcs.schematics;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import noppes.npcs.schematics.BlueprintUtil;
import noppes.npcs.schematics.ISchematic;

public class Blueprint
implements ISchematic {
    private List<String> requiredMods;
    private short sizeX;
    private short sizeY;
    private short sizeZ;
    private short palleteSize;
    private BlockState[] pallete;
    private String name;
    private String[] architects;
    private short[][][] structure;
    private CompoundTag[] tileEntities;

    public Blueprint(short sizeX, short sizeY, short sizeZ, short palleteSize, BlockState[] pallete, short[][][] structure, CompoundTag[] tileEntities, List<String> requiredMods) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.palleteSize = palleteSize;
        this.pallete = pallete;
        this.structure = structure;
        this.tileEntities = tileEntities;
        this.requiredMods = requiredMods;
    }

    public void build(Level level, BlockPos pos) {
        BlockState state;
        short x;
        short z;
        short y;
        BlockState[] pallete = this.getPallete();
        short[][][] structure = this.getStructure();
        for (y = 0; y < this.getSizeY(); y = (short)(y + 1)) {
            for (z = 0; z < this.getSizeZ(); z = (short)(z + 1)) {
                for (x = 0; x < this.getSizeX(); x = (short)(x + 1)) {
                    state = pallete[structure[y][z][x] & 0xFFFF];
                    if (state.m_60734_() == Blocks.f_50454_ || !state.m_60838_((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.f_121853_)) continue;
                    level.m_7731_(pos.m_7918_((int)x, (int)y, (int)z), state, 2);
                }
            }
        }
        for (y = 0; y < this.getSizeY(); y = (short)(y + 1)) {
            for (z = 0; z < this.getSizeZ(); z = (short)(z + 1)) {
                for (x = 0; x < this.getSizeX(); x = (short)(x + 1)) {
                    state = pallete[structure[y][z][x]];
                    if (state.m_60734_() == Blocks.f_50454_ || state.m_60838_((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.f_121853_)) continue;
                    level.m_7731_(pos.m_7918_((int)x, (int)y, (int)z), state, 2);
                }
            }
        }
        if (this.getTileEntities() != null) {
            for (CompoundTag tag : this.getTileEntities()) {
                BlockEntity te = level.m_7702_(pos.m_7918_((int)tag.m_128448_("x"), (int)tag.m_128448_("y"), (int)tag.m_128448_("z")));
                tag.m_128405_("x", pos.m_123341_() + tag.m_128448_("x"));
                tag.m_128405_("y", pos.m_123342_() + tag.m_128448_("y"));
                tag.m_128405_("z", pos.m_123343_() + tag.m_128448_("z"));
                te.deserializeNBT(tag);
            }
        }
    }

    public short getSizeX() {
        return this.sizeX;
    }

    public short getSizeY() {
        return this.sizeY;
    }

    public short getSizeZ() {
        return this.sizeZ;
    }

    public short getPalleteSize() {
        return this.palleteSize;
    }

    public BlockState[] getPallete() {
        return this.pallete;
    }

    public short[][][] getStructure() {
        return this.structure;
    }

    public CompoundTag[] getTileEntities() {
        return this.tileEntities;
    }

    public List<String> getRequiredMods() {
        return this.requiredMods;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getArchitects() {
        return this.architects;
    }

    public void setArchitects(String[] architects) {
        this.architects = architects;
    }

    @Override
    public short getWidth() {
        return this.getSizeX();
    }

    @Override
    public short getHeight() {
        return this.getSizeZ();
    }

    @Override
    public short getLength() {
        return this.getSizeY();
    }

    @Override
    public int getBlockEntityDimensions() {
        return this.tileEntities.length;
    }

    @Override
    public CompoundTag getBlockEntity(int i) {
        return this.tileEntities[i];
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return this.pallete[this.structure[y][z][x]];
    }

    @Override
    public BlockState getBlockState(int i) {
        int x = i % this.getWidth();
        int z = (i - x) / this.getWidth() % this.getLength();
        int y = ((i - x) / this.getWidth() - z) / this.getLength();
        return this.getBlockState(x, y, z);
    }

    @Override
    public CompoundTag getNBT() {
        return BlueprintUtil.writeBlueprintToNBT(this);
    }
}

