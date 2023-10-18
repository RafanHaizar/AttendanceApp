package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 176)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$unsafeTransform$1$1", mo130f = "Emitters.kt", mo131i = {}, mo132l = {53}, mo133m = "emit", mo134n = {}, mo135s = {})
/* compiled from: Emitters.kt */
public final class FlowKt__EmittersKt$unsafeTransform$1$1$emit$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FlowKt__EmittersKt$unsafeTransform$1$1<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__EmittersKt$unsafeTransform$1$1$emit$1(FlowKt__EmittersKt$unsafeTransform$1$1<? super T> flowKt__EmittersKt$unsafeTransform$1$1, Continuation<? super FlowKt__EmittersKt$unsafeTransform$1$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = flowKt__EmittersKt$unsafeTransform$1$1;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
