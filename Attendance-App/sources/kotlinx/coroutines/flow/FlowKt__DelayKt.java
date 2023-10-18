package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.FlowCoroutineKt;

@Metadata(mo112d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00050\u0004H\u0007\u001a:\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00070\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\b\b\u001a&\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0005H\u0007\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\t\u0010\n\u001a7\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00050\u0004H\u0002¢\u0006\u0002\b\r\u001a$\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u0005H\u0000\u001a&\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0005H\u0007\u001a3\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0007H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0017\u0010\n\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0018"}, mo113d2 = {"debounce", "Lkotlinx/coroutines/flow/Flow;", "T", "timeoutMillis", "Lkotlin/Function1;", "", "timeout", "Lkotlin/time/Duration;", "debounceDuration", "debounce-HG0u8IE", "(Lkotlinx/coroutines/flow/Flow;J)Lkotlinx/coroutines/flow/Flow;", "debounceInternal", "timeoutMillisSelector", "debounceInternal$FlowKt__DelayKt", "fixedPeriodTicker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "", "Lkotlinx/coroutines/CoroutineScope;", "delayMillis", "initialDelayMillis", "sample", "periodMillis", "period", "sample-HG0u8IE", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Delay.kt */
final /* synthetic */ class FlowKt__DelayKt {
    public static final <T> Flow<T> debounce(Flow<? extends T> $this$debounce, long timeoutMillis) {
        if (!(timeoutMillis >= 0)) {
            throw new IllegalArgumentException("Debounce timeout should not be negative".toString());
        } else if (timeoutMillis == 0) {
            return $this$debounce;
        } else {
            return debounceInternal$FlowKt__DelayKt($this$debounce, new FlowKt__DelayKt$debounce$2(timeoutMillis));
        }
    }

    public static final <T> Flow<T> debounce(Flow<? extends T> $this$debounce, Function1<? super T, Long> timeoutMillis) {
        return debounceInternal$FlowKt__DelayKt($this$debounce, timeoutMillis);
    }

    /* renamed from: debounce-HG0u8IE  reason: not valid java name */
    public static final <T> Flow<T> m1279debounceHG0u8IE(Flow<? extends T> $this$debounce, long timeout) {
        return FlowKt.debounce($this$debounce, DelayKt.m1872toDelayMillisLRDsOJo(timeout));
    }

    public static final <T> Flow<T> debounceDuration(Flow<? extends T> $this$debounce, Function1<? super T, Duration> timeout) {
        return debounceInternal$FlowKt__DelayKt($this$debounce, new FlowKt__DelayKt$debounce$3(timeout));
    }

    private static final <T> Flow<T> debounceInternal$FlowKt__DelayKt(Flow<? extends T> $this$debounceInternal, Function1<? super T, Long> timeoutMillisSelector) {
        return FlowCoroutineKt.scopedFlow(new FlowKt__DelayKt$debounceInternal$1(timeoutMillisSelector, $this$debounceInternal, (Continuation<? super FlowKt__DelayKt$debounceInternal$1>) null));
    }

    public static final <T> Flow<T> sample(Flow<? extends T> $this$sample, long periodMillis) {
        if (periodMillis > 0) {
            return FlowCoroutineKt.scopedFlow(new FlowKt__DelayKt$sample$2(periodMillis, $this$sample, (Continuation<? super FlowKt__DelayKt$sample$2>) null));
        }
        throw new IllegalArgumentException("Sample period should be positive".toString());
    }

    public static /* synthetic */ ReceiveChannel fixedPeriodTicker$default(CoroutineScope coroutineScope, long j, long j2, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        return FlowKt.fixedPeriodTicker(coroutineScope, j, j2);
    }

    public static final ReceiveChannel<Unit> fixedPeriodTicker(CoroutineScope $this$fixedPeriodTicker, long delayMillis, long initialDelayMillis) {
        long j = delayMillis;
        long j2 = initialDelayMillis;
        boolean z = true;
        if (j >= 0) {
            if (j2 < 0) {
                z = false;
            }
            if (z) {
                return ProduceKt.produce$default($this$fixedPeriodTicker, (CoroutineContext) null, 0, new FlowKt__DelayKt$fixedPeriodTicker$3(initialDelayMillis, delayMillis, (Continuation<? super FlowKt__DelayKt$fixedPeriodTicker$3>) null), 1, (Object) null);
            }
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + j2 + " ms").toString());
        }
        throw new IllegalArgumentException(("Expected non-negative delay, but has " + j + " ms").toString());
    }

    /* renamed from: sample-HG0u8IE  reason: not valid java name */
    public static final <T> Flow<T> m1280sampleHG0u8IE(Flow<? extends T> $this$sample, long period) {
        return FlowKt.sample($this$sample, DelayKt.m1872toDelayMillisLRDsOJo(period));
    }
}
