/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.Font$DisplayMode
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderStateShard$CullStateShard
 *  net.minecraft.client.renderer.RenderStateShard$DepthTestStateShard
 *  net.minecraft.client.renderer.RenderStateShard$LightmapStateShard
 *  net.minecraft.client.renderer.RenderStateShard$ShaderStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TransparencyStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.world.entity.player.Player
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Vector4f
 */
package noppes.npcs.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.player.Player;
import noppes.npcs.CustomNpcs;
import noppes.npcs.IChatMessages;
import noppes.npcs.client.TextBlockClient;
import noppes.npcs.entity.EntityNPCInterface;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;

public class ChatMessages
implements IChatMessages {
    private static Map<String, ChatMessages> users = new Hashtable<String, ChatMessages>();
    protected static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    private static final RenderStateShard.ShaderStateShard sharder = new RenderStateShard.ShaderStateShard(GameRenderer::m_172832_);
    protected static final RenderType type = RenderType.m_173215_((String)"chatbubble", (VertexFormat)DefaultVertexFormat.f_85816_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)false, (boolean)false, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_110661_(new RenderStateShard.CullStateShard(true)).m_110671_(new RenderStateShard.LightmapStateShard(true)).m_173292_(sharder).m_110691_(true));
    protected static final RenderType typeDepth = RenderType.m_173215_((String)"chatbubbledepth", (VertexFormat)DefaultVertexFormat.f_85816_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)false, (boolean)true, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_110661_(new RenderStateShard.CullStateShard(true)).m_110685_(TRANSLUCENT_TRANSPARENCY).m_173292_(sharder).m_110671_(new RenderStateShard.LightmapStateShard(true)).m_110663_(new RenderStateShard.DepthTestStateShard("always", 519)).m_110691_(false));
    private Map<Long, TextBlockClient> messages = new TreeMap<Long, TextBlockClient>();
    private int boxLength = 46;
    private float scale = 0.5f;
    private String lastMessage = "";
    private long lastMessageTime = 0L;

    @Override
    public void addMessage(String message, EntityNPCInterface npc) {
        if (!CustomNpcs.EnableChatBubbles) {
            return;
        }
        long time = System.currentTimeMillis();
        if (message.equals(this.lastMessage) && this.lastMessageTime + 1000L > time) {
            return;
        }
        TreeMap<Long, TextBlockClient> messages = new TreeMap<Long, TextBlockClient>(this.messages);
        messages.put(time, new TextBlockClient(message, this.boxLength * 4, true, new Object[]{Minecraft.m_91087_().f_91074_, npc}));
        if (messages.size() > 3) {
            messages.remove(messages.keySet().iterator().next());
        }
        this.messages = messages;
        this.lastMessage = message;
        this.lastMessageTime = time;
    }

    @Override
    public void renderMessages(PoseStack PoseStack2, MultiBufferSource typeBuffer, float textscale, boolean inRange, int lightmapUV) {
        Map<Long, TextBlockClient> messages = this.getMessages();
        if (messages.isEmpty()) {
            return;
        }
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.setShader(GameRenderer::m_172832_);
        if (inRange) {
            this.render(PoseStack2, typeBuffer, typeBuffer.m_6299_(typeDepth), textscale, false, lightmapUV);
        }
        this.render(PoseStack2, typeBuffer, typeBuffer.m_6299_(type), textscale, true, lightmapUV);
    }

    public void render(PoseStack poseStack, MultiBufferSource typeBuffer, VertexConsumer ivertex, float textScale, boolean depth, int lightmapUV) {
        Font font = Minecraft.m_91087_().f_91062_;
        float var14 = 0.02666667f;
        int size = 0;
        for (TextBlockClient block : this.messages.values()) {
            size += block.lines.size();
        }
        Minecraft mc = Minecraft.m_91087_();
        Objects.requireNonNull(font);
        int textYSize = (int)((float)(size * 9) * this.scale);
        poseStack.m_85836_();
        poseStack.m_252880_(0.0f, (float)textYSize * var14, 0.0f);
        poseStack.m_85841_(textScale, textScale, textScale);
        poseStack.m_252781_(mc.m_91290_().m_253208_());
        poseStack.m_85841_(-var14, -var14, var14);
        int black = depth ? -16777216 : -16777216;
        int white = depth ? -1140850689 : 0x44FFFFFF;
        PoseStack.Pose entry = poseStack.m_85850_();
        Matrix4f matrix = entry.m_252922_();
        this.drawRect(ivertex, matrix, lightmapUV, -this.boxLength - 2, -2.0f, this.boxLength + 2, textYSize + 1, white, 0.11f);
        this.drawRect(ivertex, matrix, lightmapUV, -this.boxLength - 1, -3.0f, this.boxLength + 1, -2.0f, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, -this.boxLength - 1, textYSize + 2, -1.0f, textYSize + 1, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, 3.0f, textYSize + 2, this.boxLength + 1, textYSize + 1, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, -this.boxLength - 3, -1.0f, -this.boxLength - 2, textYSize, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, this.boxLength + 3, -1.0f, this.boxLength + 2, textYSize, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, -this.boxLength - 2, -2.0f, -this.boxLength - 1, -1.0f, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, this.boxLength + 2, -2.0f, this.boxLength + 1, -1.0f, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, -this.boxLength - 2, textYSize + 1, -this.boxLength - 1, textYSize, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, this.boxLength + 2, textYSize + 1, this.boxLength + 1, textYSize, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, 0.0f, textYSize + 1, 3.0f, textYSize + 4, white, 0.11f);
        this.drawRect(ivertex, matrix, lightmapUV, -1.0f, textYSize + 4, 1.0f, textYSize + 5, white, 0.11f);
        this.drawRect(ivertex, matrix, lightmapUV, -1.0f, textYSize + 1, 0.0f, textYSize + 4, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, 3.0f, textYSize + 1, 4.0f, textYSize + 3, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, 2.0f, textYSize + 3, 3.0f, textYSize + 4, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, 1.0f, textYSize + 4, 2.0f, textYSize + 5, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, -2.0f, textYSize + 4, -1.0f, textYSize + 5, black, 0.1f);
        this.drawRect(ivertex, matrix, lightmapUV, -2.0f, textYSize + 5, 1.0f, textYSize + 6, black, 0.1f);
        poseStack.m_85841_(this.scale, this.scale, this.scale);
        int index = 0;
        for (TextBlockClient block : this.messages.values()) {
            for (Component chat : block.lines) {
                float f = -font.m_92852_((FormattedText)chat) / 2;
                Objects.requireNonNull(font);
                font.m_272077_(chat, f, (float)(index * 9), black, false, matrix, typeBuffer, !depth ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, 0, lightmapUV);
                ++index;
            }
        }
        poseStack.m_85849_();
    }

    public void drawRect(VertexConsumer ivertex, Matrix4f matrix, int lightmapUV, float x, float y, float x2, float y2, int color, float z) {
        float j1;
        if (x < x2) {
            j1 = x;
            x = x2;
            x2 = j1;
        }
        if (y < y2) {
            j1 = y;
            y = y2;
            y2 = j1;
        }
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        this.draw(ivertex, matrix, lightmapUV, x, y, z, f1, f2, f3);
        this.draw(ivertex, matrix, lightmapUV, x, y2, z, f1, f2, f3);
        this.draw(ivertex, matrix, lightmapUV, x2, y2, z, f1, f2, f3);
        this.draw(ivertex, matrix, lightmapUV, x2, y, z, f1, f2, f3);
    }

    private void draw(VertexConsumer ivertex, Matrix4f matrix, int lightmapUV, float x, float y, float z, float red, float green, float blue) {
        Vector4f v = new Vector4f(x, y, z, 1.0f);
        v.mul((Matrix4fc)matrix);
        ivertex.m_5483_((double)v.x(), (double)v.y(), (double)v.z()).m_85950_(red, green, blue, 1.0f).m_85969_(lightmapUV).m_5752_();
    }

    public static ChatMessages getChatMessages(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        ChatMessages chat = new ChatMessages();
        users.put(username, chat);
        return chat;
    }

    private static boolean validPlayer(String username) {
        for (Player player : Minecraft.m_91087_().f_91073_.m_6907_()) {
            if (!username.equals(player.m_7755_()) && !username.equals(player.m_5446_().getString())) continue;
            return true;
        }
        return false;
    }

    private Map<Long, TextBlockClient> getMessages() {
        TreeMap<Long, TextBlockClient> messages = new TreeMap<Long, TextBlockClient>();
        long time = System.currentTimeMillis();
        for (Map.Entry<Long, TextBlockClient> entry : this.messages.entrySet()) {
            if (time > entry.getKey() + 10000L) continue;
            messages.put(entry.getKey(), entry.getValue());
        }
        this.messages = messages;
        return this.messages;
    }

    public boolean hasMessage() {
        return !this.messages.isEmpty();
    }
}

