package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
/* compiled from: Collect.kt */
public final class FlowKt__CollectKt$collectIndexed$2$emit$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt__CollectKt$collectIndexed$2 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__CollectKt$collectIndexed$2$emit$1(FlowKt__CollectKt$collectIndexed$2 flowKt__CollectKt$collectIndexed$2, Continuation<? super FlowKt__CollectKt$collectIndexed$2$emit$1> continuation) {
        super(continuation);
        this.this$0 = flowKt__CollectKt$collectIndexed$2;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
