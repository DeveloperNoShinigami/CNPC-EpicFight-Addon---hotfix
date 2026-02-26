/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.horse.AbstractHorse
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.npc.Villager
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.CapabilityDispatcher
 *  net.minecraftforge.common.capabilities.CapabilityManager
 *  net.minecraftforge.common.capabilities.CapabilityProvider
 *  net.minecraftforge.common.capabilities.CapabilityToken
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.event.AttachCapabilitiesEvent
 */
package noppes.npcs.api.wrapper;

import java.lang.reflect.Field;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityDispatcher;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.wrapper.AnimalWrapper;
import noppes.npcs.api.wrapper.ArrowWrapper;
import noppes.npcs.api.wrapper.EntityItemWrapper;
import noppes.npcs.api.wrapper.EntityLivingBaseWrapper;
import noppes.npcs.api.wrapper.EntityLivingWrapper;
import noppes.npcs.api.wrapper.EntityWrapper;
import noppes.npcs.api.wrapper.MonsterWrapper;
import noppes.npcs.api.wrapper.PixelmonWrapper;
import noppes.npcs.api.wrapper.PlayerWrapper;
import noppes.npcs.api.wrapper.ProjectileWrapper;
import noppes.npcs.api.wrapper.ThrowableWrapper;
import noppes.npcs.api.wrapper.VillagerWrapper;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.shared.common.util.LogWriter;

public class WrapperEntityData
implements ICapabilityProvider {
    private static final Field capField;
    public static Capability<WrapperEntityData> ENTITYDATA_CAPABILITY;
    private LazyOptional<WrapperEntityData> instance = LazyOptional.of(() -> this);
    public IEntity base;
    private static WrapperEntityData backup;
    private static final ResourceLocation key;

    public WrapperEntityData(IEntity base) {
        this.base = base;
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == ENTITYDATA_CAPABILITY) {
            return this.instance.cast();
        }
        return LazyOptional.empty();
    }

    public static IEntity get(Entity entity) {
        if (entity == null || entity.m_20182_() == Vec3.f_82478_) {
            return null;
        }
        try {
            CapabilityDispatcher dispatcher = (CapabilityDispatcher)capField.get(entity);
            if (dispatcher == null) {
                LogWriter.warn("Unable to get EntityData for " + entity);
                return WrapperEntityData.getData((Entity)entity).base;
            }
            WrapperEntityData data = (WrapperEntityData)dispatcher.getCapability(ENTITYDATA_CAPABILITY, null).orElse((Object)backup);
            if (data == null || data == backup) {
                LogWriter.warn("Unable to get EntityData for " + entity);
                return WrapperEntityData.getData((Entity)entity).base;
            }
            return data.base;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void register(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(key, (ICapabilityProvider)WrapperEntityData.getData((Entity)event.getObject()));
    }

    private static WrapperEntityData getData(Entity entity) {
        if (entity == null || entity.m_9236_() == null || entity.m_9236_().f_46443_) {
            return null;
        }
        if (entity instanceof ServerPlayer) {
            return new WrapperEntityData(new PlayerWrapper<ServerPlayer>((ServerPlayer)entity));
        }
        if (PixelmonHelper.isPixelmon(entity)) {
            return new WrapperEntityData(new PixelmonWrapper<AbstractHorse>((AbstractHorse)entity));
        }
        if (entity instanceof Villager) {
            return new WrapperEntityData(new VillagerWrapper<Villager>((Villager)entity));
        }
        if (entity instanceof Animal) {
            return new WrapperEntityData(new AnimalWrapper<Animal>((Animal)entity));
        }
        if (entity instanceof Monster) {
            return new WrapperEntityData(new MonsterWrapper<Monster>((Monster)entity));
        }
        if (entity instanceof Mob) {
            return new WrapperEntityData(new EntityLivingWrapper<Mob>((Mob)entity));
        }
        if (entity instanceof LivingEntity) {
            return new WrapperEntityData(new EntityLivingBaseWrapper<LivingEntity>((LivingEntity)entity));
        }
        if (entity instanceof ItemEntity) {
            return new WrapperEntityData(new EntityItemWrapper<ItemEntity>((ItemEntity)entity));
        }
        if (entity instanceof EntityProjectile) {
            return new WrapperEntityData(new ProjectileWrapper<EntityProjectile>((EntityProjectile)entity));
        }
        if (entity instanceof ThrowableProjectile) {
            return new WrapperEntityData(new ThrowableWrapper<ThrowableProjectile>((ThrowableProjectile)entity));
        }
        if (entity instanceof AbstractArrow) {
            return new WrapperEntityData(new ArrowWrapper<AbstractArrow>((AbstractArrow)entity));
        }
        return new WrapperEntityData(new EntityWrapper<Entity>(entity));
    }

    static {
        Field f = null;
        try {
            f = CapabilityProvider.class.getDeclaredField("capabilities");
            f.setAccessible(true);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        capField = f;
        ENTITYDATA_CAPABILITY = CapabilityManager.get((CapabilityToken)new CapabilityToken<WrapperEntityData>(){});
        backup = new WrapperEntityData(null);
        key = new ResourceLocation("customnpcs", "entitydata");
    }
}

