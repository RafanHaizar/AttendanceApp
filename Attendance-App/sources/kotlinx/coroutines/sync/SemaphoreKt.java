package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.Symbol;

@Metadata(mo112d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u001a\u001a\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0014H\u0002\u001a6\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0019*\u00020\u00102\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00190\u001bHHø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u001c\"\u0016\u0010\u0000\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0003\"\u0016\u0010\u0006\u001a\u00020\u00078\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0003\"\u0016\u0010\t\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\n\u0010\u0003\"\u0016\u0010\u000b\u001a\u00020\u00078\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\u0003\"\u0016\u0010\r\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u000e\u0010\u0003\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, mo113d2 = {"BROKEN", "Lkotlinx/coroutines/internal/Symbol;", "getBROKEN$annotations", "()V", "CANCELLED", "getCANCELLED$annotations", "MAX_SPIN_CYCLES", "", "getMAX_SPIN_CYCLES$annotations", "PERMIT", "getPERMIT$annotations", "SEGMENT_SIZE", "getSEGMENT_SIZE$annotations", "TAKEN", "getTAKEN$annotations", "Semaphore", "Lkotlinx/coroutines/sync/Semaphore;", "permits", "acquiredPermits", "createSegment", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "id", "", "prev", "withPermit", "T", "action", "Lkotlin/Function0;", "(Lkotlinx/coroutines/sync/Semaphore;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Semaphore.kt */
public final class SemaphoreKt {
    /* access modifiers changed from: private */
    public static final Symbol BROKEN = new Symbol("BROKEN");
    /* access modifiers changed from: private */
    public static final Symbol CANCELLED = new Symbol("CANCELLED");
    /* access modifiers changed from: private */
    public static final int MAX_SPIN_CYCLES = SystemPropsKt__SystemProps_commonKt.systemProp$default("kotlinx.coroutines.semaphore.maxSpinCycles", 100, 0, 0, 12, (Object) null);
    /* access modifiers changed from: private */
    public static final Symbol PERMIT = new Symbol("PERMIT");
    /* access modifiers changed from: private */
    public static final int SEGMENT_SIZE = SystemPropsKt__SystemProps_commonKt.systemProp$default("kotlinx.coroutines.semaphore.segmentSize", 16, 0, 0, 12, (Object) null);
    /* access modifiers changed from: private */
    public static final Symbol TAKEN = new Symbol("TAKEN");

    private static /* synthetic */ void getBROKEN$annotations() {
    }

    private static /* synthetic */ void getCANCELLED$annotations() {
    }

    private static /* synthetic */ void getMAX_SPIN_CYCLES$annotations() {
    }

    private static /* synthetic */ void getPERMIT$annotations() {
    }

    private static /* synthetic */ void getSEGMENT_SIZE$annotations() {
    }

    private static /* synthetic */ void getTAKEN$annotations() {
    }

    public static final Semaphore Semaphore(int permits, int acquiredPermits) {
        return new SemaphoreImpl(permits, acquiredPermits);
    }

    public static /* synthetic */ Semaphore Semaphore$default(int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return Semaphore(i, i2);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlin.jvm.functions.Function0<? extends T>, code=kotlin.jvm.functions.Function0, for r6v0, types: [kotlin.jvm.functions.Function0<? extends T>, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object withPermit(kotlinx.coroutines.sync.Semaphore r5, kotlin.jvm.functions.Function0 r6, kotlin.coroutines.Continuation<? super T> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.sync.SemaphoreKt$withPermit$1
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.sync.SemaphoreKt$withPermit$1 r0 = (kotlinx.coroutines.sync.SemaphoreKt$withPermit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.sync.SemaphoreKt$withPermit$1 r0 = new kotlinx.coroutines.sync.SemaphoreKt$withPermit$1
            r0.<init>(r7)
        L_0x0019:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002e:
            r5 = 0
            java.lang.Object r6 = r7.L$1
            kotlin.jvm.functions.Function0 r6 = (kotlin.jvm.functions.Function0) r6
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.sync.Semaphore r1 = (kotlinx.coroutines.sync.Semaphore) r1
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x004f
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = 0
            r7.L$0 = r5
            r7.L$1 = r6
            r7.label = r3
            java.lang.Object r4 = r5.acquire(r7)
            if (r4 != r1) goto L_0x004d
            return r1
        L_0x004d:
            r1 = r5
            r5 = r2
        L_0x004f:
            java.lang.Object r2 = r6.invoke()     // Catch:{ all -> 0x005e }
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            r1.release()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            return r2
        L_0x005e:
            r2 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            r1.release()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.SemaphoreKt.withPermit(kotlinx.coroutines.sync.Semaphore, kotlin.jvm.functions.Function0, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <T> Object withPermit$$forInline(Semaphore $this$withPermit, Function0<? extends T> action, Continuation<? super T> $completion) {
        InlineMarker.mark(0);
        $this$withPermit.acquire($completion);
        InlineMarker.mark(1);
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            $this$withPermit.release();
            InlineMarker.finallyEnd(1);
        }
    }

    /* access modifiers changed from: private */
    public static final SemaphoreSegment createSegment(long id, SemaphoreSegment prev) {
        return new SemaphoreSegment(id, prev, 0);
    }
}
