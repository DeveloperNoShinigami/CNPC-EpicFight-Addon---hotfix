/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.geom.ModelPart
 */
package noppes.npcs.client.parts;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import noppes.npcs.client.parts.AnimationContainer;
import noppes.npcs.shared.client.model.NopModelPart;
import noppes.npcs.shared.common.util.NopVector3f;

public class ModelPartWrapper {
    protected ModelPart mcPart = null;
    protected NopModelPart mpmPart = null;
    public final NopVector3f oriPos;
    public final NopVector3f oriRot;
    public final NopVector3f oriScale;
    public Map<Integer, AnimationContainer> animations = new HashMap<Integer, AnimationContainer>();

    public ModelPartWrapper(ModelPart mcPart, NopVector3f oriPos, NopVector3f oriRot) {
        this.mcPart = mcPart;
        this.oriRot = oriRot;
        this.oriPos = oriPos;
        this.oriScale = new NopVector3f(1.0f, 1.0f, 1.0f);
    }

    public ModelPartWrapper(NopModelPart mpmPart, NopVector3f oriPos, NopVector3f oriRot) {
        this.mpmPart = mpmPart;
        this.oriRot = oriRot;
        this.oriPos = oriPos;
        this.oriScale = new NopVector3f(1.0f, 1.0f, 1.0f);
    }

    public NopVector3f getPos() {
        if (this.mcPart != null) {
            return new NopVector3f(this.mcPart.f_104200_, this.mcPart.f_104201_, this.mcPart.f_104202_);
        }
        return new NopVector3f(this.mpmPart.x, this.mpmPart.y, this.mpmPart.z);
    }

    public void setPos(NopVector3f pos) {
        if (this.mcPart != null) {
            this.mcPart.m_104227_(pos.x, pos.y, pos.z);
        } else {
            this.mpmPart.setPos(pos.x, pos.y, pos.z);
        }
    }

    public NopVector3f getRot() {
        if (this.mcPart != null) {
            return new NopVector3f(this.mcPart.f_104203_, this.mcPart.f_104204_, this.mcPart.f_104205_);
        }
        return new NopVector3f(this.mpmPart.xRot, this.mpmPart.yRot, this.mpmPart.zRot);
    }

    public void setRot(NopVector3f rot) {
        if (this.mcPart != null) {
            this.mcPart.m_171327_(rot.x, rot.y, rot.z);
        } else {
            this.mpmPart.setRotation(rot);
        }
    }

    public NopVector3f getScale() {
        if (this.mcPart != null) {
            return new NopVector3f(this.mcPart.f_233553_, this.mcPart.f_233554_, this.mcPart.f_233555_);
        }
        return this.mpmPart.scale;
    }

    public void setScale(NopVector3f scale) {
        if (this.mcPart != null) {
            this.mcPart.f_233553_ = scale.x;
            this.mcPart.f_233554_ = scale.y;
            this.mcPart.f_233555_ = scale.z;
        } else {
            this.mpmPart.scale = scale;
        }
    }

    public void setVisible(boolean b) {
        if (this.mcPart != null) {
            this.mcPart.f_104207_ = b;
        } else {
            this.mpmPart.visible = b;
        }
    }
}

