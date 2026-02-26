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
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 */
package noppes.npcs.items;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.entity.EntityNPCInterface;

public class ItemNpcMovingPath
extends Item {
    public ItemNpcMovingPath() {
        super(new Item.Properties().m_41487_(1));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        if (level.f_46443_ || !CustomNpcsPermissions.hasPermission((ServerPlayer)player, CustomNpcsPermissions.TOOL_PATHER)) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemstack);
        }
        EntityNPCInterface npc = this.getNpc(itemstack, level);
        if (npc != null) {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.MovingPath, npc);
        }
        return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemstack);
    }

    public InteractionResult m_6225_(UseOnContext context) {
        if (context.m_43725_().f_46443_ || !CustomNpcsPermissions.hasPermission((ServerPlayer)context.m_43723_(), CustomNpcsPermissions.TOOL_PATHER)) {
            return InteractionResult.FAIL;
        }
        ItemStack stack = context.m_43722_();
        EntityNPCInterface npc = this.getNpc(stack, context.m_43725_());
        if (npc == null) {
            return InteractionResult.PASS;
        }
        List<int[]> list = npc.ais.getMovingPath();
        int[] pos = list.get(list.size() - 1);
        int x = context.m_8083_().m_123341_();
        int y = context.m_8083_().m_123342_();
        int z = context.m_8083_().m_123343_();
        list.add(new int[]{x, y, z});
        double d3 = x - pos[0];
        double d4 = y - pos[1];
        double d5 = z - pos[2];
        double distance = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        context.m_43723_().m_213846_((Component)Component.m_237110_((String)"message.pather.added", (Object[])new Object[]{x, y, z, npc.m_7755_()}));
        if (distance > (double)CustomNpcs.NpcNavRange) {
            ((ServerPlayer)context.m_43723_()).m_213846_((Component)Component.m_237110_((String)"message.pather.farwarning", (Object[])new Object[]{CustomNpcs.NpcNavRange}));
        }
        return InteractionResult.SUCCESS;
    }

    private EntityNPCInterface getNpc(ItemStack item, Level level) {
        if (level.f_46443_ || item.m_41783_() == null) {
            return null;
        }
        Entity entity = level.m_6815_(item.m_41783_().m_128451_("NPCID"));
        if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return null;
        }
        return (EntityNPCInterface)entity;
    }
}

