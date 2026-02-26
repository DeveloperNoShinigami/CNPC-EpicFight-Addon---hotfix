/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 */
package noppes.npcs.api.wrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import noppes.npcs.api.IPos;

public class BlockPosWrapper
implements IPos {
    public static final BlockPosWrapper ZERO = new BlockPosWrapper(BlockPos.f_121853_);
    private final BlockPos blockPos;

    public BlockPosWrapper(BlockPos pos) {
        this.blockPos = pos;
    }

    @Override
    public int getX() {
        return this.blockPos.m_123341_();
    }

    @Override
    public int getY() {
        return this.blockPos.m_123342_();
    }

    @Override
    public int getZ() {
        return this.blockPos.m_123343_();
    }

    @Override
    public IPos up() {
        return new BlockPosWrapper(this.blockPos.m_7494_());
    }

    @Override
    public IPos up(int n) {
        return new BlockPosWrapper(this.blockPos.m_6630_(n));
    }

    @Override
    public IPos down() {
        return new BlockPosWrapper(this.blockPos.m_7495_());
    }

    @Override
    public IPos down(int n) {
        return new BlockPosWrapper(this.blockPos.m_6625_(n));
    }

    @Override
    public IPos north() {
        return new BlockPosWrapper(this.blockPos.m_122012_());
    }

    @Override
    public IPos north(int n) {
        return new BlockPosWrapper(this.blockPos.m_122013_(n));
    }

    @Override
    public IPos east() {
        return new BlockPosWrapper(this.blockPos.m_122029_());
    }

    @Override
    public IPos east(int n) {
        return new BlockPosWrapper(this.blockPos.m_122030_(n));
    }

    @Override
    public IPos south() {
        return new BlockPosWrapper(this.blockPos.m_122019_());
    }

    @Override
    public IPos south(int n) {
        return new BlockPosWrapper(this.blockPos.m_122020_(n));
    }

    @Override
    public IPos west() {
        return new BlockPosWrapper(this.blockPos.m_122024_());
    }

    @Override
    public IPos west(int n) {
        return new BlockPosWrapper(this.blockPos.m_122025_(n));
    }

    @Override
    public IPos add(int x, int y, int z) {
        return new BlockPosWrapper(this.blockPos.m_7918_(x, y, z));
    }

    @Override
    public IPos add(IPos pos) {
        return new BlockPosWrapper(this.blockPos.m_121955_((Vec3i)pos.getMCBlockPos()));
    }

    @Override
    public IPos subtract(int x, int y, int z) {
        return new BlockPosWrapper(this.blockPos.m_7918_(-x, -y, -z));
    }

    @Override
    public IPos subtract(IPos pos) {
        return new BlockPosWrapper(this.blockPos.m_7918_(-pos.getX(), -pos.getY(), -pos.getZ()));
    }

    @Override
    public IPos offset(int direction) {
        return new BlockPosWrapper(this.blockPos.m_121945_(Direction.m_122376_((int)direction)));
    }

    @Override
    public IPos offset(int direction, int n) {
        return new BlockPosWrapper(this.blockPos.m_5484_(Direction.m_122376_((int)direction), n));
    }

    @Override
    public BlockPos getMCBlockPos() {
        return this.blockPos;
    }

    @Override
    public double[] normalize() {
        double d = Math.sqrt(this.blockPos.m_123341_() * this.blockPos.m_123341_() + this.blockPos.m_123342_() * this.blockPos.m_123342_() + this.blockPos.m_123343_() * this.blockPos.m_123343_());
        return new double[]{(double)this.getX() / d, (double)this.getY() / d, (double)this.getZ() / d};
    }

    @Override
    public double distanceTo(IPos pos) {
        double d0 = this.getX() - pos.getX();
        double d1 = this.getY() - pos.getY();
        double d2 = this.getZ() - pos.getZ();
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }
}

