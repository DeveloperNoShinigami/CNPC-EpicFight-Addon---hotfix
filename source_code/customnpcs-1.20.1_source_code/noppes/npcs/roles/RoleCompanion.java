/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  net.minecraft.core.particles.ItemParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.EquipmentSlot$Type
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorMaterials
 *  net.minecraft.world.item.BowItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.SwordItem
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.roles;

import com.google.common.collect.Multimap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.constants.EnumCompanionJobs;
import noppes.npcs.constants.EnumCompanionStage;
import noppes.npcs.constants.EnumCompanionTalent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumParts;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.ArmorMaterialsMixin;
import noppes.npcs.roles.RoleInterface;
import noppes.npcs.roles.companion.CompanionFarmer;
import noppes.npcs.roles.companion.CompanionFoodStats;
import noppes.npcs.roles.companion.CompanionGuard;
import noppes.npcs.roles.companion.CompanionJobInterface;
import noppes.npcs.roles.companion.CompanionTrader;

public class RoleCompanion
extends RoleInterface {
    private static final CompanionJobInterface NONE = new CompanionJobInterface(){

        @Override
        public CompoundTag getNBT() {
            return null;
        }

        @Override
        public void setNBT(CompoundTag compound) {
        }

        @Override
        public EnumCompanionJobs getType() {
            return EnumCompanionJobs.NONE;
        }
    };
    public NpcMiscInventory inventory;
    public String uuid = "";
    public String ownerName = "";
    public Map<EnumCompanionTalent, Integer> talents = new TreeMap<EnumCompanionTalent, Integer>();
    public boolean canAge = true;
    public long ticksActive = 0L;
    public EnumCompanionStage stage = EnumCompanionStage.FULLGROWN;
    public Player owner = null;
    public int companionID;
    public CompanionJobInterface companionJobInterface = NONE;
    public boolean hasInv = true;
    public boolean defendOwner = true;
    public CompanionFoodStats foodstats = new CompanionFoodStats();
    private int eatingTicks = 20;
    private IItemStack eating = null;
    private int eatingDelay = 0;
    public int currentExp = 0;

    public RoleCompanion(EntityNPCInterface npc) {
        super(npc);
        this.inventory = new NpcMiscInventory(12);
    }

    @Override
    public boolean aiShouldExecute() {
        Player prev = this.owner;
        this.owner = this.getOwner();
        if (this.companionJobInterface.isSelfSufficient()) {
            return true;
        }
        if (this.owner == null && !this.uuid.isEmpty()) {
            this.npc.m_146870_();
        } else if (prev != this.owner && this.owner != null) {
            this.ownerName = this.owner.m_5446_().getString();
            PlayerData data = PlayerData.get(this.owner);
            if (data.companionID != this.companionID) {
                this.npc.m_146870_();
            }
        }
        return this.owner != null;
    }

    @Override
    public void aiUpdateTask() {
        if (this.owner != null && !this.companionJobInterface.isSelfSufficient()) {
            this.foodstats.onUpdate(this.npc);
        }
        if (this.foodstats.getFoodLevel() >= 18) {
            this.npc.stats.healthRegen = 0;
            this.npc.stats.combatRegen = 0;
        }
        if (this.foodstats.needFood() && this.isSitting()) {
            if (this.eatingDelay > 0) {
                --this.eatingDelay;
                return;
            }
            IItemStack prev = this.eating;
            this.eating = this.getFood();
            if (prev != null && this.eating == null) {
                this.npc.setRoleData("");
            }
            if (prev == null && this.eating != null) {
                this.npc.setRoleData("eating");
                this.eatingTicks = 20;
            }
            if (this.isEating()) {
                this.doEating();
            }
        } else if (this.eating != null && !this.isSitting()) {
            this.eating = null;
            this.eatingDelay = 20;
            this.npc.setRoleData("");
        }
        ++this.ticksActive;
        if (this.canAge && this.stage != EnumCompanionStage.FULLGROWN) {
            if (this.stage == EnumCompanionStage.BABY && this.ticksActive > (long)EnumCompanionStage.CHILD.matureAge) {
                this.matureTo(EnumCompanionStage.CHILD);
            } else if (this.stage == EnumCompanionStage.CHILD && this.ticksActive > (long)EnumCompanionStage.TEEN.matureAge) {
                this.matureTo(EnumCompanionStage.TEEN);
            } else if (this.stage == EnumCompanionStage.TEEN && this.ticksActive > (long)EnumCompanionStage.ADULT.matureAge) {
                this.matureTo(EnumCompanionStage.ADULT);
            } else if (this.stage == EnumCompanionStage.ADULT && this.ticksActive > (long)EnumCompanionStage.FULLGROWN.matureAge) {
                this.matureTo(EnumCompanionStage.FULLGROWN);
            }
        }
    }

    @Override
    public void clientUpdate() {
        if (this.npc.getRoleData().equals("eating")) {
            this.eating = this.getFood();
            if (this.isEating()) {
                this.doEating();
            }
        } else if (this.eating != null) {
            this.eating = null;
        }
    }

    private void doEating() {
        if (this.eating == null || this.eating.isEmpty()) {
            return;
        }
        ItemStack eating = this.eating.getMCItemStack();
        if (this.npc.m_9236_().f_46443_) {
            RandomSource rand = this.npc.m_217043_();
            for (int j = 0; j < 2; ++j) {
                Vec3 vec3 = new Vec3(((double)rand.m_188501_() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                vec3.m_82496_(-this.npc.m_146909_() * (float)Math.PI / 180.0f);
                vec3.m_82524_(-this.npc.f_20883_ * (float)Math.PI / 180.0f);
                Vec3 vec31 = new Vec3(((double)rand.m_188501_() - 0.5) * 0.3, (double)(-rand.m_188501_()) * 0.6 - 0.3, (double)(this.npc.m_20205_() / 2.0f) + 0.1);
                vec31.m_82496_(-this.npc.m_146909_() * (float)Math.PI / 180.0f);
                vec31.m_82524_(-this.npc.f_20883_ * (float)Math.PI / 180.0f);
                vec31 = vec31.m_82520_(this.npc.m_20185_(), this.npc.m_20186_() + (double)this.npc.m_20206_() + 0.1, this.npc.m_20189_());
                String s = "iconcrack_" + Item.m_41393_((Item)eating.m_41720_());
                this.npc.m_9236_().m_7106_((ParticleOptions)new ItemParticleOption(ParticleTypes.f_123752_, eating), vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, vec3.f_82479_, vec3.f_82480_ + 0.05, vec3.f_82481_);
            }
        } else {
            --this.eatingTicks;
            if (this.eatingTicks <= 0) {
                FoodProperties food = eating.m_41720_().getFoodProperties(eating, (LivingEntity)this.npc);
                if (this.inventory.removeItem(eating, 1)) {
                    this.foodstats.onFoodEaten(food, eating);
                    this.npc.m_5496_(SoundEvents.f_12321_, 0.5f, this.npc.m_217043_().m_188501_() * 0.1f + 0.9f);
                }
                this.eatingDelay = 20;
                this.npc.setRoleData("");
                this.eating = null;
            } else if (this.eatingTicks > 3 && this.eatingTicks % 2 == 0) {
                RandomSource rand = this.npc.m_217043_();
                this.npc.m_5496_(SoundEvents.f_11912_, 0.5f + 0.5f * (float)rand.m_188503_(2), (rand.m_188501_() - rand.m_188501_()) * 0.2f + 1.0f);
            }
        }
    }

    public void matureTo(EnumCompanionStage stage) {
        this.stage = stage;
        EntityCustomNpc npc = (EntityCustomNpc)this.npc;
        npc.ais.animationType = stage.animation;
        if (stage == EnumCompanionStage.BABY) {
            npc.modelData.getPartConfig(EnumParts.ARM_LEFT).setScale(0.5f, 0.5f, 0.5f);
            npc.modelData.getPartConfig(EnumParts.LEG_LEFT).setScale(0.5f, 0.5f, 0.5f);
            npc.modelData.getPartConfig(EnumParts.BODY).setScale(0.5f, 0.5f, 0.5f);
            npc.modelData.getPartConfig(EnumParts.HEAD).setScale(0.7f, 0.7f, 0.7f);
            npc.ais.onAttack = 1;
            npc.ais.setWalkingSpeed(3);
            if (!this.talents.containsKey((Object)EnumCompanionTalent.INVENTORY)) {
                this.talents.put(EnumCompanionTalent.INVENTORY, 0);
            }
        }
        if (stage == EnumCompanionStage.CHILD) {
            npc.modelData.getPartConfig(EnumParts.ARM_LEFT).setScale(0.6f, 0.6f, 0.6f);
            npc.modelData.getPartConfig(EnumParts.LEG_LEFT).setScale(0.6f, 0.6f, 0.6f);
            npc.modelData.getPartConfig(EnumParts.BODY).setScale(0.6f, 0.6f, 0.6f);
            npc.modelData.getPartConfig(EnumParts.HEAD).setScale(0.8f, 0.8f, 0.8f);
            npc.ais.onAttack = 0;
            npc.ais.setWalkingSpeed(4);
            if (!this.talents.containsKey((Object)EnumCompanionTalent.SWORD)) {
                this.talents.put(EnumCompanionTalent.SWORD, 0);
            }
        }
        if (stage == EnumCompanionStage.TEEN) {
            npc.modelData.getPartConfig(EnumParts.ARM_LEFT).setScale(0.8f, 0.8f, 0.8f);
            npc.modelData.getPartConfig(EnumParts.LEG_LEFT).setScale(0.8f, 0.8f, 0.8f);
            npc.modelData.getPartConfig(EnumParts.BODY).setScale(0.8f, 0.8f, 0.8f);
            npc.modelData.getPartConfig(EnumParts.HEAD).setScale(0.9f, 0.9f, 0.9f);
            npc.ais.onAttack = 0;
            npc.ais.setWalkingSpeed(5);
            if (!this.talents.containsKey((Object)EnumCompanionTalent.ARMOR)) {
                this.talents.put(EnumCompanionTalent.ARMOR, 0);
            }
        }
        if (stage == EnumCompanionStage.ADULT || stage == EnumCompanionStage.FULLGROWN) {
            npc.modelData.getPartConfig(EnumParts.ARM_LEFT).setScale(1.0f, 1.0f, 1.0f);
            npc.modelData.getPartConfig(EnumParts.LEG_LEFT).setScale(1.0f, 1.0f, 1.0f);
            npc.modelData.getPartConfig(EnumParts.BODY).setScale(1.0f, 1.0f, 1.0f);
            npc.modelData.getPartConfig(EnumParts.HEAD).setScale(1.0f, 1.0f, 1.0f);
            npc.ais.onAttack = 0;
            npc.ais.setWalkingSpeed(5);
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.m_128365_("CompanionInventory", (Tag)this.inventory.getToNBT());
        compound.m_128359_("CompanionOwner", this.uuid);
        compound.m_128359_("CompanionOwnerName", this.ownerName);
        compound.m_128405_("CompanionID", this.companionID);
        compound.m_128405_("CompanionStage", this.stage.ordinal());
        compound.m_128405_("CompanionExp", this.currentExp);
        compound.m_128379_("CompanionCanAge", this.canAge);
        compound.m_128356_("CompanionAge", this.ticksActive);
        compound.m_128379_("CompanionHasInv", this.hasInv);
        compound.m_128379_("CompanionDefendOwner", this.defendOwner);
        this.foodstats.writeNBT(compound);
        compound.m_128405_("CompanionJob", this.companionJobInterface.getType().ordinal());
        if (this.companionJobInterface.getType() != EnumCompanionJobs.NONE) {
            compound.m_128365_("CompanionJobData", (Tag)this.companionJobInterface.getNBT());
        }
        ListTag list = new ListTag();
        for (EnumCompanionTalent talent : this.talents.keySet()) {
            CompoundTag c = new CompoundTag();
            c.m_128405_("Talent", talent.ordinal());
            c.m_128405_("Exp", this.talents.get((Object)talent).intValue());
            list.add((Object)c);
        }
        compound.m_128365_("CompanionTalents", (Tag)list);
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        this.inventory.setFromNBT(compound.m_128469_("CompanionInventory"));
        this.uuid = compound.m_128461_("CompanionOwner");
        this.ownerName = compound.m_128461_("CompanionOwnerName");
        this.companionID = compound.m_128451_("CompanionID");
        this.stage = EnumCompanionStage.values()[compound.m_128451_("CompanionStage")];
        this.currentExp = compound.m_128451_("CompanionExp");
        this.canAge = compound.m_128471_("CompanionCanAge");
        this.ticksActive = compound.m_128454_("CompanionAge");
        this.hasInv = compound.m_128471_("CompanionHasInv");
        this.defendOwner = compound.m_128471_("CompanionDefendOwner");
        this.foodstats.readNBT(compound);
        ListTag list = compound.m_128437_("CompanionTalents", 10);
        TreeMap<EnumCompanionTalent, Integer> talents = new TreeMap<EnumCompanionTalent, Integer>();
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag c = list.m_128728_(i);
            EnumCompanionTalent talent = EnumCompanionTalent.values()[c.m_128451_("Talent")];
            talents.put(talent, c.m_128451_("Exp"));
        }
        this.talents = talents;
        this.setJob(compound.m_128451_("CompanionJob"));
        this.companionJobInterface.setNBT(compound.m_128469_("CompanionJobData"));
        this.setStats();
    }

    private void setJob(int i) {
        EnumCompanionJobs companionJob = EnumCompanionJobs.values()[i];
        this.companionJobInterface = companionJob == EnumCompanionJobs.SHOP ? new CompanionTrader() : (companionJob == EnumCompanionJobs.FARMER ? new CompanionFarmer() : (companionJob == EnumCompanionJobs.GUARD ? new CompanionGuard() : NONE));
        this.companionJobInterface.npc = this.npc;
    }

    @Override
    public void interact(Player player) {
        this.interact(player, false);
    }

    public void interact(Player player, boolean openGui) {
        if (player != null && this.companionJobInterface.getType() == EnumCompanionJobs.SHOP) {
            ((CompanionTrader)this.companionJobInterface).interact(player);
        }
        if (player != this.owner || !this.npc.m_6084_() || this.npc.isAttacking()) {
            return;
        }
        if (player.m_6047_() || openGui) {
            this.openGui(player);
        } else {
            this.setSitting(!this.isSitting());
        }
    }

    public int getTotalLevel() {
        int level = 0;
        for (EnumCompanionTalent talent : this.talents.keySet()) {
            level += this.getTalentLevel(talent);
        }
        return level;
    }

    public int getMaxExp() {
        return 500 + this.getTotalLevel() * 200;
    }

    public void addExp(int exp) {
        if (this.canAddExp(exp)) {
            this.currentExp += exp;
        }
    }

    public boolean canAddExp(int exp) {
        int newExp = this.currentExp + exp;
        return newExp >= 0 && newExp < this.getMaxExp();
    }

    public void gainExp(int chance) {
        if (this.npc.m_217043_().m_188503_(chance) == 0) {
            this.addExp(1);
        }
    }

    private void openGui(Player player) {
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.Companion, this.npc);
    }

    public Player getOwner() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            return null;
        }
        try {
            UUID id = UUID.fromString(this.uuid);
            if (id != null) {
                return NoppesUtilServer.getPlayer(this.npc.m_20194_(), id);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return null;
    }

    public void setOwner(Player player) {
        this.uuid = player.m_20148_().toString();
    }

    public boolean hasTalent(EnumCompanionTalent talent) {
        return this.getTalentLevel(talent) > 0;
    }

    public int getTalentLevel(EnumCompanionTalent talent) {
        if (!this.talents.containsKey((Object)talent)) {
            return 0;
        }
        int exp = this.talents.get((Object)talent);
        if (exp >= 5000) {
            return 5;
        }
        if (exp >= 3000) {
            return 4;
        }
        if (exp >= 1700) {
            return 3;
        }
        if (exp >= 1000) {
            return 2;
        }
        if (exp >= 400) {
            return 1;
        }
        return 0;
    }

    public Integer getNextLevel(EnumCompanionTalent talent) {
        if (!this.talents.containsKey((Object)talent)) {
            return 0;
        }
        int exp = this.talents.get((Object)talent);
        if (exp < 400) {
            return 400;
        }
        if (exp < 1000) {
            return 700;
        }
        if (exp < 1700) {
            return 1700;
        }
        if (exp < 3000) {
            return 3000;
        }
        return 5000;
    }

    public void levelSword() {
        if (!this.talents.containsKey((Object)EnumCompanionTalent.SWORD)) {
            return;
        }
    }

    public void levelTalent(EnumCompanionTalent talent, int exp) {
        if (!this.talents.containsKey((Object)EnumCompanionTalent.SWORD)) {
            return;
        }
        this.talents.put(talent, exp + this.talents.get((Object)talent));
    }

    public int getExp(EnumCompanionTalent talent) {
        if (this.talents.containsKey((Object)talent)) {
            return this.talents.get((Object)talent);
        }
        return -1;
    }

    public void setExp(EnumCompanionTalent talent, int exp) {
        this.talents.put(talent, exp);
    }

    private boolean isWeapon(ItemStack item) {
        if (item == null || item.m_41720_() == null) {
            return false;
        }
        return item.m_41720_() instanceof SwordItem || item.m_41720_() instanceof BowItem || item.m_41720_() == Item.m_41439_((Block)Blocks.f_50652_);
    }

    public boolean canWearWeapon(IItemStack stack) {
        if (stack == null || stack.getMCItemStack().m_41720_() == null) {
            return false;
        }
        Item item = stack.getMCItemStack().m_41720_();
        if (item instanceof SwordItem) {
            return this.canWearSword(stack);
        }
        if (item instanceof BowItem) {
            return this.getTalentLevel(EnumCompanionTalent.RANGED) > 2;
        }
        if (item == Item.m_41439_((Block)Blocks.f_50652_)) {
            return this.getTalentLevel(EnumCompanionTalent.RANGED) > 1;
        }
        return false;
    }

    public boolean canWearArmor(ItemStack item) {
        int level = this.getTalentLevel(EnumCompanionTalent.ARMOR);
        if (item == null || !(item.m_41720_() instanceof ArmorItem) || level <= 0) {
            return false;
        }
        if (level >= 5) {
            return true;
        }
        ArmorItem armor = (ArmorItem)item.m_41720_();
        int reduction = 1;
        if (armor.m_40401_() instanceof ArmorMaterials) {
            reduction = ((ArmorMaterialsMixin)armor.m_40401_()).durabilityMultiplier();
        }
        if (reduction <= 5 && level >= 1) {
            return true;
        }
        if (reduction <= 7 && level >= 2) {
            return true;
        }
        if (reduction <= 15 && level >= 3) {
            return true;
        }
        return reduction <= 33 && level >= 4;
    }

    public boolean canWearSword(IItemStack item) {
        int level = this.getTalentLevel(EnumCompanionTalent.SWORD);
        if (item == null || !(item.getMCItemStack().m_41720_() instanceof SwordItem) || level <= 0) {
            return false;
        }
        if (level >= 5) {
            return true;
        }
        return this.getSwordDamage(item) - (double)level < 4.0;
    }

    private double getSwordDamage(IItemStack item) {
        if (item == null || !(item.getMCItemStack().m_41720_() instanceof SwordItem)) {
            return 0.0;
        }
        Multimap map = item.getMCItemStack().m_41638_(EquipmentSlot.MAINHAND);
        for (Map.Entry entry : map.entries()) {
            if (entry.getKey() != Attributes.f_22281_) continue;
            AttributeModifier mod = (AttributeModifier)entry.getValue();
            return mod.m_22218_();
        }
        return 0.0;
    }

    public void setStats() {
        IItemStack weapon = this.npc.inventory.getRightHand();
        this.npc.stats.melee.setStrength((int)(1.0 + this.getSwordDamage(weapon)));
        this.npc.stats.healthRegen = 0;
        this.npc.stats.combatRegen = 0;
        int ranged = this.getTalentLevel(EnumCompanionTalent.RANGED);
        if (ranged > 0 && weapon != null) {
            Item item = weapon.getMCItemStack().m_41720_();
            if (ranged > 0 && item == Item.m_41439_((Block)Blocks.f_50652_)) {
                this.npc.inventory.setProjectile(weapon);
            }
            if (ranged > 0 && item instanceof BowItem) {
                this.npc.inventory.setProjectile(NpcAPI.Instance().getIItemStack(new ItemStack((ItemLike)Items.f_42412_)));
            }
        }
        this.inventory.setSize(2 + this.getTalentLevel(EnumCompanionTalent.INVENTORY) * 2);
    }

    public void setSelfsuficient(boolean bo) {
        if (this.owner == null || bo == this.companionJobInterface.isSelfSufficient()) {
            return;
        }
        PlayerData data = PlayerData.get(this.owner);
        if (!bo && data.hasCompanion()) {
            return;
        }
        data.setCompanion(bo ? null : this.npc);
        if (this.companionJobInterface.getType() == EnumCompanionJobs.GUARD) {
            ((CompanionGuard)this.companionJobInterface).isStanding = bo;
        } else if (this.companionJobInterface.getType() == EnumCompanionJobs.FARMER) {
            ((CompanionFarmer)this.companionJobInterface).isStanding = bo;
        }
    }

    public void setSitting(boolean sit) {
        if (sit) {
            this.npc.ais.animationType = 1;
            this.npc.ais.onAttack = 3;
            this.npc.ais.setStartPos(this.npc.m_20183_());
            this.npc.m_21573_().m_26573_();
            this.npc.m_6021_(this.npc.getStartXPos(), this.npc.m_20186_(), this.npc.getStartZPos());
        } else {
            this.npc.ais.animationType = this.stage.animation;
            this.npc.ais.onAttack = 0;
        }
        this.npc.updateAI = true;
    }

    public boolean isSitting() {
        return this.npc.ais.animationType == 1;
    }

    public float getDamageAfterArmorAbsorb(DamageSource source, float damage) {
        if (!this.hasInv || this.getTalentLevel(EnumCompanionTalent.ARMOR) <= 0) {
            return damage;
        }
        if (!source.m_269533_(DamageTypeTags.f_276146_)) {
            this.damageArmor(damage);
            int i = 25 - this.getTotalArmorValue();
            float f1 = damage * (float)i;
            damage = f1 / 25.0f;
        }
        return damage;
    }

    private void damageArmor(float damage) {
        if ((damage /= 4.0f) < 1.0f) {
            damage = 1.0f;
        }
        boolean hasArmor = false;
        Iterator<Map.Entry<Integer, IItemStack>> ita = this.npc.inventory.armor.entrySet().iterator();
        while (ita.hasNext()) {
            Map.Entry<Integer, IItemStack> entry = ita.next();
            IItemStack item = entry.getValue();
            if (item == null || !(item.getMCItemStack().m_41720_() instanceof ArmorItem)) continue;
            hasArmor = true;
            item.getMCItemStack().m_41622_((int)damage, (LivingEntity)this.npc, entity -> entity.m_21166_(EquipmentSlot.m_20744_((EquipmentSlot.Type)EquipmentSlot.Type.ARMOR, (int)((Integer)entry.getKey()))));
            if (item.getStackSize() > 0) continue;
            ita.remove();
        }
        this.gainExp(hasArmor ? 4 : 8);
    }

    public int getTotalArmorValue() {
        int armorValue = 0;
        for (IItemStack armor : this.npc.inventory.armor.values()) {
            if (armor == null || !(armor.getMCItemStack().m_41720_() instanceof ArmorItem)) continue;
            armorValue += ((ArmorItem)armor.getMCItemStack().m_41720_()).m_40404_();
        }
        return armorValue;
    }

    @Override
    public boolean isFollowing() {
        if (this.companionJobInterface.isSelfSufficient()) {
            return false;
        }
        return this.owner != null && !this.isSitting();
    }

    @Override
    public boolean defendOwner() {
        return this.defendOwner && this.owner != null && this.stage != EnumCompanionStage.BABY && !this.companionJobInterface.isSelfSufficient();
    }

    public boolean hasOwner() {
        return !this.uuid.isEmpty();
    }

    public void addMovementStat(double x, double y, double z) {
        long i = Math.round(Math.sqrt(x * x + y * y + z * z) * 100.0);
        if (this.npc.isAttacking()) {
            this.foodstats.addExhaustion(0.04f * (float)i * 0.01f);
        } else {
            this.foodstats.addExhaustion(0.02f * (float)i * 0.01f);
        }
    }

    private IItemStack getFood() {
        for (ItemStack item : this.inventory.items) {
            if (item.m_41619_() || item.m_41720_().m_41473_() == null) continue;
            return NpcAPI.Instance().getIItemStack(item);
        }
        return null;
    }

    public IItemStack getItemInHand() {
        if (this.eating != null && !this.eating.isEmpty()) {
            return this.eating;
        }
        return this.npc.inventory.getRightHand();
    }

    public boolean isEating() {
        return this.eating != null && !this.eating.isEmpty();
    }

    public boolean hasInv() {
        if (!this.hasInv) {
            return false;
        }
        return this.hasTalent(EnumCompanionTalent.INVENTORY) || this.hasTalent(EnumCompanionTalent.ARMOR) || this.hasTalent(EnumCompanionTalent.SWORD);
    }

    public void attackedEntity(Entity entity) {
        IItemStack weapon = this.npc.inventory.getRightHand();
        this.gainExp(weapon == null ? 8 : 4);
        if (weapon == null) {
            return;
        }
        weapon.getMCItemStack().m_41622_(1, (LivingEntity)this.npc, e -> e.m_21166_(EquipmentSlot.MAINHAND));
        if (weapon.getMCItemStack().m_41613_() <= 0) {
            this.npc.inventory.setRightHand(null);
        }
    }

    public void addTalentExp(EnumCompanionTalent talent, int exp) {
        if (this.talents.containsKey((Object)talent)) {
            exp += this.talents.get((Object)talent).intValue();
        }
        this.talents.put(talent, exp);
    }

    @Override
    public int getType() {
        return 6;
    }
}

