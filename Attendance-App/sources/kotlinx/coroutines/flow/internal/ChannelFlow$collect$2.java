package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@Metadata(mo112d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, mo113d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.internal.ChannelFlow$collect$2", mo130f = "ChannelFlow.kt", mo131i = {}, mo132l = {123}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: ChannelFlow.kt */
final class ChannelFlow$collect$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ FlowCollector<T> $collector;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChannelFlow<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelFlow$collect$2(FlowCollector<? super T> flowCollector, ChannelFlow<T> channelFlow, Continuation<? super ChannelFlow$collect$2> continuation) {
        super(2, continuation);
        this.$collector = flowCollector;
        this.this$0 = channelFlow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelFlow$collect$2 channelFlow$collect$2 = new ChannelFlow$collect$2(this.$collector, this.this$0, continuation);
        channelFlow$collect$2.L$0 = obj;
        return channelFlow$collect$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ChannelFlow$collect$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                FlowCollector<T> flowCollector = this.$collector;
                ChannelFlow<T> channelFlow = this.this$0;
                this.label = 1;
                if (FlowKt.emitAll(flowCollector, channelFlow.produceImpl((CoroutineScope) this.L$0), (Continuation<? super Unit>) this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
