package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1;

@Metadata(mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1$1", mo130f = "Combine.kt", mo131i = {}, mo132l = {93}, mo133m = "emit", mo134n = {}, mo135s = {})
/* compiled from: Combine.kt */
final class CombineKt$zipImpl$1$1$second$1$1$emit$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CombineKt$zipImpl$1$1$second$1.C00951<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$zipImpl$1$1$second$1$1$emit$1(CombineKt$zipImpl$1$1$second$1.C00951<? super T> r1, Continuation<? super CombineKt$zipImpl$1$1$second$1$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = r1;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit(null, this);
    }
}
