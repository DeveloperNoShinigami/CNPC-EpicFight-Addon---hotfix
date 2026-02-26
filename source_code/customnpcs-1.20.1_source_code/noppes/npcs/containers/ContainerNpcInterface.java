/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.containers;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.api.IContainer;
import noppes.npcs.api.wrapper.ContainerWrapper;

public class ContainerNpcInterface
extends AbstractContainerMenu {
    private int posX;
    private int posZ;
    public Player player;
    public IContainer scriptContainer;

    public ContainerNpcInterface(MenuType type, int containerId, Inventory playerInventory) {
        super(type, containerId);
        this.player = playerInventory.f_35978_;
        this.posX = Mth.m_14107_((double)this.player.m_20185_());
        this.posZ = Mth.m_14107_((double)this.player.m_20189_());
        this.player.m_20256_(Vec3.f_82478_);
    }

    public ItemStack m_7648_(Player p_38941_, int p_38942_) {
        return ItemStack.f_41583_;
    }

    public boolean m_6875_(Player player) {
        return !player.m_213877_() && this.posX == Mth.m_14107_((double)player.m_20185_()) && this.posZ == Mth.m_14107_((double)player.m_20189_());
    }

    public static IContainer getOrCreateIContainer(ContainerNpcInterface container) {
        if (container.scriptContainer != null) {
            return container.scriptContainer;
        }
        container.scriptContainer = new ContainerWrapper(container);
        return container.scriptContainer;
    }
}

