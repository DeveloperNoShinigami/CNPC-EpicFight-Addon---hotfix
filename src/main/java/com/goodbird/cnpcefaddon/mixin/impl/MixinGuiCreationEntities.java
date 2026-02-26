package com.goodbird.cnpcefaddon.mixin.impl;

import com.goodbird.cnpcefaddon.client.gui.GuiStringSelection;
import com.goodbird.cnpcefaddon.common.NpcPatchReloadListener;
import com.goodbird.cnpcefaddon.mixin.IDataDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import noppes.npcs.client.gui.model.GuiCreationEntities;
import noppes.npcs.client.gui.model.GuiCreationScreenInterface;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.shared.client.gui.components.GuiButtonNop;
import noppes.npcs.shared.client.gui.components.GuiLabel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Vector;

@Mixin(value = GuiCreationEntities.class, priority = 1001)
public class MixinGuiCreationEntities extends GuiCreationScreenInterface {
    public MixinGuiCreationEntities() {
        super(null);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        Vector<String> list = new Vector<>();
        for (ResourceLocation resLoc : NpcPatchReloadListener.AVAILABLE_MODELS) {
            list.add(resLoc.toString());
        }
        String curName = "Select Config";
        if (((IDataDisplay) npc.display).hasEFModel()) {
            curName = ((IDataDisplay) npc.display).getEFModel().toString();
        }
        addLabel(new GuiLabel(312, "EpicFight Config:", this.guiLeft + 124, this.guiTop - 21, 0xffffff));
        this.addButton(new GuiButtonNop(this, 302, this.guiLeft + 230, this.guiTop - 27, 150, 20, curName, (b) -> {
            setSubGui(new GuiStringSelection(this, "Selecting epicfight config:", list, name -> {
                ((IDataDisplay) npc.display).setEFModel(new ResourceLocation(name), false);
                getButton(302).setDisplayText(name);
            }));
        }));
    }

    @Override
    public void drawNpc(GuiGraphics graphics, LivingEntity entity, int x, int y, float zoomed, int rotation) {
        if (wrapper.subgui == null) {
            super.drawNpc(graphics, entity, x, y, zoomed, rotation);
        }
    }
}
