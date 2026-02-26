/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.shared.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ContainerEmpty
extends AbstractContainerMenu {
    public ContainerEmpty() {
        super(null, 0);
    }

    public ItemStack m_7648_(Player p_38941_, int p_38942_) {
        return ItemStack.f_41583_;
    }

    public boolean m_6875_(Player var1) {
        return false;
    }
}

