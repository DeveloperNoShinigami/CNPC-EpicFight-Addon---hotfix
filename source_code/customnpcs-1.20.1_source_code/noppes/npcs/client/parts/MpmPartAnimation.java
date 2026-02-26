/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package noppes.npcs.client.parts;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import noppes.npcs.api.CustomNPCsException;
import noppes.npcs.api.constants.AnimationType;
import noppes.npcs.client.parts.AnimationContainer;
import noppes.npcs.client.parts.ModelPartWrapper;
import noppes.npcs.client.parts.MpmPart;
import noppes.npcs.client.parts.MpmPartReader;
import noppes.npcs.shared.common.util.NopVector3f;

public class MpmPartAnimation {
    private Map<Integer, ModelPartWrapper[]> animations = new HashMap<Integer, ModelPartWrapper[]>();

    public void load(List<AnimationContainer> animationsList, MpmPart part) {
        if (animationsList == null || animationsList.size() == 0) {
            return;
        }
        animationsList.forEach(container -> {
            ModelPartWrapper[] list = this.animations.computeIfAbsent(container.animation, k -> new ModelPartWrapper[0]);
            ModelPartWrapper model = part.getPart(container.part);
            if (model != null) {
                if (container.additional) {
                    container = container.copy();
                    for (int i = 0; i < container.actualLength; ++i) {
                        if (i < container.length) {
                            if (container.hasTranslate) {
                                container.translates[i] = container.translates[i].add(model.oriPos);
                            }
                            if (!container.hasRotation) continue;
                            container.rotations[i] = container.rotations[i].add(model.oriRot);
                            continue;
                        }
                        if (container.hasTranslate) {
                            container.translates[i] = container.translates[container.length - i % container.length - 2];
                        }
                        if (!container.hasRotation) continue;
                        container.rotations[i] = container.rotations[container.length - i % container.length - 2];
                    }
                }
                model.animations.put(container.animation, (AnimationContainer)container);
                list = Arrays.copyOf(list, list.length + 1);
                list[list.length - 1] = model;
                this.animations.put(container.animation, list);
            }
        });
    }

    public static List<AnimationContainer> loadAnimations(JsonObject json) {
        ArrayList<AnimationContainer> list = new ArrayList<AnimationContainer>();
        if (json == null || json.size() == 0) {
            return list;
        }
        for (Map.Entry entry : json.entrySet()) {
            try {
                int animation = AnimationType.valueOf(((String)entry.getKey()).toUpperCase());
                JsonObject animationData = ((JsonElement)entry.getValue()).getAsJsonObject();
                int length = animationData.get("animation_length").getAsInt();
                float speed = animationData.get("animation_speed").getAsFloat();
                boolean loop = animationData.has("loop") && animationData.get("loop").getAsBoolean();
                boolean additional = animationData.has("additional") && animationData.get("additional").getAsBoolean();
                for (Map.Entry bone : animationData.get("bones").getAsJsonObject().entrySet()) {
                    int i;
                    list.removeIf(c -> c.animation == animation && c.part.equals(bone.getKey()));
                    AnimationContainer con = new AnimationContainer(animation, (String)bone.getKey(), length, speed, additional, loop);
                    list.add(con);
                    JsonObject boneAnimation = ((JsonElement)bone.getValue()).getAsJsonObject();
                    if (boneAnimation.has("rotation")) {
                        con.hasRotation = true;
                        JsonArray rotArray = boneAnimation.get("rotation").getAsJsonArray();
                        for (i = 0; i < con.actualLength; ++i) {
                            con.rotations[i] = i < length ? MpmPartReader.jsonVector3f(rotArray.get(i)).mul((float)Math.PI / 180) : con.rotations[length - i % length - 2];
                        }
                    }
                    if (boneAnimation.has("translate")) {
                        con.hasTranslate = true;
                        JsonArray tranArray = boneAnimation.get("translate").getAsJsonArray();
                        for (i = 0; i < con.actualLength; ++i) {
                            con.translates[i] = i < length ? MpmPartReader.jsonVector3f(tranArray.get(i)) : con.translates[length - i % length - 2];
                        }
                    } else {
                        for (int i2 = 0; i2 < con.actualLength; ++i2) {
                            con.translates[i2] = NopVector3f.ZERO;
                        }
                    }
                    if (boneAnimation.has("scale")) {
                        con.hasScale = true;
                        JsonArray tranArray = boneAnimation.get("scale").getAsJsonArray();
                        for (i = 0; i < con.actualLength; ++i) {
                            con.scale[i] = i < length ? MpmPartReader.jsonVector3f(tranArray.get(i)) : con.scale[length - i % length - 2];
                        }
                        continue;
                    }
                    for (int i3 = 0; i3 < con.actualLength; ++i3) {
                        con.scale[i3] = NopVector3f.ZERO;
                    }
                }
            }
            catch (Exception e) {
                throw new CustomNPCsException(e, "Error in animation: " + (String)entry.getKey(), new Object[0]);
            }
        }
        return list;
    }

    public void start(int animation) {
        ModelPartWrapper[] models = this.animations.get(animation);
        if (models != null) {
            for (int i = 0; i < models.length; ++i) {
                models[i].animations.get(animation).start();
            }
        }
    }

    public boolean animation(int animation, int step, float partialTick) {
        ModelPartWrapper[] models = this.animations.get(animation);
        if (models != null) {
            for (int i = 0; i < models.length; ++i) {
                ModelPartWrapper m = models[i];
                m.animations.get(animation).animation(m, step, partialTick);
            }
            return true;
        }
        return false;
    }

    public boolean animation(int animation, float step) {
        ModelPartWrapper[] models = this.animations.get(animation);
        if (models != null) {
            for (int i = 0; i < models.length; ++i) {
                ModelPartWrapper m = models[i];
                m.animations.get(animation).animation(m, step);
            }
            return true;
        }
        return false;
    }
}

