/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.sounds.SoundSource
 */
package noppes.npcs.entity.data;

import java.util.HashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import noppes.npcs.api.entity.data.INPCAdvanced;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.controllers.data.FactionOptions;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.controllers.data.Lines;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataScenes;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.client.PacketPlaySound;
import noppes.npcs.roles.JobBard;
import noppes.npcs.roles.JobBuilder;
import noppes.npcs.roles.JobChunkLoader;
import noppes.npcs.roles.JobConversation;
import noppes.npcs.roles.JobFarmer;
import noppes.npcs.roles.JobFollower;
import noppes.npcs.roles.JobGuard;
import noppes.npcs.roles.JobHealer;
import noppes.npcs.roles.JobInterface;
import noppes.npcs.roles.JobItemGiver;
import noppes.npcs.roles.JobPuppet;
import noppes.npcs.roles.JobSpawner;
import noppes.npcs.roles.RoleBank;
import noppes.npcs.roles.RoleCompanion;
import noppes.npcs.roles.RoleDialog;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.roles.RoleInterface;
import noppes.npcs.roles.RolePostman;
import noppes.npcs.roles.RoleTrader;
import noppes.npcs.roles.RoleTransporter;
import noppes.npcs.shared.client.util.NoppesStringUtils;
import noppes.npcs.util.ValueUtil;

public class DataAdvanced
implements INPCAdvanced {
    public Lines interactLines = new Lines();
    public Lines worldLines = new Lines();
    public Lines attackLines = new Lines();
    public Lines killedLines = new Lines();
    public Lines killLines = new Lines();
    public Lines npcInteractLines = new Lines();
    public boolean orderedLines = false;
    private String idleSound = "";
    private String angrySound = "";
    private String hurtSound = "minecraft:entity.player.hurt";
    private String deathSound = "minecraft:entity.player.hurt";
    private String stepSound = "";
    private EntityNPCInterface npc;
    public FactionOptions factions = new FactionOptions();
    public boolean attackOtherFactions = false;
    public boolean defendFaction = false;
    public boolean disablePitch = false;
    public DataScenes scenes;

    public DataAdvanced(EntityNPCInterface npc) {
        this.npc = npc;
        this.scenes = new DataScenes(npc);
    }

    public CompoundTag save(CompoundTag compound) {
        compound.m_128365_("NpcLines", (Tag)this.worldLines.save());
        compound.m_128365_("NpcKilledLines", (Tag)this.killedLines.save());
        compound.m_128365_("NpcInteractLines", (Tag)this.interactLines.save());
        compound.m_128365_("NpcAttackLines", (Tag)this.attackLines.save());
        compound.m_128365_("NpcKillLines", (Tag)this.killLines.save());
        compound.m_128365_("NpcInteractNPCLines", (Tag)this.npcInteractLines.save());
        compound.m_128379_("OrderedLines", this.orderedLines);
        compound.m_128359_("NpcIdleSound", this.idleSound);
        compound.m_128359_("NpcAngrySound", this.angrySound);
        compound.m_128359_("NpcHurtSound", this.hurtSound);
        compound.m_128359_("NpcDeathSound", this.deathSound);
        compound.m_128359_("NpcStepSound", this.stepSound);
        compound.m_128405_("FactionID", this.npc.getFaction().id);
        compound.m_128379_("AttackOtherFactions", this.attackOtherFactions);
        compound.m_128379_("DefendFaction", this.defendFaction);
        compound.m_128379_("DisablePitch", this.disablePitch);
        compound.m_128405_("Role", this.npc.role.getType());
        compound.m_128405_("NpcJob", this.npc.job.getType());
        compound.m_128365_("FactionPoints", (Tag)this.factions.save(new CompoundTag()));
        compound.m_128365_("NPCDialogOptions", (Tag)this.nbtDialogs(this.npc.dialogs));
        compound.m_128365_("NpcScenes", (Tag)this.scenes.save(new CompoundTag()));
        return compound;
    }

    public void readToNBT(CompoundTag compound) {
        this.interactLines.readNBT(compound.m_128469_("NpcInteractLines"));
        this.worldLines.readNBT(compound.m_128469_("NpcLines"));
        this.attackLines.readNBT(compound.m_128469_("NpcAttackLines"));
        this.killedLines.readNBT(compound.m_128469_("NpcKilledLines"));
        this.killLines.readNBT(compound.m_128469_("NpcKillLines"));
        this.npcInteractLines.readNBT(compound.m_128469_("NpcInteractNPCLines"));
        this.orderedLines = compound.m_128471_("OrderedLines");
        this.idleSound = compound.m_128461_("NpcIdleSound");
        this.angrySound = compound.m_128461_("NpcAngrySound");
        this.hurtSound = compound.m_128461_("NpcHurtSound");
        this.deathSound = compound.m_128461_("NpcDeathSound");
        this.stepSound = compound.m_128461_("NpcStepSound");
        this.npc.setFaction(compound.m_128451_("FactionID"));
        this.npc.faction = this.npc.getFaction();
        this.attackOtherFactions = compound.m_128471_("AttackOtherFactions");
        this.defendFaction = compound.m_128471_("DefendFaction");
        this.disablePitch = compound.m_128471_("DisablePitch");
        this.setRole(compound.m_128451_("Role"));
        this.setJob(compound.m_128451_("NpcJob"));
        this.factions.load(compound.m_128469_("FactionPoints"));
        this.npc.dialogs = this.getDialogs(compound.m_128437_("NPCDialogOptions", 10));
        this.scenes.load(compound.m_128469_("NpcScenes"));
    }

    private HashMap<Integer, DialogOption> getDialogs(ListTag tagList) {
        HashMap<Integer, DialogOption> map = new HashMap<Integer, DialogOption>();
        for (int i = 0; i < tagList.size(); ++i) {
            CompoundTag nbttagcompound = tagList.m_128728_(i);
            int slot = nbttagcompound.m_128451_("DialogSlot");
            DialogOption option = new DialogOption();
            option.readNBT(nbttagcompound.m_128469_("NPCDialog"));
            option.optionType = 1;
            map.put(slot, option);
        }
        return map;
    }

    private ListTag nbtDialogs(HashMap<Integer, DialogOption> dialogs2) {
        ListTag nbttaglist = new ListTag();
        for (int slot : dialogs2.keySet()) {
            CompoundTag nbttagcompound = new CompoundTag();
            nbttagcompound.m_128405_("DialogSlot", slot);
            nbttagcompound.m_128365_("NPCDialog", (Tag)dialogs2.get(slot).writeNBT());
            nbttaglist.add((Object)nbttagcompound);
        }
        return nbttaglist;
    }

    private Lines getLines(int type) {
        if (type == 0) {
            return this.interactLines;
        }
        if (type == 1) {
            return this.attackLines;
        }
        if (type == 2) {
            return this.worldLines;
        }
        if (type == 3) {
            return this.killedLines;
        }
        if (type == 4) {
            return this.killLines;
        }
        if (type == 5) {
            return this.npcInteractLines;
        }
        return null;
    }

    @Override
    public void setLine(int type, int slot, String text, String sound) {
        slot = ValueUtil.CorrectInt(slot, 0, 7);
        Lines lines = this.getLines(type);
        if (text == null || text.isEmpty()) {
            lines.lines.remove(slot);
        } else {
            Line line = lines.lines.get(slot);
            if (line == null) {
                line = new Line();
                lines.lines.put(slot, line);
            }
            line.setText(text);
            line.setSound(sound);
        }
    }

    @Override
    public String getLine(int type, int slot) {
        Line line = this.getLines((int)type).lines.get(slot);
        if (line == null) {
            return "";
        }
        return line.getText();
    }

    @Override
    public int getLineCount(int type) {
        return this.getLines((int)type).lines.size();
    }

    @Override
    public String getSound(int type) {
        String sound = null;
        if (type == 0) {
            sound = this.idleSound;
        } else if (type == 1) {
            sound = this.angrySound;
        } else if (type == 2) {
            sound = this.hurtSound;
        } else if (type == 3) {
            sound = this.deathSound;
        } else if (type == 4) {
            sound = this.stepSound;
        }
        if (sound != null && sound.isEmpty()) {
            return null;
        }
        return NoppesStringUtils.cleanResource(sound);
    }

    public void playSound(int type, float volume, float pitch) {
        String sound = this.getSound(type);
        if (sound == null) {
            return;
        }
        BlockPos pos = this.npc.m_20183_();
        if (!this.npc.m_9236_().f_46443_) {
            Packets.sendNearby(this.npc.m_9236_(), pos, 16, new PacketPlaySound(sound, pos, volume, pitch));
        } else {
            MusicController.Instance.playSound(SoundSource.VOICE, sound, pos, volume, pitch);
        }
    }

    @Override
    public void setSound(int type, String sound) {
        if (sound == null) {
            sound = "";
        }
        sound = NoppesStringUtils.cleanResource(sound);
        if (type == 0) {
            this.idleSound = sound;
        } else if (type == 1) {
            this.angrySound = sound;
        } else if (type == 2) {
            this.hurtSound = sound;
        } else if (type == 3) {
            this.deathSound = sound;
        } else if (type == 4) {
            this.stepSound = sound;
        }
    }

    public Line getInteractLine() {
        return this.interactLines.getLine(!this.orderedLines);
    }

    public Line getAttackLine() {
        return this.attackLines.getLine(!this.orderedLines);
    }

    public Line getKilledLine() {
        return this.killedLines.getLine(!this.orderedLines);
    }

    public Line getKillLine() {
        return this.killLines.getLine(!this.orderedLines);
    }

    public Line getLevelLine() {
        return this.worldLines.getLine(!this.orderedLines);
    }

    public Line getNPCInteractLine() {
        return this.npcInteractLines.getLine(!this.orderedLines);
    }

    public void setRole(int role) {
        if (8 <= role) {
            role -= 2;
        }
        if ((role %= 8) == 0) {
            this.npc.role = RoleInterface.NONE;
        } else if (role == 3 && !(this.npc.role instanceof RoleBank)) {
            this.npc.role = new RoleBank(this.npc);
        } else if (role == 2 && !(this.npc.role instanceof RoleFollower)) {
            this.npc.role = new RoleFollower(this.npc);
        } else if (role == 5 && !(this.npc.role instanceof RolePostman)) {
            this.npc.role = new RolePostman(this.npc);
        } else if (role == 1 && !(this.npc.role instanceof RoleTrader)) {
            this.npc.role = new RoleTrader(this.npc);
        } else if (role == 4 && !(this.npc.role instanceof RoleTransporter)) {
            this.npc.role = new RoleTransporter(this.npc);
        } else if (role == 6 && !(this.npc.role instanceof RoleCompanion)) {
            this.npc.role = new RoleCompanion(this.npc);
        } else if (role == 7 && !(this.npc.role instanceof RoleDialog)) {
            this.npc.role = new RoleDialog(this.npc);
        }
    }

    public void setJob(int job) {
        if (!this.npc.m_9236_().f_46443_) {
            this.npc.job.reset();
        }
        if ((job %= 12) == 0) {
            this.npc.job = JobInterface.NONE;
        } else if (job == 1 && !(this.npc.job instanceof JobBard)) {
            this.npc.job = new JobBard(this.npc);
        } else if (job == 2 && !(this.npc.job instanceof JobHealer)) {
            this.npc.job = new JobHealer(this.npc);
        } else if (job == 3 && !(this.npc.job instanceof JobGuard)) {
            this.npc.job = new JobGuard(this.npc);
        } else if (job == 4 && !(this.npc.job instanceof JobItemGiver)) {
            this.npc.job = new JobItemGiver(this.npc);
        } else if (job == 5 && !(this.npc.job instanceof JobFollower)) {
            this.npc.job = new JobFollower(this.npc);
        } else if (job == 6 && !(this.npc.job instanceof JobSpawner)) {
            this.npc.job = new JobSpawner(this.npc);
        } else if (job == 7 && !(this.npc.job instanceof JobConversation)) {
            this.npc.job = new JobConversation(this.npc);
        } else if (job == 8 && !(this.npc.job instanceof JobChunkLoader)) {
            this.npc.job = new JobChunkLoader(this.npc);
        } else if (job == 9 && !(this.npc.job instanceof JobPuppet)) {
            this.npc.job = new JobPuppet(this.npc);
        } else if (job == 10 && !(this.npc.job instanceof JobBuilder)) {
            this.npc.job = new JobBuilder(this.npc);
        } else if (job == 11 && !(this.npc.job instanceof JobFarmer)) {
            this.npc.job = new JobFarmer(this.npc);
        }
    }

    public boolean hasLevelLines() {
        return !this.worldLines.isEmpty();
    }
}

