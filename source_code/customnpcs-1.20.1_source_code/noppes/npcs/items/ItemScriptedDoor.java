/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.DoubleHighBlockItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 */
package noppes.npcs.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.packets.server.SPacketGuiOpen;

public class ItemScriptedDoor
extends DoubleHighBlockItem {
    public ItemScriptedDoor(Block block) {
        super(block, new Item.Properties().m_41487_(1));
    }

    public InteractionResult m_6225_(UseOnContext context) {
        InteractionResult res = super.m_6225_(context);
        if (res == InteractionResult.SUCCESS && !context.m_43725_().f_46443_) {
            PlayerData data = PlayerData.get(context.m_43723_());
            data.scriptBlockPos = context.m_8083_();
            SPacketGuiOpen.sendOpenGui(context.m_43723_(), EnumGuiType.ScriptDoor, null, context.m_8083_().m_7494_());
            return InteractionResult.SUCCESS;
        }
        return res;
    }

    public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity playerIn) {
        return stack;
    }
}

