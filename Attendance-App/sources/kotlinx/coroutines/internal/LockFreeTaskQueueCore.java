package kotlinx.coroutines.internal;

import androidx.concurrent.futures.C0613xc40028dd;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.DebugKt;

@Metadata(mo112d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0016\b\u0000\u0018\u0000 /*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001:\u0002/0B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\n\u001a\u00020\u00032\u0006\u0010\t\u001a\u00028\u0000¢\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0000j\b\u0012\u0004\u0012\u00028\u0000`\u000e2\u0006\u0010\r\u001a\u00020\fH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J'\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0000j\b\u0012\u0004\u0012\u00028\u0000`\u000e2\u0006\u0010\r\u001a\u00020\fH\u0002¢\u0006\u0004\b\u0011\u0010\u0010J\r\u0010\u0012\u001a\u00020\u0005¢\u0006\u0004\b\u0012\u0010\u0013J3\u0010\u0015\u001a\u0016\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u000e2\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\t\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u0015\u0010\u0016J\r\u0010\u0017\u001a\u00020\u0005¢\u0006\u0004\b\u0017\u0010\u0013J-\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00010\u001b\"\u0004\b\u0001\u0010\u00182\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0019¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\fH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ\u0013\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u0004\u0018\u00010\u0001¢\u0006\u0004\b\"\u0010#J3\u0010&\u001a\u0016\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u000e2\u0006\u0010$\u001a\u00020\u00032\u0006\u0010%\u001a\u00020\u0003H\u0002¢\u0006\u0004\b&\u0010'R\u0014\u0010\u0004\u001a\u00020\u00038\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010(R\u0011\u0010)\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b)\u0010\u0013R\u0014\u0010*\u001a\u00020\u00038\u0002X\u0004¢\u0006\u0006\n\u0004\b*\u0010(R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010+R\u0011\u0010.\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u00061"}, mo113d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "", "E", "", "capacity", "", "singleConsumer", "<init>", "(IZ)V", "element", "addLast", "(Ljava/lang/Object;)I", "", "state", "Lkotlinx/coroutines/internal/Core;", "allocateNextCopy", "(J)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "allocateOrGetNextCopy", "close", "()Z", "index", "fillPlaceholder", "(ILjava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "isClosed", "R", "Lkotlin/Function1;", "transform", "", "map", "(Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "markFrozen", "()J", "next", "()Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "removeFirstOrNull", "()Ljava/lang/Object;", "oldHead", "newHead", "removeSlowPath", "(II)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "I", "isEmpty", "mask", "Z", "getSize", "()I", "size", "Companion", "Placeholder", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: LockFreeTaskQueue.kt */
public final class LockFreeTaskQueueCore<E> {
    public static final int ADD_CLOSED = 2;
    public static final int ADD_FROZEN = 1;
    public static final int ADD_SUCCESS = 0;
    public static final int CAPACITY_BITS = 30;
    public static final long CLOSED_MASK = 2305843009213693952L;
    public static final int CLOSED_SHIFT = 61;
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final long FROZEN_MASK = 1152921504606846976L;
    public static final int FROZEN_SHIFT = 60;
    public static final long HEAD_MASK = 1073741823;
    public static final int HEAD_SHIFT = 0;
    public static final int INITIAL_CAPACITY = 8;
    public static final int MAX_CAPACITY_MASK = 1073741823;
    public static final int MIN_ADD_SPIN_CAPACITY = 1024;
    public static final Symbol REMOVE_FROZEN = new Symbol("REMOVE_FROZEN");
    public static final long TAIL_MASK = 1152921503533105152L;
    public static final int TAIL_SHIFT = 30;
    private static final /* synthetic */ AtomicReferenceFieldUpdater _next$FU;
    private static final /* synthetic */ AtomicLongFieldUpdater _state$FU;
    private volatile /* synthetic */ Object _next = null;
    private volatile /* synthetic */ long _state = 0;
    private /* synthetic */ AtomicReferenceArray array;
    private final int capacity;
    private final int mask;
    private final boolean singleConsumer;

    public LockFreeTaskQueueCore(int capacity2, boolean singleConsumer2) {
        this.capacity = capacity2;
        this.singleConsumer = singleConsumer2;
        int i = capacity2 - 1;
        this.mask = i;
        this.array = new AtomicReferenceArray(capacity2);
        boolean z = false;
        if (i <= 1073741823) {
            if (!((i & capacity2) == 0 ? true : z)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final boolean isEmpty() {
        Companion companion = Companion;
        long $this$withState$iv = this._state;
        if (((int) ((HEAD_MASK & $this$withState$iv) >> 0)) == ((int) ((TAIL_MASK & $this$withState$iv) >> 30))) {
            return true;
        }
        return false;
    }

    public final int getSize() {
        Companion companion = Companion;
        long $this$withState$iv = this._state;
        int head$iv = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
        return (((int) ((TAIL_MASK & $this$withState$iv) >> 30)) - head$iv) & MAX_CAPACITY_MASK;
    }

    public final boolean close() {
        long cur$iv;
        long state;
        do {
            cur$iv = this._state;
            state = cur$iv;
            if ((state & CLOSED_MASK) != 0) {
                return true;
            }
            if ((FROZEN_MASK & state) != 0) {
                return false;
            }
        } while (!_state$FU.compareAndSet(this, cur$iv, state | CLOSED_MASK));
        return true;
    }

    public final int addLast(E element) {
        LockFreeTaskQueueCore fillPlaceholder;
        E e = element;
        while (true) {
            long state = this._state;
            if ((3458764513820540928L & state) != 0) {
                return Companion.addFailReason(state);
            }
            Companion companion = Companion;
            long $this$withState$iv = state;
            int head$iv = (int) (($this$withState$iv & HEAD_MASK) >> 0);
            int tail$iv = (int) (($this$withState$iv & TAIL_MASK) >> 30);
            int head = head$iv;
            int tail = tail$iv;
            int mask2 = this.mask;
            if (((tail + 2) & mask2) == (head & mask2)) {
                return 1;
            }
            if (this.singleConsumer || this.array.get(tail & mask2) == null) {
                int newTail = (tail + 1) & MAX_CAPACITY_MASK;
                int tail2 = tail;
                int mask3 = mask2;
                int i = head$iv;
                int i2 = tail$iv;
                if (_state$FU.compareAndSet(this, state, Companion.updateTail(state, newTail))) {
                    this.array.set(tail2 & mask3, e);
                    LockFreeTaskQueueCore cur = this;
                    while ((cur._state & FROZEN_MASK) != 0 && (fillPlaceholder = cur.next().fillPlaceholder(tail2, e)) != null) {
                        cur = fillPlaceholder;
                    }
                    return 0;
                }
            } else {
                int i3 = this.capacity;
                if (i3 < 1024 || (1073741823 & (tail - head)) > (i3 >> 1)) {
                    return 1;
                }
            }
        }
        return 1;
    }

    private final LockFreeTaskQueueCore<E> fillPlaceholder(int index, E element) {
        Object old = this.array.get(this.mask & index);
        if (!(old instanceof Placeholder) || ((Placeholder) old).index != index) {
            return null;
        }
        this.array.set(this.mask & index, element);
        return this;
    }

    public final Object removeFirstOrNull() {
        int $i$f$loop;
        LockFreeTaskQueueCore $this$loop$iv = this;
        int $i$f$loop2 = 0;
        while (true) {
            long state = $this$loop$iv._state;
            if ((FROZEN_MASK & state) != 0) {
                return REMOVE_FROZEN;
            }
            Companion companion = Companion;
            long $this$withState$iv = state;
            int head$iv = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
            int tail$iv = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
            int head = head$iv;
            int i = this.mask;
            LockFreeTaskQueueCore $this$loop$iv2 = $this$loop$iv;
            if ((tail$iv & i) == (head & i)) {
                return null;
            }
            Object element = this.array.get(i & head);
            if (element == null) {
                if (this.singleConsumer) {
                    return null;
                }
                $i$f$loop = $i$f$loop2;
            } else if (element instanceof Placeholder) {
                return null;
            } else {
                int newHead = 1073741823 & (head + 1);
                int newHead2 = newHead;
                Object element2 = element;
                $i$f$loop = $i$f$loop2;
                int head2 = head;
                int i2 = head$iv;
                int i3 = tail$iv;
                if (_state$FU.compareAndSet(this, state, Companion.updateHead(state, newHead))) {
                    this.array.set(this.mask & head2, (Object) null);
                    return element2;
                } else if (this.singleConsumer) {
                    LockFreeTaskQueueCore cur = this;
                    while (true) {
                        LockFreeTaskQueueCore removeSlowPath = cur.removeSlowPath(head2, newHead2);
                        if (removeSlowPath == null) {
                            return element2;
                        }
                        cur = removeSlowPath;
                    }
                }
            }
            $this$loop$iv = $this$loop$iv2;
            $i$f$loop2 = $i$f$loop;
        }
    }

    private final LockFreeTaskQueueCore<E> removeSlowPath(int oldHead, int newHead) {
        LockFreeTaskQueueCore $this$loop$iv = this;
        while (true) {
            long state = $this$loop$iv._state;
            Companion companion = Companion;
            long $this$withState$iv = state;
            boolean z = false;
            int head$iv = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
            int tail$iv = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
            int head = head$iv;
            int i = tail$iv;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (head == oldHead) {
                    z = true;
                }
                if (!z) {
                    throw new AssertionError();
                }
            } else {
                int i2 = oldHead;
            }
            if ((state & FROZEN_MASK) != 0) {
                return next();
            }
            LockFreeTaskQueueCore $this$loop$iv2 = $this$loop$iv;
            int head2 = head;
            int i3 = head$iv;
            int i4 = tail$iv;
            if (_state$FU.compareAndSet(this, state, Companion.updateHead(state, newHead))) {
                this.array.set(head2 & this.mask, (Object) null);
                return null;
            }
            $this$loop$iv = $this$loop$iv2;
        }
    }

    public final LockFreeTaskQueueCore<E> next() {
        return allocateOrGetNextCopy(markFrozen());
    }

    private final long markFrozen() {
        long cur$iv;
        long upd$iv;
        do {
            cur$iv = this._state;
            long state = cur$iv;
            if ((state & FROZEN_MASK) != 0) {
                return state;
            }
            upd$iv = state | FROZEN_MASK;
        } while (!_state$FU.compareAndSet(this, cur$iv, upd$iv));
        return upd$iv;
    }

    private final LockFreeTaskQueueCore<E> allocateOrGetNextCopy(long state) {
        while (true) {
            LockFreeTaskQueueCore next = (LockFreeTaskQueueCore) this._next;
            if (next != null) {
                return next;
            }
            C0613xc40028dd.m151m(_next$FU, this, (Object) null, allocateNextCopy(state));
        }
    }

    private final LockFreeTaskQueueCore<E> allocateNextCopy(long state) {
        LockFreeTaskQueueCore next = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
        Companion this_$iv = Companion;
        long $this$withState$iv = state;
        int head$iv = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
        int tail = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
        int index = head$iv;
        while (true) {
            int i = this.mask;
            if ((index & i) != (tail & i)) {
                Object value = this.array.get(i & index);
                if (value == null) {
                    value = new Placeholder(index);
                }
                next.array.set(next.mask & index, value);
                index++;
            } else {
                Companion companion = this_$iv;
                long j = $this$withState$iv;
                next._state = Companion.mo842wo(state, FROZEN_MASK);
                return next;
            }
        }
    }

    public final <R> List<R> map(Function1<? super E, ? extends R> transform) {
        ArrayList res = new ArrayList(this.capacity);
        Companion companion = Companion;
        long $this$withState$iv = this._state;
        int head$iv = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
        int tail = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
        int index = head$iv;
        while (true) {
            int i = this.mask;
            if ((index & i) == (tail & i)) {
                return res;
            }
            Object element = this.array.get(i & index);
            if (element != null && !(element instanceof Placeholder)) {
                res.add(transform.invoke(element));
            }
            index++;
        }
    }

    public final boolean isClosed() {
        return (this._state & CLOSED_MASK) != 0;
    }

    @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, mo113d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Placeholder;", "", "index", "", "(I)V", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: LockFreeTaskQueue.kt */
    public static final class Placeholder {
        public final int index;

        public Placeholder(int index2) {
            this.index = index2;
        }
    }

    @Metadata(mo112d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u0016\u001a\u00020\u0004*\u00020\tJ\u0012\u0010\u0017\u001a\u00020\t*\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0004J\u0012\u0010\u0019\u001a\u00020\t*\u00020\t2\u0006\u0010\u001a\u001a\u00020\u0004JP\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0001\u0010\u001c*\u00020\t26\u0010\u001d\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(!\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u001c0\u001eH\b¢\u0006\u0002\u0010#J\u0015\u0010$\u001a\u00020\t*\u00020\t2\u0006\u0010%\u001a\u00020\tH\u0004R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00138\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006&"}, mo113d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Companion;", "", "()V", "ADD_CLOSED", "", "ADD_FROZEN", "ADD_SUCCESS", "CAPACITY_BITS", "CLOSED_MASK", "", "CLOSED_SHIFT", "FROZEN_MASK", "FROZEN_SHIFT", "HEAD_MASK", "HEAD_SHIFT", "INITIAL_CAPACITY", "MAX_CAPACITY_MASK", "MIN_ADD_SPIN_CAPACITY", "REMOVE_FROZEN", "Lkotlinx/coroutines/internal/Symbol;", "TAIL_MASK", "TAIL_SHIFT", "addFailReason", "updateHead", "newHead", "updateTail", "newTail", "withState", "T", "block", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "head", "tail", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "wo", "other", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: LockFreeTaskQueue.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: wo */
        public final long mo842wo(long $this$wo, long other) {
            return (-1 ^ other) & $this$wo;
        }

        public final long updateHead(long $this$updateHead, int newHead) {
            return mo842wo($this$updateHead, LockFreeTaskQueueCore.HEAD_MASK) | (((long) newHead) << 0);
        }

        public final long updateTail(long $this$updateTail, int newTail) {
            return mo842wo($this$updateTail, LockFreeTaskQueueCore.TAIL_MASK) | (((long) newTail) << 30);
        }

        public final <T> T withState(long $this$withState, Function2<? super Integer, ? super Integer, ? extends T> block) {
            return block.invoke(Integer.valueOf((int) ((LockFreeTaskQueueCore.HEAD_MASK & $this$withState) >> 0)), Integer.valueOf((int) ((LockFreeTaskQueueCore.TAIL_MASK & $this$withState) >> 30)));
        }

        public final int addFailReason(long $this$addFailReason) {
            return (LockFreeTaskQueueCore.CLOSED_MASK & $this$addFailReason) != 0 ? 2 : 1;
        }
    }

    static {
        Class<LockFreeTaskQueueCore> cls = LockFreeTaskQueueCore.class;
        _next$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_next");
        _state$FU = AtomicLongFieldUpdater.newUpdater(cls, "_state");
    }
}
