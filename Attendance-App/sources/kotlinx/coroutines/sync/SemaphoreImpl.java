package kotlinx.coroutines.sync;

import androidx.concurrent.futures.C0613xc40028dd;
import com.itextpdf.forms.xfdf.XfdfConstants;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;

@Metadata(mo112d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\b\u0002\u0018\u00002\u00020\u001eB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\u0007\u001a\u00020\u0006H@ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\t\u001a\u00020\u0006H@ø\u0001\u0000¢\u0006\u0004\b\t\u0010\bJ\u001d\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\fH\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\fH\u0002¢\u0006\u0004\b\u0013\u0010\u0012J\u0019\u0010\u0014\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u00060\nH\u0002¢\u0006\u0004\b\u0014\u0010\u000eR\u0014\u0010\u0017\u001a\u00020\u00018VX\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R \u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00060\u00188\u0002X\u0004¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\u001c\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, mo113d2 = {"Lkotlinx/coroutines/sync/SemaphoreImpl;", "", "permits", "acquiredPermits", "<init>", "(II)V", "", "acquire", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acquireSlowPath", "Lkotlinx/coroutines/CancellableContinuation;", "cont", "", "addAcquireToQueue", "(Lkotlinx/coroutines/CancellableContinuation;)Z", "release", "()V", "tryAcquire", "()Z", "tryResumeNextFromQueue", "tryResumeAcquire", "getAvailablePermits", "()I", "availablePermits", "Lkotlin/Function1;", "", "onCancellationRelease", "Lkotlin/jvm/functions/Function1;", "I", "kotlinx-coroutines-core", "Lkotlinx/coroutines/sync/Semaphore;"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Semaphore.kt */
final class SemaphoreImpl implements Semaphore {
    static final /* synthetic */ AtomicIntegerFieldUpdater _availablePermits$FU;
    private static final /* synthetic */ AtomicLongFieldUpdater deqIdx$FU;
    private static final /* synthetic */ AtomicLongFieldUpdater enqIdx$FU;
    private static final /* synthetic */ AtomicReferenceFieldUpdater head$FU;
    private static final /* synthetic */ AtomicReferenceFieldUpdater tail$FU;
    volatile /* synthetic */ int _availablePermits;
    private volatile /* synthetic */ long deqIdx = 0;
    private volatile /* synthetic */ long enqIdx = 0;
    private volatile /* synthetic */ Object head;
    /* access modifiers changed from: private */
    public final Function1<Throwable, Unit> onCancellationRelease;
    private final int permits;
    private volatile /* synthetic */ Object tail;

    static {
        Class<SemaphoreImpl> cls = SemaphoreImpl.class;
        head$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, XfdfConstants.HEAD);
        deqIdx$FU = AtomicLongFieldUpdater.newUpdater(cls, "deqIdx");
        tail$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, XfdfConstants.TAIL);
        enqIdx$FU = AtomicLongFieldUpdater.newUpdater(cls, "enqIdx");
        _availablePermits$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_availablePermits");
    }

    public SemaphoreImpl(int permits2, int acquiredPermits) {
        this.permits = permits2;
        boolean z = true;
        if (permits2 > 0) {
            if ((acquiredPermits < 0 || acquiredPermits > permits2) ? false : z) {
                SemaphoreSegment s = new SemaphoreSegment(0, (SemaphoreSegment) null, 2);
                this.head = s;
                this.tail = s;
                this._availablePermits = permits2 - acquiredPermits;
                this.onCancellationRelease = new SemaphoreImpl$onCancellationRelease$1(this);
                return;
            }
            throw new IllegalArgumentException(Intrinsics.stringPlus("The number of acquired permits should be in 0..", Integer.valueOf(permits2)).toString());
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Semaphore should have at least 1 permit, but had ", Integer.valueOf(permits2)).toString());
    }

    public int getAvailablePermits() {
        return Math.max(this._availablePermits, 0);
    }

    public boolean tryAcquire() {
        int p;
        do {
            p = this._availablePermits;
            if (p <= 0) {
                return false;
            }
        } while (!_availablePermits$FU.compareAndSet(this, p, p - 1));
        return true;
    }

    public Object acquire(Continuation<? super Unit> $completion) {
        if (_availablePermits$FU.getAndDecrement(this) > 0) {
            return Unit.INSTANCE;
        }
        Object acquireSlowPath = acquireSlowPath($completion);
        return acquireSlowPath == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? acquireSlowPath : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final Object acquireSlowPath(Continuation<? super Unit> $completion) {
        CancellableContinuationImpl<? super Unit> orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        CancellableContinuation cont = orCreateCancellableContinuation;
        while (true) {
            if (!addAcquireToQueue(cont)) {
                if (_availablePermits$FU.getAndDecrement(this) > 0) {
                    cont.resume(Unit.INSTANCE, this.onCancellationRelease);
                    break;
                }
            } else {
                break;
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0028 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x000d  */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x000f  */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0012  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void release() {
        /*
            r8 = this;
        L_0x0001:
            r0 = r8
            r1 = 0
        L_0x0004:
            int r2 = r0._availablePermits
            r3 = r2
            r4 = 0
            int r5 = r8.permits
            if (r3 >= r5) goto L_0x000f
            r6 = 1
            goto L_0x0010
        L_0x000f:
            r6 = 0
        L_0x0010:
            if (r6 == 0) goto L_0x0028
            int r3 = r3 + 1
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r4 = _availablePermits$FU
            boolean r4 = r4.compareAndSet(r0, r2, r3)
            if (r4 == 0) goto L_0x0027
            r0 = r2
            if (r0 < 0) goto L_0x0020
            return
        L_0x0020:
            boolean r1 = r8.tryResumeNextFromQueue()
            if (r1 == 0) goto L_0x0001
            return
        L_0x0027:
            goto L_0x0004
        L_0x0028:
            r6 = 0
            java.lang.String r7 = "The number of released permits cannot be greater than "
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.String r5 = kotlin.jvm.internal.Intrinsics.stringPlus(r7, r5)
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r5 = r5.toString()
            r6.<init>(r5)
            goto L_0x003e
        L_0x003d:
            throw r6
        L_0x003e:
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.SemaphoreImpl.release():void");
    }

    /* access modifiers changed from: private */
    public final boolean addAcquireToQueue(CancellableContinuation<? super Unit> cont) {
        SemaphoreSegment next$iv$iv;
        SemaphoreSegment curTail;
        Object obj;
        Object s$iv;
        boolean z;
        CancellableContinuation<? super Unit> cancellableContinuation = cont;
        SemaphoreSegment next$iv$iv2 = (SemaphoreSegment) this.tail;
        long enqIdx2 = enqIdx$FU.getAndIncrement(this);
        long id$iv = enqIdx2 / ((long) SemaphoreKt.SEGMENT_SIZE);
        while (true) {
            Segment cur$iv$iv = next$iv$iv;
            while (true) {
                if (cur$iv$iv.getId() >= id$iv && !cur$iv$iv.getRemoved()) {
                    obj = SegmentOrClosed.m1297constructorimpl(cur$iv$iv);
                    curTail = next$iv$iv;
                    break;
                }
                Object it$iv$iv$iv = cur$iv$iv.getNextOrClosed();
                curTail = next$iv$iv;
                if (it$iv$iv$iv == ConcurrentLinkedListKt.CLOSED) {
                    obj = SegmentOrClosed.m1297constructorimpl(ConcurrentLinkedListKt.CLOSED);
                    break;
                }
                Segment next$iv$iv3 = (Segment) ((ConcurrentLinkedListNode) it$iv$iv$iv);
                if (next$iv$iv3 != null) {
                    cur$iv$iv = next$iv$iv3;
                    next$iv$iv = curTail;
                } else {
                    Segment newTail$iv$iv = SemaphoreKt.createSegment(cur$iv$iv.getId() + 1, (SemaphoreSegment) cur$iv$iv);
                    if (cur$iv$iv.trySetNext(newTail$iv$iv)) {
                        if (cur$iv$iv.getRemoved()) {
                            cur$iv$iv.remove();
                        }
                        cur$iv$iv = newTail$iv$iv;
                        next$iv$iv = curTail;
                    } else {
                        next$iv$iv = curTail;
                    }
                }
            }
            s$iv = obj;
            if (SegmentOrClosed.m1302isClosedimpl(s$iv)) {
                break;
            }
            Segment to$iv$iv = SegmentOrClosed.m1300getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv2 = (Segment) this.tail;
                if (cur$iv$iv2.getId() >= to$iv$iv.getId()) {
                    z = true;
                    break;
                } else if (!to$iv$iv.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                } else if (C0613xc40028dd.m151m(tail$FU, this, cur$iv$iv2, to$iv$iv)) {
                    if (cur$iv$iv2.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv2.remove();
                    }
                    z = true;
                } else if (to$iv$iv.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv.remove();
                }
            }
            if (z) {
                break;
            }
            next$iv$iv2 = curTail;
        }
        SemaphoreSegment segment = (SemaphoreSegment) SegmentOrClosed.m1300getSegmentimpl(s$iv);
        int i = (int) (enqIdx2 % ((long) SemaphoreKt.SEGMENT_SIZE));
        if (ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(segment.acquirers, i, (Object) null, cancellableContinuation)) {
            cancellableContinuation.invokeOnCancellation(new CancelSemaphoreAcquisitionHandler(segment, i));
            return true;
        }
        if (ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(segment.acquirers, i, SemaphoreKt.PERMIT, SemaphoreKt.TAKEN)) {
            cancellableContinuation.resume(Unit.INSTANCE, this.onCancellationRelease);
            return true;
        } else if (!DebugKt.getASSERTIONS_ENABLED()) {
            return false;
        } else {
            if (segment.acquirers.get(i) == SemaphoreKt.BROKEN) {
                return false;
            }
            throw new AssertionError();
        }
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean tryResumeNextFromQueue() {
        /*
            r23 = this;
            r0 = r23
            java.lang.Object r1 = r0.head
            kotlinx.coroutines.sync.SemaphoreSegment r1 = (kotlinx.coroutines.sync.SemaphoreSegment) r1
            java.util.concurrent.atomic.AtomicLongFieldUpdater r2 = deqIdx$FU
            long r2 = r2.getAndIncrement(r0)
            int r4 = kotlinx.coroutines.sync.SemaphoreKt.SEGMENT_SIZE
            long r4 = (long) r4
            long r4 = r2 / r4
            r6 = r23
            r7 = 0
        L_0x0016:
            r8 = r1
            kotlinx.coroutines.internal.Segment r8 = (kotlinx.coroutines.internal.Segment) r8
            r9 = 0
            r10 = r8
        L_0x001c:
            long r11 = r10.getId()
            int r13 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r13 < 0) goto L_0x0030
            boolean r11 = r10.getRemoved()
            if (r11 == 0) goto L_0x002b
            goto L_0x0030
        L_0x002b:
            java.lang.Object r11 = kotlinx.coroutines.internal.SegmentOrClosed.m1297constructorimpl(r10)
            goto L_0x004a
        L_0x0030:
            r11 = r10
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r11 = (kotlinx.coroutines.internal.ConcurrentLinkedListNode) r11
            r12 = 0
            java.lang.Object r13 = r11.getNextOrClosed()
            r14 = 0
            kotlinx.coroutines.internal.Symbol r15 = kotlinx.coroutines.internal.ConcurrentLinkedListKt.CLOSED
            if (r13 != r15) goto L_0x0102
            r15 = 0
            kotlinx.coroutines.internal.Symbol r16 = kotlinx.coroutines.internal.ConcurrentLinkedListKt.CLOSED
            java.lang.Object r16 = kotlinx.coroutines.internal.SegmentOrClosed.m1297constructorimpl(r16)
            r11 = r16
        L_0x004a:
            r8 = r11
            boolean r9 = kotlinx.coroutines.internal.SegmentOrClosed.m1302isClosedimpl(r8)
            if (r9 != 0) goto L_0x0096
            kotlinx.coroutines.internal.Segment r9 = kotlinx.coroutines.internal.SegmentOrClosed.m1300getSegmentimpl(r8)
            r12 = r6
            r13 = 0
            r14 = r12
            r15 = 0
        L_0x0059:
            java.lang.Object r11 = r14.head
            kotlinx.coroutines.internal.Segment r11 = (kotlinx.coroutines.internal.Segment) r11
            r17 = 0
            long r18 = r11.getId()
            long r20 = r9.getId()
            int r22 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r22 < 0) goto L_0x006e
            r10 = 1
            goto L_0x0088
        L_0x006e:
            boolean r18 = r9.tryIncPointers$kotlinx_coroutines_core()
            if (r18 != 0) goto L_0x0076
            r10 = 0
            goto L_0x0088
        L_0x0076:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r10 = head$FU
            boolean r10 = androidx.concurrent.futures.C0613xc40028dd.m151m(r10, r12, r11, r9)
            if (r10 == 0) goto L_0x008b
            boolean r10 = r11.decPointers$kotlinx_coroutines_core()
            if (r10 == 0) goto L_0x0087
            r11.remove()
        L_0x0087:
            r10 = 1
        L_0x0088:
            if (r10 == 0) goto L_0x0016
            goto L_0x0096
        L_0x008b:
            boolean r10 = r9.decPointers$kotlinx_coroutines_core()
            if (r10 == 0) goto L_0x0094
            r9.remove()
        L_0x0094:
            goto L_0x0059
        L_0x0096:
            kotlinx.coroutines.internal.Segment r6 = kotlinx.coroutines.internal.SegmentOrClosed.m1300getSegmentimpl(r8)
            kotlinx.coroutines.sync.SemaphoreSegment r6 = (kotlinx.coroutines.sync.SemaphoreSegment) r6
            r6.cleanPrev()
            long r7 = r6.getId()
            int r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r9 <= 0) goto L_0x00aa
            r7 = 0
            return r7
        L_0x00aa:
            int r7 = kotlinx.coroutines.sync.SemaphoreKt.SEGMENT_SIZE
            long r7 = (long) r7
            long r7 = r2 % r7
            int r8 = (int) r7
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.sync.SemaphoreKt.PERMIT
            r9 = r6
            r10 = 0
            java.util.concurrent.atomic.AtomicReferenceArray r11 = r9.acquirers
            java.lang.Object r7 = r11.getAndSet(r8, r7)
            if (r7 != 0) goto L_0x00f2
            int r9 = kotlinx.coroutines.sync.SemaphoreKt.MAX_SPIN_CYCLES
            r10 = 0
        L_0x00c7:
            if (r10 >= r9) goto L_0x00df
            int r11 = r10 + 1
            r12 = 0
            r13 = r6
            r14 = 0
            java.util.concurrent.atomic.AtomicReferenceArray r15 = r13.acquirers
            java.lang.Object r13 = r15.get(r8)
            kotlinx.coroutines.internal.Symbol r14 = kotlinx.coroutines.sync.SemaphoreKt.TAKEN
            if (r13 != r14) goto L_0x00dc
            r9 = 1
            return r9
        L_0x00dc:
            r10 = r11
            goto L_0x00c7
        L_0x00df:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.sync.SemaphoreKt.PERMIT
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.sync.SemaphoreKt.BROKEN
            r11 = r6
            r12 = 0
            java.util.concurrent.atomic.AtomicReferenceArray r13 = r11.acquirers
            boolean r9 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m19m(r13, r8, r9, r10)
            r10 = 1
            r9 = r9 ^ r10
            return r9
        L_0x00f2:
            kotlinx.coroutines.internal.Symbol r9 = kotlinx.coroutines.sync.SemaphoreKt.CANCELLED
            if (r7 != r9) goto L_0x00fa
            r9 = 0
            return r9
        L_0x00fa:
            r9 = r7
            kotlinx.coroutines.CancellableContinuation r9 = (kotlinx.coroutines.CancellableContinuation) r9
            boolean r9 = r0.tryResumeAcquire(r9)
            return r9
        L_0x0102:
            r15 = r13
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r15 = (kotlinx.coroutines.internal.ConcurrentLinkedListNode) r15
            r11 = r15
            kotlinx.coroutines.internal.Segment r11 = (kotlinx.coroutines.internal.Segment) r11
            if (r11 == 0) goto L_0x0110
            r10 = r11
            goto L_0x001c
        L_0x0110:
            long r12 = r10.getId()
            r14 = 1
            long r12 = r12 + r14
            r14 = r10
            kotlinx.coroutines.sync.SemaphoreSegment r14 = (kotlinx.coroutines.sync.SemaphoreSegment) r14
            r15 = 0
            kotlinx.coroutines.sync.SemaphoreSegment r12 = kotlinx.coroutines.sync.SemaphoreKt.createSegment(r12, r14)
            kotlinx.coroutines.internal.Segment r12 = (kotlinx.coroutines.internal.Segment) r12
            r13 = r12
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r13 = (kotlinx.coroutines.internal.ConcurrentLinkedListNode) r13
            boolean r13 = r10.trySetNext(r13)
            if (r13 == 0) goto L_0x001c
            boolean r13 = r10.getRemoved()
            if (r13 == 0) goto L_0x0133
            r10.remove()
        L_0x0133:
            r10 = r12
            goto L_0x001c
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.SemaphoreImpl.tryResumeNextFromQueue():boolean");
    }

    private final boolean tryResumeAcquire(CancellableContinuation<? super Unit> $this$tryResumeAcquire) {
        Object token = $this$tryResumeAcquire.tryResume(Unit.INSTANCE, (Object) null, this.onCancellationRelease);
        if (token == null) {
            return false;
        }
        $this$tryResumeAcquire.completeResume(token);
        return true;
    }
}
