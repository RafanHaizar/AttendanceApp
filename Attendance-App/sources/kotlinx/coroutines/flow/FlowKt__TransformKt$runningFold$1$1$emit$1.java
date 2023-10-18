package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$1$1", mo130f = "Transform.kt", mo131i = {0}, mo132l = {103, 104}, mo133m = "emit", mo134n = {"this"}, mo135s = {"L$0"})
/* compiled from: Transform.kt */
final class FlowKt__TransformKt$runningFold$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt__TransformKt$runningFold$1$1<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__TransformKt$runningFold$1$1$emit$1(FlowKt__TransformKt$runningFold$1$1<? super T> flowKt__TransformKt$runningFold$1$1, Continuation<? super FlowKt__TransformKt$runningFold$1$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = flowKt__TransformKt$runningFold$1$1;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
