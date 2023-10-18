package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__LimitKt$drop$2$1", mo130f = "Limit.kt", mo131i = {}, mo132l = {25}, mo133m = "emit", mo134n = {}, mo135s = {})
/* compiled from: Limit.kt */
final class FlowKt__LimitKt$drop$2$1$emit$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt__LimitKt$drop$2$1<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__LimitKt$drop$2$1$emit$1(FlowKt__LimitKt$drop$2$1<? super T> flowKt__LimitKt$drop$2$1, Continuation<? super FlowKt__LimitKt$drop$2$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = flowKt__LimitKt$drop$2$1;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
