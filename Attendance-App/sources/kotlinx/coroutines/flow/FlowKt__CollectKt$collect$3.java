package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo112d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006"}, mo113d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: Collect.kt */
public final class FlowKt__CollectKt$collect$3 implements FlowCollector<T> {
    final /* synthetic */ Function2<T, Continuation<? super Unit>, Object> $action;

    public FlowKt__CollectKt$collect$3(Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> $action2) {
        this.$action = $action2;
    }

    public Object emit(T value, Continuation<? super Unit> $completion) {
        Object invoke = this.$action.invoke(value, $completion);
        return invoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? invoke : Unit.INSTANCE;
    }

    public Object emit$$forInline(T value, Continuation<? super Unit> $completion) {
        InlineMarker.mark(4);
        new FlowKt__CollectKt$collect$3$emit$1(this, $completion);
        InlineMarker.mark(5);
        this.$action.invoke(value, $completion);
        return Unit.INSTANCE;
    }
}
