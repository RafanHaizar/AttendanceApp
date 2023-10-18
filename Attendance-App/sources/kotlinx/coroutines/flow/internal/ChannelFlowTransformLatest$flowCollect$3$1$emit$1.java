package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1", mo130f = "Merge.kt", mo131i = {0, 0}, mo132l = {30}, mo133m = "emit", mo134n = {"this", "value"}, mo135s = {"L$0", "L$1"})
/* compiled from: Merge.kt */
final class ChannelFlowTransformLatest$flowCollect$3$1$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ChannelFlowTransformLatest$flowCollect$3.C00871<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelFlowTransformLatest$flowCollect$3$1$emit$1(ChannelFlowTransformLatest$flowCollect$3.C00871<? super T> r1, Continuation<? super ChannelFlowTransformLatest$flowCollect$3$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = r1;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
