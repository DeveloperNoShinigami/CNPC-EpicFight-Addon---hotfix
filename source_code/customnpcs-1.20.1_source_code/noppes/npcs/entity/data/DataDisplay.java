/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  javax.annotation.Nullable
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.StringUtil
 *  net.minecraft.world.BossEvent$BossBarColor
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.SkullBlockEntity
 *  net.minecraftforge.registries.ForgeRegistries
 */
package noppes.npcs.entity.data;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtil;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import nikedemos.markovnames.generators.MarkovGenerator;
import noppes.npcs.ModelData;
import noppes.npcs.ModelPartConfig;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.api.entity.data.INPCDisplay;
import noppes.npcs.constants.EnumParts;
import noppes.npcs.controllers.VisibilityController;
import noppes.npcs.controllers.data.Availability;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.entity.data.DataPeople;
import noppes.npcs.shared.client.util.NoppesStringUtils;
import noppes.npcs.util.ValueUtil;

public class DataDisplay
implements INPCDisplay {
    EntityNPCInterface npc;
    private String name = "Noppes";
    private String title = "";
    private int markovGeneratorId = 8;
    private int markovGender = 0;
    public byte skinType = 0;
    private String url = "";
    public GameProfile playerProfile;
    private String texture = "customnpcs:textures/entity/humanmale/steve.png";
    private String cloakTexture = "";
    private String glowTexture = "";
    private boolean overlayGlowing = true;
    private boolean showLayers = true;
    private int visible = 0;
    public Availability availability = new Availability();
    private int modelSize = 5;
    private int showName = 0;
    private int skinColor = 0xFFFFFF;
    private boolean disableLivingAnimation = false;
    private byte hitboxState = 0;
    private byte showBossBar = 0;
    private BossEvent.BossBarColor bossColor = BossEvent.BossBarColor.PINK;

    public DataDisplay(EntityNPCInterface npc) {
        this.npc = npc;
        if (!npc.isClientSide()) {
            this.markovGeneratorId = new Random().nextInt(10);
            this.name = this.getRandomName();
        }
        if (npc.m_217043_().m_188503_(10) == 0) {
            DataPeople.Person p = DataPeople.get();
            this.name = p.name;
            this.title = p.title;
            if (!p.skin.isEmpty()) {
                this.texture = p.skin;
            }
        }
    }

    public String getRandomName() {
        return MarkovGenerator.fetch(this.markovGeneratorId, this.markovGender);
    }

    public CompoundTag save(CompoundTag nbttagcompound) {
        nbttagcompound.m_128359_("Name", this.name);
        nbttagcompound.m_128405_("MarkovGeneratorId", this.markovGeneratorId);
        nbttagcompound.m_128405_("MarkovGender", this.markovGender);
        nbttagcompound.m_128359_("Title", this.title);
        nbttagcompound.m_128359_("SkinUrl", this.url);
        nbttagcompound.m_128359_("Texture", this.texture);
        nbttagcompound.m_128359_("CloakTexture", this.cloakTexture);
        nbttagcompound.m_128359_("GlowTexture", this.glowTexture);
        nbttagcompound.m_128379_("OverlayGlowing", this.overlayGlowing);
        nbttagcompound.m_128379_("ShowLayers", this.showLayers);
        nbttagcompound.m_128344_("UsingSkinUrl", this.skinType);
        if (this.playerProfile != null) {
            CompoundTag nbttagcompound1 = new CompoundTag();
            NbtUtils.m_129230_((CompoundTag)nbttagcompound1, (GameProfile)this.playerProfile);
            nbttagcompound.m_128365_("SkinUsername", (Tag)nbttagcompound1);
        }
        nbttagcompound.m_128405_("Size", this.modelSize);
        nbttagcompound.m_128405_("ShowName", this.showName);
        nbttagcompound.m_128405_("SkinColor", this.skinColor);
        nbttagcompound.m_128405_("NpcVisible", this.visible);
        nbttagcompound.m_128365_("VisibleAvailability", (Tag)this.availability.save(new CompoundTag()));
        nbttagcompound.m_128379_("NoLivingAnimation", this.disableLivingAnimation);
        nbttagcompound.m_128344_("IsStatue", this.hitboxState);
        nbttagcompound.m_128344_("BossBar", this.showBossBar);
        nbttagcompound.m_128405_("BossColor", this.bossColor.ordinal());
        return nbttagcompound;
    }

    public void readToNBT(CompoundTag nbttagcompound) {
        this.setName(nbttagcompound.m_128461_("Name"));
        this.setMarkovGeneratorId(nbttagcompound.m_128451_("MarkovGeneratorId"));
        this.setMarkovGender(nbttagcompound.m_128451_("MarkovGender"));
        this.title = nbttagcompound.m_128461_("Title");
        byte prevSkinType = this.skinType;
        String prevTexture = this.texture;
        String prevUrl = this.url;
        String prevPlayer = this.getSkinPlayer();
        this.url = nbttagcompound.m_128461_("SkinUrl");
        this.skinType = nbttagcompound.m_128445_("UsingSkinUrl");
        this.texture = nbttagcompound.m_128461_("Texture");
        this.cloakTexture = nbttagcompound.m_128461_("CloakTexture");
        this.glowTexture = nbttagcompound.m_128461_("GlowTexture");
        if (nbttagcompound.m_128441_("OverlayGlowing")) {
            this.overlayGlowing = nbttagcompound.m_128471_("OverlayGlowing");
        }
        if (nbttagcompound.m_128441_("ShowLayers")) {
            this.showLayers = nbttagcompound.m_128471_("ShowLayers");
        }
        this.playerProfile = null;
        if (this.skinType == 1) {
            if (nbttagcompound.m_128425_("SkinUsername", 10)) {
                this.playerProfile = NbtUtils.m_129228_((CompoundTag)nbttagcompound.m_128469_("SkinUsername"));
            } else if (nbttagcompound.m_128425_("SkinUsername", 8) && !StringUtil.m_14408_((String)nbttagcompound.m_128461_("SkinUsername"))) {
                this.playerProfile = new GameProfile(null, nbttagcompound.m_128461_("SkinUsername"));
            }
            this.loadProfile();
        }
        this.modelSize = ValueUtil.CorrectInt(nbttagcompound.m_128451_("Size"), 1, 30);
        this.showName = nbttagcompound.m_128451_("ShowName");
        if (nbttagcompound.m_128441_("SkinColor")) {
            this.skinColor = nbttagcompound.m_128451_("SkinColor");
        }
        this.visible = nbttagcompound.m_128451_("NpcVisible");
        this.availability.load(nbttagcompound.m_128469_("VisibleAvailability"));
        VisibilityController.instance.trackNpc(this.npc);
        this.disableLivingAnimation = nbttagcompound.m_128471_("NoLivingAnimation");
        this.hitboxState = nbttagcompound.m_128445_("IsStatue");
        this.setBossbar(nbttagcompound.m_128445_("BossBar"));
        this.setBossColor(nbttagcompound.m_128451_("BossColor"));
        if (!(prevSkinType == this.skinType && this.texture.equals(prevTexture) && this.url.equals(prevUrl) && this.getSkinPlayer().equals(prevPlayer))) {
            this.npc.textureLocation = null;
        }
        this.npc.textureGlowLocation = null;
        this.npc.textureCloakLocation = null;
        this.npc.m_6210_();
    }

    public void loadProfile() {
        if (this.playerProfile != null && !StringUtil.m_14408_((String)this.playerProfile.getName())) {
            if (this.npc.m_20194_() == null) {
                SkullBlockEntity.m_155738_((GameProfile)this.playerProfile, profile -> {
                    this.playerProfile = profile;
                });
            } else {
                this.playerProfile = DataDisplay.getGameprofile(this.npc.m_20194_(), this.playerProfile);
            }
        }
    }

    private static GameProfile getGameprofile(MinecraftServer server, @Nullable GameProfile profile) {
        try {
            if (profile == null || StringUtil.m_14408_((String)profile.getName()) || profile.isComplete() && profile.getProperties().containsKey((Object)"textures")) {
                return profile;
            }
            GameProfile gameprofile = server.m_129927_().m_10996_(profile.getName()).orElse(null);
            if (gameprofile == null) {
                return profile;
            }
            Property property = (Property)Iterables.getFirst((Iterable)gameprofile.getProperties().get((Object)"textures"), (Object)null);
            if (property == null) {
                gameprofile = server.m_129925_().fillProfileProperties(gameprofile, true);
            }
            return gameprofile;
        }
        catch (Exception e) {
            return profile;
        }
    }

    public boolean showName() {
        if (this.npc.isKilled()) {
            return false;
        }
        return this.showName == 0 || this.showName == 2 && this.npc.isAttacking();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (this.name.equals(name)) {
            return;
        }
        this.name = name;
        this.npc.bossInfo.m_6456_(this.npc.m_5446_());
        this.npc.updateClient = true;
    }

    @Override
    public int getShowName() {
        return this.showName;
    }

    @Override
    public void setShowName(int type) {
        if (type == this.showName) {
            return;
        }
        this.showName = ValueUtil.CorrectInt(type, 0, 2);
        this.npc.updateClient = true;
    }

    public int getMarkovGender() {
        return this.markovGender;
    }

    public void setMarkovGender(int gender) {
        if (this.markovGender == gender) {
            return;
        }
        this.markovGender = ValueUtil.CorrectInt(gender, 0, 2);
    }

    public int getMarkovGeneratorId() {
        return this.markovGeneratorId;
    }

    public void setMarkovGeneratorId(int id) {
        if (this.markovGeneratorId == id) {
            return;
        }
        this.markovGeneratorId = ValueUtil.CorrectInt(id, 0, 9);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        if (this.title.equals(title)) {
            return;
        }
        this.title = title;
        this.npc.updateClient = true;
    }

    @Override
    public String getSkinUrl() {
        return this.url;
    }

    @Override
    public void setSkinUrl(String url) {
        if (this.url.equals(url)) {
            return;
        }
        this.url = url;
        this.skinType = url.isEmpty() ? (byte)0 : (byte)2;
        this.npc.updateClient = true;
    }

    @Override
    public String getSkinPlayer() {
        return this.playerProfile == null ? "" : this.playerProfile.getName();
    }

    @Override
    public void setSkinPlayer(String name) {
        if (name == null || name.isEmpty()) {
            this.playerProfile = null;
            this.skinType = 0;
        } else {
            this.playerProfile = new GameProfile(null, name);
            this.skinType = 1;
        }
        this.npc.updateClient = true;
    }

    @Override
    public String getSkinTexture() {
        return NoppesStringUtils.cleanResource(this.texture);
    }

    @Override
    public void setSkinTexture(String texture) {
        if (texture == null || this.texture.equals(texture)) {
            return;
        }
        this.texture = NoppesStringUtils.cleanResource(texture);
        this.npc.textureLocation = null;
        this.skinType = 0;
        this.npc.updateClient = true;
    }

    @Override
    public String getOverlayTexture() {
        return NoppesStringUtils.cleanResource(this.glowTexture);
    }

    public boolean isOverlayGlowing() {
        return this.overlayGlowing;
    }

    public void setOverlayGlowing(boolean glowing) {
        this.overlayGlowing = glowing;
    }

    public boolean isShowingLayers() {
        return this.showLayers;
    }

    public void setShowingLayers(boolean showLayers) {
        this.showLayers = showLayers;
    }

    @Override
    public void setOverlayTexture(String texture) {
        if (this.glowTexture.equals(texture)) {
            return;
        }
        this.glowTexture = NoppesStringUtils.cleanResource(texture);
        this.npc.textureGlowLocation = null;
        this.npc.updateClient = true;
    }

    @Override
    public String getCapeTexture() {
        return NoppesStringUtils.cleanResource(this.cloakTexture);
    }

    @Override
    public void setCapeTexture(String texture) {
        if (this.cloakTexture.equals(texture)) {
            return;
        }
        this.cloakTexture = NoppesStringUtils.cleanResource(texture);
        this.npc.textureCloakLocation = null;
        this.npc.updateClient = true;
    }

    @Override
    public boolean getHasLivingAnimation() {
        return !this.disableLivingAnimation;
    }

    @Override
    public void setHasLivingAnimation(boolean enabled) {
        this.disableLivingAnimation = !enabled;
        this.npc.updateClient = true;
    }

    @Override
    public int getBossbar() {
        return this.showBossBar;
    }

    @Override
    public void setBossbar(int type) {
        if (type == this.showBossBar) {
            return;
        }
        this.showBossBar = (byte)ValueUtil.CorrectInt(type, 0, 2);
        this.npc.bossInfo.m_8321_(this.showBossBar == 1);
        this.npc.updateClient = true;
    }

    @Override
    public int getBossColor() {
        return this.bossColor.ordinal();
    }

    @Override
    public void setBossColor(int color) {
        if (color < 0 || color >= BossEvent.BossBarColor.values().length) {
            throw new CustomNPCsException("Invalid Boss Color: " + color, new Object[0]);
        }
        this.bossColor = BossEvent.BossBarColor.values()[color];
        this.npc.bossInfo.m_6451_(this.bossColor);
    }

    @Override
    public int getVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(int type) {
        if (type == this.visible) {
            return;
        }
        this.visible = ValueUtil.CorrectInt(type, 0, 2);
        this.npc.updateClient = true;
    }

    @Override
    public int getSize() {
        return this.modelSize;
    }

    @Override
    public void setSize(int size) {
        if (this.modelSize == size) {
            return;
        }
        this.modelSize = ValueUtil.CorrectInt(size, 1, 30);
        this.npc.updateClient = true;
    }

    @Override
    public void setModelScale(int part, float x, float y, float z) {
        ModelData modeldata = ((EntityCustomNpc)this.npc).modelData;
        ModelPartConfig model = null;
        if (part == 0) {
            model = modeldata.getPartConfig(EnumParts.HEAD);
        } else if (part == 1) {
            model = modeldata.getPartConfig(EnumParts.BODY);
        } else if (part == 2) {
            model = modeldata.getPartConfig(EnumParts.ARM_LEFT);
        } else if (part == 3) {
            model = modeldata.getPartConfig(EnumParts.ARM_RIGHT);
        } else if (part == 4) {
            model = modeldata.getPartConfig(EnumParts.LEG_LEFT);
        } else if (part == 5) {
            model = modeldata.getPartConfig(EnumParts.LEG_RIGHT);
        }
        if (model == null) {
            throw new CustomNPCsException("Unknown part: " + part, new Object[0]);
        }
        model.setScale(x, y, z);
        this.npc.updateClient = true;
    }

    @Override
    public float[] getModelScale(int part) {
        ModelData modeldata = ((EntityCustomNpc)this.npc).modelData;
        ModelPartConfig model = null;
        if (part == 0) {
            model = modeldata.getPartConfig(EnumParts.HEAD);
        } else if (part == 1) {
            model = modeldata.getPartConfig(EnumParts.BODY);
        } else if (part == 2) {
            model = modeldata.getPartConfig(EnumParts.ARM_LEFT);
        } else if (part == 3) {
            model = modeldata.getPartConfig(EnumParts.ARM_RIGHT);
        } else if (part == 4) {
            model = modeldata.getPartConfig(EnumParts.LEG_LEFT);
        } else if (part == 5) {
            model = modeldata.getPartConfig(EnumParts.LEG_RIGHT);
        }
        if (model == null) {
            throw new CustomNPCsException("Unknown part: " + part, new Object[0]);
        }
        return new float[]{model.scaleX, model.scaleY, model.scaleZ};
    }

    @Override
    public int getTint() {
        return this.skinColor;
    }

    @Override
    public void setTint(int color) {
        if (color == this.skinColor) {
            return;
        }
        this.skinColor = color;
        this.npc.updateClient = true;
    }

    @Override
    public void setModel(String id) {
        ModelData modeldata = ((EntityCustomNpc)this.npc).modelData;
        if (id == null) {
            if (modeldata.getEntityName() == null) {
                return;
            }
            modeldata.setEntity(null);
            this.npc.updateClient = true;
        } else {
            ResourceLocation resource = new ResourceLocation(id);
            EntityType type = (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(resource);
            if (type == null) {
                throw new CustomNPCsException("Unknown entity id: " + id, new Object[0]);
            }
            modeldata.setEntity(resource);
            this.npc.updateClient = true;
        }
    }

    @Override
    public String getModel() {
        ModelData modeldata = ((EntityCustomNpc)this.npc).modelData;
        if (modeldata.getEntityName() == null) {
            return null;
        }
        return modeldata.getEntityName().toString();
    }

    @Override
    public byte getHitboxState() {
        return this.hitboxState;
    }

    @Override
    public void setHitboxState(byte state) {
        if (this.hitboxState == state) {
            return;
        }
        this.hitboxState = state;
        this.npc.updateClient = true;
    }

    @Override
    public boolean isVisibleTo(IPlayer player) {
        return this.isVisibleTo((Player)player.getMCEntity());
    }

    public boolean isVisibleTo(Player player) {
        if (this.visible == 1) {
            return !this.availability.isAvailable(player);
        }
        return this.availability.isAvailable(player);
    }
}

