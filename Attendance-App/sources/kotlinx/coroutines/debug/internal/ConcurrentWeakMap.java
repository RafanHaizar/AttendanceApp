package kotlinx.coroutines.debug.internal;

import com.itextpdf.forms.xfdf.XfdfConstants;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt;

@Metadata(mo112d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0003*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010*:\u0003&'(B\u0011\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\nH\u0016¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\nH\u0002¢\u0006\u0004\b\u000f\u0010\u000eJ\u001a\u0010\u0011\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0014\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0001H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J#\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\b\u0010\u0013\u001a\u0004\u0018\u00018\u0001H\u0002¢\u0006\u0004\b\u0016\u0010\u0015J\u0019\u0010\u0017\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u0017\u0010\u0012J\r\u0010\u0018\u001a\u00020\n¢\u0006\u0004\b\u0018\u0010\u000eR&\u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001a0\u00198VX\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00198VX\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u0014\u0010#\u001a\u00020 8VX\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001c\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$8\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010%¨\u0006)"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "K", "V", "", "weakRefQueue", "<init>", "(Z)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "w", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "clear", "()V", "decrementSize", "key", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "value", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putSynchronized", "remove", "runWeakRefQueueCleaningLoopUntilInterrupted", "", "", "getEntries", "()Ljava/util/Set;", "entries", "getKeys", "keys", "", "getSize", "()I", "size", "Ljava/lang/ref/ReferenceQueue;", "Ljava/lang/ref/ReferenceQueue;", "Core", "Entry", "KeyValueSet", "kotlinx-coroutines-core", "Lkotlin/collections/AbstractMutableMap;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ConcurrentWeakMap.kt */
public final class ConcurrentWeakMap<K, V> extends AbstractMutableMap<K, V> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater _size$FU = AtomicIntegerFieldUpdater.newUpdater(ConcurrentWeakMap.class, "_size");
    private volatile /* synthetic */ int _size;
    volatile /* synthetic */ Object core;
    /* access modifiers changed from: private */
    public final ReferenceQueue<K> weakRefQueue;

    public ConcurrentWeakMap() {
        this(false, 1, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ConcurrentWeakMap(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    public ConcurrentWeakMap(boolean weakRefQueue2) {
        this._size = 0;
        this.core = new Core(16);
        this.weakRefQueue = weakRefQueue2 ? new ReferenceQueue<>() : null;
    }

    public int getSize() {
        return this._size;
    }

    /* access modifiers changed from: private */
    public final void decrementSize() {
        _size$FU.decrementAndGet(this);
    }

    public V get(Object key) {
        if (key == null) {
            return null;
        }
        return ((Core) this.core).getImpl(key);
    }

    public V put(K key, V value) {
        Object oldValue = Core.putImpl$default((Core) this.core, key, value, (HashedWeakRef) null, 4, (Object) null);
        if (oldValue == ConcurrentWeakMapKt.REHASH) {
            oldValue = putSynchronized(key, value);
        }
        if (oldValue == null) {
            _size$FU.incrementAndGet(this);
        }
        return oldValue;
    }

    public V remove(Object key) {
        if (key == null) {
            return null;
        }
        Object oldValue = Core.putImpl$default((Core) this.core, key, (Object) null, (HashedWeakRef) null, 4, (Object) null);
        if (oldValue == ConcurrentWeakMapKt.REHASH) {
            oldValue = putSynchronized(key, (Object) null);
        }
        if (oldValue != null) {
            _size$FU.decrementAndGet(this);
        }
        return oldValue;
    }

    private final synchronized V putSynchronized(K key, V value) {
        Object oldValue;
        Core curCore = (Core) this.core;
        while (true) {
            oldValue = Core.putImpl$default(curCore, key, value, (HashedWeakRef) null, 4, (Object) null);
            if (oldValue == ConcurrentWeakMapKt.REHASH) {
                curCore = curCore.rehash();
                this.core = curCore;
            }
        }
        return oldValue;
    }

    public Set<K> getKeys() {
        return new KeyValueSet<>(ConcurrentWeakMap$keys$1.INSTANCE);
    }

    public Set<Map.Entry<K, V>> getEntries() {
        return new KeyValueSet<>(ConcurrentWeakMap$entries$1.INSTANCE);
    }

    public void clear() {
        for (Object k : keySet()) {
            remove(k);
        }
    }

    public final void runWeakRefQueueCleaningLoopUntilInterrupted() {
        if (this.weakRefQueue != null) {
            while (true) {
                try {
                    Reference<? extends K> remove = this.weakRefQueue.remove();
                    if (remove != null) {
                        cleanWeakRef((HashedWeakRef) remove);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.debug.internal.HashedWeakRef<*>");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } else {
            throw new IllegalStateException("Must be created with weakRefQueue = true".toString());
        }
    }

    private final void cleanWeakRef(HashedWeakRef<?> w) {
        ((Core) this.core).cleanWeakRef(w);
    }

    @Metadata(mo112d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0004\u0018\u00002\u00020\u0018:\u0001#B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0019\u0010\b\u001a\u00020\u00072\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ3\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00020\u0013\"\u0004\b\u0002\u0010\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0011¢\u0006\u0004\b\u0014\u0010\u0015J3\u0010\u0019\u001a\u0004\u0018\u00010\u00182\u0006\u0010\n\u001a\u00028\u00002\b\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0010\b\u0002\u0010\u0017\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005¢\u0006\u0004\b\u0019\u0010\u001aJ\u001d\u0010\u001c\u001a\u00120\u0000R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001b¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010 R\u0014\u0010!\u001a\u00020\u00018\u0002X\u0004¢\u0006\u0006\n\u0004\b!\u0010 R\u0014\u0010\"\u001a\u00020\u00018\u0002X\u0004¢\u0006\u0006\n\u0004\b\"\u0010 ¨\u0006$"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "", "allocated", "<init>", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;I)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "weakRef", "", "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "key", "getImpl", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "index", "(I)I", "E", "Lkotlin/Function2;", "factory", "", "keyValueIterator", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", "value", "weakKey0", "", "putImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/coroutines/debug/internal/HashedWeakRef;)Ljava/lang/Object;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "rehash", "()Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "removeCleanedAt", "(I)V", "I", "shift", "threshold", "KeyValueIterator", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: ConcurrentWeakMap.kt */
    private final class Core {
        private static final /* synthetic */ AtomicIntegerFieldUpdater load$FU = AtomicIntegerFieldUpdater.newUpdater(Core.class, "load");
        /* access modifiers changed from: private */
        public final int allocated;
        /* synthetic */ AtomicReferenceArray keys;
        private volatile /* synthetic */ int load = 0;
        private final int shift;
        private final int threshold;
        /* synthetic */ AtomicReferenceArray values;

        public Core(int allocated2) {
            this.allocated = allocated2;
            this.shift = Integer.numberOfLeadingZeros(allocated2) + 1;
            this.threshold = (allocated2 * 2) / 3;
            this.keys = new AtomicReferenceArray(allocated2);
            this.values = new AtomicReferenceArray(allocated2);
        }

        private final int index(int hash) {
            return (-1640531527 * hash) >>> this.shift;
        }

        public final V getImpl(K key) {
            int index = index(key.hashCode());
            while (true) {
                HashedWeakRef w = (HashedWeakRef) this.keys.get(index);
                if (w == null) {
                    return null;
                }
                Object k = w.get();
                if (Intrinsics.areEqual((Object) key, k)) {
                    Object value = this.values.get(index);
                    return value instanceof Marked ? ((Marked) value).ref : value;
                }
                if (k == null) {
                    removeCleanedAt(index);
                }
                if (index == 0) {
                    index = this.allocated;
                }
                index--;
            }
        }

        private final void removeCleanedAt(int index) {
            Object oldValue;
            do {
                oldValue = this.values.get(index);
                if (oldValue == null || (oldValue instanceof Marked)) {
                    return;
                }
            } while (!ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(this.values, index, oldValue, (Object) null));
            ConcurrentWeakMap.this.decrementSize();
        }

        public static /* synthetic */ Object putImpl$default(Core core, Object obj, Object obj2, HashedWeakRef hashedWeakRef, int i, Object obj3) {
            if ((i & 4) != 0) {
                hashedWeakRef = null;
            }
            return core.putImpl(obj, obj2, hashedWeakRef);
        }

        public final Object putImpl(K key, V value, HashedWeakRef<K> weakKey0) {
            Object oldValue;
            int cur$iv;
            int n;
            int index = index(key.hashCode());
            boolean loadIncremented = false;
            HashedWeakRef<K> hashedWeakRef = weakKey0;
            while (true) {
                HashedWeakRef w = (HashedWeakRef) this.keys.get(index);
                if (w != null) {
                    Object k = w.get();
                    if (Intrinsics.areEqual((Object) key, k)) {
                        if (loadIncremented) {
                            load$FU.decrementAndGet(this);
                        }
                        boolean z = loadIncremented;
                        HashedWeakRef<K> hashedWeakRef2 = hashedWeakRef;
                    } else {
                        if (k == null) {
                            removeCleanedAt(index);
                        }
                        if (index == 0) {
                            index = this.allocated;
                        }
                        index--;
                    }
                } else if (value == null) {
                    return null;
                } else {
                    if (!loadIncremented) {
                        do {
                            cur$iv = this.load;
                            n = cur$iv;
                            if (n >= this.threshold) {
                                return ConcurrentWeakMapKt.REHASH;
                            }
                        } while (!load$FU.compareAndSet(this, cur$iv, n + 1));
                        loadIncremented = true;
                    }
                    if (hashedWeakRef == null) {
                        hashedWeakRef = new HashedWeakRef<>(key, ConcurrentWeakMap.this.weakRefQueue);
                    }
                    if (ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(this.keys, index, (Object) null, hashedWeakRef)) {
                        boolean z2 = loadIncremented;
                        HashedWeakRef<K> hashedWeakRef3 = hashedWeakRef;
                        break;
                    }
                }
            }
            do {
                oldValue = this.values.get(index);
                if (oldValue instanceof Marked) {
                    return ConcurrentWeakMapKt.REHASH;
                }
            } while (!ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(this.values, index, oldValue, value));
            return oldValue;
        }

        public final ConcurrentWeakMap<K, V>.Core rehash() {
            Object value;
            while (true) {
                Core newCore = new Core(Integer.highestOneBit(RangesKt.coerceAtLeast(ConcurrentWeakMap.this.size(), 4)) * 4);
                int i = this.allocated;
                int i2 = 0;
                while (true) {
                    if (i2 >= i) {
                        return newCore;
                    }
                    int index = i2;
                    i2++;
                    HashedWeakRef w = (HashedWeakRef) this.keys.get(index);
                    Object k = w == null ? null : w.get();
                    if (w != null && k == null) {
                        removeCleanedAt(index);
                    }
                    while (true) {
                        value = this.values.get(index);
                        if (!(value instanceof Marked)) {
                            if (ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(this.values, index, value, ConcurrentWeakMapKt.mark(value))) {
                                break;
                            }
                        } else {
                            value = ((Marked) value).ref;
                            break;
                        }
                    }
                    if (!(k == null || value == null)) {
                        Object oldValue = newCore.putImpl(k, value, w);
                        if (oldValue != ConcurrentWeakMapKt.REHASH) {
                            if (!(oldValue == null)) {
                                throw new AssertionError("Assertion failed");
                            }
                        }
                    }
                }
            }
        }

        public final void cleanWeakRef(HashedWeakRef<?> weakRef) {
            int index = index(weakRef.hash);
            while (true) {
                HashedWeakRef w = (HashedWeakRef) this.keys.get(index);
                if (w != null) {
                    if (w == weakRef) {
                        removeCleanedAt(index);
                        return;
                    }
                    if (index == 0) {
                        index = this.allocated;
                    }
                    index--;
                } else {
                    return;
                }
            }
        }

        public final <E> Iterator<E> keyValueIterator(Function2<? super K, ? super V, ? extends E> factory) {
            return new KeyValueIterator<>(factory);
        }

        @Metadata(mo112d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0002J\t\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00028\u0002H\u0002¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00028\u0000X.¢\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\n\u001a\u00028\u0001X.¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0013"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core$KeyValueIterator;", "E", "", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;Lkotlin/jvm/functions/Function2;)V", "index", "", "key", "Ljava/lang/Object;", "value", "findNext", "", "hasNext", "", "next", "()Ljava/lang/Object;", "remove", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
        /* compiled from: ConcurrentWeakMap.kt */
        private final class KeyValueIterator<E> implements Iterator<E>, KMutableIterator {
            private final Function2<K, V, E> factory;
            private int index = -1;
            private K key;
            private V value;

            public KeyValueIterator(Function2<? super K, ? super V, ? extends E> factory2) {
                this.factory = factory2;
                findNext();
            }

            private final void findNext() {
                while (true) {
                    int i = this.index + 1;
                    this.index = i;
                    if (i < Core.this.allocated) {
                        HashedWeakRef hashedWeakRef = (HashedWeakRef) Core.this.keys.get(this.index);
                        K k = hashedWeakRef == null ? null : hashedWeakRef.get();
                        if (k != null) {
                            this.key = k;
                            V v = Core.this.values.get(this.index);
                            if (v instanceof Marked) {
                                v = ((Marked) v).ref;
                            }
                            if (v != null) {
                                this.value = v;
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                }
            }

            public boolean hasNext() {
                return this.index < Core.this.allocated;
            }

            public E next() {
                if (this.index < Core.this.allocated) {
                    Function2<K, V, E> function2 = this.factory;
                    K k = this.key;
                    if (k == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("key");
                        k = Unit.INSTANCE;
                    }
                    V v = this.value;
                    if (v == null) {
                        Intrinsics.throwUninitializedPropertyAccessException(XfdfConstants.VALUE);
                        v = Unit.INSTANCE;
                    }
                    E invoke = function2.invoke(k, v);
                    E e = invoke;
                    findNext();
                    return invoke;
                }
                throw new NoSuchElementException();
            }

            public Void remove() {
                Void unused = ConcurrentWeakMapKt.noImpl();
                throw new KotlinNothingValueException();
            }
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0002\b\u000b\b\u0002\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0002\u0012\u0006\u0010\u0005\u001a\u00028\u0003¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00028\u00032\u0006\u0010\f\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\rR\u0016\u0010\u0004\u001a\u00028\u0002X\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0005\u001a\u00028\u0003X\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u000e"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Entry;", "K", "V", "", "key", "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getValue", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: ConcurrentWeakMap.kt */
    private static final class Entry<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        private final K key;
        private final V value;

        public Entry(K key2, V value2) {
            this.key = key2;
            this.value = value2;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V newValue) {
            Void unused = ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }
    }

    @Metadata(mo112d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010)\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0015\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\rJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00020\u000fH\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$KeyValueSet;", "E", "Lkotlin/collections/AbstractMutableSet;", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;Lkotlin/jvm/functions/Function2;)V", "size", "", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "iterator", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: ConcurrentWeakMap.kt */
    private final class KeyValueSet<E> extends AbstractMutableSet<E> {
        private final Function2<K, V, E> factory;

        public KeyValueSet(Function2<? super K, ? super V, ? extends E> factory2) {
            this.factory = factory2;
        }

        public int getSize() {
            return ConcurrentWeakMap.this.size();
        }

        public boolean add(E element) {
            Void unused = ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }

        public Iterator<E> iterator() {
            return ((Core) ConcurrentWeakMap.this.core).keyValueIterator(this.factory);
        }
    }
}
