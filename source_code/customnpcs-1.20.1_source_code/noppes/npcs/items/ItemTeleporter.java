/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.items;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.CustomNpcs;
import noppes.npcs.constants.EnumGuiType;

public class ItemTeleporter
extends Item {
    public ItemTeleporter() {
        super(new Item.Properties().m_41487_(1));
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        if (!level.f_46443_) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemstack);
        }
        CustomNpcs.proxy.openGui(player, EnumGuiType.NpcDimensions);
        return new InteractionResultHolder(InteractionResult.PASS, (Object)itemstack);
    }

    public boolean onEntitySwing(ItemStack stack, LivingEntity livingEntity) {
        if (livingEntity.m_9236_().f_46443_) {
            return true;
        }
        float f = livingEntity.m_146909_();
        float f1 = livingEntity.m_146908_();
        Vec3 vector3d = livingEntity.m_20299_(1.0f);
        float f2 = Mth.m_14089_((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
        float f3 = Mth.m_14031_((float)(-f1 * ((float)Math.PI / 180) - (float)Math.PI));
        float f4 = -Mth.m_14089_((float)(-f * ((float)Math.PI / 180)));
        float f5 = Mth.m_14031_((float)(-f * ((float)Math.PI / 180)));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 80.0;
        Vec3 vector3d1 = vector3d.m_82520_((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        BlockHitResult movingobjectposition = livingEntity.m_9236_().m_45547_(new ClipContext(vector3d, vector3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, (Entity)livingEntity));
        if (movingobjectposition == null) {
            return true;
        }
        Vec3 vec32 = livingEntity.m_20252_(f);
        boolean flag = false;
        float f9 = 1.0f;
        List list = livingEntity.m_9236_().m_45933_((Entity)livingEntity, livingEntity.m_20191_().m_82377_(vec32.f_82479_ * d0, vec32.f_82480_ * d0, vec32.f_82481_ * d0).m_82377_((double)f9, (double)f9, (double)f9));
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity)list.get(i);
            if (!entity.m_5829_()) continue;
            float f10 = entity.m_6143_();
            AABB axisalignedbb = entity.m_20191_().m_82377_((double)f10, (double)f10, (double)f10);
            if (!axisalignedbb.m_82390_(vector3d)) continue;
            flag = true;
        }
        if (flag) {
            return true;
        }
        if (movingobjectposition.m_6662_() == HitResult.Type.BLOCK) {
            BlockPos pos = movingobjectposition.m_82425_();
            while (livingEntity.m_9236_().m_8055_(pos).m_60734_() != Blocks.f_50016_) {
                pos = pos.m_7494_();
            }
            livingEntity.m_6021_((double)((float)pos.m_123341_() + 0.5f), (double)((float)pos.m_123342_() + 1.0f), (double)((float)pos.m_123343_() + 0.5f));
        }
        return true;
    }
}

