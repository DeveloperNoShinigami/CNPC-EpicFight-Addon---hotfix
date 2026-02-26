/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.fluids.IFluidBlock
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 */
package noppes.npcs.api.wrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import noppes.npcs.api.block.IBlockFluidContainer;
import noppes.npcs.api.wrapper.BlockWrapper;

public class BlockFluidContainerWrapper
extends BlockWrapper
implements IBlockFluidContainer {
    private IFluidBlock block;

    public BlockFluidContainerWrapper(Level level, Block block, BlockPos pos) {
        super(level, block, pos);
        this.block = (IFluidBlock)block;
    }

    @Override
    public float getFluidPercentage() {
        return this.block.getFilledPercentage((Level)this.level.getMCLevel(), this.pos);
    }

    @Override
    public float getFuildDensity() {
        return this.block.getFluid().getFluidType().getDensity(this.level.getMCLevel().m_6425_(this.pos), (BlockAndTintGetter)this.level.getMCLevel(), this.pos);
    }

    @Override
    public float getFuildTemperature() {
        return this.block.getFluid().getFluidType().getTemperature(this.level.getMCLevel().m_6425_(this.pos), (BlockAndTintGetter)this.level.getMCLevel(), this.pos);
    }

    @Override
    public String getFluidName() {
        return ((IForgeRegistry)ForgeRegistries.FLUID_TYPES.get()).getKey((Object)this.block.getFluid().getFluidType()).toString();
    }
}

