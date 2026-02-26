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

public class ContainerNPCBankUnlock
extends ContainerNPCBankInterface {
    public ContainerNPCBankUnlock(int containerId, Inventory playerInventory, int slot, int bankid) {
        super(CustomContainer.container_bankunlock, containerId, playerInventory, slot, bankid);
    }
}

