package kotlinx.coroutines.debug.internal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

@Metadata(mo112d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B!\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014J\b\u0010$\u001a\u00020\u000eH\u0016J!\u0010%\u001a\u00020&2\u0006\u0010 \u001a\u00020\u000e2\n\u0010'\u001a\u0006\u0012\u0002\b\u00030(H\u0000¢\u0006\u0002\b)J%\u0010*\u001a\u00020&*\b\u0012\u0004\u0012\u00020\u00150+2\b\u0010'\u001a\u0004\u0018\u00010\fHPø\u0001\u0000¢\u0006\u0002\u0010,R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\nX\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\nX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R(\u0010\u0019\u001a\u0004\u0018\u00010\f2\b\u0010\u0018\u001a\u0004\u0018\u00010\f8@@@X\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u001f8\u0000@\u0000X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0000X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b!\u0010\"\u0002\u0004\n\u0002\b\u0019¨\u0006-"}, mo113d2 = {"Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "", "context", "Lkotlin/coroutines/CoroutineContext;", "creationStackBottom", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "sequenceNumber", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/debug/internal/StackTraceFrame;J)V", "_context", "Ljava/lang/ref/WeakReference;", "_lastObservedFrame", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "_state", "", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getCreationStackBottom", "()Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "creationStackTrace", "", "Ljava/lang/StackTraceElement;", "getCreationStackTrace", "()Ljava/util/List;", "value", "lastObservedFrame", "getLastObservedFrame$kotlinx_coroutines_core", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "setLastObservedFrame$kotlinx_coroutines_core", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V", "lastObservedThread", "Ljava/lang/Thread;", "state", "getState", "()Ljava/lang/String;", "lastObservedStackTrace", "toString", "updateState", "", "frame", "Lkotlin/coroutines/Continuation;", "updateState$kotlinx_coroutines_core", "yieldFrames", "Lkotlin/sequences/SequenceScope;", "(Lkotlin/sequences/SequenceScope;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: DebugCoroutineInfoImpl.kt */
public final class DebugCoroutineInfoImpl {
    private final WeakReference<CoroutineContext> _context;
    private WeakReference<CoroutineStackFrame> _lastObservedFrame;
    private String _state = DebugCoroutineInfoImplKt.CREATED;
    private final StackTraceFrame creationStackBottom;
    public Thread lastObservedThread;
    public final long sequenceNumber;

    public DebugCoroutineInfoImpl(CoroutineContext context, StackTraceFrame creationStackBottom2, long sequenceNumber2) {
        this.creationStackBottom = creationStackBottom2;
        this.sequenceNumber = sequenceNumber2;
        this._context = new WeakReference<>(context);
    }

    public final StackTraceFrame getCreationStackBottom() {
        return this.creationStackBottom;
    }

    public final CoroutineContext getContext() {
        return (CoroutineContext) this._context.get();
    }

    public final List<StackTraceElement> getCreationStackTrace() {
        return creationStackTrace();
    }

    public final String getState() {
        return this._state;
    }

    public final CoroutineStackFrame getLastObservedFrame$kotlinx_coroutines_core() {
        WeakReference<CoroutineStackFrame> weakReference = this._lastObservedFrame;
        if (weakReference == null) {
            return null;
        }
        return (CoroutineStackFrame) weakReference.get();
    }

    public final void setLastObservedFrame$kotlinx_coroutines_core(CoroutineStackFrame value) {
        WeakReference<CoroutineStackFrame> weakReference;
        if (value == null) {
            weakReference = null;
        } else {
            weakReference = new WeakReference<>(value);
        }
        this._lastObservedFrame = weakReference;
    }

    public final List<StackTraceElement> lastObservedStackTrace() {
        CoroutineStackFrame frame = getLastObservedFrame$kotlinx_coroutines_core();
        if (frame == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList result = new ArrayList();
        while (frame != null) {
            StackTraceElement it = frame.getStackTraceElement();
            if (it != null) {
                result.add(it);
            }
            frame = frame.getCallerFrame();
        }
        return result;
    }

    private final List<StackTraceElement> creationStackTrace() {
        StackTraceFrame bottom = this.creationStackBottom;
        if (bottom == null) {
            return CollectionsKt.emptyList();
        }
        return SequencesKt.toList(SequencesKt.sequence(new DebugCoroutineInfoImpl$creationStackTrace$1(this, bottom, (Continuation<? super DebugCoroutineInfoImpl$creationStackTrace$1>) null)));
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object yieldFrames(kotlin.sequences.SequenceScope<? super java.lang.StackTraceElement> r7, kotlin.coroutines.jvm.internal.CoroutineStackFrame r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r6 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl$yieldFrames$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl$yieldFrames$1 r0 = (kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl$yieldFrames$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl$yieldFrames$1 r0 = new kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl$yieldFrames$1
            r0.<init>(r6, r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x003e;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002d:
            r7 = 0
            java.lang.Object r8 = r9.L$2
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r8 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r8
            java.lang.Object r2 = r9.L$1
            kotlin.sequences.SequenceScope r2 = (kotlin.sequences.SequenceScope) r2
            java.lang.Object r3 = r9.L$0
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r3 = (kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl) r3
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0062
        L_0x003e:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            r3 = r2
            r2 = r7
        L_0x0044:
            if (r8 != 0) goto L_0x0049
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L_0x0049:
            java.lang.StackTraceElement r7 = r8.getStackTraceElement()
            if (r7 != 0) goto L_0x0050
            goto L_0x0063
        L_0x0050:
            r4 = 0
            r9.L$0 = r3
            r9.L$1 = r2
            r9.L$2 = r8
            r5 = 1
            r9.label = r5
            java.lang.Object r7 = r2.yield(r7, r9)
            if (r7 != r1) goto L_0x0061
            return r1
        L_0x0061:
            r7 = r4
        L_0x0062:
        L_0x0063:
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r8 = r8.getCallerFrame()
            if (r8 == 0) goto L_0x006a
            goto L_0x0044
        L_0x006a:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl.yieldFrames(kotlin.sequences.SequenceScope, kotlin.coroutines.jvm.internal.CoroutineStackFrame, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void updateState$kotlinx_coroutines_core(String state, Continuation<?> frame) {
        if (!Intrinsics.areEqual((Object) this._state, (Object) state) || !Intrinsics.areEqual((Object) state, (Object) DebugCoroutineInfoImplKt.SUSPENDED) || getLastObservedFrame$kotlinx_coroutines_core() == null) {
            this._state = state;
            Thread thread = null;
            setLastObservedFrame$kotlinx_coroutines_core(frame instanceof CoroutineStackFrame ? (CoroutineStackFrame) frame : null);
            if (Intrinsics.areEqual((Object) state, (Object) DebugCoroutineInfoImplKt.RUNNING)) {
                thread = Thread.currentThread();
            } else {
                Thread thread2 = null;
            }
            this.lastObservedThread = thread;
        }
    }

    public String toString() {
        return "DebugCoroutineInfo(state=" + getState() + ",context=" + getContext() + ')';
    }
}
