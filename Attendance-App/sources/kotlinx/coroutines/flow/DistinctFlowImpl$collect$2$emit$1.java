package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.DistinctFlowImpl$collect$2", mo130f = "Distinct.kt", mo131i = {}, mo132l = {81}, mo133m = "emit", mo134n = {}, mo135s = {})
/* compiled from: Distinct.kt */
final class DistinctFlowImpl$collect$2$emit$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DistinctFlowImpl$collect$2<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DistinctFlowImpl$collect$2$emit$1(DistinctFlowImpl$collect$2<? super T> distinctFlowImpl$collect$2, Continuation<? super DistinctFlowImpl$collect$2$emit$1> continuation) {
        super(continuation);
        this.this$0 = distinctFlowImpl$collect$2;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
