/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.NoppesUtilServer;

public class NoppesUtilPlayer {
    public static boolean compareItems(ItemStack item, ItemStack item2, boolean ignoreDamage, boolean ignoreNBT) {
        if (NoppesUtilServer.IsItemStackNull(item) || NoppesUtilServer.IsItemStackNull(item2)) {
            return false;
        }
        return NoppesUtilPlayer.compareItemDetails(item, item2, ignoreDamage, ignoreNBT);
    }

    private static boolean compareItemDetails(ItemStack item, ItemStack item2, boolean ignoreDamage, boolean ignoreNBT) {
        if (item.m_41720_() != item2.m_41720_()) {
            return false;
        }
        if (!ignoreDamage && item.m_41773_() != -1 && item.m_41773_() != item2.m_41773_()) {
            return false;
        }
        if (!(ignoreNBT || item.m_41783_() == null || item2.m_41783_() != null && item.m_41783_().equals((Object)item2.m_41783_()))) {
            return false;
        }
        return ignoreNBT || item2.m_41783_() == null || item.m_41783_() != null;
    }

    public static boolean compareItems(Player player, ItemStack item, boolean ignoreDamage, boolean ignoreNBT) {
        int size = 0;
        for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack is = player.m_150109_().m_8020_(i);
            if (NoppesUtilServer.IsItemStackNull(is) || !NoppesUtilPlayer.compareItems(item, is, ignoreDamage, ignoreNBT)) continue;
            size += is.m_41613_();
        }
        return size >= item.m_41613_();
    }

    public static void consumeItem(Player player, ItemStack item, boolean ignoreDamage, boolean ignoreNBT) {
        if (NoppesUtilServer.IsItemStackNull(item)) {
            return;
        }
        int size = item.m_41613_();
        for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack is = player.m_150109_().m_8020_(i);
            if (NoppesUtilServer.IsItemStackNull(is) || !NoppesUtilPlayer.compareItems(item, is, ignoreDamage, ignoreNBT)) continue;
            if (size >= is.m_41613_()) {
                size -= is.m_41613_();
                player.m_150109_().m_6836_(i, ItemStack.f_41583_);
                continue;
            }
            is.m_41620_(size);
            break;
        }
    }

    public static List<ItemStack> countStacks(Container inv, boolean ignoreDamage, boolean ignoreNBT) {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int i = 0; i < inv.m_6643_(); ++i) {
            ItemStack item = inv.m_8020_(i);
            if (NoppesUtilServer.IsItemStackNull(item)) continue;
            boolean found = false;
            for (ItemStack is : list) {
                if (!NoppesUtilPlayer.compareItems(item, is, ignoreDamage, ignoreNBT)) continue;
                is.m_41764_(is.m_41613_() + item.m_41613_());
                found = true;
                break;
            }
            if (found) continue;
            list.add(item.m_41777_());
        }
        return list;
    }
}

