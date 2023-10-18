package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__CountKt", mo130f = "Count.kt", mo131i = {0}, mo132l = {30}, mo133m = "count", mo134n = {"i"}, mo135s = {"L$0"})
/* compiled from: Count.kt */
final class FlowKt__CountKt$count$3<T> extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    FlowKt__CountKt$count$3(Continuation<? super FlowKt__CountKt$count$3> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return FlowKt.count((Flow) null, (Function2) null, this);
    }
}
