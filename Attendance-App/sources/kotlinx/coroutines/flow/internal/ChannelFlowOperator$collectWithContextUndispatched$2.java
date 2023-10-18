package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo112d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H@"}, mo113d2 = {"<anonymous>", "", "S", "T", "it", "Lkotlinx/coroutines/flow/FlowCollector;"}, mo114k = 3, mo115mv = {1, 6, 0}, mo117xi = 48)
@DebugMetadata(mo129c = "kotlinx.coroutines.flow.internal.ChannelFlowOperator$collectWithContextUndispatched$2", mo130f = "ChannelFlow.kt", mo131i = {}, mo132l = {152}, mo133m = "invokeSuspend", mo134n = {}, mo135s = {})
/* compiled from: ChannelFlow.kt */
final class ChannelFlowOperator$collectWithContextUndispatched$2 extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChannelFlowOperator<S, T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelFlowOperator$collectWithContextUndispatched$2(ChannelFlowOperator<S, T> channelFlowOperator, Continuation<? super ChannelFlowOperator$collectWithContextUndispatched$2> continuation) {
        super(2, continuation);
        this.this$0 = channelFlowOperator;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelFlowOperator$collectWithContextUndispatched$2 channelFlowOperator$collectWithContextUndispatched$2 = new ChannelFlowOperator$collectWithContextUndispatched$2(this.this$0, continuation);
        channelFlowOperator$collectWithContextUndispatched$2.L$0 = obj;
        return channelFlowOperator$collectWithContextUndispatched$2;
    }

    public final Object invoke(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return ((ChannelFlowOperator$collectWithContextUndispatched$2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                ChannelFlowOperator<S, T> channelFlowOperator = this.this$0;
                this.label = 1;
                if (channelFlowOperator.flowCollect((FlowCollector) this.L$0, this) != coroutine_suspended) {
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
