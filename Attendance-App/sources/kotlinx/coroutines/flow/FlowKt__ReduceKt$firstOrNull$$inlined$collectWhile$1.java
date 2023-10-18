package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.AbortFlowException;

@Metadata(mo112d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/FlowKt__LimitKt$collectWhile$collector$1", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: Limit.kt */
public final class FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1 implements FlowCollector<T> {
    final /* synthetic */ Ref.ObjectRef $result$inlined;

    public FlowKt__ReduceKt$firstOrNull$$inlined$collectWhile$1(Ref.ObjectRef objectRef) {
        this.$result$inlined = objectRef;
    }

    public Object emit(T value, Continuation<? super Unit> $completion) {
        Continuation<? super Unit> continuation = $completion;
        this.$result$inlined.element = value;
        throw new AbortFlowException(this);
    }
}
