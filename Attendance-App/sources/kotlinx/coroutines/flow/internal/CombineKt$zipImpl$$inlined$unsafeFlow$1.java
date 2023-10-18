package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class CombineKt$zipImpl$$inlined$unsafeFlow$1 implements Flow<R> {
    final /* synthetic */ Flow $flow$inlined;
    final /* synthetic */ Flow $flow2$inlined;
    final /* synthetic */ Function3 $transform$inlined;

    public CombineKt$zipImpl$$inlined$unsafeFlow$1(Flow flow, Flow flow2, Function3 function3) {
        this.$flow2$inlined = flow;
        this.$flow$inlined = flow2;
        this.$transform$inlined = function3;
    }

    public Object collect(FlowCollector<? super R> collector, Continuation<? super Unit> $completion) {
        Continuation<? super Unit> continuation = $completion;
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new CombineKt$zipImpl$1$1(collector, this.$flow2$inlined, this.$flow$inlined, this.$transform$inlined, (Continuation<? super CombineKt$zipImpl$1$1>) null), $completion);
        if (coroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return coroutineScope;
        }
        return Unit.INSTANCE;
    }
}
