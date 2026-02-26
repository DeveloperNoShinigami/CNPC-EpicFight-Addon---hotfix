/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 */
package noppes.npcs.controllers.data;

import java.util.ArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import noppes.npcs.controllers.data.PlayerMail;

public class PlayerMailData {
    public ArrayList<PlayerMail> playermail = new ArrayList();

    public void loadNBTData(CompoundTag compound) {
        ArrayList<PlayerMail> newmail = new ArrayList<PlayerMail>();
        ListTag list = compound.m_128437_("MailData", 10);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            PlayerMail mail = new PlayerMail();
            mail.readNBT(list.m_128728_(i));
            newmail.add(mail);
        }
        this.playermail = newmail;
    }

    public CompoundTag saveNBTData(CompoundTag compound) {
        ListTag list = new ListTag();
        for (PlayerMail mail : this.playermail) {
            list.add((Object)mail.writeNBT());
        }
        compound.m_128365_("MailData", (Tag)list);
        return compound;
    }

    public boolean hasMail() {
        for (PlayerMail mail : this.playermail) {
            if (mail.beenRead) continue;
            return true;
        }
        return false;
    }
}

