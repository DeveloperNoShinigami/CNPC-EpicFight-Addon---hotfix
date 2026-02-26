/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import noppes.npcs.controllers.data.BlockData;
import noppes.npcs.entity.EntityNPCInterface;

public class MassBlockController {
    private static Queue<IMassBlock> queue;
    private static MassBlockController Instance;

    public MassBlockController() {
        queue = new LinkedList<IMassBlock>();
        Instance = this;
    }

    public static void Update() {
        if (queue.isEmpty()) {
            return;
        }
        IMassBlock imb = queue.remove();
        Level level = imb.getNpc().m_9236_();
        BlockPos pos = imb.getNpc().m_20183_();
        int range = imb.getRange();
        ArrayList<BlockData> list = new ArrayList<BlockData>();
        for (int x = -range; x < range; ++x) {
            for (int z = -range; z < range; ++z) {
                if (!level.m_46749_(new BlockPos(x + pos.m_123341_(), 64, z + pos.m_123343_()))) continue;
                for (int y = 0; y < range; ++y) {
                    BlockPos blockPos = pos.m_7918_(x, y - range / 2, z);
                    list.add(new BlockData(blockPos, level.m_8055_(blockPos), null));
                }
            }
        }
        imb.processed(list);
    }

    public static void Queue(IMassBlock imb) {
        queue.add(imb);
    }

    public static interface IMassBlock {
        public EntityNPCInterface getNpc();

        public int getRange();

        public void processed(List<BlockData> var1);
    }
}

