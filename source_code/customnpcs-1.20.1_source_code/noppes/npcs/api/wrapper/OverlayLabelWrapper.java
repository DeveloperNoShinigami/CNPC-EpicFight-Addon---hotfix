/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package noppes.npcs.api.wrapper;

import net.minecraft.nbt.CompoundTag;
import noppes.npcs.api.overlay.ILabel;
import noppes.npcs.api.wrapper.OverlayComponentWrapper;

public class OverlayLabelWrapper
extends OverlayComponentWrapper
implements ILabel {
    private String text;
    private boolean isCenter = false;
    private float scale = 1.0f;

    public OverlayLabelWrapper(int id, int x, int y, String text) {
        super(id, x, y);
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public ILabel setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public float getScale() {
        return this.scale;
    }

    @Override
    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public ILabel setCentered(boolean centered) {
        this.isCenter = centered;
        return this;
    }

    @Override
    public boolean isCentered() {
        return this.isCenter;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void toNbt(CompoundTag compound) {
        super.toNbt(compound);
        compound.m_128359_("text", this.text);
        compound.m_128350_("scale", this.scale);
        if (this.isCenter) {
            compound.m_128379_("centered", true);
        }
    }

    @Override
    public void fromNbt(CompoundTag compound) {
        super.fromNbt(compound);
        this.text = compound.m_128461_("text");
        this.scale = compound.m_128457_("scale");
        this.isCenter = compound.m_128471_("centered");
    }
}

