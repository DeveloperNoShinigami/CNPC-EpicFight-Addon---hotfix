/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.syncher.SynchedEntityData$DataItem
 *  net.minecraft.network.syncher.SynchedEntityData$DataValue
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.animal.Chicken
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.client.ISynchedEntityData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.EntityIMixin;
import noppes.npcs.mixin.EntityLivingIMixin;
import noppes.npcs.mixin.WalkAnimationStateMixin;
import noppes.npcs.shared.common.util.LogWriter;

public class EntityUtil {
    private static HashMap<EntityType<? extends Entity>, Class> entityClasses = new HashMap();

    public static void Copy(LivingEntity copied, LivingEntity entity) {
        EntityNPCInterface npc;
        ((EntityIMixin)entity).setLevel(copied.m_9236_());
        entity.f_20919_ = copied.f_20919_;
        entity.f_19787_ = copied.f_19787_;
        entity.f_19867_ = copied.f_19787_;
        entity.f_19788_ = copied.f_19788_;
        entity.f_20902_ = copied.f_20902_;
        entity.f_20900_ = copied.f_20900_;
        entity.m_6853_(copied.m_20096_());
        entity.f_19789_ = copied.f_19789_;
        entity.m_6862_(((EntityLivingIMixin)copied).jumping());
        List<SynchedEntityData.DataItem<?>> copiedData = ((ISynchedEntityData)copied.m_20088_()).getAll();
        List<SynchedEntityData.DataItem<?>> data = ((ISynchedEntityData)entity.m_20088_()).getAll();
        for (SynchedEntityData.DataItem<?> entry : copiedData) {
            if (!data.stream().anyMatch(e -> e.m_135396_() == entry.m_135396_()) || !(entry.m_135403_() instanceof SynchedEntityData.DataValue)) continue;
            entity.m_20088_().m_135381_(entry.m_135396_(), ((SynchedEntityData.DataValue)entry.m_135403_()).f_252525_());
        }
        entity.f_19854_ = copied.f_19854_;
        entity.f_19855_ = copied.f_19855_;
        entity.f_19856_ = copied.f_19856_;
        entity.m_6034_(copied.m_20185_(), copied.m_20186_(), copied.m_20189_());
        entity.f_19790_ = copied.f_19790_;
        entity.f_19791_ = copied.f_19791_;
        entity.f_19792_ = copied.f_19792_;
        entity.m_20256_(copied.m_20184_());
        entity.m_146926_(copied.m_146909_());
        entity.m_146922_(copied.m_146908_());
        entity.f_19860_ = copied.f_19860_;
        entity.f_19859_ = copied.f_19859_;
        entity.f_20885_ = copied.f_20885_;
        entity.f_20886_ = copied.f_20886_;
        entity.f_20883_ = copied.f_20883_;
        entity.f_20884_ = copied.f_20884_;
        ((EntityLivingIMixin)entity).useItemRemaining(copied.m_21212_());
        ((WalkAnimationStateMixin)entity.f_267362_).setPosition(copied.f_267362_.m_267756_());
        ((EntityLivingIMixin)entity).animStep(((EntityLivingIMixin)copied).animStep());
        ((EntityLivingIMixin)entity).animStepO(((EntityLivingIMixin)copied).animStepO());
        ((EntityLivingIMixin)entity).swimAmount(((EntityLivingIMixin)copied).swimAmount());
        ((EntityLivingIMixin)entity).swimAmountO(((EntityLivingIMixin)copied).swimAmountO());
        entity.f_20911_ = copied.f_20911_;
        entity.f_20913_ = copied.f_20913_;
        entity.f_267362_.m_267771_(copied.f_267362_.m_267731_());
        ((WalkAnimationStateMixin)entity.f_267362_).setSpeedOld(((WalkAnimationStateMixin)copied.f_267362_).getSpeedOld());
        entity.f_20921_ = copied.f_20921_;
        entity.f_20920_ = copied.f_20920_;
        entity.f_19797_ = copied.f_19797_;
        entity.m_21153_(Math.min(copied.m_21223_(), entity.m_21233_()));
        entity.f_20916_ = copied.f_20916_;
        entity.f_20919_ = copied.f_20919_;
        entity.getPersistentData().m_128391_(copied.getPersistentData());
        if (entity instanceof Player && copied instanceof Player) {
            EquipmentSlot[] ePlayer = (EquipmentSlot[])entity;
            Player cPlayer = (Player)copied;
            ePlayer.f_36100_ = cPlayer.f_36100_;
            ePlayer.f_36099_ = cPlayer.f_36099_;
            ePlayer.f_36102_ = cPlayer.f_36102_;
            ePlayer.f_36103_ = cPlayer.f_36103_;
            ePlayer.f_36104_ = cPlayer.f_36104_;
            ePlayer.f_36105_ = cPlayer.f_36105_;
            ePlayer.f_36106_ = cPlayer.f_36106_;
            ePlayer.f_36075_ = cPlayer.f_36075_;
        }
        for (Player player : EquipmentSlot.values()) {
            entity.m_8061_((EquipmentSlot)player, copied.m_6844_((EquipmentSlot)player));
        }
        if (entity instanceof EnderDragon) {
            entity.m_146926_(entity.m_146909_() + 180.0f);
        }
        ((EntityIMixin)entity).removal(((EntityIMixin)copied).removal());
        entity.f_20919_ = copied.f_20919_;
        entity.f_19797_ = copied.f_19797_;
        if (entity instanceof EnderDragon) {
            entity.m_146922_(entity.m_146908_() + 180.0f);
        }
        if (entity instanceof Chicken) {
            ((Chicken)entity).f_28226_ = copied.m_20096_() ? 0.0f : 1.0f;
        }
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            entity.m_8061_(equipmentSlot, copied.m_6844_(equipmentSlot));
        }
        if (copied instanceof EntityNPCInterface && entity instanceof EntityNPCInterface) {
            npc = (EntityNPCInterface)copied;
            EntityNPCInterface target = (EntityNPCInterface)entity;
            target.textureLocation = npc.textureLocation;
            target.textureGlowLocation = npc.textureGlowLocation;
            target.textureCloakLocation = npc.textureCloakLocation;
            target.display = npc.display;
            target.inventory = npc.inventory;
            if (npc.job.getType() == 9) {
                target.job = npc.job;
            }
            if (target.currentAnimation != npc.currentAnimation) {
                target.currentAnimation = npc.currentAnimation;
                npc.m_6210_();
            }
            target.setDataWatcher(npc.m_20088_());
        }
        if (entity instanceof EntityCustomNpc && copied instanceof EntityCustomNpc) {
            npc = (EntityCustomNpc)copied;
            EntityCustomNpc target = (EntityCustomNpc)entity;
            target.modelData = ((EntityCustomNpc)npc).modelData.copy();
            target.modelData.setEntity(null);
        }
    }

    private <T> void setData(LivingEntity entity, List<SynchedEntityData.DataItem<T>> copiedData, List<SynchedEntityData.DataItem<T>> data) {
        for (SynchedEntityData.DataItem entry : copiedData) {
            if (!data.stream().anyMatch(e -> e.m_135396_() == entry.m_135396_())) continue;
            entity.m_20088_().m_135381_(entry.m_135396_(), entry.m_135403_());
        }
    }

    public static void setRecentlyHit(LivingEntity entity) {
        ((EntityLivingIMixin)entity).lastHurtByPlayerTime(100);
    }

    public static HashMap<EntityType<? extends Entity>, Class> getAllEntitiesClasses(Level level) {
        if (!entityClasses.isEmpty()) {
            return entityClasses;
        }
        HashMap<EntityType<? extends Entity>, Class> data = new HashMap<EntityType<? extends Entity>, Class>();
        for (EntityType ent : ForgeRegistries.ENTITY_TYPES.getValues()) {
            try {
                Entity e = ent.m_20615_(level);
                if (e == null) continue;
                if (LivingEntity.class.isAssignableFrom(e.getClass())) {
                    data.put((EntityType<? extends Entity>)ent, e.getClass());
                }
                e.m_146870_();
            }
            catch (Exception exception) {}
        }
        entityClasses = data;
        return entityClasses;
    }

    public static HashMap<EntityType<? extends Entity>, Class> getAllEntitiesClassesNoNpcs(Level level) {
        HashMap<EntityType<? extends Entity>, Class> data = new HashMap<EntityType<? extends Entity>, Class>(EntityUtil.getAllEntitiesClasses(level));
        Iterator<Map.Entry<EntityType<? extends Entity>, Class>> ita = data.entrySet().iterator();
        while (ita.hasNext()) {
            Map.Entry<EntityType<? extends Entity>, Class> entry = ita.next();
            if (!EntityNPCInterface.class.isAssignableFrom(entry.getValue()) && LivingEntity.class.isAssignableFrom(entry.getValue())) continue;
            ita.remove();
        }
        return data;
    }

    public static HashMap<String, ResourceLocation> getAllEntities(Level level, boolean withNpcs) {
        HashMap<String, ResourceLocation> data = new HashMap<String, ResourceLocation>();
        for (EntityType ent : ForgeRegistries.ENTITY_TYPES.getValues()) {
            try {
                Entity e = ent.m_20615_(level);
                if (e == null) continue;
                if (LivingEntity.class.isAssignableFrom(e.getClass()) && (withNpcs || !EntityNPCInterface.class.isAssignableFrom(e.getClass()))) {
                    data.put(ent.m_20675_(), ForgeRegistries.ENTITY_TYPES.getKey((Object)ent));
                }
                e.m_146870_();
            }
            catch (Throwable e) {
                LogWriter.except(e);
            }
        }
        return data;
    }
}

