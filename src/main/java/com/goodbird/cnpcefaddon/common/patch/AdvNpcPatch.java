package com.goodbird.cnpcefaddon.common.patch;

/*
 * import com.goodbird.cnpcefaddon.common.provider.AdvNpcPatchProvider;
 * import com.goodbird.cnpcefaddon.common.provider.NpcPatchProvider;
 * import com.nameless.indestructible.data.AdvancedMobpatchReloader;
 * import
 * com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
 * import net.minecraft.world.entity.PathfinderMob;
 * import noppes.npcs.entity.EntityNPCInterface;
 * import yesman.epicfight.api.utils.math.OpenMatrix4f;
 * import yesman.epicfight.main.EpicFightMod;
 * import yesman.epicfight.world.capabilities.entitypatch.Faction;
 * 
 * public class AdvNpcPatch<T extends PathfinderMob> extends
 * AdvancedCustomHumanoidMobPatch<T> implements INpcPatch {
 * AdvNpcPatchProvider provider;
 * 
 * public AdvNpcPatch(Faction faction, AdvNpcPatchProvider provider) {
 * super(faction, provider);
 * this.provider = provider;
 * }
 * 
 * public void onConstructed(T entityIn) {
 * try{
 * this.original = entityIn;
 * this.armature = provider.armature.deepCopy();
 * super.onConstructed(entityIn);
 * }catch (Exception e){
 * 
 * }
 * }
 * 
 * public OpenMatrix4f getModelMatrix(float partialTicks) {
 * float scale = ((EntityNPCInterface)original).display.getSize()/5f;
 * return super.getModelMatrix(partialTicks).scale(scale, scale, scale);
 * }
 * }
 */
