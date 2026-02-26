/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.items;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleFollower;

public class ItemSoulstoneFilled
extends Item {
    public ItemSoulstoneFilled() {
        super(new Item.Properties().m_41487_(1));
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_7373_(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
        CompoundTag compound = stack.m_41783_();
        if (compound == null || !compound.m_128425_("Entity", 10)) {
            list.add((Component)Component.m_237113_((String)(ChatFormatting.RED + "Error")));
            return;
        }
        MutableComponent name = Component.m_237115_((String)compound.m_128461_("Name"));
        if (compound.m_128441_("DisplayName")) {
            name = Component.m_237115_((String)compound.m_128461_("DisplayName")).m_130946_(" (").m_7220_((Component)name).m_130946_(")");
        }
        list.add((Component)Component.m_237113_((String)("" + ChatFormatting.BLUE)).m_7220_((Component)name));
        if (stack.m_41783_().m_128441_("ExtraText")) {
            String[] split;
            MutableComponent text = Component.m_237113_((String)"");
            for (String s : split = compound.m_128461_("ExtraText").split(",")) {
                text.m_7220_((Component)Component.m_237115_((String)s));
            }
            list.add((Component)text);
        }
    }

    public InteractionResult m_6225_(UseOnContext context) {
        if (context.m_43725_().f_46443_) {
            return InteractionResult.SUCCESS;
        }
        ItemStack stack = context.m_43722_();
        if (ItemSoulstoneFilled.Spawn(context.m_43723_(), stack, context.m_43725_(), context.m_8083_()) == null) {
            return InteractionResult.FAIL;
        }
        if (!context.m_43723_().m_150110_().f_35937_) {
            stack.m_41620_(1);
        }
        return InteractionResult.SUCCESS;
    }

    public static Entity Spawn(Player player, ItemStack stack, Level level, BlockPos pos) {
        if (level.f_46443_) {
            return null;
        }
        if (stack.m_41783_() == null || !stack.m_41783_().m_128425_("Entity", 10)) {
            return null;
        }
        CompoundTag compound = stack.m_41783_().m_128469_("Entity");
        Entity entity = EntityType.m_20642_((CompoundTag)compound, (Level)level).orElse(null);
        if (entity == null) {
            return null;
        }
        entity.m_6034_((double)pos.m_123341_() + 0.5, (double)((float)(pos.m_123342_() + 1) + 0.2f), (double)pos.m_123343_() + 0.5);
        if (entity instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            npc.ais.setStartPos(pos);
            npc.m_21153_(npc.m_21233_());
            npc.m_6034_((float)pos.m_123341_() + 0.5f, npc.getStartYPos(), (float)pos.m_123343_() + 0.5f);
            if (npc.role.getType() == 6 && player != null) {
                PlayerData data = PlayerData.get(player);
                if (data.hasCompanion()) {
                    return null;
                }
                ((RoleCompanion)npc.role).setOwner(player);
                data.setCompanion(npc);
            }
            if (npc.role.getType() == 2 && player != null) {
                ((RoleFollower)npc.role).setOwner(player);
            }
        }
        if (!level.m_7967_(entity)) {
            player.m_213846_((Component)Component.m_237115_((String)"error.failedToSpawn"));
            return null;
        }
        return entity;
    }
}

