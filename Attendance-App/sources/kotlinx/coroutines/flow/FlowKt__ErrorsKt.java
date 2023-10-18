package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(mo112d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001ah\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012B\u0010\u0003\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0004¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001a1\u0010\u000f\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u0019\u0010\u0012\u001a\u00020\u0013*\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0002¢\u0006\u0002\b\u0016\u001a\u001b\u0010\u0017\u001a\u00020\u0013*\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0002\b\u0019\u001ac\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\b\b\u0002\u0010\u001b\u001a\u00020\u001c23\b\u0002\u0010\u001d\u001a-\b\u0001\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001eø\u0001\u0000¢\u0006\u0002\u0010\u001f\u001a}\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012W\u0010\u001d\u001aS\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\n\u0012\u0006\u0012\u0004\u0018\u00010\f0!¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010#\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, mo113d2 = {"catch", "Lkotlinx/coroutines/flow/Flow;", "T", "action", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "Lkotlin/ParameterName;", "name", "cause", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "catchImpl", "collector", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isCancellationCause", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "isCancellationCause$FlowKt__ErrorsKt", "isSameExceptionAs", "other", "isSameExceptionAs$FlowKt__ErrorsKt", "retry", "retries", "", "predicate", "Lkotlin/Function2;", "(Lkotlinx/coroutines/flow/Flow;JLkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "retryWhen", "Lkotlin/Function4;", "attempt", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function4;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Errors.kt */
final /* synthetic */ class FlowKt__ErrorsKt {
    /* renamed from: catch  reason: not valid java name */
    public static final <T> Flow<T> m1283catch(Flow<? extends T> $this$catch, Function3<? super FlowCollector<? super T>, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> action) {
        return new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1($this$catch, action);
    }

    public static /* synthetic */ Flow retry$default(Flow flow, long j, Function2 function2, int i, Object obj) {
        if ((i & 1) != 0) {
            j = Long.MAX_VALUE;
        }
        if ((i & 2) != 0) {
            function2 = new FlowKt__ErrorsKt$retry$1((Continuation<? super FlowKt__ErrorsKt$retry$1>) null);
        }
        return FlowKt.retry(flow, j, function2);
    }

    public static final <T> Flow<T> retry(Flow<? extends T> $this$retry, long retries, Function2<? super Throwable, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        if (retries > 0) {
            return FlowKt.retryWhen($this$retry, new FlowKt__ErrorsKt$retry$3(retries, predicate, (Continuation<? super FlowKt__ErrorsKt$retry$3>) null));
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Expected positive amount of retries, but had ", Long.valueOf(retries)).toString());
    }

    public static final <T> Flow<T> retryWhen(Flow<? extends T> $this$retryWhen, Function4<? super FlowCollector<? super T>, ? super Throwable, ? super Long, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return new FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1($this$retryWhen, predicate);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object catchImpl(kotlinx.coroutines.flow.Flow<? extends T> r5, kotlinx.coroutines.flow.FlowCollector<? super T> r6, kotlin.coroutines.Continuation<? super java.lang.Throwable> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1 r0 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1 r0 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$1
            r0.<init>(r7)
        L_0x0019:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002d:
            java.lang.Object r5 = r7.L$0
            kotlin.jvm.internal.Ref$ObjectRef r5 = (kotlin.jvm.internal.Ref.ObjectRef) r5
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0035 }
            goto L_0x0054
        L_0x0035:
            r6 = move-exception
            goto L_0x0059
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r0)
            kotlin.jvm.internal.Ref$ObjectRef r2 = new kotlin.jvm.internal.Ref$ObjectRef
            r2.<init>()
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2 r3 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2     // Catch:{ all -> 0x0057 }
            r3.<init>(r6, r2)     // Catch:{ all -> 0x0057 }
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3     // Catch:{ all -> 0x0057 }
            r7.L$0 = r2     // Catch:{ all -> 0x0057 }
            r4 = 1
            r7.label = r4     // Catch:{ all -> 0x0057 }
            java.lang.Object r3 = r5.collect(r3, r7)     // Catch:{ all -> 0x0057 }
            if (r3 != r1) goto L_0x0053
            return r1
        L_0x0053:
            r5 = r2
        L_0x0054:
            r6 = 0
            return r6
        L_0x0057:
            r6 = move-exception
            r5 = r2
        L_0x0059:
            T r1 = r5.element
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            boolean r2 = isSameExceptionAs$FlowKt__ErrorsKt(r6, r1)
            if (r2 != 0) goto L_0x007c
            kotlin.coroutines.CoroutineContext r2 = r7.getContext()
            boolean r2 = isCancellationCause$FlowKt__ErrorsKt(r6, r2)
            if (r2 != 0) goto L_0x007c
            if (r1 != 0) goto L_0x0070
            return r6
        L_0x0070:
            boolean r2 = r6 instanceof java.util.concurrent.CancellationException
            if (r2 == 0) goto L_0x0078
            kotlin.ExceptionsKt.addSuppressed(r1, r6)
            throw r1
        L_0x0078:
            kotlin.ExceptionsKt.addSuppressed(r6, r1)
            throw r6
        L_0x007c:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt.catchImpl(kotlinx.coroutines.flow.Flow, kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final boolean isCancellationCause$FlowKt__ErrorsKt(Throwable $this$isCancellationCause, CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job == null || !job.isCancelled()) {
            return false;
        }
        return isSameExceptionAs$FlowKt__ErrorsKt($this$isCancellationCause, job.getCancellationException());
    }

    private static final boolean isSameExceptionAs$FlowKt__ErrorsKt(Throwable $this$isSameExceptionAs, Throwable other) {
        if (other != null) {
            if (Intrinsics.areEqual((Object) !DebugKt.getRECOVER_STACK_TRACES() ? other : StackTraceRecoveryKt.unwrapImpl(other), (Object) !DebugKt.getRECOVER_STACK_TRACES() ? $this$isSameExceptionAs : StackTraceRecoveryKt.unwrapImpl($this$isSameExceptionAs)) != 0) {
                return true;
            }
        }
        return false;
    }
}
