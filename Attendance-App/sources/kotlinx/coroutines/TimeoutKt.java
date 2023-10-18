package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(mo112d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a_\u0010\u0006\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\b\"\b\b\u0001\u0010\t*\u0002H\b2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\n2'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001aU\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\u0013\u001a]\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0004\b\u0016\u0010\u0013\u001aJ\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aR\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0018\u0010\u0013\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0019"}, mo113d2 = {"TimeoutCancellationException", "Lkotlinx/coroutines/TimeoutCancellationException;", "time", "", "coroutine", "Lkotlinx/coroutines/Job;", "setupTimeout", "", "U", "T", "Lkotlinx/coroutines/TimeoutCoroutine;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/TimeoutCoroutine;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "withTimeout", "timeMillis", "(JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "timeout", "Lkotlin/time/Duration;", "withTimeout-KLykuaI", "withTimeoutOrNull", "withTimeoutOrNull-KLykuaI", "kotlinx-coroutines-core"}, mo114k = 2, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Timeout.kt */
public final class TimeoutKt {
    public static final <T> Object withTimeout(long timeMillis, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        if (timeMillis > 0) {
            Object obj = setupTimeout(new TimeoutCoroutine(timeMillis, $completion), block);
            if (obj == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return obj;
        }
        throw new TimeoutCancellationException("Timed out immediately");
    }

    /* renamed from: withTimeout-KLykuaI  reason: not valid java name */
    public static final <T> Object m1875withTimeoutKLykuaI(long timeout, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        return withTimeout(DelayKt.m1872toDelayMillisLRDsOJo(timeout), block, $completion);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0080 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object withTimeoutOrNull(long r8, kotlin.jvm.functions.Function2<? super kotlinx.coroutines.CoroutineScope, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r10, kotlin.coroutines.Continuation<? super T> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = (kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = new kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1
            r0.<init>(r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x003f;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002e:
            long r8 = r11.J$0
            java.lang.Object r8 = r11.L$1
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            java.lang.Object r9 = r11.L$0
            kotlin.jvm.functions.Function2 r9 = (kotlin.jvm.functions.Function2) r9
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ TimeoutCancellationException -> 0x003d }
            r7 = r0
            goto L_0x0077
        L_0x003d:
            r9 = move-exception
            goto L_0x007a
        L_0x003f:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = 0
            int r2 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x0049
            return r3
        L_0x0049:
            kotlin.jvm.internal.Ref$ObjectRef r2 = new kotlin.jvm.internal.Ref$ObjectRef
            r2.<init>()
            r11.L$0 = r10     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r11.L$1 = r2     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r11.J$0 = r8     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r4 = 1
            r11.label = r4     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r4 = r11
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r5 = 0
            kotlinx.coroutines.TimeoutCoroutine r6 = new kotlinx.coroutines.TimeoutCoroutine     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r6.<init>(r8, r4)     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r2.element = r6     // Catch:{ TimeoutCancellationException -> 0x0078 }
            java.lang.Object r7 = setupTimeout(r6, r10)     // Catch:{ TimeoutCancellationException -> 0x0078 }
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch:{ TimeoutCancellationException -> 0x0078 }
            if (r7 != r4) goto L_0x0073
            r4 = r11
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4     // Catch:{ TimeoutCancellationException -> 0x0078 }
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r4)     // Catch:{ TimeoutCancellationException -> 0x0078 }
        L_0x0073:
            if (r7 != r1) goto L_0x0076
            return r1
        L_0x0076:
            r8 = r2
        L_0x0077:
            return r7
        L_0x0078:
            r9 = move-exception
            r8 = r2
        L_0x007a:
            kotlinx.coroutines.Job r10 = r9.coroutine
            T r1 = r8.element
            if (r10 != r1) goto L_0x0081
            return r3
        L_0x0081:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(long, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* renamed from: withTimeoutOrNull-KLykuaI  reason: not valid java name */
    public static final <T> Object m1876withTimeoutOrNullKLykuaI(long timeout, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        return withTimeoutOrNull(DelayKt.m1872toDelayMillisLRDsOJo(timeout), block, $completion);
    }

    private static final <U, T extends U> Object setupTimeout(TimeoutCoroutine<U, ? super T> coroutine, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        JobKt.disposeOnCompletion(coroutine, DelayKt.getDelay(coroutine.uCont.getContext()).invokeOnTimeout(coroutine.time, coroutine, coroutine.getContext()));
        return UndispatchedKt.startUndispatchedOrReturnIgnoreTimeout(coroutine, coroutine, block);
    }

    public static final TimeoutCancellationException TimeoutCancellationException(long time, Job coroutine) {
        return new TimeoutCancellationException("Timed out waiting for " + time + " ms", coroutine);
    }
}
