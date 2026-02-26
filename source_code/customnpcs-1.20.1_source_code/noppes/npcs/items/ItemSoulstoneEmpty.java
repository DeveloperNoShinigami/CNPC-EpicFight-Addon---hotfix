/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package noppes.npcs.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.controllers.ServerCloneController;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.roles.RoleInterface;
import noppes.npcs.shared.common.CommonUtil;

public class ItemSoulstoneEmpty
extends Item {
    public ItemSoulstoneEmpty() {
        super(new Item.Properties().m_41487_(64));
    }

    public boolean store(LivingEntity entity, ItemStack stack, Player player) {
        if (!this.hasPermission(entity, player) || entity instanceof Player) {
            return false;
        }
        ItemStack stone = new ItemStack((ItemLike)CustomItems.soulstoneFull);
        CompoundTag compound = new CompoundTag();
        if (!entity.m_20086_(compound)) {
            return false;
        }
        ServerCloneController.Instance.cleanTags(compound);
        stone.m_41700_("Entity", (Tag)compound);
        String name = entity.m_20078_();
        if (name == null) {
            name = "generic";
        }
        stone.m_41700_("Name", (Tag)StringTag.m_129297_((String)name));
        if (entity instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            stone.m_41700_("DisplayName", (Tag)StringTag.m_129297_((String)entity.m_7755_().getString()));
            if (npc.role.getType() == 6) {
                RoleCompanion role = (RoleCompanion)npc.role;
                stone.m_41700_("ExtraText", (Tag)StringTag.m_129297_((String)("companion.stage,: ," + role.stage.name)));
            }
        } else if (entity.m_8077_()) {
            stone.m_41700_("DisplayName", (Tag)StringTag.m_129297_((String)entity.m_7770_().getString()));
        }
        NoppesUtilServer.GivePlayerItem((Entity)player, player, stone);
        if (!player.m_150110_().f_35937_) {
            stack.m_41620_(1);
            if (stack.m_41613_() <= 0) {
                player.m_150109_().m_36057_(stack);
            }
        }
        entity.m_146870_();
        return true;
    }

    public boolean hasPermission(LivingEntity entity, Player player) {
        if (CommonUtil.isOp(player)) {
            return true;
        }
        if (CustomNpcsPermissions.hasPermission((ServerPlayer)player, CustomNpcsPermissions.SOULSTONE_ALL)) {
            return true;
        }
        if (entity instanceof EntityNPCInterface) {
            RoleInterface role;
            EntityNPCInterface npc = (EntityNPCInterface)entity;
            if (npc.role.getType() == 6 && ((RoleCompanion)(role = (RoleCompanion)npc.role)).getOwner() == player) {
                return true;
            }
            if (npc.role.getType() == 2 && ((RoleFollower)(role = (RoleFollower)npc.role)).getOwner() == player) {
                return !((RoleFollower)role).refuseSoulStone;
            }
            return CustomNpcs.SoulStoneNPCs;
        }
        if (entity instanceof Animal) {
            return CustomNpcs.SoulStoneAnimals;
        }
        return false;
    }
}

