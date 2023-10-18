package kotlinx.coroutines.internal;

import java.lang.Comparable;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

@Metadata(mo112d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010\u0000\n\u0002\u0018\u0002\b\u0017\u0018\u0000*\u0012\b\u0000\u0010\u0003*\u00020\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00022\u000602j\u0002`3B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000H\u0001¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\n\u0010\tJ.\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u00002\u0014\u0010\r\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020\f0\u000bH\b¢\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u0007¢\u0006\u0004\b\u0010\u0010\u0005J\u0011\u0010\u0011\u001a\u0004\u0018\u00018\u0000H\u0001¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0013\u0010\u0012J\u0017\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0014H\u0002¢\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u0019H\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ&\u0010\u001e\u001a\u0004\u0018\u00018\u00002\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bH\b¢\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b \u0010\u0012J\u0018\u0010\"\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0019H\u0010¢\u0006\u0004\b\"\u0010#J\u0018\u0010$\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0019H\u0010¢\u0006\u0004\b$\u0010#J\u001f\u0010&\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00192\u0006\u0010%\u001a\u00020\u0019H\u0002¢\u0006\u0004\b&\u0010'R \u0010(\u001a\f\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0018\u00010\u00148\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b(\u0010)R\u0011\u0010*\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b*\u0010+R$\u00100\u001a\u00020\u00192\u0006\u0010,\u001a\u00020\u00198F@BX\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u0010#¨\u00061"}, mo113d2 = {"Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "", "T", "<init>", "()V", "node", "", "addImpl", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V", "addLast", "Lkotlin/Function1;", "", "cond", "addLastIf", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;Lkotlin/jvm/functions/Function1;)Z", "clear", "firstImpl", "()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "peek", "", "realloc", "()[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "remove", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)Z", "", "index", "removeAtImpl", "(I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "predicate", "removeFirstIf", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "removeFirstOrNull", "i", "siftDownFrom", "(I)V", "siftUpFrom", "j", "swap", "(II)V", "a", "[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "isEmpty", "()Z", "value", "getSize", "()I", "setSize", "size", "kotlinx-coroutines-core", "", "Lkotlinx/coroutines/internal/SynchronizedObject;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ThreadSafeHeap.kt */
public class ThreadSafeHeap<T extends ThreadSafeHeapNode & Comparable<? super T>> {
    private volatile /* synthetic */ int _size = 0;

    /* renamed from: a */
    private T[] f13a;

    public final int getSize() {
        return this._size;
    }

    private final void setSize(int value) {
        this._size = value;
    }

    public final boolean isEmpty() {
        return getSize() == 0;
    }

    public final void clear() {
        synchronized (this) {
            T[] tArr = this.f13a;
            if (tArr != null) {
                ArraysKt.fill$default((Object[]) tArr, (Object) null, 0, 0, 6, (Object) null);
            }
            this._size = 0;
            Unit unit = Unit.INSTANCE;
        }
    }

    public final T peek() {
        T firstImpl;
        synchronized (this) {
            firstImpl = firstImpl();
        }
        return firstImpl;
    }

    public final T removeFirstOrNull() {
        T t;
        synchronized (this) {
            if (getSize() > 0) {
                t = removeAtImpl(0);
            } else {
                t = null;
                ThreadSafeHeapNode threadSafeHeapNode = null;
            }
        }
        return t;
    }

    public final T removeFirstIf(Function1<? super T, Boolean> predicate) {
        synchronized (this) {
            try {
                ThreadSafeHeapNode first = firstImpl();
                T t = null;
                if (first == null) {
                    InlineMarker.finallyStart(2);
                    InlineMarker.finallyEnd(2);
                    return null;
                }
                if (predicate.invoke(first).booleanValue()) {
                    t = removeAtImpl(0);
                } else {
                    ThreadSafeHeapNode threadSafeHeapNode = null;
                }
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                return t;
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
    }

    public final void addLast(T node) {
        synchronized (this) {
            addImpl(node);
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean addLastIf(T node, Function1<? super T, Boolean> cond) {
        boolean z;
        synchronized (this) {
            try {
                if (cond.invoke(firstImpl()).booleanValue()) {
                    addImpl(node);
                    z = true;
                } else {
                    z = false;
                }
                InlineMarker.finallyStart(1);
            } catch (Throwable th) {
                InlineMarker.finallyStart(1);
                InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        InlineMarker.finallyEnd(1);
        return z;
    }

    public final boolean remove(T node) {
        boolean z;
        synchronized (this) {
            z = false;
            if (node.getHeap() != null) {
                int index = node.getIndex();
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (index >= 0) {
                        z = true;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
                removeAtImpl(index);
                z = true;
            }
        }
        return z;
    }

    public final T firstImpl() {
        T[] tArr = this.f13a;
        if (tArr == null) {
            return null;
        }
        return tArr[0];
    }

    public final T removeAtImpl(int index) {
        boolean z = false;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((getSize() > 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        ThreadSafeHeapNode[] a = this.f13a;
        Intrinsics.checkNotNull(a);
        setSize(getSize() - 1);
        if (index < getSize()) {
            swap(index, getSize());
            int j = (index - 1) / 2;
            if (index > 0) {
                ThreadSafeHeapNode threadSafeHeapNode = a[index];
                Intrinsics.checkNotNull(threadSafeHeapNode);
                ThreadSafeHeapNode threadSafeHeapNode2 = a[j];
                Intrinsics.checkNotNull(threadSafeHeapNode2);
                if (((Comparable) threadSafeHeapNode).compareTo(threadSafeHeapNode2) < 0) {
                    swap(index, j);
                    siftUpFrom(j);
                }
            }
            siftDownFrom(index);
        }
        ThreadSafeHeapNode result = a[getSize()];
        Intrinsics.checkNotNull(result);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (result.getHeap() == this) {
                z = true;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
        result.setHeap((ThreadSafeHeap<?>) null);
        result.setIndex(-1);
        a[getSize()] = null;
        return result;
    }

    public final void addImpl(T node) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(node.getHeap() == null)) {
                throw new AssertionError();
            }
        }
        node.setHeap(this);
        ThreadSafeHeapNode[] a = realloc();
        int i = getSize();
        setSize(i + 1);
        a[i] = node;
        node.setIndex(i);
        siftUpFrom(i);
    }

    private final void siftUpFrom(int i) {
        int i2 = i;
        while (i2 > 0) {
            ThreadSafeHeapNode[] a = this.f13a;
            Intrinsics.checkNotNull(a);
            int j = (i2 - 1) / 2;
            ThreadSafeHeapNode threadSafeHeapNode = a[j];
            Intrinsics.checkNotNull(threadSafeHeapNode);
            ThreadSafeHeapNode threadSafeHeapNode2 = a[i2];
            Intrinsics.checkNotNull(threadSafeHeapNode2);
            if (((Comparable) threadSafeHeapNode).compareTo(threadSafeHeapNode2) > 0) {
                swap(i2, j);
                i2 = j;
            } else {
                return;
            }
        }
    }

    private final void siftDownFrom(int i) {
        int i2 = i;
        while (true) {
            int j = (i2 * 2) + 1;
            if (j < getSize()) {
                ThreadSafeHeapNode[] a = this.f13a;
                Intrinsics.checkNotNull(a);
                if (j + 1 < getSize()) {
                    ThreadSafeHeapNode threadSafeHeapNode = a[j + 1];
                    Intrinsics.checkNotNull(threadSafeHeapNode);
                    ThreadSafeHeapNode threadSafeHeapNode2 = a[j];
                    Intrinsics.checkNotNull(threadSafeHeapNode2);
                    if (((Comparable) threadSafeHeapNode).compareTo(threadSafeHeapNode2) < 0) {
                        j++;
                    }
                }
                ThreadSafeHeapNode threadSafeHeapNode3 = a[i2];
                Intrinsics.checkNotNull(threadSafeHeapNode3);
                ThreadSafeHeapNode threadSafeHeapNode4 = a[j];
                Intrinsics.checkNotNull(threadSafeHeapNode4);
                if (((Comparable) threadSafeHeapNode3).compareTo(threadSafeHeapNode4) > 0) {
                    swap(i2, j);
                    i2 = j;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final T[] realloc() {
        ThreadSafeHeapNode[] a = this.f13a;
        if (a == null) {
            ThreadSafeHeapNode[] it = new ThreadSafeHeapNode[4];
            this.f13a = it;
            return it;
        } else if (getSize() < a.length) {
            return a;
        } else {
            T[] it2 = Arrays.copyOf(a, getSize() * 2);
            Intrinsics.checkNotNullExpressionValue(it2, "copyOf(this, newSize)");
            this.f13a = it2;
            return (ThreadSafeHeapNode[]) it2;
        }
    }

    private final void swap(int i, int j) {
        ThreadSafeHeapNode[] a = this.f13a;
        Intrinsics.checkNotNull(a);
        ThreadSafeHeapNode ni = a[j];
        Intrinsics.checkNotNull(ni);
        ThreadSafeHeapNode nj = a[i];
        Intrinsics.checkNotNull(nj);
        a[i] = ni;
        a[j] = nj;
        ni.setIndex(i);
        nj.setIndex(j);
    }
}
