/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.event.entity.EntityJoinLevelEvent
 *  net.minecraftforge.event.entity.item.ItemTossEvent
 *  net.minecraftforge.event.entity.player.EntityItemPickupEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package noppes.npcs;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import noppes.npcs.CustomItems;
import noppes.npcs.EventHooks;
import noppes.npcs.items.ItemScripted;

public class ScriptItemEventHandler {
    @SubscribeEvent
    public void invoke(EntityJoinLevelEvent event) {
        if (event.getLevel().f_46443_ || !(event.getEntity() instanceof ItemEntity)) {
            return;
        }
        ItemEntity entity = (ItemEntity)event.getEntity();
        ItemStack stack = entity.m_32055_();
        if (!stack.m_41619_() && stack.m_41720_() == CustomItems.scripted_item && EventHooks.onScriptItemSpawn(ItemScripted.GetWrapper(stack), entity)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void invoke(ItemTossEvent event) {
        if (event.getPlayer().m_9236_().f_46443_) {
            return;
        }
        ItemEntity entity = event.getEntity();
        ItemStack stack = entity.m_32055_();
        if (!stack.m_41619_() && stack.m_41720_() == CustomItems.scripted_item && EventHooks.onScriptItemTossed(ItemScripted.GetWrapper(stack), event.getPlayer(), entity)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void invoke(EntityItemPickupEvent event) {
        if (event.getEntity().m_9236_().f_46443_) {
            return;
        }
        ItemEntity entity = event.getItem();
        ItemStack stack = entity.m_32055_();
        if (!stack.m_41619_() && stack.m_41720_() == CustomItems.scripted_item) {
            EventHooks.onScriptItemPickedUp(ItemScripted.GetWrapper(stack), event.getEntity(), entity);
        }
    }
}

