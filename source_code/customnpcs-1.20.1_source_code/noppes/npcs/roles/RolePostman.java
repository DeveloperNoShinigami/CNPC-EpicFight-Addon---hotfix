/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.roles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.controllers.data.Line;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.roles.RoleInterface;

public class RolePostman
extends RoleInterface {
    public NpcMiscInventory inventory = new NpcMiscInventory(1);
    private List<Player> recentlyChecked = new ArrayList<Player>();
    private List<Player> toCheck;

    public RolePostman(EntityNPCInterface npc) {
        super(npc);
    }

    @Override
    public boolean aiShouldExecute() {
        if (this.npc.f_19797_ % 20 != 0) {
            return false;
        }
        this.toCheck = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_(10.0, 10.0, 10.0));
        this.toCheck.removeAll(this.recentlyChecked);
        List listMax = this.npc.m_9236_().m_45976_(Player.class, this.npc.m_20191_().m_82377_(20.0, 20.0, 20.0));
        this.recentlyChecked.retainAll(listMax);
        this.recentlyChecked.addAll(this.toCheck);
        for (Player player : this.toCheck) {
            if (!PlayerData.get((Player)player).mailData.hasMail()) continue;
            this.npc.say(player, new Line("mailbox.gotmail"));
        }
        return false;
    }

    @Override
    public boolean aiContinueExecute() {
        return false;
    }

    @Override
    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128365_("PostInv", (Tag)this.inventory.getToNBT());
        return nbttagcompound;
    }

    @Override
    public void load(CompoundTag nbttagcompound) {
        this.inventory.setFromNBT(nbttagcompound.m_128469_("PostInv"));
    }

    @Override
    public void interact(Player player) {
        NoppesUtilServer.openContainerGui((ServerPlayer)player, EnumGuiType.PlayerMailman, buf -> {
            buf.writeBoolean(true);
            buf.writeBoolean(true);
        });
    }

    @Override
    public int getType() {
        return 5;
    }
}

