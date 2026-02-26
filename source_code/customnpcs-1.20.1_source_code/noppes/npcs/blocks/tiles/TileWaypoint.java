/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package noppes.npcs.blocks.tiles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import noppes.npcs.CustomBlocks;
import noppes.npcs.blocks.tiles.TileNpcEntity;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.quests.QuestLocation;

public class TileWaypoint
extends TileNpcEntity {
    public String name = "";
    private int ticks = 10;
    private List<Player> recentlyChecked = new ArrayList<Player>();
    private List<Player> toCheck;
    public int range = 10;

    public TileWaypoint(BlockPos pos, BlockState state) {
        super(CustomBlocks.tile_waypoint, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileWaypoint tile) {
        if (level.f_46443_ || tile.name.isEmpty()) {
            return;
        }
        --tile.ticks;
        if (tile.ticks > 0) {
            return;
        }
        tile.ticks = 10;
        tile.toCheck = tile.getPlayerList(tile.range, tile.range, tile.range);
        tile.toCheck.removeAll(tile.recentlyChecked);
        List<Player> listMax = tile.getPlayerList(tile.range + 10, tile.range + 10, tile.range + 10);
        tile.recentlyChecked.retainAll(listMax);
        tile.recentlyChecked.addAll(tile.toCheck);
        if (tile.toCheck.isEmpty()) {
            return;
        }
        for (Player player : tile.toCheck) {
            PlayerData pdata = PlayerData.get(player);
            PlayerQuestData playerdata = pdata.questData;
            for (QuestData data : playerdata.activeQuests.values()) {
                QuestLocation quest;
                if (data.quest.type != 3 || !(quest = (QuestLocation)data.quest.questInterface).setFound(data, tile.name)) continue;
                player.m_213846_((Component)Component.m_237115_((String)tile.name).m_130946_(" ").m_7220_((Component)Component.m_237115_((String)"quest.found")));
                playerdata.checkQuestCompletion(player, 3);
                pdata.updateClient = true;
            }
        }
    }

    private List<Player> getPlayerList(int x, int y, int z) {
        return this.f_58857_.m_45976_(Player.class, new AABB(this.f_58858_, this.f_58858_.m_7918_(1, 1, 1)).m_82377_((double)x, (double)y, (double)z));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.name = compound.m_128461_("LocationName");
        this.range = compound.m_128451_("LocationRange");
        if (this.range < 2) {
            this.range = 2;
        }
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        if (!this.name.isEmpty()) {
            compound.m_128359_("LocationName", this.name);
        }
        compound.m_128405_("LocationRange", this.range);
        super.m_183515_(compound);
    }
}

