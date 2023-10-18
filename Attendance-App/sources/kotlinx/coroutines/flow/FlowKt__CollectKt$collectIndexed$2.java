package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\b"}, mo113d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collectIndexed$2", "Lkotlinx/coroutines/flow/FlowCollector;", "index", "", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: Collect.kt */
public final class FlowKt__CollectKt$collectIndexed$2 implements FlowCollector<T> {
    final /* synthetic */ Function3<Integer, T, Continuation<? super Unit>, Object> $action;
    private int index;

    public FlowKt__CollectKt$collectIndexed$2(Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> $action2) {
        this.$action = $action2;
    }

    public Object emit(T value, Continuation<? super Unit> $completion) {
        Function3<Integer, T, Continuation<? super Unit>, Object> function3 = this.$action;
        int index$iv = this.index;
        this.index = index$iv + 1;
        if (index$iv >= 0) {
            Object invoke = function3.invoke(Boxing.boxInt(index$iv), value, $completion);
            return invoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? invoke : Unit.INSTANCE;
        }
        throw new ArithmeticException("Index overflow has happened");
    }

    public Object emit$$forInline(T value, Continuation<? super Unit> $completion) {
        InlineMarker.mark(4);
        new FlowKt__CollectKt$collectIndexed$2$emit$1(this, $completion);
        InlineMarker.mark(5);
        Function3<Integer, T, Continuation<? super Unit>, Object> function3 = this.$action;
        int index$iv = this.index;
        this.index = index$iv + 1;
        if (index$iv >= 0) {
            function3.invoke(Integer.valueOf(index$iv), value, $completion);
            return Unit.INSTANCE;
        }
        throw new ArithmeticException("Index overflow has happened");
    }
}
