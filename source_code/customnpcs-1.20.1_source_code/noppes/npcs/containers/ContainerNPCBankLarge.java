/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package noppes.npcs.containers;

import net.minecraft.world.entity.player.Inventory;
import noppes.npcs.CustomContainer;
import noppes.npcs.containers.ContainerNPCBankInterface;

public class ContainerNPCBankLarge
extends ContainerNPCBankInterface {
    public ContainerNPCBankLarge(int containerId, Inventory playerInventory, int slot, int bankid) {
        super(CustomContainer.container_banklarge, containerId, playerInventory, slot, bankid);
    }

    @Override
    public boolean isUpgraded() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int getRowNumber() {
        return 6;
    }
}

