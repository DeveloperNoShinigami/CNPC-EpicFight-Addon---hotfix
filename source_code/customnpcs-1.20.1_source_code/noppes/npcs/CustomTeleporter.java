/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.BlockUtil$FoundRectangle
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.level.portal.PortalForcer
 *  net.minecraft.world.level.portal.PortalInfo
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.portal.PortalForcer;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;

public class CustomTeleporter
extends PortalForcer {
    private float yRot;
    private float xRot;
    private Vec3 pos;

    public CustomTeleporter(ServerLevel par1ServerLevel, Vec3 pos, float yRot, float xRot) {
        super(par1ServerLevel);
        this.pos = pos;
        this.yRot = yRot;
        this.xRot = xRot;
    }

    public Optional<BlockUtil.FoundRectangle> m_192985_(BlockPos pos, boolean isNether, WorldBorder border) {
        return Optional.empty();
    }

    public PortalInfo getPortalInfo(Entity entity, ServerLevel destLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        return new PortalInfo(this.pos, Vec3.f_82478_, this.yRot, this.xRot);
    }
}

