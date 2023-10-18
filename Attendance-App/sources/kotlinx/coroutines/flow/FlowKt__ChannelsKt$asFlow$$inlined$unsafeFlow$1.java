package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.channels.BroadcastChannel;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo113d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__ChannelsKt$asFlow$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ BroadcastChannel $this_asFlow$inlined;

    public FlowKt__ChannelsKt$asFlow$$inlined$unsafeFlow$1(BroadcastChannel broadcastChannel) {
        this.$this_asFlow$inlined = broadcastChannel;
    }

    public Object collect(FlowCollector<? super T> collector, Continuation<? super Unit> $completion) {
        Continuation<? super Unit> continuation = $completion;
        Object emitAll = FlowKt.emitAll(collector, this.$this_asFlow$inlined.openSubscription(), $completion);
        if (emitAll == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return emitAll;
        }
        return Unit.INSTANCE;
    }
}
