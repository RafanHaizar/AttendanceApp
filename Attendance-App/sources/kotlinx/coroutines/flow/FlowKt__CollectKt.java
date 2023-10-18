package kotlinx.coroutines.flow;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.NopCollector;

@Metadata(mo112d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002H@ø\u0001\u0000¢\u0006\u0002\u0010\u0003\u001aV\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u000223\b\u0004\u0010\u0005\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0006HHø\u0001\u0000¢\u0006\u0002\u0010\f\u001ak\u0010\r\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00022H\b\u0004\u0010\u0005\u001aB\b\u0001\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u000eHHø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001aT\u0010\u0012\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u000221\u0010\u0005\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\f\u001a/\u0010\u0013\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0002H@ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u001e\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00022\u0006\u0010\u0019\u001a\u00020\u001a\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, mo113d2 = {"collect", "", "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "T", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "value", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectIndexed", "Lkotlin/Function3;", "", "index", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectLatest", "emitAll", "Lkotlinx/coroutines/flow/FlowCollector;", "flow", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "launchIn", "Lkotlinx/coroutines/Job;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, mo114k = 5, mo115mv = {1, 6, 0}, mo117xi = 48, mo118xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Collect.kt */
final /* synthetic */ class FlowKt__CollectKt {
    public static final Object collect(Flow<?> $this$collect, Continuation<? super Unit> $completion) {
        Object collect = $this$collect.collect(NopCollector.INSTANCE, $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    public static final <T> Job launchIn(Flow<? extends T> $this$launchIn, CoroutineScope scope) {
        return BuildersKt__Builders_commonKt.launch$default(scope, (CoroutineContext) null, (CoroutineStart) null, new FlowKt__CollectKt$launchIn$1($this$launchIn, (Continuation<? super FlowKt__CollectKt$launchIn$1>) null), 3, (Object) null);
    }

    public static final <T> Object collectIndexed(Flow<? extends T> $this$collectIndexed, Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        Object collect = $this$collectIndexed.collect(new FlowKt__CollectKt$collectIndexed$2(action), $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    private static final <T> Object collectIndexed$$forInline(Flow<? extends T> $this$collectIndexed, Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        InlineMarker.mark(0);
        $this$collectIndexed.collect(new FlowKt__CollectKt$collectIndexed$2(action), $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    public static final <T> Object collectLatest(Flow<? extends T> $this$collectLatest, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        Object collect = FlowKt.collect(FlowKt__ContextKt.buffer$default(FlowKt.mapLatest($this$collectLatest, action), 0, (BufferOverflow) null, 2, (Object) null), $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    public static final <T> Object emitAll(FlowCollector<? super T> $this$emitAll, Flow<? extends T> flow, Continuation<? super Unit> $completion) {
        FlowKt.ensureActive($this$emitAll);
        Object collect = flow.collect($this$emitAll, $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Backwards compatibility with JS and K/N")
    public static final /* synthetic */ <T> Object collect(Flow<? extends T> $this$collect, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        Object collect = $this$collect.collect(new FlowKt__CollectKt$collect$3(action), $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Backwards compatibility with JS and K/N")
    private static final /* synthetic */ <T> Object collect$$forInline(Flow<? extends T> $this$collect, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        InlineMarker.mark(0);
        $this$collect.collect(new FlowKt__CollectKt$collect$3(action), $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
