/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.JobInterface;

public class JobConversation
extends JobInterface {
    public Availability availability = new Availability();
    private ArrayList<String> names = new ArrayList();
    private HashMap<String, EntityNPCInterface> npcs = new HashMap();
    public HashMap<Integer, ConversationLine> lines = new HashMap();
    public int quest = -1;
    public String questTitle = "";
    public int generalDelay = 400;
    public int ticks = 100;
    public int range = 20;
    private ConversationLine nextLine;
    private boolean hasStarted = false;
    private int startedTicks = 20;
    public int mode = 0;

    public JobConversation(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.m_128365_("ConversationAvailability", (Tag)this.availability.save(new CompoundTag()));
        compound.m_128405_("ConversationQuest", this.quest);
        compound.m_128405_("ConversationDelay", this.generalDelay);
        compound.m_128405_("ConversationRange", this.range);
        compound.m_128405_("ConversationMode", this.mode);
        ListTag nbttaglist = new ListTag();
        for (int slot : this.lines.keySet()) {
            ConversationLine line = this.lines.get(slot);
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("Slot", slot);
            line.addAdditionalSaveData(nbttagcompound);
            nbttaglist.add((Object)nbttagcompound);
        }
        compound.m_128365_("ConversationLines", (Tag)nbttaglist);
        if (this.hasQuest()) {
            compound.m_128359_("ConversationQuestTitle", this.getQuest().title);
        }
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        this.names.clear();
        this.availability.load(compound.m_128469_("ConversationAvailability"));
        this.quest = compound.m_128451_("ConversationQuest");
        this.generalDelay = compound.m_128451_("ConversationDelay");
        this.questTitle = compound.m_128461_("ConversationQuestTitle");
        this.range = compound.m_128451_("ConversationRange");
        this.mode = compound.m_128451_("ConversationMode");
        ListTag nbttaglist = compound.m_128437_("ConversationLines", 10);
        HashMap<Integer, ConversationLine> map = new HashMap<Integer, ConversationLine>();
        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound = nbttaglist.m_128728_(i);
            ConversationLine line = new ConversationLine();
            line.readAdditionalSaveData(nbttagcompound);
            if (!line.npc.isEmpty() && !this.names.contains(line.npc.toLowerCase())) {
                this.names.add(line.npc.toLowerCase());
            }
            map.put(nbttagcompound.m_128451_("Slot"), line);
        }
        this.lines = map;
        this.ticks = this.generalDelay;
    }

    public boolean hasQuest() {
        return this.getQuest() != null;
    }

    public Quest getQuest() {
        if (this.npc.isClientSide()) {
            return null;
        }
        return QuestController.instance.quests.get(this.quest);
    }

    @Override
    public void aiUpdateTask() {
        --this.ticks;
        if (this.ticks > 0 || this.nextLine == null) {
            return;
        }
        this.say(this.nextLine);
        boolean seenNext = false;
        ConversationLine compare = this.nextLine;
        this.nextLine = null;
        for (ConversationLine line : this.lines.values()) {
            if (line.isEmpty()) continue;
            if (seenNext) {
                this.nextLine = line;
                break;
            }
            if (line != compare) continue;
            seenNext = true;
        }
        if (this.nextLine != null) {
            this.ticks = this.nextLine.delay;
        } else if (this.hasQuest()) {
            List inRange = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_((double)this.range, (double)this.range, (double)this.range));
            for (Player player : inRange) {
                if (!this.availability.isAvailable(player)) continue;
                PlayerQuestController.addActiveQuest(this.getQuest(), player);
            }
        }
    }

    @Override
    public boolean aiShouldExecute() {
        if (this.lines.isEmpty() || this.npc.isKilled() || this.npc.isAttacking() || !this.shouldRun()) {
            return false;
        }
        if (!this.hasStarted && this.mode == 1) {
            if (this.startedTicks-- > 0) {
                return false;
            }
            this.startedTicks = 10;
            if (this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_((double)this.range, (double)this.range, (double)this.range)).isEmpty()) {
                return false;
            }
        }
        for (ConversationLine line : this.lines.values()) {
            if (line == null || line.isEmpty()) continue;
            this.nextLine = line;
            break;
        }
        return this.nextLine != null;
    }

    private boolean shouldRun() {
        boolean bo;
        --this.ticks;
        if (this.ticks > 0) {
            return false;
        }
        this.npcs.clear();
        List list = this.npc.m_9236_().m_45976_(EntityNPCInterface.class, this.npc.m_20191_().m_82377_(10.0, 10.0, 10.0));
        for (EntityNPCInterface npc : list) {
            String name = npc.m_7755_().getString().toLowerCase();
            if (npc.isKilled() || npc.isAttacking() || !this.names.contains(name)) continue;
            this.npcs.put(name, npc);
        }
        boolean bl = bo = this.names.size() == this.npcs.size();
        if (!bo) {
            this.ticks = 20;
        }
        return bo;
    }

    @Override
    public boolean aiContinueExecute() {
        for (EntityNPCInterface npc : this.npcs.values()) {
            if (!npc.isKilled() && !npc.isAttacking()) continue;
            return false;
        }
        return this.nextLine != null;
    }

    @Override
    public void stop() {
        this.nextLine = null;
        this.ticks = this.generalDelay;
        this.hasStarted = false;
    }

    @Override
    public void aiStartExecuting() {
        this.startedTicks = 20;
        this.hasStarted = true;
    }

    private void say(ConversationLine line) {
        List inRange = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_((double)this.range, (double)this.range, (double)this.range));
        EntityNPCInterface npc = this.npcs.get(line.npc.toLowerCase());
        if (npc == null) {
            return;
        }
        for (Player player : inRange) {
            if (!this.availability.isAvailable(player)) continue;
            npc.say(player, line);
        }
    }

    @Override
    public void reset() {
        this.hasStarted = false;
        this.stop();
        this.ticks = 60;
    }

    @Override
    public void killed() {
        this.reset();
    }

    public ConversationLine getLine(int slot) {
        if (this.lines.containsKey(slot)) {
            return this.lines.get(slot);
        }
        ConversationLine line = new ConversationLine();
        this.lines.put(slot, line);
        return line;
    }

    @Override
    public int getType() {
        return 7;
    }

    public class ConversationLine
    extends Line {
        public String npc = "";
        public int delay = 40;

        public void addAdditionalSaveData(CompoundTag compound) {
            compound.m_128359_("Line", this.text);
            compound.m_128359_("Npc", this.npc);
            compound.m_128359_("Sound", this.sound);
            compound.m_128405_("Delay", this.delay);
        }

        public void readAdditionalSaveData(CompoundTag compound) {
            this.text = compound.m_128461_("Line");
            this.npc = compound.m_128461_("Npc");
            this.sound = compound.m_128461_("Sound");
            this.delay = compound.m_128451_("Delay");
        }

        public boolean isEmpty() {
            return this.npc.isEmpty() || this.text.isEmpty();
        }
    }
}

