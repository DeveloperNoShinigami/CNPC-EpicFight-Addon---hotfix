/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.items;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomEntities;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.util.CustomNPCsScheduler;

public class ItemNpcWand
extends Item {
    public ItemNpcWand() {
        super(new Item.Properties().m_41487_(1));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        if (!level.f_46443_) {
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemstack);
        }
        CustomNpcs.proxy.openGui(player, EnumGuiType.NpcRemote);
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemstack);
    }

    public int m_8105_(ItemStack p_77626_1_) {
        return 72000;
    }

    public InteractionResult m_6225_(UseOnContext context) {
        if (context.m_43725_().f_46443_) {
            return InteractionResult.SUCCESS;
        }
        if (CustomNpcs.OpsOnly && !context.m_43723_().m_20194_().m_6846_().m_11303_(context.m_43723_().m_36316_())) {
            context.m_43723_().m_213846_((Component)Component.m_237115_((String)"availability.permission"));
        } else if (CustomNpcsPermissions.hasPermission((ServerPlayer)context.m_43723_(), CustomNpcsPermissions.NPC_CREATE)) {
            EntityCustomNpc npc = new EntityCustomNpc(CustomEntities.entityCustomNpc, context.m_43725_());
            npc.ais.setStartPos(context.m_8083_().m_7494_());
            npc.m_7678_((float)context.m_8083_().m_123341_() + 0.5f, npc.getStartYPos(), (float)context.m_8083_().m_123343_() + 0.5f, context.m_43723_().m_146908_(), context.m_43723_().m_146909_());
            context.m_43725_().m_7967_((Entity)npc);
            npc.m_21153_(npc.m_21233_());
            CustomNPCsScheduler.runTack(() -> NoppesUtilServer.sendOpenGui(context.m_43723_(), EnumGuiType.MainMenuDisplay, npc), 100);
        } else {
            ((ServerPlayer)context.m_43723_()).m_213846_((Component)Component.m_237115_((String)"availability.permission"));
        }
        return InteractionResult.SUCCESS;
    }

    public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity playerIn) {
        return stack;
    }
}

