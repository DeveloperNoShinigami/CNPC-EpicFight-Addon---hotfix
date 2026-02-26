/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles;

import java.util.HashMap;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.EventHooks;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.entity.data.role.IRoleFollower;
import noppes.npcs.api.event.RoleEvent;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;
import noppes.npcs.shared.client.util.NoppesStringUtils;

public class RoleFollower
extends RoleInterface
implements IRoleFollower {
    private String ownerUUID;
    public boolean isFollowing = true;
    public HashMap<Integer, Integer> rates;
    public NpcMiscInventory inventory = new NpcMiscInventory(3);
    public String dialogHire = "";
    public String dialogFarewell = "";
    public int daysHired;
    public long hiredTime;
    public boolean disableGui = false;
    public boolean infiniteDays = false;
    public boolean refuseSoulStone = false;
    public Player owner = null;

    public RoleFollower(EntityNPCInterface npc) {
        super(npc);
        this.rates = new HashMap();
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128405_("MercenaryDaysHired", this.daysHired);
        nbttagcompound.m_128356_("MercenaryHiredTime", this.hiredTime);
        nbttagcompound.m_128359_("MercenaryDialogHired", this.dialogHire);
        nbttagcompound.m_128359_("MercenaryDialogFarewell", this.dialogFarewell);
        if (this.hasOwner()) {
            nbttagcompound.m_128359_("MercenaryOwner", this.ownerUUID);
        }
        nbttagcompound.m_128365_("MercenaryDayRates", (Tag)NBTTags.nbtIntegerIntegerMap(this.rates));
        nbttagcompound.m_128365_("MercenaryInv", (Tag)this.inventory.getToNBT());
        nbttagcompound.m_128379_("MercenaryIsFollowing", this.isFollowing);
        nbttagcompound.m_128379_("MercenaryDisableGui", this.disableGui);
        nbttagcompound.m_128379_("MercenaryInfiniteDays", this.infiniteDays);
        nbttagcompound.m_128379_("MercenaryRefuseSoulstone", this.refuseSoulStone);
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.ownerUUID = nbttagcompound.m_128461_("MercenaryOwner");
        this.daysHired = nbttagcompound.m_128451_("MercenaryDaysHired");
        this.hiredTime = nbttagcompound.m_128454_("MercenaryHiredTime");
        this.dialogHire = nbttagcompound.m_128461_("MercenaryDialogHired");
        this.dialogFarewell = nbttagcompound.m_128461_("MercenaryDialogFarewell");
        this.rates = NBTTags.getIntegerIntegerMap(nbttagcompound.m_128437_("MercenaryDayRates", 10));
        this.inventory.setFromNBT(nbttagcompound.m_128469_("MercenaryInv"));
        this.isFollowing = nbttagcompound.m_128471_("MercenaryIsFollowing");
        this.disableGui = nbttagcompound.m_128471_("MercenaryDisableGui");
        this.infiniteDays = nbttagcompound.m_128471_("MercenaryInfiniteDays");
        this.refuseSoulStone = nbttagcompound.m_128471_("MercenaryRefuseSoulstone");
    }

    @Override
    public boolean aiShouldExecute() {
        this.owner = this.getOwner();
        if (!this.infiniteDays && this.owner != null && this.getDays() <= 0) {
            RoleEvent.FollowerFinishedEvent event = new RoleEvent.FollowerFinishedEvent(this.owner, this.npc.wrappedNPC);
            EventHooks.onNPCRole(this.npc, event);
            this.npc.say(this.owner, new Line(NoppesStringUtils.formatText(this.dialogFarewell, new Object[]{this.owner, this.npc})));
            this.killed();
        }
        return false;
    }

    public Player getOwner() {
        if (this.npc.m_9236_().f_46443_) {
            return null;
        }
        if (this.ownerUUID == null || this.ownerUUID.isEmpty()) {
            return null;
        }
        try {
            UUID uuid = UUID.fromString(this.ownerUUID);
            if (uuid != null) {
                return this.npc.m_9236_().m_46003_(uuid);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return ((ServerLevel)this.npc.m_9236_()).m_6907_().stream().filter(t -> t.m_7755_().getString().equals(this.ownerUUID)).findFirst().orElse(null);
    }

    public boolean hasOwner() {
        if (!this.infiniteDays && this.daysHired <= 0) {
            return false;
        }
        return this.ownerUUID != null && !this.ownerUUID.isEmpty();
    }

    @Override
    public void killed() {
        this.ownerUUID = null;
        this.daysHired = 0;
        this.hiredTime = 0L;
        this.isFollowing = true;
    }

    @Override
    public void reset() {
        this.killed();
    }

    @Override
    public void interact(Player player) {
        if (this.ownerUUID == null || this.ownerUUID.isEmpty()) {
            this.npc.say(player, this.npc.advanced.getInteractLine());
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollowerHire, this.npc);
        } else if (player == this.owner && !this.disableGui) {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollower, this.npc);
        }
    }

    @Override
    public boolean defendOwner() {
        return this.isFollowing() && this.npc.job.getType() == 3;
    }

    @Override
    public void delete() {
    }

    @Override
    public boolean isFollowing() {
        return this.owner != null && this.isFollowing && this.getDays() > 0;
    }

    public void setOwner(Player player) {
        UUID id = player.m_20148_();
        if (this.ownerUUID == null || id == null || !this.ownerUUID.equals(id.toString())) {
            this.killed();
        }
        this.ownerUUID = id.toString();
    }

    @Override
    public int getDays() {
        if (this.infiniteDays) {
            return 100;
        }
        if (this.daysHired <= 0) {
            return 0;
        }
        int days = (int)((this.npc.m_9236_().m_46467_() - this.hiredTime) / 24000L);
        return this.daysHired - days;
    }

    @Override
    public void addDays(int days) {
        this.daysHired = days + this.getDays();
        this.hiredTime = this.npc.m_9236_().m_46467_();
    }

    @Override
    public boolean getInfinite() {
        return this.infiniteDays;
    }

    @Override
    public void setInfinite(boolean infinite) {
        this.infiniteDays = infinite;
    }

    @Override
    public boolean getGuiDisabled() {
        return this.disableGui;
    }

    @Override
    public void setGuiDisabled(boolean disabled) {
        this.disableGui = disabled;
    }

    @Override
    public boolean getRefuseSoulstone() {
        return this.refuseSoulStone;
    }

    @Override
    public void setRefuseSoulstone(boolean refuse) {
        this.refuseSoulStone = refuse;
    }

    @Override
    public IPlayer getFollowing() {
        Player owner = this.getOwner();
        if (owner != null) {
            return (IPlayer)NpcAPI.Instance().getIEntity((Entity)owner);
        }
        return null;
    }

    @Override
    public void setFollowing(IPlayer player) {
        if (player == null) {
            this.setOwner(null);
        } else {
            this.setOwner((Player)player.getMCEntity());
        }
    }

    @Override
    public int getType() {
        return 2;
    }
}

