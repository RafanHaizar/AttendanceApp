package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo112d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b'\u0018\u0000 )*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001)B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u001f\u0010\u0013\u001a\u00020\u00142\u0010\u0010\u0015\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0016H\u0000¢\u0006\u0002\b\u0017J\u0015\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001aJ\u0013\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0002J\u0018\u0010 \u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\rH\u0016J#\u0010#\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00162\u0006\u0010\u0019\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0014H\u0016J\b\u0010&\u001a\u00020'H\u0016J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u001fH\u0002J\u001c\u0010&\u001a\u00020'2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0016H\bR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\b8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006*"}, mo113d2 = {"Lkotlin/collections/AbstractMap;", "K", "V", "", "()V", "_keys", "", "_values", "", "keys", "getKeys", "()Ljava/util/Set;", "size", "", "getSize", "()I", "values", "getValues", "()Ljava/util/Collection;", "containsEntry", "", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "key", "(Ljava/lang/Object;)Z", "containsValue", "value", "equals", "other", "", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "implFindEntry", "(Ljava/lang/Object;)Ljava/util/Map$Entry;", "isEmpty", "toString", "", "o", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: AbstractMap.kt */
public abstract class AbstractMap<K, V> implements Map<K, V>, KMappedMarker {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private volatile Set<? extends K> _keys;
    private volatile Collection<? extends V> _values;

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract Set getEntries();

    public V put(K k, V v) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public V remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    protected AbstractMap() {
    }

    public final /* bridge */ Set<Map.Entry<K, V>> entrySet() {
        return getEntries();
    }

    public final /* bridge */ Set<K> keySet() {
        return getKeys();
    }

    public final /* bridge */ int size() {
        return getSize();
    }

    public final /* bridge */ Collection<V> values() {
        return getValues();
    }

    public boolean containsKey(Object key) {
        return implFindEntry(key) != null;
    }

    public boolean containsValue(Object value) {
        Iterable<Map.Entry> $this$any$iv = entrySet();
        if (($this$any$iv instanceof Collection) && ((Collection) $this$any$iv).isEmpty()) {
            return false;
        }
        for (Map.Entry it : $this$any$iv) {
            if (Intrinsics.areEqual(it.getValue(), value)) {
                return true;
            }
        }
        return false;
    }

    public final boolean containsEntry$kotlin_stdlib(Map.Entry<?, ?> entry) {
        if (entry == null) {
            return false;
        }
        Object key = entry.getKey();
        Object value = entry.getValue();
        Map map = this;
        Intrinsics.checkNotNull(map, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.get, V of kotlin.collections.MapsKt__MapsKt.get>");
        Object ourValue = map.get(key);
        if (!Intrinsics.areEqual(value, ourValue)) {
            return false;
        }
        if (ourValue != null) {
            return true;
        }
        Map map2 = this;
        Intrinsics.checkNotNull(map2, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.containsKey, *>");
        if (!map2.containsKey(key)) {
            return false;
        }
        return true;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Map) || size() != ((Map) other).size()) {
            return false;
        }
        Iterable<Map.Entry> $this$all$iv = ((Map) other).entrySet();
        if (($this$all$iv instanceof Collection) && ((Collection) $this$all$iv).isEmpty()) {
            return true;
        }
        for (Map.Entry it : $this$all$iv) {
            if (!containsEntry$kotlin_stdlib(it)) {
                return false;
            }
        }
        return true;
    }

    public V get(Object key) {
        Map.Entry implFindEntry = implFindEntry(key);
        if (implFindEntry != null) {
            return implFindEntry.getValue();
        }
        return null;
    }

    public int hashCode() {
        return entrySet().hashCode();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int getSize() {
        return entrySet().size();
    }

    public Set<K> getKeys() {
        if (this._keys == null) {
            this._keys = new AbstractMap$keys$1(this);
        }
        Set<? extends K> set = this._keys;
        Intrinsics.checkNotNull(set);
        return set;
    }

    public String toString() {
        return CollectionsKt.joinToString$default(entrySet(), ", ", "{", "}", 0, (CharSequence) null, new AbstractMap$toString$1(this), 24, (Object) null);
    }

    /* access modifiers changed from: private */
    public final String toString(Map.Entry<? extends K, ? extends V> entry) {
        return toString((Object) entry.getKey()) + '=' + toString((Object) entry.getValue());
    }

    private final String toString(Object o) {
        return o == this ? "(this Map)" : String.valueOf(o);
    }

    public Collection<V> getValues() {
        if (this._values == null) {
            this._values = new AbstractMap$values$1(this);
        }
        Collection<? extends V> collection = this._values;
        Intrinsics.checkNotNull(collection);
        return collection;
    }

    private final Map.Entry<K, V> implFindEntry(K key) {
        Object element$iv;
        Iterator it = entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (Intrinsics.areEqual(((Map.Entry) element$iv).getKey(), (Object) key)) {
                break;
            }
        }
        return (Map.Entry) element$iv;
    }

    @Metadata(mo112d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000¢\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u000e¨\u0006\u000f"}, mo113d2 = {"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: AbstractMap.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int entryHashCode$kotlin_stdlib(Map.Entry<?, ?> e) {
            Intrinsics.checkNotNullParameter(e, "e");
            Map.Entry $this$entryHashCode_u24lambda_u2d0 = e;
            Object key = $this$entryHashCode_u24lambda_u2d0.getKey();
            int i = 0;
            int hashCode = key != null ? key.hashCode() : 0;
            Object value = $this$entryHashCode_u24lambda_u2d0.getValue();
            if (value != null) {
                i = value.hashCode();
            }
            return hashCode ^ i;
        }

        public final String entryToString$kotlin_stdlib(Map.Entry<?, ?> e) {
            Intrinsics.checkNotNullParameter(e, "e");
            Map.Entry $this$entryToString_u24lambda_u2d1 = e;
            return new StringBuilder().append($this$entryToString_u24lambda_u2d1.getKey()).append('=').append($this$entryToString_u24lambda_u2d1.getValue()).toString();
        }

        public final boolean entryEquals$kotlin_stdlib(Map.Entry<?, ?> e, Object other) {
            Intrinsics.checkNotNullParameter(e, "e");
            if ((other instanceof Map.Entry) && Intrinsics.areEqual((Object) e.getKey(), ((Map.Entry) other).getKey()) && Intrinsics.areEqual((Object) e.getValue(), ((Map.Entry) other).getValue())) {
                return true;
            }
            return false;
        }
    }
}
