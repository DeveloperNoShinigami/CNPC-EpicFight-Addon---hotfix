/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.commands.CommandSource
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.network.syncher.SynchedEntityData$DataItem
 *  net.minecraft.network.syncher.SynchedEntityData$DataValue
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerBossEvent
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.BossEvent$BossBarOverlay
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$RemovalReason
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.GoalSelector
 *  net.minecraft.world.entity.ai.goal.OpenDoorGoal
 *  net.minecraft.world.entity.ai.goal.RestrictSunGoal
 *  net.minecraft.world.entity.ai.goal.WrappedGoal
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
 *  net.minecraft.world.entity.ai.navigation.GroundPathNavigation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.monster.RangedAttackMob
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.util.FakePlayer
 *  net.minecraftforge.entity.IEntityAdditionalSpawnData
 *  net.minecraftforge.event.ServerChatEvent
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.fluids.FluidType
 *  net.minecraftforge.network.NetworkHooks
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package noppes.npcs.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.server.ServerLifecycleHooks;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EventHooks;
import noppes.npcs.IChatMessages;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcDamageSource;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.ai.CombatHandler;
import noppes.npcs.ai.EntityAIAnimation;
import noppes.npcs.ai.EntityAIAttackTarget;
import noppes.npcs.ai.EntityAIAvoidTarget;
import noppes.npcs.ai.EntityAIBustDoor;
import noppes.npcs.ai.EntityAIFindShade;
import noppes.npcs.ai.EntityAIFollow;
import noppes.npcs.ai.EntityAIJob;
import noppes.npcs.ai.EntityAILook;
import noppes.npcs.ai.EntityAIMoveIndoors;
import noppes.npcs.ai.EntityAIMovingPath;
import noppes.npcs.ai.EntityAIPanic;
import noppes.npcs.ai.EntityAIPounceTarget;
import noppes.npcs.ai.EntityAIRangedAttack;
import noppes.npcs.ai.EntityAIReturn;
import noppes.npcs.ai.EntityAIRole;
import noppes.npcs.ai.EntityAISprintToTarget;
import noppes.npcs.ai.EntityAITransform;
import noppes.npcs.ai.EntityAIWander;
import noppes.npcs.ai.EntityAIWatchClosest;
import noppes.npcs.ai.EntityAIWaterNav;
import noppes.npcs.ai.EntityAIWorldLines;
import noppes.npcs.ai.FlyingMoveHelper;
import noppes.npcs.ai.NpcGroundPathNavigator;
import noppes.npcs.ai.selector.NPCAttackSelector;
import noppes.npcs.ai.target.EntityAIClearTarget;
import noppes.npcs.ai.target.EntityAIOwnerHurtByTarget;
import noppes.npcs.ai.target.EntityAIOwnerHurtTarget;
import noppes.npcs.ai.target.NpcNearestAttackableTargetGoal;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.constants.PotionEffectType;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IProjectile;
import noppes.npcs.api.event.NpcEvent;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.api.wrapper.ItemStackWrapper;
import noppes.npcs.api.wrapper.NPCWrapper;
import noppes.npcs.client.EntityUtil;
import noppes.npcs.client.ISynchedEntityData;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.LinkedNpcController;
import noppes.npcs.controllers.VisibilityController;
import noppes.npcs.controllers.data.DataTransform;
import noppes.npcs.controllers.data.Dialog;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.controllers.data.Faction;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.controllers.data.MarkData;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.entity.data.DataAI;
import noppes.npcs.entity.data.DataAbilities;
import noppes.npcs.entity.data.DataAdvanced;
import noppes.npcs.entity.data.DataDisplay;
import noppes.npcs.entity.data.DataInventory;
import noppes.npcs.entity.data.DataScript;
import noppes.npcs.entity.data.DataStats;
import noppes.npcs.entity.data.DataTimers;
import noppes.npcs.items.ItemSoulstoneFilled;
import noppes.npcs.mixin.EntityIMixin;
import noppes.npcs.mixin.GoalSelectorMixin;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketChatBubble;
import noppes.npcs.packets.client.PacketNpcUpdate;
import noppes.npcs.packets.client.PacketNpcVisibleFalse;
import noppes.npcs.packets.client.PacketNpcVisibleTrue;
import noppes.npcs.packets.client.PacketPlaySound;
import noppes.npcs.packets.client.PacketQuestCompletion;
import noppes.npcs.packets.client.PacketUpdatePhysics;
import noppes.npcs.roles.JobBard;
import noppes.npcs.roles.JobFollower;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.roles.RoleInterface;
import noppes.npcs.util.GameProfileAlt;

public abstract class EntityNPCInterface
extends PathfinderMob
implements IEntityAdditionalSpawnData,
RangedAttackMob {
    public static final EntityDataAccessor<Boolean> Attacking = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    protected static final EntityDataAccessor<Integer> Animation = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<String> RoleData = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    private static final EntityDataAccessor<String> JobData = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135030_);
    private static final EntityDataAccessor<Integer> FactionData = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> Walking = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> Interacting = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> IsDead = SynchedEntityData.m_135353_(EntityNPCInterface.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    public static final GameProfileAlt CommandProfile = new GameProfileAlt();
    public static final GameProfileAlt ChatEventProfile = new GameProfileAlt();
    public static final GameProfileAlt GenericProfile = new GameProfileAlt();
    public static FakePlayer ChatEventPlayer;
    public static FakePlayer CommandPlayer;
    public static FakePlayer GenericPlayer;
    public ICustomNpc wrappedNPC;
    public final DataAbilities abilities = new DataAbilities(this);
    public DataDisplay display = new DataDisplay(this);
    public DataStats stats = new DataStats(this);
    public DataInventory inventory = new DataInventory(this);
    public final DataAI ais = new DataAI(this);
    public final DataAdvanced advanced = new DataAdvanced(this);
    public final DataScript script = new DataScript(this);
    public final DataTransform transform = new DataTransform(this);
    public final DataTimers timers = new DataTimers((Object)this);
    public CombatHandler combatHandler = new CombatHandler(this);
    public String linkedName = "";
    public long linkedLast = 0L;
    public LinkedNpcController.LinkedData linkedData;
    public EntityDimensions baseSize = new EntityDimensions(0.6f, 1.8f, false);
    private static final EntityDimensions sizeSleep;
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    private boolean wasKilled = false;
    public RoleInterface role = RoleInterface.NONE;
    public JobInterface job = JobInterface.NONE;
    public HashMap<Integer, DialogOption> dialogs;
    public boolean hasDied = false;
    public long killedtime = 0L;
    public long totalTicksAlive = 0L;
    private int taskCount = 1;
    public int lastInteract = 0;
    public Faction faction;
    private EntityAIRangedAttack aiRange;
    private Goal aiAttackTarget;
    public EntityAILook lookAi;
    public EntityAIAnimation animateAi;
    public List<LivingEntity> interactingEntities = new ArrayList<LivingEntity>();
    public ResourceLocation textureLocation = null;
    public ResourceLocation textureGlowLocation = null;
    public ResourceLocation textureCloakLocation = null;
    public int currentAnimation = 0;
    public int animationStart = 0;
    public int npcVersion = VersionCompatibility.ModRev;
    public IChatMessages messages;
    public boolean updateClient = false;
    public boolean updateAI = false;
    public final ServerBossEvent bossInfo;
    public final HashSet<Integer> tracking = new HashSet();
    public double prevChasingPosX;
    public double prevChasingPosY;
    public double prevChasingPosZ;
    public double chasingPosX;
    public double chasingPosY;
    public double chasingPosZ;
    private double startYPos = -6666.0;

    public EntityNPCInterface(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
        if (!this.isClientSide()) {
            this.wrappedNPC = new NPCWrapper<EntityNPCInterface>(this);
        }
        this.registerBaseAttributes();
        this.dialogs = new HashMap();
        if (!CustomNpcs.DefaultInteractLine.isEmpty()) {
            this.advanced.interactLines.lines.put(0, new Line(CustomNpcs.DefaultInteractLine));
        }
        this.f_21364_ = 0;
        this.scaleZ = 0.9375f;
        this.scaleY = 0.9375f;
        this.scaleX = 0.9375f;
        this.faction = this.getFaction();
        this.setFaction(this.faction.id);
        this.updateAI = true;
        this.bossInfo = new ServerBossEvent(this.m_5446_(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);
        this.bossInfo.m_8321_(false);
    }

    public boolean m_6040_() {
        return this.ais.movementType == 2;
    }

    public boolean m_6063_() {
        return this.ais.movementType != 2;
    }

    @Nullable
    public LivingEntity m_6688_() {
        return this.m_20197_().isEmpty() || !(this.m_20197_().get(0) instanceof LivingEntity) || !this.ais.mountControl ? null : (LivingEntity)this.m_20197_().get(0);
    }

    private void registerBaseAttributes() {
        this.m_21051_(Attributes.f_22276_).m_22100_((double)this.stats.maxHealth);
        this.m_21051_(Attributes.f_22277_).m_22100_((double)CustomNpcs.NpcNavRange);
        this.m_21051_(Attributes.f_22279_).m_22100_((double)this.m_6113_());
        this.m_21051_(Attributes.f_22281_).m_22100_((double)this.stats.melee.getStrength());
        this.m_21051_(Attributes.f_22282_).m_22100_(0.0);
        this.m_21051_(Attributes.f_22280_).m_22100_((double)(this.m_6113_() * 2.0f));
    }

    public static AttributeSupplier.Builder m_21552_() {
        return LivingEntity.m_21183_().m_22266_(Attributes.f_22281_).m_22266_(Attributes.f_22280_).m_22266_(Attributes.f_22277_).m_22266_(Attributes.f_22282_);
    }

    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(RoleData, (Object)String.valueOf(""));
        this.f_19804_.m_135372_(JobData, (Object)String.valueOf(""));
        this.f_19804_.m_135372_(FactionData, (Object)0);
        this.f_19804_.m_135372_(Animation, (Object)0);
        this.f_19804_.m_135372_(Walking, (Object)false);
        this.f_19804_.m_135372_(Interacting, (Object)false);
        this.f_19804_.m_135372_(IsDead, (Object)false);
        this.f_19804_.m_135372_(Attacking, (Object)false);
    }

    public boolean m_6084_() {
        return super.m_6084_() && !this.isKilled();
    }

    public void m_8119_() {
        super.m_8119_();
        if (this.f_19797_ % 10 == 0) {
            this.startYPos = this.calculateStartYPos(this.ais.startPos()) + 1.0;
            if (this.startYPos < (double)this.m_9236_().m_141937_() && !this.isClientSide()) {
                this.m_146870_();
            }
            EventHooks.onNPCTick(this);
        }
        this.timers.update();
        if (this.m_9236_().f_46443_ && this.wasKilled != this.isKilled() && this.wasKilled) {
            this.f_20919_ = 0;
            this.m_6210_();
        }
        this.wasKilled = this.isKilled();
        if (this.currentAnimation == 14) {
            this.f_20919_ = 19;
        }
    }

    public boolean m_275843_() {
        return false;
    }

    public boolean m_7327_(Entity par1Entity) {
        Holder.Reference damageTypeHolder;
        boolean var4;
        float f = this.stats.melee.getStrength();
        if (this.stats.melee.getDelay() < 10) {
            par1Entity.f_19802_ = 0;
        }
        if (par1Entity instanceof LivingEntity) {
            NpcEvent.MeleeAttackEvent event = new NpcEvent.MeleeAttackEvent(this.wrappedNPC, (LivingEntity)par1Entity, f);
            if (EventHooks.onNPCAttacksMelee(this, event)) {
                return false;
            }
            f = event.damage;
        }
        if (var4 = par1Entity.m_6469_(new DamageSource((Holder)(damageTypeHolder = this.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_246971_(NpcDamageSource.NPC)), (Entity)this), f)) {
            if (this.getOwner() instanceof Player) {
                EntityUtil.setRecentlyHit((LivingEntity)par1Entity);
            }
            if (this.stats.melee.getKnockback() > 0) {
                par1Entity.m_5997_((double)(-Mth.m_14031_((float)(this.m_146908_() * (float)Math.PI / 180.0f)) * (float)this.stats.melee.getKnockback() * 0.5f), 0.1, (double)(Mth.m_14089_((float)(this.m_146908_() * (float)Math.PI / 180.0f)) * (float)this.stats.melee.getKnockback() * 0.5f));
                this.m_20256_(this.m_20184_().m_82542_(0.6, 1.0, 0.6));
            }
            if (this.role.getType() == 6) {
                ((RoleCompanion)this.role).attackedEntity(par1Entity);
            }
        }
        if (this.stats.melee.getEffectType() != 0) {
            if (this.stats.melee.getEffectType() != 666) {
                ((LivingEntity)par1Entity).m_7292_(new MobEffectInstance(PotionEffectType.getMCType(this.stats.melee.getEffectType()), this.stats.melee.getEffectTime() * 20, this.stats.melee.getEffectStrength()));
            } else {
                par1Entity.m_7311_(this.stats.melee.getEffectTime() * 20);
            }
        }
        return var4;
    }

    public void m_8107_() {
        float f;
        if (CustomNpcs.FreezeNPCs) {
            return;
        }
        if (this.m_21525_()) {
            super.m_8107_();
            return;
        }
        ++this.totalTicksAlive;
        this.m_21203_();
        if (this.f_19797_ % 20 == 0) {
            this.faction = this.getFaction();
        }
        if (!this.m_9236_().f_46443_) {
            if (!this.isKilled() && this.f_19797_ % 20 == 0) {
                this.advanced.scenes.update();
                if (this.m_21223_() < this.m_21233_()) {
                    if (this.stats.healthRegen > 0 && !this.isAttacking()) {
                        this.m_5634_(this.stats.healthRegen);
                    }
                    if (this.stats.combatRegen > 0 && this.isAttacking()) {
                        this.m_5634_(this.stats.combatRegen);
                    }
                }
                if (this.faction.getsAttacked && !this.isAttacking()) {
                    List list = this.m_9236_().m_45976_(Monster.class, this.m_20191_().m_82377_(16.0, 16.0, 16.0));
                    for (Monster mob : list) {
                        if (mob.m_5448_() != null || !this.canNpcSee((Entity)mob)) continue;
                        mob.m_6710_((LivingEntity)this);
                    }
                }
                if (this.linkedData != null && this.linkedData.time > this.linkedLast) {
                    LinkedNpcController.Instance.loadNpcData(this);
                }
                if (this.updateClient) {
                    this.updateClient();
                }
                if (this.updateAI) {
                    this.updateTasks();
                    this.updateAI = false;
                }
            }
            if (this.m_21223_() <= 0.0f && !this.isKilled()) {
                this.m_21219_();
                this.f_19804_.m_135381_(IsDead, (Object)true);
                this.updateTasks();
                this.m_6210_();
            }
            if (this.display.getBossbar() == 2) {
                this.bossInfo.m_8321_(this.m_5448_() != null);
            }
            this.f_19804_.m_135381_(Walking, (Object)(!this.m_21573_().m_26571_() ? 1 : 0));
            this.f_19804_.m_135381_(Interacting, (Object)this.isInteracting());
            this.combatHandler.update();
            this.onCollide();
        }
        if (this.wasKilled != this.isKilled() && this.wasKilled) {
            this.reset();
        }
        if (this.m_9236_().m_46461_() && !this.m_9236_().f_46443_ && this.stats.burnInSun && (f = this.m_213856_()) > 0.5f && this.f_19796_.m_188501_() * 30.0f < (f - 0.4f) * 2.0f && this.m_9236_().m_45527_(this.m_20183_())) {
            this.m_7311_(160);
        }
        super.m_8107_();
        if (this.m_9236_().f_46443_) {
            this.role.clientUpdate();
            if (this.textureCloakLocation != null) {
                this.cloakUpdate();
            }
            if (this.currentAnimation != (Integer)this.f_19804_.m_135370_(Animation)) {
                this.currentAnimation = (Integer)this.f_19804_.m_135370_(Animation);
                this.animationStart = this.f_19797_;
                this.m_6210_();
            }
            if (this.job.getType() == 1) {
                ((JobBard)this.job).aiStep();
            }
        }
        if (this.display.getBossbar() > 0) {
            this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
        }
    }

    public void updateClient() {
        Packets.sendNearby((Entity)this, new PacketNpcUpdate(this.m_19879_(), this.writeSpawnData()));
        this.updateClient = false;
    }

    protected InteractionResult m_6071_(Player player, InteractionHand hand) {
        if (this.m_9236_().f_46443_) {
            return this.isAttacking() ? InteractionResult.FAIL : InteractionResult.PASS;
        }
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        if (CustomNpcs.EnableInvisibleNpcs && CustomNpcs.InvisibilityAlgorithm == 2 && !this.display.isVisibleTo(player) && !player.m_5833_() && player.m_21205_().m_41720_() != CustomItems.wand) {
            return InteractionResult.PASS;
        }
        ItemStack stack = player.m_21120_(hand);
        if (stack != null) {
            Item item = stack.m_41720_();
            if (item == CustomItems.cloner || item == CustomItems.wand || item == CustomItems.mount || item == CustomItems.scripter) {
                this.m_6710_(null);
                this.m_6703_(null);
                return InteractionResult.SUCCESS;
            }
            if (item == CustomItems.moving) {
                this.m_6710_(null);
                stack.m_41700_("NPCID", (Tag)IntTag.m_128679_((int)this.m_19879_()));
                player.m_213846_((Component)Component.m_237110_((String)"message.pather.register", (Object[])new Object[]{this.m_7755_()}));
                return InteractionResult.SUCCESS;
            }
        }
        if (EventHooks.onNPCInteract(this, player)) {
            return InteractionResult.FAIL;
        }
        if (this.getFaction().isAggressiveToPlayer(player) || this.isAttacking()) {
            return InteractionResult.FAIL;
        }
        this.addInteract((LivingEntity)player);
        Dialog dialog = this.getDialog(player);
        QuestData data = PlayerData.get((Player)player).questData.getQuestCompletion(player, this);
        if (data != null) {
            Packets.send((ServerPlayer)player, new PacketQuestCompletion(data.quest.id));
        } else if (dialog != null) {
            NoppesUtilServer.openDialog(player, this, dialog);
        } else if (this.role.getType() != 0) {
            this.role.interact(player);
        } else {
            this.say(player, this.advanced.getInteractLine());
        }
        return InteractionResult.PASS;
    }

    public void addInteract(LivingEntity entity) {
        if (!this.ais.stopAndInteract || this.isAttacking() || !entity.m_6084_() || this.m_21525_()) {
            return;
        }
        if (this.f_19797_ - this.lastInteract < 180) {
            this.interactingEntities.clear();
        }
        this.m_21573_().m_26573_();
        this.lastInteract = this.f_19797_;
        if (!this.interactingEntities.contains(entity)) {
            this.interactingEntities.add(entity);
        }
    }

    public boolean isInteracting() {
        if (this.f_19797_ - this.lastInteract < 40 || this.isClientSide() && ((Boolean)this.f_19804_.m_135370_(Interacting)).booleanValue()) {
            return true;
        }
        return this.ais.stopAndInteract && !this.interactingEntities.isEmpty() && this.f_19797_ - this.lastInteract < 180;
    }

    private Dialog getDialog(Player player) {
        for (DialogOption option : this.dialogs.values()) {
            if (option == null || !option.hasDialog()) continue;
            Dialog dialog = option.getDialog();
            if (!dialog.availability.isAvailable(player)) continue;
            return dialog;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean m_6469_(DamageSource damagesource, float i) {
        if (this.m_9236_().f_46443_ || CustomNpcs.FreezeNPCs || damagesource.m_19385_().equals("inWall")) {
            return false;
        }
        if (damagesource.m_19385_().equals("outOfLevel") && this.isKilled()) {
            this.reset();
        }
        i = this.stats.resistances.applyResistance(damagesource, i);
        float f = this.f_19802_;
        Objects.requireNonNull(this);
        if (f > 20.0f / 2.0f && i <= this.f_20898_) {
            return false;
        }
        Entity entity = NoppesUtilServer.GetDamageSourcee(damagesource);
        LivingEntity attackingEntity = null;
        if (entity instanceof LivingEntity) {
            attackingEntity = (LivingEntity)entity;
        }
        if (attackingEntity != null && attackingEntity == this.getOwner()) {
            return false;
        }
        if (attackingEntity instanceof EntityNPCInterface) {
            EntityNPCInterface npc = (EntityNPCInterface)attackingEntity;
            if (npc.faction.id == this.faction.id) {
                return false;
            }
            if (npc.getOwner() instanceof Player) {
                this.f_20916_ = 100;
            }
        } else if (attackingEntity instanceof Player && this.faction.isFriendlyToPlayer((Player)attackingEntity)) {
            ForgeHooks.onLivingAttack((LivingEntity)this, (DamageSource)damagesource, (float)i);
            return false;
        }
        NpcEvent.DamagedEvent event = new NpcEvent.DamagedEvent(this.wrappedNPC, entity, i, damagesource);
        if (EventHooks.onNPCDamaged(this, event)) {
            ForgeHooks.onLivingAttack((LivingEntity)this, (DamageSource)damagesource, (float)i);
            return false;
        }
        i = event.damage;
        if (this.isKilled()) {
            return false;
        }
        if (attackingEntity == null) {
            return super.m_6469_(damagesource, i);
        }
        try {
            if (this.isAttacking()) {
                if (this.m_5448_() != null && this.m_20280_((Entity)this.m_5448_()) > this.m_20280_((Entity)attackingEntity)) {
                    this.m_6710_(attackingEntity);
                }
                boolean bl = super.m_6469_(damagesource, i);
                return bl;
            }
            if (i > 0.0f) {
                List inRange = this.m_9236_().m_45976_(EntityNPCInterface.class, this.m_20191_().m_82377_(32.0, 16.0, 32.0));
                for (EntityNPCInterface npc : inRange) {
                    if (npc.isKilled() || !npc.advanced.defendFaction || npc.faction.id != this.faction.id || !npc.canNpcSee((Entity)this) && !npc.ais.directLOS && !npc.canNpcSee((Entity)attackingEntity)) continue;
                    npc.onAttack(attackingEntity);
                }
                this.m_6710_(attackingEntity);
            }
            boolean bl = super.m_6469_(damagesource, i);
            return bl;
        }
        finally {
            if (event.clearTarget) {
                this.m_6710_(null);
                this.m_6703_(null);
            }
        }
    }

    protected void m_6475_(DamageSource damageSrc, float damageAmount) {
        super.m_6475_(damageSrc, damageAmount);
        this.combatHandler.damage(damageSrc, damageAmount);
    }

    public void onAttack(LivingEntity entity) {
        if (entity == null || entity == this || this.isAttacking() || this.ais.onAttack == 3 || entity == this.getOwner()) {
            return;
        }
        super.m_6710_(entity);
    }

    public void m_6710_(LivingEntity entity) {
        Line line;
        if (entity instanceof Player && ((Player)entity).m_150110_().f_35934_ || entity != null && entity == this.getOwner() || this.m_5448_() == entity) {
            return;
        }
        if (entity != null) {
            Object event = new NpcEvent.TargetEvent(this.wrappedNPC, (LivingEntity)entity);
            if (EventHooks.onNPCTarget(this, event)) {
                return;
            }
            entity = ((NpcEvent.TargetEvent)((Object)event)).entity == null ? null : ((NpcEvent.TargetEvent)((Object)event)).entity.getMCEntity();
        } else {
            for (WrappedGoal en : this.f_21346_.m_148105_()) {
                en.m_8041_();
            }
            if (EventHooks.onNPCTargetLost(this, this.m_5448_())) {
                return;
            }
        }
        if (entity != null && entity != this && this.ais.onAttack != 3 && !this.isAttacking() && !this.isClientSide() && (line = this.advanced.getAttackLine()) != null) {
            this.saySurrounding(Line.formatTarget(line, entity));
        }
        super.m_6710_(entity);
    }

    public void m_6504_(LivingEntity entity, float f) {
        ItemStack proj = ItemStackWrapper.MCItem(this.inventory.getProjectile());
        if (proj == null) {
            this.updateAI = true;
            return;
        }
        NpcEvent.RangedLaunchedEvent event = new NpcEvent.RangedLaunchedEvent(this.wrappedNPC, entity, this.stats.ranged.getStrength());
        for (int i = 0; i < this.stats.ranged.getShotCount(); ++i) {
            EntityProjectile projectile = this.shoot(entity, this.stats.ranged.getAccuracy(), proj, f == 1.0f);
            projectile.damage = event.damage;
            projectile.callback = (projectile1, pos, entity1) -> {
                SoundEvent sound;
                Entity e;
                if (proj.m_41720_() == CustomItems.soulstoneFull && (e = ItemSoulstoneFilled.Spawn(null, proj, this.m_9236_(), pos)) instanceof LivingEntity && entity1 instanceof LivingEntity) {
                    if (e instanceof Mob) {
                        ((Mob)e).m_6710_((LivingEntity)entity1);
                    } else {
                        ((LivingEntity)e).m_6703_((LivingEntity)entity1);
                    }
                }
                if ((sound = this.stats.ranged.getSoundEvent(entity1 != null ? 1 : 2)) != null) {
                    projectile1.m_5496_(sound, 1.0f, 1.2f / (this.m_217043_().m_188501_() * 0.2f + 0.9f));
                }
                return false;
            };
            SoundEvent sound = this.stats.ranged.getSoundEvent(0);
            if (sound != null) {
                this.m_5496_(sound, 2.0f, 1.0f);
            }
            event.projectiles.add((IProjectile)NpcAPI.Instance().getIEntity((Entity)projectile));
        }
        EventHooks.onNPCRangedLaunched(this, event);
    }

    public EntityProjectile shoot(LivingEntity entity, int accuracy, ItemStack proj, boolean indirect) {
        return this.shoot(entity.m_20185_(), entity.m_20191_().f_82289_ + (double)(entity.m_20206_() / 2.0f), entity.m_20189_(), accuracy, proj, indirect);
    }

    public EntityProjectile shoot(double x, double y, double z, int accuracy, ItemStack proj, boolean indirect) {
        EntityProjectile projectile = new EntityProjectile(this.m_9236_(), (LivingEntity)this, proj.m_41777_(), true);
        double varX = x - this.m_20185_();
        double varY = y - (this.m_20186_() + (double)this.m_20192_());
        double varZ = z - this.m_20189_();
        float varF = projectile.hasGravity() ? (float)Math.sqrt(varX * varX + varZ * varZ) : 0.0f;
        float angle = projectile.getAngleForXYZ(varX, varY, varZ, varF, indirect);
        float acc = 20.0f - (float)Mth.m_14143_((float)((float)accuracy / 5.0f));
        projectile.m_6686_(varX, varY, varZ, angle, acc);
        this.m_9236_().m_7967_((Entity)projectile);
        return projectile;
    }

    private void clearTasks(GoalSelector tasks) {
        ArrayList list = new ArrayList(tasks.m_148105_());
        for (WrappedGoal entityaitaskentry : list) {
            tasks.m_25363_((Goal)entityaitaskentry);
        }
        tasks.m_148105_().clear();
        ((GoalSelectorMixin)tasks).lockedFlags().clear();
        ((GoalSelectorMixin)tasks).disabledFlags().clear();
    }

    private void updateTasks() {
        if (this.m_9236_() == null || this.m_9236_().f_46443_ || !(this.m_9236_() instanceof ServerLevel)) {
            return;
        }
        ServerLevel sLevel = (ServerLevel)this.m_9236_();
        this.clearTasks(this.f_21345_);
        this.clearTasks(this.f_21346_);
        if (this.isKilled()) {
            return;
        }
        this.f_21346_.m_25352_(0, (Goal)new EntityAIClearTarget(this));
        this.f_21346_.m_25352_(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[0]));
        this.f_21346_.m_25352_(2, new NpcNearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 4, this.ais.directLOS, false, (Predicate<LivingEntity>)((Object)new NPCAttackSelector(this))));
        this.f_21346_.m_25352_(3, (Goal)new EntityAIOwnerHurtByTarget(this));
        this.f_21346_.m_25352_(4, (Goal)new EntityAIOwnerHurtTarget(this));
        if (this.ais.movementType == 1) {
            this.f_21342_ = new FlyingMoveHelper(this);
            if (!(this.f_21344_ instanceof FlyingPathNavigation)) {
                this.f_21344_ = new FlyingPathNavigation((Mob)this, this.m_9236_()){

                    public boolean m_6342_(BlockPos p_26439_) {
                        return true;
                    }
                };
            }
        } else if (this.ais.movementType == 2) {
            this.f_21342_ = new FlyingMoveHelper(this);
            if (!(this.f_21344_ instanceof WaterBoundPathNavigation)) {
                this.f_21344_ = new WaterBoundPathNavigation((Mob)this, this.m_9236_());
            }
        } else {
            this.f_21342_ = new MoveControl((Mob)this);
            if (!(this.f_21344_ instanceof GroundPathNavigation)) {
                this.f_21344_ = new NpcGroundPathNavigator((Mob)this, this.m_9236_());
            }
            this.f_21345_.m_25352_(0, (Goal)new EntityAIWaterNav(this));
        }
        this.taskCount = 1;
        this.addRegularEntries();
        this.doorInteractType();
        this.seekShelter();
        this.setResponse();
        this.setMoveType();
    }

    protected PathNavigation m_6037_(Level p_21480_) {
        return new NpcGroundPathNavigator((Mob)this, p_21480_);
    }

    private void setResponse() {
        this.aiRange = null;
        this.aiAttackTarget = null;
        if (this.ais.canSprint) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAISprintToTarget(this));
        }
        if (this.ais.onAttack == 1) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIPanic(this, 1.2f));
        } else if (this.ais.onAttack == 2) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIAvoidTarget(this));
        } else if (this.ais.onAttack == 0) {
            if (this.ais.canLeap) {
                this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIPounceTarget(this));
            }
            this.aiAttackTarget = new EntityAIAttackTarget(this);
            this.f_21345_.m_25352_(this.taskCount, this.aiAttackTarget);
            if (this.inventory.getProjectile() != null) {
                this.aiRange = new EntityAIRangedAttack(this);
                this.f_21345_.m_25352_(this.taskCount++, (Goal)this.aiRange);
            }
        } else if (this.ais.onAttack == 3) {
            // empty if block
        }
    }

    public boolean canFly() {
        return this.f_21344_ instanceof FlyingPathNavigation;
    }

    public void setMoveType() {
        if (this.ais.getMovingType() == 1) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIWander(this));
        }
        if (this.ais.getMovingType() == 2) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIMovingPath(this));
        }
    }

    public void doorInteractType() {
        if (this.f_21344_ instanceof GroundPathNavigation) {
            Object aiDoor = null;
            if (this.ais.doorInteract == 1) {
                aiDoor = new OpenDoorGoal((Mob)this, true);
                this.f_21345_.m_25352_(this.taskCount++, (Goal)aiDoor);
            } else if (this.ais.doorInteract == 0) {
                aiDoor = new EntityAIBustDoor((Mob)this);
                this.f_21345_.m_25352_(this.taskCount++, (Goal)aiDoor);
            }
            ((GroundPathNavigation)this.f_21344_).m_26477_(aiDoor != null);
        }
    }

    public void seekShelter() {
        if (this.ais.findShelter == 0) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIMoveIndoors(this));
        } else if (this.ais.findShelter == 1) {
            if (!this.canFly()) {
                this.f_21345_.m_25352_(this.taskCount++, (Goal)new RestrictSunGoal((PathfinderMob)this));
            }
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIFindShade(this));
        }
    }

    public void addRegularEntries() {
        this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIReturn(this));
        this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIFollow(this));
        if (this.ais.getStandingType() != 1 && this.ais.getStandingType() != 3) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIWatchClosest(this, LivingEntity.class, 5.0f));
        }
        this.lookAi = new EntityAILook(this);
        this.f_21345_.m_25352_(this.taskCount++, (Goal)this.lookAi);
        this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIWorldLines(this));
        this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIJob(this));
        this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAIRole(this));
        this.animateAi = new EntityAIAnimation(this);
        this.f_21345_.m_25352_(this.taskCount++, (Goal)this.animateAi);
        if (this.transform.isValid()) {
            this.f_21345_.m_25352_(this.taskCount++, (Goal)new EntityAITransform(this));
        }
    }

    public float m_6113_() {
        return (float)this.ais.getWalkingSpeed() / 20.0f;
    }

    protected float m_6108_() {
        return this.ais.movementType == 2 ? 0.95f : 0.8f;
    }

    public float m_21692_(BlockPos pos) {
        if (this.ais.movementType == 2) {
            return this.m_20069_() ? 10.0f : 0.0f;
        }
        float weight = (float)this.m_9236_().m_7146_(pos) - 0.5f;
        if (this.m_9236_().m_8055_(pos).m_60804_((BlockGetter)this.m_9236_(), pos)) {
            weight += 10.0f;
        }
        return weight;
    }

    protected int m_7302_(int par1) {
        if (!this.stats.canDrown) {
            return par1;
        }
        return super.m_7302_(par1);
    }

    public MobType m_6336_() {
        return this.stats == null ? null : this.stats.creatureType;
    }

    public int m_8100_() {
        return 160;
    }

    public void m_8032_() {
        if (!this.m_6084_()) {
            return;
        }
        this.advanced.playSound(this.m_5448_() != null ? 1 : 0, this.m_6121_(), this.m_6100_());
    }

    protected void m_6677_(DamageSource source) {
        this.advanced.playSound(2, this.m_6121_(), this.m_6100_());
    }

    public SoundEvent m_5592_() {
        return null;
    }

    public float m_6100_() {
        if (this.advanced.disablePitch) {
            return 1.0f;
        }
        return super.m_6100_();
    }

    protected void m_7355_(BlockPos pos, BlockState state) {
        if (this.advanced.getSound(4) != null) {
            this.advanced.playSound(4, 0.15f, 1.0f);
        } else {
            super.m_7355_(pos, state);
        }
    }

    public ServerPlayer getFakeChatPlayer() {
        if (this.m_9236_().f_46443_) {
            return null;
        }
        EntityUtil.Copy((LivingEntity)this, (LivingEntity)ChatEventPlayer);
        EntityNPCInterface.ChatEventProfile.npc = this;
        ((EntityIMixin)ChatEventPlayer).setLevel((Level)((ServerLevel)this.m_9236_()));
        ChatEventPlayer.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        return ChatEventPlayer;
    }

    public void saySurrounding(Line line) {
        if (line == null) {
            return;
        }
        if (line.getShowText() && !line.getText().isEmpty()) {
            ServerChatEvent event = new ServerChatEvent(this.getFakeChatPlayer(), line.getText(), (Component)Component.m_237115_((String)line.getText().replace("%", "%%")));
            if (CustomNpcs.NpcSpeachTriggersChatEvent && (MinecraftForge.EVENT_BUS.post((Event)event) || event.getMessage() == null)) {
                return;
            }
            line.setText(event.getMessage().getString().replace("%%", "%"));
        }
        List inRange = this.m_9236_().m_45976_(Player.class, this.m_20191_().m_82377_(20.0, 20.0, 20.0));
        for (Player player : inRange) {
            this.say(player, line);
        }
    }

    public void say(Player player, Line line) {
        if (line == null || !this.canNpcSee((Entity)player)) {
            return;
        }
        if (!line.getSound().isEmpty()) {
            BlockPos pos = this.m_20183_();
            Packets.send((ServerPlayer)player, new PacketPlaySound(line.getSound(), pos, this.m_6121_(), this.m_6100_()));
        }
        if (!line.getText().isEmpty()) {
            Packets.send((ServerPlayer)player, new PacketChatBubble(this.m_19879_(), (Component)Component.m_237115_((String)line.getText()), line.getShowText()));
        }
    }

    public boolean m_6052_() {
        return true;
    }

    public void m_5997_(double d, double d1, double d2) {
        if (this.isWalking() && !this.isKilled()) {
            super.m_5997_(d, d1, d2);
        }
    }

    public void m_7378_(CompoundTag compound) {
        super.m_7378_(compound);
        this.npcVersion = compound.m_128451_("ModRev");
        VersionCompatibility.CheckNpcCompatibility(this, compound);
        this.display.readToNBT(compound);
        this.stats.readToNBT(compound);
        this.ais.readToNBT(compound);
        this.script.load(compound);
        this.timers.load(compound);
        this.advanced.readToNBT(compound);
        this.role.load(compound);
        this.job.load(compound);
        this.inventory.load(compound);
        this.transform.readToNBT(compound);
        this.killedtime = compound.m_128454_("KilledTime");
        this.totalTicksAlive = compound.m_128454_("TotalTicksAlive");
        this.linkedName = compound.m_128461_("LinkedNpcName");
        if (!this.isClientSide()) {
            LinkedNpcController.Instance.loadNpcData(this);
        }
        this.m_21051_(Attributes.f_22277_).m_22100_((double)CustomNpcs.NpcNavRange);
        this.updateAI = true;
    }

    public void m_7380_(CompoundTag compound) {
        super.m_7380_(compound);
        this.display.save(compound);
        this.stats.save(compound);
        this.ais.save(compound);
        this.script.save(compound);
        this.timers.save(compound);
        this.advanced.save(compound);
        this.role.save(compound);
        this.job.save(compound);
        this.inventory.save(compound);
        this.transform.save(compound);
        compound.m_128356_("KilledTime", this.killedtime);
        compound.m_128356_("TotalTicksAlive", this.totalTicksAlive);
        compound.m_128405_("ModRev", this.npcVersion);
        compound.m_128359_("LinkedNpcName", this.linkedName);
    }

    public EntityDimensions m_6972_(Pose poseIn) {
        EntityDimensions size = this.baseSize;
        if (this.currentAnimation == 2 || this.currentAnimation == 7 || this.f_20919_ > 0) {
            size = sizeSleep;
        } else if (this.m_20159_() || this.currentAnimation == 1) {
            size = this.baseSize.m_20390_(1.0f, 0.77f);
        }
        size = size.m_20388_((float)this.display.getSize() * 0.2f);
        if (this.display.getHitboxState() == 1 || this.isKilled() && this.stats.hideKilledBody) {
            size = EntityDimensions.m_20395_((float)1.0E-5f, (float)size.f_20378_);
        }
        if ((double)(size.f_20377_ / 2.0f) > this.m_9236_().getMaxEntityRadius()) {
            this.m_9236_().increaseMaxEntityRadius((double)(size.f_20377_ / 2.0f));
        }
        return size;
    }

    public void m_6153_() {
        if (this.stats.spawnCycle == 3 || this.stats.spawnCycle == 4) {
            super.m_6153_();
            return;
        }
        ++this.f_20919_;
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (!this.hasDied) {
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
        if (this.killedtime < System.currentTimeMillis() && (this.stats.spawnCycle == 0 || this.m_9236_().m_46461_() && this.stats.spawnCycle == 1 || !this.m_9236_().m_46461_() && this.stats.spawnCycle == 2)) {
            this.reset();
        }
    }

    public void reset() {
        boolean needsSync = this.hasDied;
        this.hasDied = false;
        this.m_146912_();
        this.f_20890_ = false;
        this.revive();
        this.wasKilled = false;
        this.m_6858_(false);
        this.m_21153_(this.m_21233_());
        this.f_19804_.m_135381_(Animation, (Object)0);
        this.f_19804_.m_135381_(Walking, (Object)false);
        this.f_19804_.m_135381_(IsDead, (Object)false);
        this.f_19804_.m_135381_(Interacting, (Object)false);
        this.interactingEntities.clear();
        this.combatHandler.reset();
        this.m_6710_(null);
        this.m_6703_(null);
        this.f_20919_ = 0;
        if (this.ais.returnToStart && !this.hasOwner() && !this.isClientSide() && !this.m_20159_()) {
            this.m_7678_(this.getStartXPos(), this.getStartYPos(), this.getStartZPos(), this.m_146908_(), this.m_146909_());
        }
        this.killedtime = 0L;
        this.m_20095_();
        this.m_21219_();
        this.m_7023_(Vec3.f_82478_);
        this.f_19787_ = 0.0f;
        this.f_19867_ = 0.0f;
        this.m_21573_().m_26573_();
        this.currentAnimation = 0;
        this.m_6210_();
        this.updateAI = true;
        this.ais.movingPos = 0;
        if (this.getOwner() != null) {
            this.getOwner().m_21335_(null);
        }
        this.bossInfo.m_8321_(this.display.getBossbar() == 1);
        this.job.reset();
        EventHooks.onNPCInit(this);
        if (needsSync) {
            List data = this.m_20088_().m_252804_();
            for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().m_6846_().m_11314_()) {
                if (!this.display.isVisibleTo((Player)player) && !player.m_5833_() && player.m_21205_().m_41720_() != CustomItems.wand) continue;
                Packets.send(player, new PacketUpdatePhysics((Entity)this));
                if (data != null) {
                    player.f_8906_.m_9829_((Packet)new ClientboundSetEntityDataPacket(this.m_19879_(), data));
                }
                Packets.send(player, new PacketNpcUpdate(this.m_19879_(), this.writeSpawnData()));
            }
        }
    }

    public void onCollide() {
        if (!this.m_6084_() || this.f_19797_ % 4 != 0 || this.m_9236_().f_46443_) {
            return;
        }
        AABB axisalignedbb = null;
        axisalignedbb = this.m_20202_() != null && this.m_20202_().m_6084_() ? this.m_20191_().m_82367_(this.m_20202_().m_20191_()).m_82377_(1.0, 0.0, 1.0) : this.m_20191_().m_82377_(1.0, 0.5, 1.0);
        List list = this.m_9236_().m_45976_(LivingEntity.class, axisalignedbb);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity)list.get(i);
            if (entity == this || !entity.m_6084_()) continue;
            EventHooks.onNPCCollide(this, entity);
        }
    }

    public void m_20221_(BlockPos pos) {
    }

    public void cloakUpdate() {
        this.prevChasingPosX = this.chasingPosX;
        this.prevChasingPosY = this.chasingPosY;
        this.prevChasingPosZ = this.chasingPosZ;
        double d0 = this.m_20185_() - this.chasingPosX;
        double d1 = this.m_20186_() - this.chasingPosY;
        double d2 = this.m_20189_() - this.chasingPosZ;
        double d3 = 10.0;
        if (d0 > 10.0) {
            this.prevChasingPosX = this.chasingPosX = this.m_20185_();
        }
        if (d2 > 10.0) {
            this.prevChasingPosZ = this.chasingPosZ = this.m_20189_();
        }
        if (d1 > 10.0) {
            this.prevChasingPosY = this.chasingPosY = this.m_20186_();
        }
        if (d0 < -10.0) {
            this.prevChasingPosX = this.chasingPosX = this.m_20185_();
        }
        if (d2 < -10.0) {
            this.prevChasingPosZ = this.chasingPosZ = this.m_20189_();
        }
        if (d1 < -10.0) {
            this.prevChasingPosY = this.chasingPosY = this.m_20186_();
        }
        this.chasingPosX += d0 * 0.25;
        this.chasingPosZ += d2 * 0.25;
        this.chasingPosY += d1 * 0.25;
    }

    public boolean m_6785_(double distanceToPlayer) {
        return this.stats != null && this.stats.spawnCycle == 4;
    }

    public ItemStack m_21205_() {
        IItemStack item = null;
        item = this.isAttacking() ? this.inventory.getRightHand() : (this.role.getType() == 6 ? ((RoleCompanion)this.role).getItemInHand() : (this.job.overrideMainHand ? this.job.getMainhand() : this.inventory.getRightHand()));
        return ItemStackWrapper.MCItem(item);
    }

    public ItemStack m_21206_() {
        IItemStack item = null;
        item = this.isAttacking() ? this.inventory.getLeftHand() : (this.job.overrideOffHand ? this.job.getOffhand() : this.inventory.getLeftHand());
        return ItemStackWrapper.MCItem(item);
    }

    public ItemStack m_6844_(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.m_21205_();
        }
        if (slot == EquipmentSlot.OFFHAND) {
            return this.m_21206_();
        }
        return ItemStackWrapper.MCItem(this.inventory.getArmor(3 - slot.m_20749_()));
    }

    public void m_8061_(EquipmentSlot slot, ItemStack item) {
        if (slot == EquipmentSlot.MAINHAND) {
            this.inventory.weapons.put(0, NpcAPI.Instance().getIItemStack(item));
        } else if (slot == EquipmentSlot.OFFHAND) {
            this.inventory.weapons.put(2, NpcAPI.Instance().getIItemStack(item));
        } else {
            this.inventory.armor.put(3 - slot.m_20749_(), NpcAPI.Instance().getIItemStack(item));
        }
    }

    public Iterable<ItemStack> m_6168_() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int i = 0; i < 4; ++i) {
            list.add(ItemStackWrapper.MCItem(this.inventory.armor.get(3 - i)));
        }
        return list;
    }

    public Iterable<ItemStack> m_20158_() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.add(ItemStackWrapper.MCItem(this.inventory.weapons.get(0)));
        list.add(ItemStackWrapper.MCItem(this.inventory.weapons.get(2)));
        return list;
    }

    protected void m_7472_(DamageSource source, int looting, boolean recentlyHitIn) {
    }

    protected void m_7625_(DamageSource damageSourceIn, boolean attackedRecently) {
    }

    public void m_6667_(DamageSource damagesource) {
        this.m_6858_(false);
        this.m_21573_().m_26573_();
        this.m_20095_();
        this.m_21219_();
        if (!this.isClientSide()) {
            this.advanced.playSound(3, this.m_6121_(), this.m_6100_());
            Entity attackingEntity = NoppesUtilServer.GetDamageSourcee(damagesource);
            NpcEvent.DiedEvent event = new NpcEvent.DiedEvent(this.wrappedNPC, damagesource, attackingEntity);
            event.droppedItems = this.inventory.getItemsRNG();
            event.expDropped = this.inventory.getExpRNG();
            event.line = this.advanced.getKilledLine();
            EventHooks.onNPCDied(this, event);
            this.bossInfo.m_8321_(false);
            this.inventory.dropStuff(event, attackingEntity, damagesource);
            if (event.line != null) {
                this.saySurrounding(Line.formatTarget((Line)event.line, attackingEntity instanceof LivingEntity ? (LivingEntity)attackingEntity : null));
            }
        }
        super.m_6667_(damagesource);
    }

    public void m_6457_(ServerPlayer player) {
        super.m_6457_(player);
        this.bossInfo.m_6543_(player);
    }

    public void m_6452_(ServerPlayer player) {
        super.m_6452_(player);
        this.bossInfo.m_6539_(player);
    }

    public void m_142687_(Entity.RemovalReason reason) {
        if (reason != Entity.RemovalReason.KILLED) {
            super.m_142687_(reason);
            return;
        }
        this.hasDied = true;
        this.m_20153_();
        this.m_8127_();
        if (this.m_9236_().f_46443_ || this.stats.spawnCycle == 3 || this.stats.spawnCycle == 4) {
            this.delete();
        } else {
            this.m_21153_(-1.0f);
            this.m_6858_(false);
            this.m_21573_().m_26573_();
            this.setCurrentAnimation(2);
            this.m_6210_();
            if (this.killedtime <= 0L) {
                this.killedtime = (long)(this.stats.respawnTime * 1000) + System.currentTimeMillis();
            }
            this.role.killed();
            this.job.killed();
        }
    }

    public void delete() {
        VisibilityController.instance.remove(this);
        this.role.delete();
        this.job.delete();
        super.m_142687_(Entity.RemovalReason.DISCARDED);
    }

    public float getStartXPos() {
        return (float)this.ais.startPos().m_123341_() + this.ais.bodyOffsetX / 10.0f;
    }

    public float getStartZPos() {
        return (float)this.ais.startPos().m_123343_() + this.ais.bodyOffsetZ / 10.0f;
    }

    public boolean isVeryNearAssignedPlace() {
        double xx = this.m_20185_() - (double)this.getStartXPos();
        double zz = this.m_20189_() - (double)this.getStartZPos();
        if (xx < -0.2 || xx > 0.2) {
            return false;
        }
        return !(zz < -0.2) && !(zz > 0.2);
    }

    public double getStartYPos() {
        if (this.startYPos < (double)this.m_9236_().m_141937_()) {
            return this.calculateStartYPos(this.ais.startPos());
        }
        return this.startYPos;
    }

    private double calculateStartYPos(BlockPos pos) {
        BlockPos startPos = this.ais.startPos();
        if (!this.m_9236_().m_46749_(this.ais.startPos())) {
            return startPos.m_123342_();
        }
        while (pos.m_123342_() >= this.m_9236_().m_141937_()) {
            BlockState state = this.m_9236_().m_8055_(pos);
            VoxelShape shape = state.m_60808_((BlockGetter)this.m_9236_(), pos);
            if (shape.m_83281_()) {
                pos = pos.m_7495_();
                continue;
            }
            AABB bb = shape.m_83215_().m_82338_(pos);
            if (this.ais.movementType == 2 && startPos.m_123342_() <= pos.m_123342_() && state.m_60713_(Blocks.f_49990_)) {
                pos = pos.m_7495_();
                continue;
            }
            return bb.f_82292_;
        }
        return this.m_9236_().m_141937_();
    }

    private BlockPos calculateTopPos(BlockPos pos) {
        BlockPos check = pos;
        while (check.m_123342_() > this.m_9236_().m_141937_()) {
            AABB bb;
            BlockState state = this.m_9236_().m_8055_(pos);
            VoxelShape shape = state.m_60808_((BlockGetter)this.m_9236_(), pos);
            if (!shape.m_83281_() && (bb = shape.m_83215_().m_82338_(pos)) != null) {
                return check;
            }
            check = check.m_7495_();
        }
        return pos;
    }

    public boolean isInRange(Entity entity, double range) {
        return this.isInRange(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), range);
    }

    public boolean isInRange(double posX, double posY, double posZ, double range) {
        double y = Math.abs(this.m_20186_() - posY);
        if (posY >= (double)this.m_9236_().m_141937_() && y > range) {
            return false;
        }
        double x = Math.abs(this.m_20185_() - posX);
        double z = Math.abs(this.m_20189_() - posZ);
        return x <= range && z <= range;
    }

    public void givePlayerItem(Player player, ItemStack item) {
        if (this.m_9236_().f_46443_) {
            return;
        }
        item = item.m_41777_();
        float f = 0.7f;
        double d = (double)(this.m_9236_().f_46441_.m_188501_() * f) + (double)(1.0f - f);
        double d1 = (double)(this.m_9236_().f_46441_.m_188501_() * f) + (double)(1.0f - f);
        double d2 = (double)(this.m_9236_().f_46441_.m_188501_() * f) + (double)(1.0f - f);
        ItemEntity entityitem = new ItemEntity(this.m_9236_(), this.m_20185_() + d, this.m_20186_() + d1, this.m_20189_() + d2, item);
        entityitem.m_32010_(2);
        this.m_9236_().m_7967_((Entity)entityitem);
        int i = item.m_41613_();
        if (player.m_150109_().m_36054_(item)) {
            this.m_9236_().m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2f, ((this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.7f + 1.0f) * 2.0f);
            player.m_7938_((Entity)entityitem, i);
            if (item.m_41613_() <= 0) {
                entityitem.m_146870_();
            }
        }
    }

    public boolean m_5803_() {
        return this.currentAnimation == 2 && !this.isAttacking();
    }

    public boolean isWalking() {
        return this.ais.getMovingType() != 0 || this.isAttacking() || this.isFollower() || (Boolean)this.f_19804_.m_135370_(Walking) != false;
    }

    public boolean m_6047_() {
        return this.currentAnimation == 4;
    }

    public void m_147240_(double strength, double ratioX, double ratioZ) {
        super.m_147240_(strength * (double)(2.0f - this.stats.resistances.knockback), ratioX, ratioZ);
    }

    public Faction getFaction() {
        Faction fac = FactionController.instance.getFaction((Integer)this.f_19804_.m_135370_(FactionData));
        if (fac == null) {
            return FactionController.instance.getFaction(FactionController.instance.getFirstFactionId());
        }
        return fac;
    }

    public boolean isClientSide() {
        return this.m_9236_() == null || this.m_9236_().f_46443_;
    }

    public void setFaction(int id) {
        if (id < 0 || this.isClientSide()) {
            return;
        }
        this.f_19804_.m_135381_(FactionData, (Object)id);
    }

    public boolean m_7301_(MobEffectInstance effect) {
        if (this.stats.potionImmune) {
            return false;
        }
        if (this.m_6336_() == MobType.f_21642_ && effect.m_19544_() == MobEffects.f_19614_) {
            return false;
        }
        return super.m_7301_(effect);
    }

    public boolean isAttacking() {
        return (Boolean)this.f_19804_.m_135370_(Attacking);
    }

    public boolean isKilled() {
        return this.m_213877_() || (Boolean)this.f_19804_.m_135370_(IsDead) != false;
    }

    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.m_130079_(this.writeSpawnData());
    }

    public CompoundTag writeSpawnData() {
        CompoundTag bard;
        CompoundTag compound = new CompoundTag();
        this.display.save(compound);
        compound.m_128405_("MaxHealth", this.stats.maxHealth);
        compound.m_128365_("Armor", (Tag)NBTTags.nbtIItemStackMap(this.inventory.armor));
        compound.m_128365_("Weapons", (Tag)NBTTags.nbtIItemStackMap(this.inventory.weapons));
        compound.m_128405_("Speed", this.ais.getWalkingSpeed());
        compound.m_128379_("MountControl", this.ais.mountControl);
        compound.m_128379_("DeadBody", this.stats.hideKilledBody);
        compound.m_128405_("StandingState", this.ais.getStandingType());
        compound.m_128405_("MovingState", this.ais.getMovingType());
        compound.m_128405_("Orientation", this.ais.orientation);
        compound.m_128350_("PositionXOffset", this.ais.bodyOffsetX);
        compound.m_128350_("PositionYOffset", this.ais.bodyOffsetY);
        compound.m_128350_("PositionZOffset", this.ais.bodyOffsetZ);
        compound.m_128405_("Role", this.role.getType());
        compound.m_128405_("Job", this.job.getType());
        if (this.job.getType() == 1) {
            bard = new CompoundTag();
            this.job.save(bard);
            compound.m_128365_("Bard", (Tag)bard);
        }
        if (this.job.getType() == 9) {
            bard = new CompoundTag();
            this.job.save(bard);
            compound.m_128365_("Puppet", (Tag)bard);
        }
        if (this.role.getType() == 6) {
            bard = new CompoundTag();
            this.role.save(bard);
            compound.m_128365_("Companion", (Tag)bard);
        }
        if (this instanceof EntityCustomNpc) {
            compound.m_128365_("ModelData", (Tag)((EntityCustomNpc)this).modelData.save());
        }
        return compound;
    }

    public void readSpawnData(FriendlyByteBuf buf) {
        this.readSpawnData(buf.m_130260_());
    }

    public void readSpawnData(CompoundTag compound) {
        CompoundTag puppet;
        this.stats.setMaxHealth(compound.m_128451_("MaxHealth"));
        this.ais.setWalkingSpeed(compound.m_128451_("Speed"));
        this.stats.hideKilledBody = compound.m_128471_("DeadBody");
        this.ais.setStandingType(compound.m_128451_("StandingState"));
        this.ais.mountControl = compound.m_128471_("MountControl");
        this.ais.setMovingType(compound.m_128451_("MovingState"));
        this.ais.orientation = compound.m_128451_("Orientation");
        this.ais.bodyOffsetX = compound.m_128457_("PositionXOffset");
        this.ais.bodyOffsetY = compound.m_128457_("PositionYOffset");
        this.ais.bodyOffsetZ = compound.m_128457_("PositionZOffset");
        this.inventory.armor = NBTTags.getIItemStackMap(compound.m_128437_("Armor", 10));
        this.inventory.weapons = NBTTags.getIItemStackMap(compound.m_128437_("Weapons", 10));
        this.advanced.setRole(compound.m_128451_("Role"));
        this.advanced.setJob(compound.m_128451_("Job"));
        if (this.job.getType() == 1) {
            CompoundTag bard = compound.m_128469_("Bard");
            this.job.load(bard);
        }
        if (this.job.getType() == 9) {
            puppet = compound.m_128469_("Puppet");
            this.job.load(puppet);
        }
        if (this.role.getType() == 6) {
            puppet = compound.m_128469_("Companion");
            this.role.load(puppet);
        }
        if (this instanceof EntityCustomNpc) {
            ((EntityCustomNpc)this).modelData.load(compound.m_128469_("ModelData"));
        }
        this.display.readToNBT(compound);
        this.m_6210_();
    }

    public CommandSourceStack m_20203_() {
        if (this.m_9236_().f_46443_) {
            return super.m_20203_();
        }
        EntityUtil.Copy((LivingEntity)this, (LivingEntity)CommandPlayer);
        ((EntityIMixin)CommandPlayer).setLevel((Level)((ServerLevel)this.m_9236_()));
        CommandPlayer.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        return new CommandSourceStack((CommandSource)this, this.m_20182_(), this.m_20155_(), this.m_9236_() instanceof ServerLevel ? (ServerLevel)this.m_9236_() : null, this.m_8088_(), this.m_7755_().getString(), this.m_5446_(), this.m_9236_().m_7654_(), (Entity)this);
    }

    public Component m_7755_() {
        return Component.m_237115_((String)this.display.getName());
    }

    public void setImmuneToFire(boolean immuneToFire) {
        this.stats.immuneToFire = immuneToFire;
    }

    public boolean m_5825_() {
        return this.stats.immuneToFire;
    }

    public boolean m_142535_(float distance, float modifier, DamageSource source) {
        if (!this.stats.noFallDamage) {
            return super.m_142535_(distance, modifier, source);
        }
        return false;
    }

    public void m_7601_(BlockState state, Vec3 motionMultiplierIn) {
        if (state != null && !state.m_60713_(Blocks.f_50033_) || !this.stats.ignoreCobweb) {
            super.m_7601_(state, motionMultiplierIn);
        }
    }

    public boolean m_5829_() {
        return !this.isKilled() && this.display.getHitboxState() == 2;
    }

    protected void m_6138_() {
        if (this.display.getHitboxState() != 0) {
            return;
        }
        super.m_6138_();
    }

    public boolean m_6094_() {
        return this.isWalking() && !this.isKilled();
    }

    public PushReaction m_7752_() {
        return this.display.getHitboxState() == 0 ? super.m_7752_() : PushReaction.IGNORE;
    }

    public EntityAIRangedAttack getRangedTask() {
        return this.aiRange;
    }

    public String getRoleData() {
        return (String)this.f_19804_.m_135370_(RoleData);
    }

    public void setRoleData(String s) {
        this.f_19804_.m_135381_(RoleData, (Object)s);
    }

    public String getJobData() {
        return (String)this.f_19804_.m_135370_(RoleData);
    }

    public void setJobData(String s) {
        this.f_19804_.m_135381_(RoleData, (Object)s);
    }

    public Level m_20193_() {
        return this.m_9236_();
    }

    public boolean m_20177_(Player player) {
        return this.display.getVisible() == 1 && player.m_21205_().m_41720_() != CustomItems.wand && !this.display.availability.hasOptions();
    }

    public boolean m_20145_() {
        return this.display.getVisible() != 0 && !this.display.availability.hasOptions();
    }

    public void setInvisible(ServerPlayer playerMP) {
        if (this.tracking.contains(playerMP.m_19879_())) {
            this.tracking.remove(playerMP.m_19879_());
            Packets.send(playerMP, new PacketNpcVisibleFalse(this.m_19879_()));
        }
    }

    public void setVisible(ServerPlayer playerMP) {
        if (!this.tracking.contains(playerMP.m_19879_())) {
            this.tracking.add(playerMP.m_19879_());
            Packets.send(playerMP, new PacketNpcVisibleTrue((Entity)this));
            List data = this.m_20088_().m_252804_();
            if (data != null) {
                playerMP.f_8906_.m_9829_((Packet)new ClientboundSetEntityDataPacket(this.m_19879_(), data));
            }
        }
        Packets.send(playerMP, new PacketNpcUpdate(this.m_19879_(), this.writeSpawnData()));
        MarkData.get((LivingEntity)this).syncClients();
    }

    public void setCurrentAnimation(int animation) {
        this.currentAnimation = animation;
        this.f_19804_.m_135381_(Animation, (Object)animation);
    }

    public boolean canNpcSee(Entity entity) {
        return this.m_21574_().m_148306_(entity);
    }

    public boolean isFollower() {
        if (this.advanced.scenes.getOwner() != null) {
            return true;
        }
        return this.role.isFollowing() || this.job.isFollowing();
    }

    public LivingEntity getOwner() {
        if (this.advanced.scenes.getOwner() != null) {
            return this.advanced.scenes.getOwner();
        }
        if (this.role.getType() == 2 && this.role instanceof RoleFollower) {
            return ((RoleFollower)this.role).owner;
        }
        if (this.role.getType() == 6 && this.role instanceof RoleCompanion) {
            return ((RoleCompanion)this.role).owner;
        }
        if (this.job.getType() == 5 && this.job instanceof JobFollower) {
            return ((JobFollower)this.job).following;
        }
        return null;
    }

    public boolean hasOwner() {
        if (this.advanced.scenes.getOwner() != null) {
            return true;
        }
        return this.role.getType() == 2 && ((RoleFollower)this.role).hasOwner() || this.role.getType() == 6 && ((RoleCompanion)this.role).hasOwner() || this.job.getType() == 5 && ((JobFollower)this.job).hasOwner();
    }

    public int followRange() {
        if (this.advanced.scenes.getOwner() != null) {
            return 4;
        }
        if (this.role.getType() == 2 && this.role.isFollowing()) {
            return 4;
        }
        if (this.role.getType() == 6 && this.role.isFollowing()) {
            return 4;
        }
        if (this.job.getType() == 5 && this.job.isFollowing()) {
            return 4;
        }
        return 15;
    }

    protected float m_21161_(DamageSource source, float damage) {
        if (this.role.getType() == 6) {
            damage = ((RoleCompanion)this.role).getDamageAfterArmorAbsorb(source, damage);
        }
        return damage;
    }

    public boolean m_7307_(Entity entity) {
        if (!this.isClientSide()) {
            if (entity instanceof Player && this.getFaction().isFriendlyToPlayer((Player)entity)) {
                return true;
            }
            if (entity == this.getOwner()) {
                return true;
            }
            if (entity instanceof EntityNPCInterface && ((EntityNPCInterface)entity).faction.id == this.faction.id) {
                return true;
            }
        }
        return super.m_7307_(entity);
    }

    public void setDataWatcher(SynchedEntityData entityData) {
        ArrayList<SynchedEntityData.DataValue> list = new ArrayList<SynchedEntityData.DataValue>();
        for (SynchedEntityData.DataItem<?> entry : ((ISynchedEntityData)entityData).getAll()) {
            if (!(entry.m_135403_() instanceof SynchedEntityData.DataValue)) continue;
            list.add((SynchedEntityData.DataValue)entry.m_135403_());
        }
        this.f_19804_.m_135356_(list);
    }

    public void m_7023_(Vec3 travelVector) {
        BlockPos pos = this.m_20183_();
        if (this.m_6084_() && this.m_20160_() && this.ais.mountControl && this.m_6688_() != null) {
            LivingEntity livingentity = this.m_6688_();
            this.m_146922_(livingentity.m_146908_());
            this.f_19859_ = this.m_146908_();
            this.m_146926_(livingentity.m_146909_() * 0.5f);
            this.m_19915_(this.m_146908_(), this.m_146909_());
            this.f_20885_ = this.f_20883_ = this.m_146908_();
            float f = livingentity.f_20900_ * 0.5f;
            float f1 = livingentity.f_20902_;
            if (f1 <= 0.0f) {
                f1 *= 0.25f;
            }
            this.m_274367_(1.1f);
            if (this.canFly()) {
                this.m_20242_(true);
                this.f_19789_ = 0.0f;
                super.m_7023_(new Vec3((double)(f * 2.0f), -Math.sin(Math.toRadians(this.m_146909_())) * 2.0, (double)(f1 * 2.0f)));
            } else {
                this.m_20242_(false);
                super.m_7023_(new Vec3((double)f, travelVector.f_82480_, (double)f1));
            }
        } else {
            this.m_274367_(0.5f);
            super.m_7023_(travelVector);
        }
        if (this.role.getType() == 6 && !this.isClientSide()) {
            BlockPos delta = this.m_20183_().m_121996_((Vec3i)pos);
            ((RoleCompanion)this.role).addMovementStat(delta.m_123341_(), delta.m_123342_(), delta.m_123343_());
        }
    }

    public boolean m_6573_(Player player) {
        return false;
    }

    public boolean m_21523_() {
        return false;
    }

    public boolean nearPosition(BlockPos pos) {
        BlockPos npcpos = this.m_20183_();
        float x = npcpos.m_123341_() - pos.m_123341_();
        float z = npcpos.m_123343_() - pos.m_123343_();
        float y = npcpos.m_123342_() - pos.m_123342_();
        float height = Mth.m_14167_((float)(this.m_20206_() + 1.0f)) * Mth.m_14167_((float)(this.m_20206_() + 1.0f));
        return (double)(x * x + z * z) < 2.5 && (double)(y * y) < (double)height + 2.5;
    }

    public void tpTo(LivingEntity owner) {
        if (owner == null) {
            return;
        }
        Direction facing = owner.m_6350_().m_122424_();
        BlockPos pos = new BlockPos((int)owner.m_20185_(), (int)owner.m_20191_().f_82289_, (int)owner.m_20189_());
        pos = pos.m_7918_(facing.m_122429_(), 0, facing.m_122431_());
        pos = this.calculateTopPos(pos);
        block0: for (int i = -1; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                BlockPos check = facing.m_122429_() == 0 ? pos.m_7918_(i, 0, j * facing.m_122431_()) : pos.m_7918_(j * facing.m_122429_(), 0, i);
                check = this.calculateTopPos(check);
                if (this.m_9236_().m_8055_(check).m_60804_((BlockGetter)this.m_9236_(), check) || this.m_9236_().m_8055_(check.m_7494_()).m_60804_((BlockGetter)this.m_9236_(), check.m_7494_())) continue;
                this.m_7678_((float)check.m_123341_() + 0.5f, check.m_123342_(), (float)check.m_123343_() + 0.5f, this.m_146908_(), this.m_146909_());
                this.m_21573_().m_26573_();
                continue block0;
            }
        }
    }

    public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
        return true;
    }

    public void m_7350_(EntityDataAccessor<?> para) {
        super.m_7350_(para);
        if (Animation.equals(para)) {
            this.m_6210_();
        }
    }

    protected void m_8022_() {
        boolean flag1 = !(this.m_20202_() instanceof Boat);
        this.f_21345_.m_25360_(Goal.Flag.MOVE, true);
        this.f_21345_.m_25360_(Goal.Flag.JUMP, flag1);
        this.f_21345_.m_25360_(Goal.Flag.LOOK, true);
    }

    public void m_6043_() {
        double range;
        double d0;
        Player entity;
        super.m_6043_();
        if (this.m_21216_() != 0 && this.m_9236_() != null && (entity = this.m_9236_().m_45930_((Entity)this, -1.0)) != null && (d0 = entity.m_20280_((Entity)this)) < (range = (double)this.ais.activeRange * (double)this.ais.activeRange)) {
            this.f_20891_ = 0;
        }
    }

    public Packet<ClientGamePacketListener> m_5654_() {
        if (!CustomNpcs.EnableInvisibleNpcs || CustomNpcs.InvisibilityAlgorithm != 0) {
            return NetworkHooks.getEntitySpawningPacket((Entity)this);
        }
        return super.m_5654_();
    }

    public void startFallFlying() {
        this.m_20115_(7, true);
    }

    public void stopFallFlying() {
        this.m_20115_(7, true);
        this.m_20115_(7, false);
    }

    @Deprecated
    public void jumpInFluid(FluidType type) {
        this.m_20256_(this.m_20184_().m_82520_(0.0, (double)0.04f, 0.0));
    }

    @Deprecated
    public void sinkInFluid(FluidType type) {
        this.m_20256_(this.m_20184_().m_82520_(0.0, (double)0.04f, 0.0));
    }

    static {
        sizeSleep = new EntityDimensions(0.8f, 0.4f, false);
    }
}

