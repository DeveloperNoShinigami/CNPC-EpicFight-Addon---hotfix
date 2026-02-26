/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  net.minecraft.server.packs.PackResources
 *  net.minecraft.server.packs.PathPackResources
 *  net.minecraft.server.packs.repository.PackRepository
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package noppes.npcs.mixin;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.PackRepository;
import noppes.npcs.CustomNpcs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PackRepository.class})
public class PackRepositoryMixin {
    @Inject(at={@At(value="TAIL")}, method={"openAllSelected"}, cancellable=true)
    private void reload(CallbackInfoReturnable<List<PackResources>> ci) {
        ArrayList<PathPackResources> l = new ArrayList<PathPackResources>((Collection)ci.getReturnValue());
        l.add(new PathPackResources("cnpcs", CustomNpcs.Dir.toPath(), false));
        ci.setReturnValue((Object)ImmutableList.copyOf(l));
    }
}

