/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.gui.subgui;

import noppes.npcs.api.entity.IEntity;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.gui.IEntityDisplay;
import noppes.npcs.api.wrapper.gui.CustomGuiAssetsSelectorWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiButtonWrapper;
import noppes.npcs.api.wrapper.gui.CustomGuiWrapper;

public class AssetsGui {
    public static CustomGuiWrapper openTexture(String resource, IPlayer player, IEntity entity, SelectionCallback callback) {
        CustomGuiWrapper gui = new CustomGuiWrapper(player);
        gui.setBackgroundTexture("customnpcs:textures/gui/components.png");
        gui.setSize(400, 214);
        gui.getBackgroundRect().setTextureOffset(0, 0);
        gui.getBackgroundRect().setRepeatingTexture(64, 64, 4);
        CustomGuiButtonWrapper b = gui.addTexturedButton(666, "X", 386, -4, 14, 14, "customnpcs:textures/gui/components.png", 0, 64);
        b.getTextureRect().setRepeatingTexture(64, 22, 3).setHoverText("gui.close");
        b.setTextureHoverOffset(22).setOnPress((guii, bb) -> guii.close());
        IEntityDisplay display = (IEntityDisplay)gui.addEntityDisplay(40, 306, 47, entity).setScale(1.4f).setSize(88, 120);
        ((CustomGuiAssetsSelectorWrapper)gui.addAssetsSelector(11, 4, 4, 300, 204).setSelected(resource).setOnPress((gui2, assets) -> gui2.close())).setOnChange((gui2, assets) -> {
            callback.call(assets.getSelected());
            display.setEntity(entity);
            gui2.update(display);
        });
        return gui;
    }

    public static CustomGuiWrapper openCloakTexture(String resource, IPlayer player, IEntity entity, SelectionCallback callback) {
        CustomGuiWrapper gui = new CustomGuiWrapper(player);
        gui.setBackgroundTexture("customnpcs:textures/gui/components.png");
        gui.setSize(400, 214);
        gui.getBackgroundRect().setTextureOffset(0, 0);
        gui.getBackgroundRect().setRepeatingTexture(64, 64, 4);
        CustomGuiButtonWrapper b = gui.addTexturedButton(666, "X", 386, -4, 14, 14, "customnpcs:textures/gui/components.png", 0, 64);
        b.getTextureRect().setRepeatingTexture(64, 22, 3).setHoverText("gui.close");
        b.setTextureHoverOffset(22).setOnPress((guii, bb) -> guii.close());
        IEntityDisplay display = (IEntityDisplay)gui.addEntityDisplay(40, 306, 47, entity).setScale(1.4f).setRotation(180).setSize(88, 120);
        ((CustomGuiAssetsSelectorWrapper)gui.addAssetsSelector(11, 4, 4, 300, 204).setSelected(resource).setOnPress((gui2, assets) -> gui2.close())).setOnChange((gui2, assets) -> {
            callback.call(assets.getSelected());
            display.setEntity(entity);
            gui2.update(display);
        });
        return gui;
    }

    @FunctionalInterface
    public static interface SelectionCallback {
        public void call(String var1);
    }
}

