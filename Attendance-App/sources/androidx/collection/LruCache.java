package androidx.collection;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize2) {
        if (maxSize2 > 0) {
            this.maxSize = maxSize2;
            this.map = new LinkedHashMap<>(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public void resize(int maxSize2) {
        if (maxSize2 > 0) {
            synchronized (this) {
                this.maxSize = maxSize2;
            }
            trimToSize(maxSize2);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        r1 = create(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        if (r1 != null) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r4.createCount++;
        r0 = r4.map.put(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0030, code lost:
        if (r0 == null) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0032, code lost:
        r4.map.put(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        r4.size += safeSizeOf(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0041, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0042, code lost:
        if (r0 == null) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
        entryRemoved(false, r5, r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0048, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
        trimToSize(r4.maxSize);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004e, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(K r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0055
            monitor-enter(r4)
            java.util.LinkedHashMap<K, V> r0 = r4.map     // Catch:{ all -> 0x0052 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0052 }
            if (r0 == 0) goto L_0x0013
            int r1 = r4.hitCount     // Catch:{ all -> 0x0052 }
            int r1 = r1 + 1
            r4.hitCount = r1     // Catch:{ all -> 0x0052 }
            monitor-exit(r4)     // Catch:{ all -> 0x0052 }
            return r0
        L_0x0013:
            int r1 = r4.missCount     // Catch:{ all -> 0x0052 }
            int r1 = r1 + 1
            r4.missCount = r1     // Catch:{ all -> 0x0052 }
            monitor-exit(r4)     // Catch:{ all -> 0x0052 }
            java.lang.Object r1 = r4.create(r5)
            if (r1 != 0) goto L_0x0022
            r2 = 0
            return r2
        L_0x0022:
            monitor-enter(r4)
            int r2 = r4.createCount     // Catch:{ all -> 0x004f }
            int r2 = r2 + 1
            r4.createCount = r2     // Catch:{ all -> 0x004f }
            java.util.LinkedHashMap<K, V> r2 = r4.map     // Catch:{ all -> 0x004f }
            java.lang.Object r2 = r2.put(r5, r1)     // Catch:{ all -> 0x004f }
            r0 = r2
            if (r0 == 0) goto L_0x0038
            java.util.LinkedHashMap<K, V> r2 = r4.map     // Catch:{ all -> 0x004f }
            r2.put(r5, r0)     // Catch:{ all -> 0x004f }
            goto L_0x0041
        L_0x0038:
            int r2 = r4.size     // Catch:{ all -> 0x004f }
            int r3 = r4.safeSizeOf(r5, r1)     // Catch:{ all -> 0x004f }
            int r2 = r2 + r3
            r4.size = r2     // Catch:{ all -> 0x004f }
        L_0x0041:
            monitor-exit(r4)     // Catch:{ all -> 0x004f }
            if (r0 == 0) goto L_0x0049
            r2 = 0
            r4.entryRemoved(r2, r5, r1, r0)
            return r0
        L_0x0049:
            int r2 = r4.maxSize
            r4.trimToSize(r2)
            return r1
        L_0x004f:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x004f }
            throw r2
        L_0x0052:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0052 }
            throw r0
        L_0x0055:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "key == null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LruCache.get(java.lang.Object):java.lang.Object");
    }

    public final V put(K key, V value) {
        V previous;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(key, value);
            previous = this.map.put(key, value);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, value);
        }
        trimToSize(this.maxSize);
        return previous;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0073, code lost:
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r6) {
        /*
            r5 = this;
        L_0x0001:
            monitor-enter(r5)
            int r0 = r5.size     // Catch:{ all -> 0x0074 }
            if (r0 < 0) goto L_0x0053
            java.util.LinkedHashMap<K, V> r0 = r5.map     // Catch:{ all -> 0x0074 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0074 }
            if (r0 == 0) goto L_0x0012
            int r0 = r5.size     // Catch:{ all -> 0x0074 }
            if (r0 != 0) goto L_0x0053
        L_0x0012:
            int r0 = r5.size     // Catch:{ all -> 0x0074 }
            if (r0 <= r6) goto L_0x0051
            java.util.LinkedHashMap<K, V> r0 = r5.map     // Catch:{ all -> 0x0074 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0074 }
            if (r0 == 0) goto L_0x001f
            goto L_0x0051
        L_0x001f:
            java.util.LinkedHashMap<K, V> r0 = r5.map     // Catch:{ all -> 0x0074 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ all -> 0x0074 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0074 }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x0074 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x0074 }
            java.lang.Object r1 = r0.getKey()     // Catch:{ all -> 0x0074 }
            java.lang.Object r2 = r0.getValue()     // Catch:{ all -> 0x0074 }
            java.util.LinkedHashMap<K, V> r3 = r5.map     // Catch:{ all -> 0x0074 }
            r3.remove(r1)     // Catch:{ all -> 0x0074 }
            int r3 = r5.size     // Catch:{ all -> 0x0074 }
            int r4 = r5.safeSizeOf(r1, r2)     // Catch:{ all -> 0x0074 }
            int r3 = r3 - r4
            r5.size = r3     // Catch:{ all -> 0x0074 }
            int r3 = r5.evictionCount     // Catch:{ all -> 0x0074 }
            r4 = 1
            int r3 = r3 + r4
            r5.evictionCount = r3     // Catch:{ all -> 0x0074 }
            monitor-exit(r5)     // Catch:{ all -> 0x0074 }
            r0 = 0
            r5.entryRemoved(r4, r1, r2, r0)
            goto L_0x0001
        L_0x0051:
            monitor-exit(r5)     // Catch:{ all -> 0x0074 }
            return
        L_0x0053:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0074 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0074 }
            r1.<init>()     // Catch:{ all -> 0x0074 }
            java.lang.Class r2 = r5.getClass()     // Catch:{ all -> 0x0074 }
            java.lang.String r2 = r2.getName()     // Catch:{ all -> 0x0074 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0074 }
            java.lang.String r2 = ".sizeOf() is reporting inconsistent results!"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0074 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0074 }
            r0.<init>(r1)     // Catch:{ all -> 0x0074 }
            throw r0     // Catch:{ all -> 0x0074 }
        L_0x0074:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0074 }
            goto L_0x0078
        L_0x0077:
            throw r0
        L_0x0078:
            goto L_0x0077
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LruCache.trimToSize(int):void");
    }

    public final V remove(K key) {
        V previous;
        if (key != null) {
            synchronized (this) {
                previous = this.map.remove(key);
                if (previous != null) {
                    this.size -= safeSizeOf(key, previous);
                }
            }
            if (previous != null) {
                entryRemoved(false, key, previous, (V) null);
            }
            return previous;
        }
        throw new NullPointerException("key == null");
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean evicted, K k, V v, V v2) {
    }

    /* access modifiers changed from: protected */
    public V create(K k) {
        return null;
    }

    private int safeSizeOf(K key, V value) {
        int result = sizeOf(key, value);
        if (result >= 0) {
            return result;
        }
        throw new IllegalStateException("Negative size: " + key + "=" + value);
    }

    /* access modifiers changed from: protected */
    public int sizeOf(K k, V v) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int i;
        int accesses;
        i = this.hitCount;
        accesses = this.missCount + i;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(accesses != 0 ? (i * 100) / accesses : 0)});
    }
}
