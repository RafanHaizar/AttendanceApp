package kotlin.coroutines.jvm.internal;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b!\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u00032\u00020\u0004B\u0017\u0012\u0010\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001¢\u0006\u0002\u0010\u0006J$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00012\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\"\u0010\u0011\u001a\u0004\u0018\u00010\u00022\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0013H$ø\u0001\u0000¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\rH\u0014J\u001e\u0010\u0016\u001a\u00020\r2\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0013ø\u0001\u0000¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0005\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"}, mo113d2 = {"Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Ljava/io/Serializable;", "completion", "(Lkotlin/coroutines/Continuation;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getCompletion", "()Lkotlin/coroutines/Continuation;", "create", "", "value", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "invokeSuspend", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "releaseIntercepted", "resumeWith", "(Ljava/lang/Object;)V", "toString", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ContinuationImpl.kt */
public abstract class BaseContinuationImpl implements Continuation<Object>, CoroutineStackFrame, Serializable {
    private final Continuation<Object> completion;

    /* access modifiers changed from: protected */
    public abstract Object invokeSuspend(Object obj);

    public BaseContinuationImpl(Continuation<Object> completion2) {
        this.completion = completion2;
    }

    public final Continuation<Object> getCompletion() {
        return this.completion;
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [kotlin.coroutines.Continuation, java.lang.Object, kotlin.coroutines.Continuation<java.lang.Object>] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void resumeWith(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 0
            r0 = r7
            r1 = 0
            r1 = r8
        L_0x0004:
            r2 = r0
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineResumed(r2)
            r2 = r0
            kotlin.coroutines.jvm.internal.BaseContinuationImpl r2 = (kotlin.coroutines.jvm.internal.BaseContinuationImpl) r2
            r3 = 0
            kotlin.coroutines.Continuation<java.lang.Object> r4 = r2.completion
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.Object r5 = r2.invokeSuspend(r1)     // Catch:{ all -> 0x0027 }
            java.lang.Object r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch:{ all -> 0x0027 }
            if (r5 != r6) goto L_0x0020
            return
        L_0x0020:
            kotlin.Result$Companion r6 = kotlin.Result.Companion     // Catch:{ all -> 0x0027 }
            java.lang.Object r6 = kotlin.Result.m1345constructorimpl(r5)     // Catch:{ all -> 0x0027 }
            goto L_0x0032
        L_0x0027:
            r5 = move-exception
            kotlin.Result$Companion r6 = kotlin.Result.Companion
            java.lang.Object r6 = kotlin.ResultKt.createFailure(r5)
            java.lang.Object r6 = kotlin.Result.m1345constructorimpl(r6)
        L_0x0032:
            r5 = r6
            r2.releaseIntercepted()
            boolean r6 = r4 instanceof kotlin.coroutines.jvm.internal.BaseContinuationImpl
            if (r6 == 0) goto L_0x003f
            r0 = r4
            r1 = r5
            goto L_0x0004
        L_0x003f:
            r4.resumeWith(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(java.lang.Object):void");
    }

    /* access modifiers changed from: protected */
    public void releaseIntercepted() {
    }

    public Continuation<Unit> create(Continuation<?> completion2) {
        Intrinsics.checkNotNullParameter(completion2, "completion");
        throw new UnsupportedOperationException("create(Continuation) has not been overridden");
    }

    public Continuation<Unit> create(Object value, Continuation<?> completion2) {
        Intrinsics.checkNotNullParameter(completion2, "completion");
        throw new UnsupportedOperationException("create(Any?;Continuation) has not been overridden");
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("Continuation at ");
        Serializable stackTraceElement = getStackTraceElement();
        if (stackTraceElement == null) {
            stackTraceElement = getClass().getName();
        }
        return append.append(stackTraceElement).toString();
    }

    public CoroutineStackFrame getCallerFrame() {
        Continuation<Object> continuation = this.completion;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    public StackTraceElement getStackTraceElement() {
        return DebugMetadataKt.getStackTraceElement(this);
    }
}
