/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.entity;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomEntities;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityDialogNpc
extends EntityNPCInterface {
    public EntityDialogNpc(Level world) {
        super(CustomEntities.entityCustomNpc, world);
    }

    @Override
    public boolean m_20177_(Player player) {
        return true;
    }

    @Override
    public boolean m_20145_() {
        return true;
    }

    @Override
    public void m_8119_() {
    }

    @Override
    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        return InteractionResult.FAIL;
    }
}

