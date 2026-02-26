/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.wrapper;

import net.minecraft.nbt.CompoundTag;
import noppes.npcs.api.overlay.IOverlayComponent;

public abstract class OverlayComponentWrapper
implements IOverlayComponent {
    private int id;
    private int x;
    private int y;
    private int alignment = -1;

    public OverlayComponentWrapper(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getPosX() {
        return this.x;
    }

    @Override
    public int getPosY() {
        return this.y;
    }

    @Override
    public IOverlayComponent setPos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public void toNbt(CompoundTag compound) {
        compound.m_128405_("id", this.id);
        compound.m_128385_("pos", new int[]{this.x, this.y});
        compound.m_128405_("type", this.getType());
        compound.m_128405_("alignment", this.getAlignment());
    }

    @Override
    public void fromNbt(CompoundTag compound) {
        int[] pos = compound.m_128465_("pos");
        this.x = pos[0];
        this.y = pos[1];
        this.id = compound.m_128451_("id");
        if (compound.m_128441_("alignment")) {
            this.alignment = compound.m_128451_("alignment");
        }
    }

    @Override
    public int getAlignment() {
        return this.alignment;
    }

    @Override
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }
}

