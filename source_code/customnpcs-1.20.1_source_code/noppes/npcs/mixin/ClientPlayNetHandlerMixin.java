/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package noppes.npcs.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import noppes.npcs.CustomEntities;
import noppes.npcs.entity.EntityProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPacketListener.class})
public class ClientPlayNetHandlerMixin {
    @Inject(at={@At(value="TAIL")}, method={"handleAddEntity"})
    private void handleAddEntity(ClientboundAddEntityPacket packet, CallbackInfo ci) {
        EntityProjectile entity = null;
        ClientLevel level = Minecraft.m_91087_().f_91073_;
        if (packet.m_131508_() == CustomEntities.entityProjectile) {
            entity = new EntityProjectile(CustomEntities.entityProjectile, (Level)level);
            Entity entity2 = level.m_6815_(packet.m_131509_());
            if (entity2 != null) {
                entity.m_5602_(entity2);
            }
        }
        if (entity != null) {
            int i = packet.m_131496_();
            entity.m_6034_(packet.m_131500_(), packet.m_131501_(), packet.m_131502_());
            entity.m_6027_(packet.m_131500_(), packet.m_131501_(), packet.m_131502_());
            entity.m_146926_(packet.m_237566_() * 360.0f / 256.0f);
            entity.m_146922_(packet.m_237567_() * 360.0f / 256.0f);
            entity.m_20234_(i);
            entity.m_20084_(packet.m_131499_());
            Minecraft.m_91087_().f_91073_.m_104627_(i, (Entity)entity);
        }
    }
}

