package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__ReduceKt", mo130f = "Reduce.kt", mo131i = {0, 0}, mo132l = {183}, mo133m = "firstOrNull", mo134n = {"result", "collector$iv"}, mo135s = {"L$0", "L$1"})
/* compiled from: Reduce.kt */
final class FlowKt__ReduceKt$firstOrNull$1<T> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;

    FlowKt__ReduceKt$firstOrNull$1(Continuation<? super FlowKt__ReduceKt$firstOrNull$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return FlowKt.firstOrNull((Flow) null, this);
    }
}
