/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.HolderGetter
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.util.Mth
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$MovementEmission
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.monster.EnderMan
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.ProjectileUtil
 *  net.minecraft.world.entity.projectile.ThrowableProjectile
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.Level$ExplosionInteraction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package noppes.npcs.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import noppes.npcs.CustomEntities;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EventHooks;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.constants.ParticleType;
import noppes.npcs.api.constants.PotionEffectType;
import noppes.npcs.api.entity.IProjectile;
import noppes.npcs.api.event.ProjectileEvent;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataRanged;

public class EntityProjectile
extends ThrowableProjectile {
    private static final EntityDataAccessor<Boolean> Gravity = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> Arrow = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> Is3d = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> Glows = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> Rotating = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> Sticks = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<ItemStack> ItemStackThrown = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135033_);
    private static final EntityDataAccessor<Integer> Velocity = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> Size = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> Particle = SynchedEntityData.m_135353_(EntityProjectile.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private BlockPos tilePos = BlockPos.f_121853_;
    private BlockState inBlock;
    protected boolean inGround = false;
    public int throwableShake = 0;
    public int arrowShake = 0;
    public boolean canBePickedUp = false;
    public boolean destroyedOnEntityHit = true;
    private Entity thrower;
    private EntityNPCInterface npc;
    private String throwerName = null;
    private int ticksInGround;
    public int ticksInAir = 0;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;
    public float damage = 5.0f;
    public int punch = 0;
    public boolean accelerate = false;
    public boolean explosiveDamage = true;
    public int explosiveRadius = 0;
    public int effect = 0;
    public int duration = 5;
    public int amplify = 0;
    public int accuracy = 60;
    public IProjectileCallback callback;
    public List<ScriptContainer> scripts = new ArrayList<ScriptContainer>();

    public EntityProjectile(EntityType type, Level par1Level) {
        super(type, par1Level);
    }

    protected void m_8097_() {
        this.f_19804_.m_135372_(ItemStackThrown, (Object)ItemStack.f_41583_);
        this.f_19804_.m_135372_(Velocity, (Object)10);
        this.f_19804_.m_135372_(Size, (Object)10);
        this.f_19804_.m_135372_(Particle, (Object)0);
        this.f_19804_.m_135372_(Gravity, (Object)false);
        this.f_19804_.m_135372_(Glows, (Object)false);
        this.f_19804_.m_135372_(Arrow, (Object)false);
        this.f_19804_.m_135372_(Is3d, (Object)false);
        this.f_19804_.m_135372_(Rotating, (Object)false);
        this.f_19804_.m_135372_(Sticks, (Object)false);
    }

    @OnlyIn(value=Dist.CLIENT)
    public boolean m_6783_(double par1) {
        double d1 = this.m_20191_().m_82309_() * 4.0;
        return par1 < (d1 *= 64.0) * d1;
    }

    public EntityProjectile(Level level, LivingEntity limbSwingAmountEntityLiving, ItemStack item, boolean isNPC) {
        super(CustomEntities.entityProjectile, level);
        this.thrower = limbSwingAmountEntityLiving;
        if (this.thrower != null) {
            this.throwerName = this.thrower.m_20148_().toString();
        }
        this.setThrownItem(item);
        this.f_19804_.m_135381_(Arrow, (Object)(this.getItem() == Items.f_42412_ ? 1 : 0));
        this.m_7678_(limbSwingAmountEntityLiving.m_20185_(), limbSwingAmountEntityLiving.m_20186_() + (double)limbSwingAmountEntityLiving.m_20192_(), limbSwingAmountEntityLiving.m_20189_(), limbSwingAmountEntityLiving.m_146908_(), limbSwingAmountEntityLiving.m_146909_());
        double posX = this.m_20185_() - (double)(Mth.m_14089_((float)(this.m_146908_() / 180.0f * (float)Math.PI)) * 0.1f);
        double posY = this.m_20186_() - (double)0.1f;
        double posZ = this.m_20189_() - (double)(Mth.m_14031_((float)(this.m_146908_() / 180.0f * (float)Math.PI)) * 0.1f);
        this.m_6034_(posX, posY, posZ);
        if (isNPC) {
            this.npc = (EntityNPCInterface)this.thrower;
            this.getStatProperties(this.npc.stats.ranged);
            this.m_6210_();
        }
    }

    public void m_7350_(EntityDataAccessor<?> para) {
        if (Size.equals(para)) {
            this.m_6210_();
        }
    }

    public void setThrownItem(ItemStack item) {
        this.f_19804_.m_135381_(ItemStackThrown, (Object)item);
    }

    public int getSize() {
        return (Integer)this.f_19804_.m_135370_(Size);
    }

    public EntityDimensions m_6972_(Pose pose) {
        return new EntityDimensions((float)this.getSize() / 10.0f, (float)this.getSize() / 10.0f, false);
    }

    public void m_6686_(double par1, double par3, double par5, float par7, float par8) {
        double f2 = Math.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
        double f3 = Math.sqrt(par1 * par1 + par5 * par5);
        float yaw = (float)(Math.atan2(par1, par5) * 180.0 / Math.PI);
        float pitch = this.hasGravity() ? par7 : (float)(Math.atan2(par3, f3) * 180.0 / Math.PI);
        this.f_19859_ = yaw;
        this.f_19860_ = pitch;
        this.m_146922_(yaw);
        this.m_146926_(pitch);
        Vec3 m = new Vec3((double)(Mth.m_14031_((float)(yaw / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(pitch / 180.0f * (float)Math.PI))), (double)Mth.m_14031_((float)((pitch + 1.0f) / 180.0f * (float)Math.PI)), (double)(Mth.m_14089_((float)(yaw / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(pitch / 180.0f * (float)Math.PI)))).m_82520_(this.f_19796_.m_188583_() * 0.0075 * (double)par8, this.f_19796_.m_188583_() * 0.0075 * (double)par8, this.f_19796_.m_188583_() * 0.0075 * (double)par8).m_82490_((double)this.getSpeed());
        this.m_20256_(m);
        this.accelerationX = par1 / f2 * 0.1;
        this.accelerationY = par3 / f2 * 0.1;
        this.accelerationZ = par5 / f2 * 0.1;
        this.ticksInGround = 0;
    }

    public float getAngleForXYZ(double varX, double varY, double varZ, float horiDist, boolean arc) {
        float g = this.m_7139_();
        float var1 = this.getSpeed() * this.getSpeed();
        float var2 = g * horiDist;
        float var3 = (float)((double)(g * horiDist * horiDist) + 2.0 * varY * (double)var1);
        float var4 = var1 * var1 - g * var3;
        if (var4 < 0.0f) {
            return 30.0f;
        }
        float var6 = arc ? var1 + Mth.m_14116_((float)var4) : var1 - Mth.m_14116_((float)var4);
        float var7 = (float)(Math.atan2(var6, var2) * 180.0 / Math.PI);
        return var7;
    }

    public void shoot(float speed) {
        double varX = -Mth.m_14031_((float)(this.m_146908_() / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(this.m_146909_() / 180.0f * (float)Math.PI));
        double varZ = Mth.m_14089_((float)(this.m_146908_() / 180.0f * (float)Math.PI)) * Mth.m_14089_((float)(this.m_146909_() / 180.0f * (float)Math.PI));
        double varY = -Mth.m_14031_((float)(this.m_146909_() / 180.0f * (float)Math.PI));
        this.m_6686_(varX, varY, varZ, -this.m_146909_(), speed);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void m_6453_(double par1, double par3, double par5, float par7, float par8, int par9, boolean bo) {
        if (this.m_9236_().f_46443_ && this.inGround) {
            return;
        }
        this.m_6034_(par1, par3, par5);
        this.m_19915_(par7, par8);
    }

    public void m_8119_() {
        AABB axisalignedbb;
        BlockState state;
        VoxelShape shape;
        super.m_6075_();
        if (++this.f_19797_ % 10 == 0) {
            EventHooks.onProjectileTick(this);
        }
        Vec3 motion = this.m_20184_();
        if (this.f_19860_ == 0.0f && this.f_19859_ == 0.0f) {
            double f = motion.m_165924_();
            this.m_146922_((float)(Mth.m_14136_((double)motion.f_82479_, (double)motion.f_82481_) * 57.2957763671875));
            this.m_146926_((float)(Mth.m_14136_((double)motion.f_82480_, (double)f) * 57.2957763671875));
            this.f_19859_ = this.m_146908_();
            this.f_19860_ = this.m_146909_();
        }
        if (this.effect != 666 || !this.inGround) {
            // empty if block
        }
        if ((this.isArrow() || this.sticksToWalls()) && this.tilePos != BlockPos.f_121853_ && this.m_9236_().m_46749_(this.tilePos) && !(shape = (state = this.m_9236_().m_8055_(this.tilePos)).m_60808_((BlockGetter)this.m_9236_(), this.tilePos)).m_83281_() && (axisalignedbb = shape.m_83215_()) != null && axisalignedbb.m_82390_(this.m_20182_())) {
            this.inGround = true;
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround && this.m_9236_().m_46749_(this.tilePos)) {
            BlockState state2 = this.m_9236_().m_8055_(this.tilePos);
            if (state2 == this.inBlock) {
                ++this.ticksInGround;
                if (this.ticksInGround == CustomNpcs.ProjectileLifespan) {
                    this.m_142687_(Entity.RemovalReason.DISCARDED);
                }
            } else {
                this.inGround = false;
                this.m_20256_(this.m_20184_().m_82542_((double)(this.f_19796_.m_188501_() * 0.2f), (double)(this.f_19796_.m_188501_() * 0.2f), (double)(this.f_19796_.m_188501_() * 0.2f)));
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            ++this.ticksInAir;
            if (this.ticksInAir == CustomNpcs.ProjectileLifespan) {
                this.m_142687_(Entity.RemovalReason.DISCARDED);
            }
            Vec3 pos = this.m_20182_();
            Vec3 nextpos = pos.m_82549_(motion);
            HitResult hitresult = ProjectileUtil.m_278158_((Entity)this, this::canHit);
            if (hitresult != null && hitresult.m_6662_() != HitResult.Type.MISS) {
                this.f_19804_.m_135381_(Rotating, (Object)false);
                this.m_6532_(hitresult);
            }
            motion = this.m_20184_();
            double f1 = motion.m_165924_();
            this.m_146926_(EntityProjectile.m_37273_((float)this.f_19860_, (float)((float)(Mth.m_14136_((double)motion.f_82480_, (double)f1) * 57.2957763671875))));
            this.m_146922_(EntityProjectile.m_37273_((float)this.f_19859_, (float)((float)(Mth.m_14136_((double)motion.f_82479_, (double)motion.f_82481_) * 57.2957763671875))));
            if (this.isRotating()) {
                int spin = this.isBlock() ? 10 : 20;
                this.m_146926_(this.m_146909_() - (float)spin * this.getSpeed());
            }
            float f2 = this.getMotionFactor();
            float f3 = this.m_7139_();
            if (this.m_20069_()) {
                for (int j = 0; j < 4; ++j) {
                    float f4 = 0.25f;
                    this.m_9236_().m_7106_((ParticleOptions)ParticleTypes.f_123795_, nextpos.f_82479_ - motion.f_82479_ * 0.25, nextpos.f_82480_ - motion.f_82480_ * 0.25, nextpos.f_82481_ - motion.f_82481_ * 0.25, motion.f_82479_, motion.f_82480_, motion.f_82481_);
                }
                f2 = 0.6f;
            }
            motion = motion.m_82490_((double)f2);
            if (this.hasGravity()) {
                motion = motion.m_82492_(0.0, (double)f3, 0.0);
            }
            if (this.accelerate) {
                motion = motion.m_82520_(this.accelerationX, this.accelerationY, this.accelerationZ);
            }
            if (this.m_9236_().f_46443_ && (Integer)this.f_19804_.m_135370_(Particle) > 0) {
                this.m_9236_().m_7106_(ParticleType.getMCType((Integer)this.f_19804_.m_135370_(Particle)), this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.0, 0.0);
            }
            this.m_20256_(motion);
            this.m_6034_(nextpos.f_82479_, nextpos.f_82480_, nextpos.f_82481_);
            this.m_20101_();
        }
    }

    protected boolean canHit(Entity entity) {
        if (!super.m_5603_(entity) || entity == this.thrower || this.npc != null && (entity == this.npc || this.npc.m_7307_(entity))) {
            return false;
        }
        if (entity instanceof Player) {
            Player entityplayer = (Player)entity;
            if (entityplayer.m_150110_().f_35934_ || this.thrower instanceof Player && !((Player)this.thrower).m_7099_(entityplayer)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBlock() {
        ItemStack item = this.getItemDisplay();
        if (item.m_41619_()) {
            return false;
        }
        return item.m_41720_() instanceof BlockItem;
    }

    private Item getItem() {
        ItemStack item = this.getItemDisplay();
        if (item.m_41619_()) {
            return Items.f_41852_;
        }
        return item.m_41720_();
    }

    protected float getMotionFactor() {
        return this.accelerate ? 0.95f : 1.0f;
    }

    protected void m_6532_(HitResult movingobjectposition) {
        block31: {
            block29: {
                block30: {
                    Vec3 m;
                    double f3;
                    if (!this.m_9236_().f_46443_) {
                        ProjectileEvent.ImpactEvent event;
                        BlockPos pos = BlockPos.f_121853_;
                        Entity e = null;
                        if (movingobjectposition.m_6662_() == HitResult.Type.ENTITY) {
                            e = ((EntityHitResult)movingobjectposition).m_82443_();
                            pos = e.m_20183_();
                            event = new ProjectileEvent.ImpactEvent((IProjectile)NpcAPI.Instance().getIEntity((Entity)this), 0, e);
                        } else {
                            pos = ((BlockHitResult)movingobjectposition).m_82425_();
                            BlockState state = this.m_9236_().m_8055_(pos);
                            event = new ProjectileEvent.ImpactEvent((IProjectile)NpcAPI.Instance().getIEntity((Entity)this), 1, NpcAPI.Instance().getIBlock(this.m_9236_(), pos));
                        }
                        if (pos == BlockPos.f_121853_) {
                            pos = new BlockPos((int)movingobjectposition.m_82450_().f_82479_, (int)movingobjectposition.m_82450_().f_82480_, (int)movingobjectposition.m_82450_().f_82481_);
                        }
                        if (this.callback != null && this.callback.onImpact(this, pos, e)) {
                            return;
                        }
                        EventHooks.onProjectileImpact(this, event);
                    }
                    if (movingobjectposition.m_6662_() != HitResult.Type.ENTITY) break block29;
                    Entity e = ((EntityHitResult)movingobjectposition).m_82443_();
                    float damage = this.damage;
                    if (damage == 0.0f) {
                        damage = 0.001f;
                    }
                    if (!e.m_6469_(this.m_269291_().m_269390_((Entity)this, this.m_19749_()), damage)) break block30;
                    if (e instanceof LivingEntity) {
                        LivingEntity entityliving = (LivingEntity)e;
                        if (!this.m_9236_().f_46443_ && (this.isArrow() || this.sticksToWalls())) {
                            entityliving.m_21317_(entityliving.m_21234_() + 1);
                        }
                        if (this.destroyedOnEntityHit && !(e instanceof EnderMan)) {
                            this.m_142687_(Entity.RemovalReason.DISCARDED);
                        }
                        if (this.effect != 0) {
                            if (this.effect != 666) {
                                MobEffect p = PotionEffectType.getMCType(this.effect);
                                entityliving.m_7292_(new MobEffectInstance(p, this.duration * 20, this.amplify));
                            } else {
                                entityliving.m_7311_(this.duration * 20);
                            }
                        }
                    }
                    if (this.isBlock()) {
                        this.m_9236_().m_5898_((Player)null, 2001, e.m_20183_(), Block.m_49956_((BlockState)((BlockItem)this.getItem()).m_40614_().m_49966_()));
                    } else if (!this.isArrow() && !this.sticksToWalls()) {
                        for (int i = 0; i < 8; ++i) {
                            this.m_9236_().m_7106_((ParticleOptions)new ItemParticleOption(ParticleTypes.f_123752_, this.getItemDisplay()), this.m_20185_(), this.m_20186_(), this.m_20189_(), this.f_19796_.m_188583_() * 0.15, this.f_19796_.m_188583_() * 0.2, this.f_19796_.m_188583_() * 0.15);
                        }
                    }
                    if (this.punch > 0 && (f3 = (m = this.m_20184_()).m_165924_()) > 0.0) {
                        e.m_5997_(m.m_7096_() * (double)this.punch * 0.6 / f3, 0.1, m.m_7094_() * (double)this.punch * 0.6 / f3);
                    }
                    break block31;
                }
                if (!this.hasGravity() || !this.isArrow() && !this.sticksToWalls()) break block31;
                this.m_20256_(this.m_20184_().m_82490_(-0.1));
                this.m_146922_(this.m_146908_() + 180.0f);
                this.f_19859_ += 180.0f;
                this.ticksInAir = 0;
                break block31;
            }
            if (this.isArrow() || this.sticksToWalls()) {
                this.tilePos = ((BlockHitResult)movingobjectposition).m_82425_();
                this.inBlock = this.m_9236_().m_8055_(this.tilePos);
                Vec3 m = movingobjectposition.m_82450_().m_82546_(this.m_20182_());
                this.m_20256_(m);
                Vec3 vector3d1 = m.m_82541_().m_82490_((double)0.05f);
                this.m_20343_(this.m_20185_() - vector3d1.f_82479_, this.m_20186_() - vector3d1.f_82480_, this.m_20189_() - vector3d1.f_82481_);
                this.inGround = true;
                this.arrowShake = 7;
                if (!this.hasGravity()) {
                    this.f_19804_.m_135381_(Gravity, (Object)true);
                }
                if (this.inBlock != null) {
                    this.inBlock.m_60682_(this.m_9236_(), this.tilePos, (Entity)this);
                }
            } else if (this.isBlock()) {
                this.m_9236_().m_5898_((Player)null, 2001, this.m_20183_(), Block.m_49956_((BlockState)((BlockItem)this.getItem()).m_40614_().m_49966_()));
            } else {
                for (int i = 0; i < 8; ++i) {
                    this.m_9236_().m_7106_((ParticleOptions)new ItemParticleOption(ParticleTypes.f_123752_, this.getItemDisplay()), this.m_20185_(), this.m_20186_(), this.m_20189_(), this.f_19796_.m_188583_() * 0.15, this.f_19796_.m_188583_() * 0.2, this.f_19796_.m_188583_() * 0.15);
                }
            }
        }
        if (this.explosiveRadius > 0) {
            boolean terraindamage = this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_) && this.explosiveDamage;
            this.m_9236_().m_255391_((Entity)(this.m_19749_() == null ? this : this.m_19749_()), this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)this.explosiveRadius, this.effect == 666, terraindamage ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
            if (this.effect != 0) {
                AABB axisalignedbb = this.m_20191_().m_82377_((double)(this.explosiveRadius * 2), (double)(this.explosiveRadius * 2), (double)(this.explosiveRadius * 2));
                List list1 = this.m_9236_().m_45976_(LivingEntity.class, axisalignedbb);
                MobEffect p = PotionEffectType.getMCType(this.effect);
                for (LivingEntity entity : list1) {
                    if (this.effect != 666) {
                        entity.m_7292_(new MobEffectInstance(p, this.duration * 20, this.amplify));
                        continue;
                    }
                    entity.m_7311_(this.duration * 20);
                }
                this.m_9236_().m_5898_((Player)null, 2002, this.m_20183_(), this.getPotionColor(this.effect));
            }
            this.m_142687_(Entity.RemovalReason.DISCARDED);
        }
        if (!(this.m_9236_().f_46443_ || this.isArrow() || this.sticksToWalls())) {
            this.m_142687_(Entity.RemovalReason.DISCARDED);
        }
    }

    private void blockParticles() {
    }

    public void m_7380_(CompoundTag par1CompoundTag) {
        par1CompoundTag.m_128376_("xTile", (short)this.tilePos.m_123341_());
        par1CompoundTag.m_128376_("yTile", (short)this.tilePos.m_123342_());
        par1CompoundTag.m_128376_("zTile", (short)this.tilePos.m_123343_());
        if (this.inBlock != null) {
            par1CompoundTag.m_128365_("inBlockState", (Tag)NbtUtils.m_129202_((BlockState)this.inBlock));
        }
        par1CompoundTag.m_128344_("shake", (byte)this.throwableShake);
        par1CompoundTag.m_128379_("inGround", this.inGround);
        par1CompoundTag.m_128379_("isArrow", this.isArrow());
        Vec3 m = this.m_20184_();
        par1CompoundTag.m_128365_("direction", (Tag)this.m_20063_(new double[]{m.f_82479_, m.f_82480_, m.f_82481_}));
        par1CompoundTag.m_128379_("canBePickedUp", this.canBePickedUp);
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof Player) {
            this.throwerName = this.thrower.m_20148_().toString();
        }
        par1CompoundTag.m_128359_("ownerName", this.throwerName == null ? "" : this.throwerName);
        par1CompoundTag.m_128365_("Item", (Tag)this.getItemDisplay().m_41739_(new CompoundTag()));
        par1CompoundTag.m_128350_("damagev2", this.damage);
        par1CompoundTag.m_128405_("punch", this.punch);
        par1CompoundTag.m_128405_("size", ((Integer)this.f_19804_.m_135370_(Size)).intValue());
        par1CompoundTag.m_128405_("velocity", ((Integer)this.f_19804_.m_135370_(Velocity)).intValue());
        par1CompoundTag.m_128405_("explosiveRadius", this.explosiveRadius);
        par1CompoundTag.m_128405_("effectDuration", this.duration);
        par1CompoundTag.m_128379_("gravity", this.hasGravity());
        par1CompoundTag.m_128379_("accelerate", this.accelerate);
        par1CompoundTag.m_128379_("glows", ((Boolean)this.f_19804_.m_135370_(Glows)).booleanValue());
        par1CompoundTag.m_128405_("PotionEffect", this.effect);
        par1CompoundTag.m_128405_("trailenum", ((Integer)this.f_19804_.m_135370_(Particle)).intValue());
        par1CompoundTag.m_128379_("Render3D", ((Boolean)this.f_19804_.m_135370_(Is3d)).booleanValue());
        par1CompoundTag.m_128379_("Spins", ((Boolean)this.f_19804_.m_135370_(Rotating)).booleanValue());
        par1CompoundTag.m_128379_("Sticks", ((Boolean)this.f_19804_.m_135370_(Sticks)).booleanValue());
        par1CompoundTag.m_128405_("accuracy", this.accuracy);
    }

    public void m_7378_(CompoundTag compound) {
        CompoundTag var2;
        ItemStack item;
        this.tilePos = new BlockPos((int)compound.m_128448_("xTile"), (int)compound.m_128448_("yTile"), (int)compound.m_128448_("zTile"));
        if (compound.m_128425_("inBlockState", 10)) {
            this.inBlock = NbtUtils.m_247651_((HolderGetter)this.m_9236_().m_246945_(Registries.f_256747_), (CompoundTag)compound.m_128469_("inBlockState"));
        }
        this.throwableShake = compound.m_128445_("shake") & 0xFF;
        this.inGround = compound.m_128445_("inGround") == 1;
        this.f_19804_.m_135381_(Arrow, (Object)compound.m_128471_("isArrow"));
        this.throwerName = compound.m_128461_("ownerName");
        this.canBePickedUp = compound.m_128471_("canBePickedUp");
        this.damage = compound.m_128457_("damagev2");
        this.punch = compound.m_128451_("punch");
        this.explosiveRadius = compound.m_128451_("explosiveRadius");
        this.duration = compound.m_128451_("effectDuration");
        this.accelerate = compound.m_128471_("accelerate");
        this.effect = compound.m_128451_("PotionEffect");
        this.accuracy = compound.m_128451_("accuracy");
        this.f_19804_.m_135381_(Particle, (Object)compound.m_128451_("trailenum"));
        this.f_19804_.m_135381_(Size, (Object)compound.m_128451_("size"));
        this.f_19804_.m_135381_(Glows, (Object)compound.m_128471_("glows"));
        this.f_19804_.m_135381_(Velocity, (Object)compound.m_128451_("velocity"));
        this.f_19804_.m_135381_(Gravity, (Object)compound.m_128471_("gravity"));
        this.f_19804_.m_135381_(Is3d, (Object)compound.m_128471_("Render3D"));
        this.f_19804_.m_135381_(Rotating, (Object)compound.m_128471_("Spins"));
        this.f_19804_.m_135381_(Sticks, (Object)compound.m_128471_("Sticks"));
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
        if (compound.m_128441_("direction")) {
            ListTag nbttaglist = compound.m_128437_("direction", 6);
            this.m_20256_(new Vec3(nbttaglist.m_128772_(0), nbttaglist.m_128772_(1), nbttaglist.m_128772_(2)));
        }
        if ((item = ItemStack.m_41712_((CompoundTag)(var2 = compound.m_128469_("Item")))).m_41619_()) {
            this.m_146870_();
        } else {
            this.f_19804_.m_135381_(ItemStackThrown, (Object)item);
        }
    }

    public Entity m_19749_() {
        if (this.throwerName == null || this.throwerName.isEmpty()) {
            return null;
        }
        try {
            UUID uuid = UUID.fromString(this.throwerName);
            if (this.thrower == null && uuid != null) {
                this.thrower = this.m_9236_().m_46003_(uuid);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return this.thrower;
    }

    private int getPotionColor(int p) {
        switch (p) {
            case 19: {
                return 32660;
            }
            case 17: {
                return 32660;
            }
            case 18: {
                return 32696;
            }
            case 2: {
                return 32698;
            }
            case 9: {
                return 32732;
            }
            case 15: {
                return 15;
            }
            case 20: {
                return 32732;
            }
        }
        return 0;
    }

    public void getStatProperties(DataRanged stats) {
        this.damage = stats.getStrength();
        this.punch = stats.getKnockback();
        this.accelerate = stats.getAccelerate();
        this.explosiveRadius = stats.getExplodeSize();
        this.effect = stats.getEffectType();
        this.duration = stats.getEffectTime();
        this.amplify = stats.getEffectStrength();
        this.setParticleEffect(stats.getParticle());
        this.f_19804_.m_135381_(Size, (Object)stats.getSize());
        this.f_19804_.m_135381_(Glows, (Object)stats.getGlows());
        this.setSpeed(stats.getSpeed());
        this.setHasGravity(stats.getHasGravity());
        this.setIs3D(stats.getRender3D());
        this.setRotating(stats.getSpins());
        this.setStickInWall(stats.getSticks());
    }

    public void setParticleEffect(int type) {
        this.f_19804_.m_135381_(Particle, (Object)type);
    }

    public void setHasGravity(boolean bo) {
        this.f_19804_.m_135381_(Gravity, (Object)bo);
    }

    public void setIs3D(boolean bo) {
        this.f_19804_.m_135381_(Is3d, (Object)bo);
    }

    public void setStickInWall(boolean bo) {
        this.f_19804_.m_135381_(Sticks, (Object)bo);
    }

    public ItemStack getItemDisplay() {
        return (ItemStack)this.f_19804_.m_135370_(ItemStackThrown);
    }

    public float m_213856_() {
        return (Boolean)this.f_19804_.m_135370_(Glows) != false ? 1.0f : super.m_213856_();
    }

    public boolean hasGravity() {
        return (Boolean)this.f_19804_.m_135370_(Gravity);
    }

    public void setSpeed(int speed) {
        this.f_19804_.m_135381_(Velocity, (Object)speed);
    }

    public float getSpeed() {
        return (float)((Integer)this.f_19804_.m_135370_(Velocity)).intValue() / 10.0f;
    }

    public boolean isArrow() {
        return (Boolean)this.f_19804_.m_135370_(Arrow);
    }

    public void setRotating(boolean bo) {
        this.f_19804_.m_135381_(Rotating, (Object)bo);
    }

    public boolean isRotating() {
        return (Boolean)this.f_19804_.m_135370_(Rotating);
    }

    public boolean glows() {
        return (Boolean)this.f_19804_.m_135370_(Glows);
    }

    public boolean is3D() {
        return (Boolean)this.f_19804_.m_135370_(Is3d) != false || this.isBlock();
    }

    public boolean sticksToWalls() {
        return this.is3D() && (Boolean)this.f_19804_.m_135370_(Sticks) != false;
    }

    public void m_6123_(Player par1Player) {
        if (this.m_9236_().f_46443_ || !this.canBePickedUp || !this.inGround || this.arrowShake > 0) {
            return;
        }
        if (par1Player.m_150109_().m_36054_(this.getItemDisplay())) {
            this.inGround = false;
            this.m_5496_(SoundEvents.f_12019_, 0.2f, ((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.7f + 1.0f) * 2.0f);
            par1Player.m_7938_((Entity)this, 1);
            this.m_146870_();
        }
    }

    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    public Component m_5446_() {
        if (!this.getItemDisplay().m_41619_()) {
            return this.getItemDisplay().m_41611_();
        }
        return super.m_5446_();
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        Entity entity = this.m_19749_();
        return new ClientboundAddEntityPacket((Entity)this, entity == null ? 0 : entity.m_19879_());
    }

    public static interface IProjectileCallback {
        public boolean onImpact(EntityProjectile var1, BlockPos var2, Entity var3);
    }
}

