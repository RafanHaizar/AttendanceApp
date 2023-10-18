package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo112d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b \u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B+\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0010H@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\u001f\u0010\u0012\u001a\u00020\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014H@ø\u0001\u0000¢\u0006\u0002\u0010\u0015J'\u0010\u0016\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00102\u0006\u0010\u0017\u001a\u00020\u0007H@ø\u0001\u0000¢\u0006\u0002\u0010\u0018J\u001f\u0010\u0019\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0010H¤@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0004X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001c"}, mo113d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "S", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectTo", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectWithContextUndispatched", "newContext", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "flowCollect", "toString", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: ChannelFlow.kt */
public abstract class ChannelFlowOperator<S, T> extends ChannelFlow<T> {
    protected final Flow<S> flow;

    public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return collect$suspendImpl(this, flowCollector, continuation);
    }

    /* access modifiers changed from: protected */
    public Object collectTo(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation) {
        return collectTo$suspendImpl(this, producerScope, continuation);
    }

    /* access modifiers changed from: protected */
    public abstract Object flowCollect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation);

    public ChannelFlowOperator(Flow<? extends S> flow2, CoroutineContext context, int capacity, BufferOverflow onBufferOverflow) {
        super(context, capacity, onBufferOverflow);
        this.flow = flow2;
    }

    /* access modifiers changed from: private */
    public final Object collectWithContextUndispatched(FlowCollector<? super T> collector, CoroutineContext newContext, Continuation<? super Unit> $completion) {
        Object withContextUndispatched$default = ChannelFlowKt.withContextUndispatched$default(newContext, ChannelFlowKt.withUndispatchedContextCollector(collector, $completion.getContext()), (Object) null, new ChannelFlowOperator$collectWithContextUndispatched$2(this, (Continuation<? super ChannelFlowOperator$collectWithContextUndispatched$2>) null), $completion, 4, (Object) null);
        return withContextUndispatched$default == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? withContextUndispatched$default : Unit.INSTANCE;
    }

    static /* synthetic */ Object collectTo$suspendImpl(ChannelFlowOperator channelFlowOperator, ProducerScope scope, Continuation $completion) {
        Object flowCollect = channelFlowOperator.flowCollect(new SendingCollector(scope), $completion);
        return flowCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? flowCollect : Unit.INSTANCE;
    }

    static /* synthetic */ Object collect$suspendImpl(ChannelFlowOperator channelFlowOperator, FlowCollector collector, Continuation $completion) {
        if (channelFlowOperator.capacity == -3) {
            CoroutineContext collectContext = $completion.getContext();
            CoroutineContext newContext = collectContext.plus(channelFlowOperator.context);
            if (Intrinsics.areEqual((Object) newContext, (Object) collectContext)) {
                Object flowCollect = channelFlowOperator.flowCollect(collector, $completion);
                return flowCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? flowCollect : Unit.INSTANCE;
            } else if (Intrinsics.areEqual((Object) newContext.get(ContinuationInterceptor.Key), (Object) collectContext.get(ContinuationInterceptor.Key))) {
                Object collectWithContextUndispatched = channelFlowOperator.collectWithContextUndispatched(collector, newContext, $completion);
                return collectWithContextUndispatched == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collectWithContextUndispatched : Unit.INSTANCE;
            }
        }
        Object collect = super.collect(collector, $completion);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }

    public String toString() {
        return this.flow + " -> " + super.toString();
    }
}
