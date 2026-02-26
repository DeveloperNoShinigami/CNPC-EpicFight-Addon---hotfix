/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package noppes.npcs.client.gui.roles;

import java.util.HashMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.containers.ContainerNPCFollowerSetup;
import noppes.npcs.packets.Packets;
import noppes.npcs.packets.server.SPacketNpcRoleSave;
import noppes.npcs.roles.RoleFollower;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiButtonYesNo;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import noppes.npcs.shared.client.gui.components.GuiTextFieldNop;
import noppes.npcs.shared.client.gui.listeners.IGuiInterface;

public class GuiNpcFollowerSetup
extends GuiContainerNPCInterface2<ContainerNPCFollowerSetup> {
    private RoleFollower role;
    private static final ResourceLocation field_110422_t = new ResourceLocation("textures/gui/followersetup.png");

    public GuiNpcFollowerSetup(ContainerNPCFollowerSetup container, Inventory inv, Component titleIn) {
        super(NoppesUtil.getLastNpc(), container, inv, titleIn);
        this.f_97727_ = 200;
        this.role = (RoleFollower)this.npc.role;
        this.setBackground("followersetup.png");
    }

    @Override
    public void m_7856_() {
        int i;
        super.m_7856_();
        for (i = 0; i < 3; ++i) {
            int x = this.guiLeft + 66;
            int y = this.guiTop + 37;
            GuiTextFieldNop tf = new GuiTextFieldNop(i, (Screen)this, x, y += i * 25, 24, 20, "1");
            tf.numbersOnly = true;
            tf.setMinMaxDefault(1, Integer.MAX_VALUE, 1);
            this.addTextField(tf);
        }
        i = 0;
        for (int day : this.role.rates.values()) {
            this.getTextField(i).m_94144_("" + day);
            ++i;
        }
        MutableComponent text = Component.m_237115_((String)"follower.hireText").m_130946_(" {days} ").m_7220_((Component)Component.m_237115_((String)"follower.days"));
        if (!this.role.dialogHire.isEmpty()) {
            text = Component.m_237115_((String)this.role.dialogHire);
        }
        this.addTextField(new GuiTextFieldNop(3, (Screen)this, this.guiLeft + 100, this.guiTop + 6, 286, 20, (Component)text));
        text = Component.m_237115_((String)"follower.farewellText").m_130946_(" {player}");
        if (!this.role.dialogFarewell.isEmpty()) {
            text = Component.m_237115_((String)this.role.dialogFarewell);
        }
        this.addTextField(new GuiTextFieldNop(4, (Screen)this, this.guiLeft + 100, this.guiTop + 30, 286, 20, (Component)text));
        this.addLabel(new GuiLabel(7, "follower.infiniteDays", this.guiLeft + 180, this.guiTop + 80));
        this.addButton(new GuiButtonYesNo((IGuiInterface)this, 7, this.guiLeft + 260, this.guiTop + 75, this.role.infiniteDays));
        this.addLabel(new GuiLabel(8, "follower.guiDisabled", this.guiLeft + 180, this.guiTop + 104));
        this.addButton(new GuiButtonYesNo((IGuiInterface)this, 8, this.guiLeft + 260, this.guiTop + 99, this.role.disableGui));
        this.addLabel(new GuiLabel(9, "follower.allowSoulstone", this.guiLeft + 180, this.guiTop + 128));
        this.addButton(new GuiButtonYesNo((IGuiInterface)this, 9, this.guiLeft + 260, this.guiTop + 123, !this.role.refuseSoulStone));
        this.addButton(new GuiButtonNop(this, 10, this.guiLeft + 195, this.guiTop + 147, 100, 20, "gui.reset"));
    }

    @Override
    public void buttonEvent(GuiButtonNop guibutton) {
        if (guibutton.id == 7) {
            this.role.infiniteDays = ((GuiButtonYesNo)guibutton).getBoolean();
        }
        if (guibutton.id == 8) {
            this.role.disableGui = ((GuiButtonYesNo)guibutton).getBoolean();
        }
        if (guibutton.id == 9) {
            boolean bl = this.role.refuseSoulStone = !((GuiButtonYesNo)guibutton).getBoolean();
        }
        if (guibutton.id == 10) {
            this.role.killed();
        }
    }

    @Override
    protected void m_280003_(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
    }

    @Override
    public void save() {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.role.inventory.m_6643_(); ++i) {
            ItemStack item = this.role.inventory.m_8020_(i);
            if (item == null || item.m_41619_()) continue;
            int days = 1;
            if (!this.getTextField(i).isEmpty() && this.getTextField(i).isInteger()) {
                days = this.getTextField(i).getInteger();
            }
            if (days <= 0) {
                days = 1;
            }
            map.put(i, days);
        }
        this.role.rates = map;
        this.role.dialogHire = this.getTextField(3).m_94155_();
        this.role.dialogFarewell = this.getTextField(4).m_94155_();
        Packets.sendServer(new SPacketNpcRoleSave(this.role.save(new CompoundTag())));
    }
}

