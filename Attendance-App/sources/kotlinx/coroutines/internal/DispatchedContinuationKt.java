package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

@Metadata(mo112d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a;\u0010\u0006\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00072\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\b\u001aU\u0010\u0011\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u00120\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u00152%\b\u0002\u0010\u0016\u001a\u001f\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0017H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a\u0012\u0010\u001d\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00100\bH\u0000\"\u0016\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0003\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, mo113d2 = {"REUSABLE_CLAIMED", "Lkotlinx/coroutines/internal/Symbol;", "getREUSABLE_CLAIMED$annotations", "()V", "UNDEFINED", "getUNDEFINED$annotations", "executeUnconfined", "", "Lkotlinx/coroutines/internal/DispatchedContinuation;", "contState", "", "mode", "", "doYield", "block", "Lkotlin/Function0;", "", "resumeCancellableWith", "T", "Lkotlin/coroutines/Continuation;", "result", "Lkotlin/Result;", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "yieldUndispatched", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: DispatchedContinuation.kt */
public final class DispatchedContinuationKt {
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");
    /* access modifiers changed from: private */
    public static final Symbol UNDEFINED = new Symbol("UNDEFINED");

    public static /* synthetic */ void getREUSABLE_CLAIMED$annotations() {
    }

    private static /* synthetic */ void getUNDEFINED$annotations() {
    }

    public static /* synthetic */ void resumeCancellableWith$default(Continuation continuation, Object obj, Function1 function1, int i, Object obj2) {
        if ((i & 2) != 0) {
            function1 = null;
        }
        resumeCancellableWith(continuation, obj, function1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f8, code lost:
        if (r18.clearThreadContext() != false) goto L_0x00fa;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x011d A[Catch:{ all -> 0x00ff, all -> 0x012c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> void resumeCancellableWith(kotlin.coroutines.Continuation<? super T> r23, java.lang.Object r24, kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> r25) {
        /*
            r1 = r23
            boolean r0 = r1 instanceof kotlinx.coroutines.internal.DispatchedContinuation
            if (r0 == 0) goto L_0x014a
            r2 = r1
            kotlinx.coroutines.internal.DispatchedContinuation r2 = (kotlinx.coroutines.internal.DispatchedContinuation) r2
            r3 = 0
            java.lang.Object r4 = kotlinx.coroutines.CompletionStateKt.toState((java.lang.Object) r24, (kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit>) r25)
            kotlinx.coroutines.CoroutineDispatcher r0 = r2.dispatcher
            kotlin.coroutines.CoroutineContext r5 = r2.getContext()
            boolean r0 = r0.isDispatchNeeded(r5)
            r5 = 1
            if (r0 == 0) goto L_0x0033
            r2._state = r4
            r2.resumeMode = r5
            kotlinx.coroutines.CoroutineDispatcher r0 = r2.dispatcher
            kotlin.coroutines.CoroutineContext r5 = r2.getContext()
            r6 = r2
            java.lang.Runnable r6 = (java.lang.Runnable) r6
            r0.dispatch(r5, r6)
            r1 = r24
            r19 = r2
            r20 = r3
            goto L_0x0141
        L_0x0033:
            r0 = 1
            r6 = r2
            r7 = r0
            r8 = 0
            r9 = 0
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x0041
            r0 = 0
        L_0x0041:
            kotlinx.coroutines.ThreadLocalEventLoop r0 = kotlinx.coroutines.ThreadLocalEventLoop.INSTANCE
            kotlinx.coroutines.EventLoop r10 = r0.getEventLoop$kotlinx_coroutines_core()
            boolean r0 = r10.isUnconfinedLoopActive()
            if (r0 == 0) goto L_0x0060
            r6._state = r4
            r6.resumeMode = r7
            r0 = r6
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0
            r10.dispatchUnconfined(r0)
            r1 = r24
            r19 = r2
            r20 = r3
            goto L_0x0140
        L_0x0060:
            r11 = r6
            kotlinx.coroutines.DispatchedTask r11 = (kotlinx.coroutines.DispatchedTask) r11
            r12 = 0
            r10.incrementUseCount(r5)
            r13 = 0
            r0 = r2
            r14 = 0
            kotlin.coroutines.CoroutineContext r5 = r0.getContext()     // Catch:{ all -> 0x012e }
            kotlinx.coroutines.Job$Key r16 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x012e }
            r15 = r16
            kotlin.coroutines.CoroutineContext$Key r15 = (kotlin.coroutines.CoroutineContext.Key) r15     // Catch:{ all -> 0x012e }
            kotlin.coroutines.CoroutineContext$Element r5 = r5.get(r15)     // Catch:{ all -> 0x012e }
            kotlinx.coroutines.Job r5 = (kotlinx.coroutines.Job) r5     // Catch:{ all -> 0x012e }
            if (r5 == 0) goto L_0x00ae
            boolean r15 = r5.isActive()     // Catch:{ all -> 0x00a5 }
            if (r15 != 0) goto L_0x00ae
            java.util.concurrent.CancellationException r15 = r5.getCancellationException()     // Catch:{ all -> 0x00a5 }
            r1 = r15
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x00a5 }
            r0.cancelCompletedResult$kotlinx_coroutines_core(r4, r1)     // Catch:{ all -> 0x00a5 }
            r1 = r0
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1     // Catch:{ all -> 0x00a5 }
            kotlin.Result$Companion r16 = kotlin.Result.Companion     // Catch:{ all -> 0x00a5 }
            r16 = r15
            java.lang.Throwable r16 = (java.lang.Throwable) r16     // Catch:{ all -> 0x00a5 }
            java.lang.Object r16 = kotlin.ResultKt.createFailure(r16)     // Catch:{ all -> 0x00a5 }
            r18 = r0
            java.lang.Object r0 = kotlin.Result.m1345constructorimpl(r16)     // Catch:{ all -> 0x00a5 }
            r1.resumeWith(r0)     // Catch:{ all -> 0x00a5 }
            r0 = 1
            goto L_0x00b1
        L_0x00a5:
            r0 = move-exception
            r1 = r24
            r19 = r2
            r20 = r3
            goto L_0x0135
        L_0x00ae:
            r18 = r0
            r0 = 0
        L_0x00b1:
            if (r0 != 0) goto L_0x011d
            r1 = r2
            r5 = 0
            kotlin.coroutines.Continuation<T> r0 = r1.continuation     // Catch:{ all -> 0x012e }
            java.lang.Object r14 = r1.countOrElement     // Catch:{ all -> 0x012e }
            r15 = r0
            r16 = 0
            kotlin.coroutines.CoroutineContext r0 = r15.getContext()     // Catch:{ all -> 0x012e }
            r18 = r0
            r19 = r2
            r2 = r18
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r2, r14)     // Catch:{ all -> 0x0117 }
            r18 = r0
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch:{ all -> 0x0117 }
            r20 = r3
            r3 = r18
            if (r3 == r0) goto L_0x00d9
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r15, r2, r3)     // Catch:{ all -> 0x0113 }
            goto L_0x00e0
        L_0x00d9:
            r17 = 0
            r0 = r17
            kotlinx.coroutines.UndispatchedCoroutine r0 = (kotlinx.coroutines.UndispatchedCoroutine) r0     // Catch:{ all -> 0x0113 }
            r0 = 0
        L_0x00e0:
            r18 = r0
            r0 = 0
            r21 = r0
            kotlin.coroutines.Continuation<T> r0 = r1.continuation     // Catch:{ all -> 0x0101 }
            r22 = r1
            r1 = r24
            r0.resumeWith(r1)     // Catch:{ all -> 0x00ff }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ff }
            if (r18 == 0) goto L_0x00fa
            boolean r0 = r18.clearThreadContext()     // Catch:{ all -> 0x012c }
            if (r0 == 0) goto L_0x00fd
        L_0x00fa:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r2, r3)     // Catch:{ all -> 0x012c }
        L_0x00fd:
            goto L_0x0123
        L_0x00ff:
            r0 = move-exception
            goto L_0x0106
        L_0x0101:
            r0 = move-exception
            r22 = r1
            r1 = r24
        L_0x0106:
            if (r18 == 0) goto L_0x010e
            boolean r21 = r18.clearThreadContext()     // Catch:{ all -> 0x012c }
            if (r21 == 0) goto L_0x0111
        L_0x010e:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r2, r3)     // Catch:{ all -> 0x012c }
        L_0x0111:
            throw r0     // Catch:{ all -> 0x012c }
        L_0x0113:
            r0 = move-exception
            r1 = r24
            goto L_0x0135
        L_0x0117:
            r0 = move-exception
            r1 = r24
            r20 = r3
            goto L_0x0135
        L_0x011d:
            r1 = r24
            r19 = r2
            r20 = r3
        L_0x0123:
        L_0x0124:
            boolean r0 = r10.processUnconfinedEvent()     // Catch:{ all -> 0x012c }
            if (r0 != 0) goto L_0x0124
            goto L_0x0139
        L_0x012c:
            r0 = move-exception
            goto L_0x0135
        L_0x012e:
            r0 = move-exception
            r1 = r24
            r19 = r2
            r20 = r3
        L_0x0135:
            r2 = 0
            r11.handleFatalException(r0, r2)     // Catch:{ all -> 0x0143 }
        L_0x0139:
            r2 = 1
            r10.decrementUseCount(r2)
        L_0x0140:
        L_0x0141:
            goto L_0x014f
        L_0x0143:
            r0 = move-exception
            r2 = r0
            r3 = 1
            r10.decrementUseCount(r3)
            throw r2
        L_0x014a:
            r1 = r24
            r23.resumeWith(r24)
        L_0x014f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.DispatchedContinuationKt.resumeCancellableWith(kotlin.coroutines.Continuation, java.lang.Object, kotlin.jvm.functions.Function1):void");
    }

    public static final boolean yieldUndispatched(DispatchedContinuation<? super Unit> $this$yieldUndispatched) {
        Object contState$iv = Unit.INSTANCE;
        DispatchedContinuation<? super Unit> dispatchedContinuation = $this$yieldUndispatched;
        if (DebugKt.getASSERTIONS_ENABLED()) {
        }
        EventLoop eventLoop$iv = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$iv.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop$iv.isUnconfinedLoopActive()) {
            dispatchedContinuation._state = contState$iv;
            dispatchedContinuation.resumeMode = 1;
            eventLoop$iv.dispatchUnconfined(dispatchedContinuation);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv$iv = dispatchedContinuation;
        eventLoop$iv.incrementUseCount(true);
        try {
            $this$yieldUndispatched.run();
            do {
            } while (eventLoop$iv.processUnconfinedEvent());
        } catch (Throwable th) {
            eventLoop$iv.decrementUseCount(true);
            throw th;
        }
        eventLoop$iv.decrementUseCount(true);
        return false;
    }

    static /* synthetic */ boolean executeUnconfined$default(DispatchedContinuation $this$executeUnconfined_u24default, Object contState, int mode, boolean doYield, Function0 block, int i, Object obj) {
        if ((i & 4) != 0) {
            doYield = false;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((mode != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        EventLoop eventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (doYield && eventLoop.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop.isUnconfinedLoopActive()) {
            $this$executeUnconfined_u24default._state = contState;
            $this$executeUnconfined_u24default.resumeMode = mode;
            eventLoop.dispatchUnconfined($this$executeUnconfined_u24default);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv = $this$executeUnconfined_u24default;
        eventLoop.incrementUseCount(true);
        try {
            block.invoke();
            do {
            } while (eventLoop.processUnconfinedEvent());
            InlineMarker.finallyStart(1);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            eventLoop.decrementUseCount(true);
            InlineMarker.finallyEnd(1);
            throw th;
        }
        eventLoop.decrementUseCount(true);
        InlineMarker.finallyEnd(1);
        return false;
    }

    private static final boolean executeUnconfined(DispatchedContinuation<?> $this$executeUnconfined, Object contState, int mode, boolean doYield, Function0<Unit> block) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((mode != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        EventLoop eventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (doYield && eventLoop.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop.isUnconfinedLoopActive()) {
            $this$executeUnconfined._state = contState;
            $this$executeUnconfined.resumeMode = mode;
            eventLoop.dispatchUnconfined($this$executeUnconfined);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv = $this$executeUnconfined;
        eventLoop.incrementUseCount(true);
        try {
            block.invoke();
            do {
            } while (eventLoop.processUnconfinedEvent());
            InlineMarker.finallyStart(1);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            eventLoop.decrementUseCount(true);
            InlineMarker.finallyEnd(1);
            throw th;
        }
        eventLoop.decrementUseCount(true);
        InlineMarker.finallyEnd(1);
        return false;
    }
}
