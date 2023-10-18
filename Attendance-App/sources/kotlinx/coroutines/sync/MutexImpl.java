package kotlinx.coroutines.sync;

import androidx.concurrent.futures.C0613xc40028dd;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo112d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u00112\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110 :\u0006$%&'()B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0007\u0010\bJ\u001d\u0010\n\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H@ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001d\u0010\f\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H@ø\u0001\u0000¢\u0006\u0004\b\f\u0010\u000bJT\u0010\u0014\u001a\u00020\t\"\u0004\b\u0000\u0010\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\"\u0010\u0013\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0010H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u00020\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J\u0019\u0010\u0019\u001a\u00020\u00012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b\u0019\u0010\bJ\u0019\u0010\u001a\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00018VX\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001f\u001a\u00020\u00018@X\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001dR\"\u0010#\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110 8VX\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"\u0002\u0004\n\u0002\b\u0019¨\u0006*"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl;", "", "locked", "<init>", "(Z)V", "", "owner", "holdsLock", "(Ljava/lang/Object;)Z", "", "lock", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lockSuspend", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectClause2", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "", "toString", "()Ljava/lang/String;", "tryLock", "unlock", "(Ljava/lang/Object;)V", "isLocked", "()Z", "isLockedEmptyQueueState$kotlinx_coroutines_core", "isLockedEmptyQueueState", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "onLock", "LockCont", "LockSelect", "LockWaiter", "LockedQueue", "TryLockDesc", "UnlockOp", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Mutex.kt */
public final class MutexImpl implements Mutex, SelectClause2<Object, Mutex> {
    static final /* synthetic */ AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(MutexImpl.class, Object.class, "_state");
    volatile /* synthetic */ Object _state;

    public MutexImpl(boolean locked) {
        this._state = locked ? MutexKt.EMPTY_LOCKED : MutexKt.EMPTY_UNLOCKED;
    }

    public boolean isLocked() {
        while (true) {
            Object state = this._state;
            if (state instanceof Empty) {
                return ((Empty) state).locked != MutexKt.UNLOCKED;
            }
            if (state instanceof LockedQueue) {
                return true;
            }
            if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", state).toString());
            }
        }
    }

    public final boolean isLockedEmptyQueueState$kotlinx_coroutines_core() {
        Object state = this._state;
        return (state instanceof LockedQueue) && ((LockedQueue) state).isEmpty();
    }

    public boolean tryLock(Object owner) {
        while (true) {
            Object state = this._state;
            boolean z = true;
            if (state instanceof Empty) {
                if (((Empty) state).locked != MutexKt.UNLOCKED) {
                    return false;
                }
                if (C0613xc40028dd.m151m(_state$FU, this, state, owner == null ? MutexKt.EMPTY_LOCKED : new Empty(owner))) {
                    return true;
                }
            } else if (state instanceof LockedQueue) {
                if (((LockedQueue) state).owner == owner) {
                    z = false;
                }
                if (z) {
                    return false;
                }
                throw new IllegalStateException(Intrinsics.stringPlus("Already locked by ", owner).toString());
            } else if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", state).toString());
            }
        }
    }

    public Object lock(Object owner, Continuation<? super Unit> $completion) {
        if (tryLock(owner)) {
            return Unit.INSTANCE;
        }
        Object lockSuspend = lockSuspend(owner, $completion);
        return lockSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? lockSuspend : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final Object lockSuspend(Object owner, Continuation<? super Unit> $completion) {
        Object obj = owner;
        CancellableContinuationImpl<? super Unit> orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        CancellableContinuation cont = orCreateCancellableContinuation;
        LockCont lockCont = new LockCont(obj, cont);
        while (true) {
            Object state = this._state;
            if (state instanceof Empty) {
                if (((Empty) state).locked != MutexKt.UNLOCKED) {
                    C0613xc40028dd.m151m(_state$FU, this, state, new LockedQueue(((Empty) state).locked));
                } else {
                    if (C0613xc40028dd.m151m(_state$FU, this, state, obj == null ? MutexKt.EMPTY_LOCKED : new Empty(obj))) {
                        cont.resume(Unit.INSTANCE, new MutexImpl$lockSuspend$2$1$1(this, obj));
                        break;
                    }
                }
            } else if (state instanceof LockedQueue) {
                if (((LockedQueue) state).owner != obj) {
                    ((LockedQueue) state).addLast(lockCont);
                    if (this._state == state || !lockCont.take()) {
                        CancellableContinuationKt.removeOnCancellation(cont, lockCont);
                    } else {
                        lockCont = new LockCont(obj, cont);
                    }
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("Already locked by ", obj).toString());
                }
            } else if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", state).toString());
            }
        }
        CancellableContinuationKt.removeOnCancellation(cont, lockCont);
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    public SelectClause2<Object, Mutex> getOnLock() {
        return this;
    }

    public <R> void registerSelectClause2(SelectInstance<? super R> select, Object owner, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> block) {
        while (!select.isSelected()) {
            Object state = this._state;
            if (state instanceof Empty) {
                if (((Empty) state).locked != MutexKt.UNLOCKED) {
                    C0613xc40028dd.m151m(_state$FU, this, state, new LockedQueue(((Empty) state).locked));
                } else {
                    Object failure = select.performAtomicTrySelect(new TryLockDesc(this, owner));
                    if (failure == null) {
                        UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
                        return;
                    } else if (failure != SelectKt.getALREADY_SELECTED()) {
                        if (!(failure == MutexKt.LOCK_FAIL || failure == AtomicKt.RETRY_ATOMIC)) {
                            throw new IllegalStateException(Intrinsics.stringPlus("performAtomicTrySelect(TryLockDesc) returned ", failure).toString());
                        }
                    } else {
                        return;
                    }
                }
            } else if (state instanceof LockedQueue) {
                if (((LockedQueue) state).owner != owner) {
                    LockSelect node = new LockSelect(owner, select, block);
                    ((LockedQueue) state).addLast(node);
                    if (this._state == state || !node.take()) {
                        select.disposeOnSelect(node);
                        return;
                    }
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("Already locked by ", owner).toString());
                }
            } else if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", state).toString());
            }
        }
    }

    @Metadata(mo112d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u0016J\u0016\u0010\f\u001a\u0004\u0018\u00010\u00052\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;", "Lkotlinx/coroutines/internal/AtomicDesc;", "mutex", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "complete", "", "op", "Lkotlinx/coroutines/internal/AtomicOp;", "failure", "prepare", "PrepareOp", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Mutex.kt */
    private static final class TryLockDesc extends AtomicDesc {
        public final MutexImpl mutex;
        public final Object owner;

        public TryLockDesc(MutexImpl mutex2, Object owner2) {
            this.mutex = mutex2;
            this.owner = owner2;
        }

        @Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc$PrepareOp;", "Lkotlinx/coroutines/internal/OpDescriptor;", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "(Lkotlinx/coroutines/sync/MutexImpl$TryLockDesc;Lkotlinx/coroutines/internal/AtomicOp;)V", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "perform", "", "affected", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
        /* compiled from: Mutex.kt */
        private final class PrepareOp extends OpDescriptor {
            private final AtomicOp<?> atomicOp;

            public PrepareOp(AtomicOp<?> atomicOp2) {
                this.atomicOp = atomicOp2;
            }

            public AtomicOp<?> getAtomicOp() {
                return this.atomicOp;
            }

            public Object perform(Object affected) {
                Object update = getAtomicOp().isDecided() ? MutexKt.EMPTY_UNLOCKED : getAtomicOp();
                if (affected != null) {
                    C0613xc40028dd.m151m(MutexImpl._state$FU, (MutexImpl) affected, this, update);
                    return null;
                }
                throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.sync.MutexImpl");
            }
        }

        public Object prepare(AtomicOp<?> op) {
            PrepareOp prepare = new PrepareOp(op);
            if (!C0613xc40028dd.m151m(MutexImpl._state$FU, this.mutex, MutexKt.EMPTY_UNLOCKED, prepare)) {
                return MutexKt.LOCK_FAIL;
            }
            return prepare.perform(this.mutex);
        }

        public void complete(AtomicOp<?> op, Object failure) {
            Empty update;
            if (failure != null) {
                update = MutexKt.EMPTY_UNLOCKED;
            } else {
                Object obj = this.owner;
                update = obj == null ? MutexKt.EMPTY_LOCKED : new Empty(obj);
            }
            C0613xc40028dd.m151m(MutexImpl._state$FU, this.mutex, op, update);
        }
    }

    public boolean holdsLock(Object owner) {
        Object state = this._state;
        if (state instanceof Empty) {
            return ((Empty) state).locked == owner;
        }
        if (!(state instanceof LockedQueue) || ((LockedQueue) state).owner != owner) {
            return false;
        }
        return true;
    }

    public void unlock(Object owner) {
        while (true) {
            Object state = this._state;
            boolean z = true;
            if (state instanceof Empty) {
                if (owner == null) {
                    if (((Empty) state).locked == MutexKt.UNLOCKED) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException("Mutex is not locked".toString());
                    }
                } else {
                    if (((Empty) state).locked != owner) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + ((Empty) state).locked + " but expected " + owner).toString());
                    }
                }
                if (C0613xc40028dd.m151m(_state$FU, this, state, MutexKt.EMPTY_UNLOCKED)) {
                    return;
                }
            } else if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else if (state instanceof LockedQueue) {
                if (owner != null) {
                    if (((LockedQueue) state).owner != owner) {
                        z = false;
                    }
                    if (!z) {
                        throw new IllegalStateException(("Mutex is locked by " + ((LockedQueue) state).owner + " but expected " + owner).toString());
                    }
                }
                LockFreeLinkedListNode waiter = ((LockedQueue) state).removeFirstOrNull();
                if (waiter == null) {
                    UnlockOp op = new UnlockOp((LockedQueue) state);
                    if (C0613xc40028dd.m151m(_state$FU, this, state, op) && op.perform(this) == null) {
                        return;
                    }
                } else if (((LockWaiter) waiter).tryResumeLockWaiter()) {
                    LockedQueue lockedQueue = (LockedQueue) state;
                    Object obj = ((LockWaiter) waiter).owner;
                    if (obj == null) {
                        obj = MutexKt.LOCKED;
                    }
                    lockedQueue.owner = obj;
                    ((LockWaiter) waiter).completeResumeLockWaiter();
                    return;
                }
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", state).toString());
            }
        }
    }

    public String toString() {
        while (true) {
            Object state = this._state;
            if (state instanceof Empty) {
                return "Mutex[" + ((Empty) state).locked + ']';
            }
            if (state instanceof OpDescriptor) {
                ((OpDescriptor) state).perform(this);
            } else if (state instanceof LockedQueue) {
                return "Mutex[" + ((LockedQueue) state).owner + ']';
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("Illegal state ", state).toString());
            }
        }
    }

    @Metadata(mo112d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u0012\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "owner", "", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Mutex.kt */
    private static final class LockedQueue extends LockFreeLinkedListHead {
        public Object owner;

        public LockedQueue(Object owner2) {
            this.owner = owner2;
        }

        public String toString() {
            return "LockedQueue[" + this.owner + ']';
        }
    }

    @Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b¢\u0004\u0018\u00002\u00020\u000f2\u00020\u0010B\u0011\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H&¢\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\b\u001a\u00020\u0005¢\u0006\u0004\b\b\u0010\u0007J\r\u0010\n\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\tH&¢\u0006\u0004\b\f\u0010\u000bR\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00018\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\r¨\u0006\u000e"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "", "owner", "<init>", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;)V", "", "completeResumeLockWaiter", "()V", "dispose", "", "take", "()Z", "tryResumeLockWaiter", "Ljava/lang/Object;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/DisposableHandle;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Mutex.kt */
    private abstract class LockWaiter extends LockFreeLinkedListNode implements DisposableHandle {
        private static final /* synthetic */ AtomicIntegerFieldUpdater isTaken$FU = AtomicIntegerFieldUpdater.newUpdater(LockWaiter.class, "isTaken");
        private volatile /* synthetic */ int isTaken = 0;
        public final Object owner;

        public abstract void completeResumeLockWaiter();

        public abstract boolean tryResumeLockWaiter();

        public LockWaiter(Object owner2) {
            this.owner = owner2;
        }

        public final boolean take() {
            return isTaken$FU.compareAndSet(this, 0, 1);
        }

        public final void dispose() {
            remove();
        }
    }

    @Metadata(mo112d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B\u001d\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0007H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockCont;", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeLockWaiter", "toString", "", "tryResumeLockWaiter", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Mutex.kt */
    private final class LockCont extends LockWaiter {
        private final CancellableContinuation<Unit> cont;

        public LockCont(Object owner, CancellableContinuation<? super Unit> cont2) {
            super(owner);
            this.cont = cont2;
        }

        public boolean tryResumeLockWaiter() {
            if (take() && this.cont.tryResume(Unit.INSTANCE, (Object) null, new MutexImpl$LockCont$tryResumeLockWaiter$1(MutexImpl.this, this)) != null) {
                return true;
            }
            return false;
        }

        public void completeResumeLockWaiter() {
            this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
        }

        public String toString() {
            return "LockCont[" + this.owner + ", " + this.cont + "] for " + MutexImpl.this;
        }
    }

    @Metadata(mo112d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00060\u0002R\u00020\u0003BD\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\"\u0010\b\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00050\tø\u0001\u0000¢\u0006\u0002\u0010\fJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016R1\u0010\b\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00050\t8\u0006X\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\rR\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00078\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl$LockSelect;", "R", "Lkotlinx/coroutines/sync/MutexImpl$LockWaiter;", "Lkotlinx/coroutines/sync/MutexImpl;", "owner", "", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/sync/Mutex;", "Lkotlin/coroutines/Continuation;", "(Lkotlinx/coroutines/sync/MutexImpl;Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "completeResumeLockWaiter", "", "toString", "", "tryResumeLockWaiter", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Mutex.kt */
    private final class LockSelect<R> extends LockWaiter {
        public final Function2<Mutex, Continuation<? super R>, Object> block;
        public final SelectInstance<R> select;

        public LockSelect(Object owner, SelectInstance<? super R> select2, Function2<? super Mutex, ? super Continuation<? super R>, ? extends Object> block2) {
            super(owner);
            this.select = select2;
            this.block = block2;
        }

        public boolean tryResumeLockWaiter() {
            return take() && this.select.trySelect();
        }

        public void completeResumeLockWaiter() {
            CancellableKt.startCoroutineCancellable(this.block, MutexImpl.this, this.select.getCompletion(), new MutexImpl$LockSelect$completeResumeLockWaiter$1(MutexImpl.this, this));
        }

        public String toString() {
            return "LockSelect[" + this.owner + ", " + this.select + "] for " + MutexImpl.this;
        }
    }

    @Metadata(mo112d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001a\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00022\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\b\u001a\u00020\u0002H\u0016R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, mo113d2 = {"Lkotlinx/coroutines/sync/MutexImpl$UnlockOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "Lkotlinx/coroutines/sync/MutexImpl;", "queue", "Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;", "(Lkotlinx/coroutines/sync/MutexImpl$LockedQueue;)V", "complete", "", "affected", "failure", "", "prepare", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
    /* compiled from: Mutex.kt */
    private static final class UnlockOp extends AtomicOp<MutexImpl> {
        public final LockedQueue queue;

        public UnlockOp(LockedQueue queue2) {
            this.queue = queue2;
        }

        public Object prepare(MutexImpl affected) {
            if (this.queue.isEmpty()) {
                return null;
            }
            return MutexKt.UNLOCK_FAIL;
        }

        public void complete(MutexImpl affected, Object failure) {
            C0613xc40028dd.m151m(MutexImpl._state$FU, affected, this, failure == null ? MutexKt.EMPTY_UNLOCKED : this.queue);
        }
    }
}
