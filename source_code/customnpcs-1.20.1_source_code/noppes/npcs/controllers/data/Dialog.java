/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.ICompatibilty;
import noppes.npcs.VersionCompatibility;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.handler.data.IAvailability;
import noppes.npcs.api.handler.data.IDialog;
import noppes.npcs.api.handler.data.IDialogCategory;
import noppes.npcs.api.handler.data.IDialogOption;
import noppes.npcs.api.handler.data.IQuest;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.controllers.data.DialogCategory;
import noppes.npcs.controllers.data.DialogOption;
import noppes.npcs.controllers.data.FactionOptions;
import noppes.npcs.controllers.data.PlayerMail;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.db.DatabaseColumn;

public class Dialog
implements ICompatibilty,
IDialog {
    public int version = VersionCompatibility.ModRev;
    @DatabaseColumn(name="id", type=DatabaseColumn.Type.INT)
    public int id = -1;
    @DatabaseColumn(name="title", type=DatabaseColumn.Type.VARCHAR)
    public String title = "";
    @DatabaseColumn(name="text", type=DatabaseColumn.Type.TEXT)
    public String text = "";
    @DatabaseColumn(name="quest", type=DatabaseColumn.Type.INT)
    public int quest = -1;
    @DatabaseColumn(name="category", type=DatabaseColumn.Type.VARCHAR)
    public String categoryName;
    public final DialogCategory category;
    public HashMap<Integer, DialogOption> options = new HashMap();
    public Availability availability = new Availability();
    public FactionOptions factionOptions = new FactionOptions();
    public String sound;
    public String command = "";
    public PlayerMail mail = new PlayerMail();
    public boolean hideNPC = false;
    public boolean showWheel = false;
    public boolean disableEsc = false;

    public Dialog(DialogCategory category) {
        this.category = category;
    }

    public boolean hasDialogs(Player player) {
        for (DialogOption option : this.options.values()) {
            if (option == null || option.optionType != 1 || !option.hasDialog() || !option.isAvailable(player)) continue;
            return true;
        }
        return false;
    }

    public void readNBT(CompoundTag compound) {
        this.id = compound.m_128451_("DialogId");
        this.readNBTPartial(compound);
    }

    public void readNBTPartial(CompoundTag compound) {
        this.version = compound.m_128451_("ModRev");
        VersionCompatibility.CheckAvailabilityCompatibility(this, compound);
        this.title = compound.m_128461_("DialogTitle");
        this.text = compound.m_128461_("DialogText");
        this.quest = compound.m_128451_("DialogQuest");
        this.sound = compound.m_128461_("DialogSound");
        this.command = compound.m_128461_("DialogCommand");
        this.mail.readNBT(compound.m_128469_("DialogMail"));
        this.hideNPC = compound.m_128471_("DialogHideNPC");
        this.showWheel = compound.m_128471_("DialogShowWheel");
        this.disableEsc = compound.m_128471_("DialogDisableEsc");
        ListTag options = compound.m_128437_("Options", 10);
        HashMap<Integer, DialogOption> newoptions = new HashMap<Integer, DialogOption>();
        for (int iii = 0; iii < options.size(); ++iii) {
            CompoundTag option = options.m_128728_(iii);
            int opslot = option.m_128451_("OptionSlot");
            DialogOption dia = new DialogOption();
            dia.readNBT(option.m_128469_("Option"));
            if (dia.hasDialog()) {
                // empty if block
            }
            newoptions.put(opslot, dia);
            dia.slot = opslot;
        }
        this.options = newoptions;
        this.availability.load(compound);
        this.factionOptions.load(compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.m_128405_("DialogId", this.id);
        return this.writeToNBTPartial(compound);
    }

    public CompoundTag writeToNBTPartial(CompoundTag compound) {
        compound.m_128359_("DialogTitle", this.title);
        compound.m_128359_("DialogText", this.text);
        compound.m_128405_("DialogQuest", this.quest);
        compound.m_128359_("DialogCommand", this.command);
        compound.m_128365_("DialogMail", (Tag)this.mail.writeNBT());
        compound.m_128379_("DialogHideNPC", this.hideNPC);
        compound.m_128379_("DialogShowWheel", this.showWheel);
        compound.m_128379_("DialogDisableEsc", this.disableEsc);
        if (this.sound != null && !this.sound.isEmpty()) {
            compound.m_128359_("DialogSound", this.sound);
        }
        ListTag options = new ListTag();
        for (int opslot : this.options.keySet()) {
            CompoundTag listcompound = new CompoundTag();
            listcompound.m_128405_("OptionSlot", opslot);
            listcompound.m_128365_("Option", (Tag)this.options.get(opslot).writeNBT());
            options.add((Object)listcompound);
        }
        compound.m_128365_("Options", (Tag)options);
        this.availability.save(compound);
        this.factionOptions.save(compound);
        compound.m_128405_("ModRev", this.version);
        return compound;
    }

    public boolean hasQuest() {
        return this.getQuest() != null;
    }

    @Override
    public Quest getQuest() {
        if (QuestController.instance == null) {
            return null;
        }
        return QuestController.instance.quests.get(this.quest);
    }

    public boolean hasOtherOptions() {
        for (DialogOption option : this.options.values()) {
            if (option == null || option.optionType == 2) continue;
            return true;
        }
        return false;
    }

    public Dialog copy(Player player) {
        Dialog dialog = new Dialog(this.category);
        dialog.id = this.id;
        dialog.text = this.text;
        dialog.title = this.title;
        dialog.quest = this.quest;
        dialog.sound = this.sound;
        dialog.mail = this.mail;
        dialog.command = this.command;
        dialog.hideNPC = this.hideNPC;
        dialog.showWheel = this.showWheel;
        dialog.disableEsc = this.disableEsc;
        for (int slot : this.options.keySet()) {
            DialogOption option = this.options.get(slot);
            if (option.optionType == 1 && (!option.hasDialog() || !option.isAvailable(player))) continue;
            dialog.options.put(slot, option);
        }
        return dialog;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public List<IDialogOption> getOptions() {
        return new ArrayList<IDialogOption>(this.options.values());
    }

    @Override
    public IDialogOption getOption(int slot) {
        IDialogOption option = this.options.get(slot);
        if (option == null) {
            throw new CustomNPCsException("There is no DialogOption for slot: " + slot, new Object[0]);
        }
        return option;
    }

    @Override
    public IAvailability getAvailability() {
        return this.availability;
    }

    @Override
    public IDialogCategory getCategory() {
        return this.category;
    }

    @Override
    public void save() {
        DialogController.instance.saveDialog(this.category, this);
    }

    @Override
    public void setName(String name) {
        this.title = name;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setQuest(IQuest quest) {
        if (quest == null) {
            this.quest = -1;
        } else {
            if (quest.getId() < 0) {
                throw new CustomNPCsException("Quest id is lower than 0", new Object[0]);
            }
            this.quest = quest.getId();
        }
    }

    @Override
    public String getCommand() {
        return this.command;
    }

    @Override
    public void setCommand(String command) {
        this.command = command;
    }
}

