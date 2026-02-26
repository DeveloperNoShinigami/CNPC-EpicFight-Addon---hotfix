/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraftforge.common.util.FakePlayer
 */
package noppes.npcs.packets.server;

import java.util.ArrayList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import noppes.npcs.EventHooks;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.event.QuestEvent;
import noppes.npcs.api.item.IItemStack;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.data.PlayerData;
import noppes.npcs.controllers.data.PlayerQuestData;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.controllers.data.QuestData;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.EntityIMixin;
import noppes.npcs.packets.PacketServerBasic;

public class SPacketQuestCompletionCheck
extends PacketServerBasic {
    private final int questId;

    public SPacketQuestCompletionCheck(int questId) {
        this.questId = questId;
    }

    @Override
    public boolean toolAllowed(ItemStack item) {
        return true;
    }

    public static void encode(SPacketQuestCompletionCheck msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.questId);
    }

    public static SPacketQuestCompletionCheck decode(FriendlyByteBuf buf) {
        return new SPacketQuestCompletionCheck(buf.readInt());
    }

    @Override
    protected void handle() {
        PlayerData data = PlayerData.get((Player)this.player);
        PlayerQuestData playerdata = data.questData;
        QuestData questdata = playerdata.activeQuests.get(this.questId);
        if (questdata == null) {
            return;
        }
        Quest quest = questdata.quest;
        if (!quest.questInterface.isCompleted((Player)this.player)) {
            return;
        }
        QuestEvent.QuestTurnedInEvent event = new QuestEvent.QuestTurnedInEvent(data.scriptData.getPlayer(), quest);
        event.expReward = quest.rewardExp;
        ArrayList<IItemStack> list = new ArrayList<IItemStack>();
        for (ItemStack item : quest.rewardItems.items) {
            if (item.m_41619_()) continue;
            list.add(NpcAPI.Instance().getIItemStack(item));
        }
        if (!quest.randomReward) {
            event.itemRewards = list.toArray(new IItemStack[list.size()]);
        } else if (!list.isEmpty()) {
            event.itemRewards = new IItemStack[]{(IItemStack)list.get(this.player.m_217043_().m_188503_(list.size()))};
        }
        EventHooks.onQuestTurnedIn(data.scriptData, event);
        for (IItemStack item : event.itemRewards) {
            if (item == null) continue;
            NoppesUtilServer.GivePlayerItem((Entity)this.player, (Player)this.player, item.getMCItemStack());
        }
        quest.questInterface.handleComplete((Player)this.player);
        if (event.expReward > 0) {
            NoppesUtilServer.playSound((LivingEntity)this.player, SoundEvents.f_11871_, 0.1f, 0.5f * ((this.player.m_9236_().f_46441_.m_188501_() - this.player.m_9236_().f_46441_.m_188501_()) * 0.7f + 1.8f));
            this.player.m_6756_(event.expReward);
        }
        quest.factionOptions.addPoints((Player)this.player);
        if (quest.mail.isValid()) {
            PlayerDataController.instance.addPlayerMessage(this.player.m_20194_(), this.player.m_7755_().getString(), quest.mail);
        }
        if (!quest.command.isEmpty()) {
            FakePlayer cplayer = EntityNPCInterface.CommandPlayer;
            ((EntityIMixin)cplayer).setLevel((Level)((ServerLevel)this.player.m_9236_()));
            cplayer.m_6034_(this.player.m_20185_(), this.player.m_20186_(), this.player.m_20189_());
            NoppesUtilServer.runCommand((Entity)cplayer, "QuestCompletion", quest.command, (Player)this.player);
        }
        PlayerQuestController.setQuestFinished(quest, (Player)this.player);
        if (quest.hasNewQuest()) {
            PlayerQuestController.addActiveQuest(quest.getNextQuest(), (Player)this.player);
        }
    }
}

