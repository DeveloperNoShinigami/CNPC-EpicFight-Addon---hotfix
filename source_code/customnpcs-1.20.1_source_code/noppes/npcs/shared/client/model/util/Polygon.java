/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.joml.Vector3f
 */
package noppes.npcs.shared.client.model.util;

import noppes.npcs.shared.client.model.util.Vertex;
import org.joml.Vector3f;

public class Polygon {
    public final Vector3f normal;
    public final Vertex[] vertexes;

    public Polygon(Vector3f normal, Vertex ... vertexes) {
        this.normal = normal;
        this.vertexes = vertexes;
    }
}

