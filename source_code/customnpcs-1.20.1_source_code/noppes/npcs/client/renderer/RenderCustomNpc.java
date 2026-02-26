/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.EntityModel
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.HumanoidModel$ArmPose
 *  net.minecraft.client.model.Model
 *  net.minecraft.client.model.PlayerModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.CustomHeadLayer
 *  net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer
 *  net.minecraft.client.renderer.entity.layers.ItemInHandLayer
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.UseAnim
 *  net.minecraft.world.phys.Vec3
 */
package noppes.npcs.client.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.ModelData;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.client.layer.LayerGlow;
import noppes.npcs.client.layer.LayerHeadwear;
import noppes.npcs.client.layer.LayerNpcCloak;
import noppes.npcs.client.layer.LayerNpcElytra;
import noppes.npcs.client.layer.LayerParts;
import noppes.npcs.client.layer.LayerPreRender;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.constants.BodyPart;
import noppes.npcs.controllers.CobblemonHelper;
import noppes.npcs.controllers.PixelmonHelper;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.mixin.ArmorLayerMixin;
import noppes.npcs.mixin.LivingRenderer2Mixin;
import noppes.npcs.mixin.LivingRenderer3Mixin;

public class RenderCustomNpc<T extends EntityCustomNpc, M extends HumanoidModel<T>>
extends RenderNPCInterface<T, M> {
    private float partialTicks;
    private LivingEntity entity;
    private EntityNPCInterface npc;
    private LivingEntityRenderer renderEntity;
    public M npcmodel;
    public Model otherModel;
    public ArmorLayerMixin armorLayer;
    public List<RenderLayer<T, M>> npclayers = Lists.newArrayList();
    private boolean droppedScale = false;
    private RenderLayer renderLayer = new RenderLayer(null){

        public void m_6494_(PoseStack mStack, MultiBufferSource typeBuffer, int lightmapUV, Entity p_225628_4_, float limbSwing, float limbSwingAmount, float partialTicks, float age, float netHeadYaw, float headPitch) {
            if (RenderCustomNpc.this.npc.display.isShowingLayers()) {
                for (RenderLayer layer : ((LivingRenderer2Mixin)RenderCustomNpc.this.renderEntity).layers()) {
                    layer.m_6494_(mStack, typeBuffer, lightmapUV, (Entity)RenderCustomNpc.this.entity, limbSwing, limbSwingAmount, partialTicks, age, netHeadYaw, headPitch);
                }
            }
        }
    };
    private final HumanoidModel renderModel;

    public RenderCustomNpc(EntityRendererProvider.Context manager, M model) {
        super(manager, model, 0.5f);
        this.npcmodel = model;
        this.m_115326_((RenderLayer)new CustomHeadLayer((RenderLayerParent)this, manager.m_174027_(), manager.m_234598_()));
        this.m_115326_(new LayerHeadwear(this));
        this.m_115326_(new LayerNpcCloak(this));
        this.m_115326_(new LayerNpcElytra(manager, this));
        this.m_115326_(new LayerParts(this));
        this.m_115326_((RenderLayer)new ItemInHandLayer((RenderLayerParent)this, manager.m_234598_()));
        this.m_115326_(new LayerGlow(this));
        HumanoidArmorLayer armorLayer = new HumanoidArmorLayer((RenderLayerParent)this, new HumanoidModel(manager.m_174023_(ModelLayers.f_171164_)), new HumanoidModel(manager.m_174023_(ModelLayers.f_171165_)), manager.m_266367_());
        this.m_115326_((RenderLayer)armorLayer);
        this.armorLayer = (ArmorLayerMixin)armorLayer;
        this.renderModel = new HumanoidModel(manager.m_174023_(ModelLayers.f_171162_)){

            public void m_7695_(PoseStack mStack, VertexConsumer iVertex, int lightmapUV, int packedOverlayIn, float red, float green, float blue, float alpha) {
                int color = RenderCustomNpc.this.npc.display.getTint();
                if (color < 0xFFFFFF) {
                    red = (float)(color >> 16 & 0xFF) / 255.0f;
                    green = (float)(color >> 8 & 0xFF) / 255.0f;
                    blue = (float)(color & 0xFF) / 255.0f;
                }
                RenderCustomNpc.this.otherModel.m_7695_(mStack, iVertex, lightmapUV, packedOverlayIn, red, green, blue, alpha);
            }

            public void m_6973_(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
                if (RenderCustomNpc.this.otherModel instanceof EntityModel) {
                    EntityModel em = (EntityModel)RenderCustomNpc.this.otherModel;
                    em.m_6973_((Entity)RenderCustomNpc.this.entity, limbSwing, limbSwingAmount, ((LivingRenderer3Mixin)RenderCustomNpc.this.renderEntity).callGetBob(RenderCustomNpc.this.entity, Minecraft.m_91087_().getPartialTick()), netHeadYaw, headPitch);
                }
            }

            public void m_6839_(Entity npc, float animationPos, float animationSpeed, float partialTicks) {
                Model pixModel;
                if (PixelmonHelper.isPixelmon((Entity)RenderCustomNpc.this.entity) && (pixModel = (Model)PixelmonHelper.getModel(RenderCustomNpc.this.entity)) != null) {
                    RenderCustomNpc.this.otherModel = pixModel;
                    PixelmonHelper.setupModel(RenderCustomNpc.this.entity, pixModel);
                }
                if (RenderCustomNpc.this.otherModel instanceof HumanoidModel) {
                    HumanoidModel bm = (HumanoidModel)RenderCustomNpc.this.otherModel;
                    bm.f_102818_ = ((EntityCustomNpc)npc).m_20998_(partialTicks);
                    bm.f_102817_ = ((HumanoidModel)RenderCustomNpc.this.npcmodel).f_102817_;
                }
                if (RenderCustomNpc.this.otherModel instanceof EntityModel) {
                    EntityModel em = (EntityModel)RenderCustomNpc.this.otherModel;
                    em.f_102609_ = RenderCustomNpc.this.entity.m_20159_() && RenderCustomNpc.this.entity.m_20202_() != null && RenderCustomNpc.this.entity.m_20202_().shouldRiderSit();
                    em.f_102610_ = RenderCustomNpc.this.entity.m_6162_();
                    em.f_102608_ = RenderCustomNpc.this.m_115342_((LivingEntity)((EntityCustomNpc)npc), partialTicks);
                    em.m_6839_((Entity)RenderCustomNpc.this.entity, animationPos, animationSpeed, partialTicks);
                }
            }
        };
    }

    public Vec3 getRenderOffset(T npc, float partialTicks) {
        float xOffset = 0.0f;
        float yOffset = ((EntityCustomNpc)((Object)npc)).currentAnimation == 0 ? ((EntityCustomNpc)((Object)npc)).ais.bodyOffsetY / 10.0f - 0.5f : 0.0f;
        float zOffset = 0.0f;
        if (((EntityNPCInterface)((Object)npc)).m_6084_()) {
            if (((EntityNPCInterface)((Object)npc)).m_5803_()) {
                xOffset = (float)(-Math.cos(Math.toRadians(180 - ((EntityCustomNpc)((Object)npc)).ais.orientation)));
                zOffset = (float)(-Math.sin(Math.toRadians(((EntityCustomNpc)((Object)npc)).ais.orientation)));
                yOffset += 0.14f;
            } else if (((EntityCustomNpc)((Object)npc)).currentAnimation == 1 || npc.m_20159_()) {
                yOffset -= 0.5f - ((EntityCustomNpc)((Object)npc)).modelData.getLegsY() * 0.8f;
            } else if (((EntityNPCInterface)((Object)npc)).m_6047_()) {
                yOffset = (float)((double)yOffset - 0.125);
            }
        }
        return new Vec3((double)xOffset, (double)(yOffset * ((float)((EntityCustomNpc)((Object)npc)).display.getSize() / 5.0f)), (double)zOffset);
    }

    void hideParts() {
        if (this.npc instanceof EntityCustomNpc) {
            ModelData data = ModelData.get((EntityCustomNpc)this.npc);
            ((HumanoidModel)this.npcmodel).f_102814_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.LEFT_LEG);
            ((HumanoidModel)this.npcmodel).f_102813_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.RIGHT_LEG);
            ((HumanoidModel)this.npcmodel).f_102812_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.LEFT_ARM);
            ((HumanoidModel)this.npcmodel).f_102811_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.RIGHT_ARM);
            ((HumanoidModel)this.npcmodel).f_102810_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.BODY);
            ((HumanoidModel)this.npcmodel).f_102808_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.HEAD);
            boolean bl = ((HumanoidModel)this.npcmodel).f_102809_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.HEAD);
            if (this.npcmodel instanceof PlayerModel) {
                PlayerModel playerModel = (PlayerModel)this.npcmodel;
                playerModel.f_103378_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.BODY);
                playerModel.f_103374_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.LEFT_ARM);
                playerModel.f_103375_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.RIGHT_ARM);
                playerModel.f_103376_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.LEFT_LEG);
                playerModel.f_103377_.f_104207_ = !data.hiddenParts.contains((Object)BodyPart.RIGHT_LEG);
            }
        }
    }

    @Override
    public void render(T npc, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        LocalPlayer playerMP;
        if (CustomNpcs.EnableInvisibleNpcs && CustomNpcs.InvisibilityAlgorithm > 0 && !((EntityCustomNpc)((Object)npc)).display.isVisibleTo((Player)(playerMP = Minecraft.m_91087_().f_91074_)) && !playerMP.m_5833_() && playerMP.m_21205_().m_41720_() != CustomItems.wand) {
            return;
        }
        this.npc = npc;
        this.partialTicks = partialTicks;
        LivingEntity prevEntity = this.entity;
        this.entity = ((EntityCustomNpc)((Object)npc)).modelData.getEntity((EntityNPCInterface)((Object)npc));
        if (prevEntity != null && this.entity == null) {
            this.f_115290_ = this.npcmodel;
            this.renderEntity = null;
            this.f_115291_.clear();
            this.f_115291_.addAll(this.npclayers);
        }
        if (this.entity != null) {
            EntityRenderer render = this.f_114476_.m_114382_((Entity)this.entity);
            if (((EntityCustomNpc)((Object)npc)).modelData.simpleRender) {
                this.renderEntity = null;
                matrixStack.m_85836_();
                render.m_7392_((Entity)this.entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
                this.renderNameTag(npc, (Component)Component.m_237119_(), matrixStack, buffer, packedLight);
                matrixStack.m_85849_();
                return;
            }
            if (render instanceof LivingEntityRenderer) {
                this.renderEntity = (LivingEntityRenderer)render;
                this.otherModel = this.renderEntity.m_7200_();
                if (CobblemonHelper.Enabled && CobblemonHelper.isPokemon((Entity)this.entity)) {
                    this.otherModel = CobblemonHelper.getPokemonModel((Entity)this.entity);
                }
                this.f_115290_ = this.renderModel;
                this.f_115291_.clear();
                this.f_115291_.add(this.renderLayer);
                this.f_115291_.add(new LayerGlow(this));
                if (render instanceof RenderCustomNpc) {
                    for (RenderLayer layer : ((LivingRenderer2Mixin)this.renderEntity).layers()) {
                        if (!(layer instanceof LayerPreRender)) continue;
                        ((LayerPreRender)layer).preRender((EntityCustomNpc)this.entity);
                    }
                }
            } else {
                this.renderEntity = null;
                this.entity = null;
                this.f_115290_ = this.npcmodel;
                this.f_115291_.clear();
                this.f_115291_.addAll(this.npclayers);
            }
        } else {
            this.hideParts();
            List list = this.f_115291_;
            for (RenderLayer layer : list) {
                if (!(layer instanceof LayerPreRender)) continue;
                ((LayerPreRender)layer).preRender((EntityCustomNpc)((Object)npc));
            }
        }
        ((HumanoidModel)this.npcmodel).f_102816_ = this.getPose(npc, ((EntityNPCInterface)((Object)npc)).m_21205_());
        ((HumanoidModel)this.npcmodel).f_102815_ = this.getPose(npc, ((EntityNPCInterface)((Object)npc)).m_21206_());
        super.render(npc, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    protected RenderType getRenderType(T p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        ResourceLocation resourcelocation = this.getTextureLocation(p_230496_1_);
        if (p_230496_2_ && this.f_115290_ == this.renderModel) {
            return this.otherModel.m_103119_(resourcelocation);
        }
        if (this.entity == null) {
            return ((HumanoidModel)this.f_115290_).m_103119_(resourcelocation);
        }
        return super.m_7225_(p_230496_1_, p_230496_2_, p_230496_3_, p_230496_4_);
    }

    public HumanoidModel.ArmPose getPose(T npc, ItemStack item) {
        if (NoppesUtilServer.IsItemStackNull(item)) {
            return HumanoidModel.ArmPose.EMPTY;
        }
        if (npc.m_21212_() > 0) {
            UseAnim enumaction = item.m_41780_();
            if (enumaction == UseAnim.BLOCK) {
                return HumanoidModel.ArmPose.BLOCK;
            }
            if (enumaction == UseAnim.BOW) {
                return HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
        }
        return HumanoidModel.ArmPose.ITEM;
    }

    @Override
    protected void scale(T npc, PoseStack matrixScale, float f) {
        if (this.renderEntity != null) {
            this.renderColor((EntityNPCInterface)((Object)npc));
            int size = ((EntityCustomNpc)((Object)npc)).display.getSize();
            if (this.entity instanceof EntityNPCInterface) {
                ((EntityNPCInterface)this.entity).display.setSize(5);
            }
            EntityRenderer render = this.f_114476_.m_114382_((Entity)this.entity);
            if (!((EntityCustomNpc)((Object)npc)).modelData.simpleRender && render instanceof LivingEntityRenderer) {
                matrixScale.m_85836_();
                ((LivingRenderer3Mixin)render).callScale(this.entity, matrixScale, this.partialTicks);
                if (matrixScale.m_85850_().m_252943_().isFinite()) {
                    matrixScale.m_85849_();
                    ((LivingRenderer3Mixin)render).callScale(this.entity, matrixScale, this.partialTicks);
                } else {
                    matrixScale.m_85849_();
                }
            }
            ((EntityCustomNpc)((Object)npc)).display.setSize(size);
            matrixScale.m_85841_(0.2f * (float)((EntityCustomNpc)((Object)npc)).display.getSize(), 0.2f * (float)((EntityCustomNpc)((Object)npc)).display.getSize(), 0.2f * (float)((EntityCustomNpc)((Object)npc)).display.getSize());
        } else {
            super.scale(npc, matrixScale, f);
        }
    }
}

