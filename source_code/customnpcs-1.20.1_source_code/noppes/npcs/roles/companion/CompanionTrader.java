/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles.companion;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.roles.companion.CompanionJobInterface;

public class CompanionTrader
extends CompanionJobInterface {
    @Override
    public CompoundTag getNBT() {
        CompoundTag compound = new CompoundTag();
        return compound;
    }

    @Override
    public void setNBT(CompoundTag compound) {
    }

    public void interact(Player player) {
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.CompanionTrader, this.npc);
    }

    @Override
    public EnumCompanionJobs getType() {
        return EnumCompanionJobs.SHOP;
    }
}

