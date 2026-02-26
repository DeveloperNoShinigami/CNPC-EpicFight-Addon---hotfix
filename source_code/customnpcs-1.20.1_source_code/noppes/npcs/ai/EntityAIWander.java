/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.ai;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ai.selector.NPCInteractSelector;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.entity.EntityNPCInterface;

public class EntityAIWander
extends Goal {
    private EntityNPCInterface entity;
    public final NPCInteractSelector selector;
    private double x;
    private double y;
    private double zPosition;
    private EntityNPCInterface nearbyNPC;

    public EntityAIWander(EntityNPCInterface npc) {
        this.entity = npc;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
        this.selector = new NPCInteractSelector(npc);
    }

    public boolean m_8036_() {
        if (this.entity.m_21216_() >= 100 || !this.entity.m_21573_().m_26571_() || this.entity.isInteracting() || this.entity.m_20159_() || this.entity.ais.movingPause && this.entity.m_217043_().m_188503_(80) != 0) {
            return false;
        }
        if (this.entity.ais.npcInteracting && this.entity.m_217043_().m_188503_(this.entity.ais.movingPause ? 6 : 16) == 1) {
            this.nearbyNPC = this.getNearbyNPC();
        }
        if (this.nearbyNPC != null) {
            this.x = Mth.m_14107_((double)this.nearbyNPC.m_20185_());
            this.y = Mth.m_14107_((double)this.nearbyNPC.m_20186_());
            this.zPosition = Mth.m_14107_((double)this.nearbyNPC.m_20189_());
            this.nearbyNPC.addInteract((LivingEntity)this.entity);
        } else {
            Vec3 vec = this.getVec();
            if (vec == null) {
                return false;
            }
            this.x = vec.f_82479_;
            this.y = vec.f_82480_;
            if (this.entity.ais.movementType == 1) {
                this.y = this.entity.getStartYPos() + (double)this.entity.m_217043_().m_188501_() * 0.75 * (double)this.entity.ais.walkingRange;
            }
            this.zPosition = vec.f_82481_;
        }
        return true;
    }

    public void m_8037_() {
        if (this.nearbyNPC != null) {
            this.nearbyNPC.m_21573_().m_26573_();
        }
    }

    private EntityNPCInterface getNearbyNPC() {
        List list = this.entity.m_9236_().m_6249_((Entity)this.entity, this.entity.m_20191_().m_82377_((double)this.entity.ais.walkingRange, this.entity.ais.walkingRange > 7 ? 7.0 : (double)this.entity.ais.walkingRange, (double)this.entity.ais.walkingRange), (Predicate)((Object)this.selector));
        Iterator ita = list.iterator();
        while (ita.hasNext()) {
            EntityNPCInterface npc = (EntityNPCInterface)((Object)ita.next());
            if (npc.ais.stopAndInteract && !npc.isAttacking() && npc.m_6084_() && !this.entity.faction.isAggressiveToNpc(npc)) continue;
            ita.remove();
        }
        if (list.isEmpty()) {
            return null;
        }
        return (EntityNPCInterface)((Object)list.get(this.entity.m_217043_().m_188503_(list.size())));
    }

    private Vec3 getVec() {
        if (this.entity.ais.walkingRange > 0) {
            BlockPos start = new BlockPos((int)this.entity.getStartXPos(), (int)this.entity.getStartYPos(), (int)this.entity.getStartZPos());
            int distance = (int)Math.sqrt(this.entity.m_20183_().m_123331_((Vec3i)start));
            int range = Math.min(this.entity.ais.walkingRange, CustomNpcs.NpcNavRange);
            if (range - distance < 4) {
                Vec3 pos2 = new Vec3((this.entity.m_20185_() + (double)start.m_123341_()) / 2.0, (this.entity.m_20186_() + (double)start.m_123342_()) / 2.0, (this.entity.m_20189_() + (double)start.m_123343_()) / 2.0);
                return DefaultRandomPos.m_148412_((PathfinderMob)this.entity, (int)(range / 2), (int)Math.min(range / 2, 7), (Vec3)pos2, (double)1.5707963267948966);
            }
            return DefaultRandomPos.m_148403_((PathfinderMob)this.entity, (int)(range / 2), (int)Math.min(range / 2, 7));
        }
        return DefaultRandomPos.m_148403_((PathfinderMob)this.entity, (int)CustomNpcs.NpcNavRange, (int)7);
    }

    public boolean m_8045_() {
        if (this.nearbyNPC != null && (!this.selector.apply((Object)this.nearbyNPC) || this.entity.isInRange((Entity)this.nearbyNPC, this.entity.m_20205_()))) {
            return false;
        }
        return !this.entity.m_21573_().m_26571_() && this.entity.m_6084_() && !this.entity.isInteracting();
    }

    public void m_8056_() {
        this.entity.m_21573_().m_26536_(this.entity.m_21573_().m_26524_(this.x, this.y, this.zPosition, 0), 1.0);
    }

    public void m_8041_() {
        if (this.nearbyNPC != null && this.entity.isInRange((Entity)this.nearbyNPC, 3.5)) {
            Line line;
            EntityNPCInterface talk = this.entity;
            if (this.entity.m_217043_().m_188499_()) {
                talk = this.nearbyNPC;
            }
            if ((line = talk.advanced.getNPCInteractLine()) == null) {
                line = new Line(".........");
            }
            line.setShowText(false);
            talk.saySurrounding(line);
            this.entity.addInteract((LivingEntity)this.nearbyNPC);
            this.nearbyNPC.addInteract((LivingEntity)this.entity);
        }
        this.nearbyNPC = null;
    }
}

