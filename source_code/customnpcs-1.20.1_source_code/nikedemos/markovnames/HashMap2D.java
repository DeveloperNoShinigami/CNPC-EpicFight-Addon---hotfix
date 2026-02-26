/*
 * Decompiled with CFR 0.152.
 */
package nikedemos.markovnames;

import java.util.HashMap;
import java.util.Map;

public class HashMap2D<T1, T2, T3> {
    public final Map<T1, Map<T2, T3>> mMap = new HashMap<T1, Map<T2, T3>>();

    public T3 put(T1 key1, T2 key2, T3 value) {
        Map<Object, Object> map;
        if (this.mMap.containsKey(key1)) {
            map = this.mMap.get(key1);
        } else {
            map = new HashMap();
            this.mMap.put(key1, map);
        }
        return (T3)map.put(key2, value);
    }

    public T3 get(T1 key1, T2 key2) {
        if (this.mMap.containsKey(key1)) {
            return this.mMap.get(key1).get(key2);
        }
        return null;
    }

    public boolean containsKeys(T1 key1, T2 key2) {
        return this.mMap.containsKey(key1) && this.mMap.get(key1).containsKey(key2);
    }

    public void clear() {
        this.mMap.clear();
    }
}

