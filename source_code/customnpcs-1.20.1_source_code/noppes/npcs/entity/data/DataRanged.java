/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.entity.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.api.entity.data.INPCRanged;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.shared.client.util.NoppesStringUtils;
import noppes.npcs.util.ValueUtil;

public class DataRanged
implements INPCRanged {
    private EntityNPCInterface npc;
    private int burstCount = 1;
    private int pDamage = 4;
    private int pSpeed = 10;
    private int pImpact = 0;
    private int pSize = 5;
    private int pArea = 0;
    private int pTrail = 0;
    private int minDelay = 20;
    private int maxDelay = 40;
    private int rangedRange = 15;
    private int fireRate = 5;
    private int shotCount = 1;
    private int accuracy = 60;
    private int meleeDistance = 0;
    private int canFireIndirect = 0;
    private boolean pRender3D = true;
    private boolean pSpin = false;
    private boolean pStick = false;
    private boolean pPhysics = true;
    private boolean pXlr8 = false;
    private boolean pGlows = false;
    private boolean aimWhileShooting = false;
    private int pEffect = 0;
    private int pDur = 5;
    private int pEffAmp = 0;
    private String fireSound = "minecraft:entity.arrow.shoot";
    private String hitSound = "minecraft:entity.arrow.hit";
    private String groundSound = "minecraft:block.stone.break";

    public DataRanged(EntityNPCInterface npc) {
        this.npc = npc;
    }

    public void load(CompoundTag compound) {
        this.pDamage = compound.m_128451_("pDamage");
        this.pSpeed = compound.m_128451_("pSpeed");
        this.burstCount = compound.m_128451_("BurstCount");
        this.pImpact = compound.m_128451_("pImpact");
        this.pSize = compound.m_128451_("pSize");
        this.pArea = compound.m_128451_("pArea");
        this.pTrail = compound.m_128451_("pTrail");
        this.rangedRange = compound.m_128451_("MaxFiringRange");
        this.fireRate = compound.m_128451_("FireRate");
        this.minDelay = ValueUtil.CorrectInt(compound.m_128451_("minDelay"), 1, 9999);
        this.maxDelay = ValueUtil.CorrectInt(compound.m_128451_("maxDelay"), 1, 9999);
        this.shotCount = ValueUtil.CorrectInt(compound.m_128451_("ShotCount"), 1, 10);
        this.accuracy = compound.m_128451_("Accuracy");
        this.pRender3D = compound.m_128471_("pRender3D");
        this.pSpin = compound.m_128471_("pSpin");
        this.pStick = compound.m_128471_("pStick");
        this.pPhysics = compound.m_128471_("pPhysics");
        this.pXlr8 = compound.m_128471_("pXlr8");
        this.pGlows = compound.m_128471_("pGlows");
        this.aimWhileShooting = compound.m_128471_("AimWhileShooting");
        this.pEffect = compound.m_128451_("pEffect");
        this.pDur = compound.m_128451_("pDur");
        this.pEffAmp = compound.m_128451_("pEffAmp");
        this.fireSound = compound.m_128461_("FiringSound");
        this.hitSound = compound.m_128461_("HitSound");
        this.groundSound = compound.m_128461_("GroundSound");
        this.canFireIndirect = compound.m_128451_("FireIndirect");
        this.meleeDistance = compound.m_128451_("DistanceToMelee");
    }

    public CompoundTag save(CompoundTag compound) {
        compound.m_128405_("BurstCount", this.burstCount);
        compound.m_128405_("pSpeed", this.pSpeed);
        compound.m_128405_("pDamage", this.pDamage);
        compound.m_128405_("pImpact", this.pImpact);
        compound.m_128405_("pSize", this.pSize);
        compound.m_128405_("pArea", this.pArea);
        compound.m_128405_("pTrail", this.pTrail);
        compound.m_128405_("MaxFiringRange", this.rangedRange);
        compound.m_128405_("FireRate", this.fireRate);
        compound.m_128405_("minDelay", this.minDelay);
        compound.m_128405_("maxDelay", this.maxDelay);
        compound.m_128405_("ShotCount", this.shotCount);
        compound.m_128405_("Accuracy", this.accuracy);
        compound.m_128379_("pRender3D", this.pRender3D);
        compound.m_128379_("pSpin", this.pSpin);
        compound.m_128379_("pStick", this.pStick);
        compound.m_128379_("pPhysics", this.pPhysics);
        compound.m_128379_("pXlr8", this.pXlr8);
        compound.m_128379_("pGlows", this.pGlows);
        compound.m_128379_("AimWhileShooting", this.aimWhileShooting);
        compound.m_128405_("pEffect", this.pEffect);
        compound.m_128405_("pDur", this.pDur);
        compound.m_128405_("pEffAmp", this.pEffAmp);
        compound.m_128359_("FiringSound", this.fireSound);
        compound.m_128359_("HitSound", this.hitSound);
        compound.m_128359_("GroundSound", this.groundSound);
        compound.m_128405_("FireIndirect", this.canFireIndirect);
        compound.m_128405_("DistanceToMelee", this.meleeDistance);
        return compound;
    }

    @Override
    public int getStrength() {
        return this.pDamage;
    }

    @Override
    public void setStrength(int strength) {
        this.pDamage = strength;
    }

    @Override
    public int getSpeed() {
        return this.pSpeed;
    }

    @Override
    public void setSpeed(int speed) {
        this.pSpeed = ValueUtil.CorrectInt(speed, 0, 100);
    }

    @Override
    public int getKnockback() {
        return this.pImpact;
    }

    @Override
    public void setKnockback(int punch) {
        this.pImpact = punch;
    }

    @Override
    public int getSize() {
        return this.pSize;
    }

    @Override
    public void setSize(int size) {
        this.pSize = size;
    }

    @Override
    public boolean getRender3D() {
        return this.pRender3D;
    }

    @Override
    public void setRender3D(boolean render3d) {
        this.pRender3D = render3d;
    }

    @Override
    public boolean getSpins() {
        return this.pSpin;
    }

    @Override
    public void setSpins(boolean spins) {
        this.pSpin = spins;
    }

    @Override
    public boolean getSticks() {
        return this.pStick;
    }

    @Override
    public void setSticks(boolean sticks) {
        this.pStick = sticks;
    }

    @Override
    public boolean getHasGravity() {
        return this.pPhysics;
    }

    @Override
    public void setHasGravity(boolean hasGravity) {
        this.pPhysics = hasGravity;
    }

    @Override
    public boolean getAccelerate() {
        return this.pXlr8;
    }

    @Override
    public void setAccelerate(boolean accelerate) {
        this.pXlr8 = accelerate;
    }

    @Override
    public int getExplodeSize() {
        return this.pArea;
    }

    @Override
    public void setExplodeSize(int size) {
        this.pArea = size;
    }

    @Override
    public int getEffectType() {
        return this.pEffect;
    }

    @Override
    public int getEffectTime() {
        return this.pDur;
    }

    @Override
    public int getEffectStrength() {
        return this.pEffAmp;
    }

    @Override
    public void setEffect(int type, int strength, int time) {
        this.pEffect = type;
        this.pDur = time;
        this.pEffAmp = strength;
    }

    @Override
    public boolean getGlows() {
        return this.pGlows;
    }

    @Override
    public void setGlows(boolean glows) {
        this.pGlows = glows;
    }

    @Override
    public int getParticle() {
        return this.pTrail;
    }

    @Override
    public void setParticle(int type) {
        this.pTrail = type;
    }

    @Override
    public int getAccuracy() {
        return this.accuracy;
    }

    @Override
    public void setAccuracy(int accuracy) {
        this.accuracy = ValueUtil.CorrectInt(accuracy, 1, 100);
    }

    @Override
    public int getRange() {
        return this.rangedRange;
    }

    @Override
    public void setRange(int range) {
        this.rangedRange = ValueUtil.CorrectInt(range, 1, 100);
    }

    @Override
    public int getDelayMin() {
        return this.minDelay;
    }

    @Override
    public int getDelayMax() {
        return this.maxDelay;
    }

    @Override
    public int getDelayRNG() {
        int delay = this.minDelay;
        if (this.maxDelay - this.minDelay > 0) {
            delay += this.npc.m_9236_().f_46441_.m_188503_(this.maxDelay - this.minDelay);
        }
        return delay;
    }

    @Override
    public void setDelay(int min, int max) {
        this.minDelay = min = Math.min(min, max);
        this.maxDelay = max;
    }

    @Override
    public int getBurst() {
        return this.burstCount;
    }

    @Override
    public void setBurst(int count) {
        this.burstCount = count;
    }

    @Override
    public int getBurstDelay() {
        return this.fireRate;
    }

    @Override
    public void setBurstDelay(int delay) {
        this.fireRate = delay;
    }

    @Override
    public String getSound(int type) {
        String sound = null;
        if (type == 0) {
            sound = this.fireSound;
        }
        if (type == 1) {
            sound = this.hitSound;
        }
        if (type == 2) {
            sound = this.groundSound;
        }
        if (sound == null || sound.isEmpty()) {
            return null;
        }
        return NoppesStringUtils.cleanResource(sound);
    }

    public SoundEvent getSoundEvent(int type) {
        String sound = this.getSound(type);
        if (sound == null) {
            return null;
        }
        ResourceLocation res = new ResourceLocation(sound);
        SoundEvent ev = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(res);
        if (ev != null) {
            return ev;
        }
        return SoundEvent.m_262824_((ResourceLocation)res);
    }

    @Override
    public void setSound(int type, String sound) {
        if (sound == null) {
            sound = "";
        }
        if (type == 0) {
            this.fireSound = NoppesStringUtils.cleanResource(sound);
        }
        if (type == 1) {
            this.hitSound = NoppesStringUtils.cleanResource(sound);
        }
        if (type == 2) {
            this.groundSound = NoppesStringUtils.cleanResource(sound);
        }
        this.npc.updateClient = true;
    }

    @Override
    public int getShotCount() {
        return this.shotCount;
    }

    @Override
    public void setShotCount(int count) {
        this.shotCount = count;
    }

    @Override
    public boolean getHasAimAnimation() {
        return this.aimWhileShooting;
    }

    @Override
    public void setHasAimAnimation(boolean aim) {
        this.aimWhileShooting = aim;
    }

    @Override
    public int getFireType() {
        return this.canFireIndirect;
    }

    @Override
    public void setFireType(int type) {
        this.canFireIndirect = type;
    }

    @Override
    public int getMeleeRange() {
        return this.meleeDistance;
    }

    @Override
    public void setMeleeRange(int range) {
        this.meleeDistance = range;
        this.npc.updateAI = true;
    }
}

